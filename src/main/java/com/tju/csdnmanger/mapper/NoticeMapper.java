package com.tju.csdnmanger.mapper;

import com.tju.csdnmanger.javaBean.NoticeBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公告管理dao层接口
 *
 * @author 赵云
 * @date 2020/09/11
 */
@Repository
public interface NoticeMapper {
    void putNotice(NoticeBean noticeBean);

    List<NoticeBean> getNotices();

    void updateNotice(NoticeBean noticeBean);

    void deleteNotice(int id);
}
