package com.july.pigeon.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class ForgetPassWordActivity extends BaseActivity {
    @BindView(R.id.title_center)
    public TextView title;
    @BindView(R.id.left_tv)
    public TextView leftTv;
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
}
