package com.july.pigeon.ui.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.july.pigeon.R;
import com.july.pigeon.engine.task.MainTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码-修改密码
 * Created by Administrator on 2017/7/1 0001.
 */

public class ForgetUpdatePsw extends BaseActivity {
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView title;
    @BindView(R.id.phoneNum)
    EditText phoneNum;
    @BindView(R.id.smscode)
    EditText newPsw;
    private String phone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_update_psw);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        phone = getIntent().getStringExtra("phone");
        leftTv.setVisibility(View.VISIBLE);
        title.setText("修改密码");
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 验证手机号
        if (EventUtils.isValid(eventByTag, EventTagConfig.updatePsw, null)) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
            ActivityStartUtil.start(this, LoginActivity.class);
        }
    }

    @OnClick(R.id.next_step)
    public void updatePsw() {
        if (BasicTool.isCellphone(phone) || !StringUtils.isEmpty(newPsw.getText() + "".trim())) {
            if(StringUtils.isEquals(newPsw.getText() + "".trim(),phoneNum.getText().toString().trim())){
                new MainTask().updatePsw(this, phone, newPsw.getText().toString().trim());
            }else{
                BasicTool.showToast(this, "密码不一致,请重新输入");
            }

        } else {

        }

    }
}
