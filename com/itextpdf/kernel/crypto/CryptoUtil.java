/*    */ package com.itextpdf.kernel.crypto;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.KeyStore;
/*    */ import java.security.PrivateKey;
/*    */ import java.security.cert.Certificate;
/*    */ import java.security.cert.CertificateException;
/*    */ import java.security.cert.CertificateFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CryptoUtil
/*    */ {
/*    */   public static Certificate readPublicCertificate(InputStream is) throws CertificateException {
/* 60 */     return CertificateFactory.getInstance("X.509").generateCertificate(is);
/*    */   }
/*    */   
/*    */   public static PrivateKey readPrivateKeyFromPKCS12KeyStore(InputStream keyStore, String pkAlias, char[] pkPassword) throws GeneralSecurityException, IOException {
/* 64 */     KeyStore keystore = KeyStore.getInstance("PKCS12");
/* 65 */     keystore.load(keyStore, pkPassword);
/* 66 */     return (PrivateKey)keystore.getKey(pkAlias, pkPassword);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/CryptoUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */