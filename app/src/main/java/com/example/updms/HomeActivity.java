package com.example.updms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.updms.fragment.CardExpiryOption;
import com.example.updms.fragment.ConstuctionOrDomesticFragment;
import com.example.updms.fragment.FeeStatusFragment;
import com.example.updms.fragment.HomeFragment;
import com.example.updms.fragment.LatestUpdateFragment;
import com.example.updms.fragment.NodalFragment;
import com.example.updms.fragment.VerifyFragment;
import com.example.updms.rest.Response.LoginData;
import com.example.updms.rest.Response.ResponseModel;
import com.example.updms.rest.Response.WorkerCount;
import com.example.updms.rest.RestCallBack;
import com.example.updms.rest.RestServiceFactory;
import com.example.updms.util.DialogFactory;
import com.example.updms.util.TempStorage;
import com.example.updms.util.ToastUtils;
import com.google.android.material.navigation.NavigationView;
import com.shawnlin.preferencesmanager.PreferencesManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LoginData loginData;
    private ProgressDialog progressDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = DialogFactory.createProgressDialog(context, context.getString(R.string.please_wait));
        loginData = TempStorage.getLoginData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);

        View header = navigationView.getHeaderView(0);
        TextView textAdmin = (TextView) header.findViewById(R.id.textViewAdmin);
        TextView textDate = (TextView) header.findViewById(R.id.textViewDate);

        textAdmin.setText(TempStorage.getLoginData().getUserType());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
        try {
            date = dateFormat.parse(TempStorage.getLoginData().getActivationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
        String dateStr = formatter.format(date);
        textDate.setText(dateStr);
        Menu menuNav = navigationView.getMenu();

        if (loginData.getUserType().equalsIgnoreCase("NODAL")) {
            menuNav.findItem(R.id.nav_nodal).setVisible(false);
            menuNav.findItem(R.id.nav_feestatus).setVisible(false);
            menuNav.findItem(R.id.nav_addads).setVisible(false);
            menuNav.findItem(R.id.nav_nirman).setVisible(true);
            menuNav.findItem(R.id.nav_domestic).setVisible(true);
            menuNav.findItem(R.id.nav_latestupdate).setVisible(true);
            menuNav.findItem(R.id.nav_helplineno).setVisible(false);
            menuNav.findItem(R.id.nav_songs).setVisible(false);
            menuNav.findItem(R.id.nav_registrationcard).setVisible(false);
            menuNav.findItem(R.id.nav_membershipcard).setVisible(false);


        } else {
            menuNav.findItem(R.id.nav_nodal).setVisible(true);
            menuNav.findItem(R.id.nav_feestatus).setVisible(true);
            menuNav.findItem(R.id.nav_addads).setVisible(true);
            menuNav.findItem(R.id.nav_nirman).setVisible(false);
            menuNav.findItem(R.id.nav_domestic).setVisible(false);
            menuNav.findItem(R.id.nav_latestupdate).setVisible(false);
            menuNav.findItem(R.id.nav_helplineno).setVisible(false);
            menuNav.findItem(R.id.nav_songs).setVisible(false);
            menuNav.findItem(R.id.nav_registrationcard).setVisible(false);
            menuNav.findItem(R.id.nav_membershipcard).setVisible(false);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedFragment(item.getItemId());
        return true;
    }

    private void displaySelectedFragment(int menuItemId) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (menuItemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_latestupdate:
                fragment = new LatestUpdateFragment();
                break;
            case R.id.nav_verify:
                fragment = new VerifyFragment();
                break;
            case R.id.nav_feestatus:
                fragment = new FeeStatusFragment();
                break;
            case R.id.nav_nodal:
                fragment = new NodalFragment();
                break;

            case R.id.nav_cardvalidity:
                fragment = new CardExpiryOption();
                break;
            case R.id.nav_totalregistration:
                progressDialog.show();
                hitApiTogetCount();
                break;
            case R.id.nav_nirman:
                fragment = new ConstuctionOrDomesticFragment();
                bundle.putSerializable("workerType", "construction");
                bundle.putSerializable("district", TempStorage.getLoginData().getDistrictCode());
                fragment.setArguments(bundle);
                break;
            case R.id.nav_domestic:
                fragment = new ConstuctionOrDomesticFragment();
                bundle.putSerializable("workerType", "domestic");
                bundle.putSerializable("district", TempStorage.getLoginData().getDistrictCode());
                fragment.setArguments(bundle);
                break;

            case R.id.nav_logout:
                TempStorage.setLoginData(null);
                TempStorage.loginData = null;
                PreferencesManager.clear();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            default:
                break;
        }

        //replace the current fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void hitApiTogetCount() {
        Call<ResponseModel<WorkerCount>> responseModelCall = RestServiceFactory.createService().getWorkerCount(TempStorage.getLoginData().getId());

        responseModelCall.enqueue(new RestCallBack<ResponseModel<WorkerCount>>() {
            @Override
            public void onFailure(Call<ResponseModel<WorkerCount>> call, String message) {
                progressDialog.dismiss();
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<WorkerCount>> call, Response<ResponseModel<WorkerCount>> restResponse, ResponseModel<WorkerCount> response) {
                if (RestCallBack.isSuccessFull(response)) {
                    progressDialog.dismiss();
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.add_service, null);
                    final TextView textViewTotalRegistration = (TextView) alertLayout.findViewById(R.id.textViewTotalRegistration);
                    final TextView textViewTotalMember = (TextView) alertLayout.findViewById(R.id.textViewTotalMember);

                    textViewTotalRegistration.setText(response.data.getTotal() + "");
                    textViewTotalMember.setText(response.data.getUnverified() + "");
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);
                    alert.setPositiveButton(context.getString(R.string.str_ok), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            }
        });

    }
}
