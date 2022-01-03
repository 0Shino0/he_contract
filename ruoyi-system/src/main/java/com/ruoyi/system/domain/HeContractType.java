package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 电子合同类型对象 he_contract_type
 *
 * @author henriport
 * @date 2021-10-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("he_contract_type")
public class HeContractType implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 合同类型
     */
    private String type;

}
