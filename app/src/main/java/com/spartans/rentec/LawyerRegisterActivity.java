package com.spartans.rentec;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class LawyerRegisterActivity extends AppCompatActivity {
     private String message;
    private EditText lawyerfee;
    private  OwnerRegisterPageAdapter adapter;

    private ViewPager mViewPager;
private OwnerRegisterPageAdapter lawyerRegisterPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_register);

        message = Userinfo.userid;
        lawyerfee=(EditText) findViewById(R.id.lawyerfeeEdittext);



        // Set up the ViewPager with the sections adapter.


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.lawyerregisterfloting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fee=lawyerfee.getText().toString().trim();

                if(TextUtils.isEmpty(fee)){
                    Snackbar.make(view, " Please Enter fee", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    String result= lawyerregisterquery();
                    Snackbar.make(view, result, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }




            }
        });
        Toolbar toolbar=(Toolbar) findViewById(R.id.lawyerregistertoolbar);
        toolbar.setTitle("Lawyer Register");
        lawyerRegisterPageAdapter=new OwnerRegisterPageAdapter(getSupportFragmentManager());
        mViewPager=(ViewPager) findViewById(R.id.container);
        setmViewPager(mViewPager);


    }

    public void setmViewPager(ViewPager mViewPager) {
        adapter=new OwnerRegisterPageAdapter(getSupportFragmentManager());
        adapter.addownerfragment(new lawyerregistertab1(),"TAB1");
        adapter.addownerfragment(new lawyerregistertab2(),"TAB1");

        adapter.addownerfragment(new lawyerregistertab3(),"TAB1");
        mViewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,WallActivity.class);
        intent.putExtra("uid",message);
        finish();
        startActivity(intent);
    }
    public String lawyerregisterquery(){
        String ConnectionResult = "Failed";
        String result = "Nope";
        int userid= Integer.parseInt(message);
        String L="L";
        Boolean isSuccess = false;
        String fee=lawyerfee.getText().toString().trim();

        Connection connect;
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();        // Connect to database
            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
                return ConnectionResult;
            }
            else
            {
                // Change below query according to your own database.
                String query = "INSERT INTO [Rentec].[dbo].[user_role] values('"+userid+"','"+L+"')";
                Statement stmt = connect.createStatement();
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Registered as Lawyer ";
                    Userinfo.islawyer=true;
                }else {
                    ConnectionResult = "Fail to Registered as Lawyer ";

                }

                String query2 = "UPDATE [Rentec].[dbo].[user_details] SET [Rentec].[dbo].[user_details].[lawyer_fee]='"+fee+"' WHERE [Rentec].[dbo].[user_details].[user_ID]='"+Userinfo.userid+"'";
                Statement stmt1 = connect.createStatement();
                boolean finished1=stmt1.execute(query2);
                if (!finished1){
                    ConnectionResult = "Successfully Registered Fee ";

                }else {
                    ConnectionResult = "Fail to Registered Fee ";

                }

                connect.close();
                return ConnectionResult;



            }

        }
        catch (Exception ex)
        {


            ConnectionResult = "Already Registered As Lawyer";
            return ConnectionResult;
        }
//        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

    }
}
