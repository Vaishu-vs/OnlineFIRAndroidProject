package com.example.onlinefir.complainStatus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefir.R;
import com.example.onlinefir.admin.ComplainData;

import java.util.List;

class MyComplainAdapter extends RecyclerView.Adapter<MyComplainAdapter.ViewHolder> {
    List<ComplainData> fetchData;
    Context context;
    public MyComplainAdapter(List<ComplainData> fetchData,Context context) {
        this.fetchData = fetchData;
        this.context = context;
    }

    @Override
    public MyComplainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_list_item, parent, false);
        MyComplainAdapter.ViewHolder viewHolder = new MyComplainAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyComplainAdapter.ViewHolder holder, int position) {
        MyComplainAdapter.ViewHolder viewHolder = (MyComplainAdapter.ViewHolder) holder;
        ComplainData fetchDataList = fetchData.get(position);

        viewHolder.textViewUserName.setText(fetchDataList.getEmail());
        viewHolder.textViewComplainCategory.setText(fetchDataList.getCategory());
        viewHolder.textViewDate.setText(fetchDataList.getDate_of_incident());
        viewHolder.textViewComplainStatus.setText(fetchDataList.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DisplayInListActivity.class);
                i.putExtra("uid", fetchDataList.getKey());
                context.startActivity(i);
            }
        });
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
