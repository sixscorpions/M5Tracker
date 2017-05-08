package com.magni5.m5tracker.models;

/**
 * Created by Shankar on 5/8/2017.
 */

public class VehicleListNewModel {
    private String RegNumber;
    private String DisplayNumber;
    private int Mileage;
    private String LastServicedDate;
    private boolean ServicingNeeded;
    private String OwnerId;
    private String DisplayName;
    private String _id;
    private boolean isChecked;

    private int Version;
    private boolean IsLocked;
    private String TimeStamp;
    private String Tag;
    private String tracker_id;
    private LocationSpeedModel locationSpeedModel;
    private LatLagListModel latLagListModel;


    public String getRegNumber() {
        return RegNumber;
    }

    public void setRegNumber(String regNumber) {
        RegNumber = regNumber;
    }

    public String getDisplayNumber() {
        return DisplayNumber;
    }

    public void setDisplayNumber(String displayNumber) {
        DisplayNumber = displayNumber;
    }

    public int getMileage() {
        return Mileage;
    }

    public void setMileage(int mileage) {
        Mileage = mileage;
    }

    public String getLastServicedDate() {
        return LastServicedDate;
    }

    public void setLastServicedDate(String lastServicedDate) {
        LastServicedDate = lastServicedDate;
    }

    public boolean isServicingNeeded() {
        return ServicingNeeded;
    }

    public void setServicingNeeded(boolean servicingNeeded) {
        ServicingNeeded = servicingNeeded;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public boolean isLocked() {
        return IsLocked;
    }

    public void setLocked(boolean locked) {
        IsLocked = locked;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getTracker_id() {
        return tracker_id;
    }

    public void setTracker_id(String tracker_id) {
        this.tracker_id = tracker_id;
    }

    public LocationSpeedModel getLocationSpeedModel() {
        return locationSpeedModel;
    }

    public void setLocationSpeedModel(LocationSpeedModel locationSpeedModel) {
        this.locationSpeedModel = locationSpeedModel;
    }

    public LatLagListModel getLatLagListModel() {
        return latLagListModel;
    }

    public void setLatLagListModel(LatLagListModel latLagListModel) {
        this.latLagListModel = latLagListModel;
    }
}
