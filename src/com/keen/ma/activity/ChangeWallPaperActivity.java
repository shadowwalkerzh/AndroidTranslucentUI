package com.keen.ma.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.keen.ma.MyApplication;
import com.keen.ma.R;
import com.keen.ma.adapter.WallPaperGridViewAdapter;

/**
 * ¸ü»»µ×Í¼Ò³Ãæ
 * @author KeenW
 * @lastChangeTime 2014-8-25ÏÂÎç8:38:41
 */
public class ChangeWallPaperActivity extends BaseActivity {

	private GridView wallPaperGridView;
	private WallPaperGridViewAdapter adapter;
	private SharedPreferences sp = null;
	private MyApplication myApp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (MyApplication)getApplication();
		myApp.addActivity(this);
		setContentLayout(R.layout.activity_change_wallpaper);
		setTitleText("¸ü»»µ×Í¼");
		setRightIamgeViewShow();
		setRightImageViewResource(R.drawable.done);
		sp = getSharedPreferences("KeenW", Context.MODE_PRIVATE);
		
		setRightIamgeViewListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.edit().putInt("wallPaperNumber", adapter.getSelectedPosition()+1).commit();
			}
		});
		
		wallPaperGridView = (GridView)findViewById(R.id.wallpaper_gridview);
		adapter = new WallPaperGridViewAdapter(this);
		wallPaperGridView.setAdapter(adapter);
		
	}
	
}
