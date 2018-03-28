package com.spartans.rentec;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class WallActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String message;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<WallPostDataStructure> data;
    private static ArrayList<Integer> removedItems;
    private ConnectionHelper connectionHelper;
    private boolean success;
    private int selectid;
    private String searchid;
    private String ownerid;
    private double longtitude;
    private double lattitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        message = Userinfo.userid;

        recyclerView = (RecyclerView) findViewById(R.id.rv_mainviewpost);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        connectionHelper=new ConnectionHelper();
        data=new ArrayList<WallPostDataStructure>();
        selectid= Userinfo.selection;
        searchid=Userinfo.searchid;

        lattitude=0.0;
        longtitude=0.0;
        getgps();



//



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(WallActivity.this,MapsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        datasyncquery();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.test_search_check) {
            finish();
            startActivity(new Intent(getApplicationContext(),Postsearch_Activity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_wishlist) {
            finish();
            startActivity(new Intent(this,Selectcate_Activity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



        } else if (id == R.id.nav_chat) {
            finish();
            startActivity(new Intent(this,Ownerchatface_Activity.class));


        } else if (id == R.id.nav_lawyernotification) {
            finish();
            startActivity(new Intent(this,CustomerNotificationActivity.class));


        }

        else if (id == R.id.nav_ownerregister) {

            finish();
            startActivity(new Intent(this,OwnerRegisterActivity.class));

        } else if (id == R.id.nav_ownerpost) {
            if(Userinfo.isowner==true){
                finish();
                startActivity(new Intent(this,Ownerpost_Activity.class));
            }else{
                Toast.makeText(getApplicationContext(),"Please register as owner first",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,OwnerRegisterActivity.class));
            }

        }else if (id == R.id.nav_ownerchat) {
            if(Userinfo.isowner==true){
                finish();
                startActivity(new Intent(this,Customerchatface_Activity.class));
            }else{
                Toast.makeText(getApplicationContext(),"Please register as owner first",Toast.LENGTH_SHORT).show();

                finish();
                startActivity(new Intent(this,OwnerRegisterActivity.class));
            }

        }
        else if (id == R.id.nav_lawregister) {

            finish();
            startActivity(new Intent(this,LawyerRegisterActivity.class));

        }else if (id == R.id.nav_lawrequest) {
            if(Userinfo.islawyer==true){
                finish();
                startActivity(new Intent(this,LawyerRequestActivity.class));

            }else{
                Toast.makeText(getApplicationContext(),"Please register as Lawyer first",Toast.LENGTH_SHORT).show();

                finish();
                startActivity(new Intent(this,LawyerRegisterActivity.class));
            }

        }else if (id == R.id.nav_mydetails) {
            finish();
            startActivity(new Intent(this,EditUserProfileActivity.class));

        }else if (id == R.id.nav_logout) {
            Userinfo.userid=null;
            Userinfo.username=null;
            finish();
            startActivity(new Intent(this, SignupActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void datasyncquery(){
        Connection connect;
        String ConnectionResult="Hello";
        try{
            ConnectionHelper conStr=new ConnectionHelper();
            connect=conStr.connectionclasss();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
                success=false;
            }else {
                if(selectid==1){
                    if (TextUtils.isEmpty(searchid)){
                        String query="SELECT [Rentec].[dbo].[location].[district],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[post].[longtitude],[Rentec].[dbo].[post].[lattitude],[Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[house].[area],[Rentec].[dbo].[house].[rent_per_day],[Rentec].[dbo].[house].[house_type],[Rentec].[dbo].[house].[house_ID] FROM (((([Rentec].[dbo].[house] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[house].[house_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[user_details]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[property].[owner_ID])Inner JOIN [Rentec].[dbo].[location]  ON [Rentec].[dbo].[location].[location_ID]=[Rentec].[dbo].[property].[property_ID])where [Rentec].[dbo].[user_details].[user_ID]!='"+Userinfo.userid+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {



                            while (rs.next()){
                                try {
                                    String str1=rs.getString("f_name")+" "+rs.getString("l_name");
                                    String str2=rs.getString("reg_no")+" "+rs.getString("district");
                                    Bitmap bitmap=StringToBitMap(rs.getString("post_pic"));
                                    Drawable d = new BitmapDrawable(getResources(), bitmap);


                                    data.add(new WallPostDataStructure(rs.getDouble("longtitude"),rs.getDouble("lattitude"),rs.getString("user_ID"),str1,str2,rs.getString("area"),rs.getString("house_type"),bitmap,"Rent Per Day",rs.getString("rent_per_day"),rs.getString("house_ID")));

                                }catch (Exception ex){
                                    Toast.makeText(getApplicationContext(), "ethuku" , Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                                }
                            }
                            success=true;

                        }else{
                            success=false;
                            Toast.makeText(getApplicationContext(),"There is no appropriate result for your search",Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        String query="SELECT [Rentec].[dbo].[location].[district],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[post].[longtitude],[Rentec].[dbo].[post].[lattitude],[Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[house].[area],[Rentec].[dbo].[house].[rent_per_day],[Rentec].[dbo].[house].[house_type],[Rentec].[dbo].[house].[house_ID] FROM (((([Rentec].[dbo].[house] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[house].[house_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[user_details]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[property].[owner_ID])Inner JOIN [Rentec].[dbo].[location]  ON [Rentec].[dbo].[location].[location_ID]=[Rentec].[dbo].[property].[property_ID]) WHERE [Rentec].[dbo].[location].[district]='"+searchid+"' and [Rentec].[dbo].[user_details].[user_ID]!='"+Userinfo.userid+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {



                            while (rs.next()){
                                try {
                                    String str1=rs.getString("f_name")+" "+rs.getString("l_name");
                                    String str2=rs.getString("reg_no")+" "+rs.getString("district");
                                    Bitmap bitmap=StringToBitMap(rs.getString("post_pic"));
                                    Drawable d = new BitmapDrawable(getResources(), bitmap);


                                    data.add(new WallPostDataStructure(rs.getDouble("longtitude"),rs.getDouble("lattitude"),rs.getString("user_ID"),str1,str2,rs.getString("area"),rs.getString("house_type"),bitmap,"Rent Per Day",rs.getString("rent_per_day"),rs.getString("house_ID")));

                                }catch (Exception ex){
                                    Toast.makeText(getApplicationContext(), "ethuku" , Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                                }
                            }
                            success=true;

                        }else{
                            success=false;
                            Toast.makeText(getApplicationContext(),"There is no appropriate result for your search",Toast.LENGTH_SHORT).show();

                        }

                    }

                }if(selectid==2){
                    if (TextUtils.isEmpty(searchid)){
                        String query="SELECT [Rentec].[dbo].[location].[district],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[post].[longtitude],[Rentec].[dbo].[post].[lattitude],[Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[shop].[area],[Rentec].[dbo].[shop].[rent_per_month],[Rentec].[dbo].[shop].[type],[Rentec].[dbo].[shop].[shop_ID],[Rentec].[dbo].[shop].[ac_nonac] FROM (((([Rentec].[dbo].[shop] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[shop].[shop_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[user_details]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[property].[owner_ID])Inner JOIN [Rentec].[dbo].[location]  ON [Rentec].[dbo].[location].[location_ID]=[Rentec].[dbo].[property].[property_ID])Where [Rentec].[dbo].[user_details].[user_ID]!='"+Userinfo.userid+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {



                            while (rs.next()){

                                try {
                                    String str1=rs.getString("f_name")+" "+rs.getString("l_name");
                                    String str2=rs.getString("reg_no")+" "+rs.getString("district");
                                    String str3=rs.getString("type")+" "+rs.getString("ac_nonac");
                                    Bitmap bitmap=StringToBitMap(rs.getString("post_pic"));
                                    Drawable d = new BitmapDrawable(getResources(), bitmap);


                                    data.add(new WallPostDataStructure(rs.getDouble("longtitude"),rs.getDouble("lattitude"),rs.getString("user_ID"),str1,str2,rs.getString("area"),str3,bitmap,"Rent Per Month",rs.getString("rent_per_month"),rs.getString("shop_ID")));

                                }catch (Exception ex){
                                    Toast.makeText(getApplicationContext(), "ethuku" , Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                                }
                            }
                            success=true;

                        }else{
                            success=false;
                            Toast.makeText(getApplicationContext(),"There is no appropriate result for your search",Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        String query="SELECT [Rentec].[dbo].[location].[district],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[post].[longtitude],[Rentec].[dbo].[post].[lattitude],[Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[shop].[area],[Rentec].[dbo].[shop].[rent_per_month],[Rentec].[dbo].[shop].[type],[Rentec].[dbo].[shop].[shop_ID],[Rentec].[dbo].[shop].[ac_nonac] FROM (((([Rentec].[dbo].[shop] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[shop].[shop_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[user_details]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[property].[owner_ID])Inner JOIN [Rentec].[dbo].[location]  ON [Rentec].[dbo].[location].[location_ID]=[Rentec].[dbo].[property].[property_ID]) WHERE [Rentec].[dbo].[location].[district]='"+searchid+"' and [Rentec].[dbo].[user_details].[user_ID]!='"+Userinfo.userid+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {



                            while (rs.next()){
                                try {
                                    String str1=rs.getString("f_name")+" "+rs.getString("l_name");
                                    String str2=rs.getString("reg_no")+" "+rs.getString("district");
                                    String str3=rs.getString("type")+" "+rs.getString("ac_nonac");

                                    Bitmap bitmap=StringToBitMap(rs.getString("post_pic"));
                                    Drawable d = new BitmapDrawable(getResources(), bitmap);


                                    data.add(new WallPostDataStructure(rs.getDouble("longtitude"),rs.getDouble("lattitude"),rs.getString("user_ID"),str1,str2,rs.getString("area"),str3,bitmap,"Rent Per Month",rs.getString("rent_per_month"),rs.getString("shop_ID")));

                                }catch (Exception ex){
                                    Toast.makeText(getApplicationContext(), "ethuku" , Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                                }
                            }
                            success=true;

                        }else{
                            success=false;
                            Toast.makeText(getApplicationContext(),"There is no appropriate result for your search",Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                if(selectid==3){
                    if (TextUtils.isEmpty(searchid)){
                        String query="SELECT [Rentec].[dbo].[location].[district],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[post].[longtitude],[Rentec].[dbo].[post].[lattitude],[Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[vehicle].[model],[Rentec].[dbo].[vehicle].[color],[Rentec].[dbo].[vehicle].[year],[Rentec].[dbo].[vehicle].[seating],[Rentec].[dbo].[vehicle].[rent_per_hour],[Rentec].[dbo].[vehicle].[vehicle_ID] FROM (((([Rentec].[dbo].[vehicle] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[vehicle].[vehicle_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[user_details]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[property].[owner_ID])Inner JOIN [Rentec].[dbo].[location]  ON [Rentec].[dbo].[location].[location_ID]=[Rentec].[dbo].[property].[property_ID])where [Rentec].[dbo].[user_details].[user_ID]!='"+Userinfo.userid+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {



                            while (rs.next()){

                                try {
                                    String str1=rs.getString("f_name")+" "+rs.getString("l_name");
                                    String str2=rs.getString("reg_no")+" "+rs.getString("district");
                                    String str3=rs.getString("model")+" "+rs.getString("seating")+" "+"Seats";
                                    String str4=rs.getString("year")+" "+rs.getString("color");

                                    Bitmap bitmap=StringToBitMap(rs.getString("post_pic"));
                                    Drawable d = new BitmapDrawable(getResources(), bitmap);


                                    data.add(new WallPostDataStructure(rs.getDouble("longtitude"),rs.getDouble("lattitude"),rs.getString("user_ID"),str1,str2,str3,str4,bitmap,"Rent Per Hour",rs.getString("rent_per_hour"),rs.getString("vehicle_ID")));

                                }catch (Exception ex){
                                    Toast.makeText(getApplicationContext(), "ethuku" , Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                                }
                            }
                            success=true;

                        }else{
                            success=false;
                            Toast.makeText(getApplicationContext(),"There is no appropriate result for your search",Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        String query="SELECT [Rentec].[dbo].[location].[district],[Rentec].[dbo].[user_details].[user_ID],[Rentec].[dbo].[user_details].[f_name],[Rentec].[dbo].[user_details].[l_name],[Rentec].[dbo].[post].[longtitude],[Rentec].[dbo].[post].[lattitude],[Rentec].[dbo].[post].[post_pic],[Rentec].[dbo].[property].[reg_no],[Rentec].[dbo].[vehicle].[model],[Rentec].[dbo].[vehicle].[color],[Rentec].[dbo].[vehicle].[year],[Rentec].[dbo].[vehicle].[seating],[Rentec].[dbo].[vehicle].[rent_per_hour],[Rentec].[dbo].[vehicle].[vehicle_ID] FROM (((([Rentec].[dbo].[vehicle] Inner JOIN [Rentec].[dbo].[property]  ON [Rentec].[dbo].[vehicle].[vehicle_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[post]  ON [Rentec].[dbo].[post].[post_ID]=[Rentec].[dbo].[property].[property_ID])Inner JOIN [Rentec].[dbo].[user_details]  ON [Rentec].[dbo].[user_details].[user_ID]=[Rentec].[dbo].[property].[owner_ID])Inner JOIN [Rentec].[dbo].[location]  ON [Rentec].[dbo].[location].[location_ID]=[Rentec].[dbo].[property].[property_ID]) WHERE [Rentec].[dbo].[location].[district]='"+searchid+"' and [Rentec].[dbo].[user_details].[user_ID]!='"+Userinfo.userid+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {



                            while (rs.next()){
                                try {
                                    String str1=rs.getString("f_name")+" "+rs.getString("l_name");
                                    String str2=rs.getString("reg_no")+" "+rs.getString("district");
                                    String str3=rs.getString("model")+" "+rs.getString("color");
                                    String str4=rs.getString("year")+" "+rs.getString("seating")+" "+"Seats";

                                    Bitmap bitmap=StringToBitMap(rs.getString("post_pic"));
                                    Drawable d = new BitmapDrawable(getResources(), bitmap);


                                    data.add(new WallPostDataStructure(rs.getDouble("longtitude"),rs.getDouble("lattitude"),rs.getString("user_ID"),str1,str2,str3,str4,bitmap,"Rent Per Hour",rs.getString("rent_per_hour"),rs.getString("vehicle_ID")));

                                }catch (Exception ex){
                                    Toast.makeText(getApplicationContext(), "ethuku" , Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), ex.getMessage() , Toast.LENGTH_SHORT).show();

                                }
                            }
                            success=true;

                        }else{
                            success=false;
                            Toast.makeText(getApplicationContext(),"There is no appropriate result for your search",Toast.LENGTH_SHORT).show();

                        }

                    }

                }


//

            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();

            success=false;
        }


        try{
            if(data!=null){
                RecycleviewClickListener listener = new RecycleviewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "Position " + data.get(position).getPostid(), Toast.LENGTH_SHORT).show();
                        sendnotification(position);

                    }
                } ;

                Userinfo.Postdata=data;
                adapter = new WallPostAdapter(listener,data,WallActivity.this);
                recyclerView.setAdapter(adapter);
                if(adapter.getItemCount()==0){
                    Toast.makeText(getApplicationContext(), "There is no post in "+searchid , Toast.LENGTH_SHORT).show();

                }

            }else{

            }

        }catch (Exception ex){

            ex.printStackTrace();
        }


    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    private void sendnotification(int position){

            String ConnectionResult = "";
            Boolean isSuccess = false;
            Connection connect;



            try {
                ConnectionHelper conStr = new ConnectionHelper();
                connect = conStr.connectionclasss();        // Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    // Change below query according to your own database.
                    String query = "INSERT INTO [Rentec].[dbo].[notification] values('" + data.get(position).getPostid() + "','" + Userinfo.userid + "','" + data.get(position).getOwnerid() + "',NULL)";
                    Statement stmt = connect.createStatement();

                    boolean finished = stmt.execute(query);
                    if (!finished) {
                        ConnectionResult = "Successfully Notified ";



                    } else {
                        ConnectionResult = "Fail to Notify";

                    }


                }
            } catch (Exception ex) {

                ConnectionResult = "Already You are connected. Check chat box";
            }
            Toast.makeText(getApplicationContext(), ConnectionResult, Toast.LENGTH_SHORT).show();


//




    }


    private void getgps(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION }, 0);
        }





        gpsGetter.LocationResult locationResult;
        gpsGetter myGps = new gpsGetter();

        locationResult = new gpsGetter.LocationResult(){
            @Override
            public void gotLocation(Location location){
                Userinfo.longtitude = location.getLongitude();
                Userinfo.lattitude= location.getLatitude();
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
                            WallActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {



                                    final Toast toast;
                                    CharSequence message = "Connection Failed, Retry";
                                    int duration = Toast.LENGTH_LONG;
                                    Context context = getApplicationContext();
                                    toast = Toast.makeText(context,message,duration);
//                                    toast.show();






                                }
                            });
                            this.destroy();
                        }
                        Thread.sleep(1000);
                    }

                    //progress.setMessage("Connected!!");
//                    progress.dismiss();
                    WallActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(),lattitude+ " ",Toast.LENGTH_SHORT).show();
                        }
                    });




                } catch (Exception e) {
                }
            }
        };
        t.start();


    }
    public boolean isLocationDerived(){
        return ((longtitude != 0) && (lattitude != 0));
    }

}
