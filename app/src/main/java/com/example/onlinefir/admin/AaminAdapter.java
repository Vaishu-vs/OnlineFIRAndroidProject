package com.example.onlinefir.admin;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class AaminAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public AaminAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ViewComplainActivity newsFragment = new ViewComplainActivity();
                return newsFragment;
            case 1:
                AdminUserActivity complainFragment = new AdminUserActivity();
                return complainFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
