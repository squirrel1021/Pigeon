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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andsync.xpermission.XPermissionUtils;
import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.july.pigeon.LocationDemo;
import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.adapter.holder.CommViewHolder;
import com.july.pigeon.adapter.holder.JiaoHolder;
import com.july.pigeon.bean.Circle;
import com.july.pigeon.bean.Jiaohuan;
import com.july.pigeon.bletest.DeviceScanActivity;
import com.july.pigeon.engine.ConstantValues;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.PigeonTask;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.jiaohuan.SetJiaohuan;
import com.july.pigeon.ui.activity.jiaohuan.contrastMapActivity;
import com.july.pigeon.ui.activity.jiaohuan.jiaohuanHistory;
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

public class JiaohuanFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView addjiaohuan;
    private BaseListAdapter adapter;
    List<Jiaohuan> list = new ArrayList<Jiaohuan>();
    private GridView gridView;
    private LinearLayout contrastView, transfer, setView;
    private int touthSum = 0;
    private int touthIndex = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_frag, container, false);
        EventBus.getDefault().register(this);
        initView();
        setOnItemClickListener();
        return view;
    }

    private void setOnItemClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touthSum == 0) {
                    Intent intent = new Intent(getActivity(), contrastMapActivity.class);
                    intent.putExtra("code", list.get(position).getId());
                    intent.putExtra("ringCode", list.get(position).getRingCode());
                    startActivity(intent);
                }else if(touthSum==1){
                    touthSum++;
                    touthIndex=position;
                    ConstantValues.ringCodeA=list.get(position).getRingCode();
                    adapter.notifyDataSetChanged();
                }else{
                    ConstantValues.ringCodeB=list.get(position).getRingCode();
                    touthIndex=position;
                    adapter.notifyDataSetChanged();
                    jumpMap(contrastMapActivity.class);

                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        touthSum=0;
        new UserTask().myJiaohuan(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        touthSum=0;
        setAdapter();
    }

    private void initView() {
        ((ImageView)view.findViewById(R.id.locationImage)).setOnClickListener(this);
        contrastView = (LinearLayout) view.findViewById(R.id.contrastView);
        setView = (LinearLayout) view.findViewById(R.id.setView);
        transfer = (LinearLayout) view.findViewById(R.id.transfer);
        contrastView.setOnClickListener(this);
        setView.setOnClickListener(this);
        transfer.setOnClickListener(this);
        addjiaohuan = (ImageView) view.findViewById(R.id.addjiaohuan);
        gridView = (GridView) view.findViewById(R.id.gridview);
        addjiaohuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStartUtil.start(getActivity(), AddJiaohuan.class);
            }
        });

    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new BaseListAdapter(getActivity(), list, R.layout.jiao_item) {
                @Override
                public void convert(CommViewHolder holder) {
                    TextView tv = holder.getView(R.id.tv_item);
                    ImageView image = holder.getView(R.id.iv_item);
                    tv.setText(list.get(holder.getPosition()).getRingCode());
                    if (touthSum == 1) {
                        image.setImageDrawable(getResources().getDrawable(R.drawable.footring_back));
                    } else if (touthSum == 2) {
                        if (holder.getPosition() == touthIndex) {
                            image.setImageDrawable(getResources().getDrawable(R.drawable.footring_front));
                        }
                    }else if(touthSum == 3){
                        if (holder.getPosition() == touthIndex) {
                            image.setImageDrawable(getResources().getDrawable(R.drawable.footring_front));
                        }
                    }else{
                        image.setImageDrawable(getResources().getDrawable(R.drawable.footring_front));
                    }
                }
            };
            gridView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
//我的脚环
        if (EventUtils.isValid(eventByTag, EventTagConfig.myjiaohuan, null)) {
            list = new GsonParser().parseList(eventByTag.getObj() + "", new TypeToken<List<Jiaohuan>>() {
            });
            setAdapter();
        }
        //脚环数据
        if (EventUtils.isValid(eventByTag, EventTagConfig.getUpData, null)) {
            Toast.makeText(getActivity(), eventByTag.getObj() + "", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locationImage:
                jumpMap(MapControlDemo.class);
                break;
            case R.id.setView:
                ActivityStartUtil.start(getActivity(), SetJiaohuan.class);
                break;
            case R.id.contrastView:
                if(touthSum==0){
                    touthSum++;
                    setAdapter();
                }else {
                    touthSum=0;
                    setAdapter();
                }

                break;
            case R.id.transfer:
                ActivityStartUtil.start(getActivity(), DeviceScanActivity.class);
                break;
            default:
                break;
        }
    }

    private void jumpMap(final Class zz) {
        XPermissionUtils.requestPermissions(getActivity(), 1, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                new XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        ActivityStartUtil.start(getActivity(), zz);
//                        ActivityStartUtil.start(getActivity(), MapControlDemo.class);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
