package com.scwvg.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：授权管理器
 **/
@Service
public class WvgAccessDecisionManager implements AccessDecisionManager {
    /*判定是否拥有权限的决策方法*/
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
      if(collection==null || collection.size()<=0){
      return;
      }
      ConfigAttribute c;
      String needRoles;
      for(Iterator<ConfigAttribute> iter =collection.iterator();iter.hasNext();){
        c=iter.next();
        needRoles=c.getAttribute();
        /*循环添加到 GrantedAuthority 对象中的权限信息集合*/
        for(GrantedAuthority ga:authentication.getAuthorities()){
            if(needRoles.trim().equals(ga.getAuthority())) {
                return;
            }
        }
      }
      throw new AccessDeniedException("权限信息空！");
    }

    /*表示当前AccessDecisionManager是否支持对应的ConfigAttribute*/
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    /*表示当前AccessDecisionManager是否支持对应的受保护对象类型*/
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
