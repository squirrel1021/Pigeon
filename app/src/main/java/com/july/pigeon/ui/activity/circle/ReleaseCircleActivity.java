package com.july.pigeon.ui.activity.circle;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andsync.xpermission.XPermissionUtils;
import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.adapter.holder.CommViewHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.CircleTask;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.ForgetUpdatePsw;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.ListUtils;
import com.july.pigeon.util.SharedPreferencesUtil;
import com.july.pigeon.util.StringUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ImgLoader;
import com.pizidea.imagepicker.UilImgLoader;
import com.pizidea.imagepicker.bean.ImageItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class ReleaseCircleActivity extends BaseActivity {
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView titleTv;
    @BindView(R.id.imageGv)
    GridView gv;
    @BindView(R.id.circleInfo)
    EditText circleInfo;
    private BaseListAdapter adapter;
    private List<ImageItem> itemsList = new ArrayList<ImageItem>();
    private List<File> newFileList = new ArrayList<File>();
    ImgLoader presenter = new UilImgLoader();
    private List<String> UrlList = new ArrayList<String>();
    private Circle cricle = new Circle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_circle);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        leftTv.setVisibility(View.VISIBLE);
        rightTv.setVisibility(View.VISIBLE);
        titleTv.setText("社区");
        setAdapter();
    }

    @OnClick(R.id.right_tv)
    public void release() {
        compressImage();
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 上传图片
        if (EventUtils.isValid(eventByTag, EventTagConfig.uploadImg, null)) {
            cricle = new GsonParser().parseObject(eventByTag.getObj() + "", Circle.class);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < cricle.getImgUrls().size(); i++) {
                if (i == cricle.getImgUrls().size() - 1) {
                    stringBuffer.append(cricle.getImgUrls().get(i));
                } else {
                    stringBuffer.append(cricle.getImgUrls().get(i));
                    stringBuffer.append(";");
                }

            }
//            String[] arr = (String[]) cricle.getImgUrls().toArray(new String[cricle.getImgUrls().size()]);//使用了第二种接口，返回值和参数均为结果
            if (!StringUtils.isEmpty(circleInfo.getText() + "".trim())) {
                new CircleTask().releaseCircle(this, circleInfo.getText() + "".trim(), stringBuffer.toString());
            } else {
                BasicTool.showToast(this, "请输入内容");
            }

        }
        if (EventUtils.isValid(eventByTag, EventTagConfig.releaseCircle, null)) {
            BasicTool.showToast(this, "发布成功");
            EventBus.getDefault().post(EventByTag.setDefault("", "reflush_circle"));
            finish();
        }
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new BaseListAdapter(this, itemsList, R.layout.hefans_theme_girdview_item) {
                @Override
                public int getCount() {
                    if (itemsList.size() == 9) {
                        return 9;
                    }
                    return itemsList.size() + 1;
                }

                @Override
                public void convert(CommViewHolder holder) {
                    ImageView addImage = holder.getView(R.id.addImage);
                    RelativeLayout imageShowView = holder.getView(R.id.imageShowView);
                    LinearLayout addMoreLayout = holder.getView(R.id.addMoreLayout);
                    ImageView image = holder.getView(R.id.theme_image);
                    if (itemsList.size() != holder.getPosition()) {
//                        //本地文件
//                        File file = new File(Environment.getExternalStorageDirectory(), itemsList.get(holder.getPosition()).path);
//                        //加载图片
//                        Glide.with(ReleaseCircleActivity.this).load(file).into(image);
                        presenter.onPresentImage(image, itemsList.get(holder.getPosition()).path, 80);
                    }
                    if (holder.getPosition() == itemsList.size()) {
                        addMoreLayout.setVisibility(View.VISIBLE);
                        imageShowView.setVisibility(View.GONE);
                    } else {
                        imageShowView.setVisibility(View.VISIBLE);
                        addMoreLayout.setVisibility(View.GONE);
                    }
                    addImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jumpSelectImageView();
                        }
                    });
                }
            };
            gv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    private void jumpSelectImageView() {
        XPermissionUtils.requestPermissions(this, 1, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        selectImage();
                    }

                    @Override
                    public void onPermissionDenied(final String[] deniedPermissions, boolean alwaysDenied) {
                        Toast.makeText(ReleaseCircleActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        if (alwaysDenied) { // 拒绝后不再询问 -> 提示跳转到设置
                            new AlertDialog.Builder(ReleaseCircleActivity.this).setTitle("温馨提示")
                                    .setMessage("我们需要读写权限才能正常使用该功能")
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("打开权限", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                            XPermissionUtils.requestPermissionsAgain(ForgetPassWordActivity.this, deniedPermissions,
//                                                    1);
                                            Uri packageURI = Uri.parse("package:" + ReleaseCircleActivity.this.getPackageName());
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

    private void selectImage() {
        AndroidImagePicker.getInstance().setSelectLimit(9 - itemsList.size());
        AndroidImagePicker.getInstance().pickMulti(ReleaseCircleActivity.this, true, new AndroidImagePicker.OnImagePickCompleteListener() {
            @Override
            public void onImagePickComplete(List<ImageItem> items) {
                if (items != null && items.size() > 0) {
                    Log.i("onImagePickComplete", "=====选择了：" + items.get(0).path);
                    AndroidImagePicker.clearInstance();
                    itemsList.addAll(items);

                    setAdapter();
                }
            }
        });
    }

    private void compressImage() {

        for (int i = 0; i < itemsList.size(); i++) {
            File file = new File(itemsList.get(i).path);
            final int finalI = i;
            Luban.with(this)

                    .load(file)                     //传人要压缩的图片
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            newFileList.add(file);
                            if (finalI == itemsList.size() - 1) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ListUtils.isEmpty(newFileList)) {
                                            if (!StringUtils.isEmpty(circleInfo.getText() + "".trim())) {
                                                String[] arr = {};
                                                new CircleTask().releaseCircle(ReleaseCircleActivity.this, circleInfo.getText() + "".trim(), "");
                                            } else {
                                                BasicTool.showToast(ReleaseCircleActivity.this, "请输入内容");
                                            }
                                        } else {
                                            File[] arr = newFileList.toArray(new File[newFileList.size()]);//使用了第二种接口，返回值和参数均为结果
                                            try {
                                                new CircleTask().uploadImg(ReleaseCircleActivity.this, "apptoken " + SharedPreferencesUtil.getData(ReleaseCircleActivity.this, "token", ""), arr);
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }, 300);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用

                            Log.i("sdfsd", e.toString());
                        }
                    }).launch();    //启动压缩
        }
    }

}
