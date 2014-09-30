package com.keen.ma.db.dao;

import java.util.List;

import com.keen.ma.bean.UserBean;

/**
 * 用户表的增删改查
 * 
 * @author KEEN
 * @lastChangeTime 2014年8月25日 上午10:09:47
 */
public interface UserDAO {

	public void add(UserBean user);

	public void deleteById(String id);

	public void deleteByPhone(String phoneNumber);

	public void deleteByEmail(String email);

	public void updata(UserBean user);

	public List<UserBean> find();

	public UserBean findById(String id);

	public UserBean findByPhone(String phoneNumber);

	public UserBean findByEmail(String email);
}
