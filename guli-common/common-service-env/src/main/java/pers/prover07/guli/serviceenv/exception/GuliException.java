package pers.prover07.guli.serviceenv.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/19 11:02
 */
@Data
@AllArgsConstructor
public class GuliException extends RuntimeException{

    private Integer code;
    private String message;

    public GuliException() {
        this(ResultTypeEnum.FALID);
    }

    public GuliException(ResultTypeEnum resultTypeEnum) {
        this.code = resultTypeEnum.getCode();
        this.message = resultTypeEnum.getMessage();
    }

    public GuliException(String message) {
        this.code = ResultTypeEnum.FALID.getCode();
        this.message = message;
    }
}
