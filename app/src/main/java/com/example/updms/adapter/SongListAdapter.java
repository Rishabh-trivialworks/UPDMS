package com.example.updms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.updms.R;
import com.example.updms.rest.Response.MyBooking;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SongListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> userModels;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnShowLastItemListener onShowLastItemListener;

    public interface OnItemClickListener {
        void onItemClick(View viewRoot, View view, String likeViewUserModel, int position);

        void onItemLongClick(View viewRoot, View view, String likeViewUserModel, int position);
    }

    public interface OnShowLastItemListener {
        void onShowLastItem();
    }

    public SongListAdapter(Context context, List<String> list, OnItemClickListener onItemClickListener) {
        this.userModels = list;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        this.onShowLastItemListener = onShowLastItemListener;
    }

    public void addItems(List<String> data) {
        try {
            userModels.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll(){
        userModels.clear();
    }


    public String getItem(int position) {
        try {
            return userModels.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songs, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        return 0;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (position == getItemCount() - 1) {
                if (onShowLastItemListener != null) {
                    onShowLastItemListener.onShowLastItem();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).bind(getItem(position), position, onItemClickListener);
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewSong)
        public TextView textViewSong;

        public View viewRoot;

        public MyViewHolder(View view) {
            super(view);
            viewRoot = view;
            ButterKnife.bind(this, view);
        }

        public void bind(final String subCategory, final int position, final OnItemClickListener onItemClickListener) {


            textViewSong.setText(subCategory);
            textViewSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewRoot,textViewSong,subCategory,position);
                }
            });

            }





    }
}
