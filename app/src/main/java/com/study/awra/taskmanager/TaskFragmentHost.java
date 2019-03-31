package com.study.awra.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TaskFragmentHost extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity_fragment);
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        Fragment fragment=supportFragmentManager.findFragmentById(R.id.container_fragment);
//        if(fragment==null)
//        supportFragmentManager
//                .beginTransaction()
//                .add(R.id.container_fragment,new AddTaskFragment()).
//                commit();
        TabLayout tabLayout=findViewById(R.id.tl);
        ViewPager viewPager=findViewById(R.id.vp);
        tabLayout.setupWithViewPager(viewPager);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(
                new TaskFragmentList().setTitle(getString(R.string.tasks)),
                new ProductivityFragment().setTitle(getString(R.string.productivity)));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.сallback:
                Intent intent = new Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_EMAIL, new String[] { "peter.awra@gmail.com","awra@ukr.net" })
                        .putExtra(Intent.EXTRA_SUBJECT, "Callback about TaskManager");
                startActivity(intent);
                break;
        }
        return true;
    }
}
