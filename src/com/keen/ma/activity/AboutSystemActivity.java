package com.keen.ma.activity;

import android.os.Bundle;

import com.keen.ma.MyApplication;
import com.keen.ma.R;

/**
 * 关于我们
 * @author KEEN
 * @lastChangeTime 2014年8月22日 上午9:27:14
 */
public class AboutSystemActivity extends BaseActivity {

	private MyApplication myApp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (MyApplication)getApplication();
		myApp.addActivity(this);
		setContentLayout(R.layout.activity_about);
		setTitleText("关于我们");
	}
	
}
