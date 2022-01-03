package com.ruoyi.system.service;

import com.ruoyi.system.domain.HeSeal;
import com.ruoyi.system.domain.vo.HeSealVo;
import com.ruoyi.system.domain.bo.HeSealBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 印章管理Service接口
 *
 * @author henriport
 * @date 2021-10-18
 */
public interface IHeSealService extends IServicePlus<HeSeal, HeSealVo> {
	/**
	 * 查询单个
	 * @return
	 */
	HeSealVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<HeSealVo> queryPageList(HeSealBo bo);

	/**
	 * 查询列表
	 */
	List<HeSealVo> queryList(HeSealBo bo);

    /**
     * 通过belong查询对应印章的title和url，注意，只有state=2（通过审核）的印章才会显示
     * @param userId
     * @return
     */
	List<HeSeal> queryListByUserId(Long userId);

	/**
	 * 根据新增业务对象插入印章管理
	 * @param bo 印章管理新增业务对象
	 * @return
	 */
	Boolean insertByBo(HeSealBo bo);

	/**
	 * 根据编辑业务对象修改印章管理
	 * @param bo 印章管理编辑业务对象
	 * @return
	 */
	Boolean updateByBo(HeSealBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
