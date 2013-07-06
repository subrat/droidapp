package com.publiceye.android.location;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class AppLocationManager {

	Timer timer1;
	private LocationManager locationManager;
	private AppLocationResult locationResult;
	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	Location net_loc = null, gps_loc = null;
	private final String TAG = this.getClass().getName();


	public LocationManager getLocationManager(Context context) {
		if (locationManager == null) {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		}
		return locationManager;
	}

	public boolean getLocation(Context context, AppLocationResult result) {
		locationResult = result;
		if (locationManager == null)
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		gps_enabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		network_enabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (!gps_enabled && !network_enabled)
			return false;
		if (gps_enabled)
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		if (network_enabled)
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0,
					locationListenerNetwork);
		timer1 = new Timer();
		timer1.schedule(new GetLastLocation(), 20000);
		return true;
	}

	public boolean areLocationProvidersEnabled(Context context) {
		boolean enabled = false;
		if (locationManager == null)
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

		enabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER)
				| locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		return enabled;
	}

	LocationListener locationListenerGps = new LocationListener() {
		public void onLocationChanged(Location location) {
			timer1.cancel();
			locationResult.gotLocation(location,locationManager);
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerNetwork);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	LocationListener locationListenerNetwork = new LocationListener() {
		public void onLocationChanged(Location location) {
			timer1.cancel();
			locationResult.gotLocation(location,locationManager);
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerGps);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	class GetLastLocation extends TimerTask {
		LocationHandler handler;

		GetLastLocation() {
			handler = new LocationHandler();
		}

		public LocationHandler getHandler() {
			return handler;
		}

		@Override
		public void run() {
			locationManager.removeUpdates(locationListenerGps);
			locationManager.removeUpdates(locationListenerNetwork);

			if (gps_enabled)
				gps_loc = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (network_enabled)
				net_loc = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			handler.sendEmptyMessage(0);
		}
	}

	private class LocationHandler extends Handler {
		@Override
		public final void handleMessage(Message msg) {
			// if there are both values use the latest one
			if (gps_loc != null && net_loc != null) {
				if (gps_loc.getTime() > net_loc.getTime())
					locationResult.gotLocation(gps_loc,locationManager);
				else
					locationResult.gotLocation(net_loc,locationManager);
				return;
			}

			if (gps_loc != null) {
				locationResult.gotLocation(gps_loc,locationManager);
				return;
			}
			if (net_loc != null) {
				locationResult.gotLocation(net_loc,locationManager);
				return;
			}
			locationResult.gotLocation(null,locationManager);
		}
	}
}
