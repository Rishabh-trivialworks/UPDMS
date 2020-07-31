package com.govt.updms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.govt.updms.R;
import com.govt.updms.rest.Response.NodalList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NodalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NodalList> verifyWorkers;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnShowLastItemListener onShowLastItemListener;

    public interface OnItemClickListener {
        void onItemClick(View viewRoot, View view, NodalList likeViewUserModel, int position);

        void onItemLongClick(View viewRoot, View view, NodalList likeViewUserModel, int position);
    }

    public interface OnShowLastItemListener {
        void onShowLastItem();
    }

    public NodalListAdapter(Context context, List<NodalList> list, OnItemClickListener onItemClickListener) {
        this.verifyWorkers = list;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        this.onShowLastItemListener = onShowLastItemListener;
    }

    public void addItems(List<NodalList> data) {
        try {
            verifyWorkers.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        verifyWorkers.clear();
    }

    public void replaceItem(NodalList data) {

        for (int i = 0; i < verifyWorkers.size(); i++) {
            if (verifyWorkers.get(i).getId() == data.getId()) {
                verifyWorkers.remove(i);
                verifyWorkers.add(i, data);
            }
        }
    }


    public NodalList getItem(int position) {
        try {
            return verifyWorkers.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return verifyWorkers.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nodal, parent, false);
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
        @BindView(R.id.textViewActiveStatus)
        public TextView textViewActiveStatus;
        @BindView(R.id.textViewNodalName)
        public TextView textViewNodalName;
        @BindView(R.id.textViewMobileNo)
        public TextView textViewMobileNo;

        @BindView(R.id.textViewEmailid)
        public TextView textViewEmailid;
        @BindView(R.id.textViewuserId)
        public TextView textViewuserId;
        @BindView(R.id.textViewRole)
        public TextView textViewRole;
        @BindView(R.id.textViewDate)
        public TextView textViewDate;
        //
        @BindView(R.id.layoutContent)
        public LinearLayout layoutContent;
        //        @BindView(R.id.comments_view)
//        public LinearLayout commentsView;
        public View viewRoot;

        public MyViewHolder(View view) {
            super(view);
            viewRoot = view;
            ButterKnife.bind(this, view);
        }

        public void bind(final NodalList subCategory, final int position, final OnItemClickListener onItemClickListener) {

            textViewNodalName.setText(subCategory.getUserName());
            textViewuserId.setText(subCategory.getId()+"");
            textViewEmailid.setText(subCategory.getEmail());
            textViewActiveStatus.setText(subCategory.isActivationStatus()?"ACTIVE":"DEACTIVATE");
            if(subCategory.isActivationStatus()){
                textViewActiveStatus.setTextColor(ContextCompat.getColor(context, R.color.teacher_select));
            }else {
                textViewActiveStatus.setTextColor(ContextCompat.getColor(context, R.color.red_color));
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date date =null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(subCategory.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            textViewDate.setText(dateStr);
            textViewRole.setText(subCategory.getUserType());
            textViewMobileNo.setText(subCategory.getMobile());

            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewRoot, layoutContent, subCategory, position);
                }
            });

        }


    }
}
