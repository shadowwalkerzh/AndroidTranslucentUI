package com.keen.ma.bean;

import java.io.Serializable;

/**
 * 用户bean类
 * 
 * @author KeenW
 * @lastChangeTime 2014-8-17下午7:47:21
 */
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;// UID
	private String userNickName;// 用户昵称
	private String userPassword;// 用户密码
	private String userPhone;// 用户手机号
	private String userEmail;// 用户邮箱
	private String userImagePath;// 用户图像路径
	private double userLatitude;// 用户位置纬度
	private double userLongitude;// 用户位置经度

	public UserBean() {

	}

	public UserBean(String userId, String userNickName, String userPassword,
			String userPhone, String userEmail, String userImagePath,
			double userLatitude, double userLongitude) {
		this.userId = userId;
		this.userNickName = userNickName;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.userImagePath = userImagePath;
		this.userLatitude = userLatitude;
		this.userLongitude = userLongitude;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}

	public double getUserLatitude() {
		return userLatitude;
	}

	public void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}

	public double getUserLongitude() {
		return userLongitude;
	}

	public void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}

	@Override
	public String toString() {
		return "用户ID:" + userId + ";用户昵称：" + userNickName + ";用户密码："
				+ userPassword + ";用户手机：" + userPhone + ";用户邮箱：" + userEmail
				+ ";用户图像Url:" + userImagePath + "用户经度：" + userLongitude
				+ ";用户纬度：" + userLatitude;
	}
}
