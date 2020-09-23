package com.tju.csdnmanger.javaBean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 用户Bean
 *
 * @author 赵云
 * @date 2020/09/11
 */
@Data
public class UserBean {
    private int id;
    @Length(max = 11,min = 11,message = "手机号必须是11位")
    @NotNull(message = "手机号必须不为空")
    private String phone;
    @JsonIgnore
    @NotNull(message = "密码必须不为空")
    @Length(max = 16,min = 8,message = "密码的长度必须在8-16之间")
    private String password;

    public UserBean() {
    }

    public UserBean(int id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public UserBean(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
