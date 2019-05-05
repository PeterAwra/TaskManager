package com.study.awra.taskmanager.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;

public class App extends Application {
  public static App instance;
  AppDataBase mDataBase;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    mDataBase = Room.databaseBuilder(instance, AppDataBase.class, "DBTaskManager.db")
        .fallbackToDestructiveMigration()
        .build();
  }

  public static App getInstance() {
    return instance;
  }

  public AppDataBase getDataBase() {
    return mDataBase;
  }
}
