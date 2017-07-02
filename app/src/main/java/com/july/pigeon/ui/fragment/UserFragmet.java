package com.july.pigeon.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.adapter.holder.CommViewHolder;
import com.july.pigeon.bean.User;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.task.UserTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.TestMap;
import com.july.pigeon.ui.activity.login.ForgetPassWordActivity;
import com.july.pigeon.ui.activity.login.RegisterActivity;
import com.july.pigeon.ui.activity.main.HomeActivity;
import com.july.pigeon.ui.activity.main.MapControlDemo;
import com.july.pigeon.ui.activity.user.BreedAge;
import com.july.pigeon.ui.activity.user.SetActivity;
import com.july.pigeon.ui.activity.user.SetHonor;
import com.july.pigeon.ui.activity.user.UpdateNickName;
import com.july.pigeon.ui.activity.user.UpdateNickName_ViewBinding;
import com.july.pigeon.util.ActivityStartUtil;
import com.july.pigeon.util.BasicTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ANDROID on 2017/6/7.
 */

public class UserFragmet extends Fragment implements View.OnClickListener {
    private View view;
    private GridView gv;
    private ImageView headImg;
    private BaseListAdapter adapter;
    private User user;
    private TextView phone,nickname,gznl,rongyu;
    private List<String> list = new ArrayList<String>();
    private String[] gridTvs = {"昵称", "养殖鸽龄", "荣誉", "我的鸽子", "我的脚环", "设置"};
    private int[] imgs = {R.drawable.icon_nickname, R.drawable.icon_oldpigeon, R.drawable.icon_honor, R.drawable.icon_mydove, R.drawable.icon_myfootring, R.drawable.icon_setup};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_frag, container, false);
        initView();
        EventBus.getDefault().register(this);
        setOnItemClick();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new UserTask().userinfo(getActivity());
    }
    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        // 个人信息
        if (EventUtils.isValid(eventByTag, EventTagConfig.userinfo, null)) {
            try {
                JSONObject json=new JSONObject(eventByTag.getObj()+"");
                String info=json.getString("member");
                user=new GsonParser().parseObject(info,User.class);
                setDate();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDate() {
        phone.setText(user.getMobile());
        nickname.setText(user.getNickName());
        gznl.setText(user.getBreedAge());
        rongyu.setText(user.getHonor());
    }

    private void setOnItemClick() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://昵称
                        ActivityStartUtil.start(getActivity(), UpdateNickName.class);
                        break;
                    case 1://鸽龄
                        ActivityStartUtil.start(getActivity(), BreedAge.class);
                        break;
                    case 2://荣誉
                        ActivityStartUtil.start(getActivity(), SetHonor.class);
                        break;
                    case 3://我的鸽子
                        break;
                    case 4://我的脚环
                        break;
                    case 5://设置
                        ActivityStartUtil.start(getActivity(), SetActivity.class);
                        break;


                    default:
                        break;
                }
            }
        });
    }

    private void initView() {
        phone= (TextView) view.findViewById(R.id.phone);
        nickname= (TextView) view.findViewById(R.id.nickname);
        gznl= (TextView) view.findViewById(R.id.gznl);
        rongyu= (TextView) view.findViewById(R.id.rongyu);
        gv = (GridView) view.findViewById(R.id.girdview);
        headImg = (ImageView) view.findViewById(R.id.imageView);
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStartUtil.start(getActivity(), MapControlDemo.class);
            }
        });
        setAdapter();
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new BaseListAdapter(getActivity(), list, R.layout.user_gridview_item) {
                @Override
                public int getCount() {
                    return 6;
                }

                @Override
                public void convert(CommViewHolder holder) {
                    TextView tv = holder.getView(R.id.tv_item);
                    tv.setText(gridTvs[holder.getPosition()]);
                    ((ImageView) holder.getView(R.id.iv_item)).setImageResource(imgs[holder.getPosition()]);
                }
            };
            gv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:

                break;

            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
