package com.spartans.rentec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener{
   private TextView profilename, roler;
   private EditText firstname, lastname, phonenumber, dob, nic,streetaddresnumber, streetaddress, optionalstreetaddress, city, province, mail;
   private Button editandsave;
    private boolean connected;
    private ConnectivityManager connectivityManager;

    private String username, userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);


        profilename = (TextView) findViewById(R.id.edituser_textprofilename);
        profilename.setText("Hai, "+Userinfo.username);
        userid=Userinfo.userid;
        username=Userinfo.username;
        firstname = (EditText) findViewById(R.id.edituser_editTextfirstname);
        lastname = (EditText) findViewById(R.id.edituser_editTextlastname);
        phonenumber = (EditText) findViewById(R.id.edituser_editTextphonenumber);
        nic = (EditText) findViewById(R.id.edituser_editTextnic);
        streetaddresnumber=(EditText) findViewById(R.id.edituser_editTextstreetaddressnumber);
        streetaddress = (EditText) findViewById(R.id.edituser_editTextstreetaddress);
        city = (EditText) findViewById(R.id.edituser_editTextcity);
        mail=(EditText) findViewById(R.id.edituser_editTextmail);
        editandsave = (Button) findViewById(R.id.edituser_btnEdit);


        editandsave.setOnClickListener(this);


        String ConnectionResult = "";

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
                String query = "SELECT * FROM [Rentec].[dbo].[user_details] where [Rentec].[dbo].[user_details].user_ID='"+Userinfo.userid+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    firstname.setText(rs.getString("f_name").trim());
                    lastname.setText(rs.getString("l_name").trim());
                    phonenumber.setText(rs.getString("TP_no").trim());
                    nic.setText(rs.getString("NIC").trim());
                    streetaddresnumber.setText(rs.getString("add_no").trim());
                    streetaddress.setText(rs.getString("add_street"));
                    city.setText(rs.getString("add_city").trim());
                    mail.setText(rs.getString("mail_ID").trim());




                }else{
                    ConnectionResult="Unable to retrive details";

                }





                connect.close();

            }

        }
        catch (Exception ex)
        {

            ConnectionResult = ex.getMessage();
        }
//        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();




    }

    @Override
    public void onClick(View v) {
        if(isconnected(connectivityManager)){
            if (v==editandsave){
                savedata();
            }


        }else{
            Toast.makeText(getApplicationContext(),"Internet conncetion lost.Please CHeck Your Internet connection.",Toast.LENGTH_SHORT).show();
        }

    }

    private void savedata() {
        String txtfirstname = firstname.getText().toString().trim();
        String txtlastname = lastname.getText().toString().trim();
        String txtphonenumber = phonenumber.getText().toString().trim();
        String txtnic=nic.getText().toString().trim();
        String txtstreetaddressnumber=streetaddresnumber.getText().toString().trim();
        String txtmail=mail.getText().toString().trim();
        String txtstreetaddress = streetaddress.getText().toString().trim();
        String txtcity = city.getText().toString().trim();
        String newtec=txtnic.substring(9).toLowerCase();



        if (TextUtils.isEmpty(txtfirstname)) {
            Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtlastname)) {
            Toast.makeText(getApplicationContext(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtphonenumber)) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtphonenumber.length()!=10){
            Toast.makeText(getApplicationContext(), "Please Check Your Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(txtnic)) {
            Toast.makeText(getApplicationContext(), "Please Enter Your nic", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtnic.length()!=10){
            Toast.makeText(getApplicationContext(), "Please Check Your NIC number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(newtec.equals("v"))){
            Toast.makeText(getApplicationContext(), "Please Check Your NIC number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtmail)) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtstreetaddress)) {
            Toast.makeText(getApplicationContext(), "Please Enter Street Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(txtcity)) {
            Toast.makeText(getApplicationContext(), "Please Enter Your City", Toast.LENGTH_SHORT).show();
            return;
        }

        edituserdetailsregisterquery(txtfirstname,txtlastname,txtnic,txtmail,txtphonenumber,txtstreetaddressnumber,txtstreetaddress,txtcity);

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
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),WallActivity.class));

    }
    public void edituserdetailsregisterquery(String firstname, String lastname, String nic, String mail, String tpno, String addno, String addstreet, String addcity){
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
//                return ;
            }
            else
            {
//                "','"+gender+"','"+null+"','"+nic+"','"+mail+"','"+tpno+"','"+addno+"','"+addstreet+"','"+addcity+"','"+null+
                // Change below query according to your own database.
                String query = "UPDATE [Rentec].[dbo].[user_details] SET [f_name]='"+firstname+"',[l_name]='"+lastname+"',[NIC]='"+nic+"', [mail_ID]='"+mail+"',[TP_no]='"+tpno+"',[add_no]='"+addno+"',[add_street]='"+addstreet+"',[add_city]='"+addcity+"' WHERE [user_ID]='"+userid+"'";
                Statement stmt = connect.createStatement();
//                PreparedStatement pstmt=connect.prepareStatement("INSERT INTO [Rentec].[dbo].[user_details] values('"+userid+"','"+firstname+"','"+lastname+"','"+gender+"','"+null+"','"+nic+"','"+mail+"','"+tpno+"','"+addno+"','"+addstreet+"','"+addcity+"','"+null+"')");
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Update User Details ";
                    finish();
                    startActivity(new Intent(this,WallActivity.class));


                }else {
                    ConnectionResult = "Fail to Update User Details ";

                }
                connect.close();
//                return ;



            }

        }
        catch (Exception ex)
        {


            ConnectionResult = ex.getMessage();
//            return ;
        }
        Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();
//
        return;
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

    }

}
