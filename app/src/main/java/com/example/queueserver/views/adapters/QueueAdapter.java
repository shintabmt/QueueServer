package com.example.queueserver.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.queueserver.R;
import com.example.queueserver.models.Customer;
import com.example.queueserver.models.QueueInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shintabmt@gmai.com on 7/4/2016.
 */
public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {
    private List<QueueInfo> queueInfoList = new ArrayList<>();

    public void setItems(List<QueueInfo> queueInfoList) {
        this.queueInfoList = queueInfoList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.clientName)
        TextView name;
        @BindView(R.id.clientIp)
        TextView ip;
        @BindView(R.id.queueType)
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QueueInfo item = queueInfoList.get(position);
        Customer customer = item.getCustomer();
        holder.name.setText(customer.getName());
        holder.ip.setText(item.getQueuePosition() + "");
        holder.type.setText(customer.getQueueType() + "");
    }

    @Override
    public int getItemCount() {
        return queueInfoList.size();
    }
}
