package com.study.awra.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TasksData {
    private static TasksData sTasksData;
    private  List<Task> mTasks;

    private TasksData() {
        mTasks = new ArrayList<>();
        AppDataBase dataBase = App.getInstance().mDataBase;
        mTasks= dataBase.mTaskDao().getAllTask();
    }

    public void fakeData(int count) {
        for(int i=1;i<=count;i++)
            mTasks.add(new Task("Task #"+i,new Random().nextInt(4)));
    }

    public static TasksData get(){
        if(sTasksData==null)
            sTasksData=new TasksData();
        return sTasksData;
    }

    public   void addTask(String title, int priority) {
        mTasks.add(new Task(title,priority));
    }
    public   void addTask(Task task) {
        mTasks.add(task);
    }

    public List<Task> getTasksData() {
        return mTasks ;
    }

    public int size() {
        return mTasks.size();
    }
}
