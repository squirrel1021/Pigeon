package com.july.pigeon.engine;

import com.loopj.android.http.RequestParams;

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

}
