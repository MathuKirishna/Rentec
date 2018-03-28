package com.spartans.rentec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputUsername, inputPassword, inputConfirmpassword;
    private Button btnSignin, btnSignup;
    private ProgressBar progressBar;

    private boolean connected;
    private ConnectivityManager connectivityManager;
    private RadioButton customer, lawyer, owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);


//     // firebase auth instance

        //btn,text definition
        btnSignin = (Button) findViewById(R.id.sign_in_button);
        btnSignup = (Button) findViewById(R.id.sign_up_button);
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConfirmpassword = (EditText) findViewById(R.id.confirmpassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);


        connected = false;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
            Toast.makeText(getApplicationContext(), "There is No Internet connection", Toast.LENGTH_LONG).show();

        }

    }

    private boolean isconnected(ConnectivityManager connectivityManager) {
        connected = false;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;

        }
        return connected;

    }

    private boolean isValidPassword(String password) {
        if (password.length() > 6) {
            return (false);
        }
        return true;
    }

    private void registeruser() {
        String username = inputUsername.getText().toString().trim();
        String confirmpassword = inputConfirmpassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please Enter User Name", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = inputPassword.getText().toString().trim();
        if (isValidPassword(password)) {
            //password empty
            Toast.makeText(this, "Password is too short, Try More than 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(getApplicationContext(), "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.compareTo(confirmpassword) != 0) {
            //password empty
            Toast.makeText(this, "Password mismatch.Please Confirm Your Password Correctly", Toast.LENGTH_SHORT).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        signupquery(username, password);


    }

    public void signupquery(String username, String password) {
        String ConnectionResult = "";
        Boolean isSuccess = false;
        Connection connect;
        Long currenttime = Calendar.getInstance().getTimeInMillis();


        try {
            ConnectionHelper conStr = new ConnectionHelper();
            connect = conStr.connectionclasss();        // Connect to database
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
                return;
            } else {
                // Change below query according to your own database.
                String query1 = "SELECT [Rentec].[dbo].[user].[user_ID] FROM[Rentec].[dbo].[user] WHERE [Rentec].[dbo].[user].[username]='"+username+"'";
                Statement stmt1 = connect.createStatement();

                ResultSet rs = stmt1.executeQuery(query1);
                if (!rs.next()) {
                    String query = "INSERT INTO [Rentec].[dbo].[user] values('" + (int) (currenttime.intValue() / 10000) + "','" + username + "','" + password + "')";
                    Statement stmt = connect.createStatement();

                    boolean finished = stmt.execute(query);
                    if (!finished) {
                        ConnectionResult = "Successfully Registered ";
                        getuserid(username, password);


                    } else {
                        ConnectionResult = "Fail to Register";

                    }



                } else {
                    ConnectionResult = "Username Already Used. Please Try Another";
                    Toast.makeText(getApplicationContext(), ConnectionResult , Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;

                }



            }
        } catch (Exception ex) {

            ConnectionResult = ex.getMessage();
        }
        Toast.makeText(getApplicationContext(), ConnectionResult, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);

//

    }

    @Override
    public void onClick(View v) {
        if (isconnected(connectivityManager)) {
            if (v == btnSignup) {

                registeruser();


            }
            if (v == btnSignin) {
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



            }
        } else {
            Toast.makeText(getApplicationContext(), "There is No Internet connection", Toast.LENGTH_LONG).show();

        }
    }

    public void getuserid(String username, String password) {
        String ConnectionResult = "";
        String result = "Nope";
        String userid;
        Boolean isSuccess = false;
        Connection connect;
        try {
            ConnectionHelper conStr = new ConnectionHelper();
            connect = conStr.connectionclasss();        // Connect to database
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                // Change below query according to your own database.
                String query = "SELECT * FROM [Rentec].[dbo].[user] where [Rentec].[dbo].[user].username='" + username + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    result = rs.getString("password").trim();
                    userid = rs.getString("user_ID");
                    if (result.compareTo(password) == 0) {

                        Userinfo.userid = userid;
                        Userinfo.username=username;
//
                        userregisterquery(userid);

                    } else {
                        ConnectionResult = "Password Mismatch";

                    }


                } else {
                    ConnectionResult = "Login Error";

                }


                connect.close();

            }

        } catch (Exception ex) {

            ConnectionResult = ex.getMessage();
        }


    }

    public void userregisterquery(String userid) {
        String ConnectionResult = "Failed";
        String result = "Nope";
        String userid2 = userid;
        String C = "C";
        Boolean isSuccess = false;
        Connection connect;
        try {
            ConnectionHelper conStr = new ConnectionHelper();
            connect = conStr.connectionclasss();        // Connect to database
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
                return;
            } else {
                // Change below query according to your own database.
                String query = "INSERT INTO [Rentec].[dbo].[user_role] values('" + userid + "','" + C + "')";
                Statement stmt = connect.createStatement();
                boolean finished = stmt.execute(query);
                if (!finished) {
                    ConnectionResult = "Successfully Registered as User ";
                    finish();
                    startActivity(new Intent(this,UserProfileActivity.class));



                } else {
                    ConnectionResult = "Fail to Registered as Owner ";

                }
                connect.close();
                return;


            }

        } catch (Exception ex) {


            ConnectionResult = "Already Registered As Owner";
            return;
        }
    }
}


