package com.scwvg.system.utils;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/19 23:20
 * @desc: 
**/
public class SecurityContextHolder {
	
	public static UserDetails getUserDetails() {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

}
