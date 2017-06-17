package com.july.pigeon.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.LoginActivity;
import com.july.pigeon.ui.weight.DepthPageTransformer;


public class WelcomeActivity extends BaseActivity {

    private int[] imgs = {R.drawable.bootpage_1,
            R.drawable.bootpage_2,
            R.drawable.bootpage_3,
    };
    private Button btn_enter;
    private ViewPager viewpager;
    private GuidePagerAdapter guideadapter;
    private Handler handler;
    private Runnable delayGoMain = new Runnable() {
        @Override
        public void run() {
            if (viewpager.getCurrentItem() == (imgs.length - 1)) {
                gotoMainActivity();
            } else {
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
        }
    };

    private void initView() {
        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });
        viewpager = (ViewPager) findViewById(R.id.viewpager);


        guideadapter = new GuidePagerAdapter(imgs, this);
        viewpager.setAdapter(guideadapter);
        viewpager.setPageTransformer(true, new DepthPageTransformer());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (handler != null) {
                    handler.removeCallbacks(delayGoMain);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == (imgs.length - 1)) {
                    showGotoMainBtn(true);
                } else {
                    showGotoMainBtn(false);
                }
                if (handler != null) {
                    handler.postDelayed(delayGoMain, 3000);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (handler != null) {
            handler.postDelayed(delayGoMain, 3000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler = new Handler();
        initView();
    }

    private void gotoMainActivity() {
        if (handler != null) {
            handler.removeCallbacks(delayGoMain);
            handler = null;
        }

        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        intent.putExtra("toupdate", true);
        startActivity(intent);
        finish();
    }

    private void showGotoMainBtn(boolean show) {
        if (show) {
            btn_enter.setVisibility(View.VISIBLE);
            btn_enter.setEnabled(false);
            AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);
            animation.setDuration(500);
            animation.setAnimationListener(
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            btn_enter.setEnabled(true);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
            btn_enter.startAnimation(animation);

        } else {
            btn_enter.setVisibility(View.GONE);
        }

    }


}


