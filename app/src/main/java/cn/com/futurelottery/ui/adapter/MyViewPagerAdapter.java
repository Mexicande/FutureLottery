package cn.com.virtualbitcoin.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private int size;
    private List<Fragment>list;

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment>list) {
        super(fm);
        this.list=list;
        this.size=list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return size;
    }
}
