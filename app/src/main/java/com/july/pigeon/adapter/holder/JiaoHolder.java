package com.july.pigeon.adapter.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.ui.weight.MyGridView;
import com.july.pigeon.util.GlideUtil;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class JiaoHolder extends BaseViewHolder<Circle> {
    private TextView tv_item;


    public JiaoHolder(ViewGroup parent) {
        super(parent, R.layout.jiao_item);
        tv_item = $(R.id.tv_item);
    }

    @Override
    public void setData(final Circle circle) {
        tv_item.setText(circle.getThemeInfo());
    }
}
