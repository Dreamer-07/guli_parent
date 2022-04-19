package pers.prover07.guli.serviceenv.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.Result;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/19 10:33
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception exception) {
        log.error("出现了新的全局异常: {}", exception.getMessage());
        return Result.falid();
    }

    @ExceptionHandler(GuliException.class)
    public Result handleGuliException(GuliException exception) {
        log.warn("出现了业务异常: {}", exception.getMessage());
        return Result.falid().code(exception.getCode()).message(exception.getMessage());
    }
}
