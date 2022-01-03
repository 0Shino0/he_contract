package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.system.domain.HeUserInfo;

/**
 * 用户详细信息服务层
 *
 * @author Henriport
 * @since 2021/11/11
 */
public interface IHeUserInfoService extends IService<HeUserInfo> {

    HeUserInfo getUserInfoById(Long userId);
}
