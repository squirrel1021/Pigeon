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

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class PigeonTask {
    //添加脚环
    public void login(final Context context, String ringCode) {
        RequestUtil.postRequest(context, ConstantValues.addjiaohuan, RequestParam.addjiaohuan(ringCode), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.addjiaohuan));
                Log.i("resultdsf", result);
            }
        });
    }

    //我的社区
    public void getUpData(final Context context, String ringCode) {
        RequestUtil.getRequest(context, ConstantValues.getUpData, RequestParam.getUpData(ringCode), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.getUpData));
            }
        });
    }

    //获取实时脚环上一个坐标
    public void getLastPoint(final Context context, String ringCode) {
        RequestUtil.getRequest(context, ConstantValues.getLastUpData, RequestParam.getUpData(ringCode), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.getLastUpData));
            }
        });
    }

    //获取多脚环上报数据列表
    public void getLastPointList(final Context context, String ringCode) {
        RequestUtil.getRequest(context, ConstantValues.getUpDatas, RequestParam.getUpData(ringCode), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.getUpDatas));
            }
        });
    }

    //获取某个脚环的飞行状态
    public void getStatus(final Context context, String ringId) {
        RequestUtil.postRequest(context, ConstantValues.getStatus, RequestParam.getStatus(ringId), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "脚环设置失败", Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.getStatus));
                Log.i("resultdsf", result);
            }
        });
    }

    //开始某个脚环的飞行或重新开始飞行
    public void start(final Context context, String ringId) {
        RequestUtil.postRequest(context, ConstantValues.start, RequestParam.getStatus(ringId), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "脚环开始飞行失败", Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.start));
                Log.i("resultdsf", result);
            }
        });
    }

    //开始某个脚环的飞行或重新开始飞行
    public void end(final Context context, String ringId) {
        RequestUtil.postRequest(context, ConstantValues.end, RequestParam.getStatus(ringId), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "脚环停止失败", Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.end));
                Log.i("resultdsf", result);
            }
        });
    }

    //设置脚环
    public void setJiaohuan(final Context context, String name, String intervalTime, String gpsTime, String isStart, String startTime, String endTime) {
        RequestUtil.postRequest(context, ConstantValues.setACQ, RequestParam.setACQ(name, intervalTime, gpsTime, isStart, startTime, endTime), new BaseResponse(context, "加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "脚环设置失败", Toast.LENGTH_LONG).show();
                Log.i("messagesdfds", message);
            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(EventByTag.setDefault(result, EventTagConfig.setACQ));
                Log.i("resultdsf", result);
            }
        });
    }
}
