package com.magni5.m5tracker.parsers;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.magni5.m5tracker.models.LatLagListModel;
import com.magni5.m5tracker.models.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar.
 */

public class LatLngListParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        LatLagListModel mLatLagListModel = new LatLagListModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("Status"))
                mLatLagListModel.setStatus(jsonObject.optInt("Status"));
            if (jsonObject.has("Message"))
                mLatLagListModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("Data") && jsonObject.optJSONObject("Data") != null) {
                JSONObject jsonObjectData = jsonObject.optJSONObject("Data");

                mLatLagListModel.setTrackerId(jsonObjectData.optString("TrackerId"));
                JSONArray jsonArray = jsonObjectData.optJSONArray("Path");
                ArrayList<LatLng> points = new ArrayList<LatLng>();
                PolylineOptions lineOptions = new PolylineOptions();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    LatLng position = new LatLng(jsonObject1.optDouble("lat"), jsonObject1.optDouble("lng"));
                    points.add(position);
                }

                lineOptions.addAll(points);
                if (jsonArray != null && jsonArray.length() > 0) {
                    mLatLagListModel.setLatLng(new LatLng(jsonArray.optJSONObject(0).optDouble("lat"), jsonArray.optJSONObject(0).optDouble("lng")));
                }
                mLatLagListModel.setPolylineOptions(lineOptions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mLatLagListModel;
    }
}
