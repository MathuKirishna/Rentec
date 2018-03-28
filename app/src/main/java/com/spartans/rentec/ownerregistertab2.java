package com.spartans.rentec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ownerregistertab2 extends android.support.v4.app.Fragment {

    private static final String Tag="ownerregistertab2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ownertab2,container,false);
        return view;
    }
}
