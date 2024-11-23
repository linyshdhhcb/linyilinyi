package com.linyilinyi.notice.service;

import com.linyilinyi.model.entity.notice.NoticeInfo;
import com.linyilinyi.model.vo.notice.NoticeSystemVo;
import com.linyilinyi.model.vo.notice.NoticeVo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/30
 * @ClassName: NoticeSendService
 */
public interface NoticeSendService {

    void sendLikeNotice(NoticeVo noticeVo);


    void sendSystemNotice(NoticeSystemVo noticeSystemVo);

    List<NoticeVo> readNotice();

    List<NoticeInfo> read(Integer senderId);

    Map<String,List<NoticeInfo>> getNotice();


    Map<Integer,List<NoticeInfo>> sendPrivateMessage();

    Integer getByToken(HttpServletRequest request);
}
