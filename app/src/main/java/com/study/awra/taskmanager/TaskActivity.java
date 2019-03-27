package com.study.awra.taskmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TaskActivity extends AppCompatActivity {
    List<Task> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mData=TasksData.get().getTasksData();

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new TaskAdapter(mData));
    }

        class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
            List<Task> mTaskList;

            public TaskAdapter(List<Task> taskList) {
                setData(taskList);
            }

            private void setData(List<Task> taskList) {
                mTaskList.addAll(taskList);
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View mRowList = inflater.inflate(R.layout.item_task, viewGroup,false);
                return new TaskViewHolder(mRowList,mTaskList.get(i));
            }

            @Override
            public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
                taskViewHolder. setDataTask(mTaskList.get(i));
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        }

    private class TaskViewHolder extends RecyclerView.ViewHolder {
        Task mTask;
        public TaskViewHolder(@NonNull View itemView, Task i) {
            super(itemView);
            TextView tv_tasl=itemView.findViewById(R.id.task_item);
            tv_tasl.setText(mTask.getTaskTitle());

        }

        public void setDataTask(Task task) {
            mTask=task;
        }
    }
}
