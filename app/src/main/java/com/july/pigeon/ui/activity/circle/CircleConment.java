package com.july.pigeon.ui.activity.circle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.july.pigeon.R;
import com.july.pigeon.adapter.EmotionAdapter;
import com.july.pigeon.adapter.GlobalOnItemClickManager;
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
import com.xk2318.emotionkeyboard.EmotionKeyboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    private FrameLayout extendView, emotionView;
    private TextView contentView;
    private ImageView extendButton, emotionButton;
    private EditText edittext;
    private Button btnSend;
    private EmotionKeyboard emotionKeyboard;
    private static final int emsNumOfEveryFragment = 20;//每页的表情数量
    private RadioGroup rgTipPoints;
    private RadioButton rbPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.conments_layout);
        inflater = getLayoutInflater();
        ButterKnife.bind(this);
        initView();
        bindToEmotionKeyboard();
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
        contentView = (TextView) findViewById(R.id.txt_main_content);
        extendButton = (ImageView) findViewById(R.id.img_reply_layout_add);
        emotionButton = (ImageView) findViewById(R.id.img_reply_layout_emotion);
        edittext = (EditText) findViewById(R.id.edit_text);
        edittext.addTextChangedListener(new ButtonBtnWatcher());//动态监听EditText
        btnSend = (Button) findViewById(R.id.btn_send);
        extendView = (FrameLayout) findViewById(R.id.extend_layout);
        emotionView = (FrameLayout) findViewById(R.id.emotion_layout);
    }

    private void bindToEmotionKeyboard() {
        emotionKeyboard = EmotionKeyboard.with(this)
                .setExtendView(extendView)
                .setEmotionView(emotionView)
                .bindToContent(contentView)
                .bindToEditText(edittext)
                .bindToExtendButton(extendButton)
                .bindToEmotionButton(emotionButton)
                .build();
        setUpEmotionViewPager();
        setUpExtendView();
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

    /* 设置表情布局下的视图 */
    private void setUpEmotionViewPager() {
        int fragmentNum;
		/*获取ems文件夹有多少个表情  减1 是因为有个删除键
                         每页20个表情  总共有length个表情
                         先判断能不能整除  判断是否有不满一页的表情
		 */
        int emsTotalNum = getSizeOfAssetsCertainFolder("ems") - 1;//表情的数量(除去删除按钮)
        if(emsTotalNum % emsNumOfEveryFragment == 0){
            fragmentNum = emsTotalNum / emsNumOfEveryFragment;
        } else {
            fragmentNum = (emsTotalNum / emsNumOfEveryFragment) + 1;
        }
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getSupportFragmentManager(), fragmentNum);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);

        GlobalOnItemClickManager globalOnItemClickListener = GlobalOnItemClickManager.getInstance();
        globalOnItemClickListener.attachToEditText((EditText)findViewById(R.id.edit_text));

		/* 设置表情下的提示点 */
        setUpTipPoints(fragmentNum, mViewPager);
    }

    /* 设置扩展布局下的视图 */
    private void setUpExtendView() {
        findViewById(R.id.btn_replay_layout_pic).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //图片按钮的点击事件
            }
        });
    }

    /**
     @param
     num   提示点的数量
     */
    private void setUpTipPoints(int num, ViewPager mViewPager) {
        rgTipPoints = (RadioGroup) findViewById(R.id.rg_reply_layout);
        for(int i=0;i<num;i++){
            rbPoint = new RadioButton(this);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(30, 30);
            lp.setMargins(10, 0, 10, 0);
            rbPoint.setLayoutParams(lp);
            rbPoint.setId(i);//为每个RadioButton设置标记
            rbPoint.setButtonDrawable(getResources().getDrawable(R.color.transparent));//设置button为@null
            rbPoint.setBackgroundResource(R.drawable.emotion_tip_points_selector);
            rbPoint.setClickable(false);
            if(i == 0){ // 第一个点默认为选中，与其他点显示颜色不同
                rbPoint.setChecked(true);
            } else {
                rbPoint.setChecked(false);
            }
            rgTipPoints.addView(rbPoint);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                rgTipPoints.check(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(!emotionKeyboard.interceptBackPress()){
            super.onBackPressed();
        }
    }

    /* 获取assets下某个指定文件夹下的文件数量 */
    private int getSizeOfAssetsCertainFolder(String folderName){
        int size = 0;
        try {
            size = getAssets().list(folderName).length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }
    /* EditText输入框动态监听 */
    class ButtonBtnWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!TextUtils.isEmpty(edittext.getText().toString())){ //有文本内容，按钮为可点击状态
                btnSend.setBackgroundResource(R.drawable.shape_button_reply_button_clickable);
                btnSend.setTextColor(getResources().getColor(R.color.light_white));
            } else { // 无文本内容，按钮为不可点击状态
                btnSend.setBackgroundResource(R.drawable.shape_button_reply_button_unclickable);
                btnSend.setTextColor(getResources().getColor(R.color.reply_button_text_disable));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
