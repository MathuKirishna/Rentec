package com.spartans.rentec;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OwnerAddHouseActivity extends AppCompatActivity implements View.OnClickListener{
    private Button takePictureButton;
    private ImageView imageView;
    private Button save;
    private Double longtitude,latitude;
    private String imgstr="100";
    private String district;
    private EditText txtarea,txtregno,txtrent;
    private RadioGroup typegroup;
    private RadioButton rbtype;

    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private String area,regno,rpd,type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_house);

        takePictureButton = (Button) findViewById(R.id.owneraddhouseimgbtn);
        imageView = (ImageView) findViewById(R.id.owneraddhouseimg);
        save=(Button) findViewById(R.id.owneraddhousesave);
        txtarea=(EditText) findViewById(R.id.owneraddhousearea);
        txtregno=(EditText) findViewById(R.id.owneraddhouseregno);
        txtrent=(EditText) findViewById(R.id.owneraddhouserpd);
        typegroup=(RadioGroup) findViewById(R.id.owneraddhousetype);
        spinner=(Spinner) findViewById(R.id.spinnerowneraddhouse);
        adapter=ArrayAdapter.createFromResource(this,R.array.array_post,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        save.setOnClickListener(this);
        latitude=0.0;
        longtitude=0.0;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }

    public void takePicturehouse(View view) {

            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            startActivityForResult(intent1, 0);



    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("Rentec", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                imageView.setImageURI(file);
//            }
//        }
        try {
            super.onActivityResult(requestCode,resultCode,data);
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            getgps();
        }catch (Exception e){
        finish();
        startActivity(new Intent(OwnerAddHouseActivity.this,OwnerAddHouseActivity.class));
    }

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,Ownerpost_Activity.class));
    }

    @Override
    public void onClick(View v) {
        if(v==save){

            imageconvert();
        }

    }

    private void getgps(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            save.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 0);
        }





        gpsGetter.LocationResult locationResult;
        gpsGetter myGps = new gpsGetter();

        locationResult = new gpsGetter.LocationResult(){
            @Override
            public void gotLocation(Location location){
                longtitude = location.getLongitude();
                latitude = location.getLatitude();
            }

        };
        myGps.getLocation(this, locationResult);




        //waiting for GPS location
        final Thread t = new Thread() {
            Object lock = new Object();

            int counter = 1;
            @Override
            public void run() {
                try {
                    //check if connected!
                    while (!isLocationDerived()) {
                        //Wait to connect
                        counter++;
                        if(counter > 25){
//                            progress.dismiss();
                            OwnerAddHouseActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {



                                    final Toast toast;
                                    CharSequence message = "Connection Failed, Retry";
                                    int duration = Toast.LENGTH_LONG;
                                    Context context = getApplicationContext();
                                    toast = Toast.makeText(context,message,duration);
                                    toast.show();






                                }
                            });
                            this.destroy();
                        }
                        Thread.sleep(1000);
                    }

                    //progress.setMessage("Connected!!");
//                    progress.dismiss();
                    OwnerAddHouseActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),latitude.toString()+ " "+longtitude.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });




                } catch (Exception e) {
                }
            }
        };
        t.start();


    }
    public boolean isLocationDerived(){
        return ((longtitude != 0) && (latitude != 0));
    }
    private void imageconvert(){
        imageView.buildDrawingCache();
        Bitmap bmap = imageView.getDrawingCache();
        imgstr=BitMapToString(bmap);
        savedata();

    }





    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void savedata(){
        area=txtarea.getText().toString().trim();
        regno=txtregno.getText().toString().trim();
         rpd=txtrent.getText().toString().trim();
        rbtype=(RadioButton) findViewById(typegroup.getCheckedRadioButtonId());
        type=(String) rbtype.getText();
        district=String.valueOf(spinner.getSelectedItem());

        if (TextUtils.isEmpty(area)) {
            Toast.makeText(getApplicationContext(), "Please Enter Area", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(regno)) {
            Toast.makeText(getApplicationContext(), "Please Enter Register Number", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(rpd)) {
            Toast.makeText(getApplicationContext(), "Please Enter Rent Amount", Toast.LENGTH_SHORT).show();
            return;
        }if (regno.length()>9) {
            Toast.makeText(getApplicationContext(), "Register no should not be more than 9 charectors", Toast.LENGTH_SHORT).show();
            return;
        }
        propertyinsertquery();

    }
    private void propertyinsertquery(){
        String ConnectionResult = "Failed";
        String result = "Active";


        Boolean isSuccess = false;
        Connection connect;
        Long currenttime = Calendar.getInstance().getTimeInMillis();
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
                String query = "INSERT INTO [Rentec].[dbo].[property] values('"+ (int) (currenttime.intValue() / 10000) +"','"+Userinfo.userid+"','"+regno+"')";
                Statement stmt = connect.createStatement();
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Register to prop Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();


                }else {
                    ConnectionResult = "Fail to RegisterUser Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

                }


                String query1 = "INSERT INTO [Rentec].[dbo].[post] values('"+ (int) (currenttime.intValue() / 10000) +"','"+result+"',NULL,NULL,'"+imgstr+"','"+longtitude.doubleValue()+"','"+latitude.doubleValue()+"',NULL)";
                Statement stmt1 = connect.createStatement();
                boolean finished1=stmt1.execute(query1);
                if (!finished1){
                    ConnectionResult = "Successfully Register to post Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();


                }else {
                    ConnectionResult = "Fail to Register to post Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

                }
                String query2 = "INSERT INTO [Rentec].[dbo].[location] values('"+ (int) (currenttime.intValue() / 10000) +"','"+regno+"','"+district+"',NULL,'"+district+"','"+district+"')";
                Statement stmt2 = connect.createStatement();
                boolean finished2=stmt2.execute(query2);
                if (!finished2  ){
                    ConnectionResult = "Successfully Register to Location Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();


                }else {
                    ConnectionResult = "Fail to Register to Location Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

                }
                String query3 = "INSERT INTO [Rentec].[dbo].[house] values('"+ (int) (currenttime.intValue() / 10000) +"','"+rpd+"','"+area+"','"+type+"')";
                Statement stmt3 = connect.createStatement();
                boolean finished3=stmt3.execute(query3);
                if (!finished3  ){
                    ConnectionResult = "Successfully Register to House Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(this,Ownerpost_Activity.class));

                }else {
                    ConnectionResult = "Fail to Register to House Details ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

                }

                connect.close();
//                return ;



            }

        }
        catch (Exception ex)
        {


            ConnectionResult=ex.getMessage();
//            return ;
        }
//        Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();
//
        return;
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


}
