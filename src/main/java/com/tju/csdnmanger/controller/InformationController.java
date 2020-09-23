package com.tju.csdnmanger.controller;


import com.tju.csdnmanger.javaBean.InformationBean;
import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.javaBean.AdminBean;
import com.tju.csdnmanger.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * InformationController类：管理成员的信息
 *
 * @author 赵云
 * @date 2020/09/10
 */
@RestController
@RequestMapping("/information")
public class InformationController {
    @Autowired
    private InformationService informationService;

    /**
     * 查询用户信息,若有参数则按条件查询
     * @param admin：当前登录的管理员
     * @return responseData：用户的信息
     */
    @RequestMapping
    public ResponseData getUsersInformation(InformationBean information,@ModelAttribute("admin")AdminBean admin){
        return informationService.getUsersInformation(information,admin);
    }


    @PutMapping
    public ResponseData updateInformation(@RequestBody InformationBean information,@ModelAttribute("admin")AdminBean admin){
        return informationService.updateInformation(information,admin);
    }

}
