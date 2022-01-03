package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysOss;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.HeContractTypeBo;
import com.ruoyi.system.domain.vo.HeContractTypeVo;
import com.ruoyi.system.domain.HeContractType;
import com.ruoyi.system.mapper.HeContractTypeMapper;
import com.ruoyi.system.service.IHeContractTypeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 电子合同类型Service业务层处理
 *
 * @author henriport
 * @date 2021-10-18
 */
@Service
public class HeContractTypeServiceImpl extends ServicePlusImpl<HeContractTypeMapper, HeContractType, HeContractTypeVo> implements IHeContractTypeService {

    @Resource
    private IHeContractTypeService iHeContractTypeService;

    @Override
    public HeContractTypeVo queryById(Long id) {
        return getVoById(id);
    }

    @Override
    public TableDataInfo<HeContractTypeVo> queryPageList(HeContractTypeBo bo) {
        PagePlus<HeContractType, HeContractTypeVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<HeContractTypeVo> queryList(HeContractTypeBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<HeContractType> buildQueryWrapper(HeContractTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HeContractType> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, HeContractType::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), HeContractType::getType, bo.getType());
        lqw.orderByAsc(HeContractType::getId);
        return lqw;
    }

    @Override
    public Boolean insertByBo(HeContractTypeBo bo) {
        HeContractType add = BeanUtil.toBean(bo, HeContractType.class);
        if (!validEntityBeforeSave(add)) {
            return false;
        }
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HeContractTypeBo bo) {
        HeContractType update = BeanUtil.toBean(bo, HeContractType.class);
        if (!validEntityBeforeSave(update)) {
            return false;
        }
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     * 判断是否已有相同的类型，有则vaild为false
     *
     * @param entity 实体类数据
     */
    private Boolean validEntityBeforeSave(HeContractType entity) {
        //TODO 做一些数据校验,如唯一约束
        QueryWrapper<HeContractType> heContractTypeQueryWrapper = new QueryWrapper<>();
        heContractTypeQueryWrapper.eq("type", entity.getType());
        HeContractType one = iHeContractTypeService.getOne(heContractTypeQueryWrapper);
        // 如果有相同类型，返回false,无则返回true
        return one == null;
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
