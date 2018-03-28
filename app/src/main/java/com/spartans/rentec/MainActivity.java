//package com.spartans.rentec;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Path;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//public class MainActivity extends AppCompatActivity {
//    private FirebaseAuth auth;
//    private boolean connected;
//    private ConnectivityManager connectivityManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        auth = FirebaseAuth.getInstance();
//        if(auth.getCurrentUser()== null){
//
//            finish();
//            startActivity(new Intent(getApplicationContext(),SignupActivity.class));
//        }
//        connected = false;
//        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            //we are connected to a network
//            connected = true;
//        }
//        else{
//            connected = false;
//            Toast.makeText(getApplicationContext(),"There is No Internet connection",Toast.LENGTH_LONG).show();
//
//        }
//    }
//
//    private boolean isconnected(ConnectivityManager connectivityManager){
//        connected = false;
//        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            //we are connected to a network
//            connected = true;
//        }
//        else{
//            connected = false;
//
//        }
//        return  connected;
//
//    }
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
//}
