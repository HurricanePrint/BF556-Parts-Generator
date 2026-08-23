/*    */ package com.itextpdf.kernel.crypto;
/*    */ 
/*    */ import com.itextpdf.kernel.PdfException;
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
/*    */ 
/*    */ 
/*    */ public class BadPasswordException
/*    */   extends PdfException
/*    */ {
/*    */   private static final long serialVersionUID = -2278753672963132724L;
/*    */   public static final String PdfReaderNotOpenedWithOwnerPassword = "PdfReader is not opened with owner password";
/*    */   
/*    */   public BadPasswordException(String message, Throwable cause) {
/* 64 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BadPasswordException(String message) {
/* 73 */     super(message);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/BadPasswordException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */