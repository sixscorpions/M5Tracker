package com.magni5.m5tracker.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.activities.MainActivity;
import com.magni5.m5tracker.asynctask.IAsyncCaller;
import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.SignInModel;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Manikanta on 5/1/2017.
 */

public class SettingsFragment extends Fragment implements IAsyncCaller {


    private MainActivity mParent;
    private View rootView;

    public static String TAG = "SettingsFragment";

    @BindView(R.id.lly_user_details)
    LinearLayout lly_user_details;
    @BindView(R.id.lly_update_password)
    LinearLayout lly_update_password;
    @BindView(R.id.lly_company_details)
    LinearLayout lly_company_details;
    @BindView(R.id.lly_company_address)
    LinearLayout lly_company_address;

    @BindView(R.id.tv_icon_user_details)
    TextView tv_icon_user_details;
    @BindView(R.id.tv_icon_update_password)
    TextView tv_icon_update_password;
    @BindView(R.id.tv_icon_company_details)
    TextView tv_icon_company_details;
    @BindView(R.id.tv_icon_company_address)
    TextView tv_icon_company_address;

    @BindView(R.id.tv_user_details)
    TextView tv_user_details;
    @BindView(R.id.tv_update_password)
    TextView tv_update_password;
    @BindView(R.id.tv_company_details)
    TextView tv_company_details;
    @BindView(R.id.tv_company_address)
    TextView tv_company_address;

/*    private SignInModel signInModel;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (MainActivity) getActivity();
       /* Bundle bundle = getArguments();
        if(bundle!=null){
            if (bundle.containsKey(Constants.LOGIN_DATA_MODEL))
                signInModel = (SignInModel) bundle.getSerializable(Constants.LOGIN_DATA_MODEL);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        mParent.toolbar.setTitle("Settings");
        return rootView;
    }

    /**
     * To initiate the user interface
     */
    private void initUI() {

        Typeface materialTypeFace = Utility.getMaterialIconsRegular(getActivity());
        tv_icon_company_address.setTypeface(materialTypeFace);
        tv_icon_user_details.setTypeface(materialTypeFace);
        tv_icon_company_details.setTypeface(materialTypeFace);
        tv_icon_update_password.setTypeface(materialTypeFace);
    }

    @Override
    public void onComplete(Model model) {

    }

    @OnClick(R.id.lly_user_details)
    void navigateToUserDetails() {
/*        Bundle navigationBundle = new Bundle();
        navigationBundle.putSerializable(Constants.LOGIN_DATA_MODEL, signInModel);*/
        Utility.navigateDashBoardFragment(new UserDetailsFragment(), UserDetailsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.lly_update_password)
    void navigateToUpdatePwd() {
        Utility.navigateDashBoardFragment(new UpdatePasswordFragment(), UpdatePasswordFragment.TAG, null, mParent);
    }

    @OnClick(R.id.lly_company_details)
    void navigateToCompanyDetails() {
/*        Bundle navigationBundle = new Bundle();
        navigationBundle.putSerializable(Constants.LOGIN_DATA_MODEL, signInModel);*/
        Utility.navigateDashBoardFragment(new CompanyDetailsFragment(), CompanyDetailsFragment.TAG, null, mParent);
    }

    @OnClick(R.id.lly_company_address)
    void navigateToCompanyAddress() {
/*        Bundle navigationBundle = new Bundle();
        navigationBundle.putSerializable(Constants.LOGIN_DATA_MODEL, signInModel);*/
        Utility.navigateDashBoardFragment(new CompanyAddressFragment(), CompanyAddressFragment.TAG, null, mParent);
    }
}
