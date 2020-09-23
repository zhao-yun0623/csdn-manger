package com.tju.csdnmanger.mapper;

import com.tju.csdnmanger.javaBean.InformationBean;
import com.tju.csdnmanger.javaBean.UserBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息管理dao层
 *
 * @author 赵云
 * @date 2020/09/11
 */

@Repository
public interface InformationMapper {

    /**
     * 查询用户信息
     * @return 所有用户的个人信息
     */
    List<InformationBean> getUsersInformation(InformationBean informationBean);


    void updateInformation(InformationBean information);
}
