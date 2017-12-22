package com.fly.chat.service;

import com.fly.chat.domain.Menu;

import java.util.List;

/**
 * @author Fly
 * @Description
 * @Date Created in 11:20 2017/12/21
 * @Modified by
 */
public interface MenuService {

    /**
     * 查找所有菜单
     * @return
     */
    List<Menu> findAll();


    /**
     * 通过用户ID查询该用户授权的菜单
     * @param id
     * @return
     */
    List<Menu> findMenuListByUserId(Long id);

    /**
     * 根据根据父id查找所有子菜单列表
     * @param id
     * @return
     */
    List<Menu> findMenusByParentId(Long id);

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    Menu saveMenu(Menu menu);

    /**
     * 根据ID查找菜单
     * @param id
     * @return
     */
    Menu findMenuById(Long id);

    /**
     * 删除菜单
     * @param id
     */
    void deleteMenu(Long id);
}
