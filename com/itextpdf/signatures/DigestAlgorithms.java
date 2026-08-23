/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DigestAlgorithms
/*     */ {
/*     */   public static final String SHA1 = "SHA-1";
/*     */   public static final String SHA256 = "SHA-256";
/*     */   public static final String SHA384 = "SHA-384";
/*     */   public static final String SHA512 = "SHA-512";
/*     */   public static final String RIPEMD160 = "RIPEMD160";
/*  88 */   private static final Map<String, String> digestNames = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static final Map<String, String> fixNames = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static final Map<String, String> allowedDigests = new HashMap<>();
/*     */   
/*     */   static {
/* 101 */     digestNames.put("1.2.840.113549.2.5", "MD5");
/* 102 */     digestNames.put("1.2.840.113549.2.2", "MD2");
/* 103 */     digestNames.put("1.3.14.3.2.26", "SHA1");
/* 104 */     digestNames.put("2.16.840.1.101.3.4.2.4", "SHA224");
/* 105 */     digestNames.put("2.16.840.1.101.3.4.2.1", "SHA256");
/* 106 */     digestNames.put("2.16.840.1.101.3.4.2.2", "SHA384");
/* 107 */     digestNames.put("2.16.840.1.101.3.4.2.3", "SHA512");
/* 108 */     digestNames.put("1.3.36.3.2.2", "RIPEMD128");
/* 109 */     digestNames.put("1.3.36.3.2.1", "RIPEMD160");
/* 110 */     digestNames.put("1.3.36.3.2.3", "RIPEMD256");
/* 111 */     digestNames.put("1.2.840.113549.1.1.4", "MD5");
/* 112 */     digestNames.put("1.2.840.113549.1.1.2", "MD2");
/* 113 */     digestNames.put("1.2.840.113549.1.1.5", "SHA1");
/* 114 */     digestNames.put("1.2.840.113549.1.1.14", "SHA224");
/* 115 */     digestNames.put("1.2.840.113549.1.1.11", "SHA256");
/* 116 */     digestNames.put("1.2.840.113549.1.1.12", "SHA384");
/* 117 */     digestNames.put("1.2.840.113549.1.1.13", "SHA512");
/* 118 */     digestNames.put("1.2.840.113549.2.5", "MD5");
/* 119 */     digestNames.put("1.2.840.113549.2.2", "MD2");
/* 120 */     digestNames.put("1.2.840.10040.4.3", "SHA1");
/* 121 */     digestNames.put("2.16.840.1.101.3.4.3.1", "SHA224");
/* 122 */     digestNames.put("2.16.840.1.101.3.4.3.2", "SHA256");
/* 123 */     digestNames.put("2.16.840.1.101.3.4.3.3", "SHA384");
/* 124 */     digestNames.put("2.16.840.1.101.3.4.3.4", "SHA512");
/* 125 */     digestNames.put("1.3.36.3.3.1.3", "RIPEMD128");
/* 126 */     digestNames.put("1.3.36.3.3.1.2", "RIPEMD160");
/* 127 */     digestNames.put("1.3.36.3.3.1.4", "RIPEMD256");
/* 128 */     digestNames.put("1.2.643.2.2.9", "GOST3411");
/*     */     
/* 130 */     fixNames.put("SHA256", "SHA-256");
/* 131 */     fixNames.put("SHA384", "SHA-384");
/* 132 */     fixNames.put("SHA512", "SHA-512");
/*     */     
/* 134 */     allowedDigests.put("MD2", "1.2.840.113549.2.2");
/* 135 */     allowedDigests.put("MD-2", "1.2.840.113549.2.2");
/* 136 */     allowedDigests.put("MD5", "1.2.840.113549.2.5");
/* 137 */     allowedDigests.put("MD-5", "1.2.840.113549.2.5");
/* 138 */     allowedDigests.put("SHA1", "1.3.14.3.2.26");
/* 139 */     allowedDigests.put("SHA-1", "1.3.14.3.2.26");
/* 140 */     allowedDigests.put("SHA224", "2.16.840.1.101.3.4.2.4");
/* 141 */     allowedDigests.put("SHA-224", "2.16.840.1.101.3.4.2.4");
/* 142 */     allowedDigests.put("SHA256", "2.16.840.1.101.3.4.2.1");
/* 143 */     allowedDigests.put("SHA-256", "2.16.840.1.101.3.4.2.1");
/* 144 */     allowedDigests.put("SHA384", "2.16.840.1.101.3.4.2.2");
/* 145 */     allowedDigests.put("SHA-384", "2.16.840.1.101.3.4.2.2");
/* 146 */     allowedDigests.put("SHA512", "2.16.840.1.101.3.4.2.3");
/* 147 */     allowedDigests.put("SHA-512", "2.16.840.1.101.3.4.2.3");
/* 148 */     allowedDigests.put("RIPEMD128", "1.3.36.3.2.2");
/* 149 */     allowedDigests.put("RIPEMD-128", "1.3.36.3.2.2");
/* 150 */     allowedDigests.put("RIPEMD160", "1.3.36.3.2.1");
/* 151 */     allowedDigests.put("RIPEMD-160", "1.3.36.3.2.1");
/* 152 */     allowedDigests.put("RIPEMD256", "1.3.36.3.2.3");
/* 153 */     allowedDigests.put("RIPEMD-256", "1.3.36.3.2.3");
/* 154 */     allowedDigests.put("GOST3411", "1.2.643.2.2.9");
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
/*     */   public static MessageDigest getMessageDigestFromOid(String digestOid, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 170 */     return getMessageDigest(getDigest(digestOid), provider);
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
/*     */   public static MessageDigest getMessageDigest(String hashAlgorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 186 */     return SignUtils.getMessageDigest(hashAlgorithm, provider);
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
/*     */   public static byte[] digest(InputStream data, String hashAlgorithm, String provider) throws GeneralSecurityException, IOException {
/* 202 */     MessageDigest messageDigest = getMessageDigest(hashAlgorithm, provider);
/* 203 */     return digest(data, messageDigest);
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
/*     */   public static byte[] digest(InputStream data, MessageDigest messageDigest) throws IOException {
/* 216 */     byte[] buf = new byte[8192];
/*     */     int n;
/* 218 */     while ((n = data.read(buf)) > 0) {
/* 219 */       messageDigest.update(buf, 0, n);
/*     */     }
/* 221 */     return messageDigest.digest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDigest(String oid) {
/* 231 */     String ret = digestNames.get(oid);
/* 232 */     if (ret == null) {
/* 233 */       return oid;
/*     */     }
/* 235 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String normalizeDigestName(String algo) {
/* 245 */     if (fixNames.containsKey(algo))
/* 246 */       return fixNames.get(algo); 
/* 247 */     return algo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAllowedDigest(String name) {
/* 258 */     return allowedDigests.get(name.toUpperCase());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/DigestAlgorithms.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */