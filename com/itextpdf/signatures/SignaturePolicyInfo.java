/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.io.codec.Base64;
/*     */ import org.bouncycastle.asn1.ASN1Encodable;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.ASN1OctetString;
/*     */ import org.bouncycastle.asn1.DERIA5String;
/*     */ import org.bouncycastle.asn1.DERObjectIdentifier;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.esf.OtherHashAlgAndValue;
/*     */ import org.bouncycastle.asn1.esf.SigPolicyQualifierInfo;
/*     */ import org.bouncycastle.asn1.esf.SignaturePolicyId;
/*     */ import org.bouncycastle.asn1.esf.SignaturePolicyIdentifier;
/*     */ import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
/*     */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignaturePolicyInfo
/*     */ {
/*     */   private String policyIdentifier;
/*     */   private byte[] policyHash;
/*     */   private String policyDigestAlgorithm;
/*     */   private String policyUri;
/*     */   
/*     */   public SignaturePolicyInfo(String policyIdentifier, byte[] policyHash, String policyDigestAlgorithm, String policyUri) {
/*  81 */     if (policyIdentifier == null || policyIdentifier.length() == 0) {
/*  82 */       throw new IllegalArgumentException("Policy identifier cannot be null");
/*     */     }
/*  84 */     if (policyHash == null) {
/*  85 */       throw new IllegalArgumentException("Policy hash cannot be null");
/*     */     }
/*  87 */     if (policyDigestAlgorithm == null || policyDigestAlgorithm.length() == 0) {
/*  88 */       throw new IllegalArgumentException("Policy digest algorithm cannot be null");
/*     */     }
/*     */     
/*  91 */     this.policyIdentifier = policyIdentifier;
/*  92 */     this.policyHash = policyHash;
/*  93 */     this.policyDigestAlgorithm = policyDigestAlgorithm;
/*  94 */     this.policyUri = policyUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignaturePolicyInfo(String policyIdentifier, String policyHashBase64, String policyDigestAlgorithm, String policyUri) {
/* 105 */     this(policyIdentifier, (policyHashBase64 != null) ? Base64.decode(policyHashBase64) : null, policyDigestAlgorithm, policyUri);
/*     */   }
/*     */   
/*     */   public String getPolicyIdentifier() {
/* 109 */     return this.policyIdentifier;
/*     */   }
/*     */   
/*     */   public byte[] getPolicyHash() {
/* 113 */     return this.policyHash;
/*     */   }
/*     */   
/*     */   public String getPolicyDigestAlgorithm() {
/* 117 */     return this.policyDigestAlgorithm;
/*     */   }
/*     */   
/*     */   public String getPolicyUri() {
/* 121 */     return this.policyUri;
/*     */   }
/*     */   
/*     */   SignaturePolicyIdentifier toSignaturePolicyIdentifier() {
/* 125 */     String algId = DigestAlgorithms.getAllowedDigest(this.policyDigestAlgorithm);
/*     */     
/* 127 */     if (algId == null || algId.length() == 0) {
/* 128 */       throw new IllegalArgumentException("Invalid policy hash algorithm");
/*     */     }
/*     */     
/* 131 */     SignaturePolicyIdentifier signaturePolicyIdentifier = null;
/* 132 */     SigPolicyQualifierInfo spqi = null;
/*     */     
/* 134 */     if (this.policyUri != null && this.policyUri.length() > 0) {
/* 135 */       spqi = new SigPolicyQualifierInfo(PKCSObjectIdentifiers.id_spq_ets_uri, (ASN1Encodable)new DERIA5String(this.policyUri));
/*     */     }
/*     */ 
/*     */     
/* 139 */     signaturePolicyIdentifier = new SignaturePolicyIdentifier(new SignaturePolicyId(DERObjectIdentifier.getInstance(new DERObjectIdentifier(this.policyIdentifier.replace("urn:oid:", ""))), new OtherHashAlgAndValue(new AlgorithmIdentifier(new ASN1ObjectIdentifier(algId)), (ASN1OctetString)new DEROctetString(this.policyHash)), SignUtils.createSigPolicyQualifiers(new SigPolicyQualifierInfo[] { spqi })));
/*     */     
/* 141 */     return signaturePolicyIdentifier;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/SignaturePolicyInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */