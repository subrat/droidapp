package com.publiceye.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ComplaintsDataBaseHelper extends SQLiteOpenHelper {

		public static ComplaintsDataBaseHelper baseHelper;
		private static final String DATABASE_NAME = "mydatabase";
		private static final int DATABASE_VERSION = 3;
		
		public static ComplaintsDataBaseHelper getDbHelper(Context context) {
			if(baseHelper==null)
				baseHelper=new ComplaintsDataBaseHelper(context, DATABASE_NAME,	null, DATABASE_VERSION);
			return baseHelper;
		}
		
		public ComplaintsDataBaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(ComplaintsDataBase.TABLE_NAME_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(" DROP TABLE IF EXIXT " + ComplaintsDataBase.TABLE_NAME_CREATE);
		}
	}