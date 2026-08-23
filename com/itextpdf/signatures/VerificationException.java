/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.cert.Certificate;
/*    */ import java.security.cert.X509Certificate;
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
/*    */ public class VerificationException
/*    */   extends GeneralSecurityException
/*    */ {
/*    */   private static final long serialVersionUID = 2978604513926438256L;
/*    */   
/*    */   public VerificationException(Certificate cert, String message) {
/* 62 */     super(MessageFormatUtil.format("Certificate {0} failed: {1}", new Object[] { (cert == null) ? "Unknown" : ((X509Certificate)cert).getSubjectDN().getName(), message }));
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/VerificationException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */