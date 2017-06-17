/**
 * Copyright 2014 caregg Inc. All rights reserved.
 * - Powered by Team GOF. -
 */

package com.july.pigeon.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Kevinliu
 * @ClassName: ActivityStartUtil
 * @Description: 跳转activity工具类
 * @date 2015年1月20日 下午4:51:09
 */

public class ActivityStartUtil {

    public static void start(Activity context, String fullActivityName) {
        startActivityByIntent(context, fullActivityName, new Intent());
        //这里添加动画
    }

    public static void startActivityByIntent(Activity context, String fullActivityName, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(), fullActivityName);
        intent.setComponent(comp);
        context.startActivity(intent);
        //这里添加动画
    }

    public static void startActivityByIntent(Activity context, Class<?> clazz, Intent intent) {
        intent.setClass(context, clazz);
        context.startActivity(intent);
        //这里添加动画
    }

    public static void startActivityWithSerialize(Class<?> clazz, Activity activity, Serializable serialize) {
        Intent intent = new Intent(activity, clazz);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Serialize", serialize);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        //这里添加动画
    }

    public static void startActivityWithParcelable(Class<?> clazz, Activity activity, Parcelable parcelable) {
        Intent intent = new Intent(activity, clazz);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Parcelable", parcelable);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        //这里添加动画
    }

    public static void start(Activity context, Class<?> clazz) {
        context.startActivity(new Intent(context, clazz));
        //context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void startByFade(Activity context, Class<?> clazz) {
        context.startActivity(new Intent(context, clazz));
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
