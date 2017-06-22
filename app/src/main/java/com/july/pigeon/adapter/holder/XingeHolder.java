package com.july.pigeon.adapter.holder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.july.pigeon.R;
import com.july.pigeon.bean.Circle;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class XingeHolder extends BaseViewHolder<Circle> {
    private TextView tv_item;
    private ImageView iv_item;

    public XingeHolder(ViewGroup parent) {
        super(parent, R.layout.xinge_iten);
        tv_item = $(R.id.tv_item);
        iv_item = $(R.id.iv_item);
    }

    @Override
    public void setData(final Circle circle) {
        tv_item.setText(circle.getThemeInfo());
    }
}
