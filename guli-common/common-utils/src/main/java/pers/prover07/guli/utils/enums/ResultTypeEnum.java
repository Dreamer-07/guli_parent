package pers.prover07.guli.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 同一返回结果集的常用信息
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/18 9:59
 */
@Getter
public enum ResultTypeEnum {
    // 正常业务成功
    SUCCESS(20000, "成功"),
    // 通用业务失败
    FALID(20001, "服务器出错了"),
    // OSS 文件上传错误
    OSS_FILE_UPLOAD_FAILD(30001, "OSS 文件上传失败"),
    // Vdo 文件上传错误
    VDO_FILE_UPLOAD_FAILD(30002, "Vdo 文件删除上传失败"),
    // XLSX 文件上传失败
    XLSX_FILE_UPLOAD_FILID(30002, "xlsx 文件上传失败"),
    // 发送手机验证码失败
    SEND_CODE_ERROR(30003, "发送手机验证码失败"),
    // XLSX 文件没有大小
    XLSX_FILE_NO_SIZE(30003, "xlsx 文件没有大小"),
    // 删除章节时，由于小节存在所以不允许删除
    HAS_VIDEO(40001, "存在小节信息，请先删除对应的小节信息"),
    // feign 服务降级
    FEIGN_FALLBACK(50001, "feign 调用出现了服务降级:"),
    // 查找不到用户
    MEMBER_NO_FOUND(50002, "用户不存在"),
    // 密码错误
    MEMBER_PWD_ERROR(50003, "密码错误"),
    // 用户被锁定
    MEMBER_IS_LOCKING(50004, "用户被锁定"),
    // 用户已存在
    USER_ALREADY_EXISTS(50005, "用户已存在"),
    // 验证码失效
    CODE_INVALID(50006, "验证码失效"),

    MEMBER_WX_LOGIN_TOKEN_ERROR(50007, "获取微信 access_token 失败"),

    MEMBER_WX_USER_INFO_ERROR(50008, "获取微信用户信息失败"),

    WX_PAYING(20002, "支付中"),

    ORDER_NO_FOUND(60001, "订单不存在");

    private ResultTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;
    private final String message;
}
