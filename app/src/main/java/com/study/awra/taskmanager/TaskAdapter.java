package com.study.awra.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
    private List<Task> mTaskList;
    private ClickTaskList mClickTaskList;
    private Context mContext;

    public TaskAdapter(Context context, ClickTaskList clickTaskList) {
        mContext = context;
        mTaskList=new ArrayList<>();
        mClickTaskList=clickTaskList;

    }

    public void setData(List<Task> taskList) {
        mTaskList.clear();
        mTaskList.addAll(taskList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View mRowList = inflater.inflate(R.layout.item_task, viewGroup,false);
        final TaskViewHolder viewHolder = new TaskViewHolder(mContext,mRowList);
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
        return mTaskList.size();
    }
}
