/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
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
/*     */ public class StandardHandlerUsingStandard128
/*     */   extends StandardHandlerUsingStandard40
/*     */ {
/*     */   private static final long serialVersionUID = 7184848757909055679L;
/*     */   
/*     */   public StandardHandlerUsingStandard128(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, byte[] documentId) {
/*  57 */     super(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata, embeddedFilesOnly, documentId);
/*     */   }
/*     */   
/*     */   public StandardHandlerUsingStandard128(PdfDictionary encryptionDictionary, byte[] password, byte[] documentId, boolean encryptMetadata) {
/*  61 */     super(encryptionDictionary, password, documentId, encryptMetadata);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void calculatePermissions(int permissions) {
/*  66 */     permissions |= 0xFFFFF0C0;
/*  67 */     permissions &= 0xFFFFFFFC;
/*  68 */     this.permissions = permissions;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] computeOwnerKey(byte[] userPad, byte[] ownerPad) {
/*  73 */     byte[] ownerKey = new byte[32];
/*  74 */     byte[] digest = this.md5.digest(ownerPad);
/*  75 */     byte[] mkey = new byte[this.keyLength / 8];
/*     */     
/*  77 */     for (int k = 0; k < 50; k++) {
/*  78 */       this.md5.update(digest, 0, mkey.length);
/*  79 */       System.arraycopy(this.md5.digest(), 0, digest, 0, mkey.length);
/*     */     } 
/*  81 */     System.arraycopy(userPad, 0, ownerKey, 0, 32);
/*  82 */     for (int i = 0; i < 20; i++) {
/*  83 */       for (int j = 0; j < mkey.length; j++)
/*  84 */         mkey[j] = (byte)(digest[j] ^ i); 
/*  85 */       this.arcfour.prepareARCFOURKey(mkey);
/*  86 */       this.arcfour.encryptARCFOUR(ownerKey);
/*     */     } 
/*  88 */     return ownerKey;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void computeGlobalEncryptionKey(byte[] userPad, byte[] ownerKey, boolean encryptMetadata) {
/*  93 */     this.mkey = new byte[this.keyLength / 8];
/*     */ 
/*     */     
/*  96 */     this.md5.reset();
/*  97 */     this.md5.update(userPad);
/*  98 */     this.md5.update(ownerKey);
/*     */     
/* 100 */     byte[] ext = new byte[4];
/* 101 */     ext[0] = (byte)(int)this.permissions;
/* 102 */     ext[1] = (byte)(int)(this.permissions >> 8L);
/* 103 */     ext[2] = (byte)(int)(this.permissions >> 16L);
/* 104 */     ext[3] = (byte)(int)(this.permissions >> 24L);
/* 105 */     this.md5.update(ext, 0, 4);
/* 106 */     if (this.documentId != null)
/* 107 */       this.md5.update(this.documentId); 
/* 108 */     if (!encryptMetadata) {
/* 109 */       this.md5.update(metadataPad);
/*     */     }
/* 111 */     byte[] digest = new byte[this.mkey.length];
/* 112 */     System.arraycopy(this.md5.digest(), 0, digest, 0, this.mkey.length);
/*     */     
/* 114 */     for (int k = 0; k < 50; k++) {
/* 115 */       System.arraycopy(this.md5.digest(digest), 0, digest, 0, this.mkey.length);
/*     */     }
/*     */     
/* 118 */     System.arraycopy(digest, 0, this.mkey, 0, this.mkey.length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] computeUserKey() {
/* 123 */     byte[] userKey = new byte[32];
/* 124 */     this.md5.update(pad);
/* 125 */     byte[] digest = this.md5.digest(this.documentId);
/* 126 */     System.arraycopy(digest, 0, userKey, 0, 16);
/* 127 */     for (int k = 16; k < 32; k++)
/* 128 */       userKey[k] = 0; 
/* 129 */     for (int i = 0; i < 20; i++) {
/* 130 */       for (int j = 0; j < this.mkey.length; j++)
/* 131 */         digest[j] = (byte)(this.mkey[j] ^ i); 
/* 132 */       this.arcfour.prepareARCFOURKey(digest, 0, this.mkey.length);
/* 133 */       this.arcfour.encryptARCFOUR(userKey, 0, 16);
/*     */     } 
/* 135 */     return userKey;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSpecificHandlerDicEntries(PdfDictionary encryptionDictionary, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 140 */     if (encryptMetadata) {
/* 141 */       encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(3));
/* 142 */       encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(2));
/*     */     } else {
/* 144 */       encryptionDictionary.put(PdfName.EncryptMetadata, (PdfObject)PdfBoolean.FALSE);
/* 145 */       encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(4));
/* 146 */       encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(4));
/* 147 */       PdfDictionary stdcf = new PdfDictionary();
/* 148 */       stdcf.put(PdfName.Length, (PdfObject)new PdfNumber(16));
/* 149 */       if (embeddedFilesOnly) {
/* 150 */         stdcf.put(PdfName.AuthEvent, (PdfObject)PdfName.EFOpen);
/* 151 */         encryptionDictionary.put(PdfName.EFF, (PdfObject)PdfName.StdCF);
/* 152 */         encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.Identity);
/* 153 */         encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.Identity);
/*     */       } else {
/* 155 */         stdcf.put(PdfName.AuthEvent, (PdfObject)PdfName.DocOpen);
/* 156 */         encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.StdCF);
/* 157 */         encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.StdCF);
/*     */       } 
/* 159 */       stdcf.put(PdfName.CFM, (PdfObject)PdfName.V2);
/* 160 */       PdfDictionary cf = new PdfDictionary();
/* 161 */       cf.put(PdfName.StdCF, (PdfObject)stdcf);
/* 162 */       encryptionDictionary.put(PdfName.CF, (PdfObject)cf);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidPassword(byte[] uValue, byte[] userKey) {
/* 168 */     return !equalsArray(uValue, userKey, 16);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/StandardHandlerUsingStandard128.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */