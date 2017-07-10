package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.july.pigeon.R;
import com.july.pigeon.bean.User;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class SetHonor extends BaseActivity {
    @BindView(R.id.nickEt)
    EditText honorEt;
    @BindView(R.id.title_center)
    TextView title;
    @BindView(R.id.left_tv)
    TextView left_tv;
    @BindView(R.id.right_tv)
    TextView right_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_nickname);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("荣誉");
        right_tv.setText("保存");
        right_tv.setVisibility(View.VISIBLE);
        left_tv.setVisibility(View.VISIBLE);
        honorEt.setHint("请输入荣誉资料(10字符以内)");
        honorEt.setMaxEms(10);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 荣誉
        if (EventUtils.isValid(eventByTag, EventTagConfig.honor, null)) {
            BasicTool.showToast(this, "修改成功");
            finish();
        }
    }

    @OnClick(R.id.left_tv)
    public void back() {
        finish();
    }

    @OnClick(R.id.right_tv)
    public void save() {
        if (!StringUtils.isEmpty(honorEt.getText() + "".trim())) {
            new UserTask().honor(this, honorEt.getText() + "".trim());
        } else {
            BasicTool.showToast(this, "请输入荣誉");
        }

    }
}
