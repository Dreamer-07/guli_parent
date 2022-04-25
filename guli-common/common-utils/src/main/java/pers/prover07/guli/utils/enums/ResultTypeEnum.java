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
    FALID(20001, "服务器出错了"),
    // OSS 文件上传错误
    OSS_FILE_UPLOAD_FAILD(30001, "OSS 文件上传失败"),
    // Vdo 文件上传错误
    VDO_FILE_UPLOAD_FAILD(30002, "Vdo 文件删除上传失败"),
    // XLSX 文件上传失败
    XLSX_FILE_UPLOAD_FILID(30002, "xlsx 文件上传失败"),
    // XLSX 文件没有大小
    XLSX_FILE_NO_SIZE(30003, "xlsx 文件没有大小"),
    // 删除章节时，由于小节存在所以不允许删除
    HAS_VIDEO(40001, "存在小节信息，请先删除对应的小节信息"),
    // feign 服务降级
    FEIGN_FALLBACK(50001, "feign 调用出现了服务降级: "),;

    private ResultTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;
    private final String message;
}
