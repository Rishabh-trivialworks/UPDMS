package com.govt.updms.fragment;

import android.app.Activity;
import android.content.Context;
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

import com.govt.updms.R;
import com.govt.updms.adapter.LatestUpdateListAdapter;
import com.govt.updms.rest.Response.LatestUpdate;
import com.govt.updms.rest.Response.ResponseModel;
import com.govt.updms.rest.RestCallBack;
import com.govt.updms.rest.RestServiceFactory;
import com.govt.updms.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class LatestUpdateFragment extends Fragment implements LatestUpdateListAdapter.OnItemClickListener{
    private Activity activity;
    private Context context;
    private View viewRoot;
    @BindView(R.id.recyclerViewVerify)
    public RecyclerView recyclerViewVerify;
    @BindView(R.id.progressBarContent)
    public ProgressBar progressBarContent;
    private List<LatestUpdate> latestUpdates = new ArrayList<>();
    private LatestUpdateListAdapter latestlistAdapter;


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
        getActivity().setTitle(R.string.latestupdate);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewVerify.setLayoutManager(mLayoutManager);
        recyclerViewVerify.setHasFixedSize(false);
        recyclerViewVerify.setNestedScrollingEnabled(true);
        recyclerViewVerify.setItemAnimator(new DefaultItemAnimator());
        latestlistAdapter = new LatestUpdateListAdapter(context, latestUpdates, this);
        recyclerViewVerify.setAdapter(latestlistAdapter);
        progressBarContent.setVisibility(View.VISIBLE);
        hitApiToGetLatestUpdate();

    }

    private void hitApiToGetLatestUpdate() {

        Call<ResponseModel<List<LatestUpdate>>> responseModelCall = RestServiceFactory.createService().getLatestUpdate();

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<LatestUpdate>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<LatestUpdate>>> call, String message) {
                ToastUtils.show(context,message);
                progressBarContent.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<LatestUpdate>>> call, Response<ResponseModel<List<LatestUpdate>>> restResponse, ResponseModel<List<LatestUpdate>> response) {
                if(RestCallBack.isSuccessFull(response)){
                    progressBarContent.setVisibility(View.GONE);
                    latestlistAdapter.addItems(response.data);
                    latestlistAdapter.notifyDataSetChanged();
                }
            }
        });


    }


    @Override
    public void onItemClick(View viewRoot, View view, LatestUpdate likeViewUserModel, int position) {

    }

    @Override
    public void onItemLongClick(View viewRoot, View view, LatestUpdate likeViewUserModel, int position) {

    }
}
