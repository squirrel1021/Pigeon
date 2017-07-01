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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.july.pigeon.R;
import com.july.pigeon.bean.Token;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.MainTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.ui.activity.main.WelcomeActivity;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.SharedPreferencesUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/6/17 0017.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private Button btn;
    private EditText phoneNum, pswEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.loginBtn);
        btn.setOnClickListener(this);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        pswEt = (EditText) findViewById(R.id.pswEt);
        ((TextView) findViewById(R.id.regiest)).setOnClickListener(this);
        ((TextView) findViewById(R.id.forget_psw)).setOnClickListener(this);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 登陆
        if (EventUtils.isValid(eventByTag, EventTagConfig.login, null)) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
            Token token=new GsonParser().parseObject(eventByTag.getObj()+"",Token.class);
            SharedPreferencesUtil.saveData(this,"token",token.getAccess_token());
            ActivityStartUtil.start(this, HomeActivity.class);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                new MainTask().login(this, phoneNum.getText().toString().trim(), pswEt.getText().toString().trim());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
