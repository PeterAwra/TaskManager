package com.study.awra.taskmanager.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Transaction;

@Database(entities = { Task.class, Productivity.class }, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

  public abstract TaskDao taskDao();

  public abstract DaoProductivity daoProductivity();

  @Transaction
  public void deleteAll() {
    taskDao().deleteAll();
    daoProductivity().deleteAll();
  }
}
