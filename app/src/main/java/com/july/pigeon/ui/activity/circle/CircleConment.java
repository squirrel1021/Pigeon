package com.july.pigeon.ui.activity.circle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.july.pigeon.R;
import com.july.pigeon.adapter.holder.CircleConmentHolder;
import com.july.pigeon.adapter.holder.CircleHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.CircleTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.UiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 评论列表
 * Created by Administrator on 2017/7/24 0024.
 */

public class CircleConment extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Circle> adapter;
    List<Circle> list = new ArrayList<Circle>();
    private int currentPage = 0;
    private int totalPage = 1;
    private LayoutInflater inflater;
    private Circle lastCircle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conments_layout);
        inflater = getLayoutInflater();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String id = getIntent().getStringExtra("id");
        lastCircle=(Circle)getIntent().getSerializableExtra("circle");
        new CircleTask().conments(this, 0, 10, 0, id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.color_ee), UiUtil.dip2px(this, 0.5f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Circle>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CircleConmentHolder(parent, CircleConment.this);
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);

        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view=inflater.inflate(R.layout.circle_item, parent, false);
                ((TextView)view.findViewById(R.id.userName)).setText(lastCircle.getMemberName());
                ((TextView)view.findViewById(R.id.circleInfo)).setText(lastCircle.getFdContent());
                ((TextView)view.findViewById(R.id.releaseTime)).setText(lastCircle.getFdCreateTime());
                view.findViewById(R.id.bottomLine).setVisibility(View.VISIBLE);
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }

    public void onEventMainThread(EventByTag eventByTag) {
        // 评论列表
        if (EventUtils.isValid(eventByTag, EventTagConfig.conments, null)) {
            try {
                JSONObject json = new JSONObject(eventByTag.getObj() + "");
                String result = json.getJSONObject("comments").getString("resultList");
                list = new GsonParser().parseList(result, new TypeToken<List<Circle>>() {
                });
                totalPage=json.getJSONObject("comments").getJSONObject("pageBean").getInt("totalPage");
                adapter.addAll(list);
            } catch (JSONException e) {
                BasicTool.showToast(this, "这里解析挂了");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (++currentPage < totalPage) {
//            new CircleTask().MyCircle(getActivity(), ++currentPage, 10, 1);
        } else {
            adapter.stopMore();
        }
    }
}
