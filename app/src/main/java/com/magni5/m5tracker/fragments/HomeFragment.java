package com.magni5.m5tracker.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.adapter.SelectVehicleAdapter;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.LatLagListModel;
import com.magni5.m5tracker.models.LocationSpeedModel;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.VehicleListModel;
import com.magni5.m5tracker.models.VehicleModel;
import com.magni5.m5tracker.parsers.LatLngListParser;
import com.magni5.m5tracker.parsers.LocationSpeedParser;
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
public class HomeFragment extends Fragment implements IAsyncCaller, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static final String TAG = "HomeFragment";
    private MainActivity mParent;
    private View rootView;

    public static VehicleListModel vehicleListModel;
    public static ArrayList<VehicleModel> vehicleModelArrayList;
    public static ArrayList<LocationSpeedModel> locationSpeedModelArrayList;
    private ArrayList<LatLagListModel> locationLatLagListModels;

    @BindView(R.id.fab_select_car)
    FloatingActionButton fabSelectCar;
    @BindView(R.id.fab_details)
    FloatingActionButton fabDetails;

    private GoogleMap mMap;
    private SupportMapFragment supportMapFragment;
    private float mZoomLevel = 11.5f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    /**
     * Initialize the ui and sets the type face and Find the listeners
     */
    private void initUI() {


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        supportMapFragment = SupportMapFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.map, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);

        locationSpeedModelArrayList = new ArrayList<>();
        locationLatLagListModels = new ArrayList<>();
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
                getTrackersData();
                getTrackerPathsData();
            } else if (model instanceof LocationSpeedModel) {
                locationSpeedModelArrayList.add((LocationSpeedModel) model);
                setMarkerOnMap((LocationSpeedModel) model);
            } else if (model instanceof LatLagListModel) {
                locationLatLagListModels.add((LatLagListModel) model);
                LatLagListModel latLagListModel = (LatLagListModel) model;
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLagListModel.getLatLng()));
                setPathsData();
            }
        } else {
            Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.something_went_wrong));
        }
    }

    private void setMarkerOnMap(LocationSpeedModel model) {
        LatLng mLatLng = new LatLng(model.getLatitude(),
                model.getLongitude());
        Marker myMarker;
        if (model.getIgnition() == 1) {
            myMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.vehicle_ignition_on_marker))
                            .position(mLatLng)
                    /*.title(model.getMessage())*/);
        } else {
            myMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.vehicle_ignition_off_marker))
                            .position(mLatLng)
                    /*.title(model.getMessage())*/);
        }

        myMarker.setTag(model.get_id());
    }

    private void setPathsData() {
        if (locationLatLagListModels != null && locationLatLagListModels.size() > 0) {
            for (int i = 0; i < locationLatLagListModels.size(); i++) {
                mMap.addPolyline(locationLatLagListModels.get(i).getPolylineOptions().color(Utility.getColor(mParent, R.color.light_gray)));
            }
        }
    }


    private void setPathsData(ArrayList<Integer> integers) {
        if (locationLatLagListModels != null && locationLatLagListModels.size() > 0 && integers != null && integers.size() > 0) {
            mMap.clear();
            for (int i = 0; i < locationLatLagListModels.size(); i++) {
                if (integers.contains(i)) {
                    mMap.addPolyline(locationLatLagListModels.get(i).getPolylineOptions().color(Utility.getColor(mParent, R.color.light_gray)));
                }
            }
        }
    }

    private void getTrackerPathsData() {
        for (int i = 0; i < vehicleListModel.getTrackerModelArrayList().size(); i++) {
            getPathsData(vehicleListModel.getTrackerModelArrayList().get(i).get_id());
        }
    }

    @OnClick(R.id.fab_details)
    void setDataToTheLayout() {
        final Dialog mDialog = new Dialog(mParent);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.layout_alert_dialog_map_details);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);

        TextView tv_title = (TextView) mDialog.findViewById(R.id.tv_live_tracking_details);
        tv_title.setBackgroundColor(Utility.getColor(mParent, R.color.home_type_blue_bg));
        tv_title.setText(Utility.getResourcesString(mParent, R.string.live_tracking_details));
        tv_title.setTextColor(Utility.getColor(mParent, R.color.white));

        ImageView img_close = (ImageView) mDialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        LinearLayout ll_tackers_detail_list = (LinearLayout) mDialog.findViewById(R.id.ll_tackers_detail_list);
        ll_tackers_detail_list.removeAllViews();
        if (locationSpeedModelArrayList != null && locationSpeedModelArrayList.size() > 0) {
            for (int i = 0; i < locationSpeedModelArrayList.size(); i++) {
                LinearLayout itemList = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.tracker_list_item, null);

                TextView tv_vehicle = (TextView) itemList.findViewById(R.id.tv_vehicle);
                TextView tv_vehicle_value = (TextView) itemList.findViewById(R.id.tv_vehicle_value);

                TextView tv_speed = (TextView) itemList.findViewById(R.id.tv_speed);
                TextView tv_speed_value = (TextView) itemList.findViewById(R.id.tv_speed_value);

                TextView tv_distance_travelled = (TextView) itemList.findViewById(R.id.tv_distance_travelled);
                TextView tv_distance_travelled_value = (TextView) itemList.findViewById(R.id.tv_distance_travelled_value);

                TextView tv_running = (TextView) itemList.findViewById(R.id.tv_running);
                TextView tv_running_value = (TextView) itemList.findViewById(R.id.tv_running_value);

                TextView tv_time = (TextView) itemList.findViewById(R.id.tv_time);
                TextView tv_time_value = (TextView) itemList.findViewById(R.id.tv_time_value);

                TextView tv_ignition = (TextView) itemList.findViewById(R.id.tv_ignition);
                TextView tv_ignition_value = (TextView) itemList.findViewById(R.id.tv_ignition_value);

                TextView tv_signal = (TextView) itemList.findViewById(R.id.tv_signal);
                TextView tv_signal_value = (TextView) itemList.findViewById(R.id.tv_signal_value);

                Button button = (Button) itemList.findViewById(R.id.btn_immobilize);
                button.setTag(i);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int) view.getTag();
                    }
                });

                LocationSpeedModel locationSpeedModel = locationSpeedModelArrayList.get(i);
                for (int j = 0; j < vehicleListModel.getVehicleModelArrayList().size(); j++) {
                    if (locationSpeedModel.getTrackerId().equalsIgnoreCase(vehicleListModel.getTrackerModelArrayList().get(j).get_id())) {
                        tv_vehicle_value.setText("" + vehicleListModel.getVehicleModelArrayList().get(j).getDisplayName());
                    }
                }
                tv_speed_value.setText("" + locationSpeedModel.getSpeed());
                tv_distance_travelled_value.setText("" + getDistanceTravelled(locationSpeedModel.getTrackerId()) + " Km");
                tv_running_value.setText("Pending");
                tv_time_value.setText("" + locationSpeedModel.getEventDateTime());
                if (locationSpeedModel.getIgnition() == 1) {
                    tv_ignition_value.setText("On");
                } else {
                    tv_ignition_value.setText("Off");
                }

                tv_signal_value.setText("" + locationSpeedModel.getSignal());

                ll_tackers_detail_list.addView(itemList);
            }
        }
        mDialog.show();
    }

    private String getDistanceTravelled(String trackerId) {
        double distance = 0.0;
        for (int i = 0; i < locationLatLagListModels.size(); i++) {
            if (locationLatLagListModels.get(i).getTrackerId().equalsIgnoreCase(trackerId)) {
                distance = SphericalUtil.computeLength(locationLatLagListModels.get(i).getLatLngArrayList());
            }
        }
        Utility.showLog("distance", "distance :" + String.format("%.2f", distance / 1000));

        return String.format("%.2f", distance / 1000);
    }

    private void getTrackersData() {
        for (int i = 0; i < vehicleListModel.getTrackerModelArrayList().size(); i++) {
            getLocationsData(vehicleListModel.getTrackerModelArrayList().get(i).get_id());
        }
    }

    private void getLocationsData(String id) {
        try {
            LocationSpeedParser mLocationSpeedParser = new LocationSpeedParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.LOCATIONS + id, null,
                    APIConstants.REQUEST_TYPE.GET, this, mLocationSpeedParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPathsData(String id) {
        try {
            LatLngListParser mLatLngListParser = new LatLngListParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.VEHICLES_PATHS + id, null,
                    APIConstants.REQUEST_TYPE.GET, this, mLatLngListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fab_select_car)
    public void navigateToPreVerification() {

        final Dialog mDialog = new Dialog(mParent);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.layout_alert_dialog_title);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    setPathsData(integers);
                }
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(mZoomLevel));
        mMap.setMaxZoomPreference(21.0f);
    }
}
