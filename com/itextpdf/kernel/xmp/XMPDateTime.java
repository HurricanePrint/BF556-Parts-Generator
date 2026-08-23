package com.itextpdf.kernel.xmp;

import java.util.Calendar;
import java.util.TimeZone;

public interface XMPDateTime extends Comparable {
  int getYear();
  
  void setYear(int paramInt);
  
  int getMonth();
  
  void setMonth(int paramInt);
  
  int getDay();
  
  void setDay(int paramInt);
  
  int getHour();
  
  void setHour(int paramInt);
  
  int getMinute();
  
  void setMinute(int paramInt);
  
  int getSecond();
  
  void setSecond(int paramInt);
  
  int getNanoSecond();
  
  void setNanoSecond(int paramInt);
  
  TimeZone getTimeZone();
  
  void setTimeZone(TimeZone paramTimeZone);
  
  boolean hasDate();
  
  boolean hasTime();
  
  boolean hasTimeZone();
  
  Calendar getCalendar();
  
  String getISO8601String();
}


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/XMPDateTime.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */