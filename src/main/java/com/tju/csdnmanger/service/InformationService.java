package com.tju.csdnmanger.service;

import com.tju.csdnmanger.javaBean.*;
import com.tju.csdnmanger.javaBean.state.ResponseState;
import com.tju.csdnmanger.mapper.InformationMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * InformationService类：管理成员信息
 *
 * @author 赵云
 * @date 1010/09/10
 */
@Service
public class InformationService {
    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private Logger logger;


    /**
     * 查询用户信息,若有参数则按条件查询
     * @param admin：当前登录的管理员
     * @return responseData：用户的信息
     */
    public ResponseData getUsersInformation(InformationBean information,AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在查询所有已注册的用户的个人信息");
        try {
            List<InformationBean> users=informationMapper.getUsersInformation(information);
            logger.info("手机号为"+admin.getPhone()+"查询信息成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue(),"information",users);
        } catch (Exception e) {
            logger.error("查询失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }


    public ResponseData updateInformation(InformationBean information, AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在修改用户ID为"+information.getUserId()+"的信息");
        try {
            informationMapper.updateInformation(information);
            logger.info("修改成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("修改失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }
}
