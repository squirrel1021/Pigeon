package com.july.pigeon.engine;

/**
 * Created by ANDROID on 2017/6/7.
 */

public class ConstantValues {

    public static final String BaseUrl = "http://120.24.253.74/jansen";

    /************************************
     * 接口路径
     *********************************************/
    //验证码
    public static final String smsCode = BaseUrl + "/app/sms/code";
    //登录
    public static final String login = BaseUrl + "/app/member/login";
    //注册
    public static final String regiest = BaseUrl + "/app/member/reg";
    //忘记密码（验证手机号）
    public static final String verifyPhone = BaseUrl + "/app/member/forgetCheckMobile";
    //忘记密码（修改密码）
    public static final String updatePsw = BaseUrl + "/app/member/forgetModifyPassword";
    //修改昵称
    public static final String updateNickName = BaseUrl + "/app/member/modifyNickname";
    //个人信息
    public static final String userInfo = BaseUrl + "/app/member/information";
    //修改荣誉
    public static final String honor = BaseUrl + "/app/member/modifyHonor";
    //上传图片
    public static final String uploadImage = BaseUrl + "/app/community/dynamic/upload";
    //发表动态
    public static final String releaseCircle = BaseUrl + "/app/community/dynamic/submit";
    //社区动态列表
    public static final String myCircle = BaseUrl + "/app/community/dynamic/myDynamics";
    //修改鸽龄
    public static final String updateAge = BaseUrl + "/app/member/modifyBreedAge";
    //上传头像
    public static final String headImg = BaseUrl + "/app/member/uploadIcon";
    //保存头像
    public static final String saveheadImg = BaseUrl + "/app/member/modifyIcon";
    //添加脚环
    public static final String addjiaohuan = BaseUrl + "/app/ring/create";
}
