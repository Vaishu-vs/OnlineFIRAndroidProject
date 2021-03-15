package com.example.onlinefir;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewsActivity newsFragment = new NewsActivity();
                return newsFragment;
            case 1:
                ComplainActivity complainFragment = new ComplainActivity();
                return complainFragment;
            case 2:
                StatusActivity statusFragment = new StatusActivity();
                return statusFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
