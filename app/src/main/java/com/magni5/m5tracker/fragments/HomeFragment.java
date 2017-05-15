package com.magni5.m5tracker.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.maps.model.PolylineOptions;
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
import com.magni5.m5tracker.models.VehicleListNewModel;
import com.magni5.m5tracker.parsers.LatLngListParser;
import com.magni5.m5tracker.parsers.LocationSpeedParser;
import com.magni5.m5tracker.parsers.VehicleListParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Utility;

import java.util.concurrent.TimeUnit;

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

    @BindView(R.id.fab_select_car)
    FloatingActionButton fabSelectCar;
    @BindView(R.id.fab_details)
    FloatingActionButton fabDetails;

    private GoogleMap mMap;
    private SupportMapFragment supportMapFragment;
    private float mZoomLevel = 11.5f;

    private Handler handler;
    private Runnable runnable;
    private int delay = 5000;
    private boolean isDataGot;
    private boolean isFirstTime;

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
        isDataGot = true;
        isFirstTime = true;
        handler = new Handler();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        supportMapFragment = SupportMapFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.map, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);
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
                getTrackersData();
                getTrackerPathsData();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        isFirstTime = false;
                        runnable = this;
                        handler.postDelayed(runnable, delay);
                        getTrackersData();
                    }
                }, delay);
            } else if (model instanceof LocationSpeedModel) {
                LocationSpeedModel locationSpeedModel = (LocationSpeedModel) model;
                if (vehicleListModel.getVehicleModelArrayList() != null && vehicleListModel.getVehicleModelArrayList().size() > 0)
                    for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
                        if (vehicleListModel.getVehicleModelArrayList().get(i).getTracker_id().equalsIgnoreCase(locationSpeedModel.getTrackerId())) {
                            Utility.showLog("isUpdated", "isUpdated");
                            VehicleListNewModel vehicleListNewModel = vehicleListModel.getVehicleModelArrayList().get(i);
                            vehicleListNewModel.setLocationSpeedModel(locationSpeedModel);
                            vehicleListModel.getVehicleModelArrayList().set(i, vehicleListNewModel);
                        }
                    }
                setMarkersData();
            } else if (model instanceof LatLagListModel) {
                LatLagListModel latLagListModel = (LatLagListModel) model;
                if (vehicleListModel.getVehicleModelArrayList() != null && vehicleListModel.getVehicleModelArrayList().size() > 0)
                    for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
                        if (vehicleListModel.getVehicleModelArrayList().get(i).getTracker_id().equalsIgnoreCase(latLagListModel.getTrackerId())) {
                            VehicleListNewModel vehicleListNewModel = vehicleListModel.getVehicleModelArrayList().get(i);
                            vehicleListNewModel.setLatLagListModel(latLagListModel);
                            vehicleListModel.getVehicleModelArrayList().set(i, vehicleListNewModel);
                        }
                    }
                setMarkersData();
            }
        } else {
            Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.something_went_wrong));
        }
    }

    @Override
    public void onPause() {
        isDataGot = true;
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    @Override
    public void onResume() {
        if (isDataGot) {
            isFirstTime = true;
            getVehiclesData();
        }
        super.onResume();
    }

    /*private void setPathsData() {
        if (vehicleListModel.getVehicleModelArrayList() != null && vehicleListModel.getVehicleModelArrayList().size() > 0) {
            for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
                if (vehicleListModel.getVehicleModelArrayList().get(i).isChecked())
                    mMap.addPolyline(vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel()
                            .getPolylineOptions().color(Utility.getColor(mParent, R.color.light_gray)));
            }
        }
    }*/

    private void getTrackerPathsData() {
        for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
            getPathsData(vehicleListModel.getVehicleModelArrayList().get(i).getTracker_id());
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

        ImageView img_refresh = (ImageView) mDialog.findViewById(R.id.img_refresh);
        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataToTheLayout();
                mDialog.dismiss();
            }
        });

        LinearLayout ll_tackers_detail_list = (LinearLayout) mDialog.findViewById(R.id.ll_tackers_detail_list);
        ll_tackers_detail_list.removeAllViews();
        if (vehicleListModel.getVehicleModelArrayList() != null && vehicleListModel.getVehicleModelArrayList().size() > 0) {
            for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
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
                ImageView img_signal = (ImageView) itemList.findViewById(R.id.img_signal);

                Button button = (Button) itemList.findViewById(R.id.btn_immobilize);
                button.setTag(i);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int) view.getTag();
                    }
                });

                LocationSpeedModel locationSpeedModel = vehicleListModel.getVehicleModelArrayList().get(i).getLocationSpeedModel();
                tv_vehicle_value.setText("" + vehicleListModel.getVehicleModelArrayList().get(i).getDisplayName());
                tv_speed_value.setText("" + String.format("%.2f", locationSpeedModel.getSpeed()));
                tv_distance_travelled_value.setText("" + getDistanceTravelled(locationSpeedModel.getTrackerId()) + " Km");
                tv_running_value.setText("" + getTravelledTime(locationSpeedModel.getTrackerId()));
                tv_time_value.setText("" + locationSpeedModel.getEventDateTime().substring(0, 10));
                if (locationSpeedModel.getIgnition() == 1) {
                    tv_ignition_value.setText("On");
                } else {
                    tv_ignition_value.setText("Off");
                }

                if (locationSpeedModel.getSignal() > 0 && locationSpeedModel.getSignal() < 10) {
                    img_signal.setImageDrawable(Utility.getDrawable(mParent, R.drawable.one));
                } else if (locationSpeedModel.getSignal() >= 10 && locationSpeedModel.getSignal() < 15) {
                    img_signal.setImageDrawable(Utility.getDrawable(mParent, R.drawable.two));
                } else if (locationSpeedModel.getSignal() >= 15 && locationSpeedModel.getSignal() < 20) {
                    img_signal.setImageDrawable(Utility.getDrawable(mParent, R.drawable.three));
                } else if (locationSpeedModel.getSignal() >= 20 && locationSpeedModel.getSignal() < 25) {
                    img_signal.setImageDrawable(Utility.getDrawable(mParent, R.drawable.four));
                } else if (locationSpeedModel.getSignal() >= 25) {
                    img_signal.setImageDrawable(Utility.getDrawable(mParent, R.drawable.five));
                }

                ll_tackers_detail_list.addView(itemList);
            }
        }
        mDialog.show();
    }

    private String getDistanceTravelled(String trackerId) {
        double distance = 0.0;
        for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
            if (vehicleListModel.getVehicleModelArrayList().get(i).getTracker_id().equalsIgnoreCase(trackerId)) {
                distance = SphericalUtil.computeLength(vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel().getLatLngArrayList());
            }
        }
        Utility.showLog("distance", "distance :" + String.format("%.2f", distance / 1000));

        return String.format("%.2f", distance / 1000);
    }

    private String getTravelledTime(String trackerId) {
        String time = "";
        for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
            if (vehicleListModel.getVehicleModelArrayList().get(i).getTracker_id().equalsIgnoreCase(trackerId)) {
                Utility.showLog("getTodayOnTimeMs", "getTodayOnTimeMs " + vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel().getTodayOnTimeMs());
                time = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel().getTodayOnTimeMs()),
                        TimeUnit.MILLISECONDS.toMinutes(vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel().getTodayOnTimeMs()) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel().getTodayOnTimeMs()) % TimeUnit.MINUTES.toSeconds(1));
                Utility.showLog("getTodayOnTimeMs", "getToday " + time);
            }
        }

        return time;
    }

    private void getTrackersData() {
        for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
            getLocationsData(vehicleListModel.getVehicleModelArrayList().get(i).getTracker_id());
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
                if (mMap != null) {
                    mMap.clear();
                }
                for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
                    VehicleListNewModel vehicleListNewModel = vehicleListModel.getVehicleModelArrayList().get(i);
                    vehicleListNewModel.marker = null;
                    vehicleListModel.getVehicleModelArrayList().set(i, vehicleListNewModel);
                }
                setMarkersData();
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void setMarkersData() {
        if (vehicleListModel.getVehicleModelArrayList() != null && vehicleListModel.getVehicleModelArrayList().size() > 0) {
            /*if (mMap != null) {
                mMap.clear();
            }*/
            for (int i = 0; i < vehicleListModel.getVehicleModelArrayList().size(); i++) {
                if (vehicleListModel.getVehicleModelArrayList().get(i).isChecked()) {
                    LocationSpeedModel locationSpeedModel = vehicleListModel.getVehicleModelArrayList().get(i).getLocationSpeedModel();
                    LatLng mLatLng = new LatLng(locationSpeedModel.getLatitude(),
                            locationSpeedModel.getLongitude());
                    if (locationSpeedModel.getIgnition() == 1 && vehicleListModel.getVehicleModelArrayList().get(i).marker == null) {
                        vehicleListModel.getVehicleModelArrayList().get(i).marker = mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vehicle_ignition_on_marker))
                                .position(mLatLng));
                    } else if (locationSpeedModel.getIgnition() == 0 && vehicleListModel.getVehicleModelArrayList().get(i).marker == null) {
                        vehicleListModel.getVehicleModelArrayList().get(i).marker = mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.vehicle_ignition_off_marker))
                                .position(mLatLng));
                    } else/* if (locationSpeedModel.getIgnition() == 1 && vehicleListModel.getVehicleModelArrayList().get(i).marker != null) {
                        vehicleListModel.getVehicleModelArrayList().get(i).marker.setPosition(mLatLng);
                    } else if (locationSpeedModel.getIgnition() == 0 && vehicleListModel.getVehicleModelArrayList().get(i).marker != null)*/ {
                        vehicleListModel.getVehicleModelArrayList().get(i).marker.setPosition(mLatLng);
                    }
                    if (isFirstTime)
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));

                    if (vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel() != null) {
                        if (vehicleListModel.getVehicleModelArrayList().get(i).getLocationSpeedModel().getIgnition() == 1) {
                            PolylineOptions mPolygonOptions = vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel().getPolylineOptions();
                            mPolygonOptions.add(mLatLng);
                            VehicleListNewModel vehicleModel = HomeFragment.vehicleListModel.getVehicleModelArrayList().get(i);
                            LatLagListModel latLagListModel = vehicleModel.getLatLagListModel();
                            latLagListModel.setPolylineOptions(mPolygonOptions);
                            latLagListModel.setTodayOnTimeMs(latLagListModel.getTodayOnTimeMs() + delay);
                            vehicleModel.setLatLagListModel(latLagListModel);
                            vehicleListModel.getVehicleModelArrayList().set(i, vehicleModel);
                            mMap.addPolyline(mPolygonOptions.color(Utility.getColor(mParent, R.color.light_gray)));
                        } else {
                            mMap.addPolyline(vehicleListModel.getVehicleModelArrayList().get(i).getLatLagListModel().getPolylineOptions()
                                    .color(Utility.getColor(mParent, R.color.light_gray)));
                        }
                    }
                    //myMarker.setTag(locationSpeedModel.get_id());
                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(mZoomLevel));
        mMap.setMaxZoomPreference(14.0f);
    }
}
