package com.keen.ma.activity;

import com.keen.ma.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

/**
 * @author KEEN
 * @lastChangeTime 2014年8月13日 下午2:36:52
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(3000);
							startActivity(new Intent(MainActivity.this,LoginActivity.class));
							finish();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
