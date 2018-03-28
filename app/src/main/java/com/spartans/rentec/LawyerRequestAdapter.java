package com.spartans.rentec;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mathu on 1/24/2018.
 */

public class LawyerRequestAdapter extends RecyclerView.Adapter<LawyerRequestAdapter.MyViewHolder> {
    private ArrayList<LaywerRequestDataStructure> dataSet;
    static  int custoemr_counter = 0;
    private Context context;
    private RecycleviewClickListener mListener;

    public LawyerRequestAdapter(RecycleviewClickListener mListener,Context context,ArrayList<LaywerRequestDataStructure> dataSet) {
        this.dataSet = dataSet;
        this.context = context;
        this.mListener = mListener;
    }

        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView textline1;
            TextView textline2;
            TextView textline3;
            TextView textline4;
            Button accept;
            private RecycleviewClickListener mListener;

            public MyViewHolder(View itemView,RecycleviewClickListener recycleviewClickListener) {
                super(itemView);
                mListener=recycleviewClickListener;


                this.textline1=(TextView) itemView.findViewById(R.id.lawyerrequestcardline1);
                this.textline2=(TextView) itemView.findViewById(R.id.lawyerrequestcardline2);
                this.textline3=(TextView) itemView.findViewById(R.id.lawyerrequestcardline3);
                this.textline4=(TextView) itemView.findViewById(R.id.lawyerrequestcardline4);
                this.accept=(Button) itemView.findViewById(R.id.buttonlawyeraccept);
                this.accept.setOnClickListener(this);

            }


            @Override
            public void onClick(View v) {
                mListener.onClick(v, getAdapterPosition());
            }
        }


    @Override
    public LawyerRequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lawyerrequestselectcard, parent, false);

        LawyerRequestAdapter.MyViewHolder myViewHolder = new LawyerRequestAdapter.MyViewHolder(view,mListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(LawyerRequestAdapter.MyViewHolder holder, int position3) {


        final LaywerRequestDataStructure ccds=dataSet.get(position3);
        holder.textline1.setText(ccds.getfirstline());
        holder.textline2.setText(ccds.getsecondline());
        holder.textline3.setText(ccds.getthirdline());
        holder.textline4.setText(ccds.getForthline());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
