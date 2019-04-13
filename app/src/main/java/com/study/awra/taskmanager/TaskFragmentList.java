package com.study.awra.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.study.awra.taskmanager.AddTask.AddTaskActivity;
import com.study.awra.taskmanager.db.App;
import com.study.awra.taskmanager.db.Task;
import com.study.awra.taskmanager.db.TaskDao;

import static android.app.Activity.RESULT_OK;

public class TaskFragmentList extends FragmentWithTitle {
    private Context mContext;
    private int requestCodeAddTask = 5555;
    private TaskAdapter adapter;
    private LinearLayout ll;
    private TaskDao taskDao;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskDao = App.instance.getDataBase().mTaskDao();
        mContext = getActivity();
        adapter = new TaskAdapter(mContext);
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
                taskDao.deleteTask(task);
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
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        ll=view.findViewById(R.id.no_task);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), AddTaskActivity.class), requestCodeAddTask);
            }
        });
        refresh();
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new SwipeDeleteCompletedCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeAddTask && resultCode == RESULT_OK) {
            Task task = (Task) data.getSerializableExtra(AddTaskActivity.class.getName());
            taskDao.addTask(task);
//            adapter.addData(task);
//            refresh();
        }

    }

    public void refresh() {
        adapter.setData(taskDao.getAllTask());
        if(adapter.getData().size()>0)
            ll.setVisibility(View.INVISIBLE);
        else
            ll.setVisibility(View.VISIBLE);
//        FragmentManager fragmentManager = getFragmentManager();
//        Fragment fragment = fragmentManager.getFragments().get(0);
//        fragmentManager.beginTransaction().replace(R.id.container_fragment,fragment).commit();

    }

    public interface ListenerChangeData {
        public void delete(Task task);
        public void add(Task task);
    }
}