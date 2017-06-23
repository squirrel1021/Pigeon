package com.july.pigeon.ui.activity.login;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andsync.xpermission.XPermissionUtils;
import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class ForgetPassWordActivity extends BaseActivity {
    @BindView(R.id.title_center)
    public TextView title;
    @BindView(R.id.left_tv)
    public TextView leftTv;
    private static int REQUEST_CODE=5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_psd_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("忘记密码");
        leftTv.setText("取消");
        leftTv.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.next_step)
    public void nextStep() {
        XPermissionUtils.requestPermissions(this, 1, new String[]{Manifest.permission.CAMERA},
                new XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        selectImage();
                    }

                    @Override
                    public void onPermissionDenied(final String[] deniedPermissions, boolean alwaysDenied) {
                        Toast.makeText(ForgetPassWordActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        if (alwaysDenied) { // 拒绝后不再询问 -> 提示跳转到设置
                            new AlertDialog.Builder(ForgetPassWordActivity.this).setTitle("温馨提示")
                                    .setMessage("我们需要摄像头权限才能正常使用该功能")
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("打开权限", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                            XPermissionUtils.requestPermissionsAgain(ForgetPassWordActivity.this, deniedPermissions,
//                                                    1);
                                            Uri packageURI = Uri.parse("package:" + ForgetPassWordActivity.this.getPackageName());
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
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(ForgetPassWordActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
