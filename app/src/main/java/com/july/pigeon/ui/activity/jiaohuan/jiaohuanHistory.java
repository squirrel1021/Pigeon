package com.july.pigeon.ui.activity.jiaohuan;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.july.pigeon.R;
import com.july.pigeon.bean.Jiaohuan;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.MainTask;
import com.july.pigeon.engine.task.PigeonTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/3 0003.
 */

public class jiaohuanHistory extends BaseActivity {
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView titleTv;
    private String ringCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaohuan_history);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ringCode = getIntent().getStringExtra("code");
        Toast.makeText(this, ringCode, Toast.LENGTH_SHORT).show();
//        new MainTask().getImei(this, ringCode);
        new PigeonTask().getUpData(this, ringCode);
        leftTv.setVisibility(View.VISIBLE);
        titleTv.setText("历史轨迹");
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private Handler mHandler;
    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        //脚环数据
        if (EventUtils.isValid(eventByTag, EventTagConfig.getUpData, null)) {
            Toast.makeText(this, eventByTag.getObj() + "", Toast.LENGTH_SHORT).show();
            Log.i("getUpData",eventByTag.getObj() + "");
        }
        //获取实时脚环上一个坐标
        if (EventUtils.isValid(eventByTag, EventTagConfig.getLastUpData, null)) {

            Log.i("getLastUpData",eventByTag.getObj() + "");
            Toast.makeText(this, eventByTag.getObj() + "", Toast.LENGTH_SHORT).show();
        }
        //换imei码
        if (EventUtils.isValid(eventByTag, EventTagConfig.getImei, null)) {
//            Toast.makeText(this, eventByTag.getObj() + "", Toast.LENGTH_SHORT).show();
            try {
                final String imeiCode = new JSONObject(eventByTag.getObj() + "").getString("eid");
                mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        // TODO Auto-generated method stub
                        Toast.makeText(jiaohuanHistory.this, imeiCode , Toast.LENGTH_SHORT).show();
                        new PigeonTask().getLastPoint(jiaohuanHistory.this, imeiCode);
                        new PigeonTask().getUpData(jiaohuanHistory.this, imeiCode);
                        mHandler.postDelayed(this, 3000);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
