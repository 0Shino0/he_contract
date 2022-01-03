package com.ruoyi.system.service;

import com.ruoyi.system.domain.HeContractTemplate;
import com.ruoyi.system.domain.vo.HeContractTemplateVo;
import com.ruoyi.system.domain.bo.HeContractTemplateBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 电子合同模板管理Service接口
 *
 * @author henriport
 * @date 2021-10-18
 */
public interface IHeContractTemplateService extends IServicePlus<HeContractTemplate, HeContractTemplateVo> {
	/**
	 * 查询单个
	 * @return
	 */
	HeContractTemplateVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<HeContractTemplateVo> queryPageList(HeContractTemplateBo bo);

	/**
	 * 查询列表
	 */
	List<HeContractTemplateVo> queryList(HeContractTemplateBo bo);

	/**
	 * 根据新增业务对象插入电子合同模板管理
	 * @param bo 电子合同模板管理新增业务对象
	 * @return
	 */
	Boolean insertByBo(HeContractTemplateBo bo);

	/**
	 * 根据编辑业务对象修改电子合同模板管理
	 * @param bo 电子合同模板管理编辑业务对象
	 * @return
	 */
	Boolean updateByBo(HeContractTemplateBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
