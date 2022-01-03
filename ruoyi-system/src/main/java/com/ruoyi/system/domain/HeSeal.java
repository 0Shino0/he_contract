package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 印章管理对象 he_seal
 *
 * @author henriport
 * @date 2021-10-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("he_seal")
public class HeSeal implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 印章标题
     */
    private String title;

    /**
     * oss存储路径
     */
    private String ossUrl;

    /**
     * ipfs链路径
     */
    private String ipfsHash;

    /**
     * 归属于
     */
    private Long belong;

    /**
     * 是否入链
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer isLink;

    /**
     * 是否删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;

    /**
     * 审核状态（0:未申请,1:未审核,2:通过,3:未通过）
     */
    @TableField(fill = FieldFill.INSERT)
    private String state;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
