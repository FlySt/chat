package com.fly.chat.controller.system;

import com.fly.chat.domain.User;
import com.fly.chat.exception.MyException;
import com.fly.chat.persistence.Query;
import com.fly.chat.service.RoleService;
import com.fly.chat.service.UserService;
import com.fly.chat.utils.ConstraintViolationExceptionHandler;
import com.fly.chat.vo.Response;
import org.apache.log4j.Logger;
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
 * @Date Created in 14:11 2017/12/21
 * @Modified by
 */
@RequestMapping("users")
@RestController
public class UserController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequiresPermissions("sys-user-view")
    @GetMapping()
    public ModelAndView index(){
        return new ModelAndView("system/user");
    }

    /**
     * 用户列表
     * @return
     */
    @GetMapping("list")
    public Map<String,Object> list(Query query){
        Map<String,Object> map = new HashMap<>(16);
        Page<User> page = userService.findAll(query);
        map.put("data", page.getContent());
        map.put("total", page.getTotalElements());
        return map;
    }

    /**
     * 获取添加界面
     *
     * @return
     */
    @RequiresPermissions("sys-user-edit")
    @GetMapping("/add")
    public ModelAndView addForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return new ModelAndView("system/userForm", "userModel", model);
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @RequiresPermissions("sys-user-edit")
    @PostMapping
    public ResponseEntity<Response> save(User user) throws MyException {
        try {
            userService.saveUser(user);
        } catch (ConstraintViolationException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            throw  new MyException("保存失败");
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }

    /**
     * 获取编辑界面及数据
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("sys-user-edit")
    @GetMapping("edit/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return new ModelAndView("system/userForm", "userModel", model);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequiresPermissions("sys-user-edit")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "删除成功"));
    }
}
