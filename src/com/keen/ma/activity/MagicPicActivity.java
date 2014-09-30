package com.keen.ma.activity;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.aviary.android.feather.FeatherActivity;
import com.aviary.android.feather.library.Constants;
import com.aviary.android.feather.library.utils.DecodeUtils;
import com.aviary.android.feather.library.utils.ImageSizes;
import com.keen.ma.MyApplication;
import com.keen.ma.R;
import com.keen.ma.utils.AppConstants;
import com.keen.ma.utils.ToolUtils;


/**
 * 魔幻照片页面
 * @author KeenW
 * @lastChangeTime 2014-8-23下午10:55:01
 */
public class MagicPicActivity extends BaseActivity implements OnCheckedChangeListener{

	private static final int LOCAL_IMAGE_REQUESTCODE = 100;
	private static final int CAMERA_IAMGE_REQUESTCODE = 101;
	private static final int AVIARY_RETURNED_PICTURE = 102;
	
	private ImageView showImageView;
	private RadioGroup selectImageRG;
	private MyApplication myApp = null;
	private String currentIamgePath = "";
	private Bitmap currentBitmap = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApplication)getApplication();
		myApp.addActivity(this);
		setContentLayout(R.layout.activity_magic_pic);
		setTitleText("魔幻照片");
		setRightIamgeViewShow();
		
		selectImageRG = (RadioGroup)findViewById(R.id.select_pic_radio);
		selectImageRG.setOnCheckedChangeListener(this);
		showImageView = (ImageView)findViewById(R.id.show_imageview);
		setRightImageViewResource(R.drawable.crop_icon);
		currentIamgePath = AppConstants.SDCARD_BATH_PATH+"/FEI_Test/1.jpg";
		currentBitmap = BitmapFactory.decodeFile(currentIamgePath);
		setRightIamgeViewListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(MagicPicActivity.this, FeatherActivity.class );
				newIntent.setData( Uri.parse(currentIamgePath) );
				newIntent.putExtra( Constants.EXTRA_IN_API_KEY_SECRET, AppConstants.AVIARY_SECRET );
				startActivityForResult( newIntent, AVIARY_RETURNED_PICTURE );
			}
		});
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.select_profile:
			currentIamgePath = AppConstants.SDCARD_BATH_PATH+"/FEI_Test/1.jpg";
			currentBitmap = BitmapFactory.decodeFile(currentIamgePath);
			showImageView.setImageBitmap(currentBitmap); 
			Toast.makeText(MagicPicActivity.this, "选择了我的头像", Toast.LENGTH_SHORT).show();
			break;
		case R.id.select_local_image:
			//本地取图
			Intent intent = new Intent();  
            intent.setType("image/*");  /* 开启Pictures画面Type设定为image */  
            intent.setAction(Intent.ACTION_GET_CONTENT); /* 使用Intent.ACTION_GET_CONTENT这个Action */   
            startActivityForResult(intent, LOCAL_IMAGE_REQUESTCODE);
			break;
			
		case R.id.select_camera_image:
			//相机取图
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_IAMGE_REQUESTCODE);
			break;
		default:
			break;
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
	            	Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
	            	currentBitmap = bitmap;
	            	showImageView.setImageBitmap(currentBitmap);  
	                //得到照片名称
	                Cursor cursor = cr.query(uri, null, null, null, null);
	                cursor.moveToFirst();
	               // String imgNo = cursor.getString(0); //图片编号
	                String imgPath = cursor.getString(1); //图片文件路径
	               // String imgSize = cursor.getString(2); //图片大小
	               // String imgName = cursor.getString(3); //图片文件名
	                
	                currentIamgePath = imgPath;
	            } catch (FileNotFoundException e) {  
	                Log.e("Exception", e.getMessage(),e);  
	            }  
			}else if(requestCode == CAMERA_IAMGE_REQUESTCODE){
				//相机拍照结果处理
				if(uri == null){
					Bundle bundle = data.getExtras();
					if(bundle != null){
						Bitmap bitmap = (Bitmap)bundle.get("data");
						 showImageView.setImageBitmap(bitmap); 
						 currentBitmap = bitmap;
						 //currentIamgePath = 
					}
				}else{
					Toast.makeText(this, "拍摄图片获取失败，请重试...", Toast.LENGTH_SHORT).show();
					return;
				}
			}else if(requestCode == AVIARY_RETURNED_PICTURE){
				// output image path
                Uri mImageUri = data.getData();
                Bundle extra = data.getExtras();
                    if( null != extra ) {
                        // image has been changed by the user?
                       // boolean changed = extra.getBoolean ( Constants.EXTRA_OUT_BITMAP_CHANGED );
                        ImageSizes sizes = new ImageSizes();
                        Bitmap  bitmap = DecodeUtils.decode( MagicPicActivity.this, mImageUri, 100, 100, sizes );
						 showImageView.setImageBitmap(bitmap); 
                    }

				Toast.makeText(this, "图片获取失败，请重试...", Toast.LENGTH_SHORT).show();
				return;
			}
        }  
		super.onActivityResult(requestCode, resultCode, data);
	}
}
