package com.keen.ma.utils;

import android.os.Environment;

/**
 * 常量类
 * @author KeenW
 * @lastChangeTime 2014-8-24下午3:00:13
 */
public class AppConstants {
	
	/*#########################其他类型常量###############################*/
	
	public static final String SDCARD_BATH_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	/*#########################魔幻照片相关常量###############################*/
	
	public static final String AVIARY_KEY = "e87969babcc74d24";
	public static final String AVIARY_SECRET = "d396a47bf508082f";
	
	/*#########################Sqlite数据库相关常量###############################*/
	
	public static final String DB_NAME = "keenW.db";
	public static final int DB_VERSION = 1;	//数据库版本号
	public static final String TABLE_USER = "user";//用户表，保存用户基本数据
	
	
	
	
	
}
