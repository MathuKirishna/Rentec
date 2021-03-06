package com.spartans.rentec;
//this is owner

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Customerchatface_Activity extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<customerChatFaceDataStructure> data;
    private static ArrayList<Integer> removedItems;
    private ConnectionHelper connectionHelper;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerchatface_);


            recyclerView = (RecyclerView) findViewById(R.id.rv_customerchatface);

            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            connectionHelper=new ConnectionHelper();
            data=new ArrayList<customerChatFaceDataStructure>();



    //
    //        data = new ArrayList<customerChatFaceDataStructure>();
    //        for (int p = 0; p < 3; p++) {
    //            data.add(new customerChatFaceDataStructure(
    //                   customerChatFaceData.customername[p],
    //                    customerChatFaceData.getCustomernumber[p]
    //            ));
    //        }

        removedItems = new ArrayList<Integer>();




        datasyncquery();


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

                String query="SELECT [Rentec].[dbo].[notification].[notification_ID],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[user_details].[TP_no] FROM [Rentec].[dbo].[user_details] Inner JOIN [Rentec].[dbo].[notification]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[notification].[customer_ID] where [Rentec].[dbo].[notification].[owner_ID]='"+Userinfo.userid+"' AND [Rentec].[dbo].[notification].[lawyer_ID] is NULL";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs != null) {


                    while (rs.next()){
                        try {
                            String name=rs.getString("f_name")+" "+rs.getString("l_name");
                            data.add(new customerChatFaceDataStructure(rs.getString("user_ID"),rs.getInt("notification_ID"),name,rs.getString("TP_no")));

                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                    }
                    success=true;

                }else{
                    success=false;
                }


            }

        }catch (Exception e){
            success=false;
        }
        if(success==true){

            try{
                if(data!=null){
                    RecycleviewClickListener listener = new RecycleviewClickListener() {
                        @Override
                        public void onClick(View view, int position) {
//                            Toast.makeText(getApplicationContext(), "Position " + data.get(position).getNotificationnumber(), Toast.LENGTH_SHORT).show();

                            Userinfo.checklast=20;
                            Userinfo.notificarionid=data.get(position).getNotificationnumber();
                            Userinfo.passid=data.get(position).getPassid();
                            finish();
                            startActivity(new Intent(Customerchatface_Activity.this,ChatActivity.class));
                        }
                    } ;


                        adapter = new CustomerChatFaceAdapter(listener,Customerchatface_Activity.this,data);
                        recyclerView.setAdapter(adapter);
                    if(adapter.getItemCount()==0){
                        Toast.makeText(getApplicationContext(), "There is no active chat" , Toast.LENGTH_SHORT).show();

                    }
                }else{

                }

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();

                ex.printStackTrace();
            }
        }

    }

}
