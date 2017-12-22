package com.fly.chat.controller;

import com.fly.chat.domain.Menu;
import com.fly.chat.domain.User;
import com.fly.chat.service.MenuService;
import com.fly.chat.shiro.FormAuthenticationFilter;
import com.fly.chat.shiro.SystemAuthorizingRealm;
import com.fly.chat.utils.StringUtils;
import com.fly.chat.utils.UserUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Fly
 * @Description
 * @Date Created in 16:06 2017/12/19
 * @Modified by
 */
@Controller
public class LoginController {

    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private MenuService menuService;

    @Value("${rootId}")
    private Long rootId;
    /**
     * 进入登录界面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 登录成功，进入管理界面 需要用户权限
     * @return
     */
    @RequiresPermissions("user")
    @GetMapping("/")
    public ModelAndView index(Model model){
        //根菜单
        List<Menu> menus = menuService.findMenusByParentId(rootId);
        model.addAttribute("list",menus);
        return new ModelAndView("index","menuModel",model);
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     * @param request
     * @return
     */
    @PostMapping("login")
    public String login(HttpServletRequest request , User user , Model model){
        logger.info("username:" + user.getUsername());
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();

        // 如果已经登录，则跳转到管理首页
        if(principal != null){
            return "redirect:" + "/";
        }

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, null)){
            message = "用户或密码错误, 请重试.";
        }
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
        model.addAttribute("loginError", true);
        return "login";
    }
}
