package com.linyilinyi.notice.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.notice.NoticeInfo;
import com.linyilinyi.model.vo.notice.NoticeSystemVo;
import com.linyilinyi.model.vo.notice.NoticeVo;
import com.linyilinyi.notice.service.NoticeSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@SuppressWarnings({"unchecked", "rawtypes"})
public class NoticeSendController {

    @Resource
    private NoticeSendService noticeSendService;

    @Operation(summary = "点赞、评论、收藏、关注、私信、客服通知")
    @PostMapping("/sendLikeNotice")
    @Log(title = "通知管理",content = "点赞、评论、收藏、关注、私信、客服通知")
    public void sendLikeNotice(@RequestBody NoticeVo noticeVo){
         noticeSendService.sendLikeNotice(noticeVo);
    }

    @Operation(summary = "系统通知")
    @PostMapping("/sendSystemNotice")
    @Log(title = "通知管理",content = "系统通知")
    public void sendSystemNotice(@RequestBody NoticeSystemVo noticeSystemVo){
         noticeSendService.sendSystemNotice(noticeSystemVo);
    }

    @Operation(summary = "读取未读信息")
    @PostMapping("/readNotice")
    @Log(title = "通知管理",content = "读取未读信息")
    public Result<List<NoticeVo>> readNotice(){
        return Result.ok(noticeSendService.readNotice());
    }

    @Operation(summary = "读信息")
    @PutMapping("/read")
    @Log(title = "通知管理",content = "读信息")
    public Result<List<NoticeInfo>> read(@RequestParam Integer senderId){
        return Result.ok(noticeSendService.read(senderId));
    }

    @Operation(summary = "获取信息：点赞、评论、收藏、关注、系统")
    @GetMapping("/getNotice")
    @Log(title = "通知管理",content = "获取信息：点赞、评论、收藏、关注、系统")
    public Result<Map<String,List<NoticeInfo>>> getNotice(){
        return Result.ok(noticeSendService.getNotice());
    }

    @Operation(summary = "获取信息：私信")
    @PostMapping("/sendPrivateMessage")
    @Log(title = "通知管理",content = "获取信息：私信")
    public Result<Map<Integer,List<NoticeInfo>>> sendPrivateMessage(){
         return Result.ok(noticeSendService.sendPrivateMessage());

    }
}
