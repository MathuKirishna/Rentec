package com.spartans.rentec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Selectcate_Activity extends AppCompatActivity implements View.OnClickListener{
    private ImageView house,shop,vehicle;
    private TextView txthouse,txtshop,txtvehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcate_);

        house=(ImageView) findViewById(R.id.searchselecthouse);
        shop=(ImageView) findViewById(R.id.searchselectshop);
        vehicle=(ImageView) findViewById(R.id.searchselectmotorbike);
        txthouse=(TextView) findViewById(R.id.txthouse);
        txtshop=(TextView) findViewById(R.id.txtshop);
        txtvehicle=(TextView) findViewById(R.id.txtvehicle);
        house.setOnClickListener(this);
        shop.setOnClickListener(this);
        vehicle.setOnClickListener(this);
        txthouse.setOnClickListener(this);
        txtshop.setOnClickListener(this);
        txtvehicle.setOnClickListener(this);
        Userinfo.searchid=null;
    }

    @Override
    public void onClick(View v) {
        if(v==house){

            Userinfo.selection=1;

            finish();
            startActivity(new Intent(this,WallActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

        }if(v==shop){
            Userinfo.selection=2;
            finish();
            startActivity(new Intent(this,WallActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

        }if(v==vehicle){
            Userinfo.selection=3;
            finish();
            startActivity(new Intent(this,WallActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

        }if(v==txthouse){
            Userinfo.selection=1;
            finish();
            startActivity(new Intent(this,WallActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

        }if(v==txtshop){
            Userinfo.selection=2;
            finish();
            startActivity(new Intent(this,WallActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

        }if(v==txtvehicle){
            Userinfo.selection=3;
            finish();
            startActivity(new Intent(this,WallActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Please Select Any Category",Toast.LENGTH_SHORT).show();
    }
}
