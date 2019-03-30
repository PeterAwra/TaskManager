package com.study.awra.taskmanager;

import android.content.Context;

import java.util.Random;
import java.util.UUID;

public class Task {
    private String mTaskTitle;
    private PriorityTask mPriority  ;
    private UUID ID;

    public Task(String taskTitle, PriorityTask priority) {
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

    public PriorityTask getPriority() {
        return mPriority;
    }

    public void setPriority(PriorityTask priority) {
        mPriority = priority;
    }

    public UUID getID() {
        return ID;
    }

    enum  PriorityTask{
        PRIORITY_1,
        PRIORITY_2,
        PRIORITY_3,
        PRIORITY_4;

        public static PriorityTask random() {
            PriorityTask[] values = PriorityTask.values();
            return values[new Random().nextInt(values.length)];
        }
    }
}
