package com.study.awra.taskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class TaskFragmentHost extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity_fragment);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment=supportFragmentManager.findFragmentById(R.id.container_fragment);
        if(fragment==null)
        supportFragmentManager.beginTransaction().add(R.id.container_fragment,new TaskFragmentList()).commit();
    }
}
