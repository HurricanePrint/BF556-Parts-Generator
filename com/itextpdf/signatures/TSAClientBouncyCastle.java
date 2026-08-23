/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.io.codec.Base64;
/*     */ import com.itextpdf.io.util.SystemUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.cmp.PKIFailureInfo;
/*     */ import org.bouncycastle.tsp.TSPException;
/*     */ import org.bouncycastle.tsp.TimeStampRequest;
/*     */ import org.bouncycastle.tsp.TimeStampRequestGenerator;
/*     */ import org.bouncycastle.tsp.TimeStampResponse;
/*     */ import org.bouncycastle.tsp.TimeStampToken;
/*     */ import org.bouncycastle.tsp.TimeStampTokenInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TSAClientBouncyCastle
/*     */   implements ITSAClient
/*     */ {
/*     */   public static final String DEFAULTHASHALGORITHM = "SHA-256";
/*     */   public static final int DEFAULTTOKENSIZE = 4096;
/*  87 */   private static final Logger LOGGER = LoggerFactory.getLogger(TSAClientBouncyCastle.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String tsaURL;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String tsaUsername;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String tsaPassword;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ITSAInfoBouncyCastle tsaInfo;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int tokenSizeEstimate;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String digestAlgorithm;
/*     */ 
/*     */ 
/*     */   
/*     */   private String tsaReqPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TSAClientBouncyCastle(String url) {
/* 124 */     this(url, null, null, 4096, "SHA-256");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TSAClientBouncyCastle(String url, String username, String password) {
/* 135 */     this(url, username, password, 4096, "SHA-256");
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
/*     */   public TSAClientBouncyCastle(String url, String username, String password, int tokSzEstimate, String digestAlgorithm) {
/* 150 */     this.tsaURL = url;
/* 151 */     this.tsaUsername = username;
/* 152 */     this.tsaPassword = password;
/* 153 */     this.tokenSizeEstimate = tokSzEstimate;
/* 154 */     this.digestAlgorithm = digestAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTSAInfo(ITSAInfoBouncyCastle tsaInfo) {
/* 161 */     this.tsaInfo = tsaInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTokenSizeEstimate() {
/* 172 */     return this.tokenSizeEstimate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTSAReqPolicy() {
/* 181 */     return this.tsaReqPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTSAReqPolicy(String tsaReqPolicy) {
/* 190 */     this.tsaReqPolicy = tsaReqPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageDigest getMessageDigest() throws GeneralSecurityException {
/* 200 */     return SignUtils.getMessageDigest(this.digestAlgorithm);
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
/*     */   public byte[] getTimeStampToken(byte[] imprint) throws IOException, TSPException {
/* 214 */     byte[] respBytes = null;
/*     */     
/* 216 */     TimeStampRequestGenerator tsqGenerator = new TimeStampRequestGenerator();
/* 217 */     tsqGenerator.setCertReq(true);
/* 218 */     if (this.tsaReqPolicy != null && this.tsaReqPolicy.length() > 0) {
/* 219 */       tsqGenerator.setReqPolicy(this.tsaReqPolicy);
/*     */     }
/*     */     
/* 222 */     BigInteger nonce = BigInteger.valueOf(SystemUtil.getTimeBasedSeed());
/* 223 */     TimeStampRequest request = tsqGenerator.generate(new ASN1ObjectIdentifier(DigestAlgorithms.getAllowedDigest(this.digestAlgorithm)), imprint, nonce);
/* 224 */     byte[] requestBytes = request.getEncoded();
/*     */ 
/*     */     
/* 227 */     respBytes = getTSAResponse(requestBytes);
/*     */ 
/*     */     
/* 230 */     TimeStampResponse response = new TimeStampResponse(respBytes);
/*     */ 
/*     */     
/* 233 */     response.validate(request);
/* 234 */     PKIFailureInfo failure = response.getFailInfo();
/* 235 */     int value = (failure == null) ? 0 : failure.intValue();
/* 236 */     if (value != 0)
/*     */     {
/* 238 */       throw (new PdfException("Invalid TSA {0} response code {1}.")).setMessageParams(new Object[] { this.tsaURL, String.valueOf(value) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     TimeStampToken tsToken = response.getTimeStampToken();
/* 245 */     if (tsToken == null) {
/* 246 */       throw (new PdfException("TSA {0} failed to return time stamp token: {1}.")).setMessageParams(new Object[] { this.tsaURL, response.getStatusString() });
/*     */     }
/* 248 */     TimeStampTokenInfo tsTokenInfo = tsToken.getTimeStampInfo();
/* 249 */     byte[] encoded = tsToken.getEncoded();
/*     */     
/* 251 */     LOGGER.info("Timestamp generated: " + tsTokenInfo.getGenTime());
/* 252 */     if (this.tsaInfo != null) {
/* 253 */       this.tsaInfo.inspectTimeStampTokenInfo(tsTokenInfo);
/*     */     }
/*     */     
/* 256 */     this.tokenSizeEstimate = encoded.length + 32;
/* 257 */     return encoded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getTSAResponse(byte[] requestBytes) throws IOException {
/* 268 */     SignUtils.TsaResponse response = SignUtils.getTsaResponseForUserRequest(this.tsaURL, requestBytes, this.tsaUsername, this.tsaPassword);
/*     */     
/* 270 */     InputStream inp = response.tsaResponseStream;
/* 271 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 272 */     byte[] buffer = new byte[1024];
/* 273 */     int bytesRead = 0;
/* 274 */     while ((bytesRead = inp.read(buffer, 0, buffer.length)) >= 0) {
/* 275 */       baos.write(buffer, 0, bytesRead);
/*     */     }
/* 277 */     byte[] respBytes = baos.toByteArray();
/*     */     
/* 279 */     if (response.encoding != null && response.encoding.toLowerCase().equals("base64".toLowerCase())) {
/* 280 */       respBytes = Base64.decode(new String(respBytes, "US-ASCII"));
/*     */     }
/* 282 */     return respBytes;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/TSAClientBouncyCastle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */