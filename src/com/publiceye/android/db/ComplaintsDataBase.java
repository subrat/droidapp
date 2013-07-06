package com.publiceye.android.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.publiceye.android.model.Complaint;

public class ComplaintsDataBase {

	private ComplaintsDataBaseHelper databaseHelper;
	private SQLiteDatabase database;
	private static Context context;
	private static ComplaintsDataBase complaintsDataBase;

	public static final String TABLE_NAME = "publiceye";
	private static final String KEY_DB_ID = "dbid";
	private static final String KEY_IMG_PATH = "img_path";
	private static final String KEY_CMPLNT_NUMBER = "cmplnt_no";
	private static final String KEY_CMPLNT_STATUS = "cmplnt_status";
	private static final String KEY_LAT = "loc_lat";
	private static final String KEY_LNG = "loc_lng";
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_VEH_REGNO = "veh_regno";
	private static final String KEY_CMPLNT_TYPE = "cmplnt_type";
	private static final String KEY_REMARKS = "cmplnt_remarks";

	static final String TABLE_NAME_CREATE = "CREATE TABLE "
			+ TABLE_NAME + "(" + KEY_DB_ID
			+ " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
			+ KEY_IMG_PATH
			+ " text, " 
			+ KEY_CMPLNT_NUMBER + " text, "
			+ KEY_CMPLNT_STATUS
			+ " text, " + KEY_LAT + " text, " + KEY_LNG + " text, "
			+ KEY_TIMESTAMP + " text, " + KEY_VEH_REGNO + " text, "
			+ KEY_CMPLNT_TYPE + " text, " + KEY_REMARKS + " text "
			+ ");";

	public static ComplaintsDataBase  getComplaintsDataBase(Context ctxt) {
		context = ctxt;
	
		if (complaintsDataBase==null) {
			complaintsDataBase=new ComplaintsDataBase();
		}
		return complaintsDataBase;
	}

	public void openDB() {

		databaseHelper = ComplaintsDataBaseHelper.getDbHelper(context);
		database = databaseHelper.getWritableDatabase();
	}

	public void closeDB() {
		database.close();
	}

	public void insert(String img_path, String cmplnt_no, String cmplnt_status,String timestamp,String veh_regno,String cmplnt_type,String cmplnt_remarks) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_IMG_PATH, img_path);
		cv.put(KEY_CMPLNT_NUMBER, cmplnt_no);
		cv.put(KEY_CMPLNT_STATUS, cmplnt_status);
		cv.put(KEY_LAT, "0.0");
		cv.put(KEY_LNG, "0.0");
		cv.put(KEY_TIMESTAMP, "dvfav");
		cv.put(KEY_VEH_REGNO , veh_regno);
		cv.put(KEY_CMPLNT_TYPE, cmplnt_type);
		cv.put(KEY_REMARKS, cmplnt_remarks);
		database.insert(TABLE_NAME, null, cv);
	}

	public void update(String img_path, String cmplnt_no, String cmplnt_status,String timestamp,String veh_regno,String cmplnt_type,String cmplnt_remarks) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_IMG_PATH, img_path);
		cv.put(KEY_CMPLNT_NUMBER, cmplnt_no);
		cv.put(KEY_CMPLNT_STATUS, cmplnt_status);
		cv.put(KEY_TIMESTAMP, timestamp);
		cv.put(KEY_VEH_REGNO , veh_regno);
		cv.put(KEY_CMPLNT_TYPE, cmplnt_type);
		cv.put(KEY_REMARKS, cmplnt_remarks);
		database.update(TABLE_NAME, cv, " where " + KEY_DB_ID + "=?",
				new String[] { String.valueOf(1) });
	}

	public void delete(int dbId) {
		database.delete(TABLE_NAME, KEY_DB_ID + " =?",
				new String[] { String.valueOf(dbId) });
	}

	public List<Complaint> getComplaintList() {
		List<Complaint> complients = new ArrayList<Complaint>();
		String query = "SELECT  * FROM " + TABLE_NAME;
		Cursor cursor = database.rawQuery(query, null);
		if (null == cursor || cursor.getCount() < 1) {
			cursor.close();
			return complients;
		}
		cursor.moveToFirst();

		if (cursor != null) {
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {

				Complaint complient = new Complaint();
				complient.setDbId(cursor.getInt(0));
				complient.setImgpath(cursor.getString(1));
				complient.setComplientNumber(cursor.getString(2));
				complient.setComplientStatus(cursor.getString(3));
				complient.setLat(Double.parseDouble(cursor.getString(4)));
				complient.setLng(Double.parseDouble(cursor.getString(5)));
				complient.setTimeStamp(cursor.getString(6));
				complient.setVehregNo(cursor.getString(7));
				complient.setComplientType(cursor.getString(8));
				complient.setRemarks(cursor.getString(9));
				complients.add(complient);
				cursor.moveToNext();
			}
			cursor.close();
		}

		return complients;
	}

	
}