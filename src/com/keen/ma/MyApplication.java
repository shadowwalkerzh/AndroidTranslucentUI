package com.keen.ma;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.keen.ma.bean.UserBean;

/**
 * @author KEEN
 * @lastChangeTime 2014年8月13日 下午2:29:32
 */

public class MyApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static MyApplication instance;
	private Drawable wallPaper = null;
	private UserBean userBean;
	public static int CurrentMenuItem = 0;
	
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public Drawable getWallPaper() {
		return wallPaper;
	}

	public void setWallPaper(Drawable wallPaper) {
		this.wallPaper = wallPaper;
	}

	public MyApplication() {
	}

	public void setCurrentWallPaper(Activity activity,int number){
		switch (number) {
		case 1:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg1);
			break;
		case 2:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg2);
			break;
		case 3:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg3);
			break;
		case 4:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg4);
			break;
		case 5:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg5);
			break;
		case 6:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg6);
			break;
		case 7:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg7);
			break;
		case 8:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg8);
			break;
		default:
			wallPaper = getResources().getDrawable(R.drawable.portrait_bg1);
			break;
		}
		changeWallPaper(activity, wallPaper);
	}
	
	// 单例模式中获取唯一的MyApplication实例
	public static MyApplication getInstance() {
		if (null == instance)
			instance = new MyApplication();
		return instance;
	}

	// 添加到Activity容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	
	
	// 遍历所有的Activity并finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	private void changeWallPaper(Activity activity, Drawable drawable) {
		ImageView imageView = (ImageView) activity.findViewById(R.id.activity_main_imageview);
		imageView.setImageDrawable(drawable);
	}

}
