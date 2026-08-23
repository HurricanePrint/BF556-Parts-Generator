/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.forms.PdfAcroForm;
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfCatalog;
/*     */ import com.itextpdf.kernel.pdf.PdfDeveloperExtension;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.PdfVersion;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1OctetString;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
/*     */ import org.bouncycastle.asn1.ocsp.OCSPResponse;
/*     */ import org.bouncycastle.asn1.ocsp.OCSPResponseStatus;
/*     */ import org.bouncycastle.asn1.ocsp.ResponseBytes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LtvVerification
/*     */ {
/*  95 */   private Logger LOGGER = LoggerFactory.getLogger(LtvVerification.class);
/*     */   
/*     */   private PdfDocument document;
/*     */   private SignatureUtil sgnUtil;
/*     */   private PdfAcroForm acroForm;
/* 100 */   private Map<PdfName, ValidationData> validated = new HashMap<>();
/*     */   private boolean used = false;
/* 102 */   private String securityProviderCode = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Level
/*     */   {
/* 110 */     OCSP,
/*     */ 
/*     */ 
/*     */     
/* 114 */     CRL,
/*     */ 
/*     */ 
/*     */     
/* 118 */     OCSP_CRL,
/*     */ 
/*     */ 
/*     */     
/* 122 */     OCSP_OPTIONAL_CRL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum CertificateOption
/*     */   {
/* 132 */     SIGNING_CERTIFICATE,
/*     */ 
/*     */ 
/*     */     
/* 136 */     WHOLE_CHAIN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum CertificateInclusion
/*     */   {
/* 147 */     YES,
/*     */ 
/*     */ 
/*     */     
/* 151 */     NO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LtvVerification(PdfDocument document) {
/* 161 */     this.document = document;
/* 162 */     this.acroForm = PdfAcroForm.getAcroForm(document, true);
/* 163 */     this.sgnUtil = new SignatureUtil(document);
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
/*     */   public LtvVerification(PdfDocument document, String securityProviderCode) {
/* 175 */     this(document);
/* 176 */     this.securityProviderCode = securityProviderCode;
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
/*     */ 
/*     */   
/*     */   public boolean addVerification(String signatureName, IOcspClient ocsp, ICrlClient crl, CertificateOption certOption, Level level, CertificateInclusion certInclude) throws IOException, GeneralSecurityException {
/* 194 */     if (this.used)
/* 195 */       throw new IllegalStateException("Verification already output."); 
/* 196 */     PdfPKCS7 pk = this.sgnUtil.readSignatureData(signatureName, this.securityProviderCode);
/* 197 */     this.LOGGER.info("Adding verification for " + signatureName);
/* 198 */     Certificate[] xc = pk.getCertificates();
/*     */     
/* 200 */     X509Certificate signingCert = pk.getSigningCertificate();
/* 201 */     ValidationData vd = new ValidationData();
/* 202 */     for (int k = 0; k < xc.length; k++) {
/* 203 */       X509Certificate cert = (X509Certificate)xc[k];
/* 204 */       this.LOGGER.info("Certificate: " + cert.getSubjectDN());
/* 205 */       if (certOption != CertificateOption.SIGNING_CERTIFICATE || cert
/* 206 */         .equals(signingCert)) {
/*     */ 
/*     */         
/* 209 */         byte[] ocspEnc = null;
/* 210 */         if (ocsp != null && level != Level.CRL) {
/* 211 */           ocspEnc = ocsp.getEncoded(cert, getParent(cert, xc), null);
/* 212 */           if (ocspEnc != null) {
/* 213 */             vd.ocsps.add(buildOCSPResponse(ocspEnc));
/* 214 */             this.LOGGER.info("OCSP added");
/*     */           } 
/*     */         } 
/* 217 */         if (crl != null && (level == Level.CRL || level == Level.OCSP_CRL || (level == Level.OCSP_OPTIONAL_CRL && ocspEnc == null))) {
/* 218 */           Collection<byte[]> cims = crl.getEncoded(cert, null);
/* 219 */           if (cims != null) {
/* 220 */             for (byte[] cim : cims) {
/* 221 */               boolean dup = false;
/* 222 */               for (byte[] b : vd.crls) {
/* 223 */                 if (Arrays.equals(b, cim)) {
/* 224 */                   dup = true;
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 228 */               if (!dup) {
/* 229 */                 vd.crls.add(cim);
/* 230 */                 this.LOGGER.info("CRL added");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/* 235 */         if (certInclude == CertificateInclusion.YES)
/* 236 */           vd.certs.add(cert.getEncoded()); 
/*     */       } 
/*     */     } 
/* 239 */     if (vd.crls.size() == 0 && vd.ocsps.size() == 0)
/* 240 */       return false; 
/* 241 */     this.validated.put(getSignatureHashKey(signatureName), vd);
/* 242 */     return true;
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
/*     */   private X509Certificate getParent(X509Certificate cert, Certificate[] certs) {
/* 254 */     for (int i = 0; i < certs.length; i++) {
/* 255 */       X509Certificate parent = (X509Certificate)certs[i];
/* 256 */       if (cert.getIssuerDN().equals(parent.getSubjectDN())) {
/*     */         
/*     */         try {
/* 259 */           cert.verify(parent.getPublicKey());
/* 260 */           return parent;
/* 261 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */     
/* 265 */     return null;
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
/*     */   public boolean addVerification(String signatureName, Collection<byte[]> ocsps, Collection<byte[]> crls, Collection<byte[]> certs) throws IOException, GeneralSecurityException {
/* 281 */     if (this.used)
/* 282 */       throw new IllegalStateException("Verification already output."); 
/* 283 */     ValidationData vd = new ValidationData();
/* 284 */     if (ocsps != null) {
/* 285 */       for (byte[] ocsp : ocsps) {
/* 286 */         vd.ocsps.add(buildOCSPResponse(ocsp));
/*     */       }
/*     */     }
/* 289 */     if (crls != null) {
/* 290 */       for (byte[] crl : crls) {
/* 291 */         vd.crls.add(crl);
/*     */       }
/*     */     }
/* 294 */     if (certs != null) {
/* 295 */       for (byte[] cert : certs) {
/* 296 */         vd.certs.add(cert);
/*     */       }
/*     */     }
/* 299 */     this.validated.put(getSignatureHashKey(signatureName), vd);
/* 300 */     return true;
/*     */   }
/*     */   
/*     */   private static byte[] buildOCSPResponse(byte[] basicOcspResponse) throws IOException {
/* 304 */     DEROctetString doctet = new DEROctetString(basicOcspResponse);
/* 305 */     OCSPResponseStatus respStatus = new OCSPResponseStatus(0);
/* 306 */     ResponseBytes responseBytes = new ResponseBytes(OCSPObjectIdentifiers.id_pkix_ocsp_basic, (ASN1OctetString)doctet);
/* 307 */     OCSPResponse ocspResponse = new OCSPResponse(respStatus, responseBytes);
/* 308 */     return (new OCSPResp(ocspResponse)).getEncoded();
/*     */   }
/*     */   
/*     */   private PdfName getSignatureHashKey(String signatureName) throws NoSuchAlgorithmException, IOException {
/* 312 */     PdfSignature sig = this.sgnUtil.getSignature(signatureName);
/* 313 */     PdfString contents = sig.getContents();
/* 314 */     byte[] bc = PdfEncodings.convertToBytes(contents.getValue(), null);
/* 315 */     byte[] bt = null;
/* 316 */     if (PdfName.ETSI_RFC3161.equals(sig.getSubFilter())) {
/* 317 */       ASN1InputStream din = new ASN1InputStream(new ByteArrayInputStream(bc));
/* 318 */       ASN1Primitive pkcs = din.readObject();
/* 319 */       bc = pkcs.getEncoded();
/*     */     } 
/* 321 */     bt = hashBytesSha1(bc);
/* 322 */     return new PdfName(convertToHex(bt));
/*     */   }
/*     */   
/*     */   private static byte[] hashBytesSha1(byte[] b) throws NoSuchAlgorithmException {
/* 326 */     MessageDigest sh = MessageDigest.getInstance("SHA1");
/* 327 */     return sh.digest(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void merge() {
/* 334 */     if (this.used || this.validated.size() == 0)
/*     */       return; 
/* 336 */     this.used = true;
/* 337 */     PdfDictionary catalog = (PdfDictionary)this.document.getCatalog().getPdfObject();
/* 338 */     PdfObject dss = catalog.get(PdfName.DSS);
/* 339 */     if (dss == null) {
/* 340 */       createDss();
/*     */     } else {
/* 342 */       updateDss();
/*     */     } 
/*     */   }
/*     */   private void updateDss() {
/* 346 */     PdfDictionary catalog = (PdfDictionary)this.document.getCatalog().getPdfObject();
/* 347 */     catalog.setModified();
/* 348 */     PdfDictionary dss = catalog.getAsDictionary(PdfName.DSS);
/* 349 */     PdfArray ocsps = dss.getAsArray(PdfName.OCSPs);
/* 350 */     PdfArray crls = dss.getAsArray(PdfName.CRLs);
/* 351 */     PdfArray certs = dss.getAsArray(PdfName.Certs);
/* 352 */     dss.remove(PdfName.OCSPs);
/* 353 */     dss.remove(PdfName.CRLs);
/* 354 */     dss.remove(PdfName.Certs);
/* 355 */     PdfDictionary vrim = dss.getAsDictionary(PdfName.VRI);
/*     */     
/* 357 */     if (vrim != null) {
/* 358 */       for (PdfName n : vrim.keySet()) {
/* 359 */         if (this.validated.containsKey(n)) {
/* 360 */           PdfDictionary vri = vrim.getAsDictionary(n);
/* 361 */           if (vri != null) {
/* 362 */             deleteOldReferences(ocsps, vri.getAsArray(PdfName.OCSP));
/* 363 */             deleteOldReferences(crls, vri.getAsArray(PdfName.CRL));
/* 364 */             deleteOldReferences(certs, vri.getAsArray(PdfName.Cert));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 369 */     if (ocsps == null) {
/* 370 */       ocsps = new PdfArray();
/*     */     }
/* 372 */     if (crls == null) {
/* 373 */       crls = new PdfArray();
/*     */     }
/* 375 */     if (certs == null) {
/* 376 */       certs = new PdfArray();
/*     */     }
/* 378 */     if (vrim == null) {
/* 379 */       vrim = new PdfDictionary();
/*     */     }
/* 381 */     outputDss(dss, vrim, ocsps, crls, certs);
/*     */   }
/*     */   
/*     */   private static void deleteOldReferences(PdfArray all, PdfArray toDelete) {
/* 385 */     if (all == null || toDelete == null)
/*     */       return; 
/* 387 */     for (PdfObject pi : toDelete) {
/* 388 */       PdfIndirectReference pir = pi.getIndirectReference();
/*     */       
/* 390 */       if (pir == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 394 */       for (int k = 0; k < all.size(); k++) {
/* 395 */         PdfIndirectReference pod = all.get(k).getIndirectReference();
/*     */         
/* 397 */         if (pod != null)
/*     */         {
/*     */ 
/*     */           
/* 401 */           if (pir.getObjNumber() == pod.getObjNumber()) {
/* 402 */             all.remove(k);
/* 403 */             k--;
/*     */           }  } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createDss() {
/* 410 */     outputDss(new PdfDictionary(), new PdfDictionary(), new PdfArray(), new PdfArray(), new PdfArray());
/*     */   }
/*     */   
/*     */   private void outputDss(PdfDictionary dss, PdfDictionary vrim, PdfArray ocsps, PdfArray crls, PdfArray certs) {
/* 414 */     PdfCatalog catalog = this.document.getCatalog();
/* 415 */     if (this.document.getPdfVersion().compareTo(PdfVersion.PDF_2_0) < 0) {
/* 416 */       catalog.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
/*     */     }
/* 418 */     for (PdfName vkey : this.validated.keySet()) {
/* 419 */       PdfArray ocsp = new PdfArray();
/* 420 */       PdfArray crl = new PdfArray();
/* 421 */       PdfArray cert = new PdfArray();
/* 422 */       PdfDictionary vri = new PdfDictionary();
/* 423 */       for (byte[] b : ((ValidationData)this.validated.get(vkey)).crls) {
/* 424 */         PdfStream ps = new PdfStream(b);
/* 425 */         ps.setCompressionLevel(-1);
/* 426 */         ps.makeIndirect(this.document);
/* 427 */         crl.add((PdfObject)ps);
/* 428 */         crls.add((PdfObject)ps);
/* 429 */         crls.setModified();
/*     */       } 
/* 431 */       for (byte[] b : ((ValidationData)this.validated.get(vkey)).ocsps) {
/* 432 */         PdfStream ps = new PdfStream(b);
/* 433 */         ps.setCompressionLevel(-1);
/* 434 */         ocsp.add((PdfObject)ps);
/* 435 */         ocsps.add((PdfObject)ps);
/* 436 */         ocsps.setModified();
/*     */       } 
/* 438 */       for (byte[] b : ((ValidationData)this.validated.get(vkey)).certs) {
/* 439 */         PdfStream ps = new PdfStream(b);
/* 440 */         ps.setCompressionLevel(-1);
/* 441 */         ps.makeIndirect(this.document);
/* 442 */         cert.add((PdfObject)ps);
/* 443 */         certs.add((PdfObject)ps);
/* 444 */         certs.setModified();
/*     */       } 
/* 446 */       if (ocsp.size() > 0) {
/* 447 */         ocsp.makeIndirect(this.document);
/* 448 */         vri.put(PdfName.OCSP, (PdfObject)ocsp);
/*     */       } 
/* 450 */       if (crl.size() > 0) {
/* 451 */         crl.makeIndirect(this.document);
/* 452 */         vri.put(PdfName.CRL, (PdfObject)crl);
/*     */       } 
/* 454 */       if (cert.size() > 0) {
/* 455 */         cert.makeIndirect(this.document);
/* 456 */         vri.put(PdfName.Cert, (PdfObject)cert);
/*     */       } 
/* 458 */       vri.makeIndirect(this.document);
/* 459 */       vrim.put(vkey, (PdfObject)vri);
/*     */     } 
/* 461 */     vrim.makeIndirect(this.document);
/* 462 */     vrim.setModified();
/* 463 */     dss.put(PdfName.VRI, (PdfObject)vrim);
/* 464 */     if (ocsps.size() > 0) {
/* 465 */       ocsps.makeIndirect(this.document);
/* 466 */       dss.put(PdfName.OCSPs, (PdfObject)ocsps);
/*     */     } 
/* 468 */     if (crls.size() > 0) {
/* 469 */       crls.makeIndirect(this.document);
/* 470 */       dss.put(PdfName.CRLs, (PdfObject)crls);
/*     */     } 
/* 472 */     if (certs.size() > 0) {
/* 473 */       certs.makeIndirect(this.document);
/* 474 */       dss.put(PdfName.Certs, (PdfObject)certs);
/*     */     } 
/*     */     
/* 477 */     dss.makeIndirect(this.document);
/* 478 */     dss.setModified();
/* 479 */     catalog.put(PdfName.DSS, (PdfObject)dss);
/*     */   }
/*     */   
/*     */   private static class ValidationData {
/* 483 */     public List<byte[]> crls = (List)new ArrayList<>(); private ValidationData() {}
/* 484 */     public List<byte[]> ocsps = (List)new ArrayList<>();
/* 485 */     public List<byte[]> certs = (List)new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertToHex(byte[] bytes) {
/* 496 */     ByteBuffer buf = new ByteBuffer();
/* 497 */     for (byte b : bytes) {
/* 498 */       buf.appendHex(b);
/*     */     }
/* 500 */     return PdfEncodings.convertToString(buf.toByteArray(), null).toUpperCase();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/LtvVerification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */