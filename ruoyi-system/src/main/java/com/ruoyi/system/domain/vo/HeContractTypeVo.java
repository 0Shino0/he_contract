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
 * 电子合同类型视图对象 he_contract_type
 *
 * @author henriport
 * @date 2021-10-18
 */
@Data
@ApiModel("电子合同类型视图对象")
@ExcelIgnoreUnannotated
public class HeContractTypeVo {

	private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 合同类型
     */
	@ExcelProperty(value = "合同类型")
	@ApiModelProperty("合同类型")
	private String type;


}
