package com.keen.ma.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.keen.ma.R;
import com.keen.ma.activity.HomeActivity;
import com.keen.ma.activity.MagicPicActivity;
import com.keen.ma.utils.ToolUtils;

/**
 * 首页中心
 * @author KEEN
 * @lastChangeTime 2014年8月21日 上午9:22:46
 */
public class HomeCenterFragment extends Fragment implements OnClickListener,OnFocusChangeListener{
	
	private Button magicPicCenterBtn,messageCenterBtn,friendsCenterBtn,
					mapCenterBtn,shareCenterBtn,settingCenterBtn;
	
	private Button searchBtn;
	private EditText searchET;
	private LinearLayout searchLayout;
	private View getFocusView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View contentView = inflater.inflate(R.layout.fragment_home_center, null);
		searchBtn = (Button)contentView.findViewById(R.id.search_btn);
		searchBtn.setOnClickListener(this);
		getFocusView = (View)contentView.findViewById(R.id.get_focus);
		searchET = (EditText)contentView.findViewById(R.id.search_et);
		searchET.setOnFocusChangeListener(this);
		searchET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.toString().trim().length() > 0){
					searchBtn.setText("搜索");
				}else{
					searchBtn.setText("取消");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		searchLayout = (LinearLayout)contentView.findViewById(R.id.search_layout);
		
		magicPicCenterBtn = (Button)contentView.findViewById(R.id.magic_pic_center_btn);
		magicPicCenterBtn.setOnClickListener(this);
		messageCenterBtn = (Button)contentView.findViewById(R.id.message_center_btn);
		messageCenterBtn.setOnClickListener(this);
		friendsCenterBtn = (Button)contentView.findViewById(R.id.friends_center_btn);
		friendsCenterBtn.setOnClickListener(this);
		mapCenterBtn = (Button)contentView.findViewById(R.id.map_center_btn);
		mapCenterBtn.setOnClickListener(this);
		shareCenterBtn = (Button)contentView.findViewById(R.id.share_center_btn);
		shareCenterBtn.setOnClickListener(this);
		settingCenterBtn = (Button)contentView.findViewById(R.id.setting_center_btn);
		settingCenterBtn.setOnClickListener(this);
		
		return contentView;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.search_et:
			if (hasFocus) {
				searchBtn.setVisibility(View.VISIBLE);
				searchET.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
				searchLayout.setBackgroundColor(Color.parseColor("#38EAEAEA"));
			} else {
				searchBtn.setVisibility(View.GONE);
				searchET.setGravity(Gravity.CENTER);
				searchLayout.setBackgroundColor(Color.TRANSPARENT);
			}
			break;
		
		default:
			break;
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_btn:
			String keyStr = searchET.getText().toString().trim();
			if(keyStr.length() > 0){//内容不为空，可以进行搜索
				Toast.makeText(getActivity(), "正在搜索关键字："+keyStr, Toast.LENGTH_SHORT).show();
			}else{//内容为空，searchET失去焦点
				getFocusView.requestFocus();
				ToolUtils.closeInputMethod(getActivity(), searchET);
			}
			break;
		case R.id.magic_pic_center_btn:
			Intent magicPicIntent = new Intent(getActivity(),MagicPicActivity.class);
			startActivity(magicPicIntent);
			break;
		case R.id.setting_center_btn:
			HomeActivity.setCurrentFragment(4);
			break;
		case R.id.message_center_btn:
			HomeActivity.setCurrentFragment(1);
			break;
		case R.id.friends_center_btn:
			HomeActivity.setCurrentFragment(2);
			break;
		case R.id.map_center_btn:
			HomeActivity.setCurrentFragment(3);
			break;
		case R.id.share_center_btn:
			Toast.makeText(getActivity(), "进入分享中心了", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
}
