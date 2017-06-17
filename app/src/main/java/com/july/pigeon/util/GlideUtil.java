package com.july.pigeon.util;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.july.pigeon.R;

/**
 * Created by ANDROID on 2017/4/24.
 */

public class GlideUtil {
    public static GlideUtil instance;

    private static int headOptions = 0;
    private static int advOptions = 1;
    private static int rectLogoOptions = 2;
    private static int circleLogoOptions = 3;
    private static int preOptions = 4;


    public static GlideUtil getInstance() {
        if (instance == null) {
            instance = new GlideUtil();
        }
        return instance;
    }
    /**
     *Constants.options = 0;//普通图片的，为正方形
     Constants.headOptions = 1;//会员头像模式值
     Constants.advOptions = 2;//广告模式值
     Constants.preOptions = 3;//图片查看界面黑色背景图
     Constants.rectLogoOptions = 4;//正方形logo模式值
     Constants.circleLogoOptions = 5;//圓形logo模式值
     */
    public void loadImageView(Context context, String url, ImageView imageView) {

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.head_portrait1)
                .error(R.drawable.head_portrait1)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
//                        Picasso.with(context).load(s).placeholder(R.drawable.head_portrait1).into(imageView);
    }

}
