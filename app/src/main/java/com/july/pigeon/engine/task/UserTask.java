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
import com.july.pigeon.util.SharedPreferencesUtil;

import java.io.File;
import java.io.FileNotFoundException;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class UserTask {
    //修改昵称
    public void upadteNickName(final Context context, String nickname) {
        RequestUtil.postRequest(context, ConstantValues.updateNickName, RequestParam.updateNickName(nickname), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.updateNickName));
                Log.i("resultdsf", result);
            }
        });
    }
    //个人信息
    public void userinfo(final Context context) {
        RequestUtil.getRequest(context, ConstantValues.userInfo, null, new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.userinfo));
                Log.i("resultdsf", result);
            }
        });
    }
    //个人信息
    public void honor(final Context context,String honor) {
        RequestUtil.postRequest(context, ConstantValues.honor, RequestParam.honor(honor), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.honor));
                Log.i("resultdsf", result);
            }
        });
    }
    //修改鸽龄
    public void upadteage(final Context context, String age) {
        RequestUtil.postRequest(context, ConstantValues.updateAge, RequestParam.updateAge(age), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.updateage));
                Log.i("resultdsf", result);
            }
        });
    }
    //上传头像
    public void uploadHeadImg(final Context context, File img) throws FileNotFoundException {
        String token="apptoken "+ SharedPreferencesUtil.getData(context, "token", "");
        RequestUtil.postRequest(context, ConstantValues.headImg, RequestParam.uploadheadImg(token,img), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.headimg));
                Log.i("resultdsf", result);
            }
        });
    }
    public void saveHeadImg(final Context context,String img) {
        RequestUtil.postRequest(context, ConstantValues.saveheadImg, RequestParam.saveheadImg(img), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.saveheadimg));
                Log.i("resultdsf", result);
            }
        });
    }
}
