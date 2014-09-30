package com.keen.ma.db.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keen.ma.bean.UserBean;
import com.keen.ma.db.DBHelper;
import com.keen.ma.db.dao.UserDAO;
import com.keen.ma.utils.AppConstants;

public class UserDaoImpl implements UserDAO{

	private DBHelper dbHelper = null;
	
	public UserDaoImpl(Context context){
		dbHelper = new DBHelper(context);
	}
	
	/**
	 * 添加用户
	 */
	@Override
	public void add(UserBean user) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
        ContentValues values = new ContentValues();  
 
        values.put("userId", user.getUserId());  
        values.put("userNickName", user.getUserNickName());  
        values.put("userPassword", user.getUserPassword());  
        values.put("userPhone", user.getUserPhone());  
        values.put("userEmail", user.getUserEmail());  
        values.put("userImagePath", user.getUserImagePath());  
        values.put("userLongitude", user.getUserLongitude());  
        values.put("userLatitude", user.getUserLatitude());  
 
        db.insert(AppConstants.TABLE_USER, null, values);
	}

	/**
	 * 根据id删除用户
	 */
	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
        db.delete(AppConstants.TABLE_USER, "userId=?", new String[]{id});  
	}

	/**
	 * 根据手机号删除用户
	 */
	@Override
	public void deleteByPhone(String phoneNumber) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
        db.delete(AppConstants.TABLE_USER, "userPhone=?", new String[]{phoneNumber});  
	}

	/**
	 * 根据邮箱删除用户
	 */
	@Override
	public void deleteByEmail(String email) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
        db.delete(AppConstants.TABLE_USER, "userEmail=?", new String[]{email});
	}

	/**
	 * 修改用户
	 */
	@Override
	public void updata(UserBean user) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();  
        ContentValues values = new ContentValues();  
 
        values.put("userId", user.getUserId());  
        values.put("userNickName", user.getUserNickName());  
        values.put("userPassword", user.getUserPassword());  
        values.put("userPhone", user.getUserPhone());  
        values.put("userEmail", user.getUserEmail());  
        values.put("userImagePath", user.getUserImagePath());  
        values.put("userLongitude", user.getUserLongitude());  
        values.put("userLatitude", user.getUserLatitude()); 
 
        db.update(AppConstants.TABLE_USER, values, "userId=?", new String[]{user.getUserId()});
	}

	/**
	 * 查询所有用户
	 */
	@Override
	public List<UserBean> find() {
		// TODO Auto-generated method stub
		List<UserBean> userBeans = null;  
        SQLiteDatabase db = dbHelper.getReadableDatabase();  
 
        Cursor cursor = db.query(AppConstants.TABLE_USER, null, null, null, null, null, null);  
        if(cursor != null){  
        	userBeans = new ArrayList<UserBean>();  
            while(cursor.moveToNext()){  
            	UserBean userBean = new UserBean();  
                String userId = cursor.getString(cursor.getColumnIndex("userId"));  
                String userPassword = cursor.getString(cursor.getColumnIndex("userPassword"));  
                String userNickName = cursor.getString(cursor.getColumnIndex("userNickName"));  
                String userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));  
                String userEmail = cursor.getString(cursor.getColumnIndex("userEmail"));  
                String userImagePath = cursor.getString(cursor.getColumnIndex("userImagePath"));  
                double userLongitude = cursor.getDouble(cursor.getColumnIndex("userLongitude"));  
                double userLatitude = cursor.getDouble(cursor.getColumnIndex("userLatitude"));  
                
                userBean.setUserId(userId);
                userBean.setUserPassword(userPassword);
                userBean.setUserNickName(userNickName);
                userBean.setUserPhone(userPhone);
                userBean.setUserEmail(userEmail);
                userBean.setUserImagePath(userImagePath);
                userBean.setUserLongitude(userLongitude);
                userBean.setUserLatitude(userLatitude);
                
                userBeans.add(userBean);  
            }  
        }  
        return userBeans;
	}

	/**
	 * 根据id查询用户
	 */
	@Override
	public UserBean findById(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();  
		UserBean userBean = null;  
		 
        Cursor cursor = db.query(AppConstants.TABLE_USER, null, "userId=?", new String[]{id}, null, null, null);  
        if(cursor != null && cursor.moveToFirst()){  
        	userBean = new UserBean();  
            String userId = cursor.getString(cursor.getColumnIndex("userId"));  
            String userPassword = cursor.getString(cursor.getColumnIndex("userPassword"));  
            String userNickName = cursor.getString(cursor.getColumnIndex("userNickName"));  
            String userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));  
            String userEmail = cursor.getString(cursor.getColumnIndex("userEmail"));  
            String userImagePath = cursor.getString(cursor.getColumnIndex("userImagePath"));  
            double userLongitude = cursor.getDouble(cursor.getColumnIndex("userLongitude"));  
            double userLatitude = cursor.getDouble(cursor.getColumnIndex("userLatitude"));  
            
            userBean.setUserId(userId);
            userBean.setUserPassword(userPassword);
            userBean.setUserNickName(userNickName);
            userBean.setUserPhone(userPhone);
            userBean.setUserEmail(userEmail);
            userBean.setUserImagePath(userImagePath);
            userBean.setUserLongitude(userLongitude);
            userBean.setUserLatitude(userLatitude);
        }  
        return userBean;
	}

	/**
	 * 根据手机号查询用户
	 */
	@Override
	public UserBean findByPhone(String phoneNumber) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();  
		UserBean userBean = null;  
		 
        Cursor cursor = db.query(AppConstants.TABLE_USER, null, "userPhone=?", new String[]{phoneNumber}, null, null, null);  
        if(cursor != null && cursor.moveToFirst()){  
        	userBean = new UserBean();  
            String userId = cursor.getString(cursor.getColumnIndex("userId"));  
            String userPassword = cursor.getString(cursor.getColumnIndex("userPassword"));  
            String userNickName = cursor.getString(cursor.getColumnIndex("userNickName"));  
            String userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));  
            String userEmail = cursor.getString(cursor.getColumnIndex("userEmail"));  
            String userImagePath = cursor.getString(cursor.getColumnIndex("userImagePath"));  
            double userLongitude = cursor.getDouble(cursor.getColumnIndex("userLongitude"));  
            double userLatitude = cursor.getDouble(cursor.getColumnIndex("userLatitude"));  
            
            userBean.setUserId(userId);
            userBean.setUserPassword(userPassword);
            userBean.setUserNickName(userNickName);
            userBean.setUserPhone(userPhone);
            userBean.setUserEmail(userEmail);
            userBean.setUserImagePath(userImagePath);
            userBean.setUserLongitude(userLongitude);
            userBean.setUserLatitude(userLatitude);
        }  
        return userBean;
	}

	/**
	 * 根据邮箱查询用户
	 */
	@Override
	public UserBean findByEmail(String email) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();  
		UserBean userBean = null;  
		 
        Cursor cursor = db.query(AppConstants.TABLE_USER, null, "userEmail=?", new String[]{email}, null, null, null);  
        if(cursor != null && cursor.moveToFirst()){  
        	userBean = new UserBean();  
            String userId = cursor.getString(cursor.getColumnIndex("userId"));  
            String userPassword = cursor.getString(cursor.getColumnIndex("userPassword"));  
            String userNickName = cursor.getString(cursor.getColumnIndex("userNickName"));  
            String userPhone = cursor.getString(cursor.getColumnIndex("userPhone"));  
            String userEmail = cursor.getString(cursor.getColumnIndex("userEmail"));  
            String userImagePath = cursor.getString(cursor.getColumnIndex("userImagePath"));  
            double userLongitude = cursor.getDouble(cursor.getColumnIndex("userLongitude"));  
            double userLatitude = cursor.getDouble(cursor.getColumnIndex("userLatitude"));  
            
            userBean.setUserId(userId);
            userBean.setUserPassword(userPassword);
            userBean.setUserNickName(userNickName);
            userBean.setUserPhone(userPhone);
            userBean.setUserEmail(userEmail);
            userBean.setUserImagePath(userImagePath);
            userBean.setUserLongitude(userLongitude);
            userBean.setUserLatitude(userLatitude);
        }  
        return userBean;
	}

}
