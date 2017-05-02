package com.magni5.m5tracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.MileageUpdateModel;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.VehiclesDataModel;
import com.magni5.m5tracker.parsers.MileageUpdateParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;
import com.magni5.m5tracker.utils.Validations;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleViewEditFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = "VehicleViewEditFragment";
    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.et_display_name)
    EditText etDisplayName;
    @BindView(R.id.et_avg_mileage_long)
    EditText etAvgMileageLong;
    @BindView(R.id.et_registration_number)
    EditText etRegistrationNumber;
    @BindView(R.id.et_tracker_tag)
    EditText etTrackerTag;
    @BindView(R.id.et_last_service)
    EditText etLastService;

    private VehiclesDataModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.VEHICLE_VIEW_EDIT_MODEL)) {
            model = (VehiclesDataModel) bundle.getSerializable(Constants.VEHICLE_VIEW_EDIT_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_vechicle_view_edit, container, false);
        ButterKnife.bind(this, rootView);
        setData();
        return rootView;
    }

    private void setData() {
        etDisplayName.setText("" + model.getDisplayName());
        etAvgMileageLong.setText("" + model.getMileage());
        etRegistrationNumber.setText("" + model.getRegNumber());
        etTrackerTag.setText("" + model.getTrackerTag());
        etLastService.setText("" + model.getLastServicedDate());

    }

    @OnClick(R.id.btn_save)
    void onBtnSaveClick() {
        if (isValid()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            try {
                linkedHashMap.put("vehicleId", model.get_id());
                linkedHashMap.put("DisplayNumber", model.getDisplayNumber());
                linkedHashMap.put("Mileage", "" + etAvgMileageLong.getText().toString());
                linkedHashMap.put("RegNumber", model.getRegNumber());
                linkedHashMap.put("TrackerTag", model.getTrackerTag());
                MileageUpdateParser mMileageUpdateParser = new MileageUpdateParser();
                ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                        mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                        APIConstants.VEHICLE_UPDATE, linkedHashMap,
                        APIConstants.REQUEST_TYPE.POST, this, mMileageUpdateParser);
                Utility.execute(serverJSONAsyncTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btn_mark)
    void onBtnMarkClick() {
        if (isValid()) {
            try {
                MileageUpdateParser mMileageUpdateParser = new MileageUpdateParser();
                ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                        mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                        APIConstants.MARK_SERVCIE + model.get_id() + APIConstants.SERVICED, null,
                        APIConstants.REQUEST_TYPE.GET, this, mMileageUpdateParser);
                Utility.execute(serverJSONAsyncTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValid() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(etAvgMileageLong.getText().toString())) {
            isValid = false;
            Validations.setSnackBar(mParent, etAvgMileageLong, "Please enter Mileage");
        }
        return isValid;
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof MileageUpdateModel) {
                MileageUpdateModel mMileageUpdateModel = (MileageUpdateModel) model;
                Utility.showToastMessage(mParent, mMileageUpdateModel.getMessage());
            }
        }
    }
}
