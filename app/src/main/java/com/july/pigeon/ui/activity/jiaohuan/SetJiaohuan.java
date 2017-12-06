package com.july.pigeon.ui.activity.jiaohuan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.ForgetPassWordActivity;
import com.july.pigeon.util.ActivityStartUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**设置脚环
 * Created by Administrator on 2017/11/1 0001.
 */

public class SetJiaohuan extends BaseActivity {
    @BindView(R.id.title_center)
    public TextView title;
    @BindView(R.id.left_tv)
    public TextView leftTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_jiaohuan);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("忘记密码");
        leftTv.setText("取消");
        leftTv.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.left_tv)
    public void finishs() {
        finish();
    }

}
