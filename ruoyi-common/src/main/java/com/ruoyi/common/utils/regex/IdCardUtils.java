package com.ruoyi.common.utils.regex;

import com.ruoyi.common.utils.StringUtils;

/**
 * 身份证相关正则
 *
 * @author Henriport
 * @since 2021/11/11
 */
public class IdCardUtils {
     /**
     * 身份证格式校验正则
     */
    // public static final String IDCARD_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    /**
     * 身份证脱敏筛选正则
     */
    public static final String IDCARD_BLUR_REGEX = "(\\w{5})\\w*(\\w{4})";

    /**
     * 身份证脱敏替换正则
     */
    public static final String IDCARD_BLUR_REPLACE_REGEX = "$1*********$2";

    /**
     * 身份证格式校验
     * @param idCard
     * @return
     */
    // public static final boolean checkPhone(String idCard) {
    //     if (StringUtils.isEmpty(idCard)) {
    //         return false;
    //     }
    //     return phone.matches(IDCARD_REGEX);
    // }

    /**
     * 身份证脱敏处理
     * @param idCard
     * @return
     */
    public static final String blurIdCard(String idCard) {
        return idCard.replaceAll(IDCARD_BLUR_REGEX, IDCARD_BLUR_REPLACE_REGEX);
    }
}
