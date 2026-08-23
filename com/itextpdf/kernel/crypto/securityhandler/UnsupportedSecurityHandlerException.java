/*    */ package com.itextpdf.kernel.crypto.securityhandler;
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
/*    */ public class UnsupportedSecurityHandlerException
/*    */   extends PdfException
/*    */ {
/*    */   public static final String UnsupportedSecurityHandler = "Failed to open the document. Security handler {0} is not supported";
/*    */   
/*    */   public UnsupportedSecurityHandlerException(String message) {
/* 52 */     super(message);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/UnsupportedSecurityHandlerException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */