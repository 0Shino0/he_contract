package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itextpdf.text.DocumentException;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.fabric.FabricUtils;
import com.ruoyi.common.utils.ipfs.IPFSUtils;
import com.ruoyi.common.utils.pdf.PDFUtils;
import com.ruoyi.system.service.ISysOssService;
import com.ruoyi.system.service.ISysUserService;
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
            //别疑惑，为什么这里要调一下更新的方法，因为上面那个save会改变合同的审核状态，所以这里更新一下，完成曲线救国
            updateByBo(bo);
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HeContractBo bo) {
        // 修改合同的一个逻辑，就是为了保持本地和区块链同步，已经入链的合同需要出链，是否入链状态改为否
        // 是否删除的状态需要改变是来提醒用户改合同因改变而被迫出链，需要重新提交审核
        // 重新提交之后是否删除状态改变为否，是否入链状态改变为是
        // 获取数据库中的数据
        HeContractVo heContractVo = queryById(bo.getId());
        // 判断前后端数据是否一致，一致就跳过，不一致就出链
        boolean isSame = !bo.getTitle().equals(heContractVo.getTitle()) ||
            !bo.getDescription().equals(heContractVo.getDescription()) ||
            !bo.getType().equals(heContractVo.getType()) ||
            !bo.getBelong().equals(heContractVo.getBelong()) ||
            !bo.getOssUrl().equals(heContractVo.getOssUrl()) ||
            !bo.getIpfsHash().equals(heContractVo.getIpfsHash()) ||
            !bo.getState().equals(heContractVo.getState());
        //出链的另一个条件就是该合同本身是入链的
        if (isSame && heContractVo.getIsLink() == 1) {
            if ("2".equals(bo.getState()) || "3".equals(bo.getState())) {
                bo.setState("1");
            }
            // TODO 调用数据出链方法，删除对应的数据
            // 初始化
            Network network = fabricUtils.getNetwork();
            Contract contract = fabricUtils.getContract();
            // 当合同的状态为入链状态才进行区块删除
            byte[] deleteContractResult = new byte[0];
            try {
                deleteContractResult = contract.createTransaction("deleteContractById")
                    .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                    .submit(
                        bo.getId().toString());
            } catch (ContractException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            log.info("deleteContractById：" + new String(deleteContractResult, StandardCharsets.UTF_8));
            //出链完毕，还需要更新一下是否删除的状态
            bo.setIsDelete(1);
            //出链完毕，还需要更新一下是否入链的状态
            bo.setIsLink(0);
        }
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
            // TODO 调用数据出链方法，删除对应的数据
            // 初始化区块链
            Network network = fabricUtils.getNetwork();
            Contract contract = fabricUtils.getContract();
            ids.forEach(id -> {
                // 获取contract
                HeContractVo heContractVo = queryById(id);
                // 当合同的状态为入链状态才进行区块删除
                if (heContractVo.getIsLink() == 1) {
                    byte[] deleteContractResult = new byte[0];
                    try {
                        deleteContractResult = contract.createTransaction("deleteContractById")
                            .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                            .submit(
                                id.toString());
                    } catch (ContractException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    log.info("deleteContractById：" + new String(deleteContractResult, StandardCharsets.UTF_8));
                }
            });
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
                    // 下一行代码主要承接修改后重新审核通过，更改是否删除的状态为否
                    .set("is_delete", 0)
                    .set("is_link", 1);
                // TODO 调用数据入链方法，整体数据存入Fabric
                // 初始化区块链
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
                log.info("addContract：" + new String(addContractResult, StandardCharsets.UTF_8));
            } else {
                //如果审核不通过，先要出链，然后修改文件状态为未通过
                // TODO 调用数据出链方法，删除对应的数据
                // 初始化区块链
                Network network = fabricUtils.getNetwork();
                Contract contract = fabricUtils.getContract();
                // 当合同的状态为入链状态才进行区块删除
                byte[] deleteContractResult = new byte[0];
                try {
                    deleteContractResult = contract.createTransaction("deleteContractById")
                        .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                        .submit(
                            id.toString());
                } catch (ContractException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                log.info("deleteContractById：" + new String(deleteContractResult, StandardCharsets.UTF_8));
                updateWrapper.eq("id", id).
                    set("is_link", 0).
                    set("is_delete", 1).
                    set("state", state);
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
