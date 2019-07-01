package com.mlnx.smart.auth.config;

import com.mlnx.smart.auth.entity.UserInfo;
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

            if (userInfo != null) {

                if (encoder.matches(password, userInfo.getPassword())) {
                    return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
                }else{
                    return null;
                }
            }
        }

        // 手机号登入
        String phone = requestParameters.get("phone");
        if (!StringUtils.isEmpty(phone)) {

            // 手机验证码OK
            String code = requestParameters.get("code");

            // 根据获取用户信息
            UserInfo userInfo = userService.findByPhone(phone);

            if (userInfo != null) {
                return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
            }
        }

        // 磁卡登入
        String card = requestParameters.get("card");
        if (!StringUtils.isEmpty(card)) {

            // 根据获取用户信息
            UserInfo userInfo = userService.findByCard(card);

            if (userInfo != null) {
                return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
            }
        }

        // ID登入
        String id = requestParameters.get("id");
        if (!StringUtils.isEmpty(id)) {

            // 根据获取用户信息
            UserInfo userInfo = userService.findById(id);

            if (userInfo != null) {
                return new MlnxOauthUser(userInfo.getUsername(), userInfo.getPassword());
            }
        }

        return null;

    }
}
