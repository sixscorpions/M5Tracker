package com.magni5.m5tracker.models;

/**
 * Created by Shankar on 4/28/2017.
 */

public class VehicleModel {
    private String RegNumber;
    private String DisplayNumber;
    private int Mileage;
    private String LastServicedDate;
    private boolean ServicingNeeded;
    private String OwnerId;
    private String DisplayName;
    private String _id;
    private boolean isChecked;

    public String getRegNumber() {
        return RegNumber;
    }

    public void setRegNumber(String RegNumber) {
        this.RegNumber = RegNumber;
    }

    public String getDisplayNumber() {
        return DisplayNumber;
    }

    public void setDisplayNumber(String DisplayNumber) {
        this.DisplayNumber = DisplayNumber;
    }

    public int getMileage() {
        return Mileage;
    }

    public void setMileage(int Mileage) {
        this.Mileage = Mileage;
    }

    public String getLastServicedDate() {
        return LastServicedDate;
    }

    public void setLastServicedDate(String LastServicedDate) {
        this.LastServicedDate = LastServicedDate;
    }

    public boolean getServicingNeeded() {
        return ServicingNeeded;
    }

    public void setServicingNeeded(boolean ServicingNeeded) {
        this.ServicingNeeded = ServicingNeeded;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String OwnerId) {
        this.OwnerId = OwnerId;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isServicingNeeded() {
        return ServicingNeeded;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
