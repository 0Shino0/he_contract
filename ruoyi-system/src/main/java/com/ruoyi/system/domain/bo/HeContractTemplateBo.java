package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电子合同模板管理业务对象 he_contract_template
 *
 * @author henriport
 * @date 2021-10-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("电子合同模板管理业务对象")
public class HeContractTemplateBo extends BaseEntity {

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    /**
     * 模板标题
     */
    @ApiModelProperty(value = "模板标题", required = true)
    @NotBlank(message = "模板标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * oss存储路径
     */
    @ApiModelProperty(value = "oss存储路径", required = true)
    @NotBlank(message = "oss存储路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ossUrl;

    /**
     * 模板描述
     */
    @ApiModelProperty(value = "模板描述", required = true)
    @NotBlank(message = "模板描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 模板类别
     */
    @ApiModelProperty(value = "模板类别", required = true)
    @NotBlank(message = "模板类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除", required = true)
    private Integer isDelete;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
