package com.tju.csdnmanger.javaBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 个人简历Bean
 *
 * @author 赵云
 * @date 2020/09/12
 */
@Data
public class ResumeBean {
    private int id;
    private int userId;
    private String name;
    private String phone;
    private Short sex;
    private String stuNum;
    private String faculty;
    private String major;
    private String clazz;
    private String qq;
    @NotNull(message = "简历内容不能为空")
    private String additionData;
    private Integer state;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp registrationTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp checkTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp interviewTime;

}
