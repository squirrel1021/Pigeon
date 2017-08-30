package com.july.pigeon.adapter.holder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.july.pigeon.R;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.bean.Pigeon;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class PigeonHolder extends BaseViewHolder<Pigeon> {
    private TextView pigeonId;
    private TextView pigeonName;
    public PigeonHolder(ViewGroup parent) {
        super(parent, R.layout.my_pigeon_item);
        pigeonId = $(R.id.pigeonId);
        pigeonName = $(R.id.pigeonName);
    }

    @Override
    public void setData(final Pigeon pigeon) {
        pigeonId.setText(pigeon.getDoveCode());
        pigeonName.setText(pigeon.getName());
    }
}
