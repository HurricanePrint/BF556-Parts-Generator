/*     */ package com.itextpdf.signatures;
/*     */ 
/*     */ import com.itextpdf.forms.PdfAcroForm;
/*     */ import com.itextpdf.forms.fields.PdfFormField;
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.source.IRandomAccessSource;
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import com.itextpdf.io.source.RASInputStream;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import com.itextpdf.io.source.WindowRandomAccessSource;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDate;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNull;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfReader;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class SignatureUtil
/*     */ {
/*     */   private PdfDocument document;
/*     */   private PdfAcroForm acroForm;
/*     */   private Map<String, int[]> sigNames;
/*     */   private List<String> orderedSignatureNames;
/*     */   private int totalRevisions;
/*     */   
/*     */   @Deprecated
/*     */   public static long[] asLongArray(PdfArray pdfArray) {
/*  97 */     long[] rslt = new long[pdfArray.size()];
/*     */     
/*  99 */     for (int k = 0; k < rslt.length; k++) {
/* 100 */       rslt[k] = pdfArray.getAsNumber(k).longValue();
/*     */     }
/*     */     
/* 103 */     return rslt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureUtil(PdfDocument document) {
/* 113 */     this.document = document;
/*     */     
/* 115 */     this.acroForm = PdfAcroForm.getAcroForm(document, (document.getWriter() != null));
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PdfPKCS7 verifySignature(String name) {
/* 137 */     return readSignatureData(name, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PdfPKCS7 verifySignature(String name, String provider) {
/* 160 */     return readSignatureData(name, provider);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPKCS7 readSignatureData(String signatureFieldName) {
/* 182 */     return readSignatureData(signatureFieldName, null);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfPKCS7 readSignatureData(String signatureFieldName, String securityProvider) {
/* 203 */     PdfSignature signature = getSignature(signatureFieldName);
/* 204 */     if (signature == null)
/* 205 */       return null; 
/*     */     try {
/* 207 */       PdfName sub = signature.getSubFilter();
/* 208 */       PdfString contents = signature.getContents();
/* 209 */       PdfPKCS7 pk = null;
/* 210 */       if (sub.equals(PdfName.Adbe_x509_rsa_sha1)) {
/* 211 */         PdfString cert = ((PdfDictionary)signature.getPdfObject()).getAsString(PdfName.Cert);
/* 212 */         if (cert == null)
/* 213 */           cert = ((PdfDictionary)signature.getPdfObject()).getAsArray(PdfName.Cert).getAsString(0); 
/* 214 */         pk = new PdfPKCS7(PdfEncodings.convertToBytes(contents.getValue(), null), cert.getValueBytes(), securityProvider);
/*     */       } else {
/* 216 */         pk = new PdfPKCS7(PdfEncodings.convertToBytes(contents.getValue(), null), sub, securityProvider);
/* 217 */       }  updateByteRange(pk, signature);
/* 218 */       PdfString date = signature.getDate();
/* 219 */       if (date != null)
/* 220 */         pk.setSignDate(PdfDate.decode(date.toString())); 
/* 221 */       String signName = signature.getName();
/* 222 */       pk.setSignName(signName);
/* 223 */       String reason = signature.getReason();
/* 224 */       if (reason != null)
/* 225 */         pk.setReason(reason); 
/* 226 */       String location = signature.getLocation();
/* 227 */       if (location != null)
/* 228 */         pk.setLocation(location); 
/* 229 */       return pk;
/* 230 */     } catch (Exception e) {
/* 231 */       throw new PdfException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PdfSignature getSignature(String name) {
/* 236 */     PdfDictionary sigDict = getSignatureDictionary(name);
/* 237 */     return (sigDict != null) ? new PdfSignature(sigDict) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary getSignatureDictionary(String name) {
/* 248 */     getSignatureNames();
/* 249 */     if (this.acroForm == null || !this.sigNames.containsKey(name))
/* 250 */       return null; 
/* 251 */     PdfFormField field = this.acroForm.getField(name);
/* 252 */     PdfDictionary merged = (PdfDictionary)field.getPdfObject();
/* 253 */     return merged.getAsDictionary(PdfName.V);
/*     */   }
/*     */   
/*     */   private void updateByteRange(PdfPKCS7 pkcs7, PdfSignature signature) {
/*     */     RASInputStream rASInputStream;
/* 258 */     PdfArray b = signature.getByteRange();
/* 259 */     RandomAccessFileOrArray rf = this.document.getReader().getSafeFile();
/* 260 */     InputStream rg = null;
/*     */     try {
/* 262 */       rASInputStream = new RASInputStream((new RandomAccessSourceFactory()).createRanged(rf.createSourceView(), b.toLongArray()));
/* 263 */       byte[] buf = new byte[8192];
/*     */       int rd;
/* 265 */       while ((rd = rASInputStream.read(buf, 0, buf.length)) > 0) {
/* 266 */         pkcs7.update(buf, 0, rd);
/*     */       }
/* 268 */     } catch (Exception e) {
/* 269 */       throw new PdfException(e);
/*     */     } finally {
/*     */       try {
/* 272 */         if (rASInputStream != null) rASInputStream.close(); 
/* 273 */       } catch (IOException e) {
/*     */         
/* 275 */         throw new PdfException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getSignatureNames() {
/* 286 */     if (this.sigNames != null)
/* 287 */       return new ArrayList<>(this.orderedSignatureNames); 
/* 288 */     this.sigNames = (Map)new HashMap<>();
/* 289 */     this.orderedSignatureNames = new ArrayList<>();
/* 290 */     populateSignatureNames();
/*     */     
/* 292 */     return new ArrayList<>(this.orderedSignatureNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getBlankSignatureNames() {
/* 301 */     getSignatureNames();
/* 302 */     List<String> sigs = new ArrayList<>();
/* 303 */     if (this.acroForm != null) {
/* 304 */       for (Map.Entry<String, PdfFormField> entry : (Iterable<Map.Entry<String, PdfFormField>>)this.acroForm.getFormFields().entrySet()) {
/* 305 */         PdfFormField field = entry.getValue();
/* 306 */         PdfDictionary merged = (PdfDictionary)field.getPdfObject();
/* 307 */         if (!PdfName.Sig.equals(merged.getAsName(PdfName.FT)))
/*     */           continue; 
/* 309 */         if (this.sigNames.containsKey(entry.getKey()))
/*     */           continue; 
/* 311 */         sigs.add(entry.getKey());
/*     */       } 
/*     */     }
/* 314 */     return sigs;
/*     */   }
/*     */   
/*     */   public int getTotalRevisions() {
/* 318 */     getSignatureNames();
/* 319 */     return this.totalRevisions;
/*     */   }
/*     */   
/*     */   public int getRevision(String field) {
/* 323 */     getSignatureNames();
/* 324 */     field = getTranslatedFieldName(field);
/* 325 */     if (!this.sigNames.containsKey(field))
/* 326 */       return 0; 
/* 327 */     return ((int[])this.sigNames.get(field))[1];
/*     */   }
/*     */   
/*     */   public String getTranslatedFieldName(String name) {
/* 331 */     if (this.acroForm != null && this.acroForm.getXfaForm().isXfaPresent()) {
/* 332 */       String namex = this.acroForm.getXfaForm().findFieldName(name);
/* 333 */       if (namex != null)
/* 334 */         name = namex; 
/*     */     } 
/* 336 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream extractRevision(String field) throws IOException {
/* 347 */     getSignatureNames();
/* 348 */     if (!this.sigNames.containsKey(field))
/* 349 */       return null; 
/* 350 */     int length = ((int[])this.sigNames.get(field))[0];
/* 351 */     RandomAccessFileOrArray raf = this.document.getReader().getSafeFile();
/* 352 */     return (InputStream)new RASInputStream((IRandomAccessSource)new WindowRandomAccessSource(raf.createSourceView(), 0L, length));
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
/*     */   public boolean signatureCoversWholeDocument(String name) {
/* 366 */     getSignatureNames();
/* 367 */     if (!this.sigNames.containsKey(name))
/* 368 */       return false; 
/*     */     try {
/* 370 */       ContentsChecker signatureReader = new ContentsChecker(this.document.getReader().getSafeFile().createSourceView());
/* 371 */       return signatureReader.checkWhetherSignatureCoversWholeDocument(this.acroForm.getField(name));
/* 372 */     } catch (IOException e) {
/* 373 */       throw new PdfException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesSignatureFieldExist(String name) {
/* 384 */     return (getBlankSignatureNames().contains(name) || getSignatureNames().contains(name));
/*     */   }
/*     */   
/*     */   private void populateSignatureNames() {
/* 388 */     if (this.acroForm == null) {
/*     */       return;
/*     */     }
/*     */     
/* 392 */     List<Object[]> sorter = new ArrayList();
/* 393 */     for (Map.Entry<String, PdfFormField> entry : (Iterable<Map.Entry<String, PdfFormField>>)this.acroForm.getFormFields().entrySet()) {
/* 394 */       PdfFormField field = entry.getValue();
/* 395 */       PdfDictionary merged = (PdfDictionary)field.getPdfObject();
/* 396 */       if (!PdfName.Sig.equals(merged.get(PdfName.FT)))
/*     */         continue; 
/* 398 */       PdfDictionary v = merged.getAsDictionary(PdfName.V);
/* 399 */       if (v == null)
/*     */         continue; 
/* 401 */       PdfString contents = v.getAsString(PdfName.Contents);
/* 402 */       if (contents == null) {
/*     */         continue;
/*     */       }
/* 405 */       contents.markAsUnencryptedObject();
/*     */       
/* 407 */       PdfArray ro = v.getAsArray(PdfName.ByteRange);
/* 408 */       if (ro == null)
/*     */         continue; 
/* 410 */       int rangeSize = ro.size();
/* 411 */       if (rangeSize < 2)
/*     */         continue; 
/* 413 */       int length = ro.getAsNumber(rangeSize - 1).intValue() + ro.getAsNumber(rangeSize - 2).intValue();
/* 414 */       sorter.add(new Object[] { entry.getKey(), { length, 0 } });
/*     */     } 
/* 416 */     Collections.sort(sorter, new SorterComparator());
/* 417 */     if (sorter.size() > 0) {
/*     */       try {
/* 419 */         if (((int[])((Object[])sorter.get(sorter.size() - 1))[1])[0] == this.document.getReader().getFileLength())
/* 420 */         { this.totalRevisions = sorter.size(); }
/*     */         else
/* 422 */         { this.totalRevisions = sorter.size() + 1; } 
/* 423 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 426 */       for (int k = 0; k < sorter.size(); k++) {
/* 427 */         Object[] objs = sorter.get(k);
/* 428 */         String name = (String)objs[0];
/* 429 */         int[] p = (int[])objs[1];
/* 430 */         p[1] = k + 1;
/* 431 */         this.sigNames.put(name, p);
/* 432 */         this.orderedSignatureNames.add(name);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class SorterComparator implements Comparator<Object[]> { private SorterComparator() {}
/*     */     
/*     */     public int compare(Object[] o1, Object[] o2) {
/* 440 */       int n1 = ((int[])o1[1])[0];
/* 441 */       int n2 = ((int[])o2[1])[0];
/* 442 */       return n1 - n2;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class ContentsChecker
/*     */     extends PdfReader
/*     */   {
/*     */     private long contentsStart;
/*     */     private long contentsEnd;
/* 451 */     private int currentLevel = 0;
/* 452 */     private int contentsLevel = 1;
/*     */     
/*     */     private boolean searchInV = true;
/*     */     
/*     */     private boolean rangeIsCorrect = false;
/*     */     
/*     */     public ContentsChecker(IRandomAccessSource byteSource) throws IOException {
/* 459 */       super(byteSource, null);
/*     */     }
/*     */     public boolean checkWhetherSignatureCoversWholeDocument(PdfFormField signatureField) {
/*     */       long signatureOffset;
/* 463 */       this.rangeIsCorrect = false;
/* 464 */       PdfDictionary signature = (PdfDictionary)signatureField.getValue();
/* 465 */       int[] byteRange = ((PdfArray)signature.get(PdfName.ByteRange)).toIntArray();
/*     */       try {
/* 467 */         if (4 != byteRange.length || 0 != byteRange[0] || this.tokens.getSafeFile().length() != (byteRange[2] + byteRange[3])) {
/* 468 */           return false;
/*     */         }
/* 470 */       } catch (IOException e) {
/*     */         
/* 472 */         return false;
/*     */       } 
/*     */       
/* 475 */       this.contentsStart = byteRange[1];
/* 476 */       this.contentsEnd = byteRange[2];
/*     */ 
/*     */       
/* 479 */       if (null != signature.getIndirectReference()) {
/* 480 */         signatureOffset = signature.getIndirectReference().getOffset();
/* 481 */         this.searchInV = true;
/*     */       } else {
/* 483 */         signatureOffset = ((PdfDictionary)signatureField.getPdfObject()).getIndirectReference().getOffset();
/* 484 */         this.searchInV = false;
/* 485 */         this.contentsLevel++;
/*     */       } 
/*     */       
/*     */       try {
/* 489 */         this.tokens.seek(signatureOffset);
/* 490 */         this.tokens.nextValidToken();
/* 491 */         readObject(false, false);
/* 492 */       } catch (IOException e) {
/*     */         
/* 494 */         return false;
/*     */       } 
/*     */       
/* 497 */       return this.rangeIsCorrect;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected PdfDictionary readDictionary(boolean objStm) throws IOException {
/* 504 */       this.currentLevel++;
/* 505 */       PdfDictionary dic = new PdfDictionary();
/* 506 */       while (!this.rangeIsCorrect) {
/* 507 */         PdfObject obj; this.tokens.nextValidToken();
/* 508 */         if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic) {
/* 509 */           this.currentLevel--;
/*     */           break;
/*     */         } 
/* 512 */         if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Name) {
/* 513 */           this.tokens.throwError("Dictionary key {0} is not a name.", new Object[] { this.tokens.getStringValue() });
/*     */         }
/* 515 */         PdfName name = readPdfName(true);
/*     */         
/* 517 */         if (PdfName.Contents.equals(name) && this.searchInV && this.contentsLevel == this.currentLevel) {
/* 518 */           int ch; long startPosition = this.tokens.getPosition();
/*     */           
/* 520 */           int whiteSpacesCount = -1;
/*     */           do {
/* 522 */             ch = this.tokens.read();
/* 523 */             whiteSpacesCount++;
/* 524 */           } while (ch != -1 && PdfTokenizer.isWhitespace(ch));
/* 525 */           this.tokens.seek(startPosition);
/* 526 */           obj = readObject(true, objStm);
/* 527 */           long endPosition = this.tokens.getPosition();
/* 528 */           if (endPosition == this.contentsEnd && startPosition + whiteSpacesCount == this.contentsStart) {
/* 529 */             this.rangeIsCorrect = true;
/*     */           }
/* 531 */         } else if (PdfName.V.equals(name) && !this.searchInV && 1 == this.currentLevel) {
/* 532 */           this.searchInV = true;
/* 533 */           obj = readObject(true, objStm);
/* 534 */           this.searchInV = false;
/*     */         } else {
/* 536 */           obj = readObject(true, objStm);
/*     */         } 
/* 538 */         if (obj == null) {
/* 539 */           if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic)
/* 540 */             this.tokens.throwError("unexpected >>.", new Object[0]); 
/* 541 */           if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndArray)
/* 542 */             this.tokens.throwError("Unexpected close bracket.", new Object[0]); 
/*     */         } 
/* 544 */         dic.put(name, obj);
/*     */       } 
/* 546 */       return dic;
/*     */     }
/*     */ 
/*     */     
/*     */     protected PdfObject readReference(boolean readAsDirect) {
/* 551 */       return (PdfObject)new PdfNull();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/signatures/SignatureUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */