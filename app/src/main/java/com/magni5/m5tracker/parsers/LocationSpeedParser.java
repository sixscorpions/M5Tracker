package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.LocationSpeedModel;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.TrackerModel;
import com.magni5.m5tracker.models.VehicleListModel;
import com.magni5.m5tracker.models.VehicleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar.
 */

public class LocationSpeedParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        LocationSpeedModel mLocationSpeedModel = new LocationSpeedModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("Status"))
                mLocationSpeedModel.setStatus(jsonObject.optInt("Status"));
            if (jsonObject.has("Message"))
                mLocationSpeedModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Data") && jsonObject.optJSONObject("Data") != null) {
                JSONObject jsonObjectData = jsonObject.optJSONObject("Data");
                mLocationSpeedModel.set_id(jsonObjectData.optString("_id"));
                mLocationSpeedModel.setTrackerId(jsonObjectData.optString("TrackerId"));
                mLocationSpeedModel.setLatitude(jsonObjectData.optDouble("Latitude"));
                mLocationSpeedModel.setLongitude(jsonObjectData.optDouble("Longitude"));
                mLocationSpeedModel.setSpeed(jsonObjectData.optDouble("Speed"));
                mLocationSpeedModel.setSignal(jsonObjectData.optInt("Signal"));
                mLocationSpeedModel.setIgnition(jsonObjectData.optInt("Ignition"));
                mLocationSpeedModel.setCreatedAt(jsonObjectData.optString("CreatedAt"));
                mLocationSpeedModel.setEventDateTime(jsonObjectData.optString("EventDateTime"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mLocationSpeedModel;
    }
}
