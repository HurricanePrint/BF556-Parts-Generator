/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.Certificate;
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
/*     */ public class EncryptionProperties
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3926570647944137843L;
/*     */   protected int encryptionAlgorithm;
/*     */   protected byte[] userPassword;
/*     */   protected byte[] ownerPassword;
/*     */   protected int standardEncryptPermissions;
/*     */   protected Certificate[] publicCertificates;
/*     */   protected int[] publicKeyEncryptPermissions;
/*     */   
/*     */   public EncryptionProperties setStandardEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionAlgorithm) {
/* 104 */     clearEncryption();
/* 105 */     this.userPassword = userPassword;
/* 106 */     if (ownerPassword != null) {
/* 107 */       this.ownerPassword = ownerPassword;
/*     */     } else {
/* 109 */       this.ownerPassword = new byte[16];
/* 110 */       randomBytes(this.ownerPassword);
/*     */     } 
/* 112 */     this.standardEncryptPermissions = permissions;
/* 113 */     this.encryptionAlgorithm = encryptionAlgorithm;
/*     */     
/* 115 */     return this;
/*     */   }
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
/*     */   public EncryptionProperties setPublicKeyEncryption(Certificate[] certs, int[] permissions, int encryptionAlgorithm) {
/* 154 */     clearEncryption();
/* 155 */     this.publicCertificates = certs;
/* 156 */     this.publicKeyEncryptPermissions = permissions;
/* 157 */     this.encryptionAlgorithm = encryptionAlgorithm;
/*     */     
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   boolean isStandardEncryptionUsed() {
/* 163 */     return (this.ownerPassword != null);
/*     */   }
/*     */   
/*     */   boolean isPublicKeyEncryptionUsed() {
/* 167 */     return (this.publicCertificates != null);
/*     */   }
/*     */   
/*     */   private void clearEncryption() {
/* 171 */     this.publicCertificates = null;
/* 172 */     this.publicKeyEncryptPermissions = null;
/* 173 */     this.userPassword = null;
/* 174 */     this.ownerPassword = null;
/*     */   }
/*     */   
/*     */   private static void randomBytes(byte[] bytes) {
/* 178 */     (new SecureRandom()).nextBytes(bytes);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/EncryptionProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */