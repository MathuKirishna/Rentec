package com.spartans.rentec;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView profilename, roler;
    private EditText firstname, lastname, phonenumber, dob, nic,streetaadressno, streetaddress, optionalstreetaddress, city, province, mail;
    private Button save;
    private boolean connected;
    private ConnectivityManager connectivityManager;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
     private String username,userid;
    private RadioGroup gendergroup;
    private RadioButton gender;

    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
//        Intent intent = getIntent();
//        String role = intent.getStringExtra("Rolling");
//        Toast.makeText(getApplicationContext(), role, Toast.LENGTH_SHORT).show();



        profilename = (TextView) findViewById(R.id.textprofilename);
        userid=Userinfo.userid;
        username=Userinfo.username;
        profilename.setText("Hai, "+username);
        //roler = (TextView) findViewById(R.id.textviewprofileroll);
        firstname = (EditText) findViewById(R.id.editTextfirstname);
        lastname = (EditText) findViewById(R.id.editTextlastname);
        phonenumber = (EditText) findViewById(R.id.editTextphonenumber);
        dob = (EditText) findViewById(R.id.textdob);
        nic = (EditText) findViewById(R.id.editTextnic);
        streetaadressno=(EditText)findViewById(R.id.editTextstreetaddressnumber);
        streetaddress = (EditText) findViewById(R.id.editTextstreetaddress);
        optionalstreetaddress = (EditText) findViewById(R.id.editTextstreetaddressoptional);
        spinner=(Spinner) findViewById(R.id.spinneruserdetail);
        adapter= ArrayAdapter.createFromResource(this,R.array.array_post,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        province = (EditText) findViewById(R.id.editTextprovince);
        gendergroup=(RadioGroup) findViewById(R.id.userprofile_radioSex);

        mail=(EditText) findViewById(R.id.editTextmail);
        save = (Button) findViewById(R.id.btnSave);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        save.setOnClickListener(this);

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
    public void onClick(View v) {
        if(isconnected(connectivityManager)){
            if (v==save){
                savedata();
            }


        }else{
            Toast.makeText(getApplicationContext(),"Internet conncetion lost.Please CHeck Your Internet connection.",Toast.LENGTH_SHORT).show();
        }

    }
    private boolean datecheck(String date){
        if (date.length()!=10){
            return false;
        }if ((date.substring(3,4))!="0"|(date.substring(3,4))!="1"){
            return false;
        }
        return true;

    }

    private boolean testdate(String date){
        String[] datelist=date.split("/");

        if (datelist.length!=3){
            return false;
        }
        int day= Integer.parseInt(datelist[0]);
        int month=Integer.parseInt(datelist[1]);
        int year=Integer.parseInt(datelist[2]);

        if(day<0){
            return false;
        }
        if((month<0) || (month>12 )){
            return false;
        }
        if (year==2018){
            if(month>2){
                return false;
            }else {
                if(day>2){
                    return false;
                }
            }
        }
        if (year>2018 || year<1948){
            return false;
        }
        if(month==2 && day>28){
            return false;
        }
        if(month==1 || month==3 ||month==5 ||month==7 ||month==8 ||month==10 ||month==12 ){
            if(day<32){
                return true;
            }else {
                return false;
            }
        }else {
            if(day<31){
                return true;
            }else {
                return false;
            }
        }


        }

    public  boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }



    private void savedata() {
        String txtfirstname = firstname.getText().toString().trim();
        String txtlastname = lastname.getText().toString().trim();
        String txtphonenumber = phonenumber.getText().toString().trim();
//        Date date= (Date) dob.;
        String date=dob.getText().toString().trim();
        java.util.Date dateObject=null;
        java.sql.Date dateObj=null;

        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateObject =  curFormater.parse(date);
           dateObj = new java.sql.Date(dateObject.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Please Enter date in dd/MM/yyyy format",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!testdate(date)){
            Toast.makeText(getApplicationContext(),"Please check whether  date in dd/MM/yyyy format",Toast.LENGTH_SHORT).show();

            return;
        }


        String txtnic=nic.getText().toString().trim();
        String txtmail=mail.getText().toString().trim();
        String txtstreetaddressnumber=streetaadressno.getText().toString().trim();
        String txtstreetaddress = streetaddress.getText().toString().trim();
        String txtoptionalstreetaddress = optionalstreetaddress.getText().toString().trim();


        String txtcity = String.valueOf(spinner.getSelectedItem());
        int selectgender= gendergroup.getCheckedRadioButtonId();
        gender=(RadioButton)findViewById(selectgender);
        String txtgender= (String) gender.getText();
//        String txtprovince = province.getText().toString().trim();


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
        if (TextUtils.isEmpty((CharSequence) date)) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Date Of birth", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtnic)) {
            Toast.makeText(getApplicationContext(), "Please Enter Your nic", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtnic.length()!=10){
            Toast.makeText(getApplicationContext(), "Please Check Your NIC", Toast.LENGTH_SHORT).show();
            return;
        }
        String newtec=txtnic.substring(9).toLowerCase();
        if(!(newtec.equals("v"))){
            Toast.makeText(getApplicationContext(), "Please Check Your NIC number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isValidEmail(txtmail)){
            Toast.makeText(getApplicationContext(), "Please Enter Valid Mail Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtmail)) {
            Toast.makeText(getApplicationContext(), "Please Enter Your Mail", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(txtstreetaddressnumber)) {
            Toast.makeText(getApplicationContext(), "Please Enter Street Address Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(txtstreetaddress)) {
            Toast.makeText(getApplicationContext(), "Please Enter Street Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtoptionalstreetaddress)) {
            txtoptionalstreetaddress = "";

        }

//        if (TextUtils.isEmpty(txtprovince)) {
//            Toast.makeText(getApplicationContext(), "Please Enter Your Province", Toast.LENGTH_SHORT).show();
//            return;
//        }
        userdetailsregisterquery(txtfirstname,txtlastname,txtgender,dateObj,txtnic,txtmail,txtphonenumber,txtstreetaddressnumber,txtstreetaddress +" "+ txtoptionalstreetaddress,txtcity);


    }
    public void userdetailsregisterquery(String firstname, String lastname, String gender, Date dob, String nic, String mail, String tpno, String addno, String addstreet, String addcity){
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
               String query = "INSERT INTO [Rentec].[dbo].[user_details] values('"+userid+"','"+firstname+"','"+lastname+"','"+gender+"','"+dob+"','"+nic+"','"+mail+"','"+tpno+"','"+addno+"','"+addstreet+"','"+addcity+"',NULL,NULL)";
               Statement stmt = connect.createStatement();
//                PreparedStatement pstmt=connect.prepareStatement("INSERT INTO [Rentec].[dbo].[user_details] values('"+userid+"','"+firstname+"','"+lastname+"','"+gender+"','"+null+"','"+nic+"','"+mail+"','"+tpno+"','"+addno+"','"+addstreet+"','"+addcity+"','"+null+"')");
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Register User Details ";
                    Userinfo.islawyer=false;
                    Userinfo.isowner=false;
                    finish();
                    startActivity(new Intent(this,Selectcate_Activity.class));


                }else {
                    ConnectionResult = "Fail to RegisterUser Details ";

                }
                connect.close();
//                return ;



            }

        }
        catch (Exception ex)
        {


            ConnectionResult = "Already Registered the details";
//            return ;
        }
        Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();
//
        return;
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {

    }
}
