package com.magni5.m5tracker.models;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class SignInModel extends Model{

    private DataModel Data;
    private int Status;
    private String Message;

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
