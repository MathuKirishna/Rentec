package com.spartans.rentec;

import android.content.Intent;
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

public class LawyerRequestActivity extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<LaywerRequestDataStructure> data;
    private static ArrayList<Integer> removedItems;
    private ConnectionHelper connectionHelper;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_request);

        recyclerView = (RecyclerView) findViewById(R.id.rv_lawyerrequest);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        connectionHelper = new ConnectionHelper();
        data = new ArrayList<LaywerRequestDataStructure>();



//                data = new ArrayList<LaywerRequestDataStructure>();
//                for (int p = 0; p < 3; p++) {
//                    data.add(new LaywerRequestDataStructure(
//                           LawyerRequestData.customername[p],
//                            LawyerRequestData.tpno[p],
//                            LawyerRequestData.notiifcationid[p],
//                            LawyerRequestData.reg_no[p],
//                            LawyerRequestData.ownername[p]
//
//                    ));
//                }


//        RecycleviewClickListener listener = new RecycleviewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "Position " + data.get(position).getNotificationid(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        } ;
//
//
//        adapter = new LawyerRequestAdapter(listener,LawyerRequestActivity.this,data);
//        recyclerView.setAdapter(adapter);

        lawyerrequestsyncquery();


    }
         private void lawyerrequestsyncquery(){
             Connection connect;
             String ConnectionResult="Hello";
             ArrayList<String> firstlinearray=new ArrayList<String>();
             ArrayList<String> secondlinearray=new ArrayList<String>();
             ArrayList<String> thirdlinearray=new ArrayList<String>();
             ArrayList<String> forthlinearray=new ArrayList<String>();
             ArrayList<String> notificationarray=new ArrayList<String>();
             ArrayList<String> customerarray=new ArrayList<String>();
             try{
                 ConnectionHelper conStr=new ConnectionHelper();
                 connect=conStr.connectionclasss();
                 if (connect == null) {
                     ConnectionResult = "Check Your Internet Access!";
                     success=false;
                 }else {

                     String query="SELECT [Rentec].[dbo].[notification].[customer_ID],[Rentec].[dbo].[notification].[notification_ID],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[user_details].[TP_no] FROM [Rentec].[dbo].[user_details] Inner JOIN [Rentec].[dbo].[notification]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[notification].[customer_ID] where [Rentec].[dbo].[notification].[lawyer_ID]='"+Userinfo.userid+"'";
                     Statement stmt = connect.createStatement();
                     ResultSet rs = stmt.executeQuery(query);

                     if (rs != null) {


                         while (rs.next()){
                             try {
                                 String firstline=rs.getString("f_name")+" "+rs.getString("l_name")+"  "+rs.getString("TP_no");

                                    firstlinearray.add(firstline);
                                 String notification=rs.getString("notification_ID");
                                 notificationarray.add(notification);
                                 String customer=rs.getString("customer_ID");
                                 customerarray.add(customer);

//data.add(new LaywerRequestDataStructure(name,rs.getString("TP_no"),rs.getString("notification_ID"),rs.getString("owner_ID")));

                             }catch (Exception ex){
                                 Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                                 ex.printStackTrace();
                             }
                         }
                         success=true;

                     }else{
                         success=false;
                     }

                     String query1="SELECT [Rentec].[dbo].[property].[reg_no] FROM [Rentec].[dbo].[property] Inner JOIN [Rentec].[dbo].[notification]  ON [Rentec].[dbo].[property].[property_ID]=[Rentec].[dbo].[notification].[notification_ID] where [Rentec].[dbo].[notification].[lawyer_ID]='"+Userinfo.userid+"'";
                     Statement stmt1 = connect.createStatement();
                     ResultSet rs1 = stmt1.executeQuery(query1);

                     if (rs1 != null) {


                         while (rs1.next()){
                             try {
                                 String thirdline= "Reg.Number:- "+rs1.getString("reg_no");

                                 thirdlinearray.add(thirdline);
//
//data.add(new LaywerRequestDataStructure(name,rs.getString("TP_no"),rs.getString("notification_ID"),rs.getString("owner_ID")));

                             }catch (Exception ex){
                                 Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                                 ex.printStackTrace();
                             }
                         }
                         success=true;

                     }else{
                         success=false;
                     }

                     String query2="SELECT [Rentec].[dbo].[notification].[owner_ID],[Rentec].[dbo].[notification].[notification_ID],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[user_details].[TP_no] FROM [Rentec].[dbo].[user_details] Inner JOIN [Rentec].[dbo].[notification]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[notification].[owner_ID] where [Rentec].[dbo].[notification].[lawyer_ID]='"+Userinfo.userid+"'";
                     Statement stmt2 = connect.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(query2);



                     if (rs2 != null) {


                         while (rs2.next()){
                             try {
                                 String secondline=rs2.getString("f_name")+" "+rs2.getString("l_name");

                                 secondlinearray.add(secondline);

//
//data.add(new LaywerRequestDataStructure(name,rs.getString("TP_no"),rs.getString("notification_ID"),rs.getString("owner_ID")));

                             }catch (Exception ex){
                                 Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                                 ex.printStackTrace();
                             }
                         }
                         success=true;

                     }else{
                         success=false;
                     }

                     String query3="SELECT [Rentec].[dbo].[renting_details].[amount] FROM [Rentec].[dbo].[renting_details] where [Rentec].[dbo].[renting_details].[lawyer_ID]='"+Userinfo.userid+"'";
                     Statement stmt3 = connect.createStatement();
                     ResultSet rs3 = stmt3.executeQuery(query3);

                     if (rs3 != null) {


                         while (rs3.next()){
                             try {
                                 String forthline="Amount:- "+" "+rs3.getString("amount");

                                 forthlinearray.add(forthline);

//
//data.add(new LaywerRequestDataStructure(name,rs.getString("TP_no"),rs.getString("notification_ID"),rs.getString("owner_ID")));

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
                 Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
             }
             for(int i=0;i<firstlinearray.size();i++){
                 data.add(new LaywerRequestDataStructure(customerarray.get(i),firstlinearray.get(i),secondlinearray.get(i),thirdlinearray.get(i),forthlinearray.get(i),notificationarray.get(i)));

             }

                 try{
                     if(data!=null){
                         RecycleviewClickListener listener = new RecycleviewClickListener() {
                             @Override
                             public void onClick(View view, int position) {
                            Toast.makeText(getApplicationContext(), "Position " + data.get(position).getNotificationid(), Toast.LENGTH_SHORT).show();
                                    notifucustomeraccept(data.get(position).getCustomerid(), data.get(position).getNotificationid());
                             }
                         } ;


                         adapter = new LawyerRequestAdapter(listener,LawyerRequestActivity.this,data);
                         recyclerView.setAdapter(adapter);
                     }else{
                         Toast.makeText(getApplicationContext(), "NULLL DA", Toast.LENGTH_SHORT).show();

                     }

                 }catch (Exception ex){
                     Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();

                     ex.printStackTrace();
                 }











         }

         private void notifucustomeraccept(String customerid,String noitificationid){


             String ConnectionResult = "Failed";
             String result = "Nope";


             Boolean isSuccess = false;


             Connection connect;
             try
             {
                 ConnectionHelper conStr=new ConnectionHelper();
                 connect =conStr.connectionclasss();        // Connect to database
                 if (connect == null)
                 {
                     ConnectionResult = "Check Your Internet Access!";
                     return ;
                 }
                 else
                 {


                     String query = "INSERT INTO [Rentec].[dbo].[privilege] values('"+noitificationid+"','"+Userinfo.userid+"','"+customerid+"') ";
                     Statement stmt = connect.createStatement();
                     boolean finished=stmt.execute(query);
                     if (!finished){
                         ConnectionResult = "Successfully Registered  ";


                     }else {
                         ConnectionResult = "Fail to Registered  ";

                     }

                     String query1 = "DELETE FROM [Rentec].[dbo].[notification] where [Rentec].[dbo].[notification].[notification_ID]='"+noitificationid+"'";
                     Statement stmt1 = connect.createStatement();
                     boolean finished1=stmt1.execute(query1);
                     if (!finished1){
                         ConnectionResult = "Successfully Registered  ";


                     }else {
                         ConnectionResult = "Fail to Registered  ";

                     }
                     String query2 = "DELETE FROM [Rentec].[dbo].[renting_details] where [Rentec].[dbo].[renting_details].[lawyer_ID]='"+Userinfo.userid+"' and [Rentec].[dbo].[renting_details].[customer_ID]='"+customerid+"'";
                     Statement stmt2 = connect.createStatement();
                     boolean finished2=stmt2.execute(query1);
                     if (!finished2){
                         ConnectionResult = "Successfully Registered  ";
                         finish();
                         startActivity(new Intent(this,WallActivity.class));

                     }else {
                         ConnectionResult = "Fail to Registered  ";

                     }
                     connect.close();
                     return ;



                 }

             }
             catch (Exception ex)
             {


                 Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();


                 return ;
             }

         }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,WallActivity.class));
    }
}

