package com.magni5.m5tracker.models;

import java.io.Serializable;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class SignInModel extends Model implements Serializable{

    private DataModel Data;
    private int Status;
    private String Message;
    private String Loginresponse;

    public String getLoginresponse() {
        return Loginresponse;
    }

    public void setLoginresponse(String loginresponse) {
        Loginresponse = loginresponse;
    }

    public DataModel getData() {
        return Data;
    }

    public void setData(DataModel Data) {
        this.Data = Data;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }


}
