package com.study.awra.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
        recyclerView.setAdapter(new TaskAdapter(this,mData, new ClickTaskList() {
            @Override
            public void ClickTask(Task task) {
                Toast.makeText(TaskActivity.this, task.getTaskTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
        DividerItemDecoration decor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_list));
        recyclerView.addItemDecoration(decor);
    }

}
