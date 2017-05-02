package com.magni5.m5tracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.adapter.VechicleSearchAdapter;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.VehiclesDataListModel;
import com.magni5.m5tracker.models.VehiclesDataModel;
import com.magni5.m5tracker.parsers.VehiclesDataListParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehiclesFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = "VehiclesFragment";
    private MainActivity mParent;
    private View rootView;

    @BindView(R.id.edt_search)
    EditText edtSearch;

    private ListView ll_vehicle_list;

    private VehiclesDataListModel vehiclesDataListModel;
    private VechicleSearchAdapter vechicleSearchAdapter;

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
        rootView = inflater.inflate(R.layout.fragment_vechiles, container, false);
        ll_vehicle_list = (ListView) rootView.findViewById(R.id.ll_vehicle_list);
        ButterKnife.bind(this, rootView);
        getVehiclesData();
        return rootView;
    }

    private void getVehiclesData() {
        try {
            VehiclesDataListParser mVehiclesDataListParser = new VehiclesDataListParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_VEHICLES, null,
                    APIConstants.REQUEST_TYPE.GET, this, mVehiclesDataListParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof VehiclesDataListModel) {
                vehiclesDataListModel = (VehiclesDataListModel) model;
                setDataToList();
            }
        }
    }

    private void setDataToList() {

        vechicleSearchAdapter = new VechicleSearchAdapter(mParent, 100, vehiclesDataListModel.getVehiclesDataModels());
        ll_vehicle_list.setAdapter(vechicleSearchAdapter);

        ll_vehicle_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Utility.showLog("Post Data", "ll_vehicle_list");
                VehiclesDataModel model = (VehiclesDataModel) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.VEHICLE_VIEW_EDIT_MODEL, model);
                Utility.navigateDashBoardFragment(new VehicleViewEditFragment(), VehicleViewEditFragment.TAG, bundle, mParent);
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (vechicleSearchAdapter != null && vechicleSearchAdapter.getFilter() != null)
                    vechicleSearchAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
