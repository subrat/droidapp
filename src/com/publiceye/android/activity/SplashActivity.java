package com.publiceye.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.publiceye.android.R;

public class SplashActivity extends Activity {
	private int SPLASH_DISPLAY_LENGHT = 1000;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash);
		

		new Handler().postDelayed(new Runnable() {
			public void run() {

				Intent intent = new Intent(SplashActivity.this,	ComplaintListActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);

	}
	
	

}
