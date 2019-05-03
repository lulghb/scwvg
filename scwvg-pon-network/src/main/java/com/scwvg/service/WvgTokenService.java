package com.scwvg.service;

import com.scwvg.entitys.scwvgponnetwork.Token;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：缓存类
 **/
public interface WvgTokenService {
    /*保存Token*/
   public Token saveToken(WvgLoginUser loginUser);
    /*刷新Token*/
   public void refresh(WvgLoginUser loginUser);
    /*查询Token*/
   public WvgLoginUser getLoginUser(String token);
    /*删除Token*/
   public boolean deleteToken(String token);
}
