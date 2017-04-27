package com.magni5.m5tracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.parsers.SignInParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Utility;
import com.magni5.m5tracker.utils.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements IAsyncCaller {

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
            signInApiCall(et_user_name.getText().toString(), et_password.getText().toString());
//api call

            Validations.setSnackBar(this, btn_submit, "api call");
     /*       Intent dashBoardIntent = new Intent(this, MainActivity.class);
            startActivity(dashBoardIntent);*/
        }
    }

    /**
     * initialize and set typefaces
     * setting data
     */
    private void initialization() {
    }

    /**
     * SIGN IN API CALL
     */
    private void signInApiCall(String mUserName, String mPassword) {

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", mUserName);
            jsonParam.put("password", mPassword);
            SignInParser mSignInParser = new SignInParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
                    APIConstants.SIGNIN, jsonParam,
                    APIConstants.REQUEST_TYPE.POST, this, mSignInParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Model model) {

    }
}
