package com.magni5.m5tracker.models;

import java.io.Serializable;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class RoleModel extends Model implements Serializable {
    private String DisplayName;
    private String Tag;
    private String _id;

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
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