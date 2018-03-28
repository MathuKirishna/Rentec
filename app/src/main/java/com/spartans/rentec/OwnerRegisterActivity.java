package com.spartans.rentec;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OwnerRegisterActivity extends AppCompatActivity {
    private OwnerRegisterPageAdapter fragownerRegisterPageAdapter;

    private ViewPager viewPager;
    String message;
    private boolean registered=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        message = Userinfo.userid;


        fragownerRegisterPageAdapter=new OwnerRegisterPageAdapter(getSupportFragmentManager());
        viewPager=(ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager );


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.ownerregisterfloting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    String result= ownerregisterquery();
                Snackbar.make(view, result, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();






            }
        });

        Toolbar toolbar=(Toolbar) findViewById(R.id.ownerregistertoolbar);
        toolbar.setTitle("Owner Register");

    }
    private void setupViewPager(ViewPager viewPager){

        OwnerRegisterPageAdapter ownerRegisterPageAdapter=new OwnerRegisterPageAdapter(getSupportFragmentManager());
        ownerRegisterPageAdapter.addownerfragment(new ownerregistertab1(),"Step 1");
        ownerRegisterPageAdapter.addownerfragment(new ownerregistertab2(),"Step 2");
        ownerRegisterPageAdapter.addownerfragment(new ownerregistertab3(),"Step 3");
        ownerRegisterPageAdapter.addownerfragment(new ownerregistertab4(),"Step 4");
        viewPager.setAdapter(ownerRegisterPageAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,WallActivity.class);
        intent.putExtra("uid",message);
        finish();
        startActivity(intent);
    }
    public String ownerregisterquery(){
        String ConnectionResult = "Failed";
        String result = "Nope";
        int userid= Integer.parseInt(message);
        String O="O";
        Boolean isSuccess = false;
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
                String query = "INSERT INTO [Rentec].[dbo].[user_role] values('"+userid+"','"+O+"')";
                Statement stmt = connect.createStatement();
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Registered as Owner ";
                    Userinfo.isowner=true;




            }else {
                    ConnectionResult = "Fail to Registered as Owner ";

                }
                connect.close();
                return ConnectionResult;



            }

        }
        catch (Exception ex)
        {


            ConnectionResult = "Already Registered As Owner";
            return ConnectionResult;
        }
//        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

    }


}
