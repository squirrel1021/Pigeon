package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.july.pigeon.R;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.LoginActivity;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 昵称修改
 * Created by Administrator on 2017/6/27 0027.
 */

public class UpdateNickName extends BaseActivity {
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView titleTv;
    @BindView(R.id.nickEt)
    EditText nickEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_nickname);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("昵称");
        leftTv.setVisibility(View.VISIBLE);
        rightTv.setText("保存");
        rightTv.setVisibility(View.VISIBLE);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 修改昵称
        if (EventUtils.isValid(eventByTag, EventTagConfig.updateNickName, null)) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @OnClick(R.id.right_tv)
    public void saveNickName() {
        if (!StringUtils.isEmpty(nickEt.getText() + "".trim())) {
            new UserTask().upadteNickName(this, nickEt.getText() + "".trim());
        } else {
            BasicTool.showToast(this, "请输入昵称");
        }

    }
}
