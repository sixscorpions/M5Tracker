package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.VehicleListModel;
import com.magni5.m5tracker.models.VehicleListNewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar.
 */

public class VehicleListParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        VehicleListModel mVehicleListModel = new VehicleListModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("Status"))
                mVehicleListModel.setStatus(jsonObject.optInt("Status"));
            if (jsonObject.has("Message"))
                mVehicleListModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Data") && jsonObject.optJSONArray("Data") != null) {
                JSONArray jsonArray = jsonObject.optJSONArray("Data");
                ArrayList<VehicleListNewModel> vehicleModelArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject itemJson = jsonArray.optJSONObject(i);
                    JSONObject mVehicleItemJson = itemJson.optJSONObject("Vehicle");
                    VehicleListNewModel vehicleModel = new VehicleListNewModel();
                    vehicleModel.set_id(mVehicleItemJson.optString("_id"));
                    vehicleModel.setDisplayNumber(mVehicleItemJson.optString("DisplayNumber"));
                    vehicleModel.setRegNumber(mVehicleItemJson.optString("RegNumber"));
                    vehicleModel.setMileage(mVehicleItemJson.optInt("Mileage"));
                    vehicleModel.setLastServicedDate(mVehicleItemJson.optString("LastServicedDate"));
                    vehicleModel.setOwnerId(mVehicleItemJson.optString("OwnerId"));
                    vehicleModel.setDisplayName(mVehicleItemJson.optString("DisplayName"));
                    vehicleModel.setChecked(true);

                    JSONObject mTrackerItemJson = itemJson.optJSONObject("Tracker");

                    if (mTrackerItemJson != null) {
                        vehicleModel.setTracker_id(mTrackerItemJson.optString("_id"));
                        vehicleModel.setVersion(mTrackerItemJson.optInt("Version"));
                        vehicleModel.setLocked(mTrackerItemJson.optBoolean("IsLocked"));
                        vehicleModel.setTimeStamp(mTrackerItemJson.optString("TimeStamp"));
                        vehicleModel.setTag(mTrackerItemJson.optString("Tag"));
                    }
                    if (mTrackerItemJson != null) {
                        vehicleModelArrayList.add(vehicleModel);
                    }
                }
                mVehicleListModel.setVehicleModelArrayList(vehicleModelArrayList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mVehicleListModel;
    }
}
