package com.linyilinyi.notice.client;

import com.linyilinyi.model.vo.notice.NoticeVo;
import lombok.extern.java.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/25
 * @ClassName: NoticeClient
 */

@FeignClient(value = "service-notice")
public interface NoticeClient {

    @PostMapping("/notice/sendLikeNotice")
    public void sendLikeNotice(@RequestBody NoticeVo noticeVo);
}
