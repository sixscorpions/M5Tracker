package com.magni5.m5tracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.models.VehiclesDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shankar on 5/2/2017.
 */

public class VechicleSearchAdapter extends ArrayAdapter<VehiclesDataModel> {

    private ArrayList<VehiclesDataModel> mList;
    private ArrayList<VehiclesDataModel> sortedList;
    private LayoutInflater inflater;

    public VechicleSearchAdapter(Context context, int textViewResourceId, ArrayList<VehiclesDataModel> mList) {
        super(context, textViewResourceId, mList);
        this.mList = mList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public VehiclesDataModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_item_vechicle_serach, parent, false);
            holder = new ViewHolder();

            holder.tv_display_name = (TextView) convertView.findViewById(R.id.tv_display_name);
            holder.tv_display_name_value = (TextView) convertView.findViewById(R.id.tv_display_name_value);

            holder.tv_reg_number = (TextView) convertView.findViewById(R.id.tv_reg_number);
            holder.tv_reg_number_value = (TextView) convertView.findViewById(R.id.tv_reg_number_value);

            holder.tv_avg_mileage = (TextView) convertView.findViewById(R.id.tv_avg_mileage);
            holder.tv_avg_mileage_value = (TextView) convertView.findViewById(R.id.tv_avg_mileage_value);

            holder.tv_tracker_tag = (TextView) convertView.findViewById(R.id.tv_tracker_tag);
            holder.tv_tracker_tag_value = (TextView) convertView.findViewById(R.id.tv_tracker_tag_value);
            holder.view_edit_value = (Button) convertView.findViewById(R.id.view_edit_value);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        VehiclesDataModel model = mList.get(position);

        /*NAME*/
        holder.tv_display_name_value.setText(model.getDisplayName());
        holder.tv_reg_number_value.setText(model.getRegNumber());
        holder.tv_avg_mileage_value.setText("" + model.getMileage());
        holder.tv_tracker_tag_value.setText(model.getTrackerTag());

        return convertView;
    }

    private class ViewHolder {
        TextView tv_display_name;
        TextView tv_display_name_value;

        TextView tv_reg_number;
        TextView tv_reg_number_value;

        TextView tv_avg_mileage;
        TextView tv_avg_mileage_value;

        TextView tv_tracker_tag;
        TextView tv_tracker_tag_value;

        Button view_edit_value;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mList = (ArrayList<VehiclesDataModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<VehiclesDataModel> FilteredArrList = new ArrayList<>();


                if (sortedList == null) {
                    sortedList = new ArrayList<>(mList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = sortedList.size();
                    results.values = sortedList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < sortedList.size(); i++) {
                        VehiclesDataModel data = sortedList.get(i);
                        if (data.getDisplayName().toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(data);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }
}

