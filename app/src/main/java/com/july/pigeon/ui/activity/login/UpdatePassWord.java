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
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**修改密码
 * Created by Administrator on 2017/9/4 0004.
 */

public class UpdatePassWord extends BaseActivity {
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView title;
    @BindView(R.id.oldPsw)
    EditText oldPsw;
    @BindView(R.id.newPsw)
    EditText newPsw;
    @BindView(R.id.checkPsw)
    EditText checkPsw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        leftTv.setVisibility(View.VISIBLE);
        title.setText("修改密码");
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 验证手机号
        if (EventUtils.isValid(eventByTag, EventTagConfig.updatePassword, null)) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
            ActivityStartUtil.start(this, LoginActivity.class);
        }
    }

    @OnClick(R.id.next_step)
    public void updatePsw() {
        if (!StringUtils.isEmpty(newPsw.getText() + "".trim()) && !StringUtils.isEmpty(checkPsw.getText() + "".trim()) && !StringUtils.isEmpty(oldPsw.getText() + "".trim())) {
            if (StringUtils.isEquals(newPsw.getText() + "".trim(), checkPsw.getText().toString().trim())) {
                new MainTask().updatePassword(this, newPsw.getText() + "".trim(), oldPsw.getText() + "".trim());
            } else {
                BasicTool.showToast(this, "密码不一致,请重新输入");
            }

        } else {
            BasicTool.showToast(this, "请输入密码再进行操作");
        }

    }

    @OnClick(R.id.left_tv)
    public void back() {
        finish();
    }
}
