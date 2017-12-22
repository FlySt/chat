package com.fly.chat.service;

import com.fly.chat.domain.Role;
import com.fly.chat.persistence.Query;
import com.fly.chat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fly
 * @Description
 * @Date Created in 11:30 2017/12/22
 * @Modified by
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    /**
     * 分页查找角色列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public Page<Role> findAll(Query query) {
        Pageable pageable = new PageRequest(query.getPageIndex(), query.getLimit());
        return roleRepository.findAll(pageable);
    }

    /**
     * 查找所有角色列表
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * 通过ID查找角色
     *
     * @param id
     * @return
     */
    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findOne(id);
    }

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    @Override
    public Role saveMenu(Role role) {
        return roleRepository.save(role);
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    public void removeMenu(Long id) {
        roleRepository.delete(id);
    }
}
