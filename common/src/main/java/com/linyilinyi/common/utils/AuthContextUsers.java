package com.linyilinyi.common.utils;

import java.util.Map;
import java.util.Optional;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/21
 * @ClassName: AuthContextUsers
 */
public class AuthContextUsers {

    public static Integer getUserId() {
        int i = 0;
        Integer userId = null;
        try {
            do {
                userId = AuthContextUser.getUserId();
                i++;
            } while (userId == null && i < 6);
        } catch (Exception ex) {
        }
        return userId;
    }

    public static void main(String[] args) {
        System.out.println(getUserId());
    }
}
