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
}
