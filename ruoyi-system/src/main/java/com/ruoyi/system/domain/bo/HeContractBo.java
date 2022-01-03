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
 * 电子合同管理业务对象 he_contract
 *
 * @author henriport
 * @date 2021-10-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("电子合同管理业务对象")
public class HeContractBo extends BaseEntity {

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    /**
     * 合同标题
     */
    @ApiModelProperty(value = "合同标题", required = true)
    @NotBlank(message = "合同标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 合同描述
     */
    @ApiModelProperty(value = "合同描述", required = true)
    @NotBlank(message = "合同描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 合同类别
     */
    @ApiModelProperty(value = "合同类别", required = true)
    @NotBlank(message = "合同类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 归属于
     */
    @ApiModelProperty(value = "归属于", required = true)
    private Long belong;

    /**
     * oss存储路径
     */
    @ApiModelProperty(value = "oss存储路径", required = true)
    private String ossUrl;

    /**
     * ipfs链路径
     */
    @ApiModelProperty(value = "ipfs链路径", required = true)
    private String ipfsHash;

    /**
     * 是否入链
     */
    @ApiModelProperty(value = "是否入链", required = true)
    private Integer isLink;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除", required = true)
    private Integer isDelete;

    /**
     * 审核状态（0:未申请,1:未审核,2:通过,3:未通过）
     */
    @ApiModelProperty(value = "审核状态（0:未申请,1:未审核,2:通过,3:未通过）", required = true)
    private String state;


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
