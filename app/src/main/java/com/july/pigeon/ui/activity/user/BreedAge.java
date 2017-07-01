package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.WheelPicker;
import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**鸽龄
 * Created by Administrator on 2017/7/1 0001.
 */

public class BreedAge extends BaseActivity{
    @BindView(R.id.touchView)LinearLayout touchView;
    @BindView(R.id.wheelPicker)WheelPicker picker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.breed_age);
        ButterKnife.bind(this);

    }
}
