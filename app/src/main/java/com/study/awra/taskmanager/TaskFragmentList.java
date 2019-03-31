package com.study.awra.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TaskFragmentList extends MyWithTitleFragment {
    List<Task> mData;
    Context mContext;
    private int mRequestCodeAddTask=5555;
    TaskAdapter adapter;
    private LayoutInflater mInflater;
    @Nullable
    private ViewGroup mContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;
        View view;
        if(mData.size()>=0){
            view = getView(container, inflater);
        }
        else
            view=inflater.inflate(R.layout.task_fragment_no_tasks,container,false);

        FloatingActionButton floatingActionButton=view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),AddTaskActivity.class), mRequestCodeAddTask);
            }
        });
        return view;
    }

    private View getView(@Nullable ViewGroup container, @NonNull LayoutInflater inflater) {
        View view;
        view=inflater.inflate(R.layout.list_task_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new TaskAdapter(mContext, new ClickTaskList() {
            @Override
            public void ClickTask(Task task) {
                Toast.makeText(mContext, task.getTaskTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setData(mData);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decor = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_list));
        recyclerView.addItemDecoration(decor);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==mRequestCodeAddTask && resultCode==RESULT_OK)
            mData.add((Task) data.getSerializableExtra(AddTaskActivity.class.getName()));
           adapter.setData(mData);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = TasksData.get().getTasksData();
        mContext=getActivity();

    }
}
