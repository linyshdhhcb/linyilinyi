package com.linyilinyi.notice.controller;

import com.linyilinyi.model.vo.notice.LikeMseeageVo;
import com.linyilinyi.notice.service.NoticeSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "点赞通知")
    @PostMapping("/sendLikeNotice")
    public void sendLikeNotice(@RequestBody LikeMseeageVo likeMseeageVo){
         noticeSendService.sendLikeNotice(likeMseeageVo);
    }

    @Operation(summary = "评论通知")
    @PostMapping("/sendCommentNotice")
    public void sendCommentNotice(){
         //noticeSendService.sendCommentNotice();
    }


}
