package pers.prover07.guli.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 同一返回结果集的常用信息
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/18 9:59
 */
@Getter
public enum ResultTypeEnum {
    // 正常业务成功
    SUCCESS(20000, "成功"),
    // 通用业务失败
    FALID(20001, "服务器出错了");

    private ResultTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
