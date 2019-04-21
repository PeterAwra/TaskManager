package com.study.awra.taskmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.Productivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProductivityFragment extends FragmentWithTitle {
  CustomGraph mGraph;
  private Context context;
  private TextView mTextView;
  private int showDays=7;

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.add("5 day");
    menu.add("7 day");
    menu.add("10 day");
  }

  @Override public boolean onContextItemSelected(MenuItem item) {
    if (item.getTitle() == "5 day") {
      showDays = 5;
    } else if (item.getTitle() == "7 day") {
      showDays = 7;
    } else if (item.getTitle() == "10 day") showDays = 10;
    refresh();
    return false;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    context = inflater.getContext();
    View view = inflater.inflate(R.layout.productivity_fragment_layout, container, false);
    mTextView = view.findViewById(R.id.tv_completed_task);
    mGraph = view.findViewById(R.id.graph);
    registerForContextMenu(mGraph);
    final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srl);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refresh();
        swipeRefreshLayout.setRefreshing(false);
      }
    });
    refresh();
    return view;
  }

  void refresh() {
    UpdateGraphThread updateGraphThread = new UpdateGraphThread();
    updateGraphThread.start();
    int task_completed =
        context.getSharedPreferences(MainActivity.SAVE_COMPLETED_TASK, Context.MODE_PRIVATE)
            .getInt(MainActivity.COMPLETED_TASK, 0);
    mTextView.setText(String.format(Locale.getDefault(), "%d  %s", task_completed,
        getString(R.string.completed_task)));
  }

  @Override public void onResume() {
    super.onResume();
    refresh();
  }

  private class UpdateGraphThread extends Thread {
    @Override public void run() {
      int[] valueForDay = new int[showDays];
      String[] nameDay = new String[showDays];
      int presentDay = Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_YEAR);
      for (int i = 0; i < showDays; i++) {
        int date = presentDay - (showDays - i - 1);
        Productivity productivity = getProductivityFromBase(date);
        if (productivity != null) {
          valueForDay[i] = productivity.countCompleteTask;
        } else {
          valueForDay[i] = 0;
        }
        nameDay[i] = new SimpleDateFormat("E", Locale.getDefault()).format(
            new Date(Calendar.getInstance(Locale.getDefault()).getTime().getTime()
                - 1000 * 60 * 60 * 24 * (showDays - 1 - i)));
      }
      final int[] valueForDay1 = valueForDay;
      final String[] nameDay2 = nameDay;
      mGraph.post(new Runnable() {
        @Override public void run() {
          if (mGraph != null) {
            mGraph.setData(valueForDay1, nameDay2);
            mGraph.requestLayout();
          }
        }
      });
    }

    private Productivity getProductivityFromBase(int date) {
      return App.getInstance()
          .getDataBase()
          .daoProductivity()
          .getDay(date);
    }
  }
}
