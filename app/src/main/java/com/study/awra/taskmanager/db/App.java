package com.study.awra.taskmanager.db;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {
    AppDataBase mDataBase;
    public static App instance;

    public AppDataBase getDataBase() {
        return mDataBase;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mDataBase= Room.databaseBuilder(instance,AppDataBase.class,"DBTaskManager.db").allowMainThreadQueries().build();
    }
}
