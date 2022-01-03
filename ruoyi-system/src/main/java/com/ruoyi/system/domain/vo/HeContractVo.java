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
 * 电子合同管理视图对象 he_contract
 *
 * @author henriport
 * @date 2021-10-18
 */
@Data
@ApiModel("电子合同管理视图对象")
@ExcelIgnoreUnannotated
public class HeContractVo {

	private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 合同标题
     */
	@ExcelProperty(value = "合同标题")
	@ApiModelProperty("合同标题")
	private String title;

    /**
     * 合同描述
     */
	@ExcelProperty(value = "合同描述")
	@ApiModelProperty("合同描述")
	private String description;

    /**
     * 合同类别
     */
	@ExcelProperty(value = "合同类别")
	@ApiModelProperty("合同类别")
	private String type;

    /**
     * 归属于
     */
	@ExcelProperty(value = "归属于")
	@ApiModelProperty("归属于")
	private Long belong;

    /**
     * oss存储路径
     */
	@ExcelProperty(value = "oss存储路径")
	@ApiModelProperty("oss存储路径")
	private String ossUrl;

    /**
     * ipfs链路径
     */
	@ExcelProperty(value = "ipfs链路径")
	@ApiModelProperty("ipfs链路径")
	private String ipfsHash;

    /**
     * 是否入链
     */
	@ExcelProperty(value = "是否入链")
	@ApiModelProperty("是否入链")
	private Integer isLink;

    /**
     * 是否删除
     */
	@ExcelProperty(value = "是否删除")
	@ApiModelProperty("是否删除")
	private Integer isDelete;

    /**
     * 审核状态（0:未申请,1:未审核,2:通过,3:未通过）
     */
	@ExcelProperty(value = "审核状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=:未申请,1:未审核,2:通过,3:未通过")
	@ApiModelProperty("审核状态（0:未申请,1:未审核,2:通过,3:未通过）")
	private String state;


}
