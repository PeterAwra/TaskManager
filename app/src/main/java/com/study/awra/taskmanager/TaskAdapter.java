package com.study.awra.taskmanager;

import android.content.Context;
import android.content.SharedPreferences;
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
  private ClickTaskList mClickTaskList;
  private Context mContext;
  private TaskFragmentList.ListenerChangeData mChangeData;

  public TaskAdapter(Context context) {
    mContext = context;
    taskList = new ArrayList<>();
  }

  public void setClickTaskList(ClickTaskList clickTaskList) {
    mClickTaskList = clickTaskList;
  }

  public void setChangeData(TaskFragmentList.ListenerChangeData listenerChangeData) {
    mChangeData = listenerChangeData;
  }

  @NonNull
  @Override
  public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    View mRowList = inflater.inflate(R.layout.item_task, viewGroup, false);
    final TaskViewHolder viewHolder = new TaskViewHolder(mContext, mRowList);
    mRowList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mClickTaskList != null) {
          mClickTaskList.ClickTask(taskList.get(viewHolder.getAdapterPosition()));
        }
      }
    });
    return viewHolder;
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
    return mContext;
  }

  public void deleteItem(int pos) {
    Task removeTask = taskList.remove(pos);
    notifyItemRemoved(pos);
    mChangeData.delete(removeTask);
  }

  public void completedItem(int pos) {
    Task completeTask = taskList.remove(pos);
    notifyItemRemoved(pos);
    mChangeData.complete(completeTask);
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
