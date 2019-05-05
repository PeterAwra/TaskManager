package com.study.awra.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.DaoProductivity;
import com.study.awra.taskmanager.db.Productivity;
import com.study.awra.taskmanager.db.Task;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
  public static final String SAVE_COMPLETED_TASK = "SAVE_COMPLETED_TASK";
  public static final String COMPLETED_TASK = "COMPLETED_TASK";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    Toolbar toolbar = findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .add(R.id.container_fragment, new PagerFragment())
          .commit();
    }
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
        writeDeveloper();
        break;
      }
      case R.id.menu_search: {
        break;
      }
      case R.id.delete_all: {
        deleteAll();
        break;
      }
      case R.id.fake_data: {
        fakeData();
        break;
      }
    }
    return true;
  }

  private void deleteAll() {
    new Thread(new Runnable() {
      @Override public void run() {
        App.getInstance().getDataBase().deleteAll();
        getSharedPreferences(SAVE_COMPLETED_TASK, MODE_PRIVATE).edit()
            .putInt(COMPLETED_TASK, 0)
            .apply();
      }
    }).start();
  }

  private void writeDeveloper() {
    Intent intent = new Intent(Intent.ACTION_SEND)
        .setType("text/plain")
        .putExtra(Intent.EXTRA_EMAIL, new String[] { "peter.awra@gmail.com", "awra@ukr.net" })
        .putExtra(Intent.EXTRA_SUBJECT, "Callback about TaskManager");
    startActivity(intent);
  }

  private void fakeData() {
    deleteAll();
    final List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 50; i++)
      tasks.add(new Task("Task fake  " + i, i % 4));
    new Thread(new Runnable() {
      @Override public void run() {
        App.getInstance().getDataBase().taskDao().addTasks(tasks);
        DaoProductivity daoProductivity = App.getInstance().getDataBase().daoProductivity();
        int presentDay = Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_YEAR);
        for (int i = 1; i <= 7; i++) {
          daoProductivity.addNewDay(
              new Productivity(presentDay - i).setCountCompleteTask(new Random().nextInt(10)));
        }
      }
    }).start();
  }
}
