package com.fly.chat.utils;

import com.fly.chat.SpringUtil;
import com.fly.chat.domain.User;
import com.fly.chat.service.UserService;
import com.fly.chat.shiro.SystemAuthorizingRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

/**
 * @author Fly
 * @Description
 * @Date Created in 8:36 2017/12/21
 * @Modified by
 */
public class UserUtils {

    private static UserService userService = SpringUtil.getBean(UserService.class);
    /**
     * 获取当前登录者对象
     */
    public static SystemAuthorizingRealm.Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }


    /**
     * 根据ID获取用户
     * @param id
     * @return 取不到返回null
     */
    public static User get(Long id){
        return userService.findUserById(id);
    }
    /**
     * 获取当前用户
     * @return 取不到返回 new User()
     */
    public static User getUser(){
        SystemAuthorizingRealm.Principal principal = getPrincipal();
        if (principal!=null){
            User user = get(principal.getId());
            if (user != null){
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }
}
