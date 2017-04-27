package com.magni5.m5tracker.models;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class OwnerModel {
    private String DisplayName;
    private AddressModel Address;
    private String VehicleImageName;
    private String Type;
    private int OverspeedLimit;
    private int ServiceAlertDistanceKM;
    private String ActivatedOn;
    private String ActivationEndsOn;
    private String ActivationEndsOnString;
    private String VehicleImageUrl;
    private String Tag;
    private String _id;

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    public AddressModel getAddress() {
        return Address;
    }

    public void setAddress(AddressModel Address) {
        this.Address = Address;
    }

    public String getVehicleImageName() {
        return VehicleImageName;
    }

    public void setVehicleImageName(String VehicleImageName) {
        this.VehicleImageName = VehicleImageName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getOverspeedLimit() {
        return OverspeedLimit;
    }

    public void setOverspeedLimit(int OverspeedLimit) {
        this.OverspeedLimit = OverspeedLimit;
    }

    public int getServiceAlertDistanceKM() {
        return ServiceAlertDistanceKM;
    }

    public void setServiceAlertDistanceKM(int ServiceAlertDistanceKM) {
        this.ServiceAlertDistanceKM = ServiceAlertDistanceKM;
    }

    public String getActivatedOn() {
        return ActivatedOn;
    }

    public void setActivatedOn(String ActivatedOn) {
        this.ActivatedOn = ActivatedOn;
    }

    public String getActivationEndsOn() {
        return ActivationEndsOn;
    }

    public void setActivationEndsOn(String ActivationEndsOn) {
        this.ActivationEndsOn = ActivationEndsOn;
    }

    public String getActivationEndsOnString() {
        return ActivationEndsOnString;
    }

    public void setActivationEndsOnString(String ActivationEndsOnString) {
        this.ActivationEndsOnString = ActivationEndsOnString;
    }

    public String getVehicleImageUrl() {
        return VehicleImageUrl;
    }

    public void setVehicleImageUrl(String VehicleImageUrl) {
        this.VehicleImageUrl = VehicleImageUrl;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}