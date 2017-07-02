package com.july.pigeon.engine;

import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
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

    public static RequestParams updateNickName(String nickname) {
        RequestParams params = new RequestParams();
        params.put("nickname",nickname);
        return params;
    }
    public static RequestParams honor(String honor) {
        RequestParams params = new RequestParams();
        params.put("honor",honor);
        return params;
    }
    public static RequestParams uploadImg(String token,File[] imgs) throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("token",token);
        params.put("imgUrls",imgs);
        return params;
    }
    public static RequestParams releaseCircle(String title,String content,String[] imgUrls) {
        RequestParams params = new RequestParams();
        params.put("title",title);
        params.put("content",content);
        params.put("imgUrls",imgUrls);
        return params;
    }
    public static RequestParams myCircle(int pageIndex,int pageSize) {
        RequestParams params = new RequestParams();
        params.put("pageIndex",pageIndex);
        params.put("pageSize",pageSize);
        return params;
    }
}
