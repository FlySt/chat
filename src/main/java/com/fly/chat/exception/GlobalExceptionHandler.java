package com.fly.chat.exception;

import com.fly.chat.vo.Response;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 全局异常处理
 * @author Fly
 * @Description
 * @Date Created in 8:43 2017/9/12
 * @Modified by
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Response jsonErrorHandler(MyException e) throws Exception {
        Response response = new Response(false,e.getMessage());
        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public String errorHandler(Exception e, Model model) throws Exception {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public String unauthorizedHandler(Exception e, Model model) throws Exception {
        model.addAttribute("message", "该用户无访问权限");
        return "error";
    }
}
