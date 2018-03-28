package com.spartans.rentec;
//this is for lawyer selection Accept
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SelectLawyerActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<SelectLawyerDataStructure> data;
    private static ArrayList<Integer> removedItems;
    private ConnectionHelper connectionHelper;
    private boolean success;
    private EditText amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lawyer);

        recyclerView = (RecyclerView) findViewById(R.id.rv_selectlawyer);

        recyclerView.setHasFixedSize(true);
        amount=(EditText) findViewById(R.id.customeramountEdittext);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        connectionHelper = new ConnectionHelper();
        data = new ArrayList<SelectLawyerDataStructure>();





        datasyncquery();
    }

    private void datasyncquery() {

        Connection connect;
        String ConnectionResult="Hello";
        try{
            ConnectionHelper conStr=new ConnectionHelper();
            connect=conStr.connectionclasss();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
                success=false;
            }else {

                String query="SELECT [Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[user_details].[lawyer_fee] FROM [Rentec].[dbo].[user_details] where ( [Rentec].[dbo].[user_details].[add_city]=(Select [Rentec].[dbo].[user_details].[add_city] from  [Rentec].[dbo].[user_details] where [Rentec].[dbo].[user_details].[user_ID] ='"+Userinfo.userid+"')) AND ([Rentec].[dbo].[user_details].[lawyer_fee] is not NULL AND not ([Rentec].[dbo].[user_details].[user_ID]='"+Userinfo.userid+"' OR [Rentec].[dbo].[user_details].[user_ID]='"+Userinfo.passid+"' ))";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs != null) {


                    while (rs.next()){
                        try {
                            String name=rs.getString("f_name")+" "+rs.getString("l_name");
                            data.add(new SelectLawyerDataStructure(name,rs.getString("lawyer_fee"),rs.getString("user_ID")));

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

                            String customeramount=amount.getText().toString().trim();
                            if(TextUtils.isEmpty(customeramount)){
                                Toast.makeText(getApplicationContext(), "Please Enter The Amount", Toast.LENGTH_SHORT).show();

                            }else{
                                noticationlawyerquery(data.get(position).getLawyerid(),customeramount);

                            }

//                            finish();
//                            startActivity(new Intent(SelectLawyerActivity.this,WallActivity.class));
                        }
                    } ;


                    adapter = new SelectLawyerAdapter(listener,SelectLawyerActivity.this,data);
                    recyclerView.setAdapter(adapter);
                }else{

                }

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();

                ex.printStackTrace();
            }
        }


    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,ChatActivity.class));
    }
    public void noticationlawyerquery(String lawyerid,String customamount){
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


                String query2 = "UPDATE [Rentec].[dbo].[notification] SET [Rentec].[dbo].[notification].[lawyer_ID]='"+lawyerid+"' WHERE [Rentec].[dbo].[notification].[notification_ID]='"+Userinfo.notificarionid+"' and [Rentec].[dbo].[notification].[customer_ID]='"+Userinfo.userid+"'";
                Statement stmt1 = connect.createStatement();
                boolean finished1=stmt1.execute(query2);
                if (!finished1){
                    ConnectionResult = "Successfully Registered Lawyer ";


                }else {
                    ConnectionResult = "Fail to Registered Lawyer ";

                }



                String query = "INSERT INTO [Rentec].[dbo].[renting_details] values('"+Userinfo.userid+"','"+lawyerid+"','"+Userinfo.notificarionid+"','"+customamount+"') ";
                Statement stmt = connect.createStatement();
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Registered Lawyer ";
                    finish();
                    startActivity(new Intent(this,WallActivity.class));

                }else {
                    ConnectionResult = "Fail to Registered Lawyer ";

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
}
