package com.study.awra.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.AppDataBase;
import com.study.awra.taskmanager.db.Task;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    TaskFragmentList.OnChangeCountCompleteTaskListener {
  AppDataBase appDataBase = App.getInstance().getDataBase();
  private FragmentWithTitle fragmentListTasks;
  private FragmentWithTitle fragmentProductivity;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    TabLayout tabLayout = findViewById(R.id.tl);
    ViewPager viewPager = findViewById(R.id.vp);
    tabLayout.setupWithViewPager(viewPager);
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    MyPagerAdapter adapter = new MyPagerAdapter(supportFragmentManager);
    fragmentProductivity = new ProductivityFragment().setTitle(getString(R.string.productivity));
    fragmentListTasks = new TaskFragmentList().setTitle(getString(R.string.tasks));
    adapter.addFragments(
        fragmentListTasks,
        fragmentProductivity);
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
      case R.id.feedback: {
        Intent intent = new Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_EMAIL, new String[] { "peter.awra@gmail.com", "awra@ukr.net" })
            .putExtra(Intent.EXTRA_SUBJECT, "Callback about TaskManager");
        startActivity(intent);
        break;
      }
      case R.id.menu_search: {
        break;
      }
      case R.id.delete_all: {
        appDataBase.deleteAll();
        break;
      }
      case R.id.fake_data: {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 50; i++)
          tasks.add(new Task("Task fake  " + i, i % 4));
        App.getInstance().getDataBase().taskDao().addTasks(tasks);
        break;
      }
    }
    return true;
  }

  @Override public void refresh() {
    ((ProductivityFragment) fragmentProductivity).refresh();
  }
}
