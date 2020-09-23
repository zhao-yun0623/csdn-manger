package com.tju.csdnmanger.mapper;

import com.tju.csdnmanger.javaBean.UserBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理用户
 *
 * @author 赵云
 * @date 2020/09/11
 */
@Repository
public interface UserMapper {
    List<UserBean> getAllUsers();


}
