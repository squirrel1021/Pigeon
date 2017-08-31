package com.july.pigeon.engine;

//import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.july.pigeon.ui.activity.login.LoginActivity;
import com.july.pigeon.util.ActivityStartUtil;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseResponse extends HefansCallBack {

    private String msg;
    private Context mContext;

    /**
     * 回调封装基类
     *
     * @param context
     * @param msg
     */
    public BaseResponse(Context context, String msg) {
        super(context);
        this.msg = msg;
        this.mContext = context;
    }

    @Override
    protected void resultCallBack(JSONObject result) {

        super.resultCallBack(result);
        Log.i("result", result + "");

        try {
            int code = result.getInt("code");
            if (code == 200) {
                onSuccess(result.getString("result"));
            } else {
                onFailure(result.getString("msg"));
            }

        } catch (JSONException e) {
            onFailure(result + "");
            e.printStackTrace();
        }

    }

    @Override
    public String getLoadingMsg() {
        // TODO Auto-generated method stub
        return msg;
    }

    @Override
    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
        // TODO Auto-generated method stub
        super.onFailure(statusCode, headers, responseString, throwable);
        onFailure(responseString);
        if ("尚未登录!".equals(responseString)) {
            ActivityStartUtil.start((Activity) mContext, LoginActivity.class);
        }
    }

    @Override
    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
        // TODO Auto-generated method stub
        super.onFailure(statusCode, headers, throwable, errorResponse);
        onFailure(errorResponse + "");
    }

    public abstract void onFailure(String message);

    public abstract void onSuccess(String result);

}
