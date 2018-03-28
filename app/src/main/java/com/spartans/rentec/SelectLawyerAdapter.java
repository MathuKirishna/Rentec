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

public class SelectLawyerAdapter extends RecyclerView.Adapter<SelectLawyerAdapter.MyViewHolder> {
    private ArrayList<SelectLawyerDataStructure> dataSet;
    static  int custoemr_counter = 0;
    private Context context;
    private RecycleviewClickListener mListener;


    public SelectLawyerAdapter(RecycleviewClickListener mListener,Context context,ArrayList<SelectLawyerDataStructure> dataSet) {
        this.dataSet = dataSet;
        this.context=context;
        this.mListener = mListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textlawyerName;
        TextView textfee;
        private RecycleviewClickListener mListener;

        public MyViewHolder(View itemView,RecycleviewClickListener recycleviewClickListener) {
            super(itemView);
            mListener=recycleviewClickListener;
            itemView.setOnClickListener(this);
            this.textlawyerName = (TextView) itemView.findViewById(R.id.CustomerChatFaceCustomerName);
            this.textfee = (TextView) itemView.findViewById(R.id.CustomerChatFaceCustomerNumber);
        }


        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
    @Override
    public SelectLawyerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customerchatfacecard, parent, false);

        SelectLawyerAdapter.MyViewHolder myViewHolder = new SelectLawyerAdapter.MyViewHolder(view,mListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(SelectLawyerAdapter.MyViewHolder holder, int position2) {
        final SelectLawyerDataStructure ccds=dataSet.get(position2);
        holder.textlawyerName.setText(ccds.getLawyername());
        holder.textfee.setText(ccds.getFee());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
