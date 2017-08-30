package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.july.pigeon.R;
import com.july.pigeon.adapter.holder.JiaohuanHolder;
import com.july.pigeon.adapter.holder.PigeonHolder;
import com.july.pigeon.bean.Jiaohuan;
import com.july.pigeon.bean.Pigeon;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class MyJiaohuan extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView titleTv;
    private RecyclerArrayAdapter<Jiaohuan> adapter;
    List<Jiaohuan> list = new ArrayList<Jiaohuan>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_pigeon);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UserTask().myJiaohuan(this);
    }
    @OnClick(R.id.right_tv)
    public void addPigeon() {
        ActivityStartUtil.start(this, AddJiaohuan.class);
    }
    private void initView() {
        leftTv.setVisibility(View.VISIBLE);
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("添加");
        titleTv.setText("我的脚环");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.color_ee), UiUtil.dip2px(this, 0.5f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Jiaohuan>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new JiaohuanHolder(parent);
            }
        });
        recyclerView.setRefreshListener(this);
        adapter.addAll(list);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
//我的脚环
        if (EventUtils.isValid(eventByTag, EventTagConfig.myjiaohuan, null)) {
            list = new GsonParser().parseList(eventByTag.getObj() + "", new TypeToken<List<Jiaohuan>>() {
            });
            adapter.clear();
            adapter.addAll(list);
        }
    }

    @Override
    public void onRefresh() {
        new UserTask().myJiaohuan(this);
    }

    @Override
    public void onLoadMore() {

    }
}
