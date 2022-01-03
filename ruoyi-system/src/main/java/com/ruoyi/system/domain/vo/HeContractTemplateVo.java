package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 电子合同模板管理视图对象 he_contract_template
 *
 * @author henriport
 * @date 2021-10-18
 */
@Data
@ApiModel("电子合同模板管理视图对象")
@ExcelIgnoreUnannotated
public class HeContractTemplateVo {

	private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 模板标题
     */
	@ExcelProperty(value = "模板标题")
	@ApiModelProperty("模板标题")
	private String title;

    /**
     * oss存储路径
     */
	@ExcelProperty(value = "oss存储路径")
	@ApiModelProperty("oss存储路径")
	private String ossUrl;

    /**
     * 模板描述
     */
	@ExcelProperty(value = "模板描述")
	@ApiModelProperty("模板描述")
	private String description;

    /**
     * 模板类别
     */
	@ExcelProperty(value = "模板类别")
	@ApiModelProperty("模板类别")
	private String type;

    /**
     * 是否删除
     */
	@ExcelProperty(value = "是否删除")
	@ApiModelProperty("是否删除")
	private Integer isDelete;


}
