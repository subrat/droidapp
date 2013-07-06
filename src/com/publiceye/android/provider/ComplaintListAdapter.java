package com.publiceye.android.provider;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.publiceye.android.R;
import com.publiceye.android.model.Complaint;

public class ComplaintListAdapter extends ArrayAdapter<Complaint> {

	private List<Complaint> list;
	private LayoutInflater inflater;

	public ComplaintListAdapter(Context context, int textViewResourceId,
			List<Complaint> objects) {
		super(context, textViewResourceId, objects);
		list = objects;
		inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Complaint getItem(int position) {

		return list.get(position);
	}

	@Override
	public int getPosition(Complaint item) {

		return list.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (view == null)

		view = inflater.inflate(R.layout.item_complaints_list, null);
		Complaint complaint = list.get(position);
		ImageView imageViewStatus = (ImageView) view.findViewById(R.id.image_status);
		ImageView imageViewNav = (ImageView) view.findViewById(R.id.img_nav);
		ImageView imageViewSubject = (ImageView) view.findViewById(R.id.img_subject);
		try {
			imageViewSubject.setImageBitmap(getPhotoSearchImage(complaint.getImgpath()));

		} catch (Exception e) {
			Log.e("Image Loading Exception", ""+e.getMessage());
		}
		TextView textView_regdno = (TextView) view.findViewById(R.id.regd_no);
		TextView textView_complntype = (TextView) view.findViewById(R.id.comp_type);
		TextView textView_complremarks = (TextView) view.findViewById(R.id.comp_remark);
		TextView textView_time = (TextView) view.findViewById(R.id.date_time);
		
		textView_regdno.setText(complaint.getVehregNo());
		textView_complntype.setText(complaint.getComplientType());
		textView_time.setText(complaint.getTimeStamp());
		
		
		return view;
	}

	private Bitmap getPhotoSearchImage(String path) throws Exception {
		Log.v("", "Image Path Adapter---->" + path);
		Bitmap bitmap=BitmapFactory.decodeFile(path);
		return scaleBitmap(bitmap, 100,100);
	}
	
	public static Bitmap scaleBitmap(Bitmap unscaled, int scaleSizeWidth,
			int scaleSizeHeight) {
		if (unscaled == null) {
			return null;
		}

		int width = unscaled.getWidth();
		int height = unscaled.getHeight();
		float aspect = (float) width / (float) height;
		Bitmap scaled;
		if (aspect == 1f) {
			scaled = Bitmap.createScaledBitmap(unscaled, scaleSizeWidth,
					scaleSizeHeight, true);
		} else if (aspect < 1f) {
			float scale = (float) scaleSizeHeight / (float) height;
			scaled = Bitmap.createScaledBitmap(unscaled, (int) (width * scale),
					(int) (height * scale), true);
		} else {
			float scale = (float) scaleSizeWidth / (float) width;
			scaled = Bitmap.createScaledBitmap(unscaled, (int) (width * scale),
					(int) (height * scale), true);
		}

		unscaled.recycle();
		return scaled;
	}
}
