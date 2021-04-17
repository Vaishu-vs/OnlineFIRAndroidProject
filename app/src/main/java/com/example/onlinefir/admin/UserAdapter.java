package com.example.onlinefir.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefir.R;
import com.example.onlinefir.complainStatus.DisplayInListActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<UserData> fetchData;
    Context context;
    public UserAdapter(List<UserData> fetchData, Context context) {
        this.fetchData = fetchData;
        this.context = context;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_list_item, parent, false);
        UserAdapter.ViewHolder viewHolder = new UserAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        UserAdapter.ViewHolder viewHolder = (UserAdapter.ViewHolder) holder;
        UserData fetchDataList = fetchData.get(position);
        viewHolder.textViewName.setText(fetchDataList.getF_NAME());
        viewHolder.textViewEmail.setText(fetchDataList.getEMAIL());
        viewHolder.textViewAdharCard.setText(fetchDataList.getADHARCARD_NO());
        viewHolder.textViewPhone.setText(fetchDataList.getCITY());     //setText(fetchDataList.getPHONE());
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
        public TextView textViewName;
        public TextView textViewEmail;
        public TextView textViewAdharCard;
        public TextView textViewPhone;
        public LinearLayout constrainUserList;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.textViewEmail = itemView.findViewById(R.id.textViewEmail);
            this.textViewAdharCard = itemView.findViewById(R.id.textViewAdharCard);
            this.textViewPhone = itemView.findViewById(R.id.textViewPhone);
            constrainUserList = itemView.findViewById(R.id.constrainUserList);
        }
    }
}
