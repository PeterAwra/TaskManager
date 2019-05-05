package com.study.awra.taskmanager;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.DaoProductivity;
import com.study.awra.taskmanager.db.Productivity;
import com.study.awra.taskmanager.db.Task;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
  private List<Task> taskList;
  private Context context;

  public TaskAdapter(Context context) {
    this.context = context;
    taskList = new ArrayList<>();
  }

  @NonNull
  @Override
  public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    View mRowList = inflater.inflate(R.layout.item_task, viewGroup, false);
    return new TaskViewHolder(context, mRowList);
  }

  @Override
  public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
    taskViewHolder.setDataTask(taskList.get(i));
  }

  @Override
  public int getItemCount() {
    return taskList.size();
  }

  public Context getContext() {
    return context;
  }

  public void deleteItem(final int pos, final RecyclerView.ViewHolder viewHolder) {
    final Task removeTask = taskList.remove(pos);
    showSnackbar(pos, viewHolder, removeTask);
  }

  private void showSnackbar(final int pos, RecyclerView.ViewHolder viewHolder,
      final Task changeTask) {
    notifyItemRemoved(pos);
    Snackbar snackbar =
        Snackbar.make(viewHolder.itemView, "", Snackbar.LENGTH_SHORT).setAction("Undo",
            new View.OnClickListener() {
              @Override public void onClick(View v) {
                taskList.add(pos, changeTask);
                TaskAdapter.this.notifyItemInserted(pos);
              }
            });
    View view = snackbar.setActionTextColor(Color.WHITE)
        .getView();
    view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
    snackbar.addCallback(new Snackbar.Callback() {
      @Override public void onDismissed(Snackbar transientBottomBar, int event) {
        super.onDismissed(transientBottomBar, event);
        if (event != DISMISS_EVENT_ACTION) {
          deleteTaskDB(changeTask);
        }
      }
    });
    snackbar.show();
  }

  public void completedItem(int pos, RecyclerView.ViewHolder viewHolder) {
    final Task completeTask = taskList.remove(pos);
    showSnackbar(pos, viewHolder, completeTask);
    addToStatistic();
  }

  private void addToStatistic() {
    {
      SharedPreferences sharedPreferences =
          context.getSharedPreferences(MainActivity.SAVE_COMPLETED_TASK, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt(MainActivity.COMPLETED_TASK,
          sharedPreferences.getInt(MainActivity.COMPLETED_TASK, 0) + 1);
      editor.apply();
    }
    {
      new Thread(new Runnable() {
        @Override public void run() {
          int presentDay = Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_YEAR);
          DaoProductivity daoProductivity = App.getInstance().getDataBase().daoProductivity();
          Productivity day = daoProductivity.getDay(presentDay);
          if (day == null) {
            Productivity productivityDay = new Productivity(presentDay);
            productivityDay.countCompleteTask = 1;
            daoProductivity.addNewDay(productivityDay);
          } else {
            day.countCompleteTask += 1;
            daoProductivity.update(day);
          }
        }
      }).start();
    }
  }

  private void deleteTaskDB(final Task removeTask) {
    new Thread(new Runnable() {
      @Override public void run() {
        App.getInstance().getDataBase().taskDao().deleteTask(removeTask);
      }
    }).start();
  }

  public List<Task> getData() {
    return taskList;
  }

  public void setData(List<Task> taskList) {
    this.taskList.clear();
    this.taskList.addAll(taskList);
    notifyDataSetChanged();
  }
}
