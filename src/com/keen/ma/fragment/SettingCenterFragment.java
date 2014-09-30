package com.keen.ma.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keen.ma.R;
import com.keen.ma.activity.AboutSystemActivity;
import com.keen.ma.activity.ChangeWallPaperActivity;
import com.keen.ma.activity.FeedbackActivity;
import com.keen.ma.activity.MagicPicActivity;

/**
 * 设置中心
 * 
 * @author KEEN
 * @lastChangeTime 2014年8月21日 上午11:38:57
 */
public class SettingCenterFragment extends Fragment implements OnClickListener{

	private Button logoutBtn;
	private TextView profileCenterTV, stateTV, changePicTV, magicPicTV,
			clearCacheTV, checkUpdateTV, feedbackTV, aboutTV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View contentView = inflater.inflate(R.layout.fragment_setting_center,null);
		logoutBtn = (Button)contentView.findViewById(R.id.exit_button);
		logoutBtn.setOnClickListener(this);
		profileCenterTV = (TextView)contentView.findViewById(R.id.profile_center_tv);
		profileCenterTV.setOnClickListener(this);
		stateTV = (TextView)contentView.findViewById(R.id.state_tv);
		stateTV.setOnClickListener(this);
		changePicTV = (TextView)contentView.findViewById(R.id.change_under_pic_tv);
		changePicTV.setOnClickListener(this);
		magicPicTV = (TextView)contentView.findViewById(R.id.magic_picture_tv);
		magicPicTV.setOnClickListener(this);
		clearCacheTV = (TextView)contentView.findViewById(R.id.clear_cache_tv);
		clearCacheTV.setOnClickListener(this);
		checkUpdateTV = (TextView)contentView.findViewById(R.id.check_update_tv);
		checkUpdateTV.setOnClickListener(this);
		feedbackTV = (TextView)contentView.findViewById(R.id.feedback_tv);
		feedbackTV.setOnClickListener(this);
		aboutTV = (TextView)contentView.findViewById(R.id.about_tv);
		aboutTV.setOnClickListener(this);
		
		return contentView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exit_button://注销当前用户
			
			break;
		case R.id.profile_center_tv://个人中心
			
			break;
		case R.id.state_tv://状态设置
			
			break;
		case R.id.change_under_pic_tv://更换底图
			Intent changeWPIntent = new Intent(getActivity(),ChangeWallPaperActivity.class);
			startActivity(changeWPIntent);
			break;
		case R.id.magic_picture_tv://魔幻照片
			Intent magicPicIntent = new Intent(getActivity(),MagicPicActivity.class);
			startActivity(magicPicIntent);
			break;
		case R.id.clear_cache_tv://清除缓存
			Toast.makeText(getActivity(), "清除缓存成功", Toast.LENGTH_SHORT).show();
			break;
		case R.id.check_update_tv://检查更新
			Toast.makeText(getActivity(), "当前已经是最新板本了", Toast.LENGTH_SHORT).show();
			break;
		case R.id.feedback_tv://反馈中心
			Intent feedbackIntent = new Intent(getActivity(),FeedbackActivity.class);
			startActivity(feedbackIntent);
			break;
		case R.id.about_tv://关于我们
			Intent aboutIntent = new Intent(getActivity(),AboutSystemActivity.class);
			startActivity(aboutIntent);
			break;
			
		default:
			break;
		}
	}

}
