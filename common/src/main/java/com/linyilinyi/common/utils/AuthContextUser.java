package com.linyilinyi.common.utils;

import com.linyilinyi.common.exception.LinyiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class AuthContextUser {

    private static final ThreadLocal<Integer> userId = new ThreadLocal<>();


    public static void setUserId(Integer _userId) {
        userId.set(_userId);
    }

    public static Integer getUserId(HttpServletRequest request) {
        int i = Integer.parseInt(request.getHeader("userid"));
        setUserId(i);
        try {
            return Optional.ofNullable(userId.get())
                    .orElseThrow(() -> new LinyiException(401, "没登录"));
        } finally {
           removeUserId();
        }

    }

    public static void removeUserId() {
        userId.remove();
    }
}
