package com.july.pigeon.engine;


import android.content.Context;
import android.view.WindowManager;

import com.july.pigeon.R;


public class LoadingDialogUtil {
    public static Loading createLoading(Context context,String message){
        Loading loading = new Loading(context, R.style.loadingDialogTheme, message);

        WindowManager.LayoutParams params = loading.getWindow().getAttributes();
//
        //设置背景模糊后，会影响 班车 地图查询 功能
//        ((Activity) context).getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        params.alpha = 0.6f;// 透明度
        loading.getWindow().setAttributes(params);
        return loading;
    }



}
