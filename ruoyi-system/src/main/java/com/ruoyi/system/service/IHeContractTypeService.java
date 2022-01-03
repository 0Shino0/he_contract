package com.ruoyi.system.service;

import com.ruoyi.system.domain.HeContractType;
import com.ruoyi.system.domain.vo.HeContractTypeVo;
import com.ruoyi.system.domain.bo.HeContractTypeBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 电子合同类型Service接口
 *
 * @author henriport
 * @date 2021-10-18
 */
public interface IHeContractTypeService extends IServicePlus<HeContractType, HeContractTypeVo> {
	/**
	 * 查询单个
	 * @return
	 */
	HeContractTypeVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<HeContractTypeVo> queryPageList(HeContractTypeBo bo);

	/**
	 * 查询列表
	 */
	List<HeContractTypeVo> queryList(HeContractTypeBo bo);

	/**
	 * 根据新增业务对象插入电子合同类型
	 * @param bo 电子合同类型新增业务对象
	 * @return
	 */
	Boolean insertByBo(HeContractTypeBo bo);

	/**
	 * 根据编辑业务对象修改电子合同类型
	 * @param bo 电子合同类型编辑业务对象
	 * @return
	 */
	Boolean updateByBo(HeContractTypeBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
