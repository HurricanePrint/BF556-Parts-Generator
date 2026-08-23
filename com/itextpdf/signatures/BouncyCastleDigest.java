/*    */ package com.itextpdf.signatures;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import org.bouncycastle.jcajce.provider.digest.GOST3411;
/*    */ import org.bouncycastle.jcajce.provider.digest.MD2;
/*    */ import org.bouncycastle.jcajce.provider.digest.MD5;
/*    */ import org.bouncycastle.jcajce.provider.digest.RIPEMD128;
/*    */ import org.bouncycastle.jcajce.provider.digest.RIPEMD160;
/*    */ import org.bouncycastle.jcajce.provider.digest.RIPEMD256;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA1;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA224;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA256;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA384;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA512;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BouncyCastleDigest
/*    */   implements IExternalDigest
/*    */ {
/*    */   public MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
/* 70 */     String oid = DigestAlgorithms.getAllowedDigest(hashAlgorithm);
/*    */     
/* 72 */     switch (oid) {
/*    */       case "1.2.840.113549.2.2":
/* 74 */         return (MessageDigest)new MD2.Digest();
/*    */       case "1.2.840.113549.2.5":
/* 76 */         return (MessageDigest)new MD5.Digest();
/*    */       case "1.3.14.3.2.26":
/* 78 */         return (MessageDigest)new SHA1.Digest();
/*    */       case "2.16.840.1.101.3.4.2.4":
/* 80 */         return (MessageDigest)new SHA224.Digest();
/*    */       case "2.16.840.1.101.3.4.2.1":
/* 82 */         return (MessageDigest)new SHA256.Digest();
/*    */       case "2.16.840.1.101.3.4.2.2":
/* 84 */         return (MessageDigest)new SHA384.Digest();
/*    */       case "2.16.840.1.101.3.4.2.3":
/* 86 */         return (MessageDigest)new SHA512.Digest();
/*    */       case "1.3.36.3.2.2":
/* 88 */         return (MessageDigest)new RIPEMD128.Digest();
/*    */       case "1.3.36.3.2.1":
/* 90 */         return (MessageDigest)new RIPEMD160.Digest();
/*    */       case "1.3.36.3.2.3":
/* 92 */         return (MessageDigest)new RIPEMD256.Digest();
/*    */       case "1.2.643.2.2.9":
/* 94 */         return (MessageDigest)new GOST3411.Digest();
/*    */     } 
/* 96 */     throw new NoSuchAlgorithmException(hashAlgorithm);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/BouncyCastleDigest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */