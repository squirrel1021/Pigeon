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
	TextView title, cityName, messageAllStatus, leftModuleText;
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
		arrow = (ImageView) container.findViewById(R.id.goBack);
		title = (TextView) container.findViewById(R.id.navigationTitle);
		cityName = (TextView) container.findViewById(R.id.cityName);
	}

	/**
	 * 标题栏没有搜索有地址的 (餐饮首页)
	 * 
	 * @param name
	 */
	public void setCommonNavigation(String name) {
		initView();
		final ImageView ivMore = (ImageView) container.findViewById(R.id.share);
		((LinearLayout) container.findViewById(R.id.cityLayout)).setVisibility(View.VISIBLE);

		title.setText(name);
		arrow.setVisibility(View.VISIBLE);

		// ShowView.seCommontPopwindow(mContext, ivMore);

		handleClick(R.id.share);
		// back(R.id.goBack);
		handleClick(R.id.cityLayout);
	}

	/**
	 * 标题栏没有地址的(大部分的导航栏，左箭头，右三点)
	 * 
	 * @param name
	 */
	public void setNoLocationNavigation(String name) {
		initView();
		final ImageView ivMore = (ImageView) container.findViewById(R.id.share);

		// ((LinearLayout)
		// container.findViewById(R.id.cityLayout)).setVisibility(View.GONE);
		// ((TextView)
		// container.findViewById(R.id.cityName)).setText(AppSettings.getCurCityName());
		title.setText(name);
		arrow.setVisibility(View.VISIBLE);

		back(R.id.goBack);
		// handleClick(R.id.cityLayout);
	}

	/**
	 * 右侧是分享的导航栏
	 * @param name
	 */
	public void setRightShareNavigation(String name) {
		initView();
		final ImageView ivMore = (ImageView) container.findViewById(R.id.share);

		ivMore.setVisibility(View.VISIBLE);
		title.setText(name);
		arrow.setVisibility(View.VISIBLE);
		handleClick(R.id.share);
		back(R.id.goBack);
	}
	
	/**
	 * 右侧没有东西的导航栏
	 * @param name
	 */
	public void setNoRightNavigation(String name) {
		initView();
		title.setText(name);
		((ImageView) container.findViewById(R.id.share)).setVisibility(View.INVISIBLE);
		back(R.id.goBack);
	}
	
	/**
	 * 右侧有张图片的导航栏
	 * @param name
	 */
	public void setRightImageNavigation(String name) {
		initView();
		title.setText(name);
		ImageView icon= (ImageView) container.findViewById(R.id.share);

		icon.setVisibility(View.VISIBLE);
		arrow.setVisibility(View.VISIBLE);
		back(R.id.goBack);
	}
	/**
	 * 导航栏有搜索框（目前就美食广场）
	 * @param name
	 */
	public void setMainNavigation(String name) {
		initView();
		final ImageView ivMore = (ImageView) container.findViewById(R.id.share);

		((ImageView) container.findViewById(R.id.sousuo)).setVisibility(View.VISIBLE);
//		((LinearLayout) container.findViewById(R.id.cityLayout)).setVisibility(View.GONE);
		// ((TextView)
		// container.findViewById(R.id.cityName)).setText(AppSettings.getCurCityName());
		title.setText(name);
		arrow.setVisibility(View.VISIBLE);

		back(R.id.goBack);
		
		handleClick(R.id.sousuo);
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
