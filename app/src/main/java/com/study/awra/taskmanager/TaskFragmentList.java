package com.study.awra.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class TaskFragmentList extends FragmentWithTitle {
    Context mContext;
    private int mRequestCodeAddTask = 5555;
    TaskAdapter adapter;
    private LinearLayout ll;
    private TaskDao mTaskDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDao = App.instance.getDataBase().mTaskDao();
        mContext = getActivity();
        adapter = new TaskAdapter(mContext);
        adapter.setData(mTaskDao.getAllTask());
        adapter.setClickTaskList(new ClickTaskList() {
            @Override
            public void ClickTask(Task task) {
                Toast.makeText(mContext, task.getTaskTitle(), Toast.LENGTH_SHORT).show();

            }
        });
        adapter.setClickLongTaskList(new ClickLongTaskList() {
            @Override
            public void ClickLongTask(Task task) {
                Toast.makeText(mContext, task.getPriority()+"", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setChangeData(new ListenerChangeData() {
            @Override
            public void delete(Task task) {
                mTaskDao.deleteTask(task);
                refresh();
            }

            @Override
            public void add(Task task) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_task_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        ll=view.findViewById(R.id.no_task);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new SwipeDeleteCompletedCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), AddTaskActivity.class), mRequestCodeAddTask);
            }
        });
        refresh();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCodeAddTask && resultCode == RESULT_OK) {
            Task task = (Task) data.getSerializableExtra(AddTaskActivity.class.getName());
            mTaskDao.addTask(task);
            adapter.addData(task);
            refresh();
        }

    }

    public void refresh() {
        if(adapter.getData().size()>0)
            ll.setVisibility(View.INVISIBLE);
        else
            ll.setVisibility(View.VISIBLE);

    }

    public interface ListenerChangeData {
        public void delete(Task task);
        public void add(Task task);
    }
}