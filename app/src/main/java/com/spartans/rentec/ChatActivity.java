package com.spartans.rentec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.Statement;

public class ChatActivity extends AppCompatActivity {
    //private static int Sign_IN_Request_code=1;
    private FirebaseAuth auth;
    private boolean connected;
    private ConnectivityManager connectivityManager;
    private EditText txtmsg;
    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.chattoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Rentec");


//        Toast.makeText(getApplicationContext(), ( Userinfo.notificarionid.toString()), Toast.LENGTH_SHORT).show();

        txtmsg=(EditText) findViewById(R.id.chat_typetxt);

        auth = FirebaseAuth.getInstance();
        signin();
        FirebaseUser user=auth.getCurrentUser();
        if (auth.getCurrentUser() == null) {
            auth.signInWithEmailAndPassword("Rentec@gmail.com","Rentec").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                   }else{
                    }
                }
            });
        }
//        Toast.makeText(getApplication(),user.getEmail(),Toast.LENGTH_SHORT).show();





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



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.chat_send_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Userinfo.checklast==10){
                    FirebaseDatabase.getInstance().getReference().child(Userinfo.notificarionid.toString()+Userinfo.passid+Userinfo.userid).push().setValue(new ChatMessage(txtmsg.getText().toString(),Userinfo.username));
                    txtmsg.setText("");

                }else{
                    FirebaseDatabase.getInstance().getReference().child(Userinfo.notificarionid.toString()+Userinfo.userid+Userinfo.passid).push().setValue(new ChatMessage(txtmsg.getText().toString(),Userinfo.username));
                    txtmsg.setText("");
                }

            }
        });
        displaymessage();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(Userinfo.checklast==10){
            getMenuInflater().inflate(R.menu.menu_chat, menu);
            return true;
        }
        return true;
        // Inflate the menu; this adds items to the action bar if it is present.

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.chatacceptmenu) {
            finish();
            startActivity(new Intent(this,SelectLawyerActivity.class));
//            Toast.makeText(getApplicationContext(),"Accept "+Userinfo.notificarionid.toString(),Toast.LENGTH_SHORT).show();
            return true;
        }if (id == R.id.chatdeletemenu) {

            deletenotificationquery();
//            Toast.makeText(getApplicationContext(),"Delete "+Userinfo.notificarionid.toString(),Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deletenotificationquery() {

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
                return ;
            }
            else
            {
                // Change below query according to your own database.
                String query = "DELETE FROM [Rentec].[dbo].[notification] WHERE customer_ID='"+Userinfo.userid+"'";
                Statement stmt = connect.createStatement();
                boolean finished=stmt.execute(query);
                if (!finished){
                    ConnectionResult = "Successfully Delete connection ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(new Intent(this,WallActivity.class));
                }else {
                    ConnectionResult = "Fail to Remove Connection ";
                    Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();


                }



                connect.close();
                return ;



            }

        }
        catch (Exception ex)
        {


            ConnectionResult = ex.getMessage();
            Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_SHORT).show();

            return;
        }
//
//        Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();


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
    private void signin(){
        auth.signInWithEmailAndPassword("Rentec@gmail.com","Rentec").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                }else{
                }
            }
        });
    }@Override
    public void onBackPressed() {
        if(Userinfo.checklast==10){
            finish();
            startActivity(new Intent(this,Ownerchatface_Activity.class));
        }else{
            finish();
            startActivity(new Intent(this,Customerchatface_Activity.class));
        }


    }
    private void displaymessage(){
        ListView listmeggase=(ListView) findViewById(R.id.chat_list);


        if(Userinfo.checklast==10){

           adapter=new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.chat_list,FirebaseDatabase.getInstance().getReference().child(Userinfo.notificarionid.toString()+Userinfo.passid+Userinfo.userid)) {
                @Override
                protected void populateView(View v, ChatMessage model, int position) {

                    TextView messgaeuser,message,messagetime;
                    messgaeuser=(TextView) v.findViewById(R.id.chatmsg_user);
                    message=(TextView) v.findViewById(R.id.chatmsg_msg);
                    messagetime=(TextView) v.findViewById(R.id.chatmsg_time);

                    messgaeuser.setText(model.getMessageuser());
                    message.setText(model.getMessagetxt());
                    messagetime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessagetime()));

                }
            };

        }else{

            adapter=new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.chat_list,FirebaseDatabase.getInstance().getReference().child(Userinfo.notificarionid.toString()+Userinfo.userid+Userinfo.passid)) {
                @Override
                protected void populateView(View v, ChatMessage model, int position) {

                    TextView messgaeuser,message,messagetime;
                    messgaeuser=(TextView) v.findViewById(R.id.chatmsg_user);
                    message=(TextView) v.findViewById(R.id.chatmsg_msg);
                    messagetime=(TextView) v.findViewById(R.id.chatmsg_time);

                    messgaeuser.setText(model.getMessageuser());
                    message.setText(model.getMessagetxt());
                    messagetime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessagetime()));

                }
            };
        }

        listmeggase.setAdapter(adapter);

    }

}
