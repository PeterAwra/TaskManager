package com.study.awra.taskmanager.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Productivity {
  @PrimaryKey(autoGenerate = true)
  public int id;
  public int countCompleteTask;
  public int date;

  public Productivity(int date) {
    this.date = date;
  }

  public Productivity setCountCompleteTask(int countCompleteTask) {
    this.countCompleteTask = countCompleteTask;
    return this;
  }

  @NonNull @Override public String toString() {
    return "\n" + id + "\n " + "countCompleteTask " + countCompleteTask + "\n " + date;
  }
}
