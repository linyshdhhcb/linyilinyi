package com.linyilinyi.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.linyilinyi.common.constant.MqConstant;
import com.linyilinyi.common.constant.NoticeTypeConstant;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.notice.NoticeInfo;
import com.linyilinyi.model.vo.notice.NoticeSystemVo;
import com.linyilinyi.model.vo.notice.NoticeVo;
import com.linyilinyi.notice.mapper.NoticeInfoMapper;
import com.linyilinyi.notice.service.NoticeSendService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Case;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private NoticeInfoMapper noticeInfoMapper;

    /**
     * 发送通知消息到相应的消息队列
     * 根据通知消息的类型，将消息发送到不同的交换机和路由键所指定的消息队列中
     *
     * @param noticeVo 包含通知消息详细信息的对象
     * @throws LinyiException 如果通知消息的类型不存在于预定义的类型中
     */
    @Override
    public void sendLikeNotice(NoticeVo noticeVo) {
        // 根据消息类型执行不同的发送逻辑
        switch (noticeVo.getMessageType()) {
            // 点赞
            case NoticeTypeConstant.LIKE_NOTICE->
                // 将消息发送到点赞通知的交换机和路由键指定的队列
                rabbitTemplate.convertAndSend(MqConstant.Like_EXCHANGE_NAME, MqConstant.Like_ROUTING_KEY, noticeVo);
            // 收藏
            case NoticeTypeConstant.COLLECT_NOTICE->
                // 将消息发送到收藏通知的交换机和路由键指定的队列
                rabbitTemplate.convertAndSend(MqConstant.COLLECT_EXCHANGE_NAME, MqConstant.COLLECT_ROUTING_KEY, noticeVo);
            // 评论
            case NoticeTypeConstant.COMMENT_NOTICE->
                // 将消息发送到评论通知的交换机和路由键指定的队列
                rabbitTemplate.convertAndSend(MqConstant.COMMENT_EXCHANGE_NAME, MqConstant.COMMENT_ROUTING_KEY, noticeVo);
            // 关注
            case NoticeTypeConstant.FOLLOW_NOTICE->
                // 将消息发送到关注通知的交换机和路由键指定的队列
                rabbitTemplate.convertAndSend(MqConstant.FOLLOW_EXCHANGE_NAME, MqConstant.FOLLOW_ROUTING_KEY, noticeVo);
            // 私信
            case NoticeTypeConstant.PRIVATE_MESSAGE_NOTICE->
                // 将消息发送到私信通知的交换机和路由键指定的队列
                rabbitTemplate.convertAndSend(MqConstant.PRIVATE_EXCHANGE_NAME, MqConstant.PRIVATE_ROUTING_KEY, noticeVo);
            // 客服
            case NoticeTypeConstant.CUSTOMER_NOTICE->
                // 将消息发送到客服通知的交换机和路由键指定的队列
                rabbitTemplate.convertAndSend(MqConstant.CUSTOMER_EXCHANGE_NAME, MqConstant.CUSTOMER_ROUTING_KEY, noticeVo);
            // 默认情况，抛出异常
            default -> throw new LinyiException("消息类型不存在");
        }
    }

    /**
     * 发送系统通知
     * 该方法通过RabbitMQ发送系统通知，使用指定的交换机和路由键
     * 主要用于解耦系统通知的发送逻辑，将通知的构造和发送过程移至消息队列处理
     *
     * @param noticeSystemVo 包含系统通知信息的对象，用于传递通知的详细内容
     */
    @Override
    public void sendSystemNotice(NoticeSystemVo noticeSystemVo) {
        rabbitTemplate.convertAndSend(MqConstant.SYSTEM_EXCHANGE_NAME, MqConstant.SYSTEM_ROUTING_KEY, noticeSystemVo);
    }

    @Override
    public List<NoticeVo> readNotice() {
        Integer userId = AuthContextUser.getUserId();
        LambdaQueryWrapper<NoticeInfo> queryWrapper = new LambdaQueryWrapper<NoticeInfo>().eq(NoticeInfo::getReceiverId, userId).eq(NoticeInfo::getIsRead, 0).orderByDesc(NoticeInfo::getSenderId);
        ArrayList<NoticeVo> noticeVos = new ArrayList<>();
        noticeInfoMapper.selectList(queryWrapper).stream().forEach(e->{
            NoticeVo noticeVo = new NoticeVo();
            BeanUtils.copyProperties(e,noticeVo);
            noticeVos.add(noticeVo);
        });
        return noticeVos;
    }
}
