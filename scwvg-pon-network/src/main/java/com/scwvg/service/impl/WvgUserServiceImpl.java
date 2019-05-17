package com.scwvg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.entitys.Msg;
import com.scwvg.entitys.scwvgponnetwork.*;
import com.scwvg.mappers.WvgTokenMapper;
import com.scwvg.mappers.WvgUserMapper;
import com.scwvg.service.WvgSpecTypeService;
import com.scwvg.service.WvgUserService;
import com.scwvg.utils.IpUtils;
import com.scwvg.utils.RequestHolder;
import com.scwvg.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：用户信息操作实现类
 **/
@Service
public class WvgUserServiceImpl implements WvgUserService {
    private static final Logger log = LoggerFactory.getLogger("WvgUserServiceImpl");
    private static final String scwvg="www.scwvg.com";
    @Autowired
    private WvgSpecTypeService specTypeService;
    @Autowired
    private WvgUserMapper userMapper; //用户操作mapper注入进来
    @Autowired
    private WvgTokenMapper tokenMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //密码动态加密

    Msg msg =new Msg();


    /**
     * 查询用户表里最大的ID进行+1
     * @return
     */
    public Long querMaxUserId(){
        int wvg_menu_id=userMapper.queryMaxUserID();
        return wvg_menu_id+1L;
    }
    /**
     * 查找所有用户信息
     *
     * @param params
     * @param page
     * @return
     */
    @Override
    public Page<WvgUser> queryAllUsers(Map<String, Object> params, Page<WvgUser> page) {
        String city_name;
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Page<WvgUser> wvgUsers = userMapper.queryAllUserByPage(params);

        for (int i = 0; i < wvgUsers.size(); i++) {
            //用户专业转换
            int zhSpecId = wvgUsers.get(i).getWvg_spec_id();
            String spec_name = specTypeService.queryAllSpec(zhSpecId);
            wvgUsers.get(i).setChangeStr(spec_name);
            //用户属地转换
            int city_id=wvgUsers.get(i).getWvg_city_id();
            city_name=userMapper.queryCityByCityId(city_id);
            wvgUsers.get(i).setChangeStr3(city_name);
            //用户证件类型转换
            int idType=wvgUsers.get(i).getWvg_id_type();
            String idName=userMapper.queryIdName(idType);
            wvgUsers.get(i).setChangeStr4(idName);
            //用户所属角色转换
            Long user_id = wvgUsers.get(i).getWvg_user_id();
            String wvg_role_name=userMapper.queryRoleName(user_id);
            wvgUsers.get(i).setChangeStr5(wvg_role_name);

            if (wvgUsers.get(i).getWvg_online_state() == 1) {
                wvgUsers.get(i).setChangeStr1("离线");
            } else {
                wvgUsers.get(i).setChangeStr1("在线");
            }
            if (wvgUsers.get(i).getWvg_account_enabled() == 1) {
                wvgUsers.get(i).setChangeStr2("已激活");
            } else {
                wvgUsers.get(i).setChangeStr2("未激活");
            }
        }
        return wvgUsers;
    }

    /**
     * 新增用户
     * @return
     */
    @Override
    @Transactional
    public Msg saveUser(WvgUser user) {
        int users = userMapper.querUserNameAndIhoneAndIdnumber(user.getWvg_login_name(),user.getWvg_user_iphone(),user.getWvg_id_number());
        if(users !=0){
            msg.setMessage("新增用户失败！已存在该用户，请访问"+scwvg+"联系系统厂商处理！");
            msg.setCode("1");
            return msg;
        }
        user.setWvg_user_id(querMaxUserId());
        user.setWvg_user_password(passwordEncoder.encode(user.getWvg_user_password()));
        user.setWvg_account_data(12); //账号有效期默认为12个月
        user.setWvg_password_data(3); //密码有效期默认为3个月
        int res= userMapper.saveUserInfo(user);   //用户新增
        if(res==1){
            saveRoleUserIds(user); //角色新增*/
        }
        else{
            msg.setMessage("新增用户失败！请访问"+scwvg+"联系系统厂商！");
            msg.setCode("1");
            return msg;
        }
        msg.setCode("0");
        return msg;
    }

   //新增用户与角色中间表
    private Msg saveRoleUserIds(WvgUser user) {
        if (user.getId() != null && user.getWvg_user_id() !=null) {
               int res= userMapper.saveUserRoles(user.getId(),user.getWvg_user_id());
               if(res!=1){
                   msg.setCode("1");
                   msg.setMessage("失败！请访问"+scwvg+"联系系统厂商！");
               }
               else {
                   msg.setCode("0");
                   msg.setMessage("成功！");
               }
        }else {
            msg.setCode("1");
            msg.setMessage("用户ID或者角色ID为空！请访问"+scwvg+"联系系统厂商！");
        }
        return msg;
    }

    /*修改用户*/
    @Override
    public Msg updateUser(WvgUser user) {
        int res =userMapper.updateUserInfo(user);
        if(res ==1){
           res =userMapper.editRoleUserIds(user);
           msg.setCode(res==1?"0":"1");
           msg.setMessage(res==1?"修改成功":"修改失败！");
        }
        else {
            msg.setCode("1");
            msg.setMessage("用户修改失败！请访问"+scwvg+"联系系统厂商！");

        }
        return msg;
    }

    /*根据用户名查询用户*/
    @Override
    public WvgUser querUserInfo(String username) {
        return userMapper.querUserInfo(username);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        WvgLoginUser user = (WvgLoginUser) userMapper.querUserInfo(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在!请咨询系统供应商！");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("旧密码错误!请咨询系统供应商！");
        }
        userMapper.changePassword(user.getWvg_user_id(), passwordEncoder.encode(newPassword));
        log.debug("修改" + user.getUsername() + "的密码！");
    }

    @Override
    public void updateLgIpAndlgDateAndlgonlineState(WvgLoginUser user) {
       int res = userMapper.updateLgIpAndlgDateAndlgonlineState(user);
       log.debug(res==1? "清空用户登录相关信息成功！":"清空用户登录相关信息失败！");
    }

    @Override
    public void upDateUsLoginTimeAndIp(WvgLoginUser loginUser) {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        loginUser.setWvg_login_ip(IpUtils.getRequestIP(request));
        loginUser.setWvg_online_state(0);
        int res =userMapper.upDateUsLoginTimeAndIp(loginUser);
    }
    //剔除用户下线
    @Override
    public Msg userOffline(Long wvg_user_id) {
        Msg msg=new Msg();
        int res;
        res= userMapper.userOffline(wvg_user_id);
       if(res==1){
           int wvg_online_state=1;
           res= userMapper.updateUserOffline(wvg_user_id,wvg_online_state);
           if(res==1){
               msg.setMessage("用户已踢下线！");
           }
           return msg;
       }
       else {
           msg.setMessage("剔下线失败！");
           return msg;
       }
    }

    @Override
    public Msg userReset(Long wvg_user_id) {
        int res;
        if(wvg_user_id.equals("") || "".equals(wvg_user_id) || "null".equals(wvg_user_id)){
         msg.setCode("1");
         msg.setMessage("未获取到该用户！");
        }
        else {
            String pwd=passwordEncoder.encode("ponsystem@123456");
            res= userMapper.changePassword(wvg_user_id,pwd);
            if(res==1){
                res =tokenMapper.queryCountByUserid(wvg_user_id);
                if(res !=0 ){
                    res=tokenMapper.deleteUserId(wvg_user_id);
                    msg.setMessage(res==1?"用户密码重置成功!":"用户密码重置失败,请访问"+scwvg+"联系系统厂商！");
                }
                else {
                    msg.setMessage(res==1?"用户密码重置成功!":"用户密码重置成功!");
                }
            }
        }
        return msg;
    }

    @Override
    public Msg deleteUser(Long wvg_user_id) {
        int res;
        if(wvg_user_id.equals("") || "".equals(wvg_user_id) || "null".equals(wvg_user_id)){
           msg.setMessage("未获取到该用户信息！请访问"+scwvg+"联系系统厂商处理！");
        }
        else {
            res=userMapper.deleteRoleUserId(wvg_user_id);
            if(res==1){
                res =userMapper.deleteUserByID(wvg_user_id);
              msg.setMessage(res==1?"删除成功":"删除失败！请访问"+scwvg+"联系系统厂商处理！");
            }
            else{
                msg.setMessage("用户删除失败！请访问"+scwvg+"联系系统厂商处理！");
            }
        }
        return msg;
    }
}
