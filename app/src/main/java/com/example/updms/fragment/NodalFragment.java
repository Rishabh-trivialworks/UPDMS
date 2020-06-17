package com.example.updms.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.updms.LoginActivity;
import com.example.updms.R;
import com.example.updms.SignUpActivity;
import com.example.updms.adapter.NodalListAdapter;
import com.example.updms.adapter.VerifyListAdapter;
import com.example.updms.rest.RequestModel.NodalRequest;
import com.example.updms.rest.RequestModel.VerifyNodalRequest;
import com.example.updms.rest.Response.NodalList;
import com.example.updms.rest.Response.ResponseModel;
import com.example.updms.rest.Response.VerifyWorker;
import com.example.updms.rest.RestCallBack;
import com.example.updms.rest.RestServiceFactory;
import com.example.updms.util.DialogUtils;
import com.example.updms.util.TempStorage;
import com.example.updms.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NodalFragment extends Fragment implements NodalListAdapter.OnItemClickListener {


    private Activity activity;
    private Context context;
    private View viewRoot;
    @BindView(R.id.recyclerViewVerify)
    public RecyclerView recyclerViewVerify;
    @BindView(R.id.progressBarContent)
    public ProgressBar progressBarContent;

    private List<NodalList> nodalLists = new ArrayList<>();
    private NodalListAdapter nodalListAdapter;
    public NodalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_verify, container, false);
        ButterKnife.bind(this, viewRoot);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        context = getActivity();
        getActivity().setTitle(R.string.str_manage_nodal);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewVerify.setLayoutManager(mLayoutManager);
        recyclerViewVerify.setHasFixedSize(false);
        recyclerViewVerify.setNestedScrollingEnabled(true);
        recyclerViewVerify.setItemAnimator(new DefaultItemAnimator());
        nodalListAdapter = new NodalListAdapter(context, nodalLists, this);
        recyclerViewVerify.setAdapter(nodalListAdapter);
        progressBarContent.setVisibility(View.VISIBLE);
        hitApiTogetNodalList();
    }

    private void hitApiTogetNodalList() {

        Call<ResponseModel<List<NodalList>>> responseModelCall = RestServiceFactory.createService().getNodalList();

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<NodalList>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<NodalList>>> call, String message) {
                progressBarContent.setVisibility(View.GONE);
                ToastUtils.show(context,message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<NodalList>>> call, Response<ResponseModel<List<NodalList>>> restResponse, ResponseModel<List<NodalList>> response) {
                progressBarContent.setVisibility(View.GONE);
                if(RestCallBack.isSuccessFull(response)&& response.data!=null && response.data.size()>0){

                    nodalListAdapter.addItems(response.data);
                    nodalListAdapter.notifyDataSetChanged();


                }

            }
        });
    }

    @Override
    public void onItemClick(View viewRoot, View view, NodalList likeViewUserModel, int position) {
        final List<String> items = new ArrayList<>();
        if (likeViewUserModel.isActivationStatus()) {
            items.add(getString(R.string.str_deactive));
        } else {
            items.add(getString(R.string.str_active));
        }

        DialogUtils.showBasicList(
                context,
                context.getString(R.string.please_choose_option),
                items,
                new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(final MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        String selectedItem = items.get(position);
                        if (context.getString(R.string.str_deactive).equals(selectedItem)) {
                            NodalRequest nodalRequest = new NodalRequest(likeViewUserModel.getId(),TempStorage.getLoginData().getId(),false);
                            progressBarContent.setVisibility(View.VISIBLE);
                            hitApiTosendNodalStatus(nodalRequest);

                        } else if (context.getString(R.string.str_active).equals(selectedItem)) {
                            NodalRequest nodalRequest = new NodalRequest(likeViewUserModel.getId(),TempStorage.getLoginData().getId(),true);
                            hitApiTosendNodalStatus(nodalRequest);
                        }
                    }
                });


    }

    private void hitApiTosendNodalStatus(NodalRequest nodalRequest) {

        Call<ResponseModel<NodalList>> responseModelCall = RestServiceFactory.createService().approvenodal(nodalRequest);

        responseModelCall.enqueue(new RestCallBack<ResponseModel<NodalList>>() {
            @Override
            public void onFailure(Call<ResponseModel<NodalList>> call, String message) {
                ToastUtils.show(context,message);
                progressBarContent.setVisibility(View.GONE);

            }

            @Override
            public void onResponse(Call<ResponseModel<NodalList>> call, Response<ResponseModel<NodalList>> restResponse, ResponseModel<NodalList> response) {
                progressBarContent.setVisibility(View.GONE);
                if(RestCallBack.isSuccessFull(response)){
                    nodalListAdapter.replaceItem(response.data);
                    nodalListAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void onItemLongClick(View viewRoot, View view, NodalList likeViewUserModel, int position) {

    }
}
