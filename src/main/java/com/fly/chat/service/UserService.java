package com.fly.chat.service;

import com.fly.chat.domain.Menu;
import com.fly.chat.domain.User;
import com.fly.chat.persistence.Query;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Fly
 * @Description
 * @Date Created in 16:32 2017/12/19
 * @Modified by
 */
public interface UserService {



    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    User findUserByUserName(String userName);

    /**
     * 获取菜单列表
     * @param user
     * @return
     */
    List<Menu> getMenuList(User user);

    /**
     * 分页查找用户列表
     * @param query 查询条件
     * @return
     */
    Page<User> findAll(Query query);

    /**
     * 保存用户
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void removeUser(Long id);

    /**
     * 根据ID查找用户
     * @param id
     * @return
     */
    User findUserById(Long id);
}
