package com.magni5.m5tracker.models;

/**
 * Created by Shankar on 5/1/2017.
 */

public class AddMarkModel extends Model {

    private int status;
    private String UserId;
    private String VehicleId;
    private double Latitude;
    private double Longitude;
    private String DisplayName;
    private String LastHit;
    private String _id;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String VehicleId) {
        this.VehicleId = VehicleId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    public String getLastHit() {
        return LastHit;
    }

    public void setLastHit(String LastHit) {
        this.LastHit = LastHit;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
