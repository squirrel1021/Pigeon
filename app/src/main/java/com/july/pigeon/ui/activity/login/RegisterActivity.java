package com.july.pigeon.ui.activity.login;

import android.app.Activity;
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
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.util.ActivityStartUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ((Button) findViewById(R.id.yzmBtn)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yzmBtn:
                getsmsCode();
//                ActivityStartUtil.start(this,RegisterFormal.class);
//                finish();
                break;
            default:
                break;
        }
    }

    private void getsmsCode() {
        RequestUtil.postRequest(this, ConstantValues.smsCode, RequestParam.smsCode(phoneEd.getText().toString().trim()), new BaseResponse(this,"加载中") {
            @Override
            public void onFailure(String message) {
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                Log.i("messagesdfds",message);
            }

            @Override
            public void onSuccess(String result) {
                Toast.makeText(RegisterActivity.this,"验证码发送成功",Toast.LENGTH_LONG).show();
                Log.i("resultdsf",result);
            }
        });
    }
}
