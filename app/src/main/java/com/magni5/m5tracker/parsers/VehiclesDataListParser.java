package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.VehiclesDataListModel;
import com.magni5.m5tracker.models.VehiclesDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar.
 */

public class VehiclesDataListParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        VehiclesDataListModel mVehiclesDataListModel = new VehiclesDataListModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("Status"))
                mVehiclesDataListModel.setStatus(jsonObject.optInt("Status"));
            if (jsonObject.has("Message"))
                mVehiclesDataListModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Data") && jsonObject.optJSONArray("Data") != null) {
                JSONArray jsonArray = jsonObject.optJSONArray("Data");
                ArrayList<VehiclesDataModel> vehiclesDataModels = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject itemJson = jsonArray.optJSONObject(i);
                    VehiclesDataModel vehiclesDataModel = new VehiclesDataModel();
                    vehiclesDataModel.set_id(itemJson.optString("_id"));
                    vehiclesDataModel.setTrackerTag(itemJson.optString("TrackerTag"));
                    vehiclesDataModel.setRegNumber(itemJson.optString("RegNumber"));
                    vehiclesDataModel.setDisplayNumber(itemJson.optString("DisplayNumber"));
                    vehiclesDataModel.setMileage(itemJson.optInt("Mileage"));
                    vehiclesDataModel.setLastServicedDate(itemJson.optString("LastServicedDate"));
                    vehiclesDataModel.setServicingNeeded(itemJson.optBoolean("ServicingNeeded"));
                    vehiclesDataModel.setOwnerId(itemJson.optString("OwnerId"));
                    vehiclesDataModel.setDisplayName(itemJson.optString("DisplayName"));
                    vehiclesDataModels.add(vehiclesDataModel);
                }
                mVehiclesDataListModel.setVehiclesDataModels(vehiclesDataModels);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mVehiclesDataListModel;
    }
}
