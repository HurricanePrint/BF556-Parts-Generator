/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Signature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrivateKeySignature
/*     */   implements IExternalSignature
/*     */ {
/*     */   private PrivateKey pk;
/*     */   private String hashAlgorithm;
/*     */   private String encryptionAlgorithm;
/*     */   private String provider;
/*     */   
/*     */   public PrivateKeySignature(PrivateKey pk, String hashAlgorithm, String provider) {
/*  86 */     this.pk = pk;
/*  87 */     this.provider = provider;
/*  88 */     this.hashAlgorithm = DigestAlgorithms.getDigest(DigestAlgorithms.getAllowedDigest(hashAlgorithm));
/*  89 */     this.encryptionAlgorithm = SignUtils.getPrivateKeyAlgorithm(pk);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHashAlgorithm() {
/*  97 */     return this.hashAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 105 */     return this.encryptionAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] message) throws GeneralSecurityException {
/* 113 */     String algorithm = this.hashAlgorithm + "with" + this.encryptionAlgorithm;
/* 114 */     Signature sig = SignUtils.getSignatureHelper(algorithm, this.provider);
/* 115 */     sig.initSign(this.pk);
/* 116 */     sig.update(message);
/* 117 */     return sig.sign();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/PrivateKeySignature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */