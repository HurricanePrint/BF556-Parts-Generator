/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.io.util.DateTimeUtil;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.CertificateStatus;
/*     */ import org.bouncycastle.cert.ocsp.OCSPException;
/*     */ import org.bouncycastle.cert.ocsp.SingleResp;
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
/*     */ public class OCSPVerifier
/*     */   extends RootStoreVerifier
/*     */ {
/*  74 */   protected static final Logger LOGGER = LoggerFactory.getLogger(OCSPVerifier.class);
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String id_kp_OCSPSigning = "1.3.6.1.5.5.7.3.9";
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<BasicOCSPResp> ocsps;
/*     */ 
/*     */ 
/*     */   
/*     */   public OCSPVerifier(CertificateVerifier verifier, List<BasicOCSPResp> ocsps) {
/*  87 */     super(verifier);
/*  88 */     this.ocsps = ocsps;
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
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
/* 104 */     List<VerificationOK> result = new ArrayList<>();
/* 105 */     int validOCSPsFound = 0;
/*     */     
/* 107 */     if (this.ocsps != null) {
/* 108 */       for (BasicOCSPResp ocspResp : this.ocsps) {
/* 109 */         if (verify(ocspResp, signCert, issuerCert, signDate)) {
/* 110 */           validOCSPsFound++;
/*     */         }
/*     */       } 
/*     */     }
/* 114 */     boolean online = false;
/* 115 */     if (this.onlineCheckingAllowed && validOCSPsFound == 0 && 
/* 116 */       verify(getOcspResponse(signCert, issuerCert), signCert, issuerCert, signDate)) {
/* 117 */       validOCSPsFound++;
/* 118 */       online = true;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     LOGGER.info("Valid OCSPs found: " + validOCSPsFound);
/* 123 */     if (validOCSPsFound > 0)
/* 124 */       result.add(new VerificationOK(signCert, (Class)getClass(), "Valid OCSPs Found: " + validOCSPsFound + (online ? " (online)" : ""))); 
/* 125 */     if (this.verifier != null) {
/* 126 */       result.addAll(this.verifier.verify(signCert, issuerCert, signDate));
/*     */     }
/* 128 */     return result;
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
/*     */   public boolean verify(BasicOCSPResp ocspResp, X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
/* 143 */     if (ocspResp == null) {
/* 144 */       return false;
/*     */     }
/* 146 */     SingleResp[] resp = ocspResp.getResponses();
/* 147 */     for (int i = 0; i < resp.length; i++) {
/*     */       
/* 149 */       if (!signCert.getSerialNumber().equals(resp[i].getCertID().getSerialNumber())) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 154 */         if (issuerCert == null) issuerCert = signCert; 
/* 155 */         if (!SignUtils.checkIfIssuersMatch(resp[i].getCertID(), issuerCert)) {
/* 156 */           LOGGER.info("OCSP: Issuers doesn't match.");
/*     */           continue;
/*     */         } 
/* 159 */       } catch (OCSPException e) {
/*     */         continue;
/*     */       } 
/*     */       
/* 163 */       if (resp[i].getNextUpdate() == null) {
/* 164 */         Date nextUpdate = SignUtils.add180Sec(resp[i].getThisUpdate());
/* 165 */         LOGGER.info(MessageFormatUtil.format("No 'next update' for OCSP Response; assuming {0}", new Object[] { nextUpdate }));
/* 166 */         if (signDate.after(nextUpdate)) {
/* 167 */           LOGGER.info(MessageFormatUtil.format("OCSP no longer valid: {0} after {1}", new Object[] { signDate, nextUpdate }));
/*     */           
/*     */           continue;
/*     */         } 
/* 171 */       } else if (signDate.after(resp[i].getNextUpdate())) {
/* 172 */         LOGGER.info(MessageFormatUtil.format("OCSP no longer valid: {0} after {1}", new Object[] { signDate, resp[i].getNextUpdate() }));
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 177 */       Object status = resp[i].getCertStatus();
/* 178 */       if (status == CertificateStatus.GOOD) {
/*     */         
/* 180 */         isValidResponse(ocspResp, issuerCert, signDate);
/* 181 */         return true;
/*     */       }  continue;
/*     */     } 
/* 184 */     return false;
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
/*     */   @Deprecated
/*     */   public void isValidResponse(BasicOCSPResp ocspResp, X509Certificate issuerCert) throws GeneralSecurityException, IOException {
/* 199 */     isValidResponse(ocspResp, issuerCert, DateTimeUtil.getCurrentTimeDate());
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
/*     */   public void isValidResponse(BasicOCSPResp ocspResp, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException {
/* 214 */     X509Certificate responderCert = null;
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (isSignatureValid(ocspResp, issuerCert)) {
/* 219 */       responderCert = issuerCert;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 224 */     if (responderCert == null) {
/* 225 */       if (ocspResp.getCerts() != null) {
/*     */         
/* 227 */         Iterable<X509Certificate> certs = SignUtils.getCertsFromOcspResponse(ocspResp);
/* 228 */         for (X509Certificate cert : certs) {
/* 229 */           List<String> keyPurposes = null;
/*     */           try {
/* 231 */             keyPurposes = cert.getExtendedKeyUsage();
/* 232 */             if (keyPurposes != null && keyPurposes.contains("1.3.6.1.5.5.7.3.9") && isSignatureValid(ocspResp, cert)) {
/* 233 */               responderCert = cert;
/*     */               break;
/*     */             } 
/* 236 */           } catch (CertificateParsingException certificateParsingException) {}
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 242 */         if (responderCert == null) {
/* 243 */           throw new VerificationException(issuerCert, "OCSP response could not be verified");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 252 */         responderCert.verify(issuerCert.getPublicKey());
/*     */ 
/*     */         
/* 255 */         responderCert.checkValidity(signDate);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 260 */         if (responderCert.getExtensionValue(OCSPObjectIdentifiers.id_pkix_ocsp_nocheck.getId()) == null) {
/*     */           CRL crl;
/*     */ 
/*     */           
/*     */           try {
/* 265 */             crl = CertificateUtil.getCRL(responderCert);
/* 266 */           } catch (Exception ignored) {
/* 267 */             crl = (CRL)null;
/*     */           } 
/* 269 */           if (crl != null && crl instanceof X509CRL) {
/* 270 */             CRLVerifier crlVerifier = new CRLVerifier(null, null);
/* 271 */             crlVerifier.setRootStore(this.rootStore);
/* 272 */             crlVerifier.setOnlineCheckingAllowed(this.onlineCheckingAllowed);
/* 273 */             if (!crlVerifier.verify((X509CRL)crl, responderCert, issuerCert, signDate)) {
/* 274 */               throw new VerificationException(issuerCert, "Authorized OCSP responder certificate was revoked.");
/*     */             }
/*     */           } else {
/* 277 */             Logger logger = LoggerFactory.getLogger(OCSPVerifier.class);
/* 278 */             logger.error("Authorized OCSP responder certificate revocation status cannot be checked");
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 291 */         if (this.rootStore != null) {
/*     */           try {
/* 293 */             for (X509Certificate anchor : SignUtils.getCertificates(this.rootStore)) {
/* 294 */               if (isSignatureValid(ocspResp, anchor)) {
/*     */                 
/* 296 */                 responderCert = anchor;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 300 */           } catch (Exception e) {
/* 301 */             responderCert = (X509Certificate)null;
/*     */           } 
/*     */         }
/*     */         
/* 305 */         if (responderCert == null) {
/* 306 */           throw new VerificationException(issuerCert, "OCSP response could not be verified: it does not contain certificate chain and response is not signed by issuer certificate or any from the root store.");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSignatureValid(BasicOCSPResp ocspResp, Certificate responderCert) {
/*     */     try {
/* 320 */       return SignUtils.isSignatureValid(ocspResp, responderCert, "BC");
/* 321 */     } catch (Exception e) {
/* 322 */       return false;
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
/*     */   public BasicOCSPResp getOcspResponse(X509Certificate signCert, X509Certificate issuerCert) {
/* 334 */     if (signCert == null && issuerCert == null) {
/* 335 */       return null;
/*     */     }
/* 337 */     OcspClientBouncyCastle ocsp = new OcspClientBouncyCastle(null);
/* 338 */     BasicOCSPResp ocspResp = ocsp.getBasicOCSPResp(signCert, issuerCert, null);
/* 339 */     if (ocspResp == null) {
/* 340 */       return null;
/*     */     }
/* 342 */     SingleResp[] resps = ocspResp.getResponses();
/* 343 */     for (SingleResp resp : resps) {
/* 344 */       Object status = resp.getCertStatus();
/* 345 */       if (status == CertificateStatus.GOOD) {
/* 346 */         return ocspResp;
/*     */       }
/*     */     } 
/* 349 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/OCSPVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */