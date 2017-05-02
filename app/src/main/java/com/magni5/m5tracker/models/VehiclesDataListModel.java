package com.magni5.m5tracker.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shankar on 5/2/2017.
 */

public class VehiclesDataListModel extends Model implements Serializable {
    private ArrayList<VehiclesDataModel> vehiclesDataModels;
    private int status;

    public ArrayList<VehiclesDataModel> getVehiclesDataModels() {
        return vehiclesDataModels;
    }

    public void setVehiclesDataModels(ArrayList<VehiclesDataModel> vehiclesDataModels) {
        this.vehiclesDataModels = vehiclesDataModels;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
