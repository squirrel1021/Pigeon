package com.july.pigeon.ui.activity.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.july.pigeon.R;
import com.july.pigeon.engine.task.CircleTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.util.BasicTool;

import butterknife.ButterKnife;

/**
 * 评论列表
 * Created by Administrator on 2017/7/24 0024.
 */

public class CircleConment extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conments_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String id = getIntent().getStringExtra("id");

        new CircleTask().conments(this, 0, 10, 0, id);
    }

    public void onEventMainThread(EventByTag eventByTag) {
        // 评论列表
        if (EventUtils.isValid(eventByTag, EventTagConfig.conments, null)) {
            BasicTool.showToast(this, eventByTag.getObj()+"");
        }
    }
}
