package com.july.pigeon.ui.activity.user;

import android.os.Bundle;
import android.os.Handler;
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
import com.july.pigeon.adapter.holder.CircleHolder;
import com.july.pigeon.adapter.holder.PigeonHolder;
import com.july.pigeon.bean.Circle;
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

public class MyPigeon extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @BindView(R.id.left_tv)
    TextView leftTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.title_center)
    TextView titleTv;
    private RecyclerArrayAdapter<Pigeon> adapter;
    List<Pigeon> list = new ArrayList<Pigeon>();


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
        new UserTask().myPigeon(this);
    }

    private void initView() {
        leftTv.setVisibility(View.VISIBLE);
        rightTv.setVisibility(View.VISIBLE);
        titleTv.setText("我的鸽子");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.color_ee), UiUtil.dip2px(this, 0.5f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Pigeon>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new PigeonHolder(parent);
            }
        });
//        adapter.setMore(R.layout.view_more, this);
//        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
        adapter.addAll(list);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
//我的鸽子
        if (EventUtils.isValid(eventByTag, EventTagConfig.mypigeon, null)) {
            list = new GsonParser().parseList(eventByTag.getObj() + "", new TypeToken<List<Pigeon>>() {
            });
            adapter.clear();
            adapter.addAll(list);
        }
    }

    @OnClick(R.id.right_tv)
    public void addPigeon() {
        ActivityStartUtil.start(this, AddPigeon.class);
    }

    @Override
    public void onRefresh() {
        new UserTask().myPigeon(this);
    }

    @Override
    public void onLoadMore() {

    }
}
