package com.govt.updms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.govt.updms.R;
import com.govt.updms.rest.Response.MyBooking;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyBookingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyBooking> userModels;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnShowLastItemListener onShowLastItemListener;

    public interface OnItemClickListener {
        void onItemClick(View viewRoot, View view, MyBooking likeViewUserModel, int position);

        void onItemLongClick(View viewRoot, View view, MyBooking likeViewUserModel, int position);
    }

    public interface OnShowLastItemListener {
        void onShowLastItem();
    }

    public MyBookingListAdapter(Context context, List<MyBooking> list, OnItemClickListener onItemClickListener) {
        this.userModels = list;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        this.onShowLastItemListener = onShowLastItemListener;
    }

    public void addItems(List<MyBooking> data) {
        try {
            userModels.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll(){
        userModels.clear();
    }


    public MyBooking getItem(int position) {
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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mybooking, parent, false);
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
        @BindView(R.id.textviewName)
        public TextView textViewTitle;
        @BindView(R.id.textViewPrice)
        public TextView textViewPrice;
        @BindView(R.id.textviewStatus)
        public TextView textviewStatus;
        @BindView(R.id.comments)
        public TextView comments;
        @BindView(R.id.service_name)
        public TextView serviceName;

        @BindView(R.id.layoutContent)
        public LinearLayout layoutContent;
        @BindView(R.id.comments_view)
        public LinearLayout commentsView;
        public View viewRoot;

        public MyViewHolder(View view) {
            super(view);
            viewRoot = view;
            ButterKnife.bind(this, view);
        }

        public void bind(final MyBooking subCategory, final int position, final OnItemClickListener onItemClickListener) {

            textViewTitle.setText(subCategory.getServiceCategory().getUser().getName());
            textViewPrice.setText(subCategory.getServiceCategory().getPrice()+"");
            textviewStatus.setText(subCategory.getStatus());
            serviceName.setText(subCategory.getServiceCategory().getObject().getName());
            if (subCategory.getComments() != null) {
                commentsView.setVisibility(View.VISIBLE);
                comments.setText(subCategory.getComments());
            } else {
                commentsView.setVisibility(View.GONE);
            }
            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewRoot,layoutContent,subCategory,position);
                }
            });

            }





    }
}
