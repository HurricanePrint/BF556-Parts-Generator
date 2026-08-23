/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URL;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.CertificateID;
/*     */ import org.bouncycastle.cert.ocsp.CertificateStatus;
/*     */ import org.bouncycastle.cert.ocsp.OCSPException;
/*     */ import org.bouncycastle.cert.ocsp.OCSPReq;
/*     */ import org.bouncycastle.cert.ocsp.OCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.SingleResp;
/*     */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
/*     */ import org.bouncycastle.operator.OperatorException;
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
/*     */ public class OcspClientBouncyCastle
/*     */   implements IOcspClient
/*     */ {
/*  80 */   private static final Logger LOGGER = LoggerFactory.getLogger(OcspClientBouncyCastle.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final OCSPVerifier verifier;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OcspClientBouncyCastle(OCSPVerifier verifier) {
/*  91 */     this.verifier = verifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicOCSPResp getBasicOCSPResp(X509Certificate checkCert, X509Certificate rootCert, String url) {
/*     */     try {
/* 103 */       OCSPResp ocspResponse = getOcspResponse(checkCert, rootCert, url);
/* 104 */       if (ocspResponse == null) {
/* 105 */         return null;
/*     */       }
/* 107 */       if (ocspResponse.getStatus() != 0) {
/* 108 */         return null;
/*     */       }
/* 110 */       BasicOCSPResp basicResponse = (BasicOCSPResp)ocspResponse.getResponseObject();
/* 111 */       if (this.verifier != null) {
/* 112 */         this.verifier.isValidResponse(basicResponse, rootCert);
/*     */       }
/* 114 */       return basicResponse;
/* 115 */     } catch (Exception ex) {
/* 116 */       LOGGER.error(ex.getMessage());
/*     */       
/* 118 */       return null;
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
/*     */   public byte[] getEncoded(X509Certificate checkCert, X509Certificate rootCert, String url) {
/*     */     try {
/* 133 */       BasicOCSPResp basicResponse = getBasicOCSPResp(checkCert, rootCert, url);
/* 134 */       if (basicResponse != null) {
/* 135 */         SingleResp[] responses = basicResponse.getResponses();
/* 136 */         if (responses.length == 1) {
/* 137 */           SingleResp resp = responses[0];
/* 138 */           Object status = resp.getCertStatus();
/* 139 */           if (status == CertificateStatus.GOOD)
/* 140 */             return basicResponse.getEncoded(); 
/* 141 */           if (status instanceof org.bouncycastle.cert.ocsp.RevokedStatus) {
/* 142 */             throw new IOException("OCSP status is revoked.");
/*     */           }
/* 144 */           throw new IOException("OCSP status is unknown.");
/*     */         }
/*     */       
/*     */       } 
/* 148 */     } catch (Exception ex) {
/* 149 */       LOGGER.error(ex.getMessage());
/*     */     } 
/* 151 */     return null;
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
/*     */   private static OCSPReq generateOCSPRequest(X509Certificate issuerCert, BigInteger serialNumber) throws OCSPException, IOException, OperatorException, CertificateEncodingException {
/* 167 */     Security.addProvider((Provider)new BouncyCastleProvider());
/*     */ 
/*     */     
/* 170 */     CertificateID id = SignUtils.generateCertificateId(issuerCert, serialNumber, CertificateID.HASH_SHA1);
/*     */ 
/*     */     
/* 173 */     return SignUtils.generateOcspRequestWithNonce(id);
/*     */   }
/*     */   
/*     */   private OCSPResp getOcspResponse(X509Certificate checkCert, X509Certificate rootCert, String url) throws GeneralSecurityException, OCSPException, IOException, OperatorException {
/* 177 */     if (checkCert == null || rootCert == null)
/* 178 */       return null; 
/* 179 */     if (url == null) {
/* 180 */       url = CertificateUtil.getOCSPURL(checkCert);
/*     */     }
/* 182 */     if (url == null)
/* 183 */       return null; 
/* 184 */     LOGGER.info("Getting OCSP from " + url);
/* 185 */     OCSPReq request = generateOCSPRequest(rootCert, checkCert.getSerialNumber());
/* 186 */     byte[] array = request.getEncoded();
/* 187 */     URL urlt = new URL(url);
/* 188 */     InputStream in = SignUtils.getHttpResponseForOcspRequest(array, urlt);
/* 189 */     return new OCSPResp(StreamUtil.inputStreamToArray(in));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/OcspClientBouncyCastle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */