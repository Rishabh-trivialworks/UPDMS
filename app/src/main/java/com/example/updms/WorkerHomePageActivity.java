package com.example.updms;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.updms.rest.Response.WorkerLoginData;
import com.example.updms.util.Fab;
import com.example.updms.util.TempStorage;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.shawnlin.preferencesmanager.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkerHomePageActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewValidity)
    TextView textViewValidity;
    @BindView(R.id.textViewSeriolNo)
    TextView textViewSeriolNo;
    @BindView(R.id.textViewFatherName)
    TextView textViewFatherName;
    @BindView(R.id.textViewPermanent)
    TextView textViewPermanent;
    @BindView(R.id.textViewLocal)
    TextView textViewLocal;
    @BindView(R.id.textViewOccupation)
    TextView textViewOccupation;
    @BindView(R.id.textViewMobile)
    TextView textViewMobile;
    private WorkerLoginData workerLoginData;
    private MaterialSheetFab<Fab> materialSheetFab;
    private int statusBarColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_home_page);
        ButterKnife.bind(this);
        context = this;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(context.getString(R.string.membership_card));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setupFab();
        workerLoginData = TempStorage.getWorkerLoginData();

        textViewValidity.setText(context.getString(R.string.validity) + " " + workerLoginData.getRegFeeValidity());
        textViewSeriolNo.setText(context.getString(R.string.seriol_no) + " " + workerLoginData.getMembershipId());
        textViewName.setText(context.getString(R.string.str_dms_name) + " :" + " " + workerLoginData.getName());
        textViewFatherName.setText(context.getString(R.string.father_husband) + " " + workerLoginData.getFatherName());
        textViewPermanent.setText(context.getString(R.string.permanent_address) + " " + workerLoginData.getPermanentAddress());
        textViewLocal.setText(context.getString(R.string.local_address) + " " + workerLoginData.getPermanentAddress());
        textViewOccupation.setText(context.getString(R.string.vyvsay) + " " + workerLoginData.getOccupation());
        textViewMobile.setText(context.getString(R.string.mobile_no) + " " + workerLoginData.getMobileNo());

    }

    private void setupFab() {

        Fab fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.textColorPrimary);
        int fabColor = getResources().getColor(R.color.textColorPrimary);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners

        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_note).setOnClickListener(this);
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case R.id.fab_sheet_item_photo:
                Toast.makeText(this, "Print", Toast.LENGTH_SHORT).show();

                break;

            case R.id.fab_sheet_item_note:
                TempStorage.setLoginData(null);
                TempStorage.loginData = null;
                PreferencesManager.clear();
                Intent intent = new Intent(WorkerHomePageActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
        materialSheetFab.hideSheet();
    }
}
