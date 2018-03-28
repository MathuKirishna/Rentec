package com.spartans.rentec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class    LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText inputUsername, inputPassword;

    private ProgressBar progressBar;
    private Button btnSignup, btnLogin;
    private boolean connected;
    private ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        inputUsername=(EditText) findViewById(R.id.username);
        inputPassword=(EditText) findViewById(R.id.password);
        btnSignup=(Button) findViewById(R.id.btn_sign_up);
        btnLogin=(Button) findViewById(R.id.btn_login);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        //set Listener for buttons
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        connected = false;
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;
            Toast.makeText(getApplicationContext(),"There is No Internet connection",Toast.LENGTH_LONG).show();

        }







    }
    private boolean isconnected(ConnectivityManager connectivityManager){
        connected = false;
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;

        }
        return  connected;

    }
    private boolean isValidPassword(String password){
        if (password.length()>3){
            return (false);
        }
        return true;
    }


    private void userlogin() {

        String username=inputUsername.getText().toString().trim();
        String password=inputPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) ){
            //email empty
            Toast.makeText(this,"Please enter your user name",Toast.LENGTH_SHORT).show();
            return;

        }


        if (isValidPassword(password)){
            //password empty
            Toast.makeText(this,"Password is too short, please enter more than 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginquery(username,password);
    }
    public void loginquery(String username,String password){
        String ConnectionResult = "";
        String result = "Nope";
        String userid;
        Boolean isSuccess = false;
        Connection connect;
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();        // Connect to database
            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                String query = "SELECT * FROM [Rentec].[dbo].[user] where [Rentec].[dbo].[user].username='"+username+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                   result=rs.getString("password").trim();
                    userid=rs.getString("user_ID");
                    if (result.compareTo(password)==0){
                        ConnectionResult="Login successful";
                        Userinfo.userid=userid;
                        Userinfo.username=username;
                        checkuserrole(userid);
//                        Intent intent=new Intent(this,WallActivity.class);
//                        intent.putExtra("uid",userid);
                        finish();
                        startActivity(new Intent(this,Selectcate_Activity.class));

                    }else {
                        ConnectionResult="Password Mismatch";
                        Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

                        inputPassword.setText("");
                        inputUsername.setFocusable(true);
                    }


                }else{

                    ConnectionResult="Login Error";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

                    inputPassword.setText("");
                    inputUsername.setFocusable(true);
                }





                connect.close();

            }
            progressBar.setVisibility(View.INVISIBLE);
        }
        catch (Exception ex)
        {

            ConnectionResult = ex.getMessage();
        }
//        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

    }

    public void checkuserrole(String userid){
        String ConnectionResult = "";
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
            }
            else
            {
                // Change below query according to your own database.
                String query = "SELECT * FROM [Rentec].[dbo].[user_role] where [Rentec].[dbo].[user_role].userID='"+userid+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){

                    result=rs.getString("roleID").trim();

                    if (result.compareTo("O")==0){
                        Userinfo.isowner=true;

                    }if (result.compareTo("L")==0){
                        Userinfo.islawyer=true;

                    }




                }





                connect.close();

            }

        }
        catch (Exception ex)
        {

            ConnectionResult = ex.getMessage();
        }
//        Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {
        if(isconnected(connectivityManager)){
            if (v==btnLogin){

                userlogin();

            }if (v==btnSignup){

                finish();
                startActivity(new Intent(this,SignupActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }}
        else {
            Toast.makeText(getApplicationContext(), "There is No Internet connection", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),SignupActivity.class));






    }

    }

