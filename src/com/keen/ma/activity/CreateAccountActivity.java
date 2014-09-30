package com.keen.ma.activity;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keen.ma.MyApplication;
import com.keen.ma.R;
import com.keen.ma.customui.MyAlertDialog;
import com.keen.ma.customui.MyCircleImageView;
import com.keen.ma.customui.PhotoChoicePopup;
import com.keen.ma.customui.MyAlertDialog.AlertDialogType;
import com.keen.ma.utils.ToolUtils;
import com.keen.ma.utils.ToolUtils.LengthType;

/**
 * @author KeenW
 * @lastChangeTime 2014-8-14下午12:45:44
 */
public class CreateAccountActivity extends Activity implements OnClickListener{
	
	private static final int LOCAL_IMAGE_REQUESTCODE = 100;
	private static final int CAMERA_IAMGE_REQUESTCODE = 101;
	private SharedPreferences sp = null;
	private MyApplication myApp = null;
	
	private MyCircleImageView userImageView,tipsImageView;
	private Button createAccountBtn;
	private EditText accountET,passwordET,emailEt,phoneEt;
	private PhotoChoicePopup photoPopup;
	private TextView backTV;
	MyAlertDialog myAlertDialog = null;
	
	private boolean accountIsValid = true;
	private boolean pwdIsValid = false;
	private boolean emailValid = false;
	private boolean phoneValid = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		myApp = (MyApplication)getApplication();
		initUI();
	}
	
	private void initUI(){
		userImageView = (MyCircleImageView)findViewById(R.id.user_image);
		userImageView.setOnClickListener(this);
		tipsImageView = (MyCircleImageView)findViewById(R.id.tips_info);
		tipsImageView.setOnClickListener(this);
		createAccountBtn = (Button)findViewById(R.id.create_account_btn);
		createAccountBtn.setOnClickListener(this);
		backTV = (TextView)findViewById(R.id.back);
		backTV.setOnClickListener(this);
		myAlertDialog = new MyAlertDialog(CreateAccountActivity.this, AlertDialogType.OneButton);
		myAlertDialog.setPositiveButton(getResources().getString(R.string.is_known), null);
		myAlertDialog.show();
		accountET = (EditText)findViewById(R.id.account_et);
		accountET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				accountIsValid = ToolUtils.isNullString(s.toString().trim()) && ToolUtils.checkStringLength(s.toString(), 20, LengthType.MaxLength);
				System.out.println("========================"+accountIsValid);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(!accountIsValid){
					accountET.setBackgroundResource(R.drawable.login_edittext_valid_bg);
				}else
					accountET.setBackgroundResource(R.drawable.login_edittext_invalid_bg);
			}
		});
		passwordET = (EditText)findViewById(R.id.password_et);
		passwordET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				pwdIsValid = ToolUtils.checkPasswordType(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(pwdIsValid){
					passwordET.setBackgroundResource(R.drawable.login_edittext_valid_bg);
				}else
					passwordET.setBackgroundResource(R.drawable.login_edittext_invalid_bg);
			}
		});
		emailEt = (EditText)findViewById(R.id.email_et);
		emailEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				emailValid = ToolUtils.isEmail(s.toString().trim());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(emailValid){
					emailEt.setBackgroundResource(R.drawable.login_edittext_valid_bg);
				}else
					emailEt.setBackgroundResource(R.drawable.login_edittext_invalid_bg);
			}
		});
		phoneEt = (EditText)findViewById(R.id.phone_et);
		phoneEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				phoneValid = ToolUtils.isMobileNO(s.toString().trim());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(phoneValid){
					phoneEt.setBackgroundResource(R.drawable.login_edittext_valid_bg);
				}else
					phoneEt.setBackgroundResource(R.drawable.login_edittext_invalid_bg);
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sp = getSharedPreferences("KeenW", Context.MODE_PRIVATE);
		int num = sp.getInt("wallPaperNumber", 0);
		myApp.setCurrentWallPaper(this, num);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_image:
			photoPopup = new PhotoChoicePopup(CreateAccountActivity.this,this);  
			photoPopup.showAtLocation(CreateAccountActivity.this.findViewById(R.id.main_frame),
					Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置 
			break;
		case R.id.choose_local_image:
			//本地取图
			Intent intent = new Intent();  
            intent.setType("image/*");  /* 开启Pictures画面Type设定为image */  
            intent.setAction(Intent.ACTION_GET_CONTENT); /* 使用Intent.ACTION_GET_CONTENT这个Action */   
            startActivityForResult(intent, LOCAL_IMAGE_REQUESTCODE);
			
            photoPopup.dismiss();
			break;
		case R.id.choose_camera_image:
			//相机取图
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_IAMGE_REQUESTCODE);
			
			photoPopup.dismiss();
			break;
		case R.id.create_account_btn:
			//验证登录
			checkAccountCreation();
			break;
		case R.id.tips_info:
			myAlertDialog.show();
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 创建用户验证
	 */
	private void checkAccountCreation(){
		
		if(accountIsValid){//账户不正确
			accountET.setError(Html.fromHtml("<font size=5 color=red>"+"账户格式不正确"+"</font>"));
			accountET.requestFocus();
			return;
		}else if(!pwdIsValid){//密码不正确
			passwordET.setError(Html.fromHtml("<font size=5 color=red>"+"密码长度不能小于8"+"</font>"));
			passwordET.requestFocus();
			return;
		}else if(!emailValid){//邮箱不正确
			emailEt.setError(Html.fromHtml("<font size=5 color=red>"+"邮箱格式不正确"+"</font>"));
			emailEt.requestFocus();
			return;
		}else if(!phoneValid){//手机号不正确
			phoneEt.setError(Html.fromHtml("<font size=5 color=red>"+"手机号格式不正确"+"</font>"));
			phoneEt.requestFocus();
			return;
		}else if(ToolUtils.isNullString(passwordET.getText().toString())){//用户名密码验证失败
			accountET.setError(Html.fromHtml("<font size=5 color=red>"+"登录失败，请重新输入账户密码"+"</font>"));
			accountET.requestFocus();
			return;
		}else if(ToolUtils.isNullString(passwordET.getText().toString())){//用户名密码验证成功
			finish();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {  
			Uri uri = data.getData();  
			if(requestCode == LOCAL_IMAGE_REQUESTCODE && !ToolUtils.isNullString(uri.toString())){
	            Log.e("uri", "================================"+uri.toString());  
	            ContentResolver cr = getContentResolver();  
	            try {  
	                /* 将Bitmap设定到ImageView */  
	                userImageView.setImageBitmap(BitmapFactory.decodeStream(cr.openInputStream(uri)));  
	                //得到照片名称
	                Cursor cursor = cr.query(uri, null, null, null, null);
	                cursor.moveToFirst();
	               // String imgNo = cursor.getString(0); //图片编号
	               // String imgPath = cursor.getString(1); //图片文件路径
	               // String imgSize = cursor.getString(2); //图片大小
	               // String imgName = cursor.getString(3); //图片文件名
	               // myApp.setUserImageUrl(cursor.getString(1));
	                
	            } catch (FileNotFoundException e) {  
	                Log.e("Exception", e.getMessage(),e);  
	            }  
			}else if(requestCode == CAMERA_IAMGE_REQUESTCODE){
				//相机拍照结果处理
				if(uri == null){
					Bundle bundle = data.getExtras();
					if(bundle != null){
						Bitmap bitmap = (Bitmap)bundle.get("data");
						 userImageView.setImageBitmap(bitmap);  
					}
				}else{
					Toast.makeText(this, "拍摄图片获取失败，请重试...", Toast.LENGTH_SHORT).show();
					return;
				}
			}else{
				Toast.makeText(this, "图片获取失败，请重试...", Toast.LENGTH_SHORT).show();
				return;
			}
        }  
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(photoPopup != null && photoPopup.isShowing())
				photoPopup.dismiss();
			else
				finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
