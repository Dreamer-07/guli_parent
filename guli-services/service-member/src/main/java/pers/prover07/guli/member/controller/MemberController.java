package pers.prover07.guli.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pers.prover07.guli.member.entity.Member;
import pers.prover07.guli.member.entity.vo.LoginVo;
import pers.prover07.guli.member.entity.vo.RegisterVo;
import pers.prover07.guli.member.service.MemberService;
import pers.prover07.guli.utils.JwtUtils;
import pers.prover07.guli.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/app/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public Result memberLogin(@RequestBody LoginVo loginVo) {
        String token = memberService.mobileLogin(loginVo.getMobile(), loginVo.getPassword());
        return Result.ok().data(token);
    }

    @PostMapping("/register")
    public Result memberRegister(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.ok();
    }

    @GetMapping("/info")
    public Result getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        return Result.ok().data(member);
    }


}

