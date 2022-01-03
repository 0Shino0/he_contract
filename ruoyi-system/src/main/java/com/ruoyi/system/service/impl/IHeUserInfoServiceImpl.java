package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.system.domain.HeUserInfo;
import com.ruoyi.system.mapper.HeUserInfoMapper;
import com.ruoyi.system.service.IHeUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Henriport
 * @since 2021/11/11
 */
@Service
public class IHeUserInfoServiceImpl extends ServicePlusImpl<HeUserInfoMapper,HeUserInfo,HeUserInfo> implements IHeUserInfoService {

    @Autowired
    private HeUserInfoMapper userInfoMapper;

    @Override
    public HeUserInfo getUserInfoById(Long userId) {
        return userInfoMapper.getUserInfoById(userId);
    }
}
