package com.study.awra.taskmanager;

import java.util.UUID;

public class Task {
    private String mTaskTitle;
    private int  mPriority;
    private UUID ID;

    public Task(String taskTitle, int priority) {
        mTaskTitle = taskTitle;
        mPriority = priority;
        ID=UUID.randomUUID();
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public UUID getID() {
        return ID;
    }

}
