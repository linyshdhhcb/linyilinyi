package com.linyilinyi.notice.service.impl;

import com.linyilinyi.model.vo.notice.CommentMessageVo;
import com.linyilinyi.model.vo.notice.LikeMseeageVo;
import com.linyilinyi.common.constant.MqConstant;
import com.linyilinyi.notice.service.NoticeSendService;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/30
 * @ClassName: NoticeSendServiceImpl
 */
@Service
public class NoticeSendServiceImpl implements NoticeSendService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendLikeNotice(LikeMseeageVo likeMseeageVo) {
        rabbitTemplate.convertAndSend(MqConstant.Like_EXCHANGE_NAME, MqConstant.Like_ROUTING_KEY, likeMseeageVo);
    }

    @Override
    public void sendCommentNotice(CommentMessageVo commentMessageVo) {
        rabbitTemplate.convertAndSend(MqConstant.COMMENT_EXCHANGE_NAME, MqConstant.COMMENT_ROUTING_KEY, commentMessageVo);
    }
}
