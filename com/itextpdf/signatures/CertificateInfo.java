/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.ASN1Sequence;
/*     */ import org.bouncycastle.asn1.ASN1Set;
/*     */ import org.bouncycastle.asn1.ASN1String;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateInfo
/*     */ {
/*     */   public static class X500Name
/*     */   {
/*  79 */     public static final ASN1ObjectIdentifier C = new ASN1ObjectIdentifier("2.5.4.6");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     public static final ASN1ObjectIdentifier O = new ASN1ObjectIdentifier("2.5.4.10");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     public static final ASN1ObjectIdentifier OU = new ASN1ObjectIdentifier("2.5.4.11");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     public static final ASN1ObjectIdentifier T = new ASN1ObjectIdentifier("2.5.4.12");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     public static final ASN1ObjectIdentifier CN = new ASN1ObjectIdentifier("2.5.4.3");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     public static final ASN1ObjectIdentifier SN = new ASN1ObjectIdentifier("2.5.4.5");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     public static final ASN1ObjectIdentifier L = new ASN1ObjectIdentifier("2.5.4.7");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     public static final ASN1ObjectIdentifier ST = new ASN1ObjectIdentifier("2.5.4.8");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     public static final ASN1ObjectIdentifier SURNAME = new ASN1ObjectIdentifier("2.5.4.4");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     public static final ASN1ObjectIdentifier GIVENNAME = new ASN1ObjectIdentifier("2.5.4.42");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     public static final ASN1ObjectIdentifier INITIALS = new ASN1ObjectIdentifier("2.5.4.43");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     public static final ASN1ObjectIdentifier GENERATION = new ASN1ObjectIdentifier("2.5.4.44");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     public static final ASN1ObjectIdentifier EmailAddress = new ASN1ObjectIdentifier("1.2.840.113549.1.9.1");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     public static final ASN1ObjectIdentifier E = EmailAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     public static final ASN1ObjectIdentifier DC = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     public static final ASN1ObjectIdentifier UID = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     public static final Map<ASN1ObjectIdentifier, String> DefaultSymbols = new HashMap<>();
/*     */     
/*     */     static {
/* 169 */       DefaultSymbols.put(C, "C");
/* 170 */       DefaultSymbols.put(O, "O");
/* 171 */       DefaultSymbols.put(T, "T");
/* 172 */       DefaultSymbols.put(OU, "OU");
/* 173 */       DefaultSymbols.put(CN, "CN");
/* 174 */       DefaultSymbols.put(L, "L");
/* 175 */       DefaultSymbols.put(ST, "ST");
/* 176 */       DefaultSymbols.put(SN, "SN");
/* 177 */       DefaultSymbols.put(EmailAddress, "E");
/* 178 */       DefaultSymbols.put(DC, "DC");
/* 179 */       DefaultSymbols.put(UID, "UID");
/* 180 */       DefaultSymbols.put(SURNAME, "SURNAME");
/* 181 */       DefaultSymbols.put(GIVENNAME, "GIVENNAME");
/* 182 */       DefaultSymbols.put(INITIALS, "INITIALS");
/* 183 */       DefaultSymbols.put(GENERATION, "GENERATION");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     public Map<String, List<String>> values = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X500Name(ASN1Sequence seq) {
/* 198 */       Enumeration<ASN1Set> e = seq.getObjects();
/*     */       
/* 200 */       while (e.hasMoreElements()) {
/* 201 */         ASN1Set set = e.nextElement();
/*     */         
/* 203 */         for (int i = 0; i < set.size(); i++) {
/* 204 */           ASN1Sequence s = (ASN1Sequence)set.getObjectAt(i);
/* 205 */           String id = DefaultSymbols.get(s.getObjectAt(0));
/* 206 */           if (id != null) {
/*     */             
/* 208 */             List<String> vs = this.values.get(id);
/* 209 */             if (vs == null) {
/* 210 */               vs = new ArrayList<>();
/* 211 */               this.values.put(id, vs);
/*     */             } 
/* 213 */             vs.add(((ASN1String)s.getObjectAt(1)).getString());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X500Name(String dirName) {
/* 224 */       CertificateInfo.X509NameTokenizer nTok = new CertificateInfo.X509NameTokenizer(dirName);
/*     */       
/* 226 */       while (nTok.hasMoreTokens()) {
/* 227 */         String token = nTok.nextToken();
/* 228 */         int index = token.indexOf('=');
/*     */         
/* 230 */         if (index == -1) {
/* 231 */           throw new IllegalArgumentException();
/*     */         }
/*     */         
/* 234 */         String id = token.substring(0, index).toUpperCase();
/* 235 */         String value = token.substring(index + 1);
/* 236 */         List<String> vs = this.values.get(id);
/* 237 */         if (vs == null) {
/* 238 */           vs = new ArrayList<>();
/* 239 */           this.values.put(id, vs);
/*     */         } 
/* 241 */         vs.add(value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getField(String name) {
/* 253 */       List<String> vs = this.values.get(name);
/* 254 */       return (vs == null) ? null : vs.get(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<String> getFieldArray(String name) {
/* 264 */       return this.values.get(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, List<String>> getFields() {
/* 273 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 278 */       return this.values.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class X509NameTokenizer
/*     */   {
/*     */     private String oid;
/*     */     
/*     */     private int index;
/*     */     
/* 289 */     private StringBuffer buf = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509NameTokenizer(String oid) {
/* 297 */       this.oid = oid;
/* 298 */       this.index = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasMoreTokens() {
/* 307 */       return (this.index != this.oid.length());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String nextToken() {
/* 316 */       if (this.index == this.oid.length()) {
/* 317 */         return null;
/*     */       }
/*     */       
/* 320 */       int end = this.index + 1;
/* 321 */       boolean quoted = false;
/* 322 */       boolean escaped = false;
/*     */       
/* 324 */       this.buf.setLength(0);
/*     */       
/* 326 */       while (end != this.oid.length()) {
/* 327 */         char c = this.oid.charAt(end);
/*     */         
/* 329 */         if (c == '"') {
/* 330 */           if (!escaped) {
/* 331 */             quoted = !quoted;
/*     */           } else {
/*     */             
/* 334 */             this.buf.append(c);
/*     */           } 
/* 336 */           escaped = false;
/*     */         
/*     */         }
/* 339 */         else if (escaped || quoted) {
/* 340 */           this.buf.append(c);
/* 341 */           escaped = false;
/*     */         }
/* 343 */         else if (c == '\\') {
/* 344 */           escaped = true;
/*     */         } else {
/* 346 */           if (c == ',') {
/*     */             break;
/*     */           }
/*     */           
/* 350 */           this.buf.append(c);
/*     */         } 
/*     */         
/* 353 */         end++;
/*     */       } 
/*     */       
/* 356 */       this.index = end;
/* 357 */       return this.buf.toString().trim();
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
/*     */   public static X500Name getIssuerFields(X509Certificate cert) {
/*     */     try {
/* 371 */       return new X500Name((ASN1Sequence)getIssuer(cert.getTBSCertificate()));
/*     */     }
/* 373 */     catch (Exception e) {
/* 374 */       throw new PdfException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ASN1Primitive getIssuer(byte[] enc) {
/*     */     try {
/* 386 */       ASN1InputStream in = new ASN1InputStream(new ByteArrayInputStream(enc));
/* 387 */       ASN1Sequence seq = (ASN1Sequence)in.readObject();
/* 388 */       return (ASN1Primitive)seq.getObjectAt((seq.getObjectAt(0) instanceof org.bouncycastle.asn1.ASN1TaggedObject) ? 3 : 2);
/*     */     }
/* 390 */     catch (IOException e) {
/* 391 */       throw new PdfException(e);
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
/*     */   public static X500Name getSubjectFields(X509Certificate cert) {
/*     */     try {
/* 405 */       if (cert != null) {
/* 406 */         return new X500Name((ASN1Sequence)getSubject(cert.getTBSCertificate()));
/*     */       }
/* 408 */     } catch (Exception e) {
/* 409 */       throw new PdfException(e);
/*     */     } 
/* 411 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ASN1Primitive getSubject(byte[] enc) {
/*     */     try {
/* 422 */       ASN1InputStream in = new ASN1InputStream(new ByteArrayInputStream(enc));
/* 423 */       ASN1Sequence seq = (ASN1Sequence)in.readObject();
/* 424 */       return (ASN1Primitive)seq.getObjectAt((seq.getObjectAt(0) instanceof org.bouncycastle.asn1.ASN1TaggedObject) ? 5 : 4);
/*     */     }
/* 426 */     catch (IOException e) {
/* 427 */       throw new PdfException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/CertificateInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */