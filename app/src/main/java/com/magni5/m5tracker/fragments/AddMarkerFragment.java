package com.magni5.m5tracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMarkerFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static final String TAG = "AddMarkerFragment";
    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_add_marker)
    ImageView imgAddMarker;

    private GoogleMap mAddMarkerMap;
    private SupportMapFragment supportAddMarkerMapFragment;
    private float mZoomLevel = 11.5f;

    private ArrayList<String> vehicleArrayList;

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
        for (int i = 0; i < HomeFragment.vehicleModelArrayList.size(); i++) {
            vehicleArrayList.add(HomeFragment.vehicleModelArrayList.get(i).getDisplayName());
        }
        if (vehicleArrayList != null && vehicleArrayList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, vehicleArrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

    }

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
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mAddMarkerMap = googleMap;
        mAddMarkerMap.animateCamera(CameraUpdateFactory.zoomTo(mZoomLevel));
        mAddMarkerMap.setMaxZoomPreference(14.0f);
        showMarkersOnMap();
    }
}
