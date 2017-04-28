package com.magni5.m5tracker.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.adapter.SelectVehicleAdapter;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.VehicleListModel;
import com.magni5.m5tracker.models.VehicleModel;
import com.magni5.m5tracker.parsers.VehicleListParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = "HomeFragment";
    private MainActivity mParent;
    private View rootView;

    private VehicleListModel vehicleListModel;
    public static ArrayList<VehicleModel> vehicleModelArrayList;

    @BindView(R.id.tv_live_tracking_details_header)
    TextView tvLiveTrackingDetailsHeader;

    @BindView(R.id.btn_select_vehicles)
    Button btn_select_vehicles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    /**
     * Initialize the ui and sets the type face and Find the listeners
     */
    private void initUI() {
        getVehiclesData();
    }

    private void getVehiclesData() {
        try {
            VehicleListParser mVehicleListParser = new VehicleListParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.VEHICLE_DETAILS, null,
                    APIConstants.REQUEST_TYPE.GET, this, mVehicleListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof VehicleListModel) {
                vehicleListModel = (VehicleListModel) model;
                vehicleModelArrayList = vehicleListModel.getVehicleModelArrayList();
            }
        } else {
            Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.something_went_wrong));
        }
    }

    @OnClick(R.id.btn_select_vehicles)
    public void navigateToPreVerification() {

        final Dialog mDialog = new Dialog(mParent);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.layout_alert_dialog_title);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);

        TextView tv_title = (TextView) mDialog.findViewById(R.id.tv_alert_dialog_title);
        tv_title.setBackgroundColor(Utility.getColor(mParent, R.color.home_type_blue_bg));
        tv_title.setText(Utility.getResourcesString(mParent, R.string.select_vehicles));
        tv_title.setTextColor(Utility.getColor(mParent, R.color.white));

        TextView tv_alert_dialog_button = (TextView) mDialog.findViewById(R.id.tv_alert_dialog_button);
        tv_alert_dialog_button.setBackgroundColor(Utility.getColor(mParent, R.color.home_type_blue_bg));
        tv_alert_dialog_button.setText(Utility.getResourcesString(mParent, R.string.select));
        tv_alert_dialog_button.setTextColor(Utility.getColor(mParent, R.color.white));

        ListView listView = (ListView) mDialog.findViewById(R.id.ll_list);
        SelectVehicleAdapter selectVehicleAdapter = new SelectVehicleAdapter(mParent);
        listView.setAdapter(selectVehicleAdapter);

        tv_alert_dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> integers = new ArrayList<>();
                if (vehicleModelArrayList != null && vehicleModelArrayList.size() > 0) {
                    for (int i = 0; i < vehicleModelArrayList.size(); i++) {
                        if (vehicleModelArrayList.get(i).isChecked()) {
                            integers.add(i);
                        }
                    }
                }
            }
        });
        mDialog.show();
    }
}
