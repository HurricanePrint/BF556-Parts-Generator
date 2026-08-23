/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.util.SystemUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.crypto.IDecryptor;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingAes128;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingAes256;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingStandard128;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.PubSecHandlerUsingStandard40;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.SecurityHandler;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingAes128;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingAes256;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingStandard128;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.StandardHandlerUsingStandard40;
/*     */ import com.itextpdf.kernel.crypto.securityhandler.StandardSecurityHandler;
/*     */ import com.itextpdf.kernel.security.IExternalDecryptionProcess;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
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
/*     */ public class PdfEncryption
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = -6864863940808467156L;
/*     */   private static final int STANDARD_ENCRYPTION_40 = 2;
/*     */   private static final int STANDARD_ENCRYPTION_128 = 3;
/*     */   private static final int AES_128 = 4;
/*     */   private static final int AES_256 = 5;
/*  83 */   private static long seq = SystemUtil.getTimeBasedSeed();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int cryptoMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Long permissions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean encryptMetadata;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean embeddedFilesOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] documentId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SecurityHandler securityHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionType, byte[] documentId, PdfVersion version) {
/* 131 */     super(new PdfDictionary()); StandardHandlerUsingStandard40 handlerStd40; StandardHandlerUsingStandard128 handlerStd128; StandardHandlerUsingAes128 handlerAes128; StandardHandlerUsingAes256 handlerAes256;
/* 132 */     this.documentId = documentId;
/* 133 */     if (version != null && version.compareTo(PdfVersion.PDF_2_0) >= 0) {
/* 134 */       permissions = fixAccessibilityPermissionPdf20(permissions);
/*     */     }
/* 136 */     int revision = setCryptoMode(encryptionType);
/* 137 */     switch (revision) {
/*     */       case 2:
/* 139 */         handlerStd40 = new StandardHandlerUsingStandard40(getPdfObject(), userPassword, ownerPassword, permissions, this.encryptMetadata, this.embeddedFilesOnly, documentId);
/*     */         
/* 141 */         this.permissions = Long.valueOf(handlerStd40.getPermissions());
/* 142 */         this.securityHandler = (SecurityHandler)handlerStd40;
/*     */         break;
/*     */       case 3:
/* 145 */         handlerStd128 = new StandardHandlerUsingStandard128(getPdfObject(), userPassword, ownerPassword, permissions, this.encryptMetadata, this.embeddedFilesOnly, documentId);
/*     */         
/* 147 */         this.permissions = Long.valueOf(handlerStd128.getPermissions());
/* 148 */         this.securityHandler = (SecurityHandler)handlerStd128;
/*     */         break;
/*     */       case 4:
/* 151 */         handlerAes128 = new StandardHandlerUsingAes128(getPdfObject(), userPassword, ownerPassword, permissions, this.encryptMetadata, this.embeddedFilesOnly, documentId);
/*     */         
/* 153 */         this.permissions = Long.valueOf(handlerAes128.getPermissions());
/* 154 */         this.securityHandler = (SecurityHandler)handlerAes128;
/*     */         break;
/*     */       case 5:
/* 157 */         handlerAes256 = new StandardHandlerUsingAes256(getPdfObject(), userPassword, ownerPassword, permissions, this.encryptMetadata, this.embeddedFilesOnly, version);
/*     */         
/* 159 */         this.permissions = Long.valueOf(handlerAes256.getPermissions());
/* 160 */         this.securityHandler = (SecurityHandler)handlerAes256;
/*     */         break;
/*     */     } 
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
/*     */   public PdfEncryption(Certificate[] certs, int[] permissions, int encryptionType, PdfVersion version) {
/* 201 */     super(new PdfDictionary());
/* 202 */     if (version != null && version.compareTo(PdfVersion.PDF_2_0) >= 0) {
/* 203 */       for (int i = 0; i < permissions.length; i++) {
/* 204 */         permissions[i] = fixAccessibilityPermissionPdf20(permissions[i]);
/*     */       }
/*     */     }
/* 207 */     int revision = setCryptoMode(encryptionType);
/* 208 */     switch (revision) {
/*     */       case 2:
/* 210 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingStandard40(getPdfObject(), certs, permissions, this.encryptMetadata, this.embeddedFilesOnly);
/*     */         break;
/*     */       case 3:
/* 213 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingStandard128(getPdfObject(), certs, permissions, this.encryptMetadata, this.embeddedFilesOnly);
/*     */         break;
/*     */       case 4:
/* 216 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingAes128(getPdfObject(), certs, permissions, this.encryptMetadata, this.embeddedFilesOnly);
/*     */         break;
/*     */       case 5:
/* 219 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingAes256(getPdfObject(), certs, permissions, this.encryptMetadata, this.embeddedFilesOnly);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public PdfEncryption(PdfDictionary pdfDict, byte[] password, byte[] documentId) {
/* 225 */     super(pdfDict); StandardHandlerUsingStandard40 handlerStd40; StandardHandlerUsingStandard128 handlerStd128; StandardHandlerUsingAes128 handlerAes128; StandardHandlerUsingAes256 aes256Handler;
/* 226 */     setForbidRelease();
/* 227 */     this.documentId = documentId;
/*     */     
/* 229 */     int revision = readAndSetCryptoModeForStdHandler(pdfDict);
/* 230 */     switch (revision) {
/*     */       case 2:
/* 232 */         handlerStd40 = new StandardHandlerUsingStandard40(getPdfObject(), password, documentId, this.encryptMetadata);
/* 233 */         this.permissions = Long.valueOf(handlerStd40.getPermissions());
/* 234 */         this.securityHandler = (SecurityHandler)handlerStd40;
/*     */         break;
/*     */       case 3:
/* 237 */         handlerStd128 = new StandardHandlerUsingStandard128(getPdfObject(), password, documentId, this.encryptMetadata);
/* 238 */         this.permissions = Long.valueOf(handlerStd128.getPermissions());
/* 239 */         this.securityHandler = (SecurityHandler)handlerStd128;
/*     */         break;
/*     */       case 4:
/* 242 */         handlerAes128 = new StandardHandlerUsingAes128(getPdfObject(), password, documentId, this.encryptMetadata);
/* 243 */         this.permissions = Long.valueOf(handlerAes128.getPermissions());
/* 244 */         this.securityHandler = (SecurityHandler)handlerAes128;
/*     */         break;
/*     */       case 5:
/* 247 */         aes256Handler = new StandardHandlerUsingAes256(getPdfObject(), password);
/* 248 */         this.permissions = Long.valueOf(aes256Handler.getPermissions());
/* 249 */         this.encryptMetadata = aes256Handler.isEncryptMetadata();
/* 250 */         this.securityHandler = (SecurityHandler)aes256Handler;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfEncryption(PdfDictionary pdfDict, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess) {
/* 257 */     super(pdfDict);
/* 258 */     setForbidRelease();
/* 259 */     int revision = readAndSetCryptoModeForPubSecHandler(pdfDict);
/* 260 */     switch (revision) {
/*     */       case 2:
/* 262 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingStandard40(getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
/*     */         break;
/*     */       
/*     */       case 3:
/* 266 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingStandard128(getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
/*     */         break;
/*     */       
/*     */       case 4:
/* 270 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingAes128(getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
/*     */         break;
/*     */       
/*     */       case 5:
/* 274 */         this.securityHandler = (SecurityHandler)new PubSecHandlerUsingAes256(getPdfObject(), certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, this.encryptMetadata);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] generateNewDocumentId() {
/*     */     MessageDigest md5;
/*     */     try {
/* 283 */       md5 = MessageDigest.getInstance("MD5");
/* 284 */     } catch (Exception e) {
/* 285 */       throw new PdfException("PdfEncryption exception.", e);
/*     */     } 
/* 287 */     long time = SystemUtil.getTimeBasedSeed();
/* 288 */     long mem = SystemUtil.getFreeMemory();
/* 289 */     String s = time + "+" + mem + "+" + seq++;
/*     */     
/* 291 */     return md5.digest(s.getBytes(StandardCharsets.ISO_8859_1));
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
/*     */   public static PdfObject createInfoId(byte[] id, boolean modified) {
/* 304 */     if (modified) {
/* 305 */       return createInfoId(id, generateNewDocumentId());
/*     */     }
/* 307 */     return createInfoId(id, id);
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
/*     */   public static PdfObject createInfoId(byte[] firstId, byte[] secondId) {
/* 321 */     if (firstId.length < 16) {
/* 322 */       firstId = padByteArrayTo16(firstId);
/*     */     }
/*     */     
/* 325 */     if (secondId.length < 16) {
/* 326 */       secondId = padByteArrayTo16(secondId);
/*     */     }
/*     */     
/* 329 */     ByteBuffer buf = new ByteBuffer(90);
/* 330 */     buf.append(91).append(60);
/*     */     int k;
/* 332 */     for (k = 0; k < firstId.length; k++)
/* 333 */       buf.appendHex(firstId[k]); 
/* 334 */     buf.append(62).append(60);
/* 335 */     for (k = 0; k < secondId.length; k++)
/* 336 */       buf.appendHex(secondId[k]); 
/* 337 */     buf.append(62).append(93);
/*     */     
/* 339 */     return new PdfLiteral(buf.toByteArray());
/*     */   }
/*     */   
/*     */   private static byte[] padByteArrayTo16(byte[] documentId) {
/* 343 */     byte[] paddingBytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
/*     */     
/* 345 */     System.arraycopy(documentId, 0, paddingBytes, 0, documentId.length);
/*     */     
/* 347 */     return paddingBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getPermissions() {
/* 358 */     return this.permissions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCryptoMode() {
/* 367 */     return this.cryptoMode;
/*     */   }
/*     */   
/*     */   public boolean isMetadataEncrypted() {
/* 371 */     return this.encryptMetadata;
/*     */   }
/*     */   
/*     */   public boolean isEmbeddedFilesOnly() {
/* 375 */     return this.embeddedFilesOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDocumentId() {
/* 382 */     return this.documentId;
/*     */   }
/*     */   
/*     */   public void setHashKeyForNextObject(int objNumber, int objGeneration) {
/* 386 */     this.securityHandler.setHashKeyForNextObject(objNumber, objGeneration);
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStreamEncryption getEncryptionStream(OutputStream os) {
/* 391 */     return this.securityHandler.getEncryptionStream(os);
/*     */   }
/*     */   
/*     */   public byte[] encryptByteArray(byte[] b) {
/* 395 */     ByteArrayOutputStream ba = new ByteArrayOutputStream();
/* 396 */     OutputStreamEncryption ose = getEncryptionStream(ba);
/*     */     try {
/* 398 */       ose.write(b);
/* 399 */     } catch (IOException e) {
/* 400 */       throw new PdfException("PdfEncryption exception.", e);
/*     */     } 
/* 402 */     ose.finish();
/* 403 */     return ba.toByteArray();
/*     */   }
/*     */   
/*     */   public byte[] decryptByteArray(byte[] b) {
/*     */     try {
/* 408 */       ByteArrayOutputStream ba = new ByteArrayOutputStream();
/* 409 */       IDecryptor dec = this.securityHandler.getDecryptor();
/* 410 */       byte[] b2 = dec.update(b, 0, b.length);
/* 411 */       if (b2 != null)
/* 412 */         ba.write(b2); 
/* 413 */       b2 = dec.finish();
/* 414 */       if (b2 != null)
/* 415 */         ba.write(b2); 
/* 416 */       return ba.toByteArray();
/* 417 */     } catch (IOException e) {
/* 418 */       throw new PdfException("PdfEncryption exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isOpenedWithFullPermission() {
/* 423 */     if (this.securityHandler instanceof com.itextpdf.kernel.crypto.securityhandler.PubKeySecurityHandler)
/* 424 */       return true; 
/* 425 */     if (this.securityHandler instanceof StandardSecurityHandler) {
/* 426 */       return ((StandardSecurityHandler)this.securityHandler).isUsedOwnerPassword();
/*     */     }
/* 428 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] computeUserPassword(byte[] ownerPassword) {
/* 437 */     byte[] userPassword = null;
/* 438 */     if (this.securityHandler instanceof StandardHandlerUsingStandard40) {
/* 439 */       userPassword = ((StandardHandlerUsingStandard40)this.securityHandler).computeUserPassword(ownerPassword, getPdfObject());
/*     */     }
/* 441 */     return userPassword;
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
/*     */   public void flush() {
/* 453 */     super.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 458 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setKeyLength(int keyLength) {
/* 463 */     if (keyLength != 40) {
/* 464 */       getPdfObject().put(PdfName.Length, new PdfNumber(keyLength));
/*     */     }
/*     */   }
/*     */   
/*     */   private int setCryptoMode(int mode) {
/* 469 */     return setCryptoMode(mode, 0);
/*     */   }
/*     */   
/*     */   private int setCryptoMode(int mode, int length) {
/*     */     int revision;
/* 474 */     this.cryptoMode = mode;
/* 475 */     this.encryptMetadata = ((mode & 0x8) != 8);
/* 476 */     this.embeddedFilesOnly = ((mode & 0x18) == 24);
/* 477 */     mode &= 0x7;
/* 478 */     switch (mode) {
/*     */       case 0:
/* 480 */         this.encryptMetadata = true;
/* 481 */         this.embeddedFilesOnly = false;
/* 482 */         setKeyLength(40);
/* 483 */         revision = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 505 */         return revision;case 1: this.embeddedFilesOnly = false; if (length > 0) { setKeyLength(length); } else { setKeyLength(128); }  revision = 3; return revision;case 2: setKeyLength(128); revision = 4; return revision;case 3: setKeyLength(256); revision = 5; return revision;
/*     */     }  throw new PdfException("No valid encryption mode."); } private int readAndSetCryptoModeForStdHandler(PdfDictionary encDict) { int cryptoMode;
/*     */     PdfNumber lengthValue;
/*     */     PdfDictionary dic;
/*     */     PdfBoolean em, em5;
/* 510 */     int length = 0;
/*     */     
/* 512 */     PdfNumber rValue = encDict.getAsNumber(PdfName.R);
/* 513 */     if (rValue == null)
/* 514 */       throw new PdfException("Illegal R value."); 
/* 515 */     int revision = rValue.intValue();
/* 516 */     switch (revision) {
/*     */       case 2:
/* 518 */         cryptoMode = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 560 */         revision = setCryptoMode(cryptoMode, length);
/* 561 */         return revision;case 3: lengthValue = encDict.getAsNumber(PdfName.Length); if (lengthValue == null) throw new PdfException("Illegal length value.");  length = lengthValue.intValue(); if (length > 128 || length < 40 || length % 8 != 0) throw new PdfException("Illegal length value.");  cryptoMode = 1; revision = setCryptoMode(cryptoMode, length); return revision;case 4: dic = (PdfDictionary)encDict.get(PdfName.CF); if (dic == null) throw new PdfException("/CF not found (encryption)");  dic = (PdfDictionary)dic.get(PdfName.StdCF); if (dic == null) throw new PdfException("/StdCF not found (encryption)");  if (PdfName.V2.equals(dic.get(PdfName.CFM))) { cryptoMode = 1; } else if (PdfName.AESV2.equals(dic.get(PdfName.CFM))) { cryptoMode = 2; } else { throw new PdfException("No compatible encryption found."); }  em = encDict.getAsBoolean(PdfName.EncryptMetadata); if (em != null && !em.getValue()) cryptoMode |= 0x8;  revision = setCryptoMode(cryptoMode, length); return revision;case 5: case 6: cryptoMode = 3; em5 = encDict.getAsBoolean(PdfName.EncryptMetadata); if (em5 != null && !em5.getValue()) cryptoMode |= 0x8;  revision = setCryptoMode(cryptoMode, length); return revision;
/*     */     }  throw (new PdfException("Unknown encryption type R == {0}.")).setMessageParams(new Object[] { rValue }); } private int readAndSetCryptoModeForPubSecHandler(PdfDictionary encDict) { int cryptoMode;
/*     */     PdfNumber lengthValue;
/*     */     PdfDictionary dic;
/*     */     PdfBoolean em;
/* 566 */     int length = 0;
/*     */     
/* 568 */     PdfNumber vValue = encDict.getAsNumber(PdfName.V);
/* 569 */     if (vValue == null)
/* 570 */       throw new PdfException("Illegal V value."); 
/* 571 */     int v = vValue.intValue();
/* 572 */     switch (v) {
/*     */       case 1:
/* 574 */         cryptoMode = 0;
/* 575 */         length = 40;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 614 */         return setCryptoMode(cryptoMode, length);case 2: lengthValue = encDict.getAsNumber(PdfName.Length); if (lengthValue == null) throw new PdfException("Illegal length value.");  length = lengthValue.intValue(); if (length > 128 || length < 40 || length % 8 != 0) throw new PdfException("Illegal length value.");  cryptoMode = 1; return setCryptoMode(cryptoMode, length);case 4: case 5: dic = encDict.getAsDictionary(PdfName.CF); if (dic == null) throw new PdfException("/CF not found (encryption)");  dic = (PdfDictionary)dic.get(PdfName.DefaultCryptFilter); if (dic == null) throw new PdfException("/DefaultCryptFilter not found (encryption).");  if (PdfName.V2.equals(dic.get(PdfName.CFM))) { cryptoMode = 1; length = 128; } else if (PdfName.AESV2.equals(dic.get(PdfName.CFM))) { cryptoMode = 2; length = 128; } else if (PdfName.AESV3.equals(dic.get(PdfName.CFM))) { cryptoMode = 3; length = 256; } else { throw new PdfException("No compatible encryption found."); }  em = dic.getAsBoolean(PdfName.EncryptMetadata); if (em != null && !em.getValue()) cryptoMode |= 0x8;  return setCryptoMode(cryptoMode, length);
/*     */     } 
/*     */     throw new PdfException("Unknown encryption type V == {0}.", vValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fixAccessibilityPermissionPdf20(int permissions) {
/* 624 */     return permissions | 0x200;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfEncryption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */