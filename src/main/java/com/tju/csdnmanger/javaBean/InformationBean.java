package com.tju.csdnmanger.javaBean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * InformationBean类
 *
 * @author 赵云
 * @date 2020/09/10
 */
@Data
public class InformationBean {
    private int userId;
    @NotNull(message = "姓名不能为null")
    private String name;
    private String phone;
    @NotNull(message = "性别不能为null")
    private Short sex;
    @NotNull(message = "学号不能为null")
    private String stuNum;
    @NotNull(message = "学院不能为null")
    private String faculty;
    @NotNull(message = "年级不能为null")
    private int grade;
    @NotNull(message = "专业不能为null")
    private String major;
    @NotNull(message = "班级不能为null")
    private String clazz;
    @NotNull(message = "QQ号不能为空")
    private String qq;
}
