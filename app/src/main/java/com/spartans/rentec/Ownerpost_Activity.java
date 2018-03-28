package com.spartans.rentec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Ownerpost_Activity extends AppCompatActivity implements View.OnClickListener {
private boolean isfabopen;
    private FloatingActionButton fab,fabhouse,fabbike,fabshop;
    private boolean success;


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<ownermypostDataStructure> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerpost_);
         fab=(FloatingActionButton) findViewById(R.id.ownerpost_fabadd);
         fabhouse=(FloatingActionButton) findViewById(R.id.ownerpost_fabaddhouse);
         fabbike=(FloatingActionButton) findViewById(R.id.ownerpost_fabaddbike);
         fabshop=(FloatingActionButton) findViewById(R.id.ownerpost_fabaddshop);

        fab.setOnClickListener(this);
        fabhouse.setOnClickListener(this);
        fabbike.setOnClickListener(this);
        fabshop.setOnClickListener(this);
        isfabopen=false;

        data=new ArrayList<ownermypostDataStructure>();



        recyclerView = (RecyclerView) findViewById(R.id.rv_ownermypost);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        data = new ArrayList<ownermypostDataStructure>();
//        for (int p = 0; p < 3; p++) {
//            data.add(new ownermypostDataStructure(
//                    ownermypostData.imagemypost[p],
//                    ownermypostData.firstline[p],
//                    ownermypostData.secondline[p],
//                    ownermypostData.rentviewset[p],
//                    ownermypostData.rentset[p]
//
//            ));
//        }


        datasyncquery();

        CustomerChatFaceAdapter.custoemr_counter=0;
    }


    @Override
    public void onClick(View v) {
        if(v==fab){
            if(isfabopen){
               closeFABmenu();
            }else{
                openFABmenu();
            }
        }if(v==fabhouse){
            finish();
            startActivity(new Intent(this,OwnerAddHouseActivity.class));
        }if(v==fabshop){
            finish();
            startActivity(new Intent(this,OwnerAddShopActivity.class));
        }if(v==fabbike){
            finish();
            startActivity(new Intent(this,OwnerAddBikeActivity.class));
        }
    }
    private void openFABmenu(){
        isfabopen=true;
        fabhouse.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabbike.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fabshop.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABmenu(){
        isfabopen=false;
        fabhouse.animate().translationY(0);
        fabbike.animate().translationY(0);
        fabshop.animate().translationY(0);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,WallActivity.class));
    }
    private void datasyncquery(){
        Connection connect;
        String ConnectionResult="Hello";
        try{
            ConnectionHelper conStr=new ConnectionHelper();
            connect=conStr.connectionclasss();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
                success=false;
            }else {

                String query="SELECT [Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[house].[area],[Rentec].[dbo].[house].[rent_per_day],[Rentec].[dbo].[house].[house_type],[Rentec].[dbo].[house].[house_ID] FROM (([Rentec].[dbo].[house] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[house].[house_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID]) where [Rentec].[dbo].[property].[owner_ID]='"+Userinfo.userid+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs != null) {
//                    Toast.makeText(getApplicationContext(), "rank1" , Toast.LENGTH_SHORT).show();



                    while (rs.next()){
                        try {
                                Bitmap bitmap=StringToBitMap(rs.getString("post_pic"));
                                Drawable d = new BitmapDrawable(getResources(), bitmap);


                                data.add(new ownermypostDataStructure(rs.getString("house_ID"),bitmap,rs.getString("area"),rs.getString("house_type"),"Rent Per Day",rs.getString("rent_per_day")));

                        }catch (Exception ex){

                            Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                        }
                    }
                    success=true;

                }else{
                    success=false;
                }
                String query1="SELECT [Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[shop].[area],[Rentec].[dbo].[shop].[ac_nonac],[Rentec].[dbo].[shop].[rent_per_month],[Rentec].[dbo].[shop].[type],[Rentec].[dbo].[shop].[shop_ID] FROM (([Rentec].[dbo].[shop] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[shop].[shop_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID]) where [Rentec].[dbo].[property].[owner_ID]='"+Userinfo.userid+"'";
                Statement stmt1 = connect.createStatement();
                ResultSet rs1 = stmt1.executeQuery(query1);

                if (rs1 != null) {



                    while (rs1.next()){
                        try {
                            Bitmap bitmap=StringToBitMap(rs1.getString("post_pic"));
                            Drawable d = new BitmapDrawable(getResources(), bitmap);

                            String samp=rs1.getString("type")+"  "+rs1.getString("ac_nonac");
                            data.add(new ownermypostDataStructure(rs1.getString("shop_ID"),bitmap,rs1.getString("area"),samp,"Rent Per Month",rs1.getString("rent_per_month")));

                        }catch (Exception ex){


                        }
                    }
                    success=true;

                }else{
                    success=false;
                }


                String query2="SELECT [Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[vehicle].[model],[Rentec].[dbo].[vehicle].[color],[Rentec].[dbo].[vehicle].[year],[Rentec].[dbo].[vehicle].[seating],[Rentec].[dbo].[vehicle].[vehicle_ID],[Rentec].[dbo].[vehicle].[rent_per_hour] FROM (([Rentec].[dbo].[vehicle] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[vehicle].[vehicle_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID]) where [Rentec].[dbo].[property].[owner_ID]='"+Userinfo.userid+"'";
                Statement stmt2 = connect.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);

                if (rs2 != null) {



                    while (rs2.next()){
                        try {
                            Bitmap bitmap=StringToBitMap(rs2.getString("post_pic"));
                            Drawable d = new BitmapDrawable(getResources(), bitmap);

                            String first=rs2.getString("model")+"  "+rs2.getString("color");
                            String second=rs2.getString("year")+"  "+rs2.getString("seating")+" Seats";
                            data.add(new ownermypostDataStructure(rs2.getString("vehicle_ID"),bitmap,first,second,"Rent Per hour",rs2.getString("rent_per_hour")));

                        }catch (Exception ex){

                            Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                        }
                    }
                    success=true;

                }else{
                    success=false;
                }


            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();

            success=false;
        }


            try{
                if(data!=null){
                    RecycleviewClickListener listener = new RecycleviewClickListener() {
                        @Override
                        public void onClick(View view, int position) {
//                            Toast.makeText(getApplicationContext(), "Position " + data.get(position).getPostid(), Toast.LENGTH_SHORT).show();
//                                data.remove(position);

                        }
                    } ;


                    adapter = new ownermypostadapter(listener,Ownerpost_Activity.this,data);
                    recyclerView.setAdapter(adapter);
                }else{

                }

            }catch (Exception ex){
                ex.printStackTrace();
            }


    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
