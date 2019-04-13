package com.study.awra.taskmanager.AddTask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.study.awra.taskmanager.R;

class TaskPriorityAdapter extends RecyclerView.Adapter<PriorityItemHolder> {

  private OnClickPriorityItemListListener onClickPriorityItemListListener;
  private int[] color_priority;

  TaskPriorityAdapter(int[] color_priority) {
    this.color_priority = color_priority;
  }

  @NonNull
  @Override
  public PriorityItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    View view = inflater.inflate(R.layout.item_dialog, viewGroup, false);
    final PriorityItemHolder priorityItemHolder = new PriorityItemHolder(view);
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onClickPriorityItemListListener.onClick(priorityItemHolder.getAdapterPosition());
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

  public void setOnClickPriorityItemListListener(
      OnClickPriorityItemListListener onClickPriorityItemListListener) {
    this.onClickPriorityItemListListener = onClickPriorityItemListListener;
  }
}
