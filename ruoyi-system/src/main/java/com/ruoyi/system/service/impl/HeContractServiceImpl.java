package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aliyun.oss.OSS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itextpdf.text.DocumentException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.fabric.FabricUtils;
import com.ruoyi.common.utils.ipfs.IPFSUtils;
import com.ruoyi.common.utils.pdf.PDFUtils;
import com.ruoyi.system.service.ISysOssService;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.HeContractBo;
import com.ruoyi.system.domain.vo.HeContractVo;
import com.ruoyi.system.domain.HeContract;
import com.ruoyi.system.mapper.HeContractMapper;
import com.ruoyi.system.service.IHeContractService;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * 电子合同管理Service业务层处理
 *
 * @author henriport
 * @date 2021-10-18
 */
@Slf4j
@Service
public class HeContractServiceImpl extends ServicePlusImpl<HeContractMapper, HeContract, HeContractVo> implements IHeContractService {

    @Resource
    private ISysOssService iSysOssService;

    @Resource
    private FabricUtils fabricUtils;

    @Override
    public HeContractVo queryById(Long id) {
        return getVoById(id);
    }

    @Override
    public TableDataInfo<HeContractVo> queryPageList(HeContractBo bo) {
        PagePlus<HeContract, HeContractVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<HeContractVo> queryList(HeContractBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    @Override
    public List<HeContract> queryListByUserId(Long userId) {
        QueryWrapper<HeContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("belong", userId);
        return list(queryWrapper);
    }

    private LambdaQueryWrapper<HeContract> buildQueryWrapper(HeContractBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HeContract> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, HeContract::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), HeContract::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), HeContract::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), HeContract::getType, bo.getType());
        lqw.eq(bo.getBelong() != null, HeContract::getBelong, bo.getBelong());
        lqw.eq(StringUtils.isNotBlank(bo.getOssUrl()), HeContract::getOssUrl, bo.getOssUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getIpfsHash()), HeContract::getIpfsHash, bo.getIpfsHash());
        lqw.eq(bo.getIsLink() != null, HeContract::getIsLink, bo.getIsLink());
        lqw.eq(bo.getIsDelete() != null, HeContract::getIsDelete, bo.getIsDelete());
        lqw.eq(StringUtils.isNotBlank(bo.getState()), HeContract::getState, bo.getState());
        lqw.orderByAsc(HeContract::getId);
        return lqw;
    }

    @Override
    public Boolean insertByBo(HeContractBo bo) {
        HeContract add = BeanUtil.toBean(bo, HeContract.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HeContractBo bo) {
        HeContract update = BeanUtil.toBean(bo, HeContract.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(HeContract entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
            // 获取ossurl
            Collection<String> urlList;
            List<HeContract> heContracts = listByIds(ids);
            urlList = heContracts.stream().map(heContract -> heContract.getOssUrl()).collect(Collectors.toList());
            // 删除oss文件
            iSysOssService.deleteWithValidByUrls(urlList);
        }
        return removeByIds(ids);
    }

    @Override
    public Boolean setStateById(Long id, Boolean type, String pdfUrl, String sealUrl) {
        String state = type ? "2" : "3";
        UpdateWrapper<HeContract> updateWrapper = new UpdateWrapper<>();
        // 如果审核通过
        try {
            if (type && pdfUrl != null && sealUrl != null) {
                // TODO 调用修改pdf方法,OSS文件重写
                byte[] data = PDFUtils.signPdf(pdfUrl, sealUrl);
                iSysOssService.updateFile(data, pdfUrl);
                // TODO 调用文件入ipfs方法
                String ipfsHash = IPFSUtils.upload(data);
                // 修改id对应的contract数据
                updateWrapper.eq("id", id)
                    .set("state", state)
                    .set("ipfs_hash", ipfsHash)
                    .set("is_link", 1);
                // TODO 调用数据入链方法，整体数据存入Fabric
                // 初始化
                Network network = fabricUtils.getNetwork();
                Contract contract = fabricUtils.getContract();
                // 获取contract
                HeContractVo heContractVo = queryById(id);
                byte[] addContractResult = contract.createTransaction("addContract")
                    .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                    .submit(
                        heContractVo.getId().toString(),
                        heContractVo.getTitle(),
                        heContractVo.getDescription(),
                        heContractVo.getType(),
                        ipfsHash,
                        heContractVo.getBelong().toString(),
                        heContractVo.getBelong().toString());
                log.info("addContract："+new String(addContractResult, StandardCharsets.UTF_8));
            }
        } catch (DocumentException documentException) {
            documentException.printStackTrace();
            return false;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return update(updateWrapper);
    }
}
