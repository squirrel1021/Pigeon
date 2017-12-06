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
    //验证手机号
    public void verifyPhone(final Context context,String phoneNum,String code) {
        RequestUtil.postRequest(context, ConstantValues.verifyPhone, RequestParam.verifyPhone(phoneNum,code), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.verifyPhone));
                Log.i("resultdsf", result);
            }
        });
    }
    //忘记密码
    public void updatePsw(final Context context, String phoneNum, String code) {
        RequestUtil.postRequest(context, ConstantValues.updatePsw, RequestParam.updatePsw(phoneNum, code), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.updatePsw));
                Log.i("resultdsf", result);
            }
        });
    }

    //修改密码
    public void updatePassword(final Context context, String newPsw, String oldPsw) {
        RequestUtil.postRequest(context, ConstantValues.updatePassword, RequestParam.updatePassword(oldPsw, newPsw), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.updatePassword));
                Log.i("resultdsf", result);
            }
        });
    }

    //转换
    public void getImei(final Context context, String ringCode) {
        RequestUtil.getRequest(context, ConstantValues.getEidByAid, RequestParam.getImei(ringCode), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.getImei));
            }
        });
    }
}
