package com.linyilinyi.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description
 * @Author linyi
 * @Date 2024/5/23
 * @ClassName: PasswordEncoder
 */
public class PasswordEncoder {
    /**
     * 使用BCrypt算法对密码和盐进行加密。
     *
     * 此方法的目的是通过将密码和盐结合起来，然后使用BCryptPasswordEncoder对其进行加密，
     * 以增强系统的安全性。使用盐是为了增加密码的复杂性，使破解变得更加困难。
     *
     * @param password 原始密码字符串。
     * @param passwordSalt 盐值字符串，用于增强密码的安全性。
     * @return 加密后的密码字符串。
     */
    public static String encode(String password, String passwordSalt) {
        // 实例化BCryptPasswordEncoder对象，用于后续的密码加密操作。
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 将密码和盐合并为一个新的字符串，以便一起进行加密。
        String s1=password+passwordSalt;
        // 使用BCryptPasswordEncoder对合并后的字符串进行加密，并返回加密后的结果。
        return bCryptPasswordEncoder.encode(s1);
    }

    /**
     * 使用BCrypt算法检查密码是否一致
     *
     * 此方法主要用于验证给定的明文密码是否与使用特定盐值和BCrypt算法加密的密码匹配。
     * 它首先将随机密码和盐值连接起来，然后使用BCryptPasswordEncoder检查加密后的密码是否与输入的密码匹配。
     * 如果匹配，则说明加密过程使用了相同的盐值和算法，因此返回true；否则返回false。
     *
     * @param ranPassword 用户输入的密码
     * @param password 加密的密码
     * @param passwordSalt 盐值，用于在加密过程中增加额外的随机性，提高加密强度。
     * @return 如果输入的明文密码与使用特定盐值和BCrypt算法加密的密码匹配，则返回true；否则返回false。
     */
    public static boolean encode(String ranPassword, String password, String passwordSalt) {
        // 创建BCryptPasswordEncoder实例，用于密码加密和验证
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 将随机密码和盐值连接起来，模拟加密前的密码字符串
        String s1=ranPassword+passwordSalt;

        // 使用BCryptPasswordEncoder检查输入的明文密码是否与加密后的密码匹配
        if(!bCryptPasswordEncoder.matches(s1, password)){
            return false;
        }
        // 如果匹配成功，返回true
        return true;
    }

}
