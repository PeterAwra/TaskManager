package com.study.awra.taskmanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void addTask(Task task);
    @Delete
    void deleteTask(Task task);
    @Update
    void update(Task task);
    @Query("Select * from task")
    List<Task> getAllTask();
}
