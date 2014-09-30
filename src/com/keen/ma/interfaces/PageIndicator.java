/**
 * ViewPager 页面切换监听
 */
package com.keen.ma.interfaces;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * @author wan
 *
 */
public interface PageIndicator extends OnPageChangeListener{
	
	/**
	 * 绑定ViewPager
	 * @param view
	 */
	void setViewPager(ViewPager view);
	
	/**
	 * 绑定ViewPager
	 * @param view
	 * @param initialPosition 初始化选择的page的position
	 */
	void setViewPager(ViewPager view,int initialPosition);
	
	/**
	 * 设置ViewPager的当前page
	 * @param itemPosition
	 */
	void setCurrentItem(int itemPosition);
	
	/**
	 * 设置ViewPager的页面切换监听
	 * @param listener
	 */
	void setOnPageChangeListener(OnPageChangeListener listener);
	
	/**
	 * 更新Indicator
	 */
	void notifyDataSetChanged();
}
