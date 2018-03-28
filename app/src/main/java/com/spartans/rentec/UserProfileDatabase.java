package com.spartans.rentec;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class UserProfileDatabase {

    public String firstname;
    public String lastname;
    public String phonenumber;
    public String dob;
    public String nic;
    public String mail;
    public String type;
    public String streetaddress;
    public String optionalstreetaddress;
    public String city;
    public String province;

    public UserProfileDatabase() {
    }

    public UserProfileDatabase(String firstname, String lastname, String phonenumber,String dob, String nic,String mail,String type ,String streetaddress, String optionalstreetaddress, String city, String province) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.mail=mail;
        this.nic=nic;
        this.dob=dob;
        this.type=type;
        this.streetaddress = streetaddress;
        this.optionalstreetaddress = optionalstreetaddress;
        this.city = city;
        this.province = province;
    }
}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menuset, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(isconnected(connectivityManager)) {
//
//
//            int id = item.getItemId();
//            if (id == R.id.action_edituseractivity) {
//                finish();
//                startActivity(new Intent(getApplicationContext(), EditUserProfileActivity.class));
//                return true;
//            }
//            if (id == R.id.action_logout) {
//                auth.signOut();
//
//                finish();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                return true;
//            }
//        }else{
//            Toast.makeText(getApplicationContext(), "There is No Internet connection", Toast.LENGTH_LONG).show();
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }