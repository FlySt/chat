package com.fly.chat.service;

import com.fly.chat.domain.Menu;
import com.fly.chat.domain.User;
import com.fly.chat.persistence.Query;
import com.fly.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fly
 * @Description
 * @Date Created in 16:34 2017/12/19
 * @Modified by
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuService menuService;
    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> getMenuList(User user) {
        List<Menu> menuList;
        if (user.isAdmin()){
            menuList = menuService.findAll();
        }else{
            menuList = menuService.findMenuListByUserId(user.getId());
        }
        return menuList;
    }

    /**
     * @return
     */
    @Override
    public Page<User> findAll(Query query) {
        Pageable pageable = new PageRequest(query.getPageIndex(), query.getLimit());
        return userRepository.findAll(pageable);
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @Override
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    /**
     * 根据ID查找用户
     *
     * @param id
     * @return
     */
    @Override
    public User findUserById(Long id) {
        return userRepository.findOne(id);
    }
}
