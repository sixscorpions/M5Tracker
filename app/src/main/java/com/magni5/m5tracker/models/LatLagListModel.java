package com.magni5.m5tracker.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by shankar on 4/29/2017.
 */

public class LatLagListModel extends Model {
    private String TrackerId;
    private String TodayOnTime;
    private long TodayOnTimeMs;
    private int status;
    private PolylineOptions polylineOptions;
    private LatLng latLng;
    private ArrayList<LatLng> latLngArrayList;

    public String getTrackerId() {
        return TrackerId;
    }

    public void setTrackerId(String trackerId) {
        TrackerId = trackerId;
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }

    public void setPolylineOptions(PolylineOptions polylineOptions) {
        this.polylineOptions = polylineOptions;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public ArrayList<LatLng> getLatLngArrayList() {
        return latLngArrayList;
    }

    public void setLatLngArrayList(ArrayList<LatLng> latLngArrayList) {
        this.latLngArrayList = latLngArrayList;
    }

    public String getTodayOnTime() {
        return TodayOnTime;
    }

    public void setTodayOnTime(String todayOnTime) {
        TodayOnTime = todayOnTime;
    }

    public long getTodayOnTimeMs() {
        return TodayOnTimeMs;
    }

    public void setTodayOnTimeMs(long todayOnTimeMs) {
        TodayOnTimeMs = todayOnTimeMs;
    }
}
