package com.linyilinyi.common.constant;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: RedisConstant
 */
public class RedisConstant {

    //用户登录
    public static final String USER_LOGIN_KEY_PREFIX = "user:login:";
    public static final String USER_LOGIN_REFRESH_KEY_PREFIX = "user:login:refresh:";
    public static final int USER_LOGIN_KEY_TIMEOUT = 60 * 60 * 24 * 100;
    public static final int USER_LOGIN_REFRESH_KEY_TIMEOUT = 60 * 60 * 24 * 365;

    //等待获取锁的时间
    public static final long ROB_NEW_ORDER_LOCK_WAIT_TIME = 1;
    //加锁的时间
    public static final long ROB_NEW_ORDER_LOCK_LEASE_TIME = 1;

    //优惠券信息
    public static final String COUPON_INFO = "coupon:info:";

    //优惠券分布式锁
    public static final String COUPON_LOCK = "coupon:lock:";
    //等待获取锁的时间
    public static final long COUPON_LOCK_WAIT_TIME = 1;
    //加锁的时间
    public static final long COUPON_LOCK_LEASE_TIME = 1;
}
