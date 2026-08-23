/*     */ package com.itextpdf.test.signutils;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Pkcs12FileHelper
/*     */ {
/*     */   public static Certificate[] readFirstChain(String p12FileName, char[] ksPass) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
/*  62 */     Certificate[] certChain = null;
/*     */     
/*  64 */     KeyStore p12 = KeyStore.getInstance("pkcs12");
/*  65 */     p12.load(new FileInputStream(p12FileName), ksPass);
/*     */     
/*  67 */     Enumeration<String> aliases = p12.aliases();
/*  68 */     while (aliases.hasMoreElements()) {
/*  69 */       String alias = aliases.nextElement();
/*  70 */       if (p12.isKeyEntry(alias)) {
/*  71 */         certChain = p12.getCertificateChain(alias);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  76 */     return certChain;
/*     */   }
/*     */   
/*     */   public static PrivateKey readFirstKey(String p12FileName, char[] ksPass, char[] keyPass) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
/*  80 */     PrivateKey pk = null;
/*     */     
/*  82 */     KeyStore p12 = KeyStore.getInstance("pkcs12");
/*  83 */     p12.load(new FileInputStream(p12FileName), ksPass);
/*     */     
/*  85 */     Enumeration<String> aliases = p12.aliases();
/*  86 */     while (aliases.hasMoreElements()) {
/*  87 */       String alias = aliases.nextElement();
/*  88 */       if (p12.isKeyEntry(alias)) {
/*  89 */         pk = (PrivateKey)p12.getKey(alias, keyPass);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  94 */     return pk;
/*     */   }
/*     */   
/*     */   public static KeyStore initStore(String p12FileName, char[] ksPass) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, NoSuchProviderException {
/*  98 */     KeyStore p12 = KeyStore.getInstance("PKCS12", "BC");
/*  99 */     p12.load(new FileInputStream(p12FileName), ksPass);
/* 100 */     return p12;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/test/signutils/Pkcs12FileHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */