package com.tju.csdnmanger.controller;

import com.tju.csdnmanger.javaBean.*;
import com.tju.csdnmanger.javaBean.state.ResponseState;
import com.tju.csdnmanger.service.ResumeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 个人简历管理控制器
 *
 * @author 赵云
 * @date 2020/09/12
 */

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private Logger logger;

    /**
     * 获取简历
     * @param admin：当前登录的管理员
     * @return resumes
     */
    @GetMapping
    public ResponseData getResumes(ResumeBean resume,@ModelAttribute("admin")AdminBean admin){
        return resumeService.getResumes(resume,admin);
    }


    @PostMapping("/check")
    public ResponseData checkResume(@RequestBody Map<String,Object> param, @ModelAttribute("admin")AdminBean admin){
        return resumeService.checkResume((int)param.get("state"),(int)param.get("userId"),admin);
    }

    @PostMapping("/interview")
    public ResponseData interviewResume(@RequestBody Map<String,Object> param,@ModelAttribute("admin")AdminBean admin){
        try {
            return resumeService.interviewResume((int)param.get("state"),(int)param.get("userId"),admin);
        } catch (Exception e) {
            logger.error("面试失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

}
