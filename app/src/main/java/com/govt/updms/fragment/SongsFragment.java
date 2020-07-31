package com.govt.updms.fragment;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.govt.updms.R;
import com.govt.updms.adapter.SongListAdapter;
import com.govt.updms.rest.Response.VerifyWorker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongsFragment extends Fragment implements SongListAdapter.OnItemClickListener{

    private Activity activity;
    private Context context;
    private View viewRoot;
    @BindView(R.id.recyclerViewVerify)
    public RecyclerView recyclerViewVerify;
    @BindView(R.id.progressBarContent)
    public ProgressBar progressBarContent;

    private List<VerifyWorker> verifyWorkers = new ArrayList<>();
    private SongListAdapter songListAdapter;

    ListView mainList;
    MediaPlayer mp;
    final String[] listContent = {"Abhi ke padh ke na aile", "Sarkal jata badaniya se"};
    final int[] resID = {R.raw.music_1, R.raw.music_2};

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
        getActivity().setTitle(R.string.str_songs_title);
        mp= new MediaPlayer();
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewVerify.setLayoutManager(mLayoutManager);
        recyclerViewVerify.setHasFixedSize(false);
        recyclerViewVerify.setNestedScrollingEnabled(true);
        recyclerViewVerify.setItemAnimator(new DefaultItemAnimator());
        songListAdapter = new SongListAdapter(context, Arrays.asList(listContent), this);
        recyclerViewVerify.setAdapter(songListAdapter);


//        verifyListAdapter = new VerifyListAdapter(context, verifyWorkers, this);
//        recyclerViewVerify.setAdapter(verifyListAdapter);


    }

    public void playSong(int songIndex) {
        // Play song
        mp.reset();// stops any current playing song
        mp = MediaPlayer.create(getContext(), resID[songIndex]);
        mp.start(); // starting mediaplayer

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
        mp.release();


    }

    @Override
    public void onItemClick(View viewRoot, View view, String likeViewUserModel, int position) {
        playSong(position);
    }

    @Override
    public void onItemLongClick(View viewRoot, View view, String likeViewUserModel, int position) {

    }
}
