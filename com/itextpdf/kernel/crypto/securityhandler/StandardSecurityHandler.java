/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfEncryption;
/*     */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
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
/*     */ public abstract class StandardSecurityHandler
/*     */   extends SecurityHandler
/*     */ {
/*     */   protected static final int PERMS_MASK_1_FOR_REVISION_2 = -64;
/*     */   protected static final int PERMS_MASK_1_FOR_REVISION_3_OR_GREATER = -3904;
/*     */   protected static final int PERMS_MASK_2 = -4;
/*     */   private static final long serialVersionUID = 5414978568831015690L;
/*     */   protected long permissions;
/*     */   protected boolean usedOwnerPassword = true;
/*     */   
/*     */   public long getPermissions() {
/*  66 */     return this.permissions;
/*     */   }
/*     */   
/*     */   public boolean isUsedOwnerPassword() {
/*  70 */     return this.usedOwnerPassword;
/*     */   }
/*     */   
/*     */   protected void setStandardHandlerDicEntries(PdfDictionary encryptionDictionary, byte[] userKey, byte[] ownerKey) {
/*  74 */     encryptionDictionary.put(PdfName.Filter, (PdfObject)PdfName.Standard);
/*  75 */     encryptionDictionary.put(PdfName.O, (PdfObject)new PdfLiteral(StreamUtil.createEscapedString(ownerKey)));
/*  76 */     encryptionDictionary.put(PdfName.U, (PdfObject)new PdfLiteral(StreamUtil.createEscapedString(userKey)));
/*  77 */     encryptionDictionary.put(PdfName.P, (PdfObject)new PdfNumber(this.permissions));
/*     */   }
/*     */   
/*     */   protected byte[] generateOwnerPasswordIfNullOrEmpty(byte[] ownerPassword) {
/*  81 */     if (ownerPassword == null || ownerPassword.length == 0) {
/*  82 */       ownerPassword = this.md5.digest(PdfEncryption.generateNewDocumentId());
/*     */     }
/*  84 */     return ownerPassword;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getIsoBytes(PdfString string) {
/*  94 */     return ByteUtils.getIsoBytes(string.getValue());
/*     */   }
/*     */   
/*     */   protected static boolean equalsArray(byte[] ar1, byte[] ar2, int size) {
/*  98 */     for (int k = 0; k < size; k++) {
/*  99 */       if (ar1[k] != ar2[k]) {
/* 100 */         return false;
/*     */       }
/*     */     } 
/* 103 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/StandardSecurityHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */