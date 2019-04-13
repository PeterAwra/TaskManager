package com.study.awra.taskmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Locale;
import java.util.Random;

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
    refresh();
    return view;
  }

  void refresh() {
    Random random = new Random();
    int task_completed =
        context.getSharedPreferences(TaskAdapter.SAVE_COMPLETED_TASK, Context.MODE_PRIVATE)
            .getInt(TaskAdapter.COMPLETED_TASK, 0);
    int[] i = new int[7];
    for (int j = 0; j < i.length; j++)
      i[j] = random.nextInt(5);
    mGraph.setData(i, new String[] { "ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "НД" });
    mTextView.setText(String.format(Locale.getDefault(), "%d  %s", task_completed,
        getString(R.string.completed_task)));
    mGraph.requestLayout();
  }
}
