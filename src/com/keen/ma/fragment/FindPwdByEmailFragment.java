package com.keen.ma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.keen.ma.R;
import com.keen.ma.utils.AutoSendEmail;
import com.keen.ma.utils.ToolUtils;

/**
 * @author KEEN
 * @lastChangeTime 2014年8月18日 上午9:47:32
 */
public final class FindPwdByEmailFragment extends Fragment implements
		OnClickListener {

	private EditText emailAddET, codeET;
	private Button getCodeBtn, getPasswordBtn;
	private boolean emailIsValid = false;
	private boolean codeIsValid = false;
	private String code = "";// 验证码
	private AutoSendEmail emailSender = null;//发送邮件
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LinearLayout contentView = (LinearLayout) inflater.inflate(
				R.layout.viewpager_email, null);
		emailAddET = (EditText) contentView.findViewById(R.id.email_address);
		codeET = (EditText) contentView.findViewById(R.id.code_et);
		getCodeBtn = (Button) contentView.findViewById(R.id.get_code_btn);
		getCodeBtn.setOnClickListener(this);
		getPasswordBtn = (Button) contentView.findViewById(R.id.find_pwd_btn);
		getPasswordBtn.setOnClickListener(this);
		emailAddET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				emailIsValid = ToolUtils.isEmail(s.toString().trim());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (emailIsValid) {
					emailAddET.setBackgroundResource(R.drawable.login_edittext_valid_bg);
				} else
					emailAddET.setBackgroundResource(R.drawable.login_edittext_invalid_bg);
			}
		});

		codeET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				codeIsValid = ToolUtils.isCode(s.toString().trim());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (codeIsValid) {
					codeET.setBackgroundResource(R.drawable.login_edittext_valid_bg);
				} else
					codeET.setBackgroundResource(R.drawable.login_edittext_invalid_bg);
			}
		});
		return contentView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.get_code_btn:
			if (emailIsValid) {
				code = ToolUtils.randomCodeString();
				emailSender = new AutoSendEmail();
				emailSender.SendEmail(code, emailAddET.getText().toString().trim());
				if(AutoSendEmail.getErrorMessage().trim().equals("")){
					Toast.makeText(getActivity(), "成功发送邮件", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), AutoSendEmail.getErrorMessage(), Toast.LENGTH_SHORT).show();
					return;
				}
			} else {
				Toast.makeText(getActivity(), "请先输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.find_pwd_btn:
			if (!emailIsValid) {
				Toast.makeText(getActivity(), "请先输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
				return;
			} else if (!codeIsValid	|| !code.equals(codeET.getText().toString().trim())) {
				Toast.makeText(getActivity(), "请先输入正确的验证码", Toast.LENGTH_SHORT).show();
				return;
			} else if (emailIsValid && codeIsValid	&& code.equals(codeET.getText().toString().trim())) {
				Toast.makeText(getActivity(), "找回密码成功", Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}

	}

}
