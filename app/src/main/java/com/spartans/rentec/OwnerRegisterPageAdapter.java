package com.spartans.rentec;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;



public class OwnerRegisterPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> ownerfrgmentlist=new ArrayList<>();
    private final List<String> ownerfrgmenttitlelist=new ArrayList<>();

    public void addownerfragment (Fragment fragment, String title){
        ownerfrgmentlist.add(fragment);
        ownerfrgmenttitlelist.add(title);
    }

    public OwnerRegisterPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ownerfrgmenttitlelist.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return ownerfrgmentlist.get(position);
    }

    @Override
    public int getCount() {
        return ownerfrgmentlist.size();
    }
}
