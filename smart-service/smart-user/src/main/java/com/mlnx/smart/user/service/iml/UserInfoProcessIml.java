package com.mlnx.smart.user.service.iml;

import com.mlnx.smart.user.entity.UserInfo;
import com.mlnx.smart.user.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.shan.security.resource.config.UserInfoProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by amanda.shan on 2019/7/1.
 */
@Service
public class UserInfoProcessIml implements UserInfoProcess {

    @Autowired
    private UserService userService;

    @Override
    public void process(Authentication authentication, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) {

        String username = authentication.getPrincipal().toString();

        if (!StringUtils.isEmpty(username)) {
            UserInfo userInfo = userService.getUserInfoByName(username);

            httpServletRequest.setAttribute("user", userInfo);
        }
    }
}
