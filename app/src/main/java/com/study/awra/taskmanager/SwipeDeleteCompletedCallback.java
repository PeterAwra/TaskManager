package com.study.awra.taskmanager;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class SwipeDeleteCompletedCallback extends ItemTouchHelper.SimpleCallback {

  private final ColorDrawable backgroundDelete;
  private final ColorDrawable backgroundSolve;
  private final Drawable icon_del;
  private final Drawable icon_solve;
  private TaskAdapter mTaskAdapter;
  private boolean eventDelete;
  private boolean eventSolve;

  public SwipeDeleteCompletedCallback(TaskAdapter taskAdapter) {
    super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    mTaskAdapter = taskAdapter;
    icon_del = taskAdapter.getContext().getResources().getDrawable(R.drawable.ic_delete);
    icon_solve = taskAdapter.getContext().getResources().getDrawable(R.drawable.ic_completed);
    backgroundDelete =
        new ColorDrawable(taskAdapter.getContext().getResources().getColor(R.color.swipe_delete));
    backgroundSolve = new ColorDrawable(
        taskAdapter.getContext().getResources().getColor(R.color.swipe_completed));
  }

  @Override
  public boolean onMove(@NonNull RecyclerView recyclerView,
      @NonNull RecyclerView.ViewHolder viewHolder,
      @NonNull RecyclerView.ViewHolder viewHolder1) {
    return false;
  }

  @Override
  public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
      int i) {
    int pos = viewHolder.getAdapterPosition();
    if (eventDelete && !eventSolve) {
      mTaskAdapter.deleteItem(pos);
    }
    if (!eventDelete && eventSolve) {
      mTaskAdapter.completedItem(pos);
    }
  }

  @Override
  public void onChildDraw(@NonNull Canvas c,
      @NonNull RecyclerView recyclerView,
      @NonNull RecyclerView.ViewHolder viewHolder,
      float dX,
      float dY,
      int actionState,
      boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    View itemView = viewHolder.itemView;

    int iconMargin = (itemView.getHeight() - icon_del.getIntrinsicHeight()) / 2;
    int iconTop = itemView.getTop() + (itemView.getHeight() - icon_del.getIntrinsicHeight()) / 2;
    int iconBottom = iconTop + icon_del.getIntrinsicHeight();

    if (dX > 0) {
      eventDelete = false;
      eventSolve = true;
      int iconRight = iconMargin + icon_solve.getIntrinsicWidth();
      icon_solve.setBounds(iconMargin, iconTop, iconRight, iconBottom);
      backgroundSolve.setBounds(itemView.getLeft(),
          itemView.getTop(),
          itemView.getLeft() + ((int) dX),
          itemView.getBottom());
      backgroundSolve.draw(c);
      icon_solve.draw(c);
    } else if (dX < 0) {
      eventDelete = true;
      eventSolve = false;
      int iconLeft = itemView.getRight() - iconMargin - icon_del.getIntrinsicWidth();
      int iconRight = itemView.getRight() - iconMargin;
      icon_del.setBounds(iconLeft, iconTop, iconRight, iconBottom);
      backgroundDelete.setBounds(itemView.getRight() + ((int) dX),
          itemView.getTop(), itemView.getRight(), itemView.getBottom());
      backgroundDelete.draw(c);
      icon_del.draw(c);
    }
  }
}
