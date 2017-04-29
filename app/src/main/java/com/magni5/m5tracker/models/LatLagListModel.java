package com.magni5.m5tracker.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by shankar on 4/29/2017.
 */

public class LatLagListModel extends Model {
    private String TrackerId;
    private int status;
    private PolylineOptions polylineOptions;
    private LatLng latLng;

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
}
