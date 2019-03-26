package com.study.awra.taskmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new TaskAdapter());
    }

        class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View inflate = inflater.inflate(R.layout.item_task, viewGroup,false);
                TaskViewHolder viewHolder =new TaskViewHolder(inflate);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return 20;
            }
        }

    private class TaskViewHolder extends RecyclerView.ViewHolder {
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView view=itemView.findViewById(R.id.task_item);
            view.setText("dsfdfdsf");

        }
    }
}
