package com.linyilinyi.common.utils;


/**
 * @Description 正则表达式
 * @Author linyi
 * @Date 2024/6/5
 * @ClassName: REUtil
 */
public class REUtil {

    public static void main(String[] args) {
    }

    /**
     * 判断字符串是否为六位数的数字。
     *
     * @param str 待验证的字符串
     * @return 如果字符串是六位数的数字，则返回true；否则返回false。
     */
   public static boolean isNumeric(String str) {

        String regex = "^[0-9]{6,}$";
        return str.matches(regex);
    }
    /**
     * 账号：检查给定字符串是否只包含字母和数字。
     *
     * @param str 待检查的字符串。
     * @return 如果字符串只包含字母和数字，则返回true；否则返回false。
     */
    public static boolean isAlphaNumeric(String str) {
        // 使用正则表达式检查字符串是否只包含字母和数字6至16位
        String regex = "^[a-zA-Z0-9]{6,26}$";
        return str.matches(regex);
    }

    /**
     * 密码：检查给定字符串是否只包含字母、数字和特殊字符。
     *
     * @param str 待检查的字符串
     * @return 如果字符串只包含字母、数字和下划线，则返回true；否则返回false。
     */
    public static boolean isAlphaNumericWithUnderscore(String str) {
        // 先检查字符串长度是否符合要求，能提前排除不符合长度条件的字符串，提高效率
        if (str == null || str.length() < 6 || str.length() > 26) {
            return false;
        }

        // 使用修正后的正则表达式，允许字母、数字和指定的特殊字符
        // 注意：特殊字符如!@#$%^&*()\-_=+{};:,<.>?/需要正确转义，-放在字符集的开始或结束位置表示范围
        String regex = "^[A-Za-z0-9!@#$%^&*()\\-_=+{};:,<.>?/]{6,26}$";
        return str.matches(regex);
    }

    /**
     * 检查字符串是否为有效的电子邮件地址
     * 该方法通过检查字符串是否为空以及是否符合电子邮件格式的正则表达式来判断其有效性
     *
     * @param email 待验证的电子邮件地址字符串
     * @return 如果电子邮件地址有效，则返回true；否则返回false
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.length() > 32) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }

}

