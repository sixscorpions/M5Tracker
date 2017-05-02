package com.magni5.m5tracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.SettingsUpdateModel;
import com.magni5.m5tracker.models.SignInModel;
import com.magni5.m5tracker.parsers.SettingsUserDetailsParser;
import com.magni5.m5tracker.parsers.SignInParser;
import com.magni5.m5tracker.parsers.VehicleListParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;
import com.magni5.m5tracker.utils.Validations;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Manikanta on 5/1/2017.
 */

public class UserDetailsFragment extends Fragment implements IAsyncCaller {


    public static String TAG = "UserDetailsFragment";

    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.lly_user_details)
    LinearLayout llyUserDetails;
    @BindView(R.id.et_display_name)
    EditText etDisplayName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_save)
    Button btn_save;

/*    private SignInModel signInModel;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
      /*  Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(Constants.LOGIN_DATA_MODEL))
                signInModel = (SignInModel) bundle.getSerializable(Constants.LOGIN_DATA_MODEL);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, rootView);
        mParent.toolbar.setTitle("User Details");
        initUI();
        return rootView;
    }

    /**
     * To initiate UI
     */
    private void initUI() {

            etDisplayName.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.USER_DETAILS_NAME)) ? "" : Utility.getSharedPrefStringData(mParent,Constants.USER_DETAILS_NAME));
            etEmail.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.USER_DETAILS_EMAIL)) ? "" : Utility.getSharedPrefStringData(mParent,Constants.USER_DETAILS_EMAIL));
            etPhone.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.USER_DETAILS_PHONE)) ? "" : Utility.getSharedPrefStringData(mParent,Constants.USER_DETAILS_PHONE));

    }

    @OnClick(R.id.btn_save)
    void onBtnSaveClick() {
        if (Utility.isValueNullOrEmpty(etDisplayName.getText().toString())) {
            Validations.setSnackBar(mParent, etDisplayName, "Please enter display name");
        } else {
            userDetailsApiCall(etDisplayName.getText().toString());
        }
        //TODO implement
    }

    private void userDetailsApiCall(String mDisplayName) {

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            linkedHashMap.put("ip_userdisplayname", mDisplayName);
            SettingsUserDetailsParser mSettingsUserDetailsParser = new SettingsUserDetailsParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.USER_UPDATE, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSettingsUserDetailsParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof SettingsUpdateModel) {
                SettingsUpdateModel mSettingsUpdateModel = (SettingsUpdateModel) model;
                Utility.showToastMessage(mParent, mSettingsUpdateModel.getmMessage());
                if (mSettingsUpdateModel.getmStatus() == 1){
                    Utility.setSharedPrefStringData(mParent, Constants.USER_DETAILS_NAME, etDisplayName.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.USER_DETAILS_PHONE, etPhone.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.USER_DETAILS_EMAIL, etEmail.getText().toString());
                    mParent.onBackPressed();
                }
            }
        }
    }
}
