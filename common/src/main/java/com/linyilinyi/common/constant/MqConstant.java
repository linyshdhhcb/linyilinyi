package com.linyilinyi.common.constant;

/**
 * @Description 消息队列常量
 * @Author linyi
 * @Date 2024/10/30
 * @ClassName: MqConstant
 */
public class MqConstant {

    // 点赞交换机
    public static final String Like_EXCHANGE_NAME = "like.direct";

    // 点赞路由键
    public static final String Like_ROUTING_KEY = "like.success";

    // 点赞队列
    public static final String Like_QUEUE_NAME = "like.queue";

    //评论交换机
    public static final String COMMENT_EXCHANGE_NAME = "comment.direct";

    //评论路由键
    public static final String COMMENT_ROUTING_KEY = "comment.success";

    //评论队列
    public static final String COMMENT_QUEUE_NAME = "comment.queue";

    //收藏交换机
    public static final String COLLECT_EXCHANGE_NAME = "collect.direct";

    //收藏路由键
    public static final String COLLECT_ROUTING_KEY = "collect.success";

    //收藏队列
    public static final String COLLECT_QUEUE_NAME = "collect.queue";

    //关注交换机
    public static final String FOLLOW_EXCHANGE_NAME = "follow.direct";

    //关注路由键
    public static final String FOLLOW_ROUTING_KEY = "follow.success";

    //关注队列
    public static final String FOLLOW_QUEUE_NAME = "follow.queue";

    //私信交换机
    public static final String PRIVATE_EXCHANGE_NAME = "private.direct";

    //私信路由键
    public static final String PRIVATE_ROUTING_KEY = "private.success";

    //私信队列
    public static final String PRIVATE_QUEUE_NAME = "private.queue";

    //系统通知交换机
    public static final String SYSTEM_EXCHANGE_NAME = "system.direct";

    //系统通知路由键
    public static final String SYSTEM_ROUTING_KEY = "system.success";

    //系统通知队列
    public static final String SYSTEM_QUEUE_NAME = "system.queue";

    //客服通知交换机
    public static final String CUSTOMER_EXCHANGE_NAME = "customer.direct";

    //客服通知路由键
    public static final String CUSTOMER_ROUTING_KEY = "customer.success";

    //客服通知队列
    public static final String CUSTOMER_QUEUE_NAME = "customer.queue";

}
