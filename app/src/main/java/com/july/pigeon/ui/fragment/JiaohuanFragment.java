package com.july.pigeon.ui.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andsync.xpermission.XPermissionUtils;
import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.july.pigeon.LocationDemo;
import com.july.pigeon.R;
import com.july.pigeon.adapter.holder.JiaoHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.bean.Jiaohuan;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.login.ForgetPassWordActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.ui.activity.main.MapControlDemo;
import com.july.pigeon.ui.activity.user.AddJiaohuan;
import com.july.pigeon.ui.activity.user.SetActivity;
import com.july.pigeon.util.ActivityStartUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by ANDROID on 2017/6/7.
 */

public class JiaohuanFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private View view;
    private ImageView addjiaohuan;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Jiaohuan> adapter;
    List<Jiaohuan> list = new ArrayList<Jiaohuan>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_frag, container, false);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new UserTask().myJiaohuan(getActivity());
    }

    private void initView() {
        addjiaohuan= (ImageView) view.findViewById(R.id.addjiaohuan);
        view.findViewById(R.id.setView).setOnClickListener(this);
        view.findViewById(R.id.locationImage).setOnClickListener(this);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridlayoutManger = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridlayoutManger);
//        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, UiUtil.dip2px(getActivity(), 0.5f), 0, 0);
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Jiaohuan>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new JiaoHolder(parent);
            }
        });
//        adapter.setMore(R.layout.view_more, this);
//        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);
        adapter.addAll(list);
        addjiaohuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStartUtil.start(getActivity(), AddJiaohuan.class);
            }
        });
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
        new UserTask().myJiaohuan(getActivity());
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locationImage:
                XPermissionUtils.requestPermissions(getActivity(), 1, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        new XPermissionUtils.OnPermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                ActivityStartUtil.start(getActivity(), MapControlDemo.class);
                            }

                            @Override
                            public void onPermissionDenied(final String[] deniedPermissions, boolean alwaysDenied) {
                                Toast.makeText(getActivity(), "获取权限失败", Toast.LENGTH_SHORT).show();
                                if (alwaysDenied) { // 拒绝后不再询问 -> 提示跳转到设置
                                    new AlertDialog.Builder(getActivity()).setTitle("温馨提示")
                                            .setMessage("我们需要定位权限才能正常使用该功能")
                                            .setNegativeButton("取消", null)
                                            .setPositiveButton("打开权限", new DialogInterface.OnClickListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.M)
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
//                                            XPermissionUtils.requestPermissionsAgain(ForgetPassWordActivity.this, deniedPermissions,
//                                                    1);
                                                    Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
                                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                break;
            case R.id.setView:

                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
