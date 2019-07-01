package com.mlnx.smart.auth.config;

import com.mlnx.smart.auth.entity.UserInfo;
import com.mlnx.smart.auth.exception.LoginException;
import com.mlnx.smart.auth.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.shan.security.app.authorize.AppAuthorizeManager;
import org.shan.security.app.authorize.MlnxOauthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by amanda.shan on 2019/6/20.
 */
@Component
public class SmartAppAuthorizeManager implements AppAuthorizeManager {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public MlnxOauthUser authenticate(Map<String, String> requestParameters, ClientDetails authenticatedClient) {

        // 手机号登入
        String username = requestParameters.get("username");
        String password = requestParameters.get("password");
        if (!StringUtils.isEmpty(username)) {

            // 根据获取用户信息
            UserInfo userInfo = userService.findByUsername(username);

            if (userInfo != null && encoder.matches(password, userInfo.getPassword())) {

                return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
            }else{
                throw new LoginException("用户名密码错误");
            }
        }

        // 手机号登入
        username = requestParameters.get("phone");
        if (!StringUtils.isEmpty(username)) {

            // 手机验证码OK
            String code = requestParameters.get("code");

            // 根据获取用户信息
            UserInfo userInfo = userService.findByPhone(username);

            if (userInfo != null) {
                return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
            }else{
                throw new LoginException("手机号不存在");
            }
        }

        // 磁卡登入
        username = requestParameters.get("card");
        if (!StringUtils.isEmpty(username)) {

            // 根据获取用户信息
            UserInfo userInfo = userService.findByCard(username);

            if (userInfo != null) {
                return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
            }else{
                throw new LoginException("磁卡用户不存在");
            }
        }

        // ID登入
        username = requestParameters.get("id");
        if (!StringUtils.isEmpty(username)) {

            // 根据获取用户信息
            UserInfo userInfo = userService.findById(username);

            if (userInfo != null) {
                return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
            }else{
                throw new LoginException("用户ID不存在");
            }
        }

        throw new LoginException("登录参数错误");

    }
}
