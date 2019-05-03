package com.scwvg.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scwvg.entitys.scwvgponnetwork.Token;
import com.scwvg.entitys.scwvgponnetwork.WvgLoginUser;
import com.scwvg.entitys.scwvgponnetwork.WvgToken;
import com.scwvg.mappers.WvgTokenMapper;
import com.scwvg.service.WvgTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：Token实现类目的是服务端存储用户状态，因为HTTP是无状态的，
 * 只能通过Session来进行管控，session是页面的东西，我们不可把控
 **/
@Primary
@Service
public class WvgTokenServiceImpl implements WvgTokenService {
    private static final Logger log = LoggerFactory.getLogger("WvgTokenServiceImpl");

    /*设置用户key，对用户进行双重锁的设置*/
    private static Key KEY=null;
    private static final String LOGIN_USER_KEY="LOGIN_USER_KEY";

    /*Token过期秒数*/
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;

    /*注入token数据库对象类*/
    @Autowired
    private WvgTokenMapper tokenMapper;

    /*设置tokne私匙*/
    @Value("${token.jwtSecret}")
    private String jwtSecret;

    /**
     * 存储用户Token，目的是服务端存储用户状态，因为HTTP是无状态的，
     * 只能通过Session来进行管控，session是页面的东西，我们不可把控
     */
    @Override
    public Token saveToken(WvgLoginUser loginUser) {
        loginUser.setToken(UUID.randomUUID().toString());
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime()+expireSeconds*1000);

        WvgToken token = new WvgToken();
        token.setId(loginUser.getToken());
        token.setCreateTime(new Date());  //创建时间
        token.setUpdateTime(new Date());  //修改时间
        token.setWvg_token_expireTime(new Date(loginUser.getExpireTime()));
        token.setWvg_token_val(JSONObject.toJSONString(loginUser)); //用户json串
        System.out.println(token);
        tokenMapper.save(token);

        /**
         * 登录日志(暂时预留，使用唐老师的注解日志管理)
         */
        String  jwtToken=createJWTToken(loginUser);
        return new Token(jwtToken,loginUser.getLoginTime());
    }

    /**
     * 刷新token
     * @param loginUser
     */
    @Override
    public void refresh(WvgLoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);

        WvgToken token = tokenMapper.getByTokenId(loginUser.getToken());
        token.setUpdateTime(new Date());
        token.setWvg_token_expireTime(new Date(loginUser.getExpireTime()));
        token.setWvg_token_val(JSONObject.toJSONString(loginUser));

        tokenMapper.update(token);
        log.debug("完成TOKEN的刷新！");

    }

    /**
     * 查询用户的Token信息
     * @param jwtToken
     * @return
     */
    @Override
    public WvgLoginUser getLoginUser(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            WvgToken model = tokenMapper.getByTokenId(uuid);
            return toLoginUser(model);
        }
        return null;
    }


    @Override
    public boolean deleteToken(String token) {
        String uuid = getUUIDFromJWT(token);
        if (uuid != null) {
            WvgToken model = tokenMapper.getByTokenId(uuid);
            WvgLoginUser loginUser = toLoginUser(model);
            if (loginUser != null) {
                tokenMapper.delete(uuid);
                //使用唐老师的注解接口进行入库logService.save(loginUser.getId(), "退出", true, null);

                return true;
            }
        }

        return false;
    }



    /**
     * 生成jwt
     *
     * @param loginUser
     * @return
     */
    private String createJWTToken(WvgLoginUser loginUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户

        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return jwtToken;
    }

    private WvgLoginUser toLoginUser(WvgToken token) {
        if (token == null) {
            return null;
        }

        // 校验是否已过期
        if (token.getWvg_token_expireTime().getTime() > System.currentTimeMillis()) {
            return JSONObject.parseObject(token.getWvg_token_val(), WvgLoginUser.class);
        }

        return null;
    }

    private Key getKeyInstance() {
        if (KEY == null) {
            synchronized (WvgTokenServiceImpl.class) {
                if (KEY == null) {// 双重锁
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }
        return KEY;
    }

    /*查询用户Token是否已过期*/
    private String getUUIDFromJWT(String jwt) {
        if(jwt.equals("") || "null".equals(jwt)){
            return null;
        }
        Map<String,Object> jwtClaims=null;
        try {
            jwtClaims=Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return null;//此处获取需要关注，可能使用redis;
        }catch (ExpiredJwtException e){
            log.error("{}已过期", jwt);
        }
        return  null;
    }
}
