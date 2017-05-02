package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.MileageUpdateModel;
import com.magni5.m5tracker.models.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shankar.
 */

public class MileageUpdateParser implements Parser<Model> {
    @Override
    public Model parse(String s) {
        MileageUpdateModel mMileageUpdateModel = new MileageUpdateModel();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("Status"))
                mMileageUpdateModel.setStatus(jsonObject.optInt("Status"));
            if (jsonObject.has("Message"))
                mMileageUpdateModel.setMessage(jsonObject.optString("Message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mMileageUpdateModel;
    }
}
