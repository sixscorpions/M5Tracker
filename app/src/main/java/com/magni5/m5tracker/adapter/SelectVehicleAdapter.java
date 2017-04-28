package com.magni5.m5tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.fragments.HomeFragment;
import com.magni5.m5tracker.models.VehicleModel;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/28/2017.
 */

public class SelectVehicleAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;

    public SelectVehicleAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return HomeFragment.vehicleModelArrayList.size();
    }

    @Override
    public VehicleModel getItem(int position) {
        return HomeFragment.vehicleModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SelectVehicleViewHolder selectVehicleViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.select_vehicle_item, null);
            selectVehicleViewHolder = new SelectVehicleViewHolder();
            selectVehicleViewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            selectVehicleViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

            convertView.setTag(selectVehicleViewHolder);
        } else {
            selectVehicleViewHolder = (SelectVehicleViewHolder) convertView.getTag();
        }

        VehicleModel vehicleModel = HomeFragment.vehicleModelArrayList.get(position);
        if (vehicleModel.isChecked()) {
            selectVehicleViewHolder.checkbox.setChecked(true);
        } else {
            selectVehicleViewHolder.checkbox.setChecked(false);
        }
        selectVehicleViewHolder.tv_title.setText(vehicleModel.getDisplayName() + " " + vehicleModel.getRegNumber());

        selectVehicleViewHolder.checkbox.setTag(position);
        selectVehicleViewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) buttonView.getTag();
                VehicleModel vehicleModel = HomeFragment.vehicleModelArrayList.get(pos);
                vehicleModel.setChecked(!vehicleModel.isChecked());
                HomeFragment.vehicleModelArrayList.set(pos, vehicleModel);
            }
        });

        return convertView;
    }

    private class SelectVehicleViewHolder {
        private CheckBox checkbox;
        private TextView tv_title;
    }
}
