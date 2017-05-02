package com.magni5.m5tracker.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.asynctask.ServerJSONAsyncTask;
import com.magni5.m5tracker.interfaces.ImsgeConverterInterface;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.SettingsUpdateModel;
import com.magni5.m5tracker.models.SignInModel;
import com.magni5.m5tracker.parsers.SettingsUserDetailsParser;
import com.magni5.m5tracker.utils.APIConstants;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Manikanta on 5/1/2017.
 */

public class CompanyDetailsFragment extends Fragment implements IAsyncCaller, ImsgeConverterInterface {


    public static String TAG = "CompanyDetailsFragment";

    private static MainActivity mParent;
    private View rootView;

    @BindView(R.id.lly_company_details)
    LinearLayout llyCompanyDetails;
    @BindView(R.id.et_display_name)
    EditText etDisplayName;
    @BindView(R.id.et_Overspeed_limit)
    EditText etOverspeedLimit;
    @BindView(R.id.et_service_alert_distance)
    EditText etServiceAlertDistance;
    @BindView(R.id.et_vehicle_map_marker_img)
    EditText etVehicleMapMarkerImg;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.img_selected_image)
    ImageView img_selected_image;

    String mImage = "";
    private static ImsgeConverterInterface imsgeConverterInterface;

/*    private SignInModel signInModel;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
        imsgeConverterInterface = this;
       /* Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(Constants.LOGIN_DATA_MODEL))
                signInModel = (SignInModel) bundle.getSerializable(Constants.LOGIN_DATA_MODEL);
        }*/
    }

    public static ImsgeConverterInterface getInstance() {
        return imsgeConverterInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_company_details, container, false);
        ButterKnife.bind(this, rootView);
        mParent.toolbar.setTitle("Company Details");
        initUI();
        return rootView;
    }

    /**
     * To initiate UI
     */
    private void initUI() {
        etDisplayName.setText(Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_NAME)) ? "" : "" + Utility.getSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_NAME));
        etOverspeedLimit.setText("" + Utility.getSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_OSP_LMT));
        etServiceAlertDistance.setText("" + Utility.getSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_SAD_KM));

        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_IMG))) {
            Uri uri = Uri.parse(Utility.getSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_IMG));
            img_selected_image.setVisibility(View.VISIBLE);
            img_selected_image.setImageURI(uri);

        }
    }

    @OnClick(R.id.et_vehicle_map_marker_img)
    void selectImage() {
        browsePictureData();
    }

    @OnClick(R.id.btn_save)
    void onBtnSaveClick() {
        userDetailsApiCall(etDisplayName.getText().toString(),
                etOverspeedLimit.getText().toString(),
                etServiceAlertDistance.getText().toString());
        //TODO implement
    }

    private void userDetailsApiCall(String displayName, String overSpeedLimit, String serviceAlertDistance) {

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            linkedHashMap.put("ip_markerimage", mImage);
            linkedHashMap.put("ip_companydisplayname", displayName);
            linkedHashMap.put("ip_overspeedlimit", overSpeedLimit);
            linkedHashMap.put("ip_ServiceAlertDistanceKM", serviceAlertDistance);
            SettingsUserDetailsParser mSettingsUserDetailsParser = new SettingsUserDetailsParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.COMPANY_DETAILS_UPDATE, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, mSettingsUserDetailsParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof SettingsUpdateModel) {
                SettingsUpdateModel mSettingsUpdateModel = (SettingsUpdateModel) model;
                Utility.showToastMessage(mParent, mSettingsUpdateModel.getmMessage());
                if (mSettingsUpdateModel.getmStatus() == 1) {

                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_NAME, etDisplayName.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_OSP_LMT, "" + etOverspeedLimit.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_SAD_KM, "" + etServiceAlertDistance.getText().toString());
                    Utility.setSharedPrefStringData(mParent, Constants.COMPANY_DETAILS_IMG, mImage);
                    mParent.onBackPressed();

                }
            }
        }
    }

    @Override
    public void getBase64Image(String image, Bitmap bitmap) {
        mImage = Utility.convertFileToByteArray(new File(image));
        img_selected_image.setImageBitmap(bitmap);
        img_selected_image.setVisibility(View.VISIBLE);
    }

    /**
     * This method is used to browse picture data
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void browsePictureData() {
        String[] mime_types = {"image/jpeg", "image/jpg", "image/png"};
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mime_types);
        mParent.startActivityForResult(intent, Constants.PICK_OF_ID);
      /*  Utility.displayUploadOptionDialog(getActivity(), TAG);*/
    }
}
