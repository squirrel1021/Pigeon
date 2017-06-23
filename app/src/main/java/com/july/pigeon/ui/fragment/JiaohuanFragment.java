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
import android.widget.Toast;

import com.andsync.xpermission.XPermissionUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.july.pigeon.LocationDemo;
import com.july.pigeon.R;
import com.july.pigeon.adapter.holder.JiaoHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.ui.activity.login.ForgetPassWordActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.ui.activity.main.MapControlDemo;
import com.july.pigeon.util.ActivityStartUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ANDROID on 2017/6/7.
 */

public class JiaohuanFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
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
        ((ImageView) view.findViewById(R.id.locationImage)).setOnClickListener(this);
        for (int i = 0; i < 5; i++) {
            Circle circle = new Circle();
            circle.setThemeInfo("100" + i);
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
                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_LONG).show();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locationImage:
                XPermissionUtils.requestPermissions(getActivity(), 1, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
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
                                            .setMessage("我们需要摄像头权限才能正常使用该功能")
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
            default:
                break;
        }
    }
}
