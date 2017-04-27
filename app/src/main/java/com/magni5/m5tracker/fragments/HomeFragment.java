package com.magni5.m5tracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.tv_live_tracking_details_header)
    TextView tvLiveTrackingDetailsHeader;

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

    }
}
