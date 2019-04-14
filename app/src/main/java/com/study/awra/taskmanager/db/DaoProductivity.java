package com.study.awra.taskmanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;
import java.sql.Date;
import java.util.List;

@Dao
public interface DaoProductivity {
  @Insert
  public void addNewDay(Productivity productivity);

  @TypeConverters({ DataConverter.class })
  @Query("Select * from productivity where (date+1000)>=:date and (date-1000)<=:date")
  public Productivity isHas(Date date);

  @Query("Delete from productivity")
  void deleteAll();

  @Update
  void update(Productivity item);
  @Query("select * from productivity where date>(:presentDay-:days*24*60*60*1000)")
  List<Productivity> getLatestSevenDay(long presentDay,int days);
@Query("Select * from productivity where (date+1000)>=:date and (date-1000)<=:date")
  public Productivity getDay(long date);
}
