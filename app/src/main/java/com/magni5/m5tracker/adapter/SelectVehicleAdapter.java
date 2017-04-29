package com.magni5.m5tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.fragments.HomeFragment;
import com.magni5.m5tracker.models.VehicleModel;
import com.magni5.m5tracker.utils.Utility;

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
            selectVehicleViewHolder.checkbox = (ImageView) convertView.findViewById(R.id.checkbox);
            selectVehicleViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

            convertView.setTag(selectVehicleViewHolder);
        } else {
            selectVehicleViewHolder = (SelectVehicleViewHolder) convertView.getTag();
        }

        VehicleModel vehicleModel = HomeFragment.vehicleModelArrayList.get(position);
        if (vehicleModel.isChecked()) {
            selectVehicleViewHolder.checkbox.setImageDrawable(Utility.getDrawable(context, R.drawable.ic_check_box_black_24dp));
        } else {
            selectVehicleViewHolder.checkbox.setImageDrawable(Utility.getDrawable(context, R.drawable.ic_check_box_outline_blank_black_24dp));
        }
        selectVehicleViewHolder.tv_title.setText(vehicleModel.getDisplayName() + " " + vehicleModel.getRegNumber());

        selectVehicleViewHolder.checkbox.setTag(position);
        selectVehicleViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                VehicleModel vehicleModel = HomeFragment.vehicleModelArrayList.get(pos);
                vehicleModel.setChecked(!vehicleModel.isChecked());
                HomeFragment.vehicleModelArrayList.set(pos, vehicleModel);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private class SelectVehicleViewHolder {
        private ImageView checkbox;
        private TextView tv_title;
    }
}
