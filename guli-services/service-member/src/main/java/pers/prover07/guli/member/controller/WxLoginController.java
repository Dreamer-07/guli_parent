package pers.prover07.guli.member.controller;

import com.google.gson.Gson;
import org.apache.poi.ss.formula.ptg.MemErrPtg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.prover07.guli.member.entity.Member;
import pers.prover07.guli.member.properties.WxOpenProperties;
import pers.prover07.guli.member.service.MemberService;
import pers.prover07.guli.member.utils.HttpClientUtils;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.JwtUtils;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/30 18:17
 */
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxLoginController {

    @Autowired
    private WxOpenProperties wxOpenProperties;

    @Autowired
    private MemberService memberService;

    private final Gson gson = new Gson();

    /**
     * 微信二维码连接
     */
    private final static String WX_QR_CODE_URL = "https://open.weixin.qq.com/connect/qrconnect" +
            "?appid=%s" +
            "&redirect_uri=%s" +
            "&response_type=code" +
            "&scope=snsapi_login" +
            "&state=%s" +
            "#wechat_redirect";

    /**
     * 获取微信token链接
     */
    private final static String WX_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
            "?appid=%s" +
            "&secret=%s" +
            "&code=%s" +
            "&grant_type=authorization_code";

    /**
     * 获取微信用户信息链接
     */
    private final static String WX_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo" +
            "?access_token=%s" +
            "&openid=%s";

    /**
     * 服务器地址
     */
    private final static String SERVER_URL = "http://localhost:3000/";

    /**
     * 用户选择微信登录
     *
     * @return
     */
    @GetMapping("/login")
    public String wxLogin() {
        return "redirect:" + String.format(WX_QR_CODE_URL, wxOpenProperties.getAppId(), wxOpenProperties.getRedirectUrl(), "byqtxdy");
    }

    /**
     * 微信登录回调
     *
     * @param code
     * @return
     */
    @GetMapping("/callback")
    public String wxLoginCallback(@RequestParam("code") String code) {
        // get access_token & openid by code
        String getWxTokeUrl = String.format(
                WX_TOKEN_URL,
                wxOpenProperties.getAppId(),
                wxOpenProperties.getAppSecret(),
                code
        );
        // 发起请求
        String result = null;
        try {
            result = HttpClientUtils.get(getWxTokeUrl);
        } catch (Exception exception) {
            throw new GuliException(ResultTypeEnum.MEMBER_WX_LOGIN_TOKEN_ERROR);
        }

        // 将字符串转换为 Map 结构
        HashMap resultMap = gson.fromJson(result, HashMap.class);
        // 获取 access_token & openId
        String accessToken = (String) resultMap.get("access_token");
        String openId = (String) resultMap.get("openid");
        // 查找用户是否注册过
        Member member = memberService.getByOpenId(openId);

        if (member == null) {
            // 获取用户信息
            String getWxUserInfoUrl = String.format(WX_USER_INFO_URL, accessToken, openId);

            // 发送请求
            String userInfoResult = null;
            try {
                userInfoResult = HttpClientUtils.get(getWxUserInfoUrl);
            } catch (Exception exception) {
                throw new GuliException(ResultTypeEnum.MEMBER_WX_USER_INFO_ERROR);
            }

            // 解析响应数据
            HashMap userInfoResultMap = gson.fromJson(userInfoResult, HashMap.class);

            // 获取用户的数据
            String nickname = (String) userInfoResultMap.get("nickname");
            String headImgUrl = (String) userInfoResultMap.get("headimgurl");

            // 保存用户信息
            member = new Member();
            member.setNickname(nickname);
            member.setAvatar(headImgUrl);
            member.setOpenid(openId);
            memberService.save(member);
        }

        // 生成 jwt 令牌
        String jwtToken = JwtUtils.getJwtToken(member.getId());
        return "redirect:" + SERVER_URL + "?token=" + jwtToken;
    }

}
