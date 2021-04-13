package com.example.onlinefir.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefir.R;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.ViewHolder> {
    private ComplainData[] listdata;

    // RecyclerView recyclerView;
    public ComplainAdapter(ComplainData[] listdata)
    {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.complain_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ComplainData myListData = listdata[position];
        holder.textViewComplainCategory.setText(listdata[position].getComplaintCategory());
        holder.textViewUserName.setText(listdata[position].getComplaintUserName());
        holder.textViewComplainStatus.setText(listdata[position].getComplaintStatus());
        holder.textViewDate.setText(listdata[position].getComplaintDate());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getComplaintCategory(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewComplainCategory;
        public TextView textViewUserName;
        public TextView textViewComplainStatus;
        public TextView textViewDate;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewComplainCategory = (TextView) itemView.findViewById(R.id.textViewComplainCategory);
            this.textViewUserName = (TextView) itemView.findViewById(R.id.textViewUserName);
            this.textViewComplainStatus = (TextView) itemView.findViewById(R.id.textViewComplainStatus);
            this.textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.constrainComplainList);
        }
    }
}
