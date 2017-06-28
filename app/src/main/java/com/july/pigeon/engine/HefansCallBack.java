package com.july.pigeon.engine;


import android.content.Context;

import com.july.pigeon.util.StringUtils;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class HefansCallBack extends JsonHttpResponseHandler {
    private String TAG = HefansCallBack.class.getSimpleName();

    private Loading loading = null;
    /**
     * 加载信息
     */
    private String loadingMsg = "";

    private Context context;

    public HefansCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();

        showLoadingProgressDialog();
    }

    /**
     * 进度条的提示信息，如果为空则不显示
     */
    public String getLoadingMsg() {
        return loadingMsg;
    }

    /**
     * 通信失败的回调函数
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);

//        ErrorCallBack.processError(context, statusCode, responseString, throwable);
        onFail(responseString);
    }

    /**
     * 通信失败的回调函数
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);

//        ErrorCallBack.processError(context, statusCode, errorResponse == null ? "" : errorResponse.toString(), throwable);

        onFail(errorResponse == null ? "" : errorResponse.toString());
    }

    /**
     * 通信成功的回调函数，返回信息为字符串类型，此处用不到
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        super.onSuccess(statusCode, headers, responseString);

        resultExecute(responseString);

//        LogUtil.i(TAG, responseString);
    }

    /**
     * 通信成功的回调函数，返回信息为JSON格式，此处用不到
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);

        resultExecute(response);

//        LogUtil.i(TAG, response.toString());
    }

    @Override
    public void onFinish() {
        super.onFinish();

        dismissProgressDialog();
    }

    private void resultExecute(String resultText) {
        if (StringUtils.isEmpty(resultText)) {
            return;
        }

        JSONObject r = null;

        try {
            r = new JSONObject(resultText);

            resultExecute(r);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void resultExecute(JSONObject retObj) {
        if (null == retObj) {
            return;
        }


            List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
//            String resultMessage = retObj.get("resultMessage") + "";
//            retObj.containsKey("resultMessage");

//            if(retObj.has("resultMessage")){
//                if ((retObj.get("resultMessage")+"").equals("token_error")) {
//                    resultCallBackTokeninValid(retObj);// token无效
//                    return;
//                }
//            }

//            {"code":200,"msg":"验证码发送成功！","result":{}}

//            if (retObj.getString("code").equals("200")){
                resultCallBack(retObj);
//            }
//            if (retObj.getString("errorCode").equals("000")) {
//                resultCallBack(retObj);
//            } else if (retObj.getString("errorCode").equals("100") || retObj.getString("errorCode").equals("002")) {
//                resultCallBackTokeninValid(retObj);// token无效
//            } else {
//                resultErrorCallBack(retObj);
//                ErrorCallBack.processExc(retObj.getString("errorCode"), retObj.getString("errorMessage"), context);
                return;



    }

    /**
     * 子类需要覆盖该方法进行自己的逻辑操作
     *
     * @param result
     */
    protected void resultCallBack(JSONObject result) {

    }

    /**
     * 子类需要覆盖该方法进行自己的逻辑操作(目前只用于登陆时记录错误次数，出验证码的功能)
     *
     * @param result
     */
    protected void resultErrorCallBack(JSONObject result) {

    }

    /**
     * 子类需要覆盖该方法进行自己的逻辑操作
     */
    public void onFail(String msg) {

    }

    /**
     * 子类需要覆盖该方法进行自己的逻辑操作
     *
     * @param result
     */
    protected void resultCallBackTokeninValid(JSONObject result) {
//        UappHelper.needReLogin(context, false);
    }

    private void showLoadingProgressDialog() {
        showProgressDialog(getLoadingMsg());
    }

    @SuppressWarnings("deprecation")
    private void showProgressDialog(String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }

//        loading = new Loading(context, R.style.loadingDialogTheme, message);
//
//        LayoutParams params = loading.getWindow().getAttributes();
//
//        ((Activity) context).getWindow().setFlags(LayoutParams.FLAG_BLUR_BEHIND, LayoutParams.FLAG_BLUR_BEHIND);
//        params.alpha = 1.0f;// 透明度
//        loading.getWindow().setAttributes(params);
//
//        loading.show();


        loading = LoadingDialogUtil.createLoading(context,message);
        loading.show();

    }

    /**
     * 隐藏进度条
     */
    private void dismissProgressDialog() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }

}
