package com.july.pigeon.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.july.pigeon.R;
import com.july.pigeon.adapter.holder.CircleHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.CircleTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.circle.ReleaseCircleActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;
import com.july.pigeon.util.UiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 首页社区fragment
 * Created by ANDROID on 2017/6/7.
 */

public class CircleFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Circle> adapter;
    private Handler handler = new Handler();
    List<Circle> list = new ArrayList<Circle>();
    List<Circle> list1 = new ArrayList<Circle>();
    private int currentPage = 1;
    private int totalPage = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.circle_frag, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        new CircleTask().MyCircle(getActivity(), 0, 10, 0);
        return view;
    }

    private void initView() {

        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, UiUtil.dip2px(getActivity(), 0.5f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Circle>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CircleHolder(parent, getActivity());
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
        adapter.addAll(list1);

    }

    @OnClick(R.id.write_circle)
    public void writeCircle() {
        ActivityStartUtil.start(getActivity(), ReleaseCircleActivity.class);
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 社区详情
        if (EventUtils.isValid(eventByTag, EventTagConfig.mycircle, null) || EventUtils.isValid(eventByTag, EventTagConfig.mycirclemore, null)) {
            try {

                JSONObject json = new JSONObject(eventByTag.getObj() + "");
                String dynamics = json.getString("dynamics");
                String resultList = new JSONObject(dynamics).getString("resultList");
                list1 = new GsonParser().parseList(resultList, new TypeToken<List<Circle>>() {
                });
//                totalPage=new JSONObject(json.getString("pageBean")).getInt("totalPage");
                if (EventTagConfig.mycirclemore.equals(eventByTag.getTAG())) {
                    list.addAll(list1);
                } else {
                    list.clear();
                    adapter.clear();
                    list.addAll(list1);
                }

                adapter.addAll(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        new CircleTask().MyCircle(getActivity(), currentPage, 10, 0);
    }

    @Override
    public void onLoadMore() {
        Log.i("EasyRecyclerView", "onLoadMore");
        if (currentPage < totalPage) {
            new CircleTask().MyCircle(getActivity(), ++currentPage, 10, 1);
        } else {
            adapter.stopMore();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
