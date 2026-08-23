/*    */ package com.itextpdf.kernel.log;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class SystemOutCounter
/*    */   implements ICounter
/*    */ {
/*    */   protected String name;
/*    */   
/*    */   public SystemOutCounter(String name) {
/* 62 */     this.name = name;
/*    */   }
/*    */   
/*    */   public SystemOutCounter() {
/* 66 */     this("iText");
/*    */   }
/*    */   
/*    */   public SystemOutCounter(Class<?> cls) {
/* 70 */     this(cls.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDocumentRead(long size) {
/* 76 */     System.out.println(MessageFormatUtil.format("[{0}] {1} bytes read", new Object[] { this.name, Long.valueOf(size) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDocumentWritten(long size) {
/* 81 */     System.out.println(MessageFormatUtil.format("[{0}] {1} bytes written", new Object[] { this.name, Long.valueOf(size) }));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/log/SystemOutCounter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */