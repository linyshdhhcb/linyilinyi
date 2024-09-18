package com.linyilinyi.common.utils;

import com.linyilinyi.common.exception.LinyiException;

import java.util.Optional;

/**
 * 获取当前用户信息帮助类
 */
public class AuthContextUser {

    private static ThreadLocal<Integer> userId = new ThreadLocal<Integer>();

    public static void setUserId(Integer _userId) {
        userId.set(_userId);
    }

    public static Integer getUserId() {
        // TODO 2024/9/17 AuthContextUser待完善（写死）
        setUserId(1);
        if (Optional.ofNullable(userId.get()).isEmpty()){
            throw new LinyiException(401,"没登录");
        }
        return userId.get();
    }

    public static void removeUserId() {
        userId.remove();
    }

}
