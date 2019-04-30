package com.scwvg.system.controller;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Description: TODO
 * @author: chen.baihoo
 * @date: 2019/4/14 19:01
 * version 0.1
 */
public class LoginControllerTest {

    @Test
    public void userLogin() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode("Hljdx@2019");
        System.out.println(encodePasswd);
        System.out.println(encodePasswd.length());
    }
}