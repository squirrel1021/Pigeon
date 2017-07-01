package com.july.pigeon.ui.activity.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

import android.view.WindowManager;
import android.view.Window;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.login.LoginActivity;
import com.july.pigeon.util.SharedPreferencesUtil;
import com.july.pigeon.util.StringUtils;


/**
 * Created by Marshon.Chen on 2016/6/1.
 * DESC:
 */
public class SplashActivity extends BaseActivity implements Animator.AnimatorListener {


    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        start();
    }

    private void start() {
        rootView = findViewById(R.id.main_root);
        final View viewById = findViewById(R.id.tt);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.3f, 1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                rootView.setAlpha(alpha);
                viewById.setScaleX(alpha);
                viewById.setScaleY(alpha);

            }
        });
        valueAnimator.addListener(this);
        valueAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String firstrun = (String) SharedPreferencesUtil.getData(this, "isFisrt", "");
        if (TextUtils.isEmpty(firstrun)) {
            SharedPreferencesUtil.saveData(this, "isFisrt", "xxxx");
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (StringUtils.isEmpty((String) SharedPreferencesUtil.getData(this, "token", ""))) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
