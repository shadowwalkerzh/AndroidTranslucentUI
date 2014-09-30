package com.keen.ma.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.keen.ma.MyApplication;
import com.keen.ma.R;
import com.keen.ma.adapter.ForgetPwdFragmentAdapter;
import com.keen.ma.customui.MyPagerIndicator;

/**
 * @author KeenW
 * @lastChangeTime 2014-8-14ÏÂÎç12:45:44
 */
public class ForgetPwdActivity extends FragmentActivity implements
		OnClickListener {

	private TextView backTV;
	private static TextView tv1, tv2;
	private ViewPager viewPager;
	private MyApplication myApp = null;
	private SharedPreferences sp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);
		myApp = (MyApplication) getApplication();
		tv1 = (TextView) findViewById(R.id.find_by_phone);
		tv2 = (TextView) findViewById(R.id.find_by_email);
		backTV = (TextView) findViewById(R.id.back);
		backTV.setOnClickListener(this);

		FragmentPagerAdapter adapter = new ForgetPwdFragmentAdapter(
				getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(adapter);

		MyPagerIndicator underline_indicator = (MyPagerIndicator) findViewById(R.id.underline_indicator);
		underline_indicator.setViewPager(viewPager);

		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		changeTextColor(viewPager.getCurrentItem());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sp = getSharedPreferences("KeenW", Context.MODE_PRIVATE);
		int num = sp.getInt("wallPaperNumber", 0);
		myApp.setCurrentWallPaper(this, num);
	}

	public static void changeTextColor(int position) {
		switch (position) {
		case 0:
			tv1.setTextColor(Color.rgb(200, 0, 0));
			tv2.setTextColor(Color.rgb(109, 109, 109));
			break;
		case 1:
			tv1.setTextColor(Color.rgb(109, 109, 109));
			tv2.setTextColor(Color.rgb(200, 0, 0));
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.find_by_phone:
			viewPager.setCurrentItem(0);
			changeTextColor(0);
			break;
		case R.id.find_by_email:
			viewPager.setCurrentItem(1);
			changeTextColor(1);
			break;
		default:
			break;
		}
	}
}
