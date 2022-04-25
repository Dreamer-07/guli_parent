package pers.prover07.guli.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pers.prover07.guli.edu.client.fallback.VdoFeignClientFallback;
import pers.prover07.guli.utils.Result;

import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/25 11:21
 */
@FeignClient(value = "service-vdo", fallback = VdoFeignClientFallback.class)
@Component
public interface VdoFeignClient {

    @DeleteMapping("/api/vdo/file/{videoSourceId}")
    Result delete(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping("/api/vdo/file/batch-delete")
    Result batchDetele(@RequestParam("videoSourceIds") List<String> videoSourceIds);

}
