package com.keen.ma.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keen.ma.MenuControl;
import com.keen.ma.MyApplication;
import com.keen.ma.R;

/**
 * @author KeenW
 * @lastChangeTime 2014-8-14下午8:30:22
 */
public class HomeActivity extends FragmentActivity {
	
	private long currentSysTime;
	private SharedPreferences sp = null;
	private ImageView menuImageView;
	private static MenuControl menuControl;
	private static TextView titleTV;
	private MyApplication myApp = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		myApp = (MyApplication)getApplication();
		myApp.addActivity(this);
		setContentView(R.layout.activity_home);
		menuControl = new MenuControl(this);
		menuImageView = (ImageView)findViewById(R.id.menu_imageview);
		menuImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuControl.toggleMenu();
			}
		});
		titleTV = (TextView)findViewById(R.id.title);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sp = getSharedPreferences("KeenW", Context.MODE_PRIVATE);
		int num = sp.getInt("wallPaperNumber", 0);
		myApp.setCurrentWallPaper(this, num);
	}
	
	public static void setCurrentFragment(int position){
		menuControl.setCurrentFragment(position);
	}
	
	public static void setTitle(String title){
		titleTV.setText(title);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(System.currentTimeMillis() - currentSysTime < 2000){
				myApp.exit();
				return true;
			}else{
				currentSysTime = System.currentTimeMillis();
				Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
