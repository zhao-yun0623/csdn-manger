package com.tju.csdnmanger.controller;

import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.javaBean.AdminBean;
import com.tju.csdnmanger.javaBean.UserBean;
import com.tju.csdnmanger.service.AdminService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * AdminController类
 * @author 赵云
 * @date 2020/09/10
 */

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private Logger logger;

    /**
     * 用户登录
     * @param param:包含phone,password
     * @return user信息、token
     */
    @PostMapping("/login")
    public ResponseData login(@RequestBody Map<Object,String> param){
        return adminService.login(param.get("phone"),param.get("password"));
    }

    /**
     * 用户注册
     * @param param：包含phone,password,code
     * @return responseData
     *//*
    @PostMapping("/register")
    public ResponseData register(@RequestBody Map<String,String> param){
        return adminService.register(new AdminBean(param.get("phone"),param.get("password")),param.get("code"));
    }

    *//**
     * 获取验证码
     * @param phone：手机号
     * @return responseData
     *//*
    @GetMapping("/register/getCode")
    public ResponseData getCode(String phone){
        return adminService.getCode(phone);
    }
*/
    /**
     * 退出登录
     * @param admin：当前登录的用户信息
     * @return responseData
     */
    @DeleteMapping
    public ResponseData logout(@ModelAttribute("admin") AdminBean admin){
        return adminService.logout(admin);
    }

    /**
     * 获取用户信息
     * @param phone：手机号，可为空，为空查询自动的信息
     * @param admin：当前登录的用户信息
     * @return responseData(user)
     */
    @GetMapping
    public ResponseData getUser(String phone,@ModelAttribute("admin") AdminBean admin){
        return adminService.getUser(phone,admin);
    }

    /**
     * 更新token
     * @param refreshToken：refreshToken
     * @return responseData:token,refreshToken
     */
    @GetMapping("/refresh")
    public ResponseData refreshToken(String refreshToken){
        return adminService.refreshToken(refreshToken);
    }


    /**
     * 找回密码
     * @param param：包含手机号，新密码,验证码
     * @return responseData
     */
  /*  @PostMapping("/forget")
    public ResponseData forgetPassword(@RequestBody Map<String,String> param){
        return adminService.forgetPassword(new AdminBean(param.get("phone"),param.get("password")),param.get("code"));
    }*/

    /**
     * 找回密码时获取验证码
     * @param phone：手机号
     * @return responseData
     */
   /* @GetMapping("/forget/getCode")
    public ResponseData getForgetCode(String phone){
        return adminService.getForgetCode(phone);
    }*/

    /**
     * 修改密码
     * @param param：
     * @return responseData
     */
    @PutMapping
    public ResponseData changePassword(@RequestBody Map<String,String> param,@ModelAttribute("admin") AdminBean admin){
        return adminService.changePassword(param.get("oldPassword"),param.get("newPassword"),admin);
    }
}
