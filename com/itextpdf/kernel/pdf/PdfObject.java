/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.crypto.BadPasswordException;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdfObject
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3852543867469424720L;
/*     */   public static final byte ARRAY = 1;
/*     */   public static final byte BOOLEAN = 2;
/*     */   public static final byte DICTIONARY = 3;
/*     */   public static final byte LITERAL = 4;
/*     */   public static final byte INDIRECT_REFERENCE = 5;
/*     */   public static final byte NAME = 6;
/*     */   public static final byte NULL = 7;
/*     */   public static final byte NUMBER = 8;
/*     */   public static final byte STREAM = 9;
/*     */   public static final byte STRING = 10;
/*     */   protected static final short FLUSHED = 1;
/*     */   protected static final short FREE = 2;
/*     */   protected static final short READING = 4;
/*     */   protected static final short MODIFIED = 8;
/*     */   protected static final short ORIGINAL_OBJECT_STREAM = 16;
/*     */   protected static final short MUST_BE_FLUSHED = 32;
/*     */   protected static final short MUST_BE_INDIRECT = 64;
/*     */   protected static final short FORBID_RELEASE = 128;
/*     */   protected static final short READ_ONLY = 256;
/*     */   protected static final short UNENCRYPTED = 512;
/* 134 */   protected PdfIndirectReference indirectReference = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private short state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void flush() {
/* 152 */     flush(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void flush(boolean canBeInObjStm) {
/* 161 */     if (isFlushed() || getIndirectReference() == null || getIndirectReference().isFree()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 175 */       PdfDocument document = getIndirectReference().getDocument();
/* 176 */       if (document != null) {
/* 177 */         if (document.isAppendMode() && !isModified()) {
/* 178 */           Logger logger = LoggerFactory.getLogger(PdfObject.class);
/* 179 */           logger.info("PdfObject flushing is not performed: PdfDocument is opened in append mode and the object is not marked as modified ( see PdfObject#setModified() ).");
/*     */           return;
/*     */         } 
/* 182 */         document.checkIsoConformance(this, IsoKey.PDF_OBJECT);
/* 183 */         document.flushObject(this, (canBeInObjStm && getType() != 9 && 
/* 184 */             getType() != 5 && getIndirectReference().getGenNumber() == 0));
/*     */       } 
/* 186 */     } catch (IOException e) {
/* 187 */       throw new PdfException("Cannot flush object.", e, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfIndirectReference getIndirectReference() {
/* 198 */     return this.indirectReference;
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
/*     */   public boolean isIndirect() {
/* 215 */     return (this.indirectReference != null || checkState((short)64));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject makeIndirect(PdfDocument document, PdfIndirectReference reference) {
/* 226 */     if (document == null || this.indirectReference != null) {
/* 227 */       return this;
/*     */     }
/* 229 */     if (document.getWriter() == null) {
/* 230 */       throw new PdfException("There is no associate PdfWriter for making indirects.");
/*     */     }
/* 232 */     if (reference == null) {
/* 233 */       this.indirectReference = document.createNextIndirectReference();
/* 234 */       this.indirectReference.setRefersTo(this);
/*     */     } else {
/* 236 */       reference.setState((short)8);
/* 237 */       this.indirectReference = reference;
/* 238 */       this.indirectReference.setRefersTo(this);
/*     */     } 
/* 240 */     setState((short)128);
/* 241 */     clearState((short)64);
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject makeIndirect(PdfDocument document) {
/* 252 */     return makeIndirect(document, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlushed() {
/* 261 */     PdfIndirectReference indirectReference = getIndirectReference();
/* 262 */     return (indirectReference != null && indirectReference.checkState((short)1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModified() {
/* 271 */     PdfIndirectReference indirectReference = getIndirectReference();
/* 272 */     return (indirectReference != null && indirectReference.checkState((short)8));
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
/*     */   public PdfObject clone() {
/* 284 */     PdfObject newObject = newInstance();
/* 285 */     if (this.indirectReference != null || checkState((short)64)) {
/* 286 */       newObject.setState((short)64);
/*     */     }
/* 288 */     newObject.copyContent(this, null);
/* 289 */     return newObject;
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
/*     */   public PdfObject copyTo(PdfDocument document) {
/* 301 */     return copyTo(document, true);
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
/*     */   public PdfObject copyTo(PdfDocument document, boolean allowDuplicating) {
/* 316 */     if (document == null) {
/* 317 */       throw new PdfException("Document for copyTo cannot be null.");
/*     */     }
/* 319 */     if (this.indirectReference != null) {
/*     */       
/* 321 */       if (this.indirectReference.getWriter() != null || checkState((short)64)) {
/* 322 */         throw new PdfException("Cannot copy indirect object from the document that is being written.");
/*     */       }
/* 324 */       if (!this.indirectReference.getReader().isOpenedWithFullPermission()) {
/* 325 */         throw new BadPasswordException("PdfReader is not opened with owner password");
/*     */       }
/*     */     } 
/*     */     
/* 329 */     return processCopying(document, allowDuplicating);
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
/*     */   public PdfObject setModified() {
/* 344 */     if (this.indirectReference != null) {
/* 345 */       this.indirectReference.setState((short)8);
/* 346 */       setState((short)128);
/*     */     } 
/* 348 */     return this;
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
/*     */   public boolean isReleaseForbidden() {
/* 360 */     return checkState((short)128);
/*     */   }
/*     */ 
/*     */   
/*     */   public void release() {
/* 365 */     if (isReleaseForbidden()) {
/* 366 */       Logger logger = LoggerFactory.getLogger(PdfObject.class);
/* 367 */       logger.warn("ForbidRelease flag is set and release is called. Releasing will not be performed.");
/*     */     }
/* 369 */     else if (this.indirectReference != null && this.indirectReference.getReader() != null && 
/* 370 */       !this.indirectReference.checkState((short)1)) {
/* 371 */       this.indirectReference.refersTo = null;
/* 372 */       this.indirectReference = null;
/* 373 */       setState((short)256);
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
/*     */   public boolean isNull() {
/* 386 */     return (getType() == 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoolean() {
/* 396 */     return (getType() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNumber() {
/* 406 */     return (getType() == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isString() {
/* 416 */     return (getType() == 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isName() {
/* 426 */     return (getType() == 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray() {
/* 436 */     return (getType() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDictionary() {
/* 446 */     return (getType() == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStream() {
/* 456 */     return (getType() == 9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIndirectReference() {
/* 467 */     return (getType() == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLiteral() {
/* 478 */     return (getType() == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfObject setIndirectReference(PdfIndirectReference indirectReference) {
/* 489 */     this.indirectReference = indirectReference;
/* 490 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkState(short state) {
/* 500 */     return ((this.state & state) == state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfObject setState(short state) {
/* 510 */     this.state = (short)(this.state | state);
/* 511 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfObject clearState(short state) {
/* 521 */     this.state = (short)(this.state & (short)(state ^ 0xFFFFFFFF));
/* 522 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 532 */     if (isFlushed()) {
/* 533 */       throw new PdfException("Cannot copy flushed object.", this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfObject processCopying(PdfDocument documentTo, boolean allowDuplicating) {
/* 553 */     if (documentTo != null) {
/*     */       
/* 555 */       PdfWriter writer = documentTo.getWriter();
/* 556 */       if (writer == null)
/* 557 */         throw new PdfException("Cannot copy to document opened in reading mode."); 
/* 558 */       return writer.copyObject(this, documentTo, allowDuplicating);
/*     */     } 
/*     */ 
/*     */     
/* 562 */     PdfObject obj = this;
/* 563 */     if (obj.isIndirectReference()) {
/* 564 */       PdfObject refTo = ((PdfIndirectReference)obj).getRefersTo();
/* 565 */       obj = (refTo != null) ? refTo : obj;
/*     */     } 
/* 567 */     if (obj.isIndirect() && !allowDuplicating) {
/* 568 */       return obj;
/*     */     }
/* 570 */     return obj.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean equalContent(PdfObject obj1, PdfObject obj2) {
/* 576 */     PdfObject direct1 = (obj1 != null && obj1.isIndirectReference()) ? ((PdfIndirectReference)obj1).getRefersTo(true) : obj1;
/*     */ 
/*     */     
/* 579 */     PdfObject direct2 = (obj2 != null && obj2.isIndirectReference()) ? ((PdfIndirectReference)obj2).getRefersTo(true) : obj2;
/*     */     
/* 581 */     return (direct1 != null && direct1.equals(direct2));
/*     */   }
/*     */   
/*     */   public abstract byte getType();
/*     */   
/*     */   protected abstract PdfObject newInstance();
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */