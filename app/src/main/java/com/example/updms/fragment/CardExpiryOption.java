package com.example.updms.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.updms.R;
import com.example.updms.adapter.SpinAdapter;
import com.example.updms.adapter.VerifyListAdapter;
import com.example.updms.rest.RequestModel.RenewWorker;
import com.example.updms.rest.RequestModel.VerifyAdminRequest;
import com.example.updms.rest.RequestModel.VerifyNodalRequest;
import com.example.updms.rest.Response.District;
import com.example.updms.rest.Response.ResponseModel;
import com.example.updms.rest.Response.States;
import com.example.updms.rest.Response.VerifyWorker;
import com.example.updms.rest.RestCallBack;
import com.example.updms.rest.RestServiceFactory;
import com.example.updms.util.DialogUtils;
import com.example.updms.util.TempStorage;
import com.example.updms.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class CardExpiryOption extends Fragment implements VerifyListAdapter.OnItemClickListener{
    private Activity activity;
    private Context context;
    private View viewRoot;

    @BindView(R.id.recyclerViewVerify)
    public RecyclerView recyclerViewVerify;
    @BindView(R.id.progressBarContent)
    public ProgressBar progressBarContent;
    @BindView(R.id.spinnerDistrict)
    Spinner spinnerDistrict;
    @BindView(R.id.spinnerState)
    Spinner spinnerState;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    private SpinAdapter adapterState, adapterDistrict;
    private List<VerifyWorker> verifyWorkers = new ArrayList<>();
    private VerifyListAdapter verifyListAdapter;
    private int selectedState;
    private int selectedDistrict;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_cardexpiry, container, false);
        ButterKnife.bind(this, viewRoot);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        context = getActivity();
        getActivity().setTitle(R.string.str_status_card_expire);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewVerify.setLayoutManager(mLayoutManager);
        recyclerViewVerify.setHasFixedSize(false);
        recyclerViewVerify.setNestedScrollingEnabled(true);
        recyclerViewVerify.setItemAnimator(new DefaultItemAnimator());
        verifyListAdapter = new VerifyListAdapter(context, verifyWorkers, 1,this);
        recyclerViewVerify.setAdapter(verifyListAdapter);
        progressBarContent.setVisibility(View.VISIBLE);

        if(TempStorage.getLoginData().getUserType().equalsIgnoreCase("NODAL")){
            linearLayout.setVisibility(View.GONE);
            hitAPiTogetExpiryData(TempStorage.getLoginData().getDistrictCode());
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            hitApiTogetState();
        }



//        verifyListAdapter = new VerifyListAdapter(context, verifyWorkers, this);
//        recyclerViewVerify.setAdapter(verifyListAdapter);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                States states = (States) adapterState.getItem(position);
                selectedState = states.getStateCode();
                hitApitogetDistrict(states.getStateCode());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                District states = (District) adapterDistrict.getItem(position);
                selectedDistrict = states.getDistrictCode();

                 hitAPiTogetExpiryData(states.getDistrictCode());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void hitAPiTogetExpiryData(int districtCode) {

        Call<ResponseModel<List<VerifyWorker>>> responseModelCall = RestServiceFactory.createService().getExpiryWorkerList(districtCode);

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<VerifyWorker>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<VerifyWorker>>> call, String message) {
                progressBarContent.setVisibility(View.GONE);
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<VerifyWorker>>> call, Response<ResponseModel<List<VerifyWorker>>> restResponse, ResponseModel<List<VerifyWorker>> response) {
                progressBarContent.setVisibility(View.GONE);
                if (RestCallBack.isSuccessFull(response) && response.data != null && response.data.size() > 0) {

                    verifyListAdapter.addItems(response.data);
                    verifyListAdapter.notifyDataSetChanged();


                }

            }
        });
    }

    private void hitApiTogetWorkerList() {


    }


    private void hitApitogetDistrict(int stateCode) {
        Call<ResponseModel<List<District>>> responseModelCall = RestServiceFactory.createService().getDistrictByState(stateCode);

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<District>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<District>>> call, String message) {
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<District>>> call, Response<ResponseModel<List<District>>> restResponse, ResponseModel<List<District>> response) {
                if (RestCallBack.isSuccessFull(response)) {
                    adapterDistrict = new SpinAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.data, 3);
                    spinnerDistrict.setAdapter(adapterDistrict);


                }
            }
        });
    }


    private void hitApiTogetState() {

        Call<ResponseModel<List<States>>> responseModelCall = RestServiceFactory.createService().getStates();

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<States>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<States>>> call, String message) {
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<States>>> call, Response<ResponseModel<List<States>>> restResponse, ResponseModel<List<States>> response) {
                if (RestCallBack.isSuccessFull(response) && response.data != null) {
                    for (int i = 0; i < response.data.size(); i++) {
                        if (response.data.get(i).getStateCode() == 9) {
                            response.data.set(0, response.data.get(i));
                        }
                    }
                    adapterState = new SpinAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.data, 1);
                    spinnerState.setAdapter(adapterState);

                }
            }
        });


    }


    @Override
    public void onItemClick(View viewRoot, View view, VerifyWorker likeViewUserModel, int position) {

        final List<String> items = new ArrayList<>();
        items.add(getString(R.string.renew_membership));

        DialogUtils.showBasicList(
                context,
                context.getString(R.string.please_choose_option),
                items,
                new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(final MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        RenewWorker objVeriFy = new RenewWorker(TempStorage.getLoginData().getId(), likeViewUserModel.getWorkerId());
                        progressBarContent.setVisibility(View.VISIBLE);
                        hitApiToVerifySatyapan(objVeriFy);
                    }
                });

    }

    @Override
    public void onItemLongClick(View viewRoot, View view, VerifyWorker likeViewUserModel, int position) {

    }

    private void hitApiToVerifySatyapan(RenewWorker renewWorker) {
        Call<ResponseModel<VerifyWorker>> responseModelCall;

            responseModelCall = RestServiceFactory.createService().verifyByRenew(renewWorker);


        responseModelCall.enqueue(new RestCallBack<ResponseModel<VerifyWorker>>() {
            @Override
            public void onFailure(Call<ResponseModel<VerifyWorker>> call, String message) {
                ToastUtils.show(context, message);
                progressBarContent.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call<ResponseModel<VerifyWorker>> call, Response<ResponseModel<VerifyWorker>> restResponse, ResponseModel<VerifyWorker> response) {
                progressBarContent.setVisibility(View.GONE);
                if (RestCallBack.isSuccessFull(response)) {
                    verifyListAdapter.removeItem(response.data);
                    verifyListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
