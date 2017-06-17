package com.july.pigeon.adapter.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.ui.weight.MyGridView;
import com.july.pigeon.util.GlideUtil;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class CircleHolder extends BaseViewHolder<Circle> {
    private TextView mTv_name;
    private TextView mTv_sign;
    private Context mContext;
    private MyGridView gv;
    private BaseListAdapter adapter;


    public CircleHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.circle_item);
        mTv_name = $(R.id.userName);
        mTv_sign = $(R.id.circleInfo);
        gv = $(R.id.circleGv);
        mContext = context;

    }

    @Override
    public void setData(final Circle circle) {

        mTv_name.setText(circle.getUsername());
        mTv_sign.setText(circle.getThemeInfo());
        adapter = new BaseListAdapter(mContext,circle.getImageurl(),R.layout.circle_grid_item) {
            @Override
            public void convert(CommViewHolder holder) {
                ImageView image=holder.getView(R.id.theme_image);
                GlideUtil.getInstance().loadImageView(mContext,circle.getImageurl().get(holder.getPosition()),image);
            }
        };
        gv.setAdapter(adapter);
    }
}
