package com.study.awra.taskmanager.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.sql.Date;

@Entity
public class Productivity {
  @PrimaryKey(autoGenerate = true)
  public int id;
  public int countCompleteTask;
  public long date;

  public Productivity(long date) {
    this.date = date;
  }

  @NonNull @Override public String toString() {
    return "\n" + id + "\n " +"countCompleteTask "+ countCompleteTask + "\n " + new Date(date).toString();
  }
}
