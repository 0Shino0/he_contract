package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.entity.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详细信息
 *
 * @author Henriport
 * @since 2021/11/11
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("he_user_info")
public class HeUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 归属于哪个用户
     */
    private Long belong;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 电话号码
     */
    private String phone;

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
