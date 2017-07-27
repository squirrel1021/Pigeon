package com.july.pigeon.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class CircleHolder extends BaseViewHolder<Circle> {
    private TextView mTv_name,releaseTime;
    private TextView mTv_sign;
    private Context mContext;
    private MyGridView gv;
    private BaseListAdapter adapter;
private LinearLayout touch_layout;

    public CircleHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.circle_item);
        mTv_name = $(R.id.userName);
        releaseTime= $(R.id.releaseTime);
        mTv_sign = $(R.id.circleInfo);
        gv = $(R.id.circleGv);
        touch_layout=$(R.id.touch_layout);
        mContext = context;

    }

    @Override
    public void setData(final Circle circle) {
        releaseTime.setText(circle.getFdCreateTime());
        mTv_name.setText(circle.getMemberName());
        mTv_sign.setText(circle.getFdContent());
        adapter = new BaseListAdapter(mContext,circle.getFdImgUrl(),R.layout.circle_grid_item) {
            @Override
            public void convert(CommViewHolder holder) {
                ImageView image=holder.getView(R.id.theme_image);
                GlideUtil.getInstance().loadImageView(mContext,circle.getFdImgUrl().get(holder.getPosition()),image);
            }
        };
        touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CircleConment.class);
                intent.putExtra("id",circle.getFdId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("circle", circle);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        gv.setAdapter(adapter);
    }
}
