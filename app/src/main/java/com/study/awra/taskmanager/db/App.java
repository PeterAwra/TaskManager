package com.study.awra.taskmanager.db;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {
  public static App instance;
  AppDataBase mDataBase;

  public static App getInstance() {
    return instance;
  }

  public AppDataBase getDataBase() {
    return mDataBase;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    mDataBase = Room.databaseBuilder(instance, AppDataBase.class, "DBTaskManager.db")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build();
  }
}
