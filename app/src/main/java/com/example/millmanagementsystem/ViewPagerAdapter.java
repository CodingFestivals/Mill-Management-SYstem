package com.example.millmanagementsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos){
            case 0:
                AllAdminFragment af=new AllAdminFragment();
                return af;
            case 1:
                AllManagerFragment am=new AllManagerFragment();
                return am;
            case 2:
                AllUserFragment au=new AllUserFragment();
                return au;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
