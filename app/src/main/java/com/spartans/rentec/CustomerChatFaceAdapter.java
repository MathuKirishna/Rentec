package com.spartans.rentec;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class CustomerChatFaceAdapter extends RecyclerView.Adapter<CustomerChatFaceAdapter.MyViewHolder> {
    private ArrayList<customerChatFaceDataStructure> dataSet;
    static  int custoemr_counter = 0;
    private Context context;
    private RecycleviewClickListener mListener;



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textCustomerName;
        TextView textCustomerNumber;
        private RecycleviewClickListener mListener;

        public MyViewHolder(View itemView,RecycleviewClickListener recycleviewClickListener) {
            super(itemView);
            mListener=recycleviewClickListener;
            itemView.setOnClickListener(this);
            this.textCustomerName = (TextView) itemView.findViewById(R.id.CustomerChatFaceCustomerName);
            this.textCustomerNumber = (TextView) itemView.findViewById(R.id.CustomerChatFaceCustomerNumber);
        }


        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }

    public CustomerChatFaceAdapter(RecycleviewClickListener mListener,Context context,ArrayList<customerChatFaceDataStructure> dataSet) {
        this.dataSet = dataSet;
        this.context=context;
        this.mListener = mListener;
    }

    @Override
    public CustomerChatFaceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customerchatfacecard, parent, false);

       CustomerChatFaceAdapter.MyViewHolder myViewHolder = new CustomerChatFaceAdapter.MyViewHolder(view,mListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerChatFaceAdapter.MyViewHolder holder, final int position1) {


        final customerChatFaceDataStructure ccds=dataSet.get(position1);
        holder.textCustomerName.setText(ccds.getCustomername());
        holder.textCustomerNumber.setText(ccds.getCustomernumber());
//        TextView textCustomerName=holder.textCustomerName;
//        TextView textCustomerNumbe=holder.textCustomerNumber;
//
//        textCustomerName.setText(dataSet.get(position1).getCustomername());
//        textCustomerNumbe.setText(dataSet.get(position1).getCustomernumber());
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
