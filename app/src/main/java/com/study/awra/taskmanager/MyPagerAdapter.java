package com.study.awra.taskmanager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MyPagerAdapter extends FragmentPagerAdapter {
    private List<MyWithTitleFragment> mFragments;


    public void addFragments(MyWithTitleFragment...fragment) {
        mFragments = Arrays.asList(fragment);

    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments=new ArrayList<>();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int i) {
         return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
