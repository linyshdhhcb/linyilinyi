package com.linyilinyi.common.utils;

import com.alibaba.fastjson2.JSON;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;

/**
 * @Description 屏蔽敏感字
 * @Author linyi
 * @Date 2024/11/28
 * @ClassName: SensitiveWordsUtils
 */
public class SensitiveWordsUtils {
    /**
     * 判断是否包含敏感字
     */
    public static boolean isSensitiveWords(Object object) {
        return SensitiveWordHelper.contains(JSON.toJSONString(object));
    }

}
