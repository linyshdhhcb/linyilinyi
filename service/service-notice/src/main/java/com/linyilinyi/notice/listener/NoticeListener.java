package com.linyilinyi.notice.listener;

import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.notice.NoticeInfo;
import com.linyilinyi.model.vo.notice.LikeMseeageVo;
import com.linyilinyi.common.constant.MqConstant;
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
     * 点赞监听器
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.Like_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MqConstant.Like_EXCHANGE_NAME),
            key = MqConstant.Like_ROUTING_KEY
    ))
    public void likeListener(LikeMseeageVo likeMseeageVo) {
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(likeMseeageVo,noticeInfo);
        noticeInfo.setCreatedTime(LocalDateTime.now());
        noticeInfo.setMessageType(21001);
        int i = noticeInfoMapper.insert(noticeInfo);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        log.info("点赞消息监听成功");
    }
}
