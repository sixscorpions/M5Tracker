package com.magni5.m5tracker.asynctask;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;


import com.magni5.m5tracker.customviews.CustomProgressDialog;
import com.magni5.m5tracker.parsers.Parser;
import com.magni5.m5tracker.utils.APIConstants;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Shankar on 3/7/2017.
 */
public abstract class BaseAsyncTask extends AsyncTask<Void, Void, Integer> {
    protected AnimationDrawable Anim;
    protected CustomProgressDialog mCustomProgressDialog = null;
    protected Context mContext;
    protected String mDialogMessage, mApiMessage;
    protected boolean mShowDialog;

    protected JSONObject mParams;
    protected APIConstants.REQUEST_TYPE mRequestType;
    protected IAsyncCaller caller;
    protected String mUrl;
    @SuppressWarnings("rawtypes")
    protected Parser parser;
    protected HashMap<String, File> mFileMap;
    protected LayoutInflater mLayoutInflater;
    protected File file;
    protected String tag;
    protected ArrayList<File> mFiles;

    /**
     * @param context       ,Context to show progress dialog
     * @param dialogMessage , Dialog message for progress dialog
     * @param showDialog    , boolean varialble to show progress dialog or not
     * @param url           , Url of the web service
     * @param mParamMap     , HashMap of keys
     * @param requestType   , Type of request(GET/POST)
     * @param caller        , Caller activity which will recieve response
     * @param parser        , JSON parser for the response
     */
    public BaseAsyncTask(Context context, String dialogMessage,
                         boolean showDialog, String url, JSONObject mParamMap,
                         APIConstants.REQUEST_TYPE requestType, IAsyncCaller caller, Parser parser) {

        this.mContext = context;
        this.mDialogMessage = dialogMessage;
        mShowDialog = showDialog;
        mParams = mParamMap;
        mRequestType = requestType;
        this.caller = caller;
        this.mUrl = url;
        this.parser = parser;
        mCustomProgressDialog = new CustomProgressDialog(mContext);
    }

    public BaseAsyncTask(Context context, String dialogMessage,
                         boolean showDialog, String url, JSONObject mParamMap,
                         APIConstants.REQUEST_TYPE requestType, IAsyncCaller caller, Parser parser, String tag, File file, ArrayList<File> mFiles) {

        this.mContext = context;
        this.mDialogMessage = dialogMessage;
        mShowDialog = showDialog;
        mParams = mParamMap;
        mRequestType = requestType;
        this.caller = caller;
        this.mUrl = url;
        this.parser = parser;
        this.file = file;
        this.mFiles = mFiles;
        this.tag = tag;
        mCustomProgressDialog = new CustomProgressDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mShowDialog) {
            mCustomProgressDialog.showProgress("");
        }
    }

    /**
     * Abstract method for interaction with Web Service.
     */
    public abstract Integer doInBackground(Void... params);

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.dismissProgress();
        }
    }
}
