package com.magni5.m5tracker.models;

import java.io.Serializable;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class DataModel extends Model implements Serializable{

    private UserModel User;
    private OwnerModel Owner;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public UserModel getUser() {
        return User;
    }

    public void setUser(UserModel User) {
        this.User = User;
    }

    public OwnerModel getOwner() {
        return Owner;
    }

    public void setOwner(OwnerModel Owner) {
        this.Owner = Owner;
    }
}
