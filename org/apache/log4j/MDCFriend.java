/*    */ package org.apache.log4j;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import org.apache.log4j.helpers.ThreadLocalMap;
/*    */ 
/*    */ 
/*    */ public class MDCFriend
/*    */ {
/*    */   public static void fixForJava9() {
/* 11 */     if (MDC.mdc.tlm == null) {
/* 12 */       MDC.mdc.tlm = new ThreadLocalMap();
/* 13 */       MDC.mdc.java1 = false;
/* 14 */       setRemoveMethod(MDC.mdc);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void setRemoveMethod(MDC mdc) {
/*    */     
/* 21 */     try { Method removeMethod = ThreadLocal.class.getMethod("remove", new Class[0]);
/* 22 */       Field removeMethodField = MDC.class.getDeclaredField("removeMethod");
/* 23 */       removeMethodField.setAccessible(true);
/* 24 */       removeMethodField.set(mdc, removeMethod); }
/* 25 */     catch (NoSuchMethodException noSuchMethodException) {  }
/* 26 */     catch (SecurityException securityException) {  }
/* 27 */     catch (NoSuchFieldException noSuchFieldException) {  }
/* 28 */     catch (IllegalArgumentException illegalArgumentException) {  }
/* 29 */     catch (IllegalAccessException illegalAccessException) {}
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/org/apache/log4j/MDCFriend.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */