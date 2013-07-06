package com.publiceye.android.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.publiceye.android.R;
import com.publiceye.android.db.ComplaintsDataBase;
import com.publiceye.android.model.Complaint;
import com.publiceye.android.provider.ComplaintListAdapter;
import com.publiceye.android.util.AppUtil;

public class ComplaintListActivity extends Activity {

	private ListView listViewComplaints;
	private Button mEyeButton;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	File file = null;
	private ComplaintListAdapter complaintListAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_complaintdetails);
		listViewComplaints = (ListView) findViewById(android.R.id.list);
		mEyeButton = (Button) findViewById(R.id.btn_eye);
		ComplaintsDataBase complaintsDataBase=ComplaintsDataBase.getComplaintsDataBase(getApplicationContext());
		complaintsDataBase.openDB();
		List<Complaint> complaints= complaintsDataBase.getComplaintList();
		complaintsDataBase.closeDB();
		complaintListAdapter=new ComplaintListAdapter(getApplicationContext(),0, complaints);
		listViewComplaints.setAdapter(complaintListAdapter);
		

		mEyeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				startActivity(new Intent(ComplaintListActivity.this,NewComplaintActivity.class));
				
				try {
					if(AppUtil.isSdCardAvailable()){
						file = new File(AppUtil.getAppDataDirectoryPath(),
								"publiceye_"+AppUtil.getImgPathExtension()+ ".jpeg");
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
						startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
					}else{
						showSdCardNotAvailable();
					}
				} catch (Exception e) {
					showSdCardNotAvailable();
				}
			}
		});

		
	}

	protected void showSdCardNotAvailable() {
		Toast.makeText(getApplicationContext(), "SD CARD NOT AVAILBLE IN DEVICE",Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent(this, NewComplaintActivity.class);
				intent.putExtra("name", file.getAbsolutePath());
				startActivity(intent);
				

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if(complaintListAdapter!=null){
			complaintListAdapter.notifyDataSetChanged();
		}
	}

}
