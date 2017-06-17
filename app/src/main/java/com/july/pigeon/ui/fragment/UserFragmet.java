package com.july.pigeon.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.july.pigeon.R;
import com.july.pigeon.adapter.BaseListAdapter;
import com.july.pigeon.adapter.holder.CommViewHolder;
import com.july.pigeon.ui.activity.TestMap;
import com.july.pigeon.ui.activity.main.MapControlDemo;
import com.july.pigeon.util.ActivityStartUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDROID on 2017/6/7.
 */

public class UserFragmet extends Fragment {
    private View view;
    private GridView gv;
    private ImageView headImg;
    private BaseListAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private String[] gridTvs={"昵称","养殖鸽龄","荣誉","我的鸽子","我的脚环","设置"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_frag, container, false);
        initView();
        return view;
    }

    private void initView() {
        gv = (GridView) view.findViewById(R.id.girdview);
        headImg= (ImageView) view.findViewById(R.id.imageView);
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
                    TextView tv=holder.getView(R.id.tv_item);
                    tv.setText(gridTvs[holder.getPosition()]);
                }
            };
            gv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
