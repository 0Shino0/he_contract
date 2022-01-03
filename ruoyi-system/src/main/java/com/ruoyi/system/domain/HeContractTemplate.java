package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 电子合同模板管理对象 he_contract_template
 *
 * @author henriport
 * @date 2021-10-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("he_contract_template")
public class HeContractTemplate implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 模板标题
     */
    private String title;

    /**
     * oss存储路径
     */
    private String ossUrl;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 模板类别
     */
    private String type;

    /**
     * 是否删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;

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
