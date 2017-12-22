package com.fly.chat.controller.system;

import com.fly.chat.domain.Role;
import com.fly.chat.persistence.Query;
import com.fly.chat.service.RoleService;
import com.fly.chat.utils.ConstraintViolationExceptionHandler;
import com.fly.chat.vo.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fly
 * @Description
 * @Date Created in 14:49 2017/12/21
 * @Modified by
 */
@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 用户列表
     * @return
     */
    @ResponseBody
    @GetMapping("list")
    public Map<String,Object> list(Query query){
        Map<String,Object> map = new HashMap<>(16);
        Page<Role> page = roleService.findAll(query);
        map.put("data", page.getContent());
        map.put("total", page.getTotalElements());
        return map;
    }
    /**
     * 角色界面
     * @return
     */
    @RequiresPermissions("sys-role-view")
    @GetMapping()
    public ModelAndView index() {
        return new ModelAndView("system/role");
    }

    /**
     * 角色添加界面
     * @param model
     * @return
     */
    @RequiresPermissions("sys-role-edit")
    @GetMapping("/add")
    public ModelAndView addForm(Model model) {
        model.addAttribute("role", new Role());
        return new ModelAndView("system/roleAdd", "roleModel", model);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequiresPermissions("sys-role-edit")
    @ResponseBody
    @PostMapping()
    public ResponseEntity<Response> add(Role role) {
        try {
            roleService.saveMenu(role);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "添加成功"));
    }


    /**
     * 获取编辑界面
     *
     * @return
     */
    @RequiresPermissions("sys-role-edit")
    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id, Model model) {
        Role role = roleService.findRoleById(id);
        model.addAttribute("role", role);
        return new ModelAndView("system/roleAdd", "roleModel", model);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequiresPermissions("sys-role-edit")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            roleService.removeMenu(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }
}
