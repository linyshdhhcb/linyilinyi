package com.linyilinyi.notice.listener;

import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.notice.NoticeInfo;
import com.linyilinyi.common.constant.MqConstant;
import com.linyilinyi.model.vo.notice.NoticeSystemVo;
import com.linyilinyi.model.vo.notice.NoticeVo;
import com.linyilinyi.notice.handler.WebSocketHandler;
import com.linyilinyi.notice.mapper.NoticeInfoMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description 消息队列监听器
 * @Author linyi
 * @Date 2024/10/31
 * @ClassName: NoticeListener
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeListener {

    @Resource
    private NoticeInfoMapper noticeInfoMapper;


    /**
     * 点赞信息
     * @param noticeVo
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.Like_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.Like_EXCHANGE_NAME),
            key = MqConstant.Like_ROUTING_KEY
    ))
    public void likeListener(NoticeVo noticeVo) throws InterruptedException {

        //信息插入数据库
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21001);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        log.info("点赞消息监听成功");
        //通过websocket 发送消息
        try {
            new WebSocketHandler().sendMessageToUser(String.valueOf(noticeVo.getReceiverId()), noticeVo.getContent());
            log.info("点赞消息发送成功");
        } catch (Exception e) {
            throw new LinyiException(ResultCodeEnum.SEND_WEBSOCKET_MESSAGE_ERROR);
        }
    }

    /**
     * 评论监听器
     * @param noticeVo
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.COMMENT_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.COMMENT_EXCHANGE_NAME),
            key = MqConstant.COMMENT_ROUTING_KEY
    ))
    public void commentListener(NoticeVo noticeVo) throws InterruptedException {
        //信息插入数据库
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21002);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        log.info("评论消息监听成功");
    }

    /**
     * 收藏监听器
     * @param noticeVo
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.COLLECT_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.COLLECT_EXCHANGE_NAME),
            key = MqConstant.COLLECT_ROUTING_KEY
    ))
    public void collectListener(NoticeVo noticeVo) throws InterruptedException {

        //信息插入数据库
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21003);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        log.info("收藏消息监听成功");
    }

    /**
     * 关注监听器
     * @param noticeVo
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.FOLLOW_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.FOLLOW_EXCHANGE_NAME),
            key = MqConstant.FOLLOW_ROUTING_KEY
    ))
    public void followListener(NoticeVo noticeVo) throws InterruptedException {

        //信息插入数据库
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21004);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        log.info("关注消息监听成功");
    }

    /**
     * 私信监听器
     * @param noticeVo
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.PRIVATE_EXCHANGE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.PRIVATE_EXCHANGE_NAME),
            key = MqConstant.PRIVATE_ROUTING_KEY
    ))
    public void privateListener(NoticeVo noticeVo) throws InterruptedException {

        //信息插入数据库
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21005);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
    }

    /**
     * 系统消息监听器
     * @param noticeSystemVo
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.SYSTEM_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.SYSTEM_EXCHANGE_NAME),
            key = MqConstant.SYSTEM_ROUTING_KEY
    ))
    public void systemListener(NoticeSystemVo noticeSystemVo) throws InterruptedException {

        //信息插入数据库
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeSystemVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21006);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
    }

    /**
     * 客服消息监听器
     * @param noticeSystemVo
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.CUSTOMER_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.CUSTOMER_EXCHANGE_NAME),
            key = MqConstant.CUSTOMER_ROUTING_KEY
    ))
    public void customerListener(NoticeSystemVo noticeSystemVo) throws InterruptedException {

        //信息插入数据库
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeSystemVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21007);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
    }
}
