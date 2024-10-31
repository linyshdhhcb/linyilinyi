package com.linyilinyi.notice.service;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.vo.notice.LikeMseeageVo;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/30
 * @ClassName: NoticeSendService
 */
public interface NoticeSendService {

    void sendLikeNotice(LikeMseeageVo likeMseeageVo);
}
