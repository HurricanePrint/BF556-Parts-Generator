/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.io.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class PdfWriter
/*     */   extends PdfOutputStream
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6875544505477707103L;
/*  71 */   private static final byte[] obj = ByteUtils.getIsoBytes(" obj\n");
/*  72 */   private static final byte[] endobj = ByteUtils.getIsoBytes("\nendobj\n");
/*     */ 
/*     */   
/*  75 */   private PdfOutputStream duplicateStream = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected WriterProperties properties;
/*     */ 
/*     */ 
/*     */   
/*  83 */   PdfObjectStream objectStream = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private Map<PdfDocument.IndirectRefDescription, PdfIndirectReference> copiedObjects = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private SmartModePdfObjectsSerializer smartModeSerializer = new SmartModePdfObjectsSerializer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isUserWarnedAboutAcroFormCopying;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWriter(File file) throws FileNotFoundException {
/* 109 */     this(file.getAbsolutePath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWriter(OutputStream os) {
/* 118 */     this(os, new WriterProperties());
/*     */   }
/*     */   
/*     */   public PdfWriter(OutputStream os, WriterProperties properties) {
/* 122 */     super(FileUtil.wrapWithBufferedOutputStream(os));
/* 123 */     this.properties = properties;
/* 124 */     if (properties.debugMode) {
/* 125 */       setDebugMode();
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
/*     */   public PdfWriter(String filename) throws FileNotFoundException {
/* 138 */     this(filename, new WriterProperties());
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
/*     */   public PdfWriter(String filename, WriterProperties properties) throws FileNotFoundException {
/* 151 */     this(FileUtil.getBufferedOutputStream(filename), properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullCompression() {
/* 160 */     return (this.properties.isFullCompression != null) ? this.properties.isFullCompression.booleanValue() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCompressionLevel() {
/* 170 */     return this.properties.compressionLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfWriter setCompressionLevel(int compressionLevel) {
/* 181 */     this.properties.setCompressionLevel(compressionLevel);
/* 182 */     return this;
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
/*     */   public PdfWriter setSmartMode(boolean smartMode) {
/* 198 */     this.properties.smartMode = smartMode;
/* 199 */     return this;
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
/*     */   public void write(int b) throws IOException {
/* 212 */     super.write(b);
/* 213 */     if (this.duplicateStream != null) {
/* 214 */       this.duplicateStream.write(b);
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
/*     */   public void write(byte[] b) throws IOException {
/* 228 */     super.write(b);
/* 229 */     if (this.duplicateStream != null) {
/* 230 */       this.duplicateStream.write(b);
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
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 246 */     super.write(b, off, len);
/* 247 */     if (this.duplicateStream != null) {
/* 248 */       this.duplicateStream.write(b, off, len);
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
/*     */   public void close() throws IOException {
/*     */     try {
/* 263 */       super.close();
/*     */     } finally {
/*     */       try {
/* 266 */         if (this.duplicateStream != null) {
/* 267 */           this.duplicateStream.close();
/*     */         }
/* 269 */       } catch (Exception ex) {
/* 270 */         Logger logger = LoggerFactory.getLogger(PdfWriter.class);
/* 271 */         logger.error("Closing of the duplicatedStream failed.", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfObjectStream getObjectStream() {
/* 282 */     if (!isFullCompression())
/* 283 */       return null; 
/* 284 */     if (this.objectStream == null) {
/* 285 */       this.objectStream = new PdfObjectStream(this.document);
/* 286 */     } else if (this.objectStream.getSize() == 200) {
/* 287 */       this.objectStream.flush();
/* 288 */       this.objectStream = new PdfObjectStream(this.objectStream);
/*     */     } 
/* 290 */     return this.objectStream;
/*     */   }
/*     */   
/*     */   protected void initCryptoIfSpecified(PdfVersion version) {
/* 294 */     EncryptionProperties encryptProps = this.properties.encryptionProperties;
/* 295 */     if (this.properties.isStandardEncryptionUsed()) {
/* 296 */       this
/* 297 */         .crypto = new PdfEncryption(encryptProps.userPassword, encryptProps.ownerPassword, encryptProps.standardEncryptPermissions, encryptProps.encryptionAlgorithm, ByteUtils.getIsoBytes(this.document.getOriginalDocumentId().getValue()), version);
/* 298 */     } else if (this.properties.isPublicKeyEncryptionUsed()) {
/* 299 */       this.crypto = new PdfEncryption(encryptProps.publicCertificates, encryptProps.publicKeyEncryptPermissions, encryptProps.encryptionAlgorithm, version);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushObject(PdfObject pdfObject, boolean canBeInObjStm) throws IOException {
/*     */     PdfArray array;
/*     */     PdfDictionary dictionary;
/* 312 */     PdfIndirectReference indirectReference = pdfObject.getIndirectReference();
/* 313 */     if (isFullCompression() && canBeInObjStm) {
/* 314 */       PdfObjectStream objectStream = getObjectStream();
/* 315 */       objectStream.addObject(pdfObject);
/*     */     } else {
/* 317 */       indirectReference.setOffset(getCurrentPos());
/* 318 */       writeToBody(pdfObject);
/*     */     } 
/* 320 */     indirectReference.setState((short)1).clearState((short)32);
/* 321 */     switch (pdfObject.getType()) {
/*     */       case 2:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 10:
/* 327 */         ((PdfPrimitiveObject)pdfObject).content = null;
/*     */         break;
/*     */       case 1:
/* 330 */         array = (PdfArray)pdfObject;
/* 331 */         markArrayContentToFlush(array);
/* 332 */         array.releaseContent();
/*     */         break;
/*     */       case 3:
/*     */       case 9:
/* 336 */         dictionary = (PdfDictionary)pdfObject;
/* 337 */         markDictionaryContentToFlush(dictionary);
/* 338 */         dictionary.releaseContent();
/*     */         break;
/*     */       case 5:
/* 341 */         markObjectToFlush(((PdfIndirectReference)pdfObject).getRefersTo(false));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected PdfObject copyObject(PdfObject obj, PdfDocument documentTo, boolean allowDuplicating) {
/* 347 */     if (obj instanceof PdfIndirectReference)
/* 348 */       obj = ((PdfIndirectReference)obj).getRefersTo(); 
/* 349 */     if (obj == null) {
/* 350 */       obj = PdfNull.PDF_NULL;
/*     */     }
/* 352 */     if (checkTypeOfPdfDictionary(obj, PdfName.Catalog)) {
/* 353 */       Logger logger = LoggerFactory.getLogger(PdfReader.class);
/* 354 */       logger.warn("Make copy of Catalog dictionary is forbidden.");
/* 355 */       obj = PdfNull.PDF_NULL;
/*     */     } 
/*     */     
/* 358 */     PdfIndirectReference indirectReference = obj.getIndirectReference();
/*     */     
/* 360 */     PdfDocument.IndirectRefDescription copiedObjectKey = null;
/* 361 */     boolean tryToFindDuplicate = (!allowDuplicating && indirectReference != null);
/*     */     
/* 363 */     if (tryToFindDuplicate) {
/* 364 */       copiedObjectKey = new PdfDocument.IndirectRefDescription(indirectReference);
/*     */       
/* 366 */       PdfIndirectReference copiedIndirectReference = this.copiedObjects.get(copiedObjectKey);
/* 367 */       if (copiedIndirectReference != null) {
/* 368 */         return copiedIndirectReference.getRefersTo();
/*     */       }
/*     */     } 
/*     */     
/* 372 */     SerializedObjectContent serializedContent = null;
/* 373 */     if (this.properties.smartMode && tryToFindDuplicate && !checkTypeOfPdfDictionary(obj, PdfName.Page)) {
/* 374 */       serializedContent = this.smartModeSerializer.serializeObject(obj);
/* 375 */       PdfIndirectReference objectRef = this.smartModeSerializer.getSavedSerializedObject(serializedContent);
/* 376 */       if (objectRef != null) {
/* 377 */         this.copiedObjects.put(copiedObjectKey, objectRef);
/* 378 */         return objectRef.refersTo;
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     PdfObject newObject = obj.newInstance();
/* 383 */     if (indirectReference != null) {
/* 384 */       if (copiedObjectKey == null) {
/* 385 */         copiedObjectKey = new PdfDocument.IndirectRefDescription(indirectReference);
/*     */       }
/* 387 */       PdfIndirectReference indRef = newObject.makeIndirect(documentTo).getIndirectReference();
/* 388 */       if (serializedContent != null) {
/* 389 */         this.smartModeSerializer.saveSerializedObject(serializedContent, indRef);
/*     */       }
/* 391 */       this.copiedObjects.put(copiedObjectKey, indRef);
/*     */     } 
/* 393 */     newObject.copyContent(obj, documentTo);
/*     */     
/* 395 */     return newObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeToBody(PdfObject pdfObj) throws IOException {
/* 405 */     if (this.crypto != null) {
/* 406 */       this.crypto.setHashKeyForNextObject(pdfObj.getIndirectReference().getObjNumber(), pdfObj.getIndirectReference().getGenNumber());
/*     */     }
/* 408 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)writeInteger(pdfObj.getIndirectReference().getObjNumber()))
/* 409 */       .writeSpace())
/* 410 */       .writeInteger(pdfObj.getIndirectReference().getGenNumber())).writeBytes(obj);
/* 411 */     write(pdfObj);
/* 412 */     writeBytes(endobj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeHeader() {
/* 419 */     ((PdfOutputStream)((PdfOutputStream)writeByte(37))
/* 420 */       .writeString(this.document.getPdfVersion().toString()))
/* 421 */       .writeString("\n%âãÏÓ\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushWaitingObjects(Set<PdfIndirectReference> forbiddenToFlush) {
/* 431 */     PdfXrefTable xref = this.document.getXref();
/* 432 */     boolean needFlush = true;
/* 433 */     while (needFlush) {
/* 434 */       needFlush = false;
/* 435 */       for (int i = 1; i < xref.size(); i++) {
/* 436 */         PdfIndirectReference indirectReference = xref.get(i);
/* 437 */         if (indirectReference != null && !indirectReference.isFree() && indirectReference
/* 438 */           .checkState((short)32) && 
/* 439 */           !forbiddenToFlush.contains(indirectReference)) {
/* 440 */           PdfObject obj = indirectReference.getRefersTo(false);
/* 441 */           if (obj != null) {
/* 442 */             obj.flush();
/* 443 */             needFlush = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 448 */     if (this.objectStream != null && this.objectStream.getSize() > 0) {
/* 449 */       this.objectStream.flush();
/* 450 */       this.objectStream = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushModifiedWaitingObjects(Set<PdfIndirectReference> forbiddenToFlush) {
/* 461 */     PdfXrefTable xref = this.document.getXref();
/* 462 */     for (int i = 1; i < xref.size(); i++) {
/* 463 */       PdfIndirectReference indirectReference = xref.get(i);
/* 464 */       if (null != indirectReference && !indirectReference.isFree() && !forbiddenToFlush.contains(indirectReference)) {
/* 465 */         boolean isModified = indirectReference.checkState((short)8);
/* 466 */         if (isModified) {
/* 467 */           PdfObject obj = indirectReference.getRefersTo(false);
/* 468 */           if (obj != null && 
/* 469 */             !obj.equals(this.objectStream)) {
/* 470 */             obj.flush();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 476 */     if (this.objectStream != null && this.objectStream.getSize() > 0) {
/* 477 */       this.objectStream.flush();
/* 478 */       this.objectStream = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void flushCopiedObjects(long docId) {
/* 488 */     List<PdfDocument.IndirectRefDescription> remove = new ArrayList<>();
/* 489 */     for (Map.Entry<PdfDocument.IndirectRefDescription, PdfIndirectReference> copiedObject : this.copiedObjects.entrySet()) {
/* 490 */       if (((PdfDocument.IndirectRefDescription)copiedObject.getKey()).docId == docId && 
/* 491 */         ((PdfIndirectReference)copiedObject.getValue()).refersTo != null) {
/* 492 */         ((PdfIndirectReference)copiedObject.getValue()).refersTo.flush();
/* 493 */         remove.add(copiedObject.getKey());
/*     */       } 
/*     */     } 
/*     */     
/* 497 */     for (PdfDocument.IndirectRefDescription ird : remove) {
/* 498 */       this.copiedObjects.remove(ird);
/*     */     }
/*     */   }
/*     */   
/*     */   private void markArrayContentToFlush(PdfArray array) {
/* 503 */     for (int i = 0; i < array.size(); i++) {
/* 504 */       markObjectToFlush(array.get(i, false));
/*     */     }
/*     */   }
/*     */   
/*     */   private void markDictionaryContentToFlush(PdfDictionary dictionary) {
/* 509 */     for (PdfObject item : dictionary.values(false)) {
/* 510 */       markObjectToFlush(item);
/*     */     }
/*     */   }
/*     */   
/*     */   private void markObjectToFlush(PdfObject pdfObject) {
/* 515 */     if (pdfObject != null) {
/* 516 */       PdfIndirectReference indirectReference = pdfObject.getIndirectReference();
/* 517 */       if (indirectReference != null) {
/* 518 */         if (!indirectReference.checkState((short)1)) {
/* 519 */           indirectReference.setState((short)32);
/*     */         }
/*     */       }
/* 522 */       else if (pdfObject.getType() == 5) {
/* 523 */         if (!pdfObject.checkState((short)1)) {
/* 524 */           pdfObject.setState((short)32);
/*     */         }
/* 526 */       } else if (pdfObject.getType() == 1) {
/* 527 */         markArrayContentToFlush((PdfArray)pdfObject);
/* 528 */       } else if (pdfObject.getType() == 3) {
/* 529 */         markDictionaryContentToFlush((PdfDictionary)pdfObject);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PdfWriter setDebugMode() {
/* 536 */     this.duplicateStream = new PdfOutputStream((OutputStream)new ByteArrayOutputStream());
/* 537 */     return this;
/*     */   }
/*     */   
/*     */   private byte[] getDebugBytes() throws IOException {
/* 541 */     if (this.duplicateStream != null) {
/* 542 */       this.duplicateStream.flush();
/* 543 */       return ((ByteArrayOutputStream)this.duplicateStream.getOutputStream()).toByteArray();
/*     */     } 
/* 545 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean checkTypeOfPdfDictionary(PdfObject dictionary, PdfName expectedType) {
/* 550 */     return (dictionary.isDictionary() && expectedType.equals(((PdfDictionary)dictionary).getAsName(PdfName.Type)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 561 */     in.defaultReadObject();
/* 562 */     if (this.outputStream == null) {
/* 563 */       this.outputStream = (OutputStream)(new ByteArrayOutputStream()).assignBytes(getDebugBytes());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 574 */     if (this.duplicateStream == null) {
/* 575 */       throw new NotSerializableException(getClass().getName() + ": debug mode is disabled!");
/*     */     }
/* 577 */     OutputStream tempOutputStream = this.outputStream;
/* 578 */     this.outputStream = null;
/* 579 */     out.defaultWriteObject();
/* 580 */     this.outputStream = tempOutputStream;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */