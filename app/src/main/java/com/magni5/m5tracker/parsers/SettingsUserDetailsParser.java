package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.SettingsUpdateModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Manikanta on 5/1/2017.
 */

public class SettingsUserDetailsParser implements Parser<Model> {

    SettingsUpdateModel mSettingsUpdateModel;

    @Override
    public Model parse(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            mSettingsUpdateModel = new SettingsUpdateModel();
            if(jsonObject.has("Status"))
                mSettingsUpdateModel.setmStatus(jsonObject.optInt("Status"));
            if(jsonObject.has("Message"))
                mSettingsUpdateModel.setmMessage(jsonObject.optString("Message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mSettingsUpdateModel;
    }
}
