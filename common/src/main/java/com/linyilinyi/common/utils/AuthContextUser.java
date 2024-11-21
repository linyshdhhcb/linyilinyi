package com.linyilinyi.common.utils;

import cn.dev33.satoken.stp.StpUtil;
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
        Integer i1 = convertToInteger(StpUtil.getLoginId());
        userId.set(i1);
        Integer i = userId.get();
        if (Optional.ofNullable(i).isEmpty()) {
            throw new LinyiException(401, "没登录");
        }
        return userId.get();
    }

    public static void removeUserId() {
        userId.remove();
    }

    public static Integer convertToInteger(Object loginId) {
        if (loginId instanceof Integer) {
            return (Integer) loginId;
        } else if (loginId instanceof Long) {
            return ((Long) loginId).intValue();
        } else if (loginId instanceof String) {
            try {
                return Integer.parseInt((String) loginId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
        }
        return null;
    }
}
