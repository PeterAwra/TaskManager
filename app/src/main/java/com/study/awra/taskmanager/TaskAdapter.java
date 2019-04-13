package com.study.awra.taskmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.awra.taskmanager.db.Task;

import java.util.ArrayList;
import java.util.List;

class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    public static final String SAVE_COMPLETED_TASK ="SAVE_COMPLETED_TASK" ;
    public static final String COMPLETED_TASK ="COMPLETED_TASK" ;
    private List<Task> mTaskList;
    private ClickTaskList mClickTaskList;
    private ClickLongTaskList mClickLongTaskList;
    private Context mContext;
    private TaskFragmentList.ListenerChangeData mChangeData;

    public void setClickTaskList(ClickTaskList clickTaskList) {
        mClickTaskList = clickTaskList;
    }
    public void setClickLongTaskList(ClickLongTaskList clickLongTaskList) { mClickLongTaskList = clickLongTaskList; }
    public void setChangeData(TaskFragmentList.ListenerChangeData listenerChangeData) { mChangeData = listenerChangeData; }

    public TaskAdapter(Context context) {
        mContext = context;
        mTaskList=new ArrayList<>();
    }

    public void setData(List<Task> taskList) {
        mTaskList.clear();
        mTaskList.addAll(taskList);
        notifyDataSetChanged();
    }
    public void addData(Task task) {
        mTaskList.add(task);
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
                if(mClickTaskList!=null)
                mClickTaskList.ClickTask(mTaskList.get(viewHolder.getAdapterPosition()));
            }
        });
        mRowList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mClickLongTaskList!=null)
                mClickLongTaskList.ClickLongTask(mTaskList.get(viewHolder.getAdapterPosition()));
                return true;
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

    public Context getContext() {
        return mContext;
    }

    public void deleteItem(int pos) {
        Task removeTask = mTaskList.remove(pos);
        notifyItemRemoved(pos);
        mChangeData.delete(removeTask);
    }

    public void completedItem(int pos) {
        Task completeTask = mTaskList.remove(pos);
        notifyItemRemoved(pos);
        mChangeData.delete(completeTask);
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SAVE_COMPLETED_TASK,Context.MODE_PRIVATE);
        int completed_task = sharedPreferences.getInt(COMPLETED_TASK, 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(COMPLETED_TASK,completed_task+1);
        edit.apply();
    }

    public List<Task> getData() {
    return mTaskList;
    }
}
