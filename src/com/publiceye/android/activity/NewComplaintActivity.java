package com.publiceye.android.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.publiceye.android.R;
import com.publiceye.android.db.ComplaintsDataBase;
import com.publiceye.android.location.AppLocationManager;
import com.publiceye.android.location.AppLocationResult;
import com.publiceye.android.util.AppUtil;

public class NewComplaintActivity extends Activity implements OnClickListener {

	private String imgpath;
	private AppLocationManager appLocationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_new_complaint);
		// Image View
		ImageView imgSubject = (ImageView) findViewById(R.id.img_subject);
		imgpath = getIntent().getStringExtra("name");
		Bitmap bitmap;
		try {
			bitmap = getPhotoSearchImage(imgpath);
			imgSubject.setImageBitmap(bitmap);
		} catch (Exception e) {
		}

		appLocationManager = AppUtil.getAppLocationManager();
		appLocationManager.getLocation(getApplicationContext(), locationResult);
		findViewById(R.id.btn_submit).setOnClickListener(this);

	}

	private Bitmap getPhotoSearchImage(String path) throws Exception {
		Log.v("", "Image Path---->" + path);
		return BitmapFactory.decodeFile(path);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:

			ComplaintsDataBase complaintsDataBase = ComplaintsDataBase
					.getComplaintsDataBase(getApplicationContext());
			complaintsDataBase.openDB();
			complaintsDataBase.insert(imgpath, "fjnfaivbdg", "fafvbgdb",
					AppUtil.getTimeStamp(), "efgvreg", "feerwg", "FREFRWG");
			complaintsDataBase.closeDB();
			break;

		default:
			break;
		}

	}

	AppLocationResult locationResult = new AppLocationResult() {

		@Override
		public void gotLocation(Location location,
				LocationManager locationManager) {

		}
	};
}
