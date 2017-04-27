package com.magni5.m5tracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.utils.Utility;
import com.magni5.m5tracker.utils.Validations;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_submit)
    Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initialization();
    }

    /**
     * Submit click and api call
     */
    @OnClick(R.id.btn_submit)
    void editProfilePicture() {
        if (Validations.isValidUserOrNot(this, et_user_name, et_password)) {
//api call
            Validations.setSnackBar(this, btn_submit, "api call");
            Intent dashBoardIntent = new Intent(this, MainActivity.class);
            startActivity(dashBoardIntent);
        }
    }

    /**
     * initialize and set typefaces
     * setting data
     */
    private void initialization() {
    }

}
