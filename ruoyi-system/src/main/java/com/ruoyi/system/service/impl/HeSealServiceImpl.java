package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.HeSealBo;
import com.ruoyi.system.domain.vo.HeSealVo;
import com.ruoyi.system.domain.HeSeal;
import com.ruoyi.system.mapper.HeSealMapper;
import com.ruoyi.system.service.IHeSealService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 印章管理Service业务层处理
 *
 * @author henriport
 * @date 2021-10-18
 */
@Service
public class HeSealServiceImpl extends ServicePlusImpl<HeSealMapper, HeSeal, HeSealVo> implements IHeSealService {

    @Resource
    private ISysOssService iSysOssService;

    @Override
    public HeSealVo queryById(Long id) {
        return getVoById(id);
    }

    @Override
    public TableDataInfo<HeSealVo> queryPageList(HeSealBo bo) {
        PagePlus<HeSeal, HeSealVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<HeSealVo> queryList(HeSealBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    @Override
    public List<HeSeal> queryListByUserId(Long userId) {
        QueryWrapper<HeSeal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",2).eq("belong",userId).select("id","title","oss_url");
        return list(queryWrapper);
    }

    private LambdaQueryWrapper<HeSeal> buildQueryWrapper(HeSealBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HeSeal> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, HeSeal::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), HeSeal::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getOssUrl()), HeSeal::getOssUrl, bo.getOssUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getIpfsHash()), HeSeal::getIpfsHash, bo.getIpfsHash());
        lqw.eq(bo.getBelong() != null, HeSeal::getBelong, bo.getBelong());
        lqw.eq(bo.getIsLink() != null, HeSeal::getIsLink, bo.getIsLink());
        lqw.eq(bo.getIsDelete() != null, HeSeal::getIsDelete, bo.getIsDelete());
        lqw.eq(StringUtils.isNotBlank(bo.getState()), HeSeal::getState, bo.getState());
        lqw.orderByAsc(HeSeal::getId);
        return lqw;
    }

    @Override
    public Boolean insertByBo(HeSealBo bo) {
        HeSeal add = BeanUtil.toBean(bo, HeSeal.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HeSealBo bo) {
        HeSeal update = BeanUtil.toBean(bo, HeSeal.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(HeSeal entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
            // 获取ossurl
            Collection<String> urlList;
            List<HeSeal> heSeals = listByIds(ids);
            urlList = heSeals.stream().map(heSeal -> heSeal.getOssUrl()).collect(Collectors.toList());
            // 删除oss文件
            iSysOssService.deleteWithValidByUrls(urlList);
        }
        return removeByIds(ids);
    }
}
