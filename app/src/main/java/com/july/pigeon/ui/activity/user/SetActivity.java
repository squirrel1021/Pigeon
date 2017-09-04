package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.LoginActivity;
import com.july.pigeon.ui.activity.login.UpdatePassWord;
import com.july.pigeon.util.ActionBarControl;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class SetActivity extends BaseActivity {
    @BindView(R.id.titleView)
    ViewGroup titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_about);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        new ActionBarControl(this, titleView).setNoRightNavigation("设置");
    }

    @OnClick(R.id.login_out)
    public void loginOut() {
        SharedPreferencesUtil.saveData(this, "token", "");
        ActivityStartUtil.start(this, LoginActivity.class);
        finish();
    }

    @OnClick(R.id.updateLayout)
    public void updatePsw() {
        ActivityStartUtil.start(this, UpdatePassWord.class);
    }
}
