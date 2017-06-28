package com.july.pigeon.ui.activity.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.ui.activity.main.WelcomeActivity;
import com.july.pigeon.util.ActivityStartUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/17 0017.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.loginBtn);
        btn.setOnClickListener(this);
        ((TextView) findViewById(R.id.regiest)).setOnClickListener(this);
        ((TextView) findViewById(R.id.forget_psw)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                ActivityStartUtil.start(this, HomeActivity.class);
                finish();
                break;
            case R.id.regiest:
                ActivityStartUtil.start(this, RegisterActivity.class);
                break;
            case R.id.forget_psw:
                ActivityStartUtil.start(this, ForgetPassWordActivity.class);
                break;
            default:
                break;
        }
    }
}
