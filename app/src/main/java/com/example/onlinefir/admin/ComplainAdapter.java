package com.example.onlinefir.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefir.R;

import java.util.List;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.ViewHolder> {
    List<ComplainData> fetchData;

    public ComplainAdapter(List<ComplainData> fetchData) {
        this.fetchData = fetchData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ComplainData fetchDataList = fetchData.get(position);
        viewHolder.textViewUserName.setText(fetchDataList.getUser_name());
        viewHolder.textViewComplainCategory.setText(fetchDataList.getCategory());
        viewHolder.textViewDate.setText(fetchDataList.getDate_of_incident());
        viewHolder.textViewComplainStatus.setText(fetchDataList.getEmail());
    }


    @Override
    public int getItemCount() {
        return fetchData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewComplainCategory;
        public TextView textViewUserName;
        public TextView textViewComplainStatus;
        public TextView textViewDate;
        public LinearLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewComplainCategory = itemView.findViewById(R.id.textViewComplainCategory);
            this.textViewUserName = itemView.findViewById(R.id.textViewUserName);
            this.textViewComplainStatus = itemView.findViewById(R.id.textViewComplainStatus);
            this.textViewDate = itemView.findViewById(R.id.textViewDate);
            constraintLayout = itemView.findViewById(R.id.constrainComplainList);
        }
    }
}
