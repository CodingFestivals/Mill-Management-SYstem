package com.example.millmanagementsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdpaterMoneyRecive extends FragmentPagerAdapter {
    public ViewPagerAdpaterMoneyRecive(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos){
            case 0:
                FragmentAllAdminMoneyRecive af=new FragmentAllAdminMoneyRecive();
                return af;
            case 1:
                FragmentAllManagerMoneyRecive am=new FragmentAllManagerMoneyRecive();
                return am;
            case 2:
                FragmentAllUserMoneyRecive au=new FragmentAllUserMoneyRecive();
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
