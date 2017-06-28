package com.july.pigeon.engine;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.july.pigeon.util.BasicTool;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.util.HashMap;

import cz.msebera.android.httpclient.protocol.HTTP;

//import org.apache.http.protocol.HTTP;

/**
 * @author user 请求封装基类
 */
public class RequestUtil {

	private static final String TAG = RequestUtil.class.getSimpleName();

	/**
	 * 超时时间
	 */
	private static final int TIME_OUT = 15 * 1000;
	/**
	 * 最多连接次数
	 */
	private static final int MAX_CONNECTIONS = 2;

	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(TIME_OUT);

		client.setMaxConnections(MAX_CONNECTIONS);
	}

	/**
	 * 发送Post请求,以Json形式返回数据
	 * 
	 * @param context
	 *            上下文对象
	 * @param relativeUrl
	 *            相对路径

	 *            回调函数
	 */
	public static void postRequest(Context context, String relativeUrl, RequestParams mParams, HefansCallBack asyncCallBack) {

		if (!BasicTool.isNetworkConnected(context)) {
			Toast.makeText(context, "抱歉，您的网络不通呢", Toast.LENGTH_SHORT).show();
			asyncCallBack.onFail("");
		} else {
			Log.i("TAG", relativeUrl + mParams);
			client.addHeader("charset", "UTF-8");
			client.post(relativeUrl,mParams,asyncCallBack);
		}
	}

	/**
	 * 发送GET请求,以Json形式返回数据
	 * 
	 * @param context
	 *            上下文对象
	 * @param relativeUrl
	 *            相对路径

	 *            回调函数
	 */
	public static void getRequest(Context context, String relativeUrl, String mParams, HefansCallBack asyncCallBack) {

		if (!BasicTool.isNetworkConnected(context)) {
			Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
			asyncCallBack.onFail("");
		} else {

			Log.i(TAG, relativeUrl + mParams);
			String requestUrl = relativeUrl;
			Log.i("HFurl",requestUrl);
			client.addHeader("charset", "UTF-8");
			client.get(requestUrl, asyncCallBack);
		}

	}

}
