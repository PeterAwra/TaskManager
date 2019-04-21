package com.study.awra.taskmanager;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.Productivity;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProductivityFragment extends FragmentWithTitle {
  CustomGraph mGraph;
  private Context context;
  private TextView mTextView;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    context = inflater.getContext();
    View view = inflater.inflate(R.layout.productivity_fragment_layout, container, false);
    mTextView = view.findViewById(R.id.tv_completed_task);
    mGraph = view.findViewById(R.id.graph);
    final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srl);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refresh();
        swipeRefreshLayout.setRefreshing(false);
      }
    });
    //refresh();
    return view;
  }

  void refresh() {
    int showDays = 7;
    int[] valueForDay = new int[showDays];
    String[] nameDay = new String[showDays];
    getListDaysStatistic(showDays, valueForDay, nameDay);
    int task_completed = getCompletedTaskFromPreferences();
    drawData(valueForDay, nameDay, task_completed);
  }

  private void drawData(int[] valueForDay, String[] nameDay, int task_completed) {
    mGraph.setData(valueForDay, nameDay);
    mTextView.setText(String.format(Locale.getDefault(), "%d  %s", task_completed,
        getString(R.string.completed_task)));
    mGraph.requestLayout();
  }

  private void getListDaysStatistic(int showDays, int[] valueForDay, String[] nameDay) {
    long durationDay = 1000 * 60 * 60 * 24;
    long presentDay = System.currentTimeMillis() - SystemClock.elapsedRealtime() + 1000;
    for (int i = 0; i < showDays; i++) {
      long date = presentDay - (showDays - i - 1) * durationDay;
      Productivity productivity = getProductivityFromBase(date);
      if (productivity != null) {
        valueForDay[i] = productivity.countCompleteTask;
      } else {
        valueForDay[i] = 0;
      }
      nameDay[i] = getNameDay(date);
    }
  }

  private Productivity getProductivityFromBase(long date) {
    return App.getInstance()
        .getDataBase()
        .daoProductivity()
        .getDay(date);
  }

  private String getNameDay(long date) {
    return new SimpleDateFormat("E", Locale.getDefault()).format(date);
  }

  private int getCompletedTaskFromPreferences() {
    context.getResources();
    return context.getSharedPreferences(MainActivity.SAVE_COMPLETED_TASK, Context.MODE_PRIVATE)
        .getInt(MainActivity.COMPLETED_TASK, 0);
  }
}
