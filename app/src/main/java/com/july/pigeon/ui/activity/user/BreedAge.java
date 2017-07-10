package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.july.pigeon.R;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.util.BasicTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 鸽龄
 * Created by Administrator on 2017/7/1 0001.
 */

public class BreedAge extends BaseActivity implements WheelPicker.OnItemSelectedListener {
    @BindView(R.id.touchView)
    LinearLayout touchView;
    @BindView(R.id.wheelPicker)
    WheelPicker picker;
    @BindView(R.id.ageTv)
    TextView ageTv;
    @BindView(R.id.left_tv)
    TextView left_tv;
    @BindView(R.id.right_tv)
    TextView right_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breed_age);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        left_tv.setVisibility(View.VISIBLE);
        right_tv.setVisibility(View.VISIBLE);
        List<String> data = new ArrayList<String>();
        data.add("1~2年");
        data.add("2~5年");
        data.add("5~10年");
        data.add("10~20年");
        picker.setData(data);
        picker.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.touchView)
    public void click() {
        picker.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.right_tv)
    public void save() {
        new UserTask().upadteage(this, ageTv.getText() + "");
    }
    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 荣誉
        if (EventUtils.isValid(eventByTag, EventTagConfig.updateage, null)) {
            BasicTool.showToast(this, "修改成功");
            finish();
        }
    }
    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        ageTv.setText(String.valueOf(data));
    }
}
