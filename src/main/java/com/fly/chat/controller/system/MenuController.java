package com.fly.chat.controller.system;

import com.fly.chat.domain.Menu;
import com.fly.chat.service.MenuService;
import com.fly.chat.utils.ConstraintViolationExceptionHandler;
import com.fly.chat.vo.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fly
 * @Description
 * @Date Created in 8:40 2017/12/22
 * @Modified by
 */
@RequestMapping("menus")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 进入菜单管理页面
     * @param model
     * @return
     */
    @RequiresPermissions("sys-menu-view")
    @GetMapping
    public ModelAndView index(Model model){
        model.addAttribute("menuRootId",0);
        return new ModelAndView("system/menu","menuModel",model);
    }

    /**
     * 获取菜单列表
     * @return
     */
    @GetMapping("list")
    public List<Menu> list(){
        // 当前所在页面数据列表
        return menuService.findAll();
    }

    /**
     * 进入添加菜单界面
     * @return
     */
    @RequiresPermissions("sys-menu-edit")
    @GetMapping("/add")
    public ModelAndView addForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("parentName", null);
        return new ModelAndView("system/menuForm", "menuModel", model);
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @RequiresPermissions("sys-menu-edit")
    @PostMapping
    public ResponseEntity<Response> save(Menu menu){
        try {
            menuService.saveMenu(menu);
        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", menu));
    }

    /**
     * 获取编辑界面及数据
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("sys-menu-edit")
    @GetMapping("edit/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id, Model model) {
        Menu menu = menuService.findMenuById(id);
        model.addAttribute("menu", menu);
        Menu parent = menuService.findMenuById(menu.getParentId());
        if(parent != null){
            model.addAttribute("parentName", parent.getName());
        }else {
            model.addAttribute("parentName", null);
        }
        return new ModelAndView("system/menuForm", "menuModel", model);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequiresPermissions("sys-menu-edit")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id){
        try {
            menuService.deleteMenu(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }


    /**
     * 获取菜单树
     * @return
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Menu> list = menuService.findAll();
        for (int i=0; i<list.size(); i++){
            Menu e = list.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("pId", e.getParentId());
            map.put("name", e.getName());
            mapList.add(map);
        }
        return mapList;
    }
}
