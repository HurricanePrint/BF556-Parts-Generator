/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.forms.PdfAcroForm;
/*     */ import com.itextpdf.io.util.DateTimeUtil;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.counter.event.IMetaInfo;
/*     */ import com.itextpdf.kernel.pdf.DocumentProperties;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfReader;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.OCSPException;
/*     */ import org.bouncycastle.cert.ocsp.OCSPResp;
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
/*     */ public class LtvVerifier
/*     */   extends RootStoreVerifier
/*     */ {
/*  81 */   protected static final Logger LOGGER = LoggerFactory.getLogger(LtvVerifier.class);
/*     */ 
/*     */   
/*  84 */   protected LtvVerification.CertificateOption option = LtvVerification.CertificateOption.SIGNING_CERTIFICATE;
/*     */ 
/*     */   
/*     */   protected boolean verifyRootCertificate = true;
/*     */   
/*     */   protected PdfDocument document;
/*     */   
/*     */   protected PdfAcroForm acroForm;
/*     */   
/*     */   protected Date signDate;
/*     */   
/*     */   protected String signatureName;
/*     */   
/*     */   protected PdfPKCS7 pkcs7;
/*     */   
/*     */   protected boolean latestRevision = true;
/*     */   
/*     */   protected PdfDictionary dss;
/*     */   
/* 103 */   protected String securityProviderCode = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected IMetaInfo metaInfo;
/*     */ 
/*     */   
/*     */   private SignatureUtil sgnUtil;
/*     */ 
/*     */ 
/*     */   
/*     */   public LtvVerifier(PdfDocument document) throws GeneralSecurityException {
/* 115 */     super((CertificateVerifier)null);
/* 116 */     initLtvVerifier(document);
/*     */   }
/*     */   public LtvVerifier(PdfDocument document, String securityProviderCode) throws GeneralSecurityException {
/* 119 */     super((CertificateVerifier)null);
/* 120 */     this.securityProviderCode = securityProviderCode;
/* 121 */     initLtvVerifier(document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerifier(CertificateVerifier verifier) {
/* 129 */     this.verifier = verifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCertificateOption(LtvVerification.CertificateOption option) {
/* 137 */     this.option = option;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerifyRootCertificate(boolean verifyRootCertificate) {
/* 146 */     this.verifyRootCertificate = verifyRootCertificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEventCountingMetaInfo(IMetaInfo metaInfo) {
/* 155 */     this.metaInfo = metaInfo;
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
/*     */   public List<VerificationOK> verify(List<VerificationOK> result) throws IOException, GeneralSecurityException {
/* 167 */     if (result == null)
/* 168 */       result = new ArrayList<>(); 
/* 169 */     while (this.pkcs7 != null) {
/* 170 */       result.addAll(verifySignature());
/*     */     }
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<VerificationOK> verifySignature() throws GeneralSecurityException, IOException {
/* 183 */     LOGGER.info("Verifying signature.");
/* 184 */     List<VerificationOK> result = new ArrayList<>();
/*     */     
/* 186 */     Certificate[] chain = this.pkcs7.getSignCertificateChain();
/* 187 */     verifyChain(chain);
/*     */     
/* 189 */     int total = 1;
/* 190 */     if (LtvVerification.CertificateOption.WHOLE_CHAIN.equals(this.option)) {
/* 191 */       total = chain.length;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 196 */     for (int i = 0; i < total; ) {
/*     */       
/* 198 */       X509Certificate signCert = (X509Certificate)chain[i++];
/*     */       
/* 200 */       X509Certificate issuerCert = (X509Certificate)null;
/* 201 */       if (i < chain.length) {
/* 202 */         issuerCert = (X509Certificate)chain[i];
/*     */       }
/* 204 */       LOGGER.info(signCert.getSubjectDN().getName());
/* 205 */       List<VerificationOK> list = verify(signCert, issuerCert, this.signDate);
/* 206 */       if (list.size() == 0) {
/*     */         try {
/* 208 */           signCert.verify(signCert.getPublicKey());
/* 209 */           if (this.latestRevision && chain.length > 1) {
/* 210 */             list.add(new VerificationOK(signCert, (Class)getClass(), "Root certificate in final revision"));
/*     */           }
/* 212 */           if (list.size() == 0 && this.verifyRootCertificate) {
/* 213 */             throw new GeneralSecurityException();
/*     */           }
/* 215 */           if (chain.length > 1) {
/* 216 */             list.add(new VerificationOK(signCert, (Class)getClass(), "Root certificate passed without checking"));
/*     */           }
/* 218 */         } catch (GeneralSecurityException e) {
/* 219 */           throw new VerificationException(signCert, "Couldn't verify with CRL or OCSP or trusted anchor");
/*     */         } 
/*     */       }
/* 222 */       result.addAll(list);
/*     */     } 
/*     */     
/* 225 */     switchToPreviousRevision();
/* 226 */     return result;
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
/*     */   public void verifyChain(Certificate[] chain) throws GeneralSecurityException {
/* 240 */     for (int i = 0; i < chain.length; i++) {
/* 241 */       X509Certificate cert = (X509Certificate)chain[i];
/*     */       
/* 243 */       cert.checkValidity(this.signDate);
/*     */       
/* 245 */       if (i > 0)
/* 246 */         chain[i - 1].verify(chain[i].getPublicKey()); 
/*     */     } 
/* 248 */     LOGGER.info("All certificates are valid on " + this.signDate.toString());
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
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
/* 263 */     RootStoreVerifier rootStoreVerifier = new RootStoreVerifier(this.verifier);
/* 264 */     rootStoreVerifier.setRootStore(this.rootStore);
/*     */     
/* 266 */     CRLVerifier crlVerifier = new CRLVerifier(rootStoreVerifier, getCRLsFromDSS());
/* 267 */     crlVerifier.setRootStore(this.rootStore);
/* 268 */     crlVerifier.setOnlineCheckingAllowed((this.latestRevision || this.onlineCheckingAllowed));
/*     */     
/* 270 */     OCSPVerifier ocspVerifier = new OCSPVerifier(crlVerifier, getOCSPResponsesFromDSS());
/* 271 */     ocspVerifier.setRootStore(this.rootStore);
/* 272 */     ocspVerifier.setOnlineCheckingAllowed((this.latestRevision || this.onlineCheckingAllowed));
/*     */     
/* 274 */     return ocspVerifier.verify(signCert, issuerCert, signDate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void switchToPreviousRevision() throws IOException, GeneralSecurityException {
/* 283 */     LOGGER.info("Switching to previous revision.");
/* 284 */     this.latestRevision = false;
/* 285 */     this.dss = ((PdfDictionary)this.document.getCatalog().getPdfObject()).getAsDictionary(PdfName.DSS);
/* 286 */     Calendar cal = this.pkcs7.getTimeStampDate();
/* 287 */     if (cal == TimestampConstants.UNDEFINED_TIMESTAMP_DATE) {
/* 288 */       cal = this.pkcs7.getSignDate();
/*     */     }
/*     */     
/* 291 */     this.signDate = cal.getTime();
/* 292 */     List<String> names = this.sgnUtil.getSignatureNames();
/* 293 */     if (names.size() > 1) {
/* 294 */       this.signatureName = names.get(names.size() - 2);
/* 295 */       this.document = new PdfDocument(new PdfReader(this.sgnUtil.extractRevision(this.signatureName)), (new DocumentProperties()).setEventCountingMetaInfo(this.metaInfo));
/* 296 */       this.acroForm = PdfAcroForm.getAcroForm(this.document, true);
/* 297 */       this.sgnUtil = new SignatureUtil(this.document);
/* 298 */       names = this.sgnUtil.getSignatureNames();
/* 299 */       this.signatureName = names.get(names.size() - 1);
/* 300 */       this.pkcs7 = coversWholeDocument();
/* 301 */       LOGGER.info(MessageFormatUtil.format("Checking {0}signature {1}", new Object[] { this.pkcs7.isTsp() ? "document-level timestamp " : "", this.signatureName }));
/*     */     } else {
/*     */       
/* 304 */       LOGGER.info("No signatures in revision");
/* 305 */       this.pkcs7 = null;
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
/*     */   public List<X509CRL> getCRLsFromDSS() throws GeneralSecurityException, IOException {
/* 317 */     List<X509CRL> crls = new ArrayList<>();
/* 318 */     if (this.dss == null)
/* 319 */       return crls; 
/* 320 */     PdfArray crlarray = this.dss.getAsArray(PdfName.CRLs);
/* 321 */     if (crlarray == null)
/* 322 */       return crls; 
/* 323 */     for (int i = 0; i < crlarray.size(); i++) {
/* 324 */       PdfStream stream = crlarray.getAsStream(i);
/* 325 */       crls.add((X509CRL)SignUtils.parseCrlFromStream(new ByteArrayInputStream(stream.getBytes())));
/*     */     } 
/* 327 */     return crls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BasicOCSPResp> getOCSPResponsesFromDSS() throws IOException, GeneralSecurityException {
/* 337 */     List<BasicOCSPResp> ocsps = new ArrayList<>();
/* 338 */     if (this.dss == null)
/* 339 */       return ocsps; 
/* 340 */     PdfArray ocsparray = this.dss.getAsArray(PdfName.OCSPs);
/* 341 */     if (ocsparray == null)
/* 342 */       return ocsps; 
/* 343 */     for (int i = 0; i < ocsparray.size(); i++) {
/* 344 */       PdfStream stream = ocsparray.getAsStream(i);
/* 345 */       OCSPResp ocspResponse = new OCSPResp(stream.getBytes());
/* 346 */       if (ocspResponse.getStatus() == 0)
/*     */         try {
/* 348 */           ocsps.add((BasicOCSPResp)ocspResponse.getResponseObject());
/* 349 */         } catch (OCSPException e) {
/* 350 */           throw new GeneralSecurityException(e.toString());
/*     */         }  
/*     */     } 
/* 353 */     return ocsps;
/*     */   }
/*     */   
/*     */   protected void initLtvVerifier(PdfDocument document) throws GeneralSecurityException {
/* 357 */     this.document = document;
/* 358 */     this.acroForm = PdfAcroForm.getAcroForm(document, true);
/* 359 */     this.sgnUtil = new SignatureUtil(document);
/* 360 */     List<String> names = this.sgnUtil.getSignatureNames();
/* 361 */     this.signatureName = names.get(names.size() - 1);
/* 362 */     this.signDate = DateTimeUtil.getCurrentTimeDate();
/* 363 */     this.pkcs7 = coversWholeDocument();
/* 364 */     LOGGER.info(MessageFormatUtil.format("Checking {0}signature {1}", new Object[] { this.pkcs7.isTsp() ? "document-level timestamp " : "", this.signatureName }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfPKCS7 coversWholeDocument() throws GeneralSecurityException {
/* 374 */     PdfPKCS7 pkcs7 = this.sgnUtil.readSignatureData(this.signatureName, this.securityProviderCode);
/* 375 */     if (this.sgnUtil.signatureCoversWholeDocument(this.signatureName)) {
/* 376 */       LOGGER.info("The timestamp covers whole document.");
/*     */     } else {
/*     */       
/* 379 */       throw new VerificationException((Certificate)null, "Signature doesn't cover whole document.");
/*     */     } 
/* 381 */     if (pkcs7.verifySignatureIntegrityAndAuthenticity()) {
/* 382 */       LOGGER.info("The signed document has not been modified.");
/* 383 */       return pkcs7;
/*     */     } 
/*     */     
/* 386 */     throw new VerificationException((Certificate)null, "The document was altered after the final signature was applied.");
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/LtvVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */