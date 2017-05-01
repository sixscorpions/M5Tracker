package com.magni5.m5tracker.models;

/**
 * Created by Manikanta on 5/1/2017.
 */

public class SettingsUpdateModel extends Model {


    private int mStatus;
    private String mMessage;

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
