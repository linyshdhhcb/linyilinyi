package com.linyilinyi.common.exception;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: LinyiException
 */
@Data
public class LinyiException extends RuntimeException{

    private Integer code;

    private String message;

    /**
     * 通过错误消息创建异常对象
     * @param message
     */
    public LinyiException(String message) {
        super(message);
        this.code = 201;
        this.message = message;
    }

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public LinyiException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public LinyiException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }


    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
