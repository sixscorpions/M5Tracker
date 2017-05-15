package com.magni5.m5tracker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.fragments.HomeFragment;
import com.magni5.m5tracker.utils.Utility;

/**
 * Created by Manikanta on 4/25/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private int mClosePressCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onBackPressed() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry backStackEntry = fragmentManager
                        .getBackStackEntryAt(fragmentManager
                                .getBackStackEntryCount() - 1);
                Utility.showLog("BackStackEntry Name", backStackEntry.getName());
                if (backStackEntry.getName().equalsIgnoreCase(HomeFragment.TAG)) {
                    mClosePressCount++;
                    if (mClosePressCount > 1) {
                        finishAffinity();
                    } else {
                        Utility.showToastMessage(getApplicationContext(), "Press Again To Exit");
                    }
                }
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            getFragmentManager().popBackStack();
        }
    }
}
