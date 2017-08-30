package com.july.pigeon.adapter.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.july.pigeon.R;
import com.july.pigeon.bean.Jiaohuan;
import com.july.pigeon.bean.Pigeon;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class JiaohuanHolder extends BaseViewHolder<Jiaohuan> {
    private TextView pigeonId;
    private TextView pigeonName;
    public JiaohuanHolder(ViewGroup parent) {
        super(parent, R.layout.my_pigeon_item);
        pigeonId = $(R.id.pigeonId);
        pigeonName = $(R.id.pigeonName);
    }

    @Override
    public void setData(final Jiaohuan jiaohuan ) {
        pigeonId.setText(jiaohuan.getRingCode());
        pigeonId.setTextColor(Color.RED);
        pigeonName.setVisibility(View.GONE);
    }
}
