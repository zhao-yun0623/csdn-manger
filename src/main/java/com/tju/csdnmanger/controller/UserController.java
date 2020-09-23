package com.tju.csdnmanger.controller;

import com.tju.csdnmanger.javaBean.AdminBean;
import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理用户控制器
 *
 * @author 赵云
 * @date 2020/09/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 获得所有已注册的用户信息
     * @return responseData
     */
    @GetMapping
    public ResponseData getAllUsers(@ModelAttribute("admin")AdminBean admin){
        return userService.getAllUsers(admin);
    }
}
