package com.magni5.m5tracker.models;

/**
 * Created by shankar on 4/29/2017.
 */

public class LocationSpeedModel extends Model{

    private String TrackerId;
    private double Latitude;
    private double Longitude;
    private double Speed;
    private int Signal;
    private int Ignition;
    private String CreatedAt;
    private String EventDateTime;
    private String _id;
    private int status;

    public String getTrackerId() {
        return TrackerId;
    }

    public void setTrackerId(String TrackerId) {
        this.TrackerId = TrackerId;
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

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double Speed) {
        this.Speed = Speed;
    }

    public int getSignal() {
        return Signal;
    }

    public void setSignal(int Signal) {
        this.Signal = Signal;
    }

    public int getIgnition() {
        return Ignition;
    }

    public void setIgnition(int Ignition) {
        this.Ignition = Ignition;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public String getEventDateTime() {
        return EventDateTime;
    }

    public void setEventDateTime(String EventDateTime) {
        this.EventDateTime = EventDateTime;
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
