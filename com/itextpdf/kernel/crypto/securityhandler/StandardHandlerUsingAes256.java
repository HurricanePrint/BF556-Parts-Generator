/*     */ package com.itextpdf.kernel.crypto.securityhandler;
/*     */ 
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.crypto.AESCipherCBCnoPad;
/*     */ import com.itextpdf.kernel.crypto.AesDecryptor;
/*     */ import com.itextpdf.kernel.crypto.BadPasswordException;
/*     */ import com.itextpdf.kernel.crypto.IDecryptor;
/*     */ import com.itextpdf.kernel.crypto.IVGenerator;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamAesEncryption;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardHandlerUsingAes256
/*     */   extends StandardSecurityHandler
/*     */ {
/*     */   private static final long serialVersionUID = -8365943606887257386L;
/*     */   private static final int VALIDATION_SALT_OFFSET = 32;
/*     */   private static final int KEY_SALT_OFFSET = 40;
/*     */   private static final int SALT_LENGTH = 8;
/*     */   private boolean isPdf2;
/*     */   protected boolean encryptMetadata;
/*     */   
/*     */   public StandardHandlerUsingAes256(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly, PdfVersion version) {
/*  84 */     this.isPdf2 = (version != null && version.compareTo(PdfVersion.PDF_2_0) >= 0);
/*  85 */     initKeyAndFillDictionary(encryptionDictionary, userPassword, ownerPassword, permissions, encryptMetadata, embeddedFilesOnly);
/*     */   }
/*     */   
/*     */   public StandardHandlerUsingAes256(PdfDictionary encryptionDictionary, byte[] password) {
/*  89 */     initKeyAndReadDictionary(encryptionDictionary, password);
/*     */   }
/*     */   
/*     */   public boolean isEncryptMetadata() {
/*  93 */     return this.encryptMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHashKeyForNextObject(int objNumber, int objGeneration) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStreamEncryption getEncryptionStream(OutputStream os) {
/* 103 */     return (OutputStreamEncryption)new OutputStreamAesEncryption(os, this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDecryptor getDecryptor() {
/* 108 */     return (IDecryptor)new AesDecryptor(this.nextObjectKey, 0, this.nextObjectKeySize);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initKeyAndFillDictionary(PdfDictionary encryptionDictionary, byte[] userPassword, byte[] ownerPassword, int permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 113 */     ownerPassword = generateOwnerPasswordIfNullOrEmpty(ownerPassword);
/* 114 */     permissions |= 0xFFFFF0C0;
/* 115 */     permissions &= 0xFFFFFFFC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 125 */       if (userPassword == null) {
/* 126 */         userPassword = new byte[0];
/* 127 */       } else if (userPassword.length > 127) {
/* 128 */         userPassword = Arrays.copyOf(userPassword, 127);
/*     */       } 
/* 130 */       if (ownerPassword.length > 127) {
/* 131 */         ownerPassword = Arrays.copyOf(ownerPassword, 127);
/*     */       }
/*     */ 
/*     */       
/* 135 */       byte[] userValAndKeySalt = IVGenerator.getIV(16);
/* 136 */       byte[] ownerValAndKeySalt = IVGenerator.getIV(16);
/*     */       
/* 138 */       this.nextObjectKey = IVGenerator.getIV(32);
/* 139 */       this.nextObjectKeySize = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       byte[] hash = computeHash(userPassword, userValAndKeySalt, 0, 8);
/* 145 */       byte[] userKey = Arrays.copyOf(hash, 48);
/* 146 */       System.arraycopy(userValAndKeySalt, 0, userKey, 32, 16);
/*     */ 
/*     */       
/* 149 */       hash = computeHash(userPassword, userValAndKeySalt, 8, 8);
/* 150 */       AESCipherCBCnoPad ac = new AESCipherCBCnoPad(true, hash);
/* 151 */       byte[] ueKey = ac.processBlock(this.nextObjectKey, 0, this.nextObjectKey.length);
/*     */ 
/*     */ 
/*     */       
/* 155 */       hash = computeHash(ownerPassword, ownerValAndKeySalt, 0, 8, userKey);
/* 156 */       byte[] ownerKey = Arrays.copyOf(hash, 48);
/* 157 */       System.arraycopy(ownerValAndKeySalt, 0, ownerKey, 32, 16);
/*     */ 
/*     */       
/* 160 */       hash = computeHash(ownerPassword, ownerValAndKeySalt, 8, 8, userKey);
/* 161 */       ac = new AESCipherCBCnoPad(true, hash);
/* 162 */       byte[] oeKey = ac.processBlock(this.nextObjectKey, 0, this.nextObjectKey.length);
/*     */ 
/*     */ 
/*     */       
/* 166 */       byte[] permsp = IVGenerator.getIV(16);
/* 167 */       permsp[0] = (byte)permissions;
/* 168 */       permsp[1] = (byte)(permissions >> 8);
/* 169 */       permsp[2] = (byte)(permissions >> 16);
/* 170 */       permsp[3] = (byte)(permissions >> 24);
/* 171 */       permsp[4] = -1;
/* 172 */       permsp[5] = -1;
/* 173 */       permsp[6] = -1;
/* 174 */       permsp[7] = -1;
/* 175 */       permsp[8] = encryptMetadata ? 84 : 70;
/* 176 */       permsp[9] = 97;
/* 177 */       permsp[10] = 100;
/* 178 */       permsp[11] = 98;
/* 179 */       ac = new AESCipherCBCnoPad(true, this.nextObjectKey);
/* 180 */       byte[] aes256Perms = ac.processBlock(permsp, 0, permsp.length);
/*     */       
/* 182 */       this.permissions = permissions;
/* 183 */       this.encryptMetadata = encryptMetadata;
/* 184 */       setStandardHandlerDicEntries(encryptionDictionary, userKey, ownerKey);
/* 185 */       setAES256DicEntries(encryptionDictionary, oeKey, ueKey, aes256Perms, encryptMetadata, embeddedFilesOnly);
/* 186 */     } catch (Exception ex) {
/* 187 */       throw new PdfException("PdfEncryption exception.", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAES256DicEntries(PdfDictionary encryptionDictionary, byte[] oeKey, byte[] ueKey, byte[] aes256Perms, boolean encryptMetadata, boolean embeddedFilesOnly) {
/* 193 */     int vAes256 = 5;
/* 194 */     int rAes256 = 5;
/* 195 */     int rAes256Pdf2 = 6;
/* 196 */     encryptionDictionary.put(PdfName.OE, (PdfObject)new PdfLiteral(StreamUtil.createEscapedString(oeKey)));
/* 197 */     encryptionDictionary.put(PdfName.UE, (PdfObject)new PdfLiteral(StreamUtil.createEscapedString(ueKey)));
/* 198 */     encryptionDictionary.put(PdfName.Perms, (PdfObject)new PdfLiteral(StreamUtil.createEscapedString(aes256Perms)));
/* 199 */     encryptionDictionary.put(PdfName.R, (PdfObject)new PdfNumber(this.isPdf2 ? rAes256Pdf2 : rAes256));
/* 200 */     encryptionDictionary.put(PdfName.V, (PdfObject)new PdfNumber(vAes256));
/* 201 */     PdfDictionary stdcf = new PdfDictionary();
/* 202 */     stdcf.put(PdfName.Length, (PdfObject)new PdfNumber(32));
/* 203 */     if (!encryptMetadata) {
/* 204 */       encryptionDictionary.put(PdfName.EncryptMetadata, (PdfObject)PdfBoolean.FALSE);
/*     */     }
/* 206 */     if (embeddedFilesOnly) {
/* 207 */       stdcf.put(PdfName.AuthEvent, (PdfObject)PdfName.EFOpen);
/* 208 */       encryptionDictionary.put(PdfName.EFF, (PdfObject)PdfName.StdCF);
/* 209 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.Identity);
/* 210 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.Identity);
/*     */     } else {
/* 212 */       stdcf.put(PdfName.AuthEvent, (PdfObject)PdfName.DocOpen);
/* 213 */       encryptionDictionary.put(PdfName.StrF, (PdfObject)PdfName.StdCF);
/* 214 */       encryptionDictionary.put(PdfName.StmF, (PdfObject)PdfName.StdCF);
/*     */     } 
/* 216 */     stdcf.put(PdfName.CFM, (PdfObject)PdfName.AESV3);
/* 217 */     PdfDictionary cf = new PdfDictionary();
/* 218 */     cf.put(PdfName.StdCF, (PdfObject)stdcf);
/* 219 */     encryptionDictionary.put(PdfName.CF, (PdfObject)cf);
/*     */   }
/*     */   
/*     */   private void initKeyAndReadDictionary(PdfDictionary encryptionDictionary, byte[] password) {
/*     */     try {
/* 224 */       if (password == null) {
/* 225 */         password = new byte[0];
/* 226 */       } else if (password.length > 127) {
/* 227 */         password = Arrays.copyOf(password, 127);
/*     */       } 
/*     */       
/* 230 */       this.isPdf2 = (encryptionDictionary.getAsNumber(PdfName.R).getValue() == 6.0D);
/*     */       
/* 232 */       byte[] oValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.O));
/* 233 */       byte[] uValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.U));
/* 234 */       byte[] oeValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.OE));
/* 235 */       byte[] ueValue = getIsoBytes(encryptionDictionary.getAsString(PdfName.UE));
/* 236 */       byte[] perms = getIsoBytes(encryptionDictionary.getAsString(PdfName.Perms));
/* 237 */       PdfNumber pValue = (PdfNumber)encryptionDictionary.get(PdfName.P);
/*     */       
/* 239 */       this.permissions = pValue.longValue();
/*     */ 
/*     */ 
/*     */       
/* 243 */       byte[] hash = computeHash(password, oValue, 32, 8, uValue);
/* 244 */       this.usedOwnerPassword = compareArray(hash, oValue, 32);
/*     */       
/* 246 */       if (this.usedOwnerPassword) {
/* 247 */         hash = computeHash(password, oValue, 40, 8, uValue);
/* 248 */         AESCipherCBCnoPad aESCipherCBCnoPad = new AESCipherCBCnoPad(false, hash);
/* 249 */         this.nextObjectKey = aESCipherCBCnoPad.processBlock(oeValue, 0, oeValue.length);
/*     */       } else {
/* 251 */         hash = computeHash(password, uValue, 32, 8);
/* 252 */         if (!compareArray(hash, uValue, 32)) {
/* 253 */           throw new BadPasswordException("Bad user password. Password is not provided or wrong password provided. Correct password should be passed to PdfReader constructor with properties. See ReaderProperties#setPassword() method.");
/*     */         }
/* 255 */         hash = computeHash(password, uValue, 40, 8);
/* 256 */         AESCipherCBCnoPad aESCipherCBCnoPad = new AESCipherCBCnoPad(false, hash);
/* 257 */         this.nextObjectKey = aESCipherCBCnoPad.processBlock(ueValue, 0, ueValue.length);
/*     */       } 
/* 259 */       this.nextObjectKeySize = 32;
/*     */       
/* 261 */       AESCipherCBCnoPad ac = new AESCipherCBCnoPad(false, this.nextObjectKey);
/* 262 */       byte[] decPerms = ac.processBlock(perms, 0, perms.length);
/* 263 */       if (decPerms[9] != 97 || decPerms[10] != 100 || decPerms[11] != 98)
/* 264 */         throw new BadPasswordException("Bad user password. Password is not provided or wrong password provided. Correct password should be passed to PdfReader constructor with properties. See ReaderProperties#setPassword() method."); 
/* 265 */       int permissionsDecoded = decPerms[0] & 0xFF | (decPerms[1] & 0xFF) << 8 | (decPerms[2] & 0xFF) << 16 | (decPerms[3] & 0xFF) << 24;
/*     */       
/* 267 */       boolean encryptMetadata = (decPerms[8] == 84);
/*     */       
/* 269 */       Boolean encryptMetadataEntry = encryptionDictionary.getAsBool(PdfName.EncryptMetadata);
/* 270 */       if (permissionsDecoded != this.permissions || (encryptMetadataEntry != null && encryptMetadata != encryptMetadataEntry.booleanValue())) {
/* 271 */         Logger logger = LoggerFactory.getLogger(StandardHandlerUsingAes256.class);
/* 272 */         logger.error("Encryption dictionary entries P and EncryptMetadata have value that does not correspond to encrypted values in Perms key.");
/*     */       } 
/* 274 */       this.permissions = permissionsDecoded;
/* 275 */       this.encryptMetadata = encryptMetadata;
/* 276 */     } catch (BadPasswordException ex) {
/* 277 */       throw ex;
/* 278 */     } catch (Exception ex) {
/* 279 */       throw new PdfException("PdfEncryption exception.", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] computeHash(byte[] password, byte[] salt, int saltOffset, int saltLen) throws NoSuchAlgorithmException {
/* 284 */     return computeHash(password, salt, saltOffset, saltLen, (byte[])null);
/*     */   }
/*     */   
/*     */   private byte[] computeHash(byte[] password, byte[] salt, int saltOffset, int saltLen, byte[] userKey) throws NoSuchAlgorithmException {
/* 288 */     MessageDigest mdSha256 = MessageDigest.getInstance("SHA-256");
/*     */     
/* 290 */     mdSha256.update(password);
/* 291 */     mdSha256.update(salt, saltOffset, saltLen);
/* 292 */     if (userKey != null) {
/* 293 */       mdSha256.update(userKey);
/*     */     }
/* 295 */     byte[] k = mdSha256.digest();
/*     */     
/* 297 */     if (this.isPdf2) {
/*     */ 
/*     */       
/* 300 */       MessageDigest mdSha384 = MessageDigest.getInstance("SHA-384");
/* 301 */       MessageDigest mdSha512 = MessageDigest.getInstance("SHA-512");
/*     */       
/* 303 */       int userKeyLen = (userKey != null) ? userKey.length : 0;
/* 304 */       int passAndUserKeyLen = password.length + userKeyLen;
/*     */ 
/*     */ 
/*     */       
/* 308 */       int roundNum = 0;
/*     */       
/*     */       while (true) {
/* 311 */         int k1RepLen = passAndUserKeyLen + k.length;
/* 312 */         byte[] k1 = new byte[k1RepLen * 64];
/* 313 */         System.arraycopy(password, 0, k1, 0, password.length);
/* 314 */         System.arraycopy(k, 0, k1, password.length, k.length);
/* 315 */         if (userKey != null) {
/* 316 */           System.arraycopy(userKey, 0, k1, password.length + k.length, userKeyLen);
/*     */         }
/* 318 */         for (int i = 1; i < 64; i++) {
/* 319 */           System.arraycopy(k1, 0, k1, k1RepLen * i, k1RepLen);
/*     */         }
/*     */ 
/*     */         
/* 323 */         AESCipherCBCnoPad cipher = new AESCipherCBCnoPad(true, Arrays.copyOf(k, 16), Arrays.copyOfRange(k, 16, 32));
/* 324 */         byte[] e = cipher.processBlock(k1, 0, k1.length);
/*     */ 
/*     */         
/* 327 */         MessageDigest md = null;
/* 328 */         BigInteger bigInteger = new BigInteger(1, Arrays.copyOf(e, 16));
/* 329 */         int remainder = bigInteger.remainder(BigInteger.valueOf(3L)).intValue();
/* 330 */         switch (remainder) {
/*     */           case 0:
/* 332 */             md = mdSha256;
/*     */             break;
/*     */           case 1:
/* 335 */             md = mdSha384;
/*     */             break;
/*     */           case 2:
/* 338 */             md = mdSha512;
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 343 */         k = md.digest(e);
/*     */         
/* 345 */         roundNum++;
/* 346 */         if (roundNum > 63) {
/*     */ 
/*     */           
/* 349 */           int condVal = e[e.length - 1] & 0xFF;
/* 350 */           if (condVal <= roundNum - 32) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 356 */       k = (k.length == 32) ? k : Arrays.copyOf(k, 32);
/*     */     } 
/*     */     
/* 359 */     return k;
/*     */   }
/*     */   
/*     */   private static boolean compareArray(byte[] a, byte[] b, int len) {
/* 363 */     for (int k = 0; k < len; k++) {
/* 364 */       if (a[k] != b[k]) {
/* 365 */         return false;
/*     */       }
/*     */     } 
/* 368 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/securityhandler/StandardHandlerUsingAes256.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */