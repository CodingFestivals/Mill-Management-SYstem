package com.example.millmanagementsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdapterAllNotification extends FragmentPagerAdapter {
    public ViewPagerAdapterAllNotification(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos){
            case 0:
                FragmentAllAdminNotification af=new FragmentAllAdminNotification();
                return af;
            case 1:
                FragmentAllManagerNotification am=new FragmentAllManagerNotification();
                return am;
            case 2:
                FragmentAllUserNotification au=new FragmentAllUserNotification();
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
