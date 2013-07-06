package com.publiceye.android.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

import com.publiceye.android.location.AppLocationManager;

public class AppUtil {

	private static AppLocationManager appLocationManager;
	private static int count = 1;
	/**
	 * To check SD card Availability
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean isSdCardAvailable() throws Exception {

		return android.os.Environment.getExternalStorageState().equals(	android.os.Environment.MEDIA_MOUNTED);

	}

	/**
	 * Images Directory path in SD card
	 * @return
	 * @throws Exception
	 */
	public static String getAppDataDirectoryPath() throws Exception {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sdCard = Environment.getExternalStorageDirectory();
			String path;
			path = sdCard.getAbsolutePath() + File.separator + "publiceye";
			return path;
		} else {
			throw new Exception("Can't mount media.");
		}
	}

	/**
	 * Image path extension
	 * @return
	 */
	public static String getImgPathExtension() {

		return new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date());
	}
	
	/**
	 * Time Stamp
	 * @return
	 */
	public static String getTimeStamp() {

		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
	}
	
	/**
	 * Complaint No
	 * @return
	 */
	
	public static String getComplaintNo() {
		String compNo = new SimpleDateFormat("ddMMyyyy").format(new Date())+ count++;
		return compNo;
	}

	
	
	public static AppLocationManager getAppLocationManager() {
		if (appLocationManager == null) {
			appLocationManager = new AppLocationManager();
		}
		return appLocationManager;
	}
}