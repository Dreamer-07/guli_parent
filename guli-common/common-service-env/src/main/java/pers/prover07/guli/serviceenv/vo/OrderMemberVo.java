package pers.prover07.guli.serviceenv.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/3 11:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMemberVo {

    private String memberId;

    private String nickname;

    private String mobile;

}
