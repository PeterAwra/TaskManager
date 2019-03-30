package com.study.awra.taskmanager;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    List<Task> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mData = TasksData.get().getTasksData();

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new TaskAdapter(mData, new ClickTaskList() {
            @Override
            public void ClickTask(Task task) {
                Toast.makeText(TaskActivity.this, task.getTaskTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
        DividerItemDecoration decor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_list));
        recyclerView.addItemDecoration(decor);
    }

        class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
            List<Task> mTaskList;
            ClickTaskList mClickTaskList;

            public TaskAdapter(List<Task> taskList, ClickTaskList clickTaskList) {
                mTaskList=new ArrayList<>();
                setData(taskList);
                mClickTaskList=clickTaskList;

            }

            private void setData(List<Task> taskList) {
                mTaskList.addAll(taskList);
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                final View mRowList = inflater.inflate(R.layout.item_task, viewGroup,false);
                final TaskViewHolder viewHolder = new TaskViewHolder(mRowList);
                mRowList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickTaskList.ClickTask(mTaskList.get(viewHolder.getAdapterPosition()));
                    }
                });
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
                taskViewHolder.setDataTask(mTaskList.get(i));
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        }

    private class TaskViewHolder extends RecyclerView.ViewHolder {
        Task mTask;
        View mView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;


        }

        public void setDataTask(Task task) {
            mTask=task;
            TextView tv_tasl=mView.findViewById(R.id.task_item);
            tv_tasl.setText(MessageFormat.format("{0}   {1}", mTask.getTaskTitle(), mTask.getPriority().toString()));
            ImageView imageView=mView.findViewById(R.id.task_priority);
            GradientDrawable shapeDrawable= (GradientDrawable) imageView.getDrawable();
            shapeDrawable.setColor(colorPointPriority(mTask.getPriority()));
        }

        private int colorPointPriority(Task.PriorityTask priority) {
            switch (priority) {
                case PRIORITY_1: return getResources().getColor(R.color.priority1);
                case PRIORITY_2: return getResources().getColor(R.color.priority2);
                case PRIORITY_3: return getResources().getColor(R.color.priority3);
                case PRIORITY_4: return getResources().getColor(R.color.priority4);
                default: return Color.TRANSPARENT;
            }

        }

    }
    interface ClickTaskList{
        public void ClickTask(Task task);
    }
}
