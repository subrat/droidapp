package com.publiceye.android.model;

public class Complaint {

	private int dbId;
	private String imgpath;
	private String complientNumber;
	private String complientStatus;
	private double lat;
	private double lng;
	private String timeStamp;
	private String vehregNo;
	private String complientType;
	private String remarks;

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public String getComplientNumber() {
		return complientNumber;
	}

	public void setComplientNumber(String complientNumber) {
		this.complientNumber = complientNumber;
	}

	public String getComplientStatus() {
		return complientStatus;
	}

	public void setComplientStatus(String complientStatus) {
		this.complientStatus = complientStatus;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getVehregNo() {
		return vehregNo;
	}

	public void setVehregNo(String vehregNo) {
		this.vehregNo = vehregNo;
	}

	public String getComplientType() {
		return complientType;
	}

	public void setComplientType(String complientType) {
		this.complientType = complientType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}

}
