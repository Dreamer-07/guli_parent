package pers.prover07.guli.cms.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import pers.prover07.guli.cms.service.BannerService;
import pers.prover07.guli.utils.Result;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-26
 */
@Api(tags = "banner图数据")
@CrossOrigin
@RestController
@RequestMapping("/app/cms/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;


    @Cacheable(value = "service-cms",key = "'banner'")
    @GetMapping("/list")
    public Result list() {
        return Result.ok().data(bannerService.list(null));
    }

}

