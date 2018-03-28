package com.spartans.rentec;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WallPostAdapter extends RecyclerView.Adapter<WallPostAdapter.MyViewHolder>{

    private ArrayList<WallPostDataStructure> dataset;
    static  int post_counter = 0;
    private Context mcontext;
    private RecycleviewClickListener mListener;


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtfirstline,txtsecondline,txtthirdline,txtforthline,txtrentview,txtrent;
        Button btninterest;
        ImageView wallimg;

        private RecycleviewClickListener mListener;

        public MyViewHolder(View itemView,RecycleviewClickListener recycleviewClickListener) {
            super(itemView);
            mListener=recycleviewClickListener;


            this.txtfirstline=(TextView) itemView.findViewById(R.id.txtstartpostline1);
            this.txtsecondline=(TextView) itemView.findViewById(R.id.txtstartpostline2);
            this.txtthirdline=(TextView) itemView.findViewById(R.id.txtstartpostline3);
            this.txtrent=(TextView) itemView.findViewById(R.id.txtstartpostrent);
            this.txtrentview=(TextView) itemView.findViewById(R.id.txtstartpostrentview);
            this.btninterest=(Button) itemView.findViewById(R.id.buttonownerpostinterest);
            this.wallimg=(ImageView) itemView.findViewById(R.id.imgstartpost);
            this.txtforthline=(TextView) itemView.findViewById(R.id.txtstartpostline4);

            btninterest.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }

    public WallPostAdapter(RecycleviewClickListener mListener,ArrayList<WallPostDataStructure> dataset, Context mcontext) {
        this.dataset = dataset;
        this.mcontext = mcontext;
        this.mListener = mListener;
    }

    @Override
    public WallPostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.startpostcard, parent, false);

        WallPostAdapter.MyViewHolder myViewHolder = new WallPostAdapter.MyViewHolder(view,mListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(WallPostAdapter.MyViewHolder holder, final int position2) {
        final WallPostDataStructure ccds=dataset.get(position2);
        holder.txtfirstline.setText(ccds.getLine1());
        holder.txtsecondline.setText(ccds.getLine2());
        holder.txtthirdline.setText(ccds.getLine3());
        holder.txtforthline.setText(ccds.getLine4());
        holder.txtrentview.setText(ccds.getRentview());
        holder.txtrent.setText(ccds.getRent());
        holder.wallimg.setImageBitmap(ccds.getWallimg());


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
