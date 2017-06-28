package com.july.pigeon.engine;

//import org.apache.http.Header;

import android.content.Context;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseResponse extends HefansCallBack {

    private String msg;

    /**
     * 回调封装基类
     *
     * @param context
     * @param msg
     */
    public BaseResponse(Context context, String msg) {
        super(context);
        this.msg = msg;
    }

    @Override
    protected void resultCallBack(JSONObject result) {

        super.resultCallBack(result);
        Log.i("result", result + "");

        try {
            int code = result.getInt("code");
            if (code==200) {
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
