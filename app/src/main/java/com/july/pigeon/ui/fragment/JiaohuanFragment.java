package com.july.pigeon.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.july.pigeon.R;
import com.july.pigeon.adapter.holder.CircleHolder;
import com.july.pigeon.adapter.holder.JiaoHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.util.UiUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by ANDROID on 2017/6/7.
 */

public class JiaohuanFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Circle> adapter;
    List<Circle> list = new ArrayList<Circle>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_frag, container, false);
        initView();
        return view;
    }

    private void initView() {
        for(int i=0;i<5;i++){
            Circle circle=new Circle();
            circle.setThemeInfo("100"+i);
            list.add(circle);
        }
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridlayoutManger = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridlayoutManger);
//        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, UiUtil.dip2px(getActivity(), 0.5f), 0, 0);
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Circle>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new JiaoHolder(parent);
            }
        });
//        adapter.setMore(R.layout.view_more, this);
//        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
        adapter.addAll(list);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(list);
                Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_LONG).show();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {

    }
}
