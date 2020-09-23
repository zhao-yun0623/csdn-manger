package com.tju.csdnmanger.mapper;


import com.tju.csdnmanger.javaBean.AdminBean;
import org.springframework.stereotype.Repository;

/**
 * AdminMapper接口
 *
 * @author 赵云
 * @date 2020/09/10
 */

@Repository
public interface AdminMapper {
    /**
     * 通过手机号查询用户
     * @param phone：手机号
     * @return user
     */
    AdminBean getUserByPhone(String phone);

    /**
     * 插入新用户
     * @param adminBean：用户信息
     */
    void insertUser(AdminBean adminBean);

    /**
     * 修改密码
     * @param adminBean：包含phone，password
     */
    void changePassword(AdminBean adminBean);
}
