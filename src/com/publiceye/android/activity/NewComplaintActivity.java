package com.publiceye.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.publiceye.android.R;
import com.publiceye.android.db.ComplaintsDataBase;
import com.publiceye.android.location.AppLocationManager;
import com.publiceye.android.location.AppLocationResult;
import com.publiceye.android.util.AppUtil;

public class NewComplaintActivity extends Activity implements OnClickListener {

	private String imgpath;
	private AppLocationManager appLocationManager;

	private ImageView imgSubject;
	private Spinner spinnerVechType,spinnerComplType;
	private TextView textViewCompltNo, textViewCompltStatus,textViewLocLat, textViewLocLng,textViewTime;
	private EditText editTextRegdNo, editTextRemarks;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_new_complaint);
		// Image View
		imgSubject = (ImageView) findViewById(R.id.img_subject);
		spinnerVechType = (Spinner) findViewById(R.id.text_vehType);
		spinnerComplType = (Spinner) findViewById(R.id.text_compType);
		textViewCompltNo = (TextView) findViewById(R.id.text_complaint_no);
		textViewCompltStatus = (TextView) findViewById(R.id.text_comp_status);
		textViewLocLat = (TextView) findViewById(R.id.text_loc_lat);
		textViewLocLng = (TextView) findViewById(R.id.text_loc_lang);
		textViewTime = (TextView) findViewById(R.id.text_timestamp);
		editTextRegdNo = (EditText) findViewById(R.id.editText_regno);
		editTextRemarks = (EditText) findViewById(R.id.editText_remark);
		
		textViewTime.setText("Time: "+AppUtil.getTimeStamp());
		textViewCompltStatus.setText("Active");
		textViewCompltNo.setText("Complaint No: "+AppUtil.getComplaintNo());
		
		
		
		ArrayAdapter<CharSequence> adapterVechType = ArrayAdapter.createFromResource(this,R.array.vechtype_array, android.R.layout.simple_spinner_item);
		adapterVechType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerVechType.setAdapter(adapterVechType);
		
		ArrayAdapter<CharSequence> adapterComplType = ArrayAdapter.createFromResource(this,R.array.compltype_array, android.R.layout.simple_spinner_item);
		adapterComplType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerComplType.setAdapter(adapterComplType);
		
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
		findViewById(R.id.btn_back).setOnClickListener(this);

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
			complaintsDataBase.insert(imgpath,textViewCompltNo.getText().toString() , 
					textViewCompltStatus.getText().toString(),
					textViewTime.getText().toString(),
					editTextRegdNo.getText().toString().trim(), spinnerComplType.getSelectedItem().toString(), editTextRemarks.getText().toString().trim());
			complaintsDataBase.closeDB();
			break;

		case R.id.btn_back:
//			startActivity(new Intent(NewComplaintActivity.this,ComplaintListActivity.class));
			this.finish();
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
