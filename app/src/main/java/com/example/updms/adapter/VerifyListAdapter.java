package com.example.updms.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.updms.R;
import com.example.updms.SignUpActivity;
import com.example.updms.rest.Response.VerifyWorker;
import com.example.updms.rest.Response.VerifyWorker;

import org.w3c.dom.NameList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VerifyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VerifyWorker> verifyWorkers;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnShowLastItemListener onShowLastItemListener;
    int fromWhere;

    public interface OnItemClickListener {
        void onItemClick(View viewRoot, View view, VerifyWorker likeViewUserModel, int position);

        void onItemLongClick(View viewRoot, View view, VerifyWorker likeViewUserModel, int position);
    }

    public interface OnShowLastItemListener {
        void onShowLastItem();
    }

    public VerifyListAdapter(Context context, List<VerifyWorker> list, int fromWhere, OnItemClickListener onItemClickListener) {
        this.verifyWorkers = list;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        this.fromWhere = fromWhere;
        this.onShowLastItemListener = onShowLastItemListener;
    }

    public void addItems(List<VerifyWorker> data) {
        try {
            verifyWorkers.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        verifyWorkers.clear();
    }

    public void replaceItem(VerifyWorker data) {

        for (int i = 0; i < verifyWorkers.size(); i++) {
            if (verifyWorkers.get(i).getMembershipId().equalsIgnoreCase(data.getMembershipId())) {
                verifyWorkers.remove(i);
                verifyWorkers.add(i, data);
            }
        }
    }

    public void removeItem( VerifyWorker data){
        for (int i = 0; i < verifyWorkers.size(); i++) {
            if (verifyWorkers.get(i).getMembershipId().equalsIgnoreCase(data.getMembershipId())) {
                verifyWorkers.remove(i);

            }
        }
    }


    public VerifyWorker getItem(int position) {
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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verify_worker, parent, false);
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
        @BindView(R.id.textViewRegistrationNo)
        public TextView textViewRegistrationNo;
        @BindView(R.id.textViewRegistrationDate)
        public TextView textViewRegistrationDate;
        @BindView(R.id.textViewRegistrationDOB)
        public TextView textViewRegistrationDOB;
        @BindView(R.id.textViewRegistrationName)
        public TextView textViewRegistrationName;
        @BindView(R.id.textViewFees)
        public TextView textViewFees;

        @BindView(R.id.textViewWorkerType)
        public TextView textViewWorkerType;
        @BindView(R.id.textViewNodalverify)
        public TextView textViewNodalverify;
        @BindView(R.id.textViewAdminVerify)
        public TextView textViewAdmin;
        @BindView(R.id.textViewRenewDate)
        public TextView textViewRenewDate;
        //
        @BindView(R.id.layoutContent)
        public LinearLayout layoutContent;

        @BindView(R.id.liniearReason)
        public LinearLayout liniearReason;
        @BindView(R.id.textViewReason)
        public TextView textViewReason;

        @BindView(R.id.imageViewEdit)
        public ImageView imageViewEdit;
        //        @BindView(R.id.comments_view)
//        public LinearLayout commentsView;
        public View viewRoot;

        public MyViewHolder(View view) {
            super(view);
            viewRoot = view;
            ButterKnife.bind(this, view);
        }

        public void bind(final VerifyWorker subCategory, final int position, final OnItemClickListener onItemClickListener) {

            textViewRegistrationNo.setText(subCategory.getMembershipId());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(subCategory.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            textViewRegistrationDate.setText(dateStr);
            textViewRegistrationDOB.setText(subCategory.getDateOfBirth());
            textViewRegistrationName.setText(subCategory.getName());
            textViewFees.setText(subCategory.isRegFeeStatus() ? context.getString(R.string.str_yes) : context.getString(R.string.str_no));
            if (subCategory.isRegFeeStatus()) {
                textViewFees.setTextColor(ContextCompat.getColor(context, R.color.teacher_select));
            } else {
                textViewFees.setTextColor(ContextCompat.getColor(context, R.color.red_color));

            }
            if (subCategory.isApprovedByNodal()) {
                textViewNodalverify.setTextColor(ContextCompat.getColor(context, R.color.teacher_select));
            } else {
                textViewNodalverify.setTextColor(ContextCompat.getColor(context, R.color.red_color));

            }
            if (subCategory.isApprovedByAdmin()) {
                textViewAdmin.setTextColor(ContextCompat.getColor(context, R.color.teacher_select));
            } else {
                textViewAdmin.setTextColor(ContextCompat.getColor(context, R.color.red_color));

            }
            textViewNodalverify.setText(subCategory.isApprovedByNodal() ? context.getString(R.string.str_yes) : context.getString(R.string.str_no));
            textViewAdmin.setText(subCategory.isApprovedByAdmin() ? context.getString(R.string.str_yes) : context.getString(R.string.str_no));
            if (subCategory.getRegFeeValidity() != null) {
                textViewRenewDate.setVisibility(View.VISIBLE);
                if (fromWhere == 1) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date strDate = null;
                    try {
                        strDate = sdf.parse(subCategory.getRegFeeValidity());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (System.currentTimeMillis() > strDate.getTime()) {
                        textViewRenewDate.setText(subCategory.getRegFeeValidity() + " (Expire) ");
                        textViewRenewDate.setTextColor(ContextCompat.getColor(context, R.color.red_color));
                    } else {
                        textViewRenewDate.setText(subCategory.getRegFeeValidity() + " (Going to Expire) ");
                        textViewRenewDate.setTextColor(ContextCompat.getColor(context, R.color.bg_orange));
                    }

                } else {
                    textViewRenewDate.setText(subCategory.getRegFeeValidity() + "");
                }
            } else {
                textViewRenewDate.setVisibility(View.GONE);
            }
            textViewWorkerType.setText(subCategory.getWorkerType());

            if ((subCategory.getAdminReason() != null && !subCategory.getAdminReason().equalsIgnoreCase("")) || subCategory.getNodalReason() != null && !subCategory.getNodalReason().equalsIgnoreCase("")) {
                liniearReason.setVisibility(View.VISIBLE);
                if (subCategory.getAdminReason() != null && !subCategory.getAdminReason().equalsIgnoreCase(""))
                    textViewReason.setText(subCategory.getAdminReason());
                else
                    textViewReason.setText(subCategory.getNodalReason());

            } else {
                liniearReason.setVisibility(View.GONE);
            }

            if (fromWhere == 1) {
                imageViewEdit.setVisibility(View.GONE);
            }
            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewRoot, layoutContent, subCategory, position);
                }
            });

            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewRoot, imageViewEdit, subCategory, position);

                }
            });

        }


    }
}
