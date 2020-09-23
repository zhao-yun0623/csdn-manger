package com.tju.csdnmanger.controller;

import com.tju.csdnmanger.javaBean.AdminBean;
import com.tju.csdnmanger.javaBean.NoticeBean;
import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.javaBean.UserBean;
import com.tju.csdnmanger.service.NoticeService;
import org.apache.http.protocol.RequestDate;
import org.apache.http.protocol.ResponseDate;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 公告管理控制器
 *
 * @author 赵云
 * @date 2020/09/11
 */

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 发布公告
     * @param noticeBean：公告内容
     * @param admin：当前登录的管理员
     * @return responseData
     */
    @PostMapping
    public ResponseData putNotice(@RequestBody @Valid NoticeBean noticeBean, @ModelAttribute("admin")AdminBean admin){
        return noticeService.putNotice(noticeBean,admin);
    }

    /**
     * 获取所有公告，按时间从大到小排序
     * @param admin：当前登录的管理员
     * @return 所有公告信息
     */
    @GetMapping
    public ResponseData getNotices(@ModelAttribute("admin")AdminBean admin){
        return noticeService.getNotices(admin);
    }

    /**
     * 修改公告
     * @param noticeBean：公告信息，必须有id,title、content、author至少有一个
     * @param admin：当前登录的管理员
     * @return responseData
     */
    @PutMapping
    public ResponseData updateNotice(@RequestBody NoticeBean noticeBean,@ModelAttribute("admin")AdminBean admin){
        return noticeService.updateNotice(noticeBean,admin);
    }

    @DeleteMapping
    public ResponseData deleteNotice(@RequestBody Map<String,Object> param, @ModelAttribute("admin")AdminBean admin){
        return noticeService.deleteNotice((int)param.get("id"),admin);
    }


}
