package com.linyilinyi.notice.service;

import com.linyilinyi.model.vo.notice.NoticeSystemVo;
import com.linyilinyi.model.vo.notice.NoticeVo;

import java.util.List;

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

}
