package com.study.awra.taskmanager.AddTask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.study.awra.taskmanager.R;

interface OnChosenPriority {
  void chosenPriority(int priority);
}

class TaskPriorityAdapter extends RecyclerView.Adapter<PriorityItemHolder> {
  private int[] color_priority;
  private OnChosenPriority onChosenPriority;

  TaskPriorityAdapter(int[] color_priority) {
    this.color_priority = color_priority;
  }


  @NonNull
  @Override
  public PriorityItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    final View view = inflater.inflate(R.layout.item_dialog, viewGroup, false);
    final PriorityItemHolder priorityItemHolder = new PriorityItemHolder(view);
    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onChosenPriority != null) {
          onChosenPriority.chosenPriority(priorityItemHolder.getAdapterPosition());
        }
      }
    });
    return priorityItemHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull PriorityItemHolder priorityItemHolder, int i) {
    priorityItemHolder.setData(i);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public int getItemCount() {
    return color_priority.length;
  }

  public void setOnChosenPriority(OnChosenPriority onChosenPriority) {
    this.onChosenPriority = onChosenPriority;
  }
}