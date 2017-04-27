package com.magni5.m5tracker.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.LoginActivity;
import com.magni5.m5tracker.customviews.SnackBar;


/**
 * Created by Manikanta on 4/26/2017.
 */

public class Validations {

    public static void setSnackBar(AppCompatActivity parent, View mView, String message) {
        SnackBar snackBarIconTitle = new SnackBar();
        snackBarIconTitle.view(mView)
                .text(message, "OK")
                .textColors(Color.WHITE, Color.BLACK)
                .backgroundColor(Utility.getColor(parent, R.color.colorPrimary))
                .duration(SnackBar.SnackBarDuration.LONG)
                /*.setIconForTitle(Utility.getDrawable(parent, R.drawable.icon_error),
                        SnackBar.IconPosition.LEFT, 10)*/
                .show();
    }

    public static boolean isValidUserOrNot(AppCompatActivity mContext, EditText mUserName, EditText mPassword){
        boolean isValid = true;
        if(Utility.isValueNullOrEmpty(mUserName.getText().toString())){
            setSnackBar(mContext,mUserName,"Please enter username/email id");
            isValid = false;
        }else if(!mUserName.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")){
            setSnackBar(mContext,mUserName,"Please enter valid username/email id");
            isValid = false;
        }else if(Utility.isValueNullOrEmpty(mPassword.getText().toString())){
            setSnackBar(mContext,mPassword,"Please enter password");
            isValid = false;
        }/*else if (!mPassword.getText().toString().matches("(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\\w\\s]).{6,14}")){
            setSnackBar(mContext,mPassword,"Please enter valid password");
            isValid = false;
        }*/
        return isValid;
    }
}
