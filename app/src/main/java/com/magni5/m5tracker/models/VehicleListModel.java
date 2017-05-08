package com.magni5.m5tracker.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/28/2017.
 */

public class VehicleListModel extends Model {
    private ArrayList<VehicleListNewModel> vehicleModelArrayList;
    private int status;

    public ArrayList<VehicleListNewModel> getVehicleModelArrayList() {
        return vehicleModelArrayList;
    }

    public void setVehicleModelArrayList(ArrayList<VehicleListNewModel> vehicleModelArrayList) {
        this.vehicleModelArrayList = vehicleModelArrayList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
