package pers.prover07.guli.utils;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 同一返回结果集
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/18 9:59
 */
@Data
public class Result {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    // 私有化构造器
    private Result() {
    }

    public static Result ok() {
        return Result.ok(ResultTypeEnum.SUCCESS);
    }

    public static Result ok(ResultTypeEnum resultTypeEnum) {
        return new Result().success(true).code(resultTypeEnum.getCode()).message(resultTypeEnum.getMessage());
    }

    public static Result falid() {
        return Result.falid(ResultTypeEnum.FALID);
    }

    public static Result falid(ResultTypeEnum resultTypeEnum) {
        return new Result().success(false).code(resultTypeEnum.getCode()).message(resultTypeEnum.getMessage());
    }

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(Object data) {
        this.setData(data);
        return this;
    }

}
