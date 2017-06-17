package com.july.pigeon.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.july.pigeon.R;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.fragment.BackHandledFragment;
import com.july.pigeon.ui.fragment.CircleFragment;
import com.july.pigeon.ui.fragment.JiaohuanFragment;
import com.july.pigeon.ui.fragment.UserFragmet;
import com.july.pigeon.ui.fragment.XingeFragment;
import com.july.pigeon.util.UiUtil;


/**
 * Created by ANDROID on 2017/6/7.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener{
    private FragmentManager fragmentManager = null;
    private JiaohuanFragment homeFrag = null;
    public XingeFragment xingeFragment = null;
    public CircleFragment circleFragment = null;
    private BackHandledFragment mBackHandedFragment;
    public Fragment cacheFrag = null;
    private UserFragmet userFrag = null;
    public RadioGroup mGroup;
    public RadioButton tab_home, tab_shop_cart, tab_order, tab_user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        mGroup = (RadioGroup) findViewById(R.id.tab_group);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setTabSelected(checkedId);
            }
        });
        mGroup.check(R.id.tab_home);
    }

    private void setTabSelected(int arg1) {

        switch (arg1) {
            case R.id.tab_home:
                setFragByTag(1, homeFrag, "HOME");
                break;
            case R.id.tab_shop_cart:
                setFragByTag(2, xingeFragment, "COMMUNITY");
                Log.i("shaii",UiUtil.getCertificateSHA1Fingerprint(this));
                break;
            case R.id.tab_order:
                setFragByTag(3, circleFragment, "ACTIVITY");
                break;
            case R.id.tab_user:
                setFragByTag(4, userFrag, "USER");
                break;
        }
    }

    public void setFragByTag(int tag, Fragment fragment, String fragtag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (null != cacheFrag) {
            transaction.hide(cacheFrag);
        }
        if (null != mBackHandedFragment && getSupportFragmentManager().getBackStackEntryCount() == 1) {
            transaction.hide(mBackHandedFragment);
        }
        if (null == fragment) {
            Bundle bundle = new Bundle();
            bundle.putString("from", "home");
            if (tag == 1) {
                fragment = new JiaohuanFragment();
                homeFrag = (JiaohuanFragment) fragment;
            } else if (tag == 2) {
                fragment = new XingeFragment();
                xingeFragment = (XingeFragment) fragment;
            } else if (tag == 3) {
                fragment = new CircleFragment();
                circleFragment = (CircleFragment) fragment;
            } else if (tag == 4) {
                fragment = new UserFragmet();
                userFrag = (UserFragmet) fragment;
            }
            fragment.setArguments(bundle);
            transaction.add(R.id.home_frame_content, fragment, fragtag);
        } else {
            transaction.show(fragment);
            if (tag == 1 && mBackHandedFragment != null && getSupportFragmentManager().getBackStackEntryCount() == 1) {
                transaction.show(mBackHandedFragment);
            }

        }
        cacheFrag = fragment;
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

    }
}
