package com.study.awra.taskmanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.MessageFormat;

class TaskViewHolder extends RecyclerView.ViewHolder {
    private final Context mContext;
    private View mView;
    TaskViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        mContext=context;
        mView=itemView;
    }

    void setDataTask(Task task) {
        TextView tv_tasl=mView.findViewById(R.id.task_item);
        tv_tasl.setText(MessageFormat.format("{0}   {1}", task.getTaskTitle(), task.getPriority().toString()));
        ImageView imageView=mView.findViewById(R.id.task_priority);
        GradientDrawable shapeDrawable= (GradientDrawable) imageView.getDrawable();
        shapeDrawable.setColor(colorPointPriority(task.getPriority()));
    }

    private int colorPointPriority(Task.PriorityTask priority) {
        switch (priority) {
            case PRIORITY_1: return mContext.getResources().getColor(R.color.priority1);
            case PRIORITY_2: return mContext.getResources().getColor(R.color.priority2);
            case PRIORITY_3: return mContext.getResources().getColor(R.color.priority3);
            case PRIORITY_4: return mContext.getResources().getColor(R.color.priority4);
            default: return Color.TRANSPARENT;
        }
    }
}
