package com.magni5.m5tracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.models.VehiclesDataModel;
import com.magni5.m5tracker.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleViewEditFragment extends Fragment {

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

}
