/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
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
/*     */ public class CrlClientOnline
/*     */   implements ICrlClient
/*     */ {
/*  71 */   private static final Logger LOGGER = LoggerFactory.getLogger(CrlClientOnline.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   protected List<URL> urls = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrlClientOnline() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrlClientOnline(String... crls) {
/*  91 */     for (String url : crls) {
/*  92 */       addUrl(url);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrlClientOnline(URL... crls) {
/* 102 */     for (URL url : crls) {
/* 103 */       addUrl(url);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrlClientOnline(Certificate[] chain) {
/* 113 */     for (int i = 0; i < chain.length; i++) {
/* 114 */       X509Certificate cert = (X509Certificate)chain[i];
/* 115 */       LOGGER.info("Checking certificate: " + cert.getSubjectDN());
/* 116 */       String url = null;
/*     */       try {
/* 118 */         url = CertificateUtil.getCRLURL(cert);
/* 119 */         if (url != null) {
/* 120 */           addUrl(url);
/*     */         }
/* 122 */       } catch (CertificateParsingException e) {
/* 123 */         LOGGER.info("Skipped CRL url (certificate could not be parsed)");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<byte[]> getEncoded(X509Certificate checkCert, String url) {
/* 139 */     if (checkCert == null)
/* 140 */       return null; 
/* 141 */     List<URL> urllist = new ArrayList<>(this.urls);
/* 142 */     if (urllist.size() == 0) {
/* 143 */       LOGGER.info("Looking for CRL for certificate " + checkCert.getSubjectDN());
/*     */       try {
/* 145 */         if (url == null)
/* 146 */           url = CertificateUtil.getCRLURL(checkCert); 
/* 147 */         if (url == null)
/* 148 */           throw new IllegalArgumentException("Passed url can not be null."); 
/* 149 */         urllist.add(new URL(url));
/* 150 */         LOGGER.info("Found CRL url: " + url);
/* 151 */       } catch (Exception e) {
/* 152 */         LOGGER.info("Skipped CRL url: " + e.getMessage());
/*     */       } 
/*     */     } 
/* 155 */     List<byte[]> ar = (List)new ArrayList<>();
/* 156 */     for (URL urlt : urllist) {
/*     */       try {
/* 158 */         LOGGER.info("Checking CRL: " + urlt);
/* 159 */         InputStream inp = SignUtils.getHttpResponse(urlt);
/* 160 */         byte[] buf = new byte[1024];
/* 161 */         ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*     */         while (true) {
/* 163 */           int n = inp.read(buf, 0, buf.length);
/* 164 */           if (n <= 0)
/*     */             break; 
/* 166 */           bout.write(buf, 0, n);
/*     */         } 
/* 168 */         inp.close();
/* 169 */         ar.add(bout.toByteArray());
/* 170 */         LOGGER.info("Added CRL found at: " + urlt);
/* 171 */       } catch (Exception e) {
/* 172 */         LOGGER.info("Skipped CRL: " + e.getMessage() + " for " + urlt);
/*     */       } 
/*     */     } 
/* 175 */     return (Collection<byte[]>)ar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addUrl(String url) {
/*     */     try {
/* 185 */       addUrl(new URL(url));
/* 186 */     } catch (IOException e) {
/* 187 */       LOGGER.info("Skipped CRL url (malformed): " + url);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addUrl(URL url) {
/* 197 */     if (this.urls.contains(url)) {
/* 198 */       LOGGER.info("Skipped CRL url (duplicate): " + url);
/*     */       return;
/*     */     } 
/* 201 */     this.urls.add(url);
/* 202 */     LOGGER.info("Added CRL url: " + url);
/*     */   }
/*     */   
/*     */   public int getUrlsSize() {
/* 206 */     return this.urls.size();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/CrlClientOnline.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */