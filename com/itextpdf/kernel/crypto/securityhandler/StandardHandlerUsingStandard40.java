/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.kernel.crypto.ARCFOUREncryption;
/*     */ import com.itextpdf.kernel.crypto.BadPasswordException;
/*     */ import com.itextpdf.kernel.crypto.IDecryptor;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamStandardEncryption;
/*     */ import com.itextpdf.kernel.crypto.StandardDecryptor;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import java.io.OutputStream;
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
/*     */ public class StandardHandlerUsingStandard40
/*     */   extends StandardSecurityHandler
/*     */ {
/*  59 */   protected static final byte[] pad = new byte[] { 40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 78, 86, -1, -6, 1, 8, 46, 46, 0, -74, -48, 104, 62, Byte.MIN_VALUE, 47, 12, -87, -2, 100, 83, 105, 122 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected static final byte[] metadataPad = new byte[] { -1, -1, -1, -1 };
/*     */   
/*     */   private static final long serialVersionUID = -7951837491441953183L;
/*     */   
/*     */   protected byte[] documentId;
/*     */   
/*     */   protected int keyLength;
/*     */   
/*  74 */   protected ARCFOUREncryption arcfour = new ARCFOUREncryption();
/*     */ 
/*     */   
/*     */   public StandardHandlerUsingStandard40(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId) {
/*  78 */     initKeyAndFillDictionary(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata, embeddedFilesOnly, documentId);
/*     */   }
/*     */   
/*     */   public StandardHandlerUsingStandard40(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId, boolean encryptMetadata) {
/*  82 */     initKeyAndReadDictionary(encryptionDictionary, password, documentId, encryptMetadata);
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStreamEncryption getEncryptionStream(OutputStream os) {
/*  87 */     return (OutputStreamEncryption)new OutputStreamStandardEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDecryptor getDecryptor() {
/*  92 */     return (IDecryptor)new StandardDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */   
/*     */   public byte[] computeUserPassword(byte[] ownerPassword, PdfDictionary encryptionDictionary) {
/*  96 */     byte[] ownerKey = getIsoBytes(encryptionDictionary.getAsString(PdfName.O));
/*  97 */     byte[] userPad = computeOwnerKey(ownerKey, padPassword(ownerPassword));
/*  98 */     for (int i = 0; i < userPad.length; ) {
/*  99 */       boolean match = true;
/* 100 */       for (int j = 0; j < userPad.length - i; j++) {
/* 101 */         if (userPad[i + j] != pad[j]) {
/* 102 */           match = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 106 */       if (!match) { i++; continue; }
/* 107 */        byte[] userPassword = new byte[i];
/* 108 */       System.arraycopy(userPad, 0, userPassword, 0, i);
/* 109 */       return userPassword;
/*     */     } 
/* 111 */     return userPad;
/*     */   }
/*     */   
/*     */   protected void calculatePermissions(int permissions) {
/* 115 */     permissions |= 0xFFFFFFC0;
/* 116 */     permissions &= 0xFFFFFFFC;
/* 117 */     this.permissions = permissions;
/*     */   }
/*     */   
/*     */   protected byte[] computeOwnerKey(byte[] userPad, byte[] ownerPad) {
/* 121 */     byte[] ownerKey = new byte[32];
/* 122 */     byte[] digest = this.md5.digest(ownerPad);
/* 123 */     this.arcfour.prepareARCFOURKey(digest, 0, 5);
/* 124 */     this.arcfour.encryptARCFOUR(userPad, ownerKey);
/* 125 */     return ownerKey;
/*     */   }
/*     */   
/*     */   protected void computeGlobalEncryptionKey(byte[] userPad, byte[] ownerKey, boolean encryptMetadata) {
/* 129 */     this.mkey = new byte[this.keyLength / 8];
/*     */ 
/*     */     
/* 132 */     this.md5.reset();
/* 133 */     this.md5.update(userPad);
/* 134 */     this.md5.update(ownerKey);
/*     */     
/* 136 */     byte[] ext = new byte[4];
/* 137 */     ext[0] = (byte)(int)this.permissions;
/* 138 */     ext[1] = (byte)(int)(this.permissions >> 8L);
/* 139 */     ext[2] = (byte)(int)(this.permissions >> 16L);
/* 140 */     ext[3] = (byte)(int)(this.permissions >> 24L);
/* 141 */     this.md5.update(ext, 0, 4);
/* 142 */     if (this.documentId != null)
/* 143 */       this.md5.update(this.documentId); 
/* 144 */     if (!encryptMetadata) {
/* 145 */       this.md5.update(metadataPad);
/*     */     }
/* 147 */     byte[] digest = new byte[this.mkey.length];
/* 148 */     System.arraycopy(this.md5.digest(), 0, digest, 0, this.mkey.length);
/* 149 */     System.arraycopy(digest, 0, this.mkey, 0, this.mkey.length);
/*     */   }
/*     */   
/*     */   protected byte[] computeUserKey() {
/* 153 */     byte[] userKey = new byte[32];
/* 154 */     this.arcfour.prepareARCFOURKey(this.mkey);
/* 155 */     this.arcfour.encryptARCFOUR(pad, userKey);
/* 156 */     return userKey;
/*     */   }
/*     */   
/*     */   protected void setSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 160 */     encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(2));
/* 161 */     encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidPassword(byte[] uValue, byte[] userKey) {
/* 166 */     return !equalsArray(uValue, userKey, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initKeyAndFillDictionary(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId) {
/* 171 */     ownerPassword = generateOwnerPasswordIfNullOrEmpty(ownerPassword);
/* 172 */     calculatePermissions(permissions);
/*     */     
/* 174 */     this.documentId = documentId;
/* 175 */     this.keyLength = getKeyLength(encryptionDictionary);
/*     */ 
/*     */     
/* 178 */     byte[] userPad = padPassword(userPassword);
/* 179 */     byte[] ownerPad = padPassword(ownerPassword);
/*     */     
/* 181 */     byte[] ownerKey = computeOwnerKey(userPad, ownerPad);
/* 182 */     computeGlobalEncryptionKey(userPad, ownerKey, encryptMetadata);
/* 183 */     byte[] userKey = computeUserKey();
/*     */     
/* 185 */     setStandardHandlerDicEntries(encryptionDictionary, userKey, ownerKey);
/* 186 */     setSpecificHandlerDicEntries(encryptionDictionary, encryptMetadata, embeddedFilesOnly);
/*     */   }
/*     */   
/*     */   private void initKeyAndReadDictionary(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId, boolean encryptMetadata) {
/* 190 */     byte[] uValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.U));
/* 191 */     byte[] oValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.O));
/*     */     
/* 193 */     PdfNumber pValue = (PdfNumber)encryptionDictionary.get(PdfName.P);
/* 194 */     this.permissions = pValue.longValue();
/*     */     
/* 196 */     this.documentId = documentId;
/* 197 */     this.keyLength = getKeyLength(encryptionDictionary);
/* 198 */     byte[] paddedPassword = padPassword(password);
/* 199 */     checkPassword(encryptMetadata, uValue, oValue, paddedPassword);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkPassword(boolean encryptMetadata, byte[] uValue, byte[] oValue, byte[] paddedPassword) {
/* 205 */     byte[] userPad = computeOwnerKey(oValue, paddedPassword);
/* 206 */     computeGlobalEncryptionKey(userPad, oValue, encryptMetadata);
/* 207 */     byte[] userKey = computeUserKey();
/*     */     
/* 209 */     if (isValidPassword(uValue, userKey)) {
/*     */       
/* 211 */       computeGlobalEncryptionKey(paddedPassword, oValue, encryptMetadata);
/* 212 */       userKey = computeUserKey();
/*     */       
/* 214 */       if (isValidPassword(uValue, userKey)) {
/* 215 */         throw new BadPasswordException("Bad user password. Password is not provided or wrong password provided. Correct password should be passed to PdfReader constructor with properties. See ReaderProperties#setPassword() method.");
/*     */       }
/* 217 */       this.usedOwnerPassword = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] padPassword(byte[] password) {
/* 223 */     byte[] userPad = new byte[32];
/* 224 */     if (password == null) {
/* 225 */       System.arraycopy(pad, 0, userPad, 0, 32);
/*     */     } else {
/* 227 */       System.arraycopy(password, 0, userPad, 0, Math.min(password.length, 32));
/*     */       
/* 229 */       if (password.length < 32) {
/* 230 */         System.arraycopy(pad, 0, userPad, password.length, 32 - password.length);
/*     */       }
/*     */     } 
/*     */     
/* 234 */     return userPad;
/*     */   }
/*     */   
/*     */   private int getKeyLength(PdfDictionary encryptionDict) {
/* 238 */     Integer keyLength = encryptionDict.getAsInt(PdfName.Length);
/* 239 */     return (keyLength != null) ? keyLength.intValue() : 40;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/StandardHandlerUsingStandard40.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */