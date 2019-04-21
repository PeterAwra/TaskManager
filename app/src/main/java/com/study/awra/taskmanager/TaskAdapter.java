package com.study.awra.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.study.awra.taskmanager.db.Task;
import java.util.ArrayList;
import java.util.List;

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

  public void deleteItem(int pos) {
    Task removeTask = taskList.remove(pos);
    notifyItemRemoved(pos);
  }

  public void completedItem(int pos) {
    Task completeTask = taskList.remove(pos);
    notifyItemRemoved(pos);
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
