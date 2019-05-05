package com.study.awra.taskmanager;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.study.awra.taskmanager.db.Task;

class TaskViewHolder extends RecyclerView.ViewHolder {
  private  Context mContext;
  private TextView mTextView;
  private GradientDrawable mShapeDrawable;

  TaskViewHolder(Context context, @NonNull View itemView) {
    super(itemView);
    mContext = context;
    mTextView = itemView.findViewById(R.id.item_priority_text);
    mShapeDrawable = (GradientDrawable) ((ImageView) itemView.findViewById(
        R.id.item_priority_color)).getDrawable();
  }

  void setDataTask(Task task) {
    mTextView.setText(task.getTaskTitle());
    int[] colors = mContext.getResources().getIntArray(R.array.priority_color);
    mShapeDrawable.setColor(colors[task.getPriority()]);
  }
}
