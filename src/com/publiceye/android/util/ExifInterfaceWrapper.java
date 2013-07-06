package com.publiceye.android.util;

import java.io.IOException;

import android.media.ExifInterface;
import android.util.Log;

public class ExifInterfaceWrapper {
	
	private final String TAG = this.getClass().getName();
	
	
	public static final String TAG_DATETIME = ExifInterface.TAG_DATETIME;
	public static final String TAG_GPS_LATITUDE = ExifInterface.TAG_GPS_LATITUDE;
	public static final String TAG_GPS_LATITUDE_REF = ExifInterface.TAG_GPS_LATITUDE_REF;
	public static final String TAG_GPS_LONGITUDE = ExifInterface.TAG_GPS_LONGITUDE;
	public static final String TAG_GPS_LONGITUDE_REF = ExifInterface.TAG_GPS_LONGITUDE;
	
	
	private ExifInterface exifInterface;
	
	static {
		try {
			Class.forName("ExifInterface");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void checkAvailable() {}

	
	public ExifInterfaceWrapper(String filename){
		try{
			exifInterface = new ExifInterface(filename);
		} catch (IOException ex) {
			Log.d(TAG, ""+ex);
		}
	}
	
	public String getAttribute(String tag){
		return exifInterface.getAttribute(tag);
	}
	public int getAttributeInt(String tag, int defaultValue){
		return exifInterface.getAttributeInt(tag,defaultValue);
	}
	public boolean getLatLong(float[] output){
		return exifInterface.getLatLong(output);
	}
	public byte[] 	getThumbnail(){
		return exifInterface.getThumbnail();
	}
	public boolean hasThumbnail(){
		return exifInterface.hasThumbnail();
	}
	public void saveAttributes(){
		try {
			exifInterface.saveAttributes();
		} catch (IOException ex) {
			Log.d(TAG,""+ex);
		}
		
	}
	public void setAttribute(String tag, String value){
		exifInterface.setAttribute(tag,value);
	}

}
