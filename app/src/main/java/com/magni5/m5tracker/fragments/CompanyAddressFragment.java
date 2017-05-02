package com.magni5.m5tracker.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.SettingsUpdateModel;
import com.magni5.m5tracker.models.SignInModel;
import com.magni5.m5tracker.parsers.SettingsUserDetailsParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Manikanta on 5/1/2017.
 */

public class CompanyAddressFragment extends Fragment implements IAsyncCaller {


    public static String TAG = "CompanyAddressFragment";

    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.lly_company_address)
    LinearLayout llyCompanyAddress;
    @BindView(R.id.et_street)
    EditText etStreet;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_state)
    EditText etState;
    @BindView(R.id.et_country)
    EditText etCountry;
    @BindView(R.id.et_pin_code)
    EditText etPinCode;
    @BindView(R.id.et_latitude)
    EditText etLatitude;
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    @BindView(R.id.btn_save)
    Button btn_save;

/*    private SignInModel signInModel;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();

        Bundle bundle = getArguments();
      /*  if (bundle != null) {
            if (bundle.containsKey(Constants.LOGIN_DATA_MODEL))
                signInModel = (SignInModel) bundle.getSerializable(Constants.LOGIN_DATA_MODEL);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView!=null){
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_company_address, container, false);
        ButterKnife.bind(this, rootView);
        mParent.toolbar.setTitle("Company Address");
        initUI();
        return rootView;
    }

    /**
     * To initiate UI
     */
    private void initUI() {
/*        if (signInModel != null) {*/
            etCity.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_CITY)) ? "" :
                    Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_CITY));

            etStreet.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_STREET)) ? "" :
                    Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_STREET));

        etState.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_STATE)) ? "" :
                Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_STATE));

        etCountry.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_COUNTRY)) ? "" :
                Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_COUNTRY));

            etPinCode.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_PIN)) ? "" :
                    Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_PIN));

            etLatitude.setText("" + Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_LAT));

            etLongitude.setText("" + Utility.getSharedPrefStringData(mParent,Constants.COMPANY_ADDRESS_LANG));

    }

    @OnClick(R.id.btn_save)
    void onBtnSaveClick() {
        userDetailsApiCall();
        //TODO implement
    }

    private void userDetailsApiCall() {

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            linkedHashMap.put("ip_ad_street", etStreet.getText().toString());
            linkedHashMap.put("ip_ad_city", etCity.getText().toString());
            linkedHashMap.put("ip_ad_state", etState.getText().toString());
            linkedHashMap.put("ip_ad_country", etCountry.getText().toString());
            linkedHashMap.put("ip_ad_pincode", etPinCode.getText().toString());
            linkedHashMap.put("ip_ad_latitude", etLatitude.getText().toString());
            linkedHashMap.put("ip_ad_longitude", etLongitude.getText().toString());

            SettingsUserDetailsParser mSettingsUserDetailsParser = new SettingsUserDetailsParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.ADDRESS_UPDATE, linkedHashMap,
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
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_ADDRESS_STREET, etStreet.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_ADDRESS_CITY, ""+etCity.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_ADDRESS_STATE, ""+etState.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_ADDRESS_COUNTRY, etCountry.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_ADDRESS_PIN, etPinCode.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_ADDRESS_LAT, ""+etLatitude.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_ADDRESS_LANG, ""+etLongitude.getText().toString());

                    mParent.onBackPressed();
                }

            }
        }
    }
}