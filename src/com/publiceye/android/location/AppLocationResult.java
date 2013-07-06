package com.publiceye.android.location;

import android.location.Location;
import android.location.LocationManager;

public abstract class AppLocationResult{
    public abstract void gotLocation(Location location,LocationManager locationManager);
}
