package com.july.pigeon.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.july.pigeon.R;
import com.july.pigeon.engine.BaseResponse;
import com.july.pigeon.engine.ConstantValues;
import com.july.pigeon.engine.RequestParam;
import com.july.pigeon.engine.RequestUtil;
import com.july.pigeon.engine.task.MainTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/6/18 0018.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.phoneEd)
    EditText phoneEd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.yzmBtn).setOnClickListener(this);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 验证码
        if (EventUtils.isValid(eventByTag, EventTagConfig.sms_code, null)) {
            Toast.makeText(this, "发送成功", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,RegisterFormal.class);
            intent.putExtra("phoneNum",phoneEd.getText().toString().trim());
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yzmBtn:
                if(BasicTool.isCellphone(phoneEd.getText().toString().trim())){
                    new MainTask().getsmsCode(this, phoneEd.getText().toString().trim());
                }else{
                    Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                }

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
