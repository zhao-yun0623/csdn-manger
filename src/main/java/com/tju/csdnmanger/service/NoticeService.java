package com.tju.csdnmanger.service;

import com.tju.csdnmanger.javaBean.*;
import com.tju.csdnmanger.javaBean.state.ResponseState;
import com.tju.csdnmanger.mapper.NoticeMapper;
import org.apache.http.protocol.RequestDate;
import org.apache.http.protocol.ResponseDate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 公告管理的Service层
 *
 * @author 赵云
 * @date 2020/09/11
 */

@Service
public class NoticeService {
    @Autowired
    private Logger logger;
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 发布公告
     * @param noticeBean：公告内容
     * @param admin：当前登录的管理员
     * @return responseData
     */
    public ResponseData putNotice(NoticeBean noticeBean, AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在发布公告");
        try {
            noticeBean.setTime(new Timestamp(new Date().getTime()));
            noticeMapper.putNotice(noticeBean);
            logger.info("发布公告成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("公告发布失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    /**
     * 获取所有公告，按时间从大到小排序
     * @param admin：当前登录的管理员
     * @return 所有公告信息
     */
    public ResponseData getNotices(AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在查询所有公告");
        try {
            List<NoticeBean> notices=noticeMapper.getNotices();
            logger.info("查询公告成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue(),"notices",notices);
        } catch (Exception e) {
            logger.error("查询公告失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    public ResponseData updateNotice(NoticeBean noticeBean, AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在修改公告");
        try {
            noticeMapper.updateNotice(noticeBean);
            logger.info("修改公告成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("公告修改失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    public ResponseData deleteNotice(int id, AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在删除公告");
        try {
            noticeMapper.deleteNotice(id);
            logger.info("修改公告成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("公告删除失败");
            System.out.println(e.getClass());
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }
}
