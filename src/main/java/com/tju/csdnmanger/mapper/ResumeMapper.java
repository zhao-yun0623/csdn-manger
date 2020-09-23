package com.tju.csdnmanger.mapper;

import com.tju.csdnmanger.javaBean.ResumeBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 个人简历管理dao层
 *
 * @author 赵云
 * @date 2020/09/12
 */

@Repository
public interface ResumeMapper {
    void submitResume(ResumeBean resumeBean);

    ResumeBean getResume(int userId);

    /**
     * 获取所有简历
     * @return resumes
     * @param resume
     */
    List<ResumeBean> getResumes(ResumeBean resume);


    void check(ResumeBean resume);

    void interview(ResumeBean resume);

    void backInterview(ResumeBean resumeBean);
}
