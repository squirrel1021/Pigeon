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
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
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
    public void releaseCircle(final Context context, String content, String imgUrls) {
        RequestUtil.postRequest(context, ConstantValues.releaseCircle, RequestParam.releaseCircle("发布动态", content, imgUrls), new BaseResponse(context, "加载中") {
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

    //评论列表
    public void conments(final Context context, int pageIndex, int pageSize, final int type, String dynamicId) {
        RequestUtil.getRequest(context, ConstantValues.conments, RequestParam.conments(dynamicId,pageIndex, pageSize), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                if(type==0){
                    EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.conments));
                }else{

                }
                Log.i("resultdsf", result);
            }
        });
    }

    //发表回复
    public void replyContent(final Context context, String content, String dynamicId) {
        RequestUtil.postRequest(context, ConstantValues.replyConments, RequestParam.replyContent( content, dynamicId), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.replyContent));
                Log.i("resultdsf", result);
            }
        });
    }
}
