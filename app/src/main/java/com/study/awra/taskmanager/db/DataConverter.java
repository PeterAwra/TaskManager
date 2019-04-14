package com.study.awra.taskmanager.db;

import android.arch.persistence.room.TypeConverter;
import java.sql.Date;

public class DataConverter {
  @TypeConverter
  public long convertDataToLong(Date date){
    return date.getTime();
  }
}
