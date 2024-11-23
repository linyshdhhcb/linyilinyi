package com.linyilinyi.common.utils;

import com.linyilinyi.common.exception.LinyiException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class AuthContextUser {

    private static final InheritableThreadLocal<Integer> userId = new InheritableThreadLocal<>();


    public static void setUserId(Integer _userId) {
        userId.set(_userId);
    }

    public static Integer getUserId() {
        return userId.get();
//        return Optional.ofNullable(userId.get())
//                .orElseThrow(() -> new LinyiException(401, "没登录"));
    }

    public static void removeUserId() {
        userId.remove();
    }
}
