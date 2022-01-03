package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.HeSeal;
import com.ruoyi.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.HeContractTemplateBo;
import com.ruoyi.system.domain.vo.HeContractTemplateVo;
import com.ruoyi.system.domain.HeContractTemplate;
import com.ruoyi.system.mapper.HeContractTemplateMapper;
import com.ruoyi.system.service.IHeContractTemplateService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 电子合同模板管理Service业务层处理
 *
 * @author henriport
 * @date 2021-10-18
 */
@Service
public class HeContractTemplateServiceImpl extends ServicePlusImpl<HeContractTemplateMapper, HeContractTemplate, HeContractTemplateVo> implements IHeContractTemplateService {

    @Resource
    private ISysOssService iSysOssService;

    @Override
    public HeContractTemplateVo queryById(Long id) {
        return getVoById(id);
    }

    @Override
    public TableDataInfo<HeContractTemplateVo> queryPageList(HeContractTemplateBo bo) {
        PagePlus<HeContractTemplate, HeContractTemplateVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<HeContractTemplateVo> queryList(HeContractTemplateBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<HeContractTemplate> buildQueryWrapper(HeContractTemplateBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HeContractTemplate> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, HeContractTemplate::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), HeContractTemplate::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getOssUrl()), HeContractTemplate::getOssUrl, bo.getOssUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), HeContractTemplate::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), HeContractTemplate::getType, bo.getType());
        lqw.eq(bo.getIsDelete() != null, HeContractTemplate::getIsDelete, bo.getIsDelete());
        lqw.orderByAsc(HeContractTemplate::getId);
        return lqw;
    }

    @Override
    public Boolean insertByBo(HeContractTemplateBo bo) {
        HeContractTemplate add = BeanUtil.toBean(bo, HeContractTemplate.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HeContractTemplateBo bo) {
        HeContractTemplate update = BeanUtil.toBean(bo, HeContractTemplate.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(HeContractTemplate entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
            // 获取ossurl
            Collection<String> urlList;
            List<HeContractTemplate> heContractTemplates = listByIds(ids);
            urlList = heContractTemplates.stream().map(heContractTemplate -> heContractTemplate.getOssUrl()).collect(Collectors.toList());
            // 删除oss文件
            iSysOssService.deleteWithValidByUrls(urlList);
        }
        return removeByIds(ids);
    }
}
