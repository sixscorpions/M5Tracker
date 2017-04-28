package com.magni5.m5tracker.models;

/**
 * Created by Shankar on 4/28/2017.
 */

public class TrackerModel {
    private int Version;
    private boolean IsLocked;
    private String TimeStamp;
    private String Tag;
    private String _id;

    public int getVersion() {
        return Version;
    }

    public void setVersion(int Version) {
        this.Version = Version;
    }

    public boolean getIsLocked() {
        return IsLocked;
    }

    public void setIsLocked(boolean IsLocked) {
        this.IsLocked = IsLocked;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
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
