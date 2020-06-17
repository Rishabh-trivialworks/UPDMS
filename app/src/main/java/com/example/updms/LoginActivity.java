package com.example.updms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.updms.constants.AppConstants;
import com.example.updms.rest.RequestModel.LoginRequest;
import com.example.updms.rest.Response.LoginData;
import com.example.updms.rest.Response.ResponseModel;
import com.example.updms.rest.Response.WorkerLoginData;
import com.example.updms.rest.RestCallBack;
import com.example.updms.rest.RestServiceFactory;
import com.example.updms.util.DialogFactory;
import com.example.updms.util.DialogUtils;
import com.example.updms.util.TempStorage;
import com.example.updms.util.ToastUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {

    private String[] array_type;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.liniarlayoutuser)
    LinearLayout liniarlayoutuser;
    @BindView(R.id.layout_email)
    TextInputLayout layout_email;
    @BindView(R.id.layout_password)
    TextInputLayout layout_password;
    @BindView(R.id.layout_mobile)
    TextInputLayout layout_mobile;
    @BindView(R.id.button_signupbelow)
    Button button_signupbelow;
    @BindView(R.id.button_sinin)
    Button button_sinin;
    @BindView(R.id.linear_registration)
    LinearLayout linear_registration;
    @BindView(R.id.edit_username)
    EditText edit_username;
    @BindView(R.id.edit_mobile)
    EditText edit_mobile;

    @BindView(R.id.et_passwrod)
    EditText et_passwrod;


    Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        context = this;
        progressDialog = DialogFactory.createProgressDialog(context, context.getString(R.string.please_wait));

        array_type = new String[]{"Select your type", "NODAL", "ADMIN", "WORKER"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                if (position == 0) {
                    liniarlayoutuser.setVisibility(View.GONE);
                    ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.transparent_25));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimaryDark));
                    if (position == 1 || position == 2) {
                        liniarlayoutuser.setVisibility(View.VISIBLE);
                        layout_email.setVisibility(View.VISIBLE);
                        layout_password.setVisibility(View.VISIBLE);
                        layout_mobile.setVisibility(View.GONE);
                        linear_registration.setVisibility(View.GONE);

                    } else if (position == 3) {
                        liniarlayoutuser.setVisibility(View.VISIBLE);
                        layout_mobile.setVisibility(View.VISIBLE);
                        layout_email.setVisibility(View.GONE);
                        layout_password.setVisibility(View.GONE);
                        linear_registration.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button_signupbelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final List<String> items = new ArrayList<>();
                items.add(getString(R.string.str_domestic_worker));
                items.add(getString(R.string.str_construction_worker));

                DialogUtils.showBasicList(
                        context,
                        context.getString(R.string.please_choose_option),
                        items,
                        new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(final MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String selectedItem = items.get(position);
                                if (context.getString(R.string.str_construction_worker).equals(selectedItem)) {
                                    Intent in = new Intent(LoginActivity.this, SignUpActivity.class);
                                    in.putExtra("type", context.getString(R.string.str_construction_worker));
                                    startActivity(in);
                                } else if (context.getString(R.string.str_domestic_worker).equals(selectedItem)) {
                                    Intent in = new Intent(LoginActivity.this, SignUpActivity.class);
                                    in.putExtra("type", context.getString(R.string.str_domestic_worker));
                                    startActivity(in);
                                }
                            }
                        });

            }
        });

        edit_username.setOnTouchListener(this);
        et_passwrod.setOnTouchListener(this);

        button_sinin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                LoginRequest loginRequest;
                if (spinner.getSelectedItem().equals("NODAL") || spinner.getSelectedItem().equals("ADMIN")) {
                    loginRequest = new LoginRequest(edit_username.getText().toString(), et_passwrod.getText().toString(), spinner.getSelectedItem().toString());
                    hitApiToGetLogin(loginRequest);

                } else {
                    loginRequest = new LoginRequest(edit_mobile.getText().toString(), spinner.getSelectedItem().toString());
                    hitApiToGetWorkerLogin(loginRequest);
                }

            }
        });
    }

    private void hitApiToGetLogin(LoginRequest loginRequest) {


        Call<ResponseModel<LoginData>> responseModelCall = RestServiceFactory.createService().login(loginRequest);

        responseModelCall.enqueue(new RestCallBack<ResponseModel<LoginData>>() {
            @Override
            public void onFailure(Call<ResponseModel<LoginData>> call, String message) {
                progressDialog.dismiss();
                DialogUtils.showBasicWarning(context, message,new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });

            }

            @Override
            public void onResponse(Call<ResponseModel<LoginData>> call, Response<ResponseModel<LoginData>> restResponse, ResponseModel<LoginData> response) {
                progressDialog.dismiss();
                if (RestCallBack.isSuccessFull(response)) {
                    TempStorage.setLoginData(response.data);
                    Intent in = new Intent(context, HomeActivity.class);
                    startActivity(in);
                    finish();

                }

            }
        });
    }

    private void hitApiToGetWorkerLogin(LoginRequest loginRequest) {


        Call<ResponseModel<WorkerLoginData>> responseModelCall = RestServiceFactory.createService().workerLogin(loginRequest);

        responseModelCall.enqueue(new RestCallBack<ResponseModel<WorkerLoginData>>() {
            @Override
            public void onFailure(Call<ResponseModel<WorkerLoginData>> call, String message) {
                ToastUtils.show(context, message);
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(Call<ResponseModel<WorkerLoginData>> call, Response<ResponseModel<WorkerLoginData>> restResponse, ResponseModel<WorkerLoginData> response) {
                progressDialog.dismiss();
                if (RestCallBack.isSuccessFull(response)) {
                    TempStorage.setWorkerLoginData(response.data);

                    Intent in = new Intent(context, WorkerNewHomePageActivity.class);
                    startActivity(in);
                    finish();
                }


            }
        });
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()) {
            case R.id.edit_username:
                if (view.getId() == R.id.edit_username) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if ((motionEvent.getAction() & motionEvent.getAction()) == 1) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
//                edit_username.setOnTouchListener((View.OnTouchListener)this);
//                Utils.hideSoftKeyboard(LoginActivity.this,edit_username);
                return false;
//            case R.id.et_passwrod:
//                selectKeyboard(et_passwrod);
//                if (view.getId() == R.id.et_passwrod) {
//                    view.getParent().requestDisallowInterceptTouchEvent(true);
//                    if ((motionEvent.getAction() & motionEvent.getAction()) == 1) {
//                        view.getParent().requestDisallowInterceptTouchEvent(false);
//                    }
//                }
////                et_passwrod.setOnTouchListener((View.OnTouchListener)this);
////                Utils.hideSoftKeyboard(LoginActivity.this,et_passwrod);
//                return false;
            default:
                return false;
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
}
