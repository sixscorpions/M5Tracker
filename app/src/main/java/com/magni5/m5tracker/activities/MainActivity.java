package com.magni5.m5tracker.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.fragments.AddMarkerFragment;
import com.magni5.m5tracker.fragments.HomeFragment;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;

public class MainActivity extends AppCompatActivity {

    public static DrawerLayout drawerLayout;
    public static Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                }
            }
        }

        setSupportActionBar(toolbar);
        initNavigationDrawer();
        askPermissions();
    }

    /*Open Gallery to select multiple images*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void askPermissions() {
        if (Utility.isMarshmallowOS()) {
            PackageManager pm = this.getPackageManager();
            int hasPerm = pm.checkPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    this.getPackageName());
            if (hasPerm != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    private void initNavigationDrawer() {
        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, MainActivity.this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //navigationView.setItemIconTintList(null);

       /* Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.trash).setVisible(false);*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_dashboard:
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                                    .getBackStackEntryAt(
                                            getSupportFragmentManager()
                                                    .getBackStackEntryCount() - 1);
                            String tagName = backEntry.getName();
                            if (!tagName.equals(HomeFragment.TAG)) {
                                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, MainActivity.this);
                            }
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_vehicles:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_settings:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_add_mark:
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                                    .getBackStackEntryAt(
                                            getSupportFragmentManager()
                                                    .getBackStackEntryCount() - 1);
                            String tagName = backEntry.getName();
                            if (!tagName.equals(AddMarkerFragment.TAG)) {
                                Utility.navigateDashBoardFragment(new AddMarkerFragment(), AddMarkerFragment.TAG, null, MainActivity.this);
                            }
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        drawerLayout.closeDrawers();
                        final Dialog mDialog = new Dialog(MainActivity.this);
                        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialog.setContentView(R.layout.delete_dialog_layout);
                        mDialog.getWindow().setGravity(Gravity.CENTER);
                        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        mDialog.getWindow().setBackgroundDrawable(new
                                ColorDrawable(Color.TRANSPARENT));
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(true);

                        TextView tv_delete_message = (TextView) mDialog.findViewById(R.id.tv_delete_message);
                        tv_delete_message.setText(Utility.getResourcesString(MainActivity.this, R.string.are_you_sure_you_want_to_logout));
                     /*   tv_delete_message.setTypeface(Utility.getProximaNovaRegular(MainActivity.this));*/

                        TextView tv_no = (TextView) mDialog.findViewById(R.id.tv_no);
/*                        tv_no.setTypeface(Utility.getProximaNovaRegular(MainActivity.this));*/

                        TextView tv_yes = (TextView) mDialog.findViewById(R.id.tv_yes);
/*                        tv_yes.setTypeface(Utility.getProximaNovaRegular(MainActivity.this));*/

                        tv_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                        tv_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                finish();
                            }
                        });
                        mDialog.show();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView txt_name = (TextView) header.findViewById(R.id.txt_name);
        TextView txt_user_designation = (TextView) header.findViewById(R.id.txt_user_designation);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        final View.OnClickListener originalToolbarListener = actionBarDrawerToggle.getToolbarNavigationClickListener();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(originalToolbarListener);
                }
            }
        });

    }
}
