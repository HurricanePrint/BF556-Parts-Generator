/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import com.itextpdf.kernel.PdfException;
/*    */ import java.security.cert.CRL;
/*    */ import java.security.cert.X509CRL;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
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
/*    */ public class CrlClientOffline
/*    */   implements ICrlClient
/*    */ {
/* 65 */   private List<byte[]> crls = (List)new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CrlClientOffline(byte[] crlEncoded) {
/* 74 */     this.crls.add(crlEncoded);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CrlClientOffline(CRL crl) {
/*    */     try {
/* 85 */       this.crls.add(((X509CRL)crl).getEncoded());
/* 86 */     } catch (Exception ex) {
/* 87 */       throw new PdfException(ex);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<byte[]> getEncoded(X509Certificate checkCert, String url) {
/* 97 */     return (Collection<byte[]>)this.crls;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/CrlClientOffline.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */