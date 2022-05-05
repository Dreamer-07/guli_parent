package pers.prover07.guli.member.service;

import pers.prover07.guli.member.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.guli.member.entity.vo.RegisterVo;
import pers.prover07.guli.serviceenv.vo.OrderMemberVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-27
 */
public interface MemberService extends IService<Member> {

    /**
     * 手机号登录
     * @param mobile
     * @param password
     * @return
     */
    String mobileLogin(String mobile, String password);

    /**
     * 用户注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据 openId 获取用户信息
     * @param openId
     * @return
     */
    Member getByOpenId(String openId);

    /**
     * 获取订单的用户信息
     * @param memberId
     * @return
     */
    OrderMemberVo getOrderMemberInfo(String memberId);
}
