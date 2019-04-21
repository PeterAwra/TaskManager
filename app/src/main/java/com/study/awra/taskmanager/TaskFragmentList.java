package com.study.awra.taskmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.study.awra.taskmanager.AddTask.AddTaskFragment;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.Task;
import java.lang.ref.WeakReference;
import java.util.List;

public class TaskFragmentList extends FragmentWithTitle implements View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {
  private TaskAdapter adapter;
  private SwipeRefreshLayout swipeRefreshLayout;
  private Context context;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new TaskAdapter(context);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.list_task_fragment, container, false);
    RecyclerView recyclerView = view.findViewById(R.id.rv);
    FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
    swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    styleRecycler(recyclerView);
    floatingActionButton.setOnClickListener(this);
    swipeRefreshLayout.setOnRefreshListener(this);
    return view;
  }

  private void styleRecycler(RecyclerView recyclerView) {
    recyclerView.setLayoutManager(
        new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    recyclerView.addItemDecoration(
        new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    recyclerView.setAdapter(adapter);
    ItemTouchHelper itemTouchHelper =
        new ItemTouchHelper(new SwipeDeleteCompletedCallback(adapter));
    itemTouchHelper.attachToRecyclerView(recyclerView);
  }

  public void refresh() {
    RefreshData refreshData = new RefreshData(this);
    refreshData.execute();
  }

  @Override public void onClick(View v) {
    if (context instanceof MainActivity) {
      FragmentManager supportFragmentManager = ((MainActivity) context).getSupportFragmentManager();
      supportFragmentManager
          .beginTransaction()
          .setCustomAnimations(R.animator.slide_down, R.animator.slide_in_up, R.animator.slide_down,
              R.animator.slide_in_up)
          .hide(supportFragmentManager.getFragments().get(0))
          .add(R.id.container_fragment, new AddTaskFragment())
          .addToBackStack(null)
          .commit();
    }
  }

  @Override public void onRefresh() {
    refresh();
    swipeRefreshLayout.setRefreshing(false);
  }

  public class RefreshData extends AsyncTask<Void, Void, List<Task>> {
    private final WeakReference<TaskFragmentList> fragmentListWR;

    public RefreshData(TaskFragmentList fragmentListWeakReference) {
      this.fragmentListWR =new WeakReference<>(fragmentListWeakReference);
    }

    @Override protected List<Task> doInBackground(Void... voids) {
      return App.getInstance().getDataBase().taskDao().getAllTask();
    }

    @Override protected void onPostExecute(List<Task> task) {
      super.onPostExecute(task);
      TaskFragmentList taskFragmentList=fragmentListWR.get();
      if(taskFragmentList!=null)
      taskFragmentList.adapter.setData(task);
    }
  }
}