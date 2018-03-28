package com.spartans.rentec;


import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ownermypostadapter extends RecyclerView.Adapter<ownermypostadapter.MyViewHolder>{


    private ArrayList<ownermypostDataStructure> dataset;
    static  int post_counter = 0;
    private Context mcontext;
    private RecycleviewClickListener mListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView firstline;
        TextView secondline;
        TextView rentview;
        TextView rent;
        ImageView img;
        Button del;

        private RecycleviewClickListener mListener;
        public MyViewHolder(View itemView,RecycleviewClickListener recycleviewClickListener) {
            super(itemView);
            mListener=recycleviewClickListener;

            this.firstline=(TextView) itemView.findViewById(R.id.txtOwnermypostarea);
            this.secondline=(TextView) itemView.findViewById(R.id.txtownermyposttype);
            this.rentview=(TextView) itemView.findViewById(R.id.txtownermypostrentview);
            this.rent=(TextView) itemView.findViewById(R.id.txtownermypostrent);
            this.img=(ImageView) itemView.findViewById(R.id.imgownermypost);
            this.del=(Button) itemView.findViewById(R.id.buttonownerpostdel);

            del.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {mListener.onClick(v, getAdapterPosition());

        }
    }

    public ownermypostadapter(RecycleviewClickListener recycleviewClickListener,Context context,ArrayList<ownermypostDataStructure> dataset) {
        this.dataset = dataset;
        this.mcontext=context;
        this.mListener=recycleviewClickListener;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ownermypostcard, parent, false);

        ownermypostadapter.MyViewHolder myViewHolder=new ownermypostadapter.MyViewHolder(view,mListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TextView txtfirstline=holder.firstline;
        TextView txtfirstsecondline=holder.secondline;
        TextView txtfirstrentview=holder.rentview;
        TextView txtfirstrent=holder.rent;
        ImageView img=holder.img;
        Button delete=holder.del;
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataset.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position,dataset.size());
//
//
//            }
//        });



        txtfirstline.setText(dataset.get(position).getFirstline());
        txtfirstsecondline.setText(dataset.get(position).getSecondline());
        txtfirstrentview.setText(dataset.get(position).getRentview());
        txtfirstrent.setText(dataset.get(position).getRent());
        img.setImageBitmap(dataset.get(position).getImgmypost());

    }



    @Override
    public int getItemCount() {

        return dataset.size();
    }


}
