package com.fly.chat.repository;

import com.fly.chat.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Fly
 * @Description
 * @Date Created in 11:21 2017/12/21
 * @Modified by
 */
public interface MenuRepository extends JpaRepository<Menu,Long>{

    /**
     * 根据用户ID查找用户授权菜单
     * @param id
     * @return
     */
    @Query(value = "SELECT * FROM sys_menu a" +
          /*  "LEFT JOIN sys_menu p ON p.id = a.parent_id" +*/
            " JOIN sys_role_menu rm ON rm.menu_id = a.id" +
            " JOIN sys_role r ON r.id = rm.role_id " +
            " JOIN sys_user_role ur ON ur.role_id = r.id" +
            " JOIN sys_user u ON u.id = ur.user_id AND u.id = ?1",nativeQuery = true)
    List<Menu> findMenusByUserId(Long id);

    /**
     * 根据根据父id查找所有子菜单列表
     * @param id
     * @return
     */
    List<Menu> findMenusByParentId(Long id);

    /**
     * 根据ID查找菜单
     * @param id
     * @return
     */
    Menu findMenusById(Long id);
}
