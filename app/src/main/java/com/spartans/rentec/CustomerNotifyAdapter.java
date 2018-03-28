package com.spartans.rentec;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mathu on 1/24/2018.
 */

public class CustomerNotifyAdapter extends RecyclerView.Adapter<CustomerNotifyAdapter.MyViewHolder> {
    private ArrayList<CustomerNotifyDataStructure> dataSet;
    static  int custoemr_counter = 0;
    private Context context;
    private RecycleviewClickListener mListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textLawyerName;

        private RecycleviewClickListener mListener;

        public MyViewHolder(View itemView,RecycleviewClickListener recycleviewClickListener) {
            super(itemView);
            mListener=recycleviewClickListener;
        itemView.setOnClickListener(this);
            this.textLawyerName = (TextView) itemView.findViewById(R.id.custoemrnotifylawyername);
        }


        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }

    public CustomerNotifyAdapter(ArrayList<CustomerNotifyDataStructure> dataSet, Context context, RecycleviewClickListener mListener) {
        this.dataSet = dataSet;
        this.context = context;
        this.mListener = mListener;
    }

    @Override
    public CustomerNotifyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customernotificationcard, parent, false);

        CustomerNotifyAdapter.MyViewHolder myViewHolder = new CustomerNotifyAdapter.MyViewHolder(view,mListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerNotifyAdapter.MyViewHolder holder, int position5) {
        final CustomerNotifyDataStructure ccds=dataSet.get(position5);
        holder.textLawyerName.setText(ccds.getLawyername());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
