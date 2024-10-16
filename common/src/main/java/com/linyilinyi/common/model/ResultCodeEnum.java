package com.linyilinyi.common.model;

import lombok.Getter;

/**
 * @Description 统一返回结果状态信息类
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: ResultCodeEnum
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    INSERT_FAIL(201, "插入失败"),
    DELETE_FAIL(201, "删除失败"),
    IS_DELETE_FAIL(201, "逻辑删除失败"),
    UPDATE_FAIL(201, "修改失败"),
    FAIL_VIDEO(201,"视频信息插入失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    DATA_JSON_ERROR(204, "上传的json格式数据异常"),
    JSON_PARSE_ERROR(204, "json解析失败"),
    DATA_NULL(204, "数据为空"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    EXISTED(206, "已经存在"),
    FOLLOW_ERROR(206, "已经关注了"),
    REPEAT_SUBMIT_FILE(207,"该文件已经提交过，请勿重复提交"),
    FEIGN_FAIL(207, "远程调用失败"),
    UPDATE_ERROR(204, "数据更新失败"),

    ARGUMENT_VALID_ERROR(210, "参数校验异常"),
    VALID_ERROR(210, "id非法"),
    SIGN_ERROR(300, "签名错误"),
    SIGN_OVERDUE(301, "签名已过期"),
    VALIDATECODE_ERROR(218 , "验证码错误"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    LOGIN_OTHER_TERMINAL(210, "账号已在其他地方登录"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    PASSWORDS_ERROR(215, "两次密码不相同"),
    UPDATE_PASSWORD_ERROR(215, "密码不能修改"),
    PHONE_CODE_ERROR(215, "手机验证码不正确"),
    LOGIN_MOBLE_ERROR( 216, "账号不正确"),
    ACCOUNT_STOP( 216, "账号已停用"),
    ACCOUNT_NULL( 216, "该用户不存在"),
    NODE_ERROR( 217, "该节点下有子节点，不可以删除"),
    IMAGE_AUDITION_FAIL( 217, "图片审核不通过"),
    AUTH_ERROR( 217, "认证通过后才可以开启投稿"),

    COUPON_EXPIRE( 250, "优惠券已过期"),
    COUPON_LESS( 250, "优惠券库存不足"),
    COUPON_USER_LIMIT( 250, "超出领取数量"),
    TOKEN_NULL(401, "token为空"),
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

