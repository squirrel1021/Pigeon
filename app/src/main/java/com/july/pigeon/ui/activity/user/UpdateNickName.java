package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
}
