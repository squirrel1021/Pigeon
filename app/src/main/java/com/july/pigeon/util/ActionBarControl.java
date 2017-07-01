package com.july.pigeon.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.july.pigeon.R;


/**
 * @author user 导航栏控制类
 */
public class ActionBarControl {

	private ViewGroup container;
	private Activity mContext;
	ImageView arrow;
	ImageView pullDown;
	RelativeLayout backarea;
	TextView title, rightTv, messageAllStatus, leftText;
	private int backRes;

	public ActionBarControl(Activity context, ViewGroup container) {
		init(context, container, 0);
	}

	public ActionBarControl(Activity context, ViewGroup container, int backRes) {
		init(context, container, backRes);
	}

	private void init(Activity context, ViewGroup container, int backRes) {
		this.mContext = context;
		this.container = container;
		this.backRes = backRes;
	}

	public TextView getMessageAllStatus() {
		return messageAllStatus;
	}

	private void handleClick(int res) {
		View view = container.findViewById(res);
		if (null != view) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.goBack:
						mContext.finish();
						break;
					case R.id.cityLayout:

						break;
					case R.id.share:

						break;
					case R.id.sousuo:

						break;
					default:
						break;
					}

				}
			});
		}
	}

	public void back(int backRes) {
		this.backRes = backRes;
		back();
	}

	public void back() {
		if (backRes != 0) {
			handleClick(backRes);
		}
	}

	private void initView() {
		// pullDown = (ImageView) container.findViewById(R.id.share);
		arrow = (ImageView) container.findViewById(R.id.back_arrow);
		title = (TextView) container.findViewById(R.id.title_center);
		leftText= (TextView) container.findViewById(R.id.left_tv);
		rightTv= (TextView) container.findViewById(R.id.right_tv);
//		cityName = (TextView) container.findViewById(R.id.cityName);
	}

	/**
	 * 右侧没有东西的导航栏
	 * @param name
	 */
	public void setNoRightNavigation(String name) {
		initView();
		title.setText(name);
		arrow.setVisibility(View.VISIBLE);

//		((ImageView) container.findViewById(R.id.share)).setVisibility(View.INVISIBLE);
		back(R.id.back_arrow);
	}

	public void setTitle(String name) {
		title.setText(name);
	}

	public String getTitleText() {
		return title.getText().toString();
	}

	public void setMainActivityNavigation(String titleName) {
		initView();

	}


}
