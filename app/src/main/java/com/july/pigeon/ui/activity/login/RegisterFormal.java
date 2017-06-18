package com.july.pigeon.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.util.ActivityStartUtil;

/**
 * Created by Administrator on 2017/6/18 0018.
 */

public class RegisterFormal extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_formal);
        initView();
    }

    private void initView() {
        ((Button)findViewById(R.id.doneBtn)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneBtn:
                ActivityStartUtil.start(this,HomeActivity.class);
                finish();
                break;
            default:
                break;
        }
    }
}
