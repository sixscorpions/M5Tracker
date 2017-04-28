package com.magni5.m5tracker.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/28/2017.
 */

public class VehicleListModel extends Model {
    private ArrayList<VehicleModel> vehicleModelArrayList;
    private ArrayList<TrackerModel> trackerModelArrayList;
    private int status;

    public ArrayList<VehicleModel> getVehicleModelArrayList() {
        return vehicleModelArrayList;
    }

    public void setVehicleModelArrayList(ArrayList<VehicleModel> vehicleModelArrayList) {
        this.vehicleModelArrayList = vehicleModelArrayList;
    }

    public ArrayList<TrackerModel> getTrackerModelArrayList() {
        return trackerModelArrayList;
    }

    public void setTrackerModelArrayList(ArrayList<TrackerModel> trackerModelArrayList) {
        this.trackerModelArrayList = trackerModelArrayList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
