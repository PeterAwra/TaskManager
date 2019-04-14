package com.study.awra.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.study.awra.taskmanager.AddTask.AddTaskActivity;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.DaoProductivity;
import com.study.awra.taskmanager.db.Productivity;
import com.study.awra.taskmanager.db.Task;
import com.study.awra.taskmanager.db.TaskDao;
import java.sql.Date;

import static android.app.Activity.RESULT_OK;

public class TaskFragmentList extends FragmentWithTitle  {
  public static final String SAVE_COMPLETED_TASK = "SAVE_COMPLETED_TASK";
  public static final String COMPLETED_TASK = "COMPLETED_TASK";
  private Context context;
  private int requestCodeAddTask = 5555;
  private TaskAdapter adapter;
  private LinearLayout ll;
  private TaskDao taskDao;
  private DaoProductivity daoProductivity;
  private OnChangeCountCompleteTaskListener onChangeCountCompleteTaskListener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    taskDao = App.instance.getDataBase().taskDao();
    daoProductivity = App.instance.getDataBase().daoProductivity();
    context = getActivity();
    adapter = new TaskAdapter(context);
    adapter.setClickTaskList(new ClickTaskList() {
      @Override
      public void ClickTask(Task task) {
        Toast.makeText(context, task.getTaskTitle(), Toast.LENGTH_SHORT).show();
      }
    });
    adapter.setChangeData(new ListenerChangeData() {
      @Override
      public void delete(Task task) {
        taskDao.deleteTask(task);
        refresh();
      }

      @Override public void complete(Task task) {
        taskDao.deleteTask(task);
        SharedPreferences sharedPreferences =
            context.getSharedPreferences(SAVE_COMPLETED_TASK, Context.MODE_PRIVATE);
        int completed_task = sharedPreferences.getInt(COMPLETED_TASK, 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(COMPLETED_TASK, completed_task + 1);
        edit.apply();
        addStatisticProductivity();
        refresh();
      }
    });
  }

  private void addStatisticProductivity() {
    Date day = new Date(System.currentTimeMillis() - SystemClock.elapsedRealtime());
    Productivity item = daoProductivity.isHas(day);
    if (item != null) {
      item.countCompleteTask += 1;
      daoProductivity.update(item);
    } else {
      Productivity productivity = new Productivity(day.getTime());
      productivity.countCompleteTask = 1;
      daoProductivity.addNewDay(productivity);
    }
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.list_task_fragment, container, false);
    RecyclerView recyclerView = view.findViewById(R.id.rv);
    recyclerView.setLayoutManager(
        new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    recyclerView.addItemDecoration(
        new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    recyclerView.setAdapter(adapter);
    ll = view.findViewById(R.id.no_task);

    FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivityForResult(new Intent(getContext(), AddTaskActivity.class), requestCodeAddTask);
      }
    });
    final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refresh();
        swipeRefreshLayout.setRefreshing(false);
      }
    });
    ItemTouchHelper itemTouchHelper =
        new ItemTouchHelper(new SwipeDeleteCompletedCallback(adapter));
    itemTouchHelper.attachToRecyclerView(recyclerView);
    return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == requestCodeAddTask && resultCode == RESULT_OK) {
      Task task = (Task) data.getSerializableExtra(AddTaskActivity.class.getName());
      taskDao.addTask(task);
    }
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnChangeCountCompleteTaskListener) {
      onChangeCountCompleteTaskListener = (OnChangeCountCompleteTaskListener) context;
    } else {
      throw new UnsupportedOperationException(
          "Activity must implement interface OnChangeCountCompleteTaskListener");
    }
  }

  public void refresh() {
    adapter.setData(taskDao.getAllTask());
    if (adapter.getData().size() > 0) {
      ll.setVisibility(View.INVISIBLE);
    } else {
      ll.setVisibility(View.VISIBLE);
    }
    onChangeCountCompleteTaskListener.refresh();
  }

  public interface ListenerChangeData {
    public void delete(Task task);

    public void complete(Task task);
  }
  public interface OnChangeCountCompleteTaskListener {
    public void refresh();
  }
}