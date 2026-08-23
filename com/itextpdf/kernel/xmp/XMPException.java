/*    */ package com.itextpdf.kernel.xmp;
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
/*    */ public class XMPException
/*    */   extends Exception
/*    */ {
/*    */   private int errorCode;
/*    */   
/*    */   public XMPException(String message, int errorCode) {
/* 51 */     super(message);
/* 52 */     this.errorCode = errorCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMPException(String message, int errorCode, Throwable t) {
/* 64 */     super(message, t);
/* 65 */     this.errorCode = errorCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getErrorCode() {
/* 74 */     return this.errorCode;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/XMPException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */