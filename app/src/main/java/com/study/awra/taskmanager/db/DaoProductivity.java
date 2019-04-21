package com.study.awra.taskmanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface DaoProductivity {
  @Insert
  public void addNewDay(Productivity productivity);

  @Query("Delete from productivity")
  void deleteAll();

  @Update
  void update(Productivity productivity);

  @Query("Select * from productivity where date=:date")
  public Productivity getDay(int date);

  @Query("Select * from Productivity")
  List<Productivity> getAll();
}
