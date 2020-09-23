package com.tju.csdnmanger.service;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.tju.csdnmanger.javaBean.*;
import com.tju.csdnmanger.javaBean.state.ResponseState;
import com.tju.csdnmanger.javaBean.state.TemplateId;
import com.tju.csdnmanger.javaBean.state.UserState;
import com.tju.csdnmanger.mapper.ResumeMapper;
import com.tju.csdnmanger.mapper.UserMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 个人简历管理Service层
 *
 * @author 赵云
 * @date 2020/09/12
 */

@Service
public class ResumeService {
    @Autowired
    private ResumeMapper resumeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Logger logger;
    @Autowired
    private MessageService messageService;

    /**
     * 获取简历
     *
     * @param resume ：简历内容
     * @param admin ：当前登录的管理员
     * @return resumes
     */
    public ResponseData getResumes(ResumeBean resume, AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"正在查询所有简历");
        try {
            List<ResumeBean> resumes=resumeMapper.getResumes(resume);
            logger.info("查询成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue(),"resumes",resumes);
        } catch (Exception e) {
            logger.error("查询失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }



    /**
     * 审核用户的简历
     * @param state：简历的状态，1为审核成功，-1为审核失败
     * @param userId：要修改的用户的ID
     * @param admin：当前登录的管理员
     * @return responseData
     */
    public ResponseData checkResume(int state,int userId, AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"正在审核Id为"+userId+"的用户的简历");
        try {
            ResumeBean resume=resumeMapper.getResume(userId);
            if(resume==null){
                logger.warn("该用户未提交简历");
                return new ResponseData(ResponseState.RESUME_NOT_EXIST.getMessage(),ResponseState.RESUME_NOT_EXIST.getValue());
            }
            if(resume.getState()!=0){
                logger.warn("该简历已经被审核");
                return new ResponseData(ResponseState.RESUME_IS_CHECKED.getMessage(),ResponseState.RESUME_IS_CHECKED.getValue());
            }
            if(state!=1&&state!=-1){
                logger.error("state参数错误,只能是1或-1");
                return new ResponseData(ResponseState.PARAM_IS_ERROR.getMessage(),ResponseState.PARAM_IS_ERROR.getValue());
            }
            resume.setState(state);
            resume.setCheckTime(new Timestamp(new Date().getTime()));
            resumeMapper.check(resume);
            logger.info("审核成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("审核失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    /**
     * 面试用户
     * @param state：面试的状态，2为面试成功，-2为面试失败
     * @param userId：面试的用户ID
     * @param admin：当前登录的管理员
     * @return responseData
     */
    @Transactional
    public ResponseData interviewResume(int state, int userId, AdminBean admin) throws Exception {
        logger.info("手机号为"+admin.getPhone()+"正在面试Id为"+userId+"的用户");
        ResumeBean resume=resumeMapper.getResume(userId);
        if(resume==null){
            logger.warn("该用户未提交简历");
            return new ResponseData(ResponseState.RESUME_NOT_EXIST.getMessage(),ResponseState.RESUME_NOT_EXIST.getValue());
        }
        if(resume.getState()==0){
            logger.warn("该简历尚未进行审核");
            return new ResponseData(ResponseState.RESUME_NOT_CHECKED.getMessage(),ResponseState.RESUME_NOT_CHECKED.getValue());
        }
        if(resume.getState()==2||resume.getState()==-2){
            logger.warn("该简历已完成面试");
            return new ResponseData(ResponseState.RESUME_IS_INTERVIEW.getMessage(),ResponseState.RESUME_IS_INTERVIEW.getValue());
        }
        if(resume.getState()==-1){
            logger.warn("该用户已被淘汰");
            return new ResponseData(ResponseState.RESUME_NOT_PASS_CHECK.getMessage(),ResponseState.RESUME_NOT_PASS_CHECK.getValue());
        }
        if(state!=2&&state!=-2){
            logger.error("state参数错误,只能是2或-2");
            return new ResponseData(ResponseState.PARAM_IS_ERROR.getMessage(),ResponseState.PARAM_IS_ERROR.getValue());
        }
        resume.setState(state);
        resume.setInterviewTime(new Timestamp(new Date().getTime()));
        resumeMapper.interview(resume);
        SmsSingleSenderResult result=messageService.senMessage(resume.getPhone(),new String[]{resume.getName()}, TemplateId.PASS.getValue());
        if(!result.errMsg.equals("OK")){
            resumeMapper.backInterview(resume);
            throw new Exception();
        }
        logger.info("面试成功");
        return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());

    }


}
