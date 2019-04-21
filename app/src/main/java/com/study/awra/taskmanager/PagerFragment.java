package com.study.awra.taskmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerFragment extends Fragment {
  private FragmentWithTitle fragmentListTasks;
  private FragmentWithTitle fragmentProductivity;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.pager_fragmen, container, false);
    TabLayout tabLayout = view.findViewById(R.id.tl);
    ViewPager viewPager = view.findViewById(R.id.vp);
    tabLayout.setupWithViewPager(viewPager);
    FragmentManager supportFragmentManager = getFragmentManager();
    MyPagerAdapter adapter = new MyPagerAdapter(supportFragmentManager);
    fragmentProductivity = new ProductivityFragment().setTitle(getString(R.string.productivity));
    fragmentListTasks = new TaskFragmentList().setTitle(getString(R.string.tasks));
    adapter.addFragments(
        fragmentListTasks,
        fragmentProductivity);
    viewPager.setAdapter(adapter);
    return view;
  }
}
