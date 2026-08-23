/*    */ package com.itextpdf.io.util;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.GregorianCalendar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DateTimeUtil
/*    */ {
/*    */   public static double getUtcMillisFromEpoch(Calendar calendar) {
/* 59 */     if (calendar == null) {
/* 60 */       calendar = new GregorianCalendar();
/*    */     }
/* 62 */     return calendar.getTimeInMillis();
/*    */   }
/*    */   
/*    */   public static Calendar getCurrentTimeCalendar() {
/* 66 */     return new GregorianCalendar();
/*    */   }
/*    */   
/*    */   public static Date getCurrentTimeDate() {
/* 70 */     return new Date();
/*    */   }
/*    */   
/*    */   public static Calendar addDaysToCalendar(Calendar calendar, int days) {
/* 74 */     calendar.add(6, days);
/* 75 */     return calendar;
/*    */   }
/*    */   
/*    */   public static Date addDaysToDate(Date date, int days) {
/* 79 */     Calendar cal = Calendar.getInstance();
/* 80 */     cal.setTime(date);
/* 81 */     cal.add(6, days);
/* 82 */     return cal.getTime();
/*    */   }
/*    */   
/*    */   public static Date parseSimpleFormat(String date, String format) {
/*    */     try {
/* 87 */       return (new SimpleDateFormat(format)).parse(date);
/* 88 */     } catch (ParseException e) {
/* 89 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/DateTimeUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */