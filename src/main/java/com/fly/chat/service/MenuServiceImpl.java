package com.fly.chat.service;

import com.fly.chat.domain.Menu;
import com.fly.chat.repository.MenuRepository;
import com.fly.chat.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Fly
 * @Description
 * @Date Created in 11:21 2017/12/21
 * @Modified by
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserService userService;
    /**
     * 查找所有菜单
     *
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    /**
     * 通过用户ID查询该用户授权的菜单
     *
     * @param id
     * @return
     */
    @Override
    public List<Menu> findMenuListByUserId(Long id) {
        return menuRepository.findMenusByUserId(id);
    }

    /**
     * 根据根据父id查找所有子菜单列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Menu> findMenusByParentId(Long id) {
        List<Menu> menuList = new ArrayList<>();
        List<Menu> menus = userService.getMenuList(UserUtils.getUser());
        for (Menu menu : menus){
            if(Objects.equals(menu.getParentId(), id)){
                menuList.add(menu);
            }
        }
        return menuList;
    }

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    @Override
    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    /**
     * 根据ID查找菜单
     *
     * @param id
     * @return
     */
    @Override
    public Menu findMenuById(Long id) {
        return menuRepository.findMenusById(id);
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @Override
    public void deleteMenu(Long id) {
        menuRepository.delete(id);
    }
}
