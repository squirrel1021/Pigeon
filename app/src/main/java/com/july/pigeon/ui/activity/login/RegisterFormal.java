package com.july.pigeon.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.july.pigeon.R;
import com.july.pigeon.engine.task.MainTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.util.ActivityStartUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/6/18 0018.
 */

public class RegisterFormal extends Activity implements View.OnClickListener {
    @BindView(R.id.smscode)
    EditText smscode;
    @BindView(R.id.nickEt)
    EditText nickEt;
    @BindView(R.id.pswEt)
    EditText pswEt;
    @BindView(R.id.pswAgain)
    EditText pswAgain;
    private String phoneNum = "";
    MainTask mainTask=new MainTask();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_formal);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        phoneNum = getIntent().getStringExtra("phoneNum");
        ((Button) findViewById(R.id.doneBtn)).setOnClickListener(this);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 注册
        if (EventUtils.isValid(eventByTag, EventTagConfig.regiest, null)) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
            mainTask.login(this,phoneNum,pswEt.getText().toString().trim());
        }
        // 登陆
        if (EventUtils.isValid(eventByTag, EventTagConfig.login, null)) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
            ActivityStartUtil.start(this,HomeActivity.class);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneBtn:
                mainTask.regiest(this, phoneNum, smscode.getText().toString().trim(), pswEt.getText().toString().trim(), nickEt.getText().toString().trim());
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
