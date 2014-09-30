package com.keen.ma;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.keen.ma.adapter.SlideMenuAdapter;
import com.keen.ma.bean.MenuItemBean;
import com.keen.ma.customui.MyAlertDialog;
import com.keen.ma.customui.MyAlertDialog.AlertDialogType;
import com.keen.ma.utils.MenuBaseUtils;

/**
 * @author KeenW
 * @lastChangeTime 2014-8-20上午10:23:22
 */
public class MenuControl implements OnClickListener,OnItemClickListener{

	private FragmentActivity fa = null;
	private DrawerLayout menuLayout;
	private ListView menuElementsList;
	private LinearLayout exitLayout;
	private LinearLayout profileLayout;
	private LinearLayout menuContentLayout;
	
	private SlideMenuAdapter slideMenuAdapter;
	private ArrayList<MenuItemBean> menuItemList;
	private MyAlertDialog myAlertDialog = null;
	private MyApplication myApp = null;
	
	public MenuControl(FragmentActivity fa) {
		this.fa = fa;
		initMenu();
	}

	public void initMenu() {
		menuLayout = (DrawerLayout) fa.findViewById(R.id.menu_layout);
		menuElementsList = (ListView) fa.findViewById(R.id.menu_elements);
		// 设置阴影
		menuLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		myApp = (MyApplication)fa.getApplication();
		
		menuItemList = new ArrayList<MenuItemBean>();
		menuItemList = MenuBaseUtils.arrayToList(fa);
		slideMenuAdapter = new SlideMenuAdapter(menuItemList,menuLayout,fa);
		menuElementsList.setAdapter(slideMenuAdapter);
		menuElementsList.setOnItemClickListener(this);
		setCurrentFragment(0);
		//设置侧滑菜单宽度
		menuContentLayout = (LinearLayout)menuLayout.findViewById(R.id.menu_content_layout);
		menuContentLayout.getLayoutParams().width = MenuBaseUtils.getScreenPixels(fa).getScreenWidth()*2/3;
		
		exitLayout = (LinearLayout)menuLayout.findViewById(R.id.exit_layout);
		exitLayout.setOnClickListener(this);
		profileLayout = (LinearLayout)menuLayout.findViewById(R.id.profile_layout);
		profileLayout.setOnClickListener(this);
	}

	public void setCurrentFragment(int position){
		slideMenuAdapter.setSelectedPosition(position);
		slideMenuAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 监听打开或关闭菜单
	 */
	public void toggleMenu() {

		if (menuLayout.isDrawerOpen(GravityCompat.START)) {
			menuLayout.closeDrawer(GravityCompat.START);
		} else
			menuLayout.openDrawer(GravityCompat.START);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.profile_layout:
			toggleMenu();
			break;
		case R.id.exit_layout:
			toggleMenu();
			myAlertDialog = new MyAlertDialog(fa, AlertDialogType.TwoButton);
			myAlertDialog.setMessage("亲，你确定要退出吗？");
			myAlertDialog.setNegativeButton("退出", new OnClickListener() {
				@Override
				public void onClick(View v) {
					myApp.exit();
				}
			});
			myAlertDialog.setPositiveButton("待会儿吧", null);
			myAlertDialog.show();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(MyApplication.CurrentMenuItem == position){
			return;
		}else{
			MyApplication.CurrentMenuItem = position;
			slideMenuAdapter.setSelectedPosition(position);
			slideMenuAdapter.notifyDataSetChanged();
		}
	}
}
