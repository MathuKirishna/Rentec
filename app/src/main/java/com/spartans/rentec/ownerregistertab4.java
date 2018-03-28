package com.spartans.rentec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class ownerregistertab4 extends android.support.v4.app.Fragment {

    private static final String Tag="ownerregistertab4";
    private  Button register;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ownertab4,container,false);


        return view;



    }
}
