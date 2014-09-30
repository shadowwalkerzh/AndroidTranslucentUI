package com.keen.ma.adapter;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keen.ma.R;
import com.keen.ma.activity.HomeActivity;
import com.keen.ma.bean.MenuItemBean;
import com.keen.ma.utils.MenuBaseUtils;

/**
 * @author KeenW
 * @lastChangeTime 2014-8-20ÉÏÎç10:23:35
 */
public class SlideMenuAdapter extends BaseAdapter {

	private ArrayList<MenuItemBean> menuItemList;
	private int selectedPosition = 0;
	private FragmentActivity fa;
	private DrawerLayout menuLayout;

	public SlideMenuAdapter(ArrayList<MenuItemBean> list,
			DrawerLayout menuLayout, FragmentActivity fa) {
		this.menuItemList = list;
		this.fa = fa;
		this.menuLayout = menuLayout;
	}

	public void setSelectedPosition(int position) {
		this.selectedPosition = position;
	}

	public int getSelectedPosition(){
		return selectedPosition;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return menuItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(fa).inflate(R.layout.menucontrol_item, null);
			viewHolder = new ViewHolder();
			viewHolder.menuItemName = (TextView) convertView.findViewById(R.id.menu_name);
			viewHolder.menuIcon = (ImageView)convertView.findViewById(R.id.menu_icon);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();

		viewHolder.menuItemName.setText(menuItemList.get(position).getMenuItemName());
		viewHolder.menuIcon.setImageResource(menuItemList.get(position).getMenuItemIcon());

		if (selectedPosition == position) {
			Fragment fragment = MenuBaseUtils.getFragmentByName(fa,"com.keen.ma.fragment", position);
			FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
			Bundle args = new Bundle();
			args.putString("title", menuItemList.get(position).getMenuItemName());
			fragment.setArguments(args);
			HomeActivity.setTitle(menuItemList.get(position).getMenuItemName());
			ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
			ft.replace(R.id.content_frame, fragment).commit();
			menuLayout.closeDrawer(GravityCompat.START);
			convertView.setBackgroundColor(fa.getResources().getColor(R.color.trans_light_green));
		} else {
			convertView.setBackgroundColor(Color.TRANSPARENT);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView menuIcon;
		TextView menuItemName;
	}

}
