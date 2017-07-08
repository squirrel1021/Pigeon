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

import java.io.File;
import java.io.FileNotFoundException;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class CircleTask {
    //上传图片
    public void uploadImg(final Context context, String token, File[] arr) throws FileNotFoundException {
        RequestUtil.postRequest(context, ConstantValues.uploadImage, RequestParam.uploadImg(token, arr), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.uploadImg));
                Log.i("resultdsf", result);
            }
        });
    }

    //我的社区
    public void MyCircle(final Context context, int pageIndex, int pageSize, final int type) {
        RequestUtil.getRequest(context, ConstantValues.myCircle, RequestParam.myCircle(pageIndex, pageSize), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                if(type==0){
                    EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.mycircle));
                }else{
                    EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.mycirclemore));
                }

                Log.i("resultdsf", result);
            }
        });
    }

    //发布动态
    public void releaseCircle(final Context context, String title, String content, String[] imgUrls) {
        RequestUtil.postRequest(context, ConstantValues.releaseCircle, RequestParam.releaseCircle(title, content, imgUrls), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.releaseCircle));
                Log.i("resultdsf", result);
            }
        });
    }
}
