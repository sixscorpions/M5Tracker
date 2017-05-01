package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.AddMarkModel;
import com.magni5.m5tracker.models.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shankar.
 */

public class AddMarkParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        AddMarkModel mAddMarkModel = new AddMarkModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("Status"))
                mAddMarkModel.setStatus(jsonObject.optInt("Status"));
            if (jsonObject.has("Message"))
                mAddMarkModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Data") && jsonObject.optJSONObject("Data") != null) {
                JSONObject jsonObjectData = jsonObject.optJSONObject("Data");
                mAddMarkModel.setUserId(jsonObjectData.optString("UserId"));
                mAddMarkModel.setVehicleId(jsonObjectData.optString("VehicleId"));
                mAddMarkModel.setLatitude(jsonObjectData.optDouble("Latitude"));
                mAddMarkModel.setLongitude(jsonObjectData.optDouble("Longitude"));
                mAddMarkModel.setDisplayName(jsonObjectData.optString("DisplayName"));
                mAddMarkModel.setLastHit(jsonObjectData.optString("LastHit"));
                mAddMarkModel.set_id(jsonObjectData.optString("_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mAddMarkModel;
    }
}
