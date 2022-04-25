package pers.prover07.guli.edu.client.fallback;

import org.springframework.stereotype.Component;
import pers.prover07.guli.edu.client.VdoFeignClient;
import pers.prover07.guli.utils.Result;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/25 20:48
 */
@Component
public class VdoFeignClientFallback implements VdoFeignClient {
    @Override
    public Result delete(String videoSourceId) {
        return Result.falid(ResultTypeEnum.FEIGN_FALLBACK);
    }

    @Override
    public Result batchDetele(List<String> videoSourceIds) {
        return Result.falid(ResultTypeEnum.FEIGN_FALLBACK);
    }
}
