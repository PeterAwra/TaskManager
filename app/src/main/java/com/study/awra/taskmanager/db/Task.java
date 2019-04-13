package com.study.awra.taskmanager.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;
@Entity
public class Task implements Serializable {
    private static final long serialVersionUID = 262893905644368855L;
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String mTaskTitle;
    private int mPriority;


    public Task(String taskTitle, int priority) {
        setPriority(priority);
        setTaskTitle(taskTitle);
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
