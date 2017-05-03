package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.AddressModel;
import com.magni5.m5tracker.models.DataModel;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.OwnerModel;
import com.magni5.m5tracker.models.RoleModel;
import com.magni5.m5tracker.models.SignInModel;
import com.magni5.m5tracker.models.UserModel;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class SignInParser implements Parser<Model> {
    SignInModel mSignInModel = new SignInModel();


    @Override
    public Model parse(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("Status"))
                mSignInModel.setStatus(jsonObject.optInt("Status"));
            if (jsonObject.has("Message"))
                mSignInModel.setMessage(jsonObject.optString("Message"));
            if (jsonObject.has("_id"))
                mSignInModel.setMessage(jsonObject.optString("_id"));

            if (jsonObject.has("Data") && jsonObject.optJSONObject("Data") != null) {
                JSONObject jsonDataObject = jsonObject.optJSONObject("Data");
                DataModel dataModel = new DataModel();
                if (jsonDataObject.has("User")) {
                    JSONObject userJsonObject = jsonDataObject.optJSONObject("User");
                    UserModel userModel = new UserModel();
                    if (userJsonObject.has("DisplayName"))
                        userModel.setDisplayName(userJsonObject.optString("DisplayName"));
                    if (userJsonObject.has("Email"))
                        userModel.setEmail(userJsonObject.optString("Email"));
                    if (userJsonObject.has("UserMagic"))
                        userModel.setUserMagic(userJsonObject.optString("UserMagic"));
                    if (userJsonObject.has("Phone"))
                        userModel.setPhone(userJsonObject.optString("Phone"));
                    if (userJsonObject.has("OwnerId"))
                        userModel.setOwnerId(userJsonObject.optString("OwnerId"));
                    if (userJsonObject.has("IsEmailVerified"))
                        userModel.setIsEmailVerified(userJsonObject.optBoolean("IsEmailVerified"));
                    if (userJsonObject.has("IsPhoneVerified"))
                        userModel.setIsPhoneVerified(userJsonObject.optBoolean("IsPhoneVerified"));
                    if (userJsonObject.has("Role")) {
                        JSONObject roleJsonObject = userJsonObject.optJSONObject("Role");
                        RoleModel roleModel = new RoleModel();
                        if (roleJsonObject.has("DisplayName"))
                            roleModel.setDisplayName(roleJsonObject.optString("DisplayName"));
                        if (roleJsonObject.has("Tag"))
                            roleModel.setTag(roleJsonObject.optString("Tag"));
                        if (roleJsonObject.has("_id"))
                            roleModel.setTag(roleJsonObject.optString("_id"));

                        userModel.setRole(roleModel);
                    }
                    if (userJsonObject.has("Owner")) {
                        JSONObject ownerJsonObject = userJsonObject.optJSONObject("Owner");
                        OwnerModel ownerModel = new OwnerModel();
                        if (ownerJsonObject.has("DisplayName"))
                            ownerModel.setDisplayName(ownerJsonObject.optString("DisplayName"));
                        if (ownerJsonObject.has("Address")) {
                            JSONObject addressJsonObject = ownerJsonObject.optJSONObject("Address");
                            AddressModel addressModel = new AddressModel();
                            if (addressJsonObject.has("Street"))
                                addressModel.setStreet(addressJsonObject.optString("Street"));
                            if (addressJsonObject.has("City"))
                                addressModel.setCity(addressJsonObject.optString("City"));
                            if (addressJsonObject.has("State"))
                                addressModel.setState(addressJsonObject.optString("State"));
                            if (addressJsonObject.has("Country"))
                                addressModel.setCountry(addressJsonObject.optString("Country"));
                            if (addressJsonObject.has("Pincode"))
                                addressModel.setPincode(addressJsonObject.optString("Pincode"));
                            if (addressJsonObject.has("Latitude"))
                                addressModel.setLatitude(addressJsonObject.optLong("Latitude"));
                            if (addressJsonObject.has("Longitude"))
                                addressModel.setLongitude(addressJsonObject.optLong("Longitude"));

                            ownerModel.setAddress(addressModel);
                        }
                        if (ownerJsonObject.has("VehicleImageName"))
                            ownerModel.setVehicleImageName(ownerJsonObject.optString("VehicleImageName"));
                        if (ownerJsonObject.has("Type"))
                            ownerModel.setType(ownerJsonObject.optString("Type"));
                        if (ownerJsonObject.has("OverspeedLimit"))
                            ownerModel.setOverspeedLimit(ownerJsonObject.optInt("OverspeedLimit"));
                        if (ownerJsonObject.has("ServiceAlertDistanceKM"))
                            ownerModel.setServiceAlertDistanceKM(ownerJsonObject.optInt("ServiceAlertDistanceKM"));
                        if (ownerJsonObject.has("ActivatedOn"))
                            ownerModel.setActivatedOn(ownerJsonObject.optString("ActivatedOn"));
                        if (ownerJsonObject.has("ActivationEndsOn"))
                            ownerModel.setActivationEndsOn(ownerJsonObject.optString("ActivationEndsOn"));
                        if (ownerJsonObject.has("ActivationEndsOnString"))
                            ownerModel.setActivationEndsOnString(ownerJsonObject.optString("ActivationEndsOnString"));
                        if (ownerJsonObject.has("VehicleImageUrl"))
                            ownerModel.setVehicleImageUrl(APIConstants.BASE_URL_IMG + ownerJsonObject.optString("VehicleImageUrl"));
                        if (ownerJsonObject.has("Tag"))
                            ownerModel.setTag(ownerJsonObject.optString("Tag"));
                        if (ownerJsonObject.has("_id"))
                            ownerModel.set_id(ownerJsonObject.optString("_id"));

                        userModel.setOwner(ownerModel);
                    }

                    dataModel.setUser(userModel);
                }
                mSignInModel.setData(dataModel);
                mSignInModel.setLoginresponse(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mSignInModel;
    }
}
