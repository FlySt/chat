package com.fly.chat.service;

import com.fly.chat.domain.Role;
import com.fly.chat.persistence.Query;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Fly
 * @Description
 * @Date Created in 11:30 2017/12/22
 * @Modified by
 */
public interface RoleService {

    /**
     * 分页查找角色列表
     * @param query 查询条件
     * @return
     */
    Page<Role> findAll(Query query);
    /**
     * 查找所有角色列表
     * @return
     */
    List<Role> findAll();

    /**
     * 通过ID查找角色
     * @param id
     * @return
     */
    Role findRoleById(Long id);
    /**
     * 保存角色
     * @param role
     * @return
     */
    Role saveMenu(Role role);

    /**
     * 删除角色
     * @param id
     */
    void removeMenu(Long id);
}
