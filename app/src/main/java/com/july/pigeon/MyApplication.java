package com.july.pigeon;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        initImageLoader(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
    public static void initImageLoader(Context context){
        if(!ImageLoader.getInstance().isInited()){
            ImageLoaderConfiguration config = null;
            if(BuildConfig.DEBUG){
                config = new ImageLoaderConfiguration.Builder(context)
						/*.threadPriority(Thread.NORM_PRIORITY - 2)
						.memoryCacheSize((int) (Runtime.getRuntime().maxMemory() / 4))
						.diskCacheSize(500 * 1024 * 1024)
						.writeDebugLogs()
						.diskCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO).build();*/

                        //.memoryCacheExtraOptions(200, 200)
                        //.diskCacheExtraOptions(200, 200, null).threadPoolSize(3)
                        .threadPriority(Thread.NORM_PRIORITY - 1)
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        //.denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
						/*.memoryCacheSize(20 * 1024 * 1024)*/
                        .memoryCacheSizePercentage(13)
                        .diskCacheSize(500 * 1024 * 1024)
                        //.imageDownloader(new BaseImageDownloader(A3App.getInstance().getApplicationContext()))
                        //.imageDecoder(new BaseImageDecoder(true))
                        //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        //.writeDebugLogs()
                        .build();
            }else{
                config = new ImageLoaderConfiguration.Builder(context)
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        .diskCacheSize(500 * 1024 * 1024)
                        .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                        .tasksProcessingOrder(QueueProcessingType.LIFO).build();
            }
            ImageLoader.getInstance().init(config);
        }

    }
}
