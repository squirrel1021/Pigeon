package com.july.pigeon.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.july.pigeon.R;
import com.july.pigeon.adapter.holder.JiaoHolder;
import com.july.pigeon.adapter.holder.XingeHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.bean.Pigeon;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.user.AddPigeon;
import com.july.pigeon.util.ActivityStartUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by ANDROID on 2017/6/7.
 */

public class XingeFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Pigeon> adapter;
    private ImageView addPigeon;
    List<Pigeon> list = new ArrayList<Pigeon>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.xinge_frag, container, false);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new UserTask().myPigeon(getActivity());
    }

    private void initView() {
        addPigeon= (ImageView) view.findViewById(R.id.addPigeon);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridlayoutManger = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridlayoutManger);
//        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, UiUtil.dip2px(getActivity(), 0.5f), 0, 0);
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Pigeon>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new XingeHolder(parent);
            }
        });
//        adapter.setMore(R.layout.view_more, this);
//        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
        adapter.addAll(list);
        addPigeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStartUtil.start(getActivity(), AddPigeon.class);
            }
        });
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
    @Override
    public void onRefresh() {
        new UserTask().myPigeon(getActivity());
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
