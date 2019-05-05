package com.study.awra.taskmanager.AddTask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.study.awra.taskmanager.R;
import java.util.Locale;

class PriorityItemHolder extends RecyclerView.ViewHolder {
  private final String text;
  private ImageView imageView;
  private TextView mTextView;
  private int[] color;

  public PriorityItemHolder(@NonNull View itemView) {
    super(itemView);

    Context context = itemView.getContext();
    mTextView = itemView.findViewById(R.id.dialog_item_priority_text);
    imageView = itemView.findViewById(R.id.dialog_item_priority_color);
    this.color = context.getResources().getIntArray(R.array.priority_color);
    this.text = context.getResources().getString(R.string.priority_text);
  }

  public void setData(int i) {
    mTextView.setText(String.format(Locale.getDefault(), "%s %d", text, (i + 1)));
    imageView.setBackgroundColor(color[i]);
  }
}
