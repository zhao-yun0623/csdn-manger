package com.tju.csdnmanger.service;

import com.tju.csdnmanger.javaBean.AdminBean;
import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.javaBean.state.ResponseState;
import com.tju.csdnmanger.javaBean.UserBean;
import com.tju.csdnmanger.mapper.UserMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理用户
 *
 * @author 赵云
 * @date 2020/09/11
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Logger logger;
    /**
     * 获取所有已注册的用户
     * @return responseData
     */
    public ResponseData getAllUsers(AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在查询所有已注册的用户信息");
        try {
            List<UserBean> users=userMapper.getAllUsers();
            logger.info("手机号为"+admin.getPhone()+"的管理员查询所有已注册的用户信息查询成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue(),"users",users);
        } catch (Exception e) {
            logger.error("查询失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }
}
