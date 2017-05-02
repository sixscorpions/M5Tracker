package com.magni5.m5tracker.utils;

/**
 * Created by Shankar Rao on 3/28/2016.
 */
public class APIConstants {


    public static final String CLIENT_ID = "56ed448b1922010f541efec3";
    public static final String CLIENT_SECRET = "9eaa48c15dc29715a3ea66b12712b069";
    public static final String GSON = "Gson";
    public static final String JSON = "Json";
    public static final String STATUS = "status";


    public enum REQUEST_TYPE {
        GET, POST, MULTIPART_GET, MULTIPART_POST, DELETE, PUT, PATCH
    }

    public static String BASE_URL = "http://m5tracker.com/api";
    public static String SIGN_IN = BASE_URL + "/user/login";
    public static String VEHICLE_DETAILS = BASE_URL + "/vehicle/detail";
    public static String LOCATIONS = BASE_URL + "/tracker/location/";
    public static String VEHICLES_PATHS = BASE_URL + "/tracker/location/path/";

    public static String ADD_MARK = BASE_URL + "/userpreference/add/";

    public static String USER_UPDATE = BASE_URL + "/user/update";
    public static String PASSWORD_CHANGE = BASE_URL + "/user/password/change";
    public static String ADDRESS_UPDATE = BASE_URL + "/user/owner/address/update";

    public static String GET_VEHICLES = BASE_URL + "/owner/vehicles";

    public static String VEHICLE_UPDATE = BASE_URL + "/owner/vehicle/update";
    public static String MARK_SERVCIE = BASE_URL + "/vehicle/";
    public static String SERVICED = "/serviced";
}
