package com.july.pigeon.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.ui.activity.circle.CircleConment;
import com.july.pigeon.ui.weight.MyGridView;
import com.july.pigeon.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class CircleConmentHolder extends BaseViewHolder<Circle> {
    private TextView mTv_name, releaseTime;
    private TextView mTv_sign;
    private LinearLayout touch_layout;


    public CircleConmentHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.circle_conment_item);
        mTv_name = $(R.id.userName);
        releaseTime = $(R.id.replyTime);
        mTv_sign = $(R.id.circleInfo);
        touch_layout = $(R.id.touch_layout);
    }

    @Override
    public void setData(final Circle circle) {
        releaseTime.setText(circle.getFdCreateTime());
        mTv_name.setText(circle.getMemberName());
        mTv_sign.setText(circle.getFdContent());
    }
}
