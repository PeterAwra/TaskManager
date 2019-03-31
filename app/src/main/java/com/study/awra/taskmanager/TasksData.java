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
//
        for(int i=0;i<10;++i){
            mTasks.add(new Task("Task #"+i, Task.PriorityTask.random())); }
//
    }

    public static TasksData get(){
        if(sTasksData!=null)
            return sTasksData;
        else {
             return new TasksData();
        }
    }

    private  void addTask(String title, Task.PriorityTask priority) {
        mTasks.add(new Task(title,priority));
    }

    public List<Task> getTasksData() {
        return mTasks ;
    }
    public Task get(UUID id){
        for (Task task:mTasks) {
            if (task.getID()==id)
                return task;
        }
        return null;
    }
}
