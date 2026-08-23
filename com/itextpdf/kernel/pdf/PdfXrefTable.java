/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.ProductInfo;
/*     */ import com.itextpdf.kernel.VersionInfo;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
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
/*     */ public class PdfXrefTable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4171655392492002944L;
/*     */   private static final int INITIAL_CAPACITY = 32;
/*     */   private static final int MAX_GENERATION = 65535;
/*  73 */   private static final byte[] freeXRefEntry = ByteUtils.getIsoBytes("f \n");
/*  74 */   private static final byte[] inUseXRefEntry = ByteUtils.getIsoBytes("n \n");
/*     */   
/*     */   private PdfIndirectReference[] xref;
/*  77 */   private int count = 0;
/*     */ 
/*     */   
/*     */   private boolean readingCompleted;
/*     */ 
/*     */   
/*     */   private final TreeMap<Integer, PdfIndirectReference> freeReferencesLinkedList;
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfXrefTable() {
/*  88 */     this(32);
/*     */   }
/*     */   
/*     */   public PdfXrefTable(int capacity) {
/*  92 */     if (capacity < 1) {
/*  93 */       capacity = 32;
/*     */     }
/*  95 */     this.xref = new PdfIndirectReference[capacity];
/*  96 */     this.freeReferencesLinkedList = new TreeMap<>();
/*  97 */     add((PdfIndirectReference)(new PdfIndirectReference(null, 0, 65535, 0L)).setState((short)2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfIndirectReference add(PdfIndirectReference reference) {
/* 107 */     if (reference == null) {
/* 108 */       return null;
/*     */     }
/* 110 */     int objNr = reference.getObjNumber();
/* 111 */     this.count = Math.max(this.count, objNr);
/* 112 */     ensureCount(objNr);
/* 113 */     this.xref[objNr] = reference;
/* 114 */     return reference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 123 */     return this.count + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountOfIndirectObjects() {
/* 132 */     int countOfIndirectObjects = 0;
/*     */     
/* 134 */     for (PdfIndirectReference ref : this.xref) {
/* 135 */       if (ref != null && !ref.isFree()) {
/* 136 */         countOfIndirectObjects++;
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return countOfIndirectObjects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfIndirectReference get(int index) {
/* 150 */     if (index > this.count) {
/* 151 */       return null;
/*     */     }
/* 153 */     return this.xref[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void markReadingCompleted() {
/* 161 */     this.readingCompleted = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isReadingCompleted() {
/* 170 */     return this.readingCompleted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void initFreeReferencesList(PdfDocument pdfDocument) {
/* 179 */     this.freeReferencesLinkedList.clear();
/*     */ 
/*     */     
/* 182 */     this.xref[0].setState((short)2);
/* 183 */     TreeSet<Integer> freeReferences = new TreeSet<>();
/* 184 */     for (int i = 1; i < size(); i++) {
/* 185 */       PdfIndirectReference ref = this.xref[i];
/* 186 */       if (ref == null || ref.isFree()) {
/* 187 */         freeReferences.add(Integer.valueOf(i));
/*     */       }
/*     */     } 
/*     */     
/* 191 */     PdfIndirectReference prevFreeRef = this.xref[0];
/* 192 */     while (!freeReferences.isEmpty()) {
/* 193 */       int currFreeRefObjNr = -1;
/* 194 */       if (prevFreeRef.getOffset() <= 2147483647L) {
/* 195 */         currFreeRefObjNr = (int)prevFreeRef.getOffset();
/*     */       }
/* 197 */       if (!freeReferences.contains(Integer.valueOf(currFreeRefObjNr)) || this.xref[currFreeRefObjNr] == null) {
/*     */         break;
/*     */       }
/*     */       
/* 201 */       this.freeReferencesLinkedList.put(Integer.valueOf(currFreeRefObjNr), prevFreeRef);
/* 202 */       prevFreeRef = this.xref[currFreeRefObjNr];
/* 203 */       freeReferences.remove(Integer.valueOf(currFreeRefObjNr));
/*     */     } 
/*     */     
/* 206 */     while (!freeReferences.isEmpty()) {
/* 207 */       int next = ((Integer)freeReferences.pollFirst()).intValue();
/* 208 */       if (this.xref[next] == null) {
/* 209 */         if (pdfDocument.properties.appendMode) {
/*     */           continue;
/*     */         }
/* 212 */         this.xref[next] = (PdfIndirectReference)(new PdfIndirectReference(pdfDocument, next, 0)).setState((short)2).setState((short)8);
/* 213 */       } else if (this.xref[next].getGenNumber() == 65535 && this.xref[next].getOffset() == 0L) {
/*     */         continue;
/*     */       } 
/* 216 */       if (prevFreeRef.getOffset() != next) {
/* 217 */         ((PdfIndirectReference)prevFreeRef.setState((short)8)).setOffset(next);
/*     */       }
/* 219 */       this.freeReferencesLinkedList.put(Integer.valueOf(next), prevFreeRef);
/* 220 */       prevFreeRef = this.xref[next];
/*     */     } 
/*     */     
/* 223 */     if (prevFreeRef.getOffset() != 0L) {
/* 224 */       ((PdfIndirectReference)prevFreeRef.setState((short)8)).setOffset(0L);
/*     */     }
/* 226 */     this.freeReferencesLinkedList.put(Integer.valueOf(0), prevFreeRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfIndirectReference createNewIndirectReference(PdfDocument document) {
/* 236 */     PdfIndirectReference reference = new PdfIndirectReference(document, ++this.count);
/* 237 */     add(reference);
/* 238 */     return (PdfIndirectReference)reference.setState((short)8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfIndirectReference createNextIndirectReference(PdfDocument document) {
/* 248 */     PdfIndirectReference reference = new PdfIndirectReference(document, ++this.count);
/* 249 */     add(reference);
/* 250 */     return (PdfIndirectReference)reference.setState((short)8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void freeReference(PdfIndirectReference reference) {
/* 259 */     if (reference.isFree()) {
/*     */       return;
/*     */     }
/* 262 */     if (reference.checkState((short)32)) {
/* 263 */       Logger logger = LoggerFactory.getLogger(PdfXrefTable.class);
/* 264 */       logger.error("An attempt is made to free an indirect reference which was already used in the flushed object. Indirect reference wasn't freed.");
/*     */       return;
/*     */     } 
/* 267 */     if (reference.checkState((short)1)) {
/* 268 */       Logger logger = LoggerFactory.getLogger(PdfXrefTable.class);
/* 269 */       logger.error("An attempt is made to free already flushed indirect object reference. Indirect reference wasn't freed.");
/*     */       
/*     */       return;
/*     */     } 
/* 273 */     reference.setState((short)2).setState((short)8);
/*     */     
/* 275 */     appendNewRefToFreeList(reference);
/*     */     
/* 277 */     if (reference.getGenNumber() < 65535) {
/* 278 */       reference.genNr++;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCapacity(int capacity) {
/* 289 */     if (capacity > this.xref.length) {
/* 290 */       extendXref(capacity);
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
/*     */   protected void writeXrefTableAndTrailer(PdfDocument document, PdfObject fileId, PdfObject crypto) throws IOException {
/* 303 */     PdfWriter writer = document.getWriter();
/*     */     
/* 305 */     if (!document.properties.appendMode) {
/* 306 */       for (int i = this.count; i > 0; ) {
/* 307 */         PdfIndirectReference lastRef = this.xref[i];
/* 308 */         if (lastRef == null || lastRef.isFree()) {
/* 309 */           removeFreeRefFromList(i);
/* 310 */           this.count--;
/*     */           
/*     */           i--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 317 */     PdfStream xrefStream = null;
/* 318 */     if (writer.isFullCompression()) {
/* 319 */       xrefStream = new PdfStream();
/* 320 */       xrefStream.makeIndirect(document);
/*     */     } 
/* 322 */     List<Integer> sections = createSections(document, false);
/*     */     
/* 324 */     boolean noModifiedObjects = (sections.size() == 0 || (xrefStream != null && sections.size() == 2 && ((Integer)sections.get(0)).intValue() == this.count && ((Integer)sections.get(1)).intValue() == 1));
/* 325 */     if (document.properties.appendMode && noModifiedObjects) {
/*     */       
/* 327 */       this.xref = null;
/*     */       
/*     */       return;
/*     */     } 
/* 331 */     document.checkIsoConformance(this, IsoKey.XREF_TABLE);
/*     */     
/* 333 */     long startxref = writer.getCurrentPos();
/* 334 */     long xRefStmPos = -1L;
/* 335 */     if (xrefStream != null) {
/* 336 */       xrefStream.put(PdfName.Type, PdfName.XRef);
/* 337 */       xrefStream.put(PdfName.ID, fileId);
/* 338 */       if (crypto != null)
/* 339 */         xrefStream.put(PdfName.Encrypt, crypto); 
/* 340 */       xrefStream.put(PdfName.Size, new PdfNumber(size()));
/*     */       
/* 342 */       int offsetSize = getOffsetSize(Math.max(startxref, size()));
/* 343 */       xrefStream.put(PdfName.W, new PdfArray(
/* 344 */             Arrays.asList(new PdfObject[] { new PdfNumber(1), new PdfNumber(offsetSize), new PdfNumber(2) })));
/* 345 */       xrefStream.put(PdfName.Info, document.getDocumentInfo().getPdfObject());
/* 346 */       xrefStream.put(PdfName.Root, document.getCatalog().getPdfObject());
/* 347 */       PdfArray index = new PdfArray();
/* 348 */       for (Integer section : sections) {
/* 349 */         index.add(new PdfNumber(section.intValue()));
/*     */       }
/* 351 */       if (document.properties.appendMode && !document.reader.hybridXref) {
/*     */         
/* 353 */         PdfNumber lastXref = new PdfNumber(document.reader.getLastXref());
/* 354 */         xrefStream.put(PdfName.Prev, lastXref);
/*     */       } 
/* 356 */       xrefStream.put(PdfName.Index, index);
/* 357 */       xrefStream.getIndirectReference().setOffset(startxref);
/* 358 */       PdfXrefTable xrefTable = document.getXref();
/* 359 */       for (int k = 0; k < sections.size(); k += 2) {
/* 360 */         int first = ((Integer)sections.get(k)).intValue();
/* 361 */         int len = ((Integer)sections.get(k + 1)).intValue();
/* 362 */         for (int i = first; i < first + len; i++) {
/* 363 */           PdfIndirectReference reference = xrefTable.get(i);
/* 364 */           if (reference.isFree()) {
/* 365 */             xrefStream.getOutputStream().write(0);
/* 366 */             xrefStream.getOutputStream().write(reference.getOffset(), offsetSize);
/* 367 */             xrefStream.getOutputStream().write(reference.getGenNumber(), 2);
/* 368 */           } else if (reference.getObjStreamNumber() == 0) {
/* 369 */             xrefStream.getOutputStream().write(1);
/* 370 */             xrefStream.getOutputStream().write(reference.getOffset(), offsetSize);
/* 371 */             xrefStream.getOutputStream().write(reference.getGenNumber(), 2);
/*     */           } else {
/* 373 */             xrefStream.getOutputStream().write(2);
/* 374 */             xrefStream.getOutputStream().write(reference.getObjStreamNumber(), offsetSize);
/* 375 */             xrefStream.getOutputStream().write(reference.getIndex(), 2);
/*     */           } 
/*     */         } 
/*     */       } 
/* 379 */       xrefStream.flush();
/* 380 */       xRefStmPos = startxref;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     boolean needsRegularXref = (!writer.isFullCompression() || (document.properties.appendMode && document.reader.hybridXref));
/*     */     
/* 388 */     if (needsRegularXref) {
/* 389 */       startxref = writer.getCurrentPos();
/* 390 */       writer.writeString("xref\n");
/* 391 */       PdfXrefTable xrefTable = document.getXref();
/* 392 */       if (xRefStmPos != -1L)
/*     */       {
/* 394 */         sections = createSections(document, true);
/*     */       }
/* 396 */       for (int k = 0; k < sections.size(); k += 2) {
/* 397 */         int first = ((Integer)sections.get(k)).intValue();
/* 398 */         int len = ((Integer)sections.get(k + 1)).intValue();
/* 399 */         ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)writer.writeInteger(first)).writeSpace()).writeInteger(len)).writeByte((byte)10);
/* 400 */         for (int i = first; i < first + len; i++) {
/* 401 */           PdfIndirectReference reference = xrefTable.get(i);
/*     */           
/* 403 */           StringBuilder off = (new StringBuilder("0000000000")).append(reference.getOffset());
/* 404 */           StringBuilder gen = (new StringBuilder("00000")).append(reference.getGenNumber());
/* 405 */           ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)writer.writeString(off.substring(off.length() - 10, off.length()))).writeSpace())
/* 406 */             .writeString(gen.substring(gen.length() - 5, gen.length()))).writeSpace();
/* 407 */           if (reference.isFree()) {
/* 408 */             writer.writeBytes(freeXRefEntry);
/*     */           } else {
/* 410 */             writer.writeBytes(inUseXRefEntry);
/*     */           } 
/*     */         } 
/*     */       } 
/* 414 */       PdfDictionary trailer = document.getTrailer();
/*     */       
/* 416 */       trailer.remove(PdfName.W);
/* 417 */       trailer.remove(PdfName.Index);
/* 418 */       trailer.remove(PdfName.Type);
/* 419 */       trailer.remove(PdfName.Length);
/* 420 */       trailer.put(PdfName.Size, new PdfNumber(size()));
/* 421 */       trailer.put(PdfName.ID, fileId);
/* 422 */       if (xRefStmPos != -1L) {
/* 423 */         trailer.put(PdfName.XRefStm, new PdfNumber(xRefStmPos));
/*     */       }
/* 425 */       if (crypto != null)
/* 426 */         trailer.put(PdfName.Encrypt, crypto); 
/* 427 */       writer.writeString("trailer\n");
/* 428 */       if (document.properties.appendMode) {
/* 429 */         PdfNumber lastXref = new PdfNumber(document.reader.getLastXref());
/* 430 */         trailer.put(PdfName.Prev, lastXref);
/*     */       } 
/* 432 */       writer.write(document.getTrailer());
/* 433 */       writer.write(10);
/*     */     } 
/* 435 */     writeKeyInfo(document);
/* 436 */     ((PdfOutputStream)((PdfOutputStream)writer.writeString("startxref\n"))
/* 437 */       .writeLong(startxref))
/* 438 */       .writeString("\n%%EOF\n");
/* 439 */     this.xref = null;
/* 440 */     this.freeReferencesLinkedList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clear() {
/* 447 */     for (int i = 1; i <= this.count; i++) {
/* 448 */       if (this.xref[i] == null || !this.xref[i].isFree())
/*     */       {
/*     */         
/* 451 */         this.xref[i] = null; } 
/*     */     } 
/* 453 */     this.count = 1;
/*     */   }
/*     */   
/*     */   private List<Integer> createSections(PdfDocument document, boolean dropObjectsFromObjectStream) {
/* 457 */     List<Integer> sections = new ArrayList<>();
/* 458 */     int first = 0;
/* 459 */     int len = 0;
/* 460 */     for (int i = 0; i < size(); i++) {
/* 461 */       PdfIndirectReference reference = this.xref[i];
/* 462 */       if (document.properties.appendMode && reference != null && (
/* 463 */         !reference.checkState((short)8) || (dropObjectsFromObjectStream && reference.getObjStreamNumber() != 0))) {
/* 464 */         reference = null;
/*     */       }
/*     */       
/* 467 */       if (reference == null) {
/* 468 */         if (len > 0) {
/* 469 */           sections.add(Integer.valueOf(first));
/* 470 */           sections.add(Integer.valueOf(len));
/*     */         } 
/* 472 */         len = 0;
/*     */       }
/* 474 */       else if (len > 0) {
/* 475 */         len++;
/*     */       } else {
/* 477 */         first = i;
/* 478 */         len = 1;
/*     */       } 
/*     */     } 
/*     */     
/* 482 */     if (len > 0) {
/* 483 */       sections.add(Integer.valueOf(first));
/* 484 */       sections.add(Integer.valueOf(len));
/*     */     } 
/*     */     
/* 487 */     return sections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getOffsetSize(long startxref) {
/* 494 */     assert startxref >= 0L && startxref < 1099511627776L;
/*     */     
/* 496 */     int size = 5;
/* 497 */     long mask = 1095216660480L;
/* 498 */     for (; size > 1 && (
/* 499 */       mask & startxref) == 0L; size--)
/*     */     {
/*     */       
/* 502 */       mask >>= 8L;
/*     */     }
/* 504 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void writeKeyInfo(PdfDocument document) {
/* 515 */     PdfWriter writer = document.getWriter();
/* 516 */     FingerPrint fingerPrint = document.getFingerPrint();
/*     */     
/* 518 */     String platform = "";
/* 519 */     VersionInfo versionInfo = document.getVersionInfo();
/* 520 */     String k = versionInfo.getKey();
/* 521 */     if (k == null) {
/* 522 */       k = "iText";
/*     */     }
/* 524 */     writer.writeString(MessageFormatUtil.format("%{0}-{1}{2}\n", new Object[] { k, versionInfo.getRelease(), platform }));
/*     */     
/* 526 */     for (ProductInfo productInfo : fingerPrint.getProducts()) {
/* 527 */       writer.writeString(MessageFormatUtil.format("%{0}\n", new Object[] { productInfo }));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void appendNewRefToFreeList(PdfIndirectReference reference) {
/* 532 */     reference.setOffset(0L);
/* 533 */     if (this.freeReferencesLinkedList.isEmpty()) {
/*     */       assert false;
/*     */       
/*     */       return;
/*     */     } 
/* 538 */     PdfIndirectReference lastFreeRef = this.freeReferencesLinkedList.get(Integer.valueOf(0));
/* 539 */     ((PdfIndirectReference)lastFreeRef.setState((short)8)).setOffset(reference.getObjNumber());
/* 540 */     this.freeReferencesLinkedList.put(Integer.valueOf(reference.getObjNumber()), lastFreeRef);
/* 541 */     this.freeReferencesLinkedList.put(Integer.valueOf(0), reference);
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
/*     */   private PdfIndirectReference removeFreeRefFromList(int freeRefObjNr) {
/* 554 */     if (this.freeReferencesLinkedList.isEmpty()) {
/*     */       assert false;
/*     */       
/* 557 */       return null;
/*     */     } 
/* 559 */     if (freeRefObjNr == 0) {
/* 560 */       return null;
/*     */     }
/* 562 */     if (freeRefObjNr < 0) {
/* 563 */       Integer leastFreeRefObjNum = null;
/* 564 */       for (Map.Entry<Integer, PdfIndirectReference> entry : this.freeReferencesLinkedList.entrySet()) {
/* 565 */         if (((Integer)entry.getKey()).intValue() <= 0 || this.xref[((Integer)entry.getKey()).intValue()].getGenNumber() >= 65535) {
/*     */           continue;
/*     */         }
/* 568 */         leastFreeRefObjNum = entry.getKey();
/*     */       } 
/*     */       
/* 571 */       if (leastFreeRefObjNum == null) {
/* 572 */         return null;
/*     */       }
/* 574 */       freeRefObjNr = leastFreeRefObjNum.intValue();
/*     */     } 
/*     */     
/* 577 */     PdfIndirectReference freeRef = this.xref[freeRefObjNr];
/* 578 */     if (!freeRef.isFree()) {
/* 579 */       return null;
/*     */     }
/*     */     
/* 582 */     PdfIndirectReference prevFreeRef = this.freeReferencesLinkedList.remove(Integer.valueOf(freeRef.getObjNumber()));
/* 583 */     if (prevFreeRef != null) {
/* 584 */       this.freeReferencesLinkedList.put(Integer.valueOf((int)freeRef.getOffset()), prevFreeRef);
/* 585 */       ((PdfIndirectReference)prevFreeRef.setState((short)8)).setOffset(freeRef.getOffset());
/*     */     } 
/*     */     
/* 588 */     return freeRef;
/*     */   }
/*     */   
/*     */   private void ensureCount(int count) {
/* 592 */     if (count >= this.xref.length) {
/* 593 */       extendXref(count << 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void extendXref(int capacity) {
/* 598 */     PdfIndirectReference[] newXref = new PdfIndirectReference[capacity];
/* 599 */     System.arraycopy(this.xref, 0, newXref, 0, this.xref.length);
/* 600 */     this.xref = newXref;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfXrefTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */