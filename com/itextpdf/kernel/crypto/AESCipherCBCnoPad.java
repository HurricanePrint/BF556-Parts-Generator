/*     */ package com.itextpdf.kernel.crypto;
/*     */ 
/*     */ import org.bouncycastle.crypto.BlockCipher;
/*     */ import org.bouncycastle.crypto.CipherParameters;
/*     */ import org.bouncycastle.crypto.engines.AESFastEngine;
/*     */ import org.bouncycastle.crypto.modes.CBCBlockCipher;
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
/*     */ public class AESCipherCBCnoPad
/*     */ {
/*     */   private BlockCipher cbc;
/*     */   
/*     */   public AESCipherCBCnoPad(boolean forEncryption, byte[] key) {
/*  68 */     AESFastEngine aESFastEngine = new AESFastEngine();
/*  69 */     this.cbc = (BlockCipher)new CBCBlockCipher((BlockCipher)aESFastEngine);
/*  70 */     KeyParameter kp = new KeyParameter(key);
/*  71 */     this.cbc.init(forEncryption, (CipherParameters)kp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AESCipherCBCnoPad(boolean forEncryption, byte[] key, byte[] initVector) {
/*  82 */     AESFastEngine aESFastEngine = new AESFastEngine();
/*  83 */     this.cbc = (BlockCipher)new CBCBlockCipher((BlockCipher)aESFastEngine);
/*  84 */     KeyParameter kp = new KeyParameter(key);
/*  85 */     ParametersWithIV piv = new ParametersWithIV((CipherParameters)kp, initVector);
/*  86 */     this.cbc.init(forEncryption, (CipherParameters)piv);
/*     */   }
/*     */   
/*     */   public byte[] processBlock(byte[] inp, int inpOff, int inpLen) {
/*  90 */     if (inpLen % this.cbc.getBlockSize() != 0)
/*  91 */       throw new IllegalArgumentException("Not multiple of block: " + inpLen); 
/*  92 */     byte[] outp = new byte[inpLen];
/*  93 */     int baseOffset = 0;
/*  94 */     while (inpLen > 0) {
/*  95 */       this.cbc.processBlock(inp, inpOff, outp, baseOffset);
/*  96 */       inpLen -= this.cbc.getBlockSize();
/*  97 */       baseOffset += this.cbc.getBlockSize();
/*  98 */       inpOff += this.cbc.getBlockSize();
/*     */     } 
/* 100 */     return outp;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/crypto/AESCipherCBCnoPad.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */