package com.example.android.chemicalplantmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.fragments.DailyProductionFragment;
import com.example.android.chemicalplantmanagementsystem.fragments.GatePassDetailFragment;
import com.example.android.chemicalplantmanagementsystem.fragments.GatePassEditorFragment;
import com.example.android.chemicalplantmanagementsystem.fragments.GatePassFragment;
import com.example.android.chemicalplantmanagementsystem.fragments.NewProductionFragment;
import com.example.android.chemicalplantmanagementsystem.fragments.ProductionFragment;


public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = DashBoard.class.getSimpleName();

    private ScrollView mDashboadContentView;
    private LinearLayout mFragmentContainer;

    // Card view Elements
    private LinearLayout mNewGatePassView;
    private LinearLayout mNewDailyProductionView;
    private LinearLayout mProductionsView;
    private LinearLayout mGatePassesView;
    private LinearLayout mNotificationsView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        setContentView(R.layout.activity_dash_board);

        // Initialize Data Members
        mDashboadContentView = (ScrollView) findViewById(R.id.sv_dashboard_content);
        mFragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);

        mNewGatePassView = (LinearLayout) findViewById(R.id.ll_new_gate_pass);
        mNewDailyProductionView = (LinearLayout) findViewById(R.id.ll_new_daily_production);
        mProductionsView = (LinearLayout) findViewById(R.id.ll_productions);
        mGatePassesView = (LinearLayout) findViewById(R.id.ll_gate_passes);
        mNotificationsView= (LinearLayout) findViewById(R.id.ll_notifications);


        // Click Listeners
        mNewGatePassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNewFragment(new GatePassEditorFragment() , "New Gate Pass");
                hideDashboardContent();

                Log.v(LOG_TAG, "New GatePass");
            }
        });

        mNewDailyProductionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNewFragment(new DailyProductionFragment() , "New Daily Production");
                hideDashboardContent();

            }
        });

        mProductionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNewFragment(new ProductionFragment() , "Productions");
                hideDashboardContent();

            }
        });

        mGatePassesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNewFragment(new GatePassFragment() , "Gate Passes");
                hideDashboardContent();

            }
        });

        mNotificationsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NotificationActivity.class);

                startActivity(intent);

            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Changing Title of the Activity

        // Start Notification Activity
//        Intent intent = new Intent(getBaseContext(), NotificationActivity.class);
//        startActivity(intent);


//        getSupportActionBar().setTitle("Production");
//        /** Starting GatePass Fragment
//          * Create new Fragment and transaction **/
//        Fragment newFragment = new ProductionFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        // Replace is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack
//        transaction.replace(R.id.fragment_container, newFragment);
//        transaction.addToBackStack(null);
//        // Commit the transaction
//        transaction.commit();
//
//        // Changing Title of the Activity
//        getSupportActionBar().setTitle("Gate Passes");

    }

    @Override
    protected void onStart() {
        super.onStart();

        Boolean loginStatus = User.isLoggedIn(getBaseContext());
        if (loginStatus == false) {
            finish();
        }

//        mNewGatePassView.performClick();

    }

    // Hide Dashbaord Content
    private void hideDashboardContent() {

        mDashboadContentView.setVisibility(View.GONE);
    }

    // Show Dashbaord Content
    private void showDashboardContent() {

        mDashboadContentView.setVisibility(View.VISIBLE);
    }


    //    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        int count = getSupportFragmentManager().getBackStackEntryCount();

        int fragmentContainerChilds = mFragmentContainer.getChildCount();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (count > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle("DashBoard");
            showDashboardContent();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // To Show the Fragment
    private void showNewFragment(Fragment fragment, String title) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replacte is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

        getSupportActionBar().setTitle(title);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = "";

        if (id == R.id.nav_daily_production) {
            // Create new Fragment and transaction
            Fragment newFragment = new DailyProductionFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replacte is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();

            // Changing Title of the Activity
            title = "Daily Productions";
            getSupportActionBar().setTitle(title);

        } else if (id == R.id.nav_production) {

            // Create new fragment and transaction
            Fragment newFragment = new ProductionFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Repleace is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();

            // Changing Title of the Activity
            title = "Productions";
            getSupportActionBar().setTitle(title);

        } else if (id == R.id.nav_gatepass) {
            // Create new Fragment and transaction
            Fragment newFragment = new GatePassFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
            // Changing Title of the Activity
            title = "Gate Passes";
            getSupportActionBar().setTitle(title);

        } else if (id == R.id.nav_notification) {

            Intent intent = new Intent(getBaseContext(), NotificationActivity.class);

            startActivity(intent);

        }else if (id == R.id.nav_log_out) {

            // Log out user
            SharedPreferences pref = getSharedPreferences(UserContract.UserEntry.TABLE_NAME, 0);
            SharedPreferences.Editor prefEditor = pref.edit();
            prefEditor.putBoolean(getString(R.string.pref_login_status), false);
            prefEditor.commit();

            Intent newIntent = new Intent(getBaseContext(), LoginActivity.class);

            startActivity(newIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
