package com.keen.ma.db.test;

import java.util.List;
import java.util.UUID;

import android.test.AndroidTestCase;

import com.keen.ma.bean.UserBean;
import com.keen.ma.db.impl.UserDaoImpl;

/**
 * 数据库功能单元测试
 * @author KEEN
 * @lastChangeTime 2014年8月25日 下午1:03:41
 */
public class DBTest extends AndroidTestCase {

	private String getUUID() {
		return UUID.randomUUID().toString();
	}

	public void testAdd() throws Exception {
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		String userId = getUUID();
		UserBean userBean = new UserBean(userId, "测试名称", "aassddff",
				"18325451125", "sddfsdf@163.com", "https://www.baidu.com",
				172.3025, 112.2541);
		userTest.add(userBean);
		System.out.println("===========添加的新用户=================="+userBean);
	}

	public void deleteById() throws Exception {
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		userTest.deleteById("12345678");
	}

	public void deleteByPhone() throws Exception {
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		userTest.deleteByPhone("12345678");
	}

	public void deleteByEmail() throws Exception {
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		userTest.deleteByEmail("12345678");
	}
	
	public void testFindById(){
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		UserBean userBean = userTest.findById("9761edcf-2f71-40c4-8e5f-c061b03a3348");
		System.out.println("===========根据ID查找到的用户=================="+userBean);
	}
	
	public void testFindByPhone(){
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		UserBean userBean = userTest.findByPhone("18325451125");
		System.out.println("===========根据手机号查找到的用户=================="+userBean);
	}
	public void testFindByEmail(){
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		UserBean userBean = userTest.findByEmail("sddfsdf@163.com");
		System.out.println("===========根据邮箱查找到的用户=================="+userBean);
	}
	
	public void testFind(){
		UserDaoImpl userTest = new UserDaoImpl(getContext());
		List<UserBean> userList = userTest.find();
		System.out.println("=============查找到所有用户数:================"+userList.size());
	}
}
