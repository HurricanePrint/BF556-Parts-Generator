/*     */ package com.itextpdf.kernel.crypto;
/*     */ 
/*     */ import org.bouncycastle.crypto.BlockCipher;
/*     */ import org.bouncycastle.crypto.CipherParameters;
/*     */ import org.bouncycastle.crypto.engines.AESFastEngine;
/*     */ import org.bouncycastle.crypto.modes.CBCBlockCipher;
/*     */ import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
/*     */ import org.bouncycastle.crypto.params.KeyParameter;
/*     */ import org.bouncycastle.crypto.params.ParametersWithIV;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AESCipher
/*     */ {
/*     */   private PaddedBufferedBlockCipher bp;
/*     */   
/*     */   public AESCipher(boolean forEncryption, byte[] key, byte[] iv) {
/*  70 */     AESFastEngine aESFastEngine = new AESFastEngine();
/*  71 */     CBCBlockCipher cBCBlockCipher = new CBCBlockCipher((BlockCipher)aESFastEngine);
/*  72 */     this.bp = new PaddedBufferedBlockCipher((BlockCipher)cBCBlockCipher);
/*  73 */     KeyParameter kp = new KeyParameter(key);
/*  74 */     ParametersWithIV piv = new ParametersWithIV((CipherParameters)kp, iv);
/*  75 */     this.bp.init(forEncryption, (CipherParameters)piv);
/*     */   }
/*     */   public byte[] update(byte[] inp, int inpOff, int inpLen) {
/*     */     byte[] outp;
/*  79 */     int neededLen = this.bp.getUpdateOutputSize(inpLen);
/*     */     
/*  81 */     if (neededLen > 0) {
/*  82 */       outp = new byte[neededLen];
/*     */     } else {
/*  84 */       outp = new byte[0];
/*     */     } 
/*  86 */     this.bp.processBytes(inp, inpOff, inpLen, outp, 0);
/*  87 */     return outp;
/*     */   }
/*     */   
/*     */   public byte[] doFinal() {
/*  91 */     int n, neededLen = this.bp.getOutputSize(0);
/*  92 */     byte[] outp = new byte[neededLen];
/*     */     
/*     */     try {
/*  95 */       n = this.bp.doFinal(outp, 0);
/*  96 */     } catch (Exception ex) {
/*  97 */       return outp;
/*     */     } 
/*  99 */     if (n != outp.length) {
/* 100 */       byte[] outp2 = new byte[n];
/* 101 */       System.arraycopy(outp, 0, outp2, 0, n);
/* 102 */       return outp2;
/*     */     } 
/*     */     
/* 105 */     return outp;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/AESCipher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */