package com.july.pigeon.engine.task;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.july.pigeon.engine.BaseResponse;
import com.july.pigeon.engine.ConstantValues;
import com.july.pigeon.engine.RequestParam;
import com.july.pigeon.engine.RequestUtil;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.ui.activity.login.RegisterActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by ANDROID on 2017/6/7.
 */

public class MainTask {
    //获取验证码
    public void getsmsCode(final Context context, String phoneNum) {
        RequestUtil.postRequest(context, ConstantValues.smsCode, RequestParam.smsCode(phoneNum), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.sms_code));
                Log.i("resultdsf", result);
            }
        });
    }

    //注册
    public void regiest(final Context context,String phoneNum,String code,String password,String nickname) {
        RequestUtil.postRequest(context, ConstantValues.regiest, RequestParam.regiest( phoneNum, code, password, nickname), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.regiest));
                Log.i("resultdsf", result);
            }
        });
    }
    //注册
    public void login(final Context context,String phoneNum,String password) {
        RequestUtil.postRequest(context, ConstantValues.login, RequestParam.login( phoneNum,password), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.login));
                Log.i("resultdsf", result);
            }
        });
    }
}
