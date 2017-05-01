package com.magni5.m5tracker.fragments;

import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMarkerFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static final String TAG = "AddMarkerFragment";
    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.spinner)
    Spinner spinner;
    /* @BindView(R.id.edt_search)
     AutoCompleteTextView edtSearch;*/
    @BindView(R.id.img_add_marker)
    ImageView imgAddMarker;

    private GoogleMap mAddMarkerMap;
    private SupportMapFragment supportAddMarkerMapFragment;
    private float mZoomLevel = 11.5f;

    private ArrayList<String> vehicleArrayList;
    private PlaceAutocompleteFragment autocompleteFragment;

    @BindView(R.id.ll_bottom_layout)
    LinearLayout llBottomLayout;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.edt_add_name)
    EditText edtAddName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_marker, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @OnClick(R.id.img_add_marker)
    void showBottomLayout() {
        llBottomLayout.setVisibility(View.VISIBLE);
        tvAddress.setText(getAddressFromLatLng(mAddMarkerMap.getCameraPosition().target));
    }

    /**
     * This method is used for initialization
     */
    private void initUI() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        supportAddMarkerMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map_add_marker);
        supportAddMarkerMapFragment = SupportMapFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.map_add_marker, supportAddMarkerMapFragment).commit();
        supportAddMarkerMapFragment.getMapAsync(this);

        vehicleArrayList = new ArrayList<>();
        if (HomeFragment.vehicleModelArrayList != null && HomeFragment.vehicleModelArrayList.size() > 0) {
            for (int i = 0; i < HomeFragment.vehicleModelArrayList.size(); i++) {
                vehicleArrayList.add(HomeFragment.vehicleModelArrayList.get(i).getDisplayName());
            }
        }
        if (vehicleArrayList != null && vehicleArrayList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, vehicleArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        autocompleteFragment = (PlaceAutocompleteFragment)
                mParent.getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mAddMarkerMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mAddMarkerMap.animateCamera(CameraUpdateFactory.zoomTo(13.5f));
                llBottomLayout.setVisibility(View.GONE);
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAddMarkerMap.moveCamera(CameraUpdateFactory.newLatLng(getLatLngFromSelection(getTrackerId(HomeFragment.vehicleModelArrayList.get(position).get_id()))));
                mAddMarkerMap.animateCamera(CameraUpdateFactory.zoomTo(mZoomLevel));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getTrackerId(String id) {
        String ids = "";
        if (HomeFragment.vehicleListModel != null && HomeFragment.vehicleListModel.getTrackerModelArrayList() != null &&
                HomeFragment.vehicleListModel.getTrackerModelArrayList().size() > 0) {
            for (int i = 0; i < HomeFragment.vehicleListModel.getVehicleModelArrayList().size(); i++) {
                if (HomeFragment.vehicleListModel.getVehicleModelArrayList().get(i).get_id().equalsIgnoreCase(id)) {
                    ids = HomeFragment.vehicleListModel.getTrackerModelArrayList().get(i).get_id();
                }
            }
        }
        return ids;
    }

    private LatLng getLatLngFromSelection(String id) {
        LatLng latLng = null;
        double lat = 0;
        double lng = 0;
        if (HomeFragment.locationSpeedModelArrayList != null && HomeFragment.locationSpeedModelArrayList.size() > 0)
            for (int i = 0; i < HomeFragment.locationSpeedModelArrayList.size(); i++) {
                if (HomeFragment.locationSpeedModelArrayList.get(i).getTrackerId().equalsIgnoreCase(id)) {
                    lat = HomeFragment.locationSpeedModelArrayList.get(i).getLatitude();
                    lng = HomeFragment.locationSpeedModelArrayList.get(i).getLongitude();
                }
            }
        latLng = new LatLng(lat, lng);
        Utility.showLog("latLng", "latLng" + latLng.latitude);
        return latLng;
    }

    /**
     * Show the markers on the map
     */
    private void showMarkersOnMap() {
        if (HomeFragment.locationSpeedModelArrayList != null && HomeFragment.locationSpeedModelArrayList.size() > 0) {
            ArrayList<Marker> markers = new ArrayList<>();
            mAddMarkerMap.clear();
            for (int i = 0; i < HomeFragment.locationSpeedModelArrayList.size(); i++) {
                LatLng mLatLng = new LatLng(HomeFragment.locationSpeedModelArrayList.get(i).getLatitude(),
                        HomeFragment.locationSpeedModelArrayList.get(i).getLongitude());
                Marker myMarker = mAddMarkerMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .position(mLatLng));
                myMarker.setTag(i);
                markers.add(myMarker);
            }
            if (HomeFragment.locationSpeedModelArrayList != null && HomeFragment.locationSpeedModelArrayList.size() > 0)
                mAddMarkerMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(HomeFragment.locationSpeedModelArrayList.get(0).getLatitude(),
                        HomeFragment.locationSpeedModelArrayList.get(0).getLongitude())));
            mAddMarkerMap.animateCamera(CameraUpdateFactory.zoomTo(mZoomLevel));
            mAddMarkerMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    tvAddress.setText("");
                    edtAddName.setText("");
                    llBottomLayout.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        llBottomLayout.setVisibility(View.GONE);
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mAddMarkerMap = googleMap;
        mAddMarkerMap.animateCamera(CameraUpdateFactory.zoomTo(mZoomLevel));
        mAddMarkerMap.setMaxZoomPreference(21.0f);
        showMarkersOnMap();
    }

    /**
     * This method is used to get the address from lat lng
     */
    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(mParent);
        String address = "";
        try {
            address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
}
