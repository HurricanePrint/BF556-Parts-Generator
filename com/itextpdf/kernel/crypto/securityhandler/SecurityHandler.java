/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.crypto.IDecryptor;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
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
/*     */ public abstract class SecurityHandler
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7980424575363686173L;
/*  61 */   protected byte[] mkey = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] nextObjectKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int nextObjectKeySize;
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient MessageDigest md5;
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected byte[] extra = new byte[5];
/*     */   
/*     */   protected SecurityHandler() {
/*  82 */     safeInitMessageDigest();
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
/*     */   public void setHashKeyForNextObject(int objNumber, int objGeneration) {
/*  94 */     this.md5.reset();
/*  95 */     this.extra[0] = (byte)objNumber;
/*  96 */     this.extra[1] = (byte)(objNumber >> 8);
/*  97 */     this.extra[2] = (byte)(objNumber >> 16);
/*  98 */     this.extra[3] = (byte)objGeneration;
/*  99 */     this.extra[4] = (byte)(objGeneration >> 8);
/* 100 */     this.md5.update(this.mkey);
/* 101 */     this.md5.update(this.extra);
/* 102 */     this.nextObjectKey = this.md5.digest();
/* 103 */     this.nextObjectKeySize = this.mkey.length + 5;
/* 104 */     if (this.nextObjectKeySize > 16) {
/* 105 */       this.nextObjectKeySize = 16;
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract OutputStreamEncryption getEncryptionStream(OutputStream paramOutputStream);
/*     */   
/*     */   public abstract IDecryptor getDecryptor();
/*     */   
/*     */   private void safeInitMessageDigest() {
/*     */     try {
/* 115 */       this.md5 = MessageDigest.getInstance("MD5");
/* 116 */     } catch (Exception e) {
/* 117 */       throw new PdfException("PdfEncryption exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 122 */     in.defaultReadObject();
/* 123 */     safeInitMessageDigest();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/SecurityHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */