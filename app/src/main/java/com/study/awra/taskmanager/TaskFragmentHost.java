package com.study.awra.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class TaskFragmentHost extends AppCompatActivity {
    private MyPagerAdapter adapter;
    private FragmentWithTitle fragmentWithTitle1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity_fragment);
        TabLayout tabLayout = findViewById(R.id.tl);
        ViewPager viewPager = findViewById(R.id.vp);
        tabLayout.setupWithViewPager(viewPager);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        MyPagerAdapter adapter = new MyPagerAdapter(supportFragmentManager);
        FragmentWithTitle fragmentWithTitle = new ProductivityFragment().setTitle(getString(R.string.productivity));
        fragmentWithTitle1 = new TaskFragmentList().setTitle(getString(R.string.tasks));
        adapter.addFragments(
                fragmentWithTitle1,
                fragmentWithTitle);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feedback:
                Intent intent = new Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_EMAIL, new String[]{"peter.awra@gmail.com", "awra@ukr.net"})
                        .putExtra(Intent.EXTRA_SUBJECT, "Callback about TaskManager");
                startActivity(intent);
                break;
            case R.id.menu_search:
                ((TaskFragmentList) fragmentWithTitle1).refresh();

        }
        return true;
    }

}
