package com.july.pigeon.engine;

import com.loopj.android.http.RequestParams;

import java.util.List;

/**
 * Created by ANDROID on 2017/6/7.
 */

public class RequestParam {
    /**
     * 获取验证码
     *
     * @return
     */
    public static RequestParams smsCode(String mobile) {
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        return params;
    }
    /**
     * 注册
     * @return
     */
    public static RequestParams regiest(String mobile,String code,String password,String nickname) {
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        params.put("code",code);
        params.put("password",password);
        params.put("nickname",nickname);
        return params;
    }
    /**
     * 登陆
     * @return
     */
    public static RequestParams login(String mobile,String password) {
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        params.put("password",password);
        return params;
    }
    /**
     * 忘记密码-验证手机号
     * @return
     */
    public static RequestParams verifyPhone(String mobile,String code) {
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        params.put("code",code);
        return params;
    }
    /**
     * 忘记密码-
     * @return
     */
    public static RequestParams updatePsw(String mobile,String password) {
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        params.put("password",password);
        return params;
    }
    /**
     * 参数基类
     * @return
     */
    public static RequestParams baseParams(String[] list) {
        RequestParams params = new RequestParams();
        for(int i=0;i<list.length;i++){
            params.put(list[i],list[i]);
        }
        return params;
    }
}
