package com.scwvg.utils;

import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：用户工具类（获取当前用户信息）
 **/
public class UserUtil {
    public static WvgLoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
               WvgLoginUser user= (WvgLoginUser) authentication.getPrincipal();
                return user;
            }
        }

        return null;
    }
}
