package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.july.pigeon.R;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.CircleTask;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class AddPigeon extends BaseActivity {
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView titleTv;
    @BindView(R.id.editText1)
    EditText edittext1;
    @BindView(R.id.editText2)
    EditText edittext2;
    @BindView(R.id.editText3)
    EditText edittext3;
    @BindView(R.id.editText4)
    EditText edittext4;
    @BindView(R.id.editText5)
    EditText edittext5;
    @BindView(R.id.editText6)
    EditText edittext6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pigeon);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        leftTv.setVisibility(View.VISIBLE);
        rightTv.setVisibility(View.VISIBLE);
        titleTv.setText("添加鸽子");
        rightTv.setText("完成");

    }

    @OnClick(R.id.right_tv)
    public void release() {
        new UserTask().addPigeon(this, edittext1.getText() + "".trim(), edittext2.getText() + "".trim(), edittext3.getText() + "".trim(), edittext4.getText() + "".trim(), edittext5.getText() + "".trim(), edittext6.getText() + "".trim());
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
//添加鸽子
        if (EventUtils.isValid(eventByTag, EventTagConfig.addpigeon, null)) {
            BasicTool.showToast(this, "添加成功");
            finish();
        }
    }
}
