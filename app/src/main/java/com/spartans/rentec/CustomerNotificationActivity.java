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

public class CustomerNotificationActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<CustomerNotifyDataStructure> data;
    private static ArrayList<Integer> removedItems;
    private ConnectionHelper connectionHelper;
    private boolean success;
    private String lawyerid;
    private String productid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_notification);

        recyclerView = (RecyclerView) findViewById(R.id.rv_notifycustomer);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        connectionHelper=new ConnectionHelper();
        data=new ArrayList<CustomerNotifyDataStructure>();
        lawyerid="";
        productid="";
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

                String query="SELECT [Rentec].[dbo].[privilege].[privilege_ID],[Rentec].[dbo].[privilege].[privilege_name],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name] FROM [Rentec].[dbo].[user_details] Inner JOIN [Rentec].[dbo].[privilege]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[privilege].[privilege_name] where [Rentec].[dbo].[privilege].[scope]='"+Userinfo.userid+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs != null) {


                    while (rs.next()){
                        try {
                            String name="Lawyer "+rs.getString("f_name")+" "+rs.getString("l_name")+" Has Accepted Your Request";
                            lawyerid=rs.getString("privilege_name");
                            productid=rs.getString("privilege_ID");

                            data.add(new CustomerNotifyDataStructure(name));

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
                            Toast.makeText(getApplicationContext(), "Position " + data.get(position).getLawyername(), Toast.LENGTH_SHORT).show();
                            customersyncquery();

                        }
                    } ;


                    adapter = new CustomerNotifyAdapter(data,CustomerNotificationActivity.this,listener);
                    recyclerView.setAdapter(adapter);
                    if(adapter.getItemCount()==0){
                        Toast.makeText(getApplicationContext(), "There is active notification", Toast.LENGTH_SHORT).show();

                    }
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
        startActivity(new Intent(this,WallActivity.class));
    }
    private void customersyncquery(){
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


                String query = "DELETE FROM [Rentec].[dbo].[privilege] WHERE [Rentec].[dbo].[privilege].[privilege_ID]='"+productid+"' and [Rentec].[dbo].[privilege].[privilege_name]='"+lawyerid+ "' and [Rentec].[dbo].[privilege].[scope]='"+Userinfo.userid+"'";
                Statement stmt = connect.createStatement();
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Registered  ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(this,CustomerNotificationActivity.class));

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
}
