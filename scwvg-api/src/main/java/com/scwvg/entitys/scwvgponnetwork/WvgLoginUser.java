package com.scwvg.entitys.scwvgponnetwork;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/1
 * @Explain：Token记录用户状态
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WvgLoginUser extends WvgUser implements UserDetails {
    private static final long serialVersionUID = -1379274258881257107L;

    private String token;
    /** 登陆时间戳（毫秒） */
    private Long loginTime;
    /** 过期时间戳 */
    private Long expireTime;


    /*菜单集合*/
    public List<WvgMenu> menuList;

    /**
     * 将全新标识Set进map里面，使用spring-security的getAuthorities方法来管理
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return menuList.parallelStream().filter(p -> !StringUtils.isEmpty(p.getWvg_authority()))
                .map(p -> new SimpleGrantedAuthority(p.getWvg_authority())).collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getWvg_user_password();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getWvg_login_name();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
