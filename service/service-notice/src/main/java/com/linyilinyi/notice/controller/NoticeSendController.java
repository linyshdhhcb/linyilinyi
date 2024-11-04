package com.linyilinyi.notice.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.vo.notice.NoticeSystemVo;
import com.linyilinyi.model.vo.notice.NoticeVo;
import com.linyilinyi.notice.service.NoticeSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/30
 * @ClassName: NoticeSendController
 */
@Tag(name = "通知管理")
@Slf4j
@RequestMapping("/notice")
@RestController
public class NoticeSendController {

    @Resource
    private NoticeSendService noticeSendService;

    @Operation(summary = "点赞、评论、收藏、关注、私信、客服通知")
    @PostMapping("/sendLikeNotice")
    public void sendLikeNotice(@RequestBody NoticeVo noticeVo){
         noticeSendService.sendLikeNotice(noticeVo);
    }

    @Operation(summary = "系统通知")
    @PostMapping("/sendSystemNotice")
    public void sendSystemNotice(@RequestBody NoticeSystemVo noticeSystemVo){
         noticeSendService.sendSystemNotice(noticeSystemVo);
    }

    @Operation(summary = "读取未读信息")
    @PostMapping("/readNotice")
    public Result<List<NoticeVo>> readNotice(){
        return Result.ok(noticeSendService.readNotice());
    }

}
