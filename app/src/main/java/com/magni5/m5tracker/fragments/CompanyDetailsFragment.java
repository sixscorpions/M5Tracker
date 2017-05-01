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
import com.magni5.m5tracker.parsers.SettingsUserDetailsParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Manikanta on 5/1/2017.
 */

public class CompanyDetailsFragment extends Fragment implements IAsyncCaller {


    public static String TAG = "CompanyDetailsFragment";

    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.lly_company_details)
    LinearLayout llyCompanyDetails;
    @BindView(R.id.ey_display_name)
    EditText eyDisplayName;
    @BindView(R.id.et_Overspeed_limit)
    EditText etOverspeedLimit;
    @BindView(R.id.et_service_alert_distance)
    EditText etServiceAlertDistance;
    @BindView(R.id.et_vehicle_map_marker_img)
    EditText etVehicleMapMarkerImg;
    @BindView(R.id.btn_save)
    Button btn_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_company_details, container, false);
        ButterKnife.bind(this, rootView);
        mParent.toolbar.setTitle("Company Details");
        initUI();
        return rootView;
    }

    /**
     * To initiate UI
     */
    private void initUI() {
    }

    @OnClick(R.id.btn_save)
    void onBtnSaveClick() {
        //TODO implement
    }

    private void userDetailsApiCall(String oldPwd, String newPwd, String retypePwd) {

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            linkedHashMap.put("ip_oldpw", oldPwd);
            linkedHashMap.put("ip_newpw", newPwd);
            linkedHashMap.put("ip_rpw", retypePwd);
            SettingsUserDetailsParser mSettingsUserDetailsParser = new SettingsUserDetailsParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.PASSWORD_CHANGE, linkedHashMap,
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
                if (mSettingsUpdateModel.getmStatus() == 1)
                    mParent.onBackPressed();
            }
        }
    }
}
