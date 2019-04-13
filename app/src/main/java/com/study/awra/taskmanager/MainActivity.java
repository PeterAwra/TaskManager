package com.study.awra.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.study.awra.taskmanager.AddTask.AddTaskFragment;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    TabLayout tabLayout = findViewById(R.id.tl);
    ViewPager viewPager = findViewById(R.id.vp);
    tabLayout.setupWithViewPager(viewPager);
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    MyPagerAdapter adapter = new MyPagerAdapter(supportFragmentManager);
    adapter.addFragments(
        new TaskFragmentList().setTitle(getString(R.string.tasks)),
        new ProductivityFragment().setTitle(getString(R.string.productivity)));
    viewPager.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.option_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    List<Fragment> fragments = null;
    switch (item.getItemId()) {
      case R.id.feedback:
        Intent intent = new Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_EMAIL, new String[] { "peter.awra@gmail.com", "awra@ukr.net" })
            .putExtra(Intent.EXTRA_SUBJECT, "Callback about TaskManager");
        startActivity(intent);
        break;
      case R.id.menu_search:

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction()
            .hide(supportFragmentManager.getFragments().get(0))
            .hide(supportFragmentManager.getFragments().get(1))
            .add(R.id.vp, new AddTaskFragment())
            .addToBackStack("task")
            .show(new ProductivityFragment())
            .commit();
        fragments = supportFragmentManager.getFragments();
    }
    fragments.size();
    return true;
  }
}
