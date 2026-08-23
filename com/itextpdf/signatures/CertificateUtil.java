/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.ASN1OctetString;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.ASN1Sequence;
/*     */ import org.bouncycastle.asn1.ASN1TaggedObject;
/*     */ import org.bouncycastle.asn1.DERIA5String;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.x509.CRLDistPoint;
/*     */ import org.bouncycastle.asn1.x509.DistributionPoint;
/*     */ import org.bouncycastle.asn1.x509.DistributionPointName;
/*     */ import org.bouncycastle.asn1.x509.Extension;
/*     */ import org.bouncycastle.asn1.x509.GeneralName;
/*     */ import org.bouncycastle.asn1.x509.GeneralNames;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateUtil
/*     */ {
/*     */   public static CRL getCRL(X509Certificate certificate) throws CertificateException, CRLException, IOException {
/*  89 */     return getCRL(getCRLURL(certificate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCRLURL(X509Certificate certificate) throws CertificateParsingException {
/*     */     ASN1Primitive obj;
/*     */     try {
/* 102 */       obj = getExtensionValue(certificate, Extension.cRLDistributionPoints.getId());
/* 103 */     } catch (IOException e) {
/* 104 */       obj = (ASN1Primitive)null;
/*     */     } 
/* 106 */     if (obj == null) {
/* 107 */       return null;
/*     */     }
/* 109 */     CRLDistPoint dist = CRLDistPoint.getInstance(obj);
/* 110 */     DistributionPoint[] dists = dist.getDistributionPoints();
/* 111 */     for (DistributionPoint p : dists) {
/* 112 */       DistributionPointName distributionPointName = p.getDistributionPoint();
/* 113 */       if (0 == distributionPointName.getType()) {
/*     */ 
/*     */         
/* 116 */         GeneralNames generalNames = (GeneralNames)distributionPointName.getName();
/* 117 */         GeneralName[] names = generalNames.getNames(); GeneralName[] arrayOfGeneralName1; int i; byte b;
/* 118 */         for (arrayOfGeneralName1 = names, i = arrayOfGeneralName1.length, b = 0; b < i; ) { GeneralName name = arrayOfGeneralName1[b];
/* 119 */           if (name.getTagNo() != 6) {
/*     */             b++; continue;
/*     */           } 
/* 122 */           DERIA5String derStr = DERIA5String.getInstance((ASN1TaggedObject)name.toASN1Primitive(), false);
/* 123 */           return derStr.getString(); }
/*     */       
/*     */       } 
/* 126 */     }  return null;
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
/*     */   public static CRL getCRL(String url) throws IOException, CertificateException, CRLException {
/* 139 */     if (url == null)
/* 140 */       return null; 
/* 141 */     return SignUtils.parseCrlFromStream((new URL(url)).openStream());
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
/*     */   public static String getOCSPURL(X509Certificate certificate) {
/*     */     try {
/* 154 */       ASN1Primitive obj = getExtensionValue(certificate, Extension.authorityInfoAccess.getId());
/* 155 */       if (obj == null) {
/* 156 */         return null;
/*     */       }
/* 158 */       ASN1Sequence AccessDescriptions = (ASN1Sequence)obj;
/* 159 */       for (int i = 0; i < AccessDescriptions.size(); i++) {
/* 160 */         ASN1Sequence AccessDescription = (ASN1Sequence)AccessDescriptions.getObjectAt(i);
/* 161 */         if (AccessDescription.size() == 2)
/*     */         {
/*     */           
/* 164 */           if (AccessDescription.getObjectAt(0) instanceof ASN1ObjectIdentifier) {
/* 165 */             ASN1ObjectIdentifier id = (ASN1ObjectIdentifier)AccessDescription.getObjectAt(0);
/* 166 */             if ("1.3.6.1.5.5.7.48.1".equals(id.getId())) {
/* 167 */               ASN1Primitive description = (ASN1Primitive)AccessDescription.getObjectAt(1);
/* 168 */               String AccessLocation = getStringFromGeneralName(description);
/* 169 */               if (AccessLocation == null) {
/* 170 */                 return "";
/*     */               }
/*     */               
/* 173 */               return AccessLocation;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 178 */     } catch (IOException e) {
/* 179 */       return null;
/*     */     } 
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTSAURL(X509Certificate certificate) {
/* 192 */     byte[] der = SignUtils.getExtensionValueByOid(certificate, "1.2.840.113583.1.1.9.1");
/* 193 */     if (der == null) {
/* 194 */       return null;
/*     */     }
/*     */     try {
/* 197 */       ASN1Primitive asn1obj = ASN1Primitive.fromByteArray(der);
/* 198 */       DEROctetString octets = (DEROctetString)asn1obj;
/* 199 */       asn1obj = ASN1Primitive.fromByteArray(octets.getOctets());
/* 200 */       ASN1Sequence asn1seq = ASN1Sequence.getInstance(asn1obj);
/* 201 */       return getStringFromGeneralName(asn1seq.getObjectAt(1).toASN1Primitive());
/* 202 */     } catch (IOException e) {
/* 203 */       return null;
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
/*     */   private static ASN1Primitive getExtensionValue(X509Certificate certificate, String oid) throws IOException {
/* 216 */     byte[] bytes = SignUtils.getExtensionValueByOid(certificate, oid);
/* 217 */     if (bytes == null) {
/* 218 */       return null;
/*     */     }
/* 220 */     ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(bytes));
/* 221 */     ASN1OctetString octs = (ASN1OctetString)aIn.readObject();
/* 222 */     aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
/* 223 */     return aIn.readObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getStringFromGeneralName(ASN1Primitive names) throws IOException {
/* 233 */     ASN1TaggedObject taggedObject = (ASN1TaggedObject)names;
/* 234 */     return new String(ASN1OctetString.getInstance(taggedObject, false).getOctets(), "ISO-8859-1");
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/CertificateUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */