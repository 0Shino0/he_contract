package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.system.domain.HeSeal;
import com.ruoyi.system.domain.HeUserInfo;

/**
 * 用户详情表 数据层
 *
 * @author Henriport
 * @since 2021/11/11
 */
public interface HeUserInfoMapper extends BaseMapperPlus<HeUserInfo> {

    HeUserInfo getUserInfoById(Long userId);
}
