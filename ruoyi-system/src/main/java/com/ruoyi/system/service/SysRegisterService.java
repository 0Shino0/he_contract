package com.ruoyi.system.service;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.service.LogininforService;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Service
public class SysRegisterService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private LogininforService asyncService;

    @Autowired
    private SysLoginService loginService;

    /**
     * 注册
     */
    public Map<String, Object> register(RegisterBody registerBody) {
        Map<String, Object> ajaxResult = new HashMap<>();
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 空值判断，为空则返回false
        Boolean isWxLogin = registerBody.getIsWxLogin();
        isWxLogin = Optional.ofNullable(isWxLogin).orElse(false);
        // 验证码开关
        if (captchaOnOff&&!isWxLogin) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username))) {
            try {
                // 用户名存在，尝试登录
                // 生成令牌
                String token = loginService.login(registerBody.getUsername(),
                registerBody.getPassword(), registerBody.getCode(),
                registerBody.getUuid(),registerBody.getIsWxLogin());
                // 将token数据返回
                ajaxResult.put("token",token);
                // 返回msg
                msg = "登录成功";

            }catch (UserPasswordNotMatchException e){
                msg = "账号'" + username + "'已存在，或登录密码错误";
            }
        } else {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                asyncService.recordLogininfor(username, Constants.REGISTER,
                        MessageUtils.message("user.register.success"), ServletUtils.getRequest());
                // 注册成功，直接登录
                // 生成令牌
                String token = loginService.login(registerBody.getUsername(),
                registerBody.getPassword(), registerBody.getCode(),
                registerBody.getUuid(),registerBody.getIsWxLogin());
                // 将token数据返回
                ajaxResult.put("token",token);
                // 返回msg
                msg = "注册并登录成功";
            }
        }
        ajaxResult.put("msg",msg);
        return ajaxResult;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
