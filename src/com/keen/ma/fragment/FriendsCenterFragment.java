package com.keen.ma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keen.ma.R;

public class FriendsCenterFragment extends Fragment {
	
	public static String currentTitle;
	private TextView content;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle bundle = getArguments();
		if (bundle != null & bundle.containsKey("title"))
		{
			currentTitle = bundle.getString("title");
		}
		View contentView = inflater.inflate(R.layout.menu_fragment, null);
		content = (TextView)contentView.findViewById(R.id.menu1);
		content.setText("当前标题是："+currentTitle);
		return contentView;
	}
	
}
