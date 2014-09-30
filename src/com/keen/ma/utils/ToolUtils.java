package com.keen.ma.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author KEEN
 * @lastChangeTime 2014年8月13日 下午2:26:59
 */

public class ToolUtils {

	/**
	 * 验证账户是手机号或者邮箱
	 * 
	 * @param account
	 * @return
	 */
	public static boolean checkAccountType(String account) {
		boolean flag = false;
		if (isMobileNO(account) || isEmail(account)) {
			flag = true;
		}
		return flag;
	}

	// 判断手机格式是否正确
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 判断email格式是否正确
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	//判断验证码格式是否是6位数字
	public static boolean isCode(String code){
		String str = "^([1-9])\\d{5}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(code);
		return m.matches();
	}
	
	/**
	 * 密码长度不小于8
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkPasswordType(String password) {
		boolean flag = false;
		if (password.length() >= 8)
			flag = true;
		return flag;
	}

	public static boolean isNullString(String string) {
		boolean flag = false;
		if (string == null || "".equals(string.trim())
				|| "null".equals(string.trim()))
			flag = true;
		return flag;
	}

	/**
	 * 将dip长度与pix像素相互转换
	 * 
	 * @author KEEN
	 * @lastChangeTime 2014年8月18日 下午3:07:08
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 生成随机6位数字并转换成字符串
	 * @author KEEN
	 * @lastChangeTime 2014年8月19日 上午11:32:11
	 * @return
	 */
	public static String randomCodeString(){
		int data = (int)(Math.random()*899999+100000);
		return String.valueOf(data);
	}
	
	public static boolean checkStringLength(String str,int length,LengthType lengthType){
		switch (lengthType) {
		case MaxLength:
			return str.length() <= length ? true : false;
		case MinLength:
			return str.length() >= length ? true : false;
		default:
			break;
		}
		return false;
	}
	
	public static enum LengthType{
		MaxLength,
		MinLength;
	}
	
	public static void closeInputMethod(Context context,View view) {

		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();

		if (isOpen) {
			imm.hideSoftInputFromWindow(view.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}
	
}