package com.magni5.m5tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.SignInModel;
import com.magni5.m5tracker.parsers.SignInParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;
import com.magni5.m5tracker.utils.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

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

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            linkedHashMap.put("email", mUserName);
            linkedHashMap.put("password", mPassword);
            SignInParser mSignInParser = new SignInParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
                    APIConstants.SIGN_IN, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSignInParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Model model) {

        if (model != null) {
            if (model instanceof SignInModel){
                SignInModel signInModel = (SignInModel)model;
                if(signInModel.getStatus() == Constants.SUCCESS){
                    loginAndNavigation(signInModel);
                }
            }
        }
    }

    private void loginAndNavigation(SignInModel signInModel) {
        Intent dashBoardIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(dashBoardIntent);
    }
}
