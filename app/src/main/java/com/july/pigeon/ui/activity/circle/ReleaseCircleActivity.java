package com.july.pigeon.ui.activity.circle;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.bumptech.glide.Glide;
import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.adapter.holder.CommViewHolder;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.ForgetPassWordActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ImgLoader;
import com.pizidea.imagepicker.UilImgLoader;
import com.pizidea.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private BaseListAdapter adapter;
    private List<ImageItem> itemsList = new ArrayList<ImageItem>();
    ImgLoader presenter = new UilImgLoader();

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

    private void setAdapter() {
        if(adapter==null){
            adapter=new BaseListAdapter(this,itemsList,R.layout.hefans_theme_girdview_item) {
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
        }else{
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
}
