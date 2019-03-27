package com.study.awra.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TasksData {
    private static TasksData sTasksData;
    private  List<Task> mTasks;

    private TasksData() {
        mTasks = new ArrayList<>();
//
        for(int i=0;i<50;++i){
            mTasks.add(new Task("Task #"+i,i%4));
//
        }
    }

    public static TasksData get(){
        if(sTasksData!=null)
            return sTasksData;
        else {
             return new TasksData();
        }
    }

    private  void addTask(String title, int priority) {
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
