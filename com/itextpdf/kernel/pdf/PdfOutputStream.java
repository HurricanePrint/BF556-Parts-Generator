/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.io.source.ByteUtils;
/*     */ import com.itextpdf.io.source.DeflaterOutputStream;
/*     */ import com.itextpdf.io.source.OutputStream;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.crypto.OutputStreamEncryption;
/*     */ import com.itextpdf.kernel.pdf.filters.FlateDecodeFilter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
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
/*     */ public class PdfOutputStream
/*     */   extends OutputStream<PdfOutputStream>
/*     */ {
/*     */   private static final long serialVersionUID = -548180479472231600L;
/*  64 */   private static final byte[] stream = ByteUtils.getIsoBytes("stream\n");
/*  65 */   private static final byte[] endstream = ByteUtils.getIsoBytes("\nendstream");
/*  66 */   private static final byte[] openDict = ByteUtils.getIsoBytes("<<");
/*  67 */   private static final byte[] closeDict = ByteUtils.getIsoBytes(">>");
/*  68 */   private static final byte[] endIndirect = ByteUtils.getIsoBytes(" R");
/*  69 */   private static final byte[] endIndirectWithZeroGenNr = ByteUtils.getIsoBytes(" 0 R");
/*     */ 
/*     */   
/*  72 */   private byte[] duplicateContentBuffer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   protected PdfDocument document = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfEncryption crypto;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOutputStream(OutputStream outputStream) {
/*  89 */     super(outputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfOutputStream write(PdfObject pdfObject) {
/* 100 */     if (pdfObject.checkState((short)64) && this.document != null) {
/* 101 */       pdfObject.makeIndirect(this.document);
/* 102 */       pdfObject = pdfObject.getIndirectReference();
/*     */     } 
/* 104 */     if (pdfObject.checkState((short)256)) {
/* 105 */       throw new PdfException("Cannot write object after it was released. In normal situation the object must be read once again before being written.");
/*     */     }
/* 107 */     switch (pdfObject.getType()) {
/*     */       case 1:
/* 109 */         write((PdfArray)pdfObject);
/*     */         break;
/*     */       case 3:
/* 112 */         write((PdfDictionary)pdfObject);
/*     */         break;
/*     */       case 5:
/* 115 */         write((PdfIndirectReference)pdfObject);
/*     */         break;
/*     */       case 6:
/* 118 */         write((PdfName)pdfObject);
/*     */         break;
/*     */       case 2:
/*     */       case 7:
/* 122 */         write((PdfPrimitiveObject)pdfObject);
/*     */         break;
/*     */       case 4:
/* 125 */         write((PdfLiteral)pdfObject);
/*     */         break;
/*     */       case 10:
/* 128 */         write((PdfString)pdfObject);
/*     */         break;
/*     */       case 8:
/* 131 */         write((PdfNumber)pdfObject);
/*     */         break;
/*     */       case 9:
/* 134 */         write((PdfStream)pdfObject);
/*     */         break;
/*     */     } 
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(long bytes, int size) throws IOException {
/* 147 */     assert bytes >= 0L;
/* 148 */     while (--size >= 0) {
/* 149 */       write((byte)(int)(bytes >> 8 * size & 0xFFL));
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
/*     */   void write(int bytes, int size) throws IOException {
/* 161 */     write(bytes & 0xFFFFFFFFL, size);
/*     */   }
/*     */   
/*     */   private void write(PdfArray pdfArray) {
/* 165 */     writeByte(91);
/* 166 */     for (int i = 0; i < pdfArray.size(); i++) {
/* 167 */       PdfObject value = pdfArray.get(i, false);
/*     */       PdfIndirectReference indirectReference;
/* 169 */       if ((indirectReference = value.getIndirectReference()) != null) {
/* 170 */         write(indirectReference);
/*     */       } else {
/* 172 */         write(value);
/*     */       } 
/* 174 */       if (i < pdfArray.size() - 1)
/* 175 */         writeSpace(); 
/*     */     } 
/* 177 */     writeByte(93);
/*     */   }
/*     */   
/*     */   private void write(PdfDictionary pdfDictionary) {
/* 181 */     writeBytes(openDict);
/* 182 */     for (PdfName key : pdfDictionary.keySet()) {
/* 183 */       boolean isAlreadyWriteSpace = false;
/* 184 */       write(key);
/* 185 */       PdfObject value = pdfDictionary.get(key, false);
/* 186 */       if (value == null) {
/* 187 */         Logger logger = LoggerFactory.getLogger(PdfOutputStream.class);
/* 188 */         logger.warn(MessageFormatUtil.format("Invalid key value: key {0} has null value.", new Object[] { key }));
/* 189 */         value = PdfNull.PDF_NULL;
/*     */       } 
/* 191 */       if (value.getType() == 8 || value
/* 192 */         .getType() == 4 || value
/* 193 */         .getType() == 2 || value
/* 194 */         .getType() == 7 || value
/* 195 */         .getType() == 5 || value
/* 196 */         .checkState((short)64)) {
/* 197 */         isAlreadyWriteSpace = true;
/* 198 */         writeSpace();
/*     */       } 
/*     */       
/*     */       PdfIndirectReference indirectReference;
/* 202 */       if ((indirectReference = value.getIndirectReference()) != null) {
/* 203 */         if (!isAlreadyWriteSpace) {
/* 204 */           writeSpace();
/*     */         }
/* 206 */         write(indirectReference); continue;
/*     */       } 
/* 208 */       write(value);
/*     */     } 
/*     */     
/* 211 */     writeBytes(closeDict);
/*     */   }
/*     */   
/*     */   private void write(PdfIndirectReference indirectReference) {
/* 215 */     if (this.document != null && !indirectReference.getDocument().equals(this.document)) {
/* 216 */       throw new PdfException("Pdf indirect object belongs to other PDF document. Copy object to current pdf document.");
/*     */     }
/* 218 */     if (indirectReference.isFree()) {
/* 219 */       Logger logger = LoggerFactory.getLogger(PdfOutputStream.class);
/* 220 */       logger.error("Flushed object contains indirect reference which is free. Null object will be written instead.");
/* 221 */       write(PdfNull.PDF_NULL);
/* 222 */     } else if (indirectReference.refersTo == null && (indirectReference
/* 223 */       .checkState((short)8) || indirectReference.getReader() == null || (indirectReference
/* 224 */       .getOffset() <= 0L && indirectReference.getIndex() < 0))) {
/* 225 */       Logger logger = LoggerFactory.getLogger(PdfOutputStream.class);
/* 226 */       logger.error("Flushed object contains indirect reference which doesn't refer to any other object. Null object will be written instead.");
/* 227 */       write(PdfNull.PDF_NULL);
/* 228 */     } else if (indirectReference.getGenNumber() == 0) {
/* 229 */       ((PdfOutputStream)writeInteger(indirectReference.getObjNumber()))
/* 230 */         .writeBytes(endIndirectWithZeroGenNr);
/*     */     } else {
/* 232 */       ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)writeInteger(indirectReference.getObjNumber()))
/* 233 */         .writeSpace())
/* 234 */         .writeInteger(indirectReference.getGenNumber()))
/* 235 */         .writeBytes(endIndirect);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void write(PdfPrimitiveObject pdfPrimitive) {
/* 240 */     writeBytes(pdfPrimitive.getInternalContent());
/*     */   }
/*     */   
/*     */   private void write(PdfLiteral literal) {
/* 244 */     literal.setPosition(getCurrentPos());
/* 245 */     writeBytes(literal.getInternalContent());
/*     */   }
/*     */   
/*     */   private void write(PdfString pdfString) {
/* 249 */     pdfString.encrypt(this.crypto);
/* 250 */     if (pdfString.isHexWriting()) {
/* 251 */       writeByte(60);
/* 252 */       writeBytes(pdfString.getInternalContent());
/* 253 */       writeByte(62);
/*     */     } else {
/* 255 */       writeByte(40);
/* 256 */       writeBytes(pdfString.getInternalContent());
/* 257 */       writeByte(41);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void write(PdfName name) {
/* 262 */     writeByte(47);
/* 263 */     writeBytes(name.getInternalContent());
/*     */   }
/*     */   
/*     */   private void write(PdfNumber pdfNumber) {
/* 267 */     if (pdfNumber.hasContent()) {
/* 268 */       writeBytes(pdfNumber.getInternalContent());
/* 269 */     } else if (pdfNumber.isDoubleNumber()) {
/* 270 */       writeDouble(pdfNumber.getValue());
/*     */     } else {
/* 272 */       writeInteger(pdfNumber.intValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isNotMetadataPdfStream(PdfStream pdfStream) {
/* 277 */     return (pdfStream.getAsName(PdfName.Type) == null || (pdfStream
/* 278 */       .getAsName(PdfName.Type) != null && !pdfStream.getAsName(PdfName.Type).equals(PdfName.Metadata)));
/*     */   }
/*     */   
/*     */   private boolean isXRefStream(PdfStream pdfStream) {
/* 282 */     return PdfName.XRef.equals(pdfStream.getAsName(PdfName.Type));
/*     */   }
/*     */   
/*     */   private void write(PdfStream pdfStream) {
/*     */     try {
/* 287 */       boolean userDefinedCompression = (pdfStream.getCompressionLevel() != Integer.MIN_VALUE);
/* 288 */       if (!userDefinedCompression) {
/*     */         
/* 290 */         int defaultCompressionLevel = (this.document != null) ? this.document.getWriter().getCompressionLevel() : -1;
/*     */         
/* 292 */         pdfStream.setCompressionLevel(defaultCompressionLevel);
/*     */       } 
/* 294 */       boolean toCompress = (pdfStream.getCompressionLevel() != 0);
/* 295 */       boolean allowCompression = (!pdfStream.containsKey(PdfName.Filter) && isNotMetadataPdfStream(pdfStream));
/*     */       
/* 297 */       if (pdfStream.getInputStream() != null) {
/* 298 */         OutputStreamEncryption outputStreamEncryption1; DeflaterOutputStream deflaterOutputStream1; PdfOutputStream pdfOutputStream = this;
/* 299 */         DeflaterOutputStream def = null;
/* 300 */         OutputStreamEncryption ose = null;
/* 301 */         if (this.crypto != null && !this.crypto.isEmbeddedFilesOnly()) {
/* 302 */           outputStreamEncryption1 = ose = this.crypto.getEncryptionStream((OutputStream)pdfOutputStream);
/*     */         }
/* 304 */         if (toCompress && (allowCompression || userDefinedCompression)) {
/* 305 */           updateCompressionFilter(pdfStream);
/* 306 */           deflaterOutputStream1 = def = new DeflaterOutputStream((OutputStream)outputStreamEncryption1, pdfStream.getCompressionLevel(), 32768);
/*     */         } 
/* 308 */         write(pdfStream);
/* 309 */         writeBytes(stream);
/* 310 */         long beginStreamContent = getCurrentPos();
/* 311 */         byte[] buf = new byte[4192];
/*     */         while (true) {
/* 313 */           int n = pdfStream.getInputStream().read(buf);
/* 314 */           if (n <= 0)
/*     */             break; 
/* 316 */           deflaterOutputStream1.write(buf, 0, n);
/*     */         } 
/* 318 */         if (def != null) {
/* 319 */           def.finish();
/*     */         }
/* 321 */         if (ose != null) {
/* 322 */           ose.finish();
/*     */         }
/* 324 */         PdfNumber length = pdfStream.getAsNumber(PdfName.Length);
/* 325 */         length.setValue((int)(getCurrentPos() - beginStreamContent));
/* 326 */         pdfStream.updateLength(length.intValue());
/* 327 */         writeBytes(endstream);
/*     */       } else {
/*     */         ByteArrayOutputStream byteArrayStream;
/*     */         
/* 331 */         if (pdfStream.getOutputStream() == null && pdfStream.getIndirectReference().getReader() != null) {
/*     */ 
/*     */           
/* 334 */           byte[] bytes = pdfStream.getIndirectReference().getReader().readStreamBytes(pdfStream, false);
/* 335 */           if (userDefinedCompression) {
/* 336 */             bytes = decodeFlateBytes(pdfStream, bytes);
/*     */           }
/* 338 */           pdfStream.initOutputStream((OutputStream)new ByteArrayOutputStream(bytes.length));
/* 339 */           pdfStream.getOutputStream().write(bytes);
/*     */         } 
/* 341 */         assert pdfStream.getOutputStream() != null : "PdfStream lost OutputStream";
/*     */         
/*     */         try {
/* 344 */           if (toCompress && !containsFlateFilter(pdfStream) && (allowCompression || userDefinedCompression)) {
/*     */             
/* 346 */             updateCompressionFilter(pdfStream);
/* 347 */             byteArrayStream = new ByteArrayOutputStream();
/* 348 */             DeflaterOutputStream zip = new DeflaterOutputStream((OutputStream)byteArrayStream, pdfStream.getCompressionLevel());
/* 349 */             if (pdfStream instanceof PdfObjectStream) {
/* 350 */               PdfObjectStream objectStream = (PdfObjectStream)pdfStream;
/* 351 */               ((ByteArrayOutputStream)objectStream.getIndexStream().getOutputStream()).writeTo((OutputStream)zip);
/* 352 */               ((ByteArrayOutputStream)objectStream.getOutputStream().getOutputStream()).writeTo((OutputStream)zip);
/*     */             } else {
/* 354 */               assert pdfStream.getOutputStream() != null : "Error in outputStream";
/* 355 */               ((ByteArrayOutputStream)pdfStream.getOutputStream().getOutputStream()).writeTo((OutputStream)zip);
/*     */             } 
/* 357 */             zip.finish();
/*     */           }
/* 359 */           else if (pdfStream instanceof PdfObjectStream) {
/* 360 */             PdfObjectStream objectStream = (PdfObjectStream)pdfStream;
/* 361 */             byteArrayStream = new ByteArrayOutputStream();
/* 362 */             ((ByteArrayOutputStream)objectStream.getIndexStream().getOutputStream()).writeTo((OutputStream)byteArrayStream);
/* 363 */             ((ByteArrayOutputStream)objectStream.getOutputStream().getOutputStream()).writeTo((OutputStream)byteArrayStream);
/*     */           } else {
/* 365 */             assert pdfStream.getOutputStream() != null : "Error in outputStream";
/* 366 */             byteArrayStream = (ByteArrayOutputStream)pdfStream.getOutputStream().getOutputStream();
/*     */           } 
/*     */           
/* 369 */           if (checkEncryption(pdfStream)) {
/* 370 */             ByteArrayOutputStream encodedStream = new ByteArrayOutputStream();
/* 371 */             OutputStreamEncryption ose = this.crypto.getEncryptionStream((OutputStream)encodedStream);
/* 372 */             byteArrayStream.writeTo((OutputStream)ose);
/* 373 */             ose.finish();
/* 374 */             byteArrayStream = encodedStream;
/*     */           } 
/* 376 */         } catch (IOException ioe) {
/* 377 */           throw new PdfException("I/O exception.", ioe);
/*     */         } 
/* 379 */         pdfStream.put(PdfName.Length, new PdfNumber(byteArrayStream.size()));
/* 380 */         pdfStream.updateLength(byteArrayStream.size());
/* 381 */         write(pdfStream);
/* 382 */         writeBytes(stream);
/* 383 */         byteArrayStream.writeTo((OutputStream)this);
/* 384 */         byteArrayStream.close();
/* 385 */         writeBytes(endstream);
/*     */       } 
/* 387 */     } catch (IOException e) {
/* 388 */       throw new PdfException("Cannot write to PdfStream.", e, pdfStream);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean checkEncryption(PdfStream pdfStream) {
/* 393 */     if (this.crypto == null || this.crypto.isEmbeddedFilesOnly())
/* 394 */       return false; 
/* 395 */     if (isXRefStream(pdfStream))
/*     */     {
/* 397 */       return false;
/*     */     }
/* 399 */     PdfObject filter = pdfStream.get(PdfName.Filter, true);
/* 400 */     if (filter != null) {
/* 401 */       if (PdfName.Crypt.equals(filter))
/* 402 */         return false; 
/* 403 */       if (filter.getType() == 1) {
/* 404 */         PdfArray filters = (PdfArray)filter;
/* 405 */         if (!filters.isEmpty() && PdfName.Crypt.equals(filters.get(0, true))) {
/* 406 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 410 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean containsFlateFilter(PdfStream pdfStream) {
/* 415 */     PdfObject filter = pdfStream.get(PdfName.Filter);
/* 416 */     if (filter != null) {
/* 417 */       if (filter.getType() == 6) {
/* 418 */         if (PdfName.FlateDecode.equals(filter)) {
/* 419 */           return true;
/*     */         }
/* 421 */       } else if (filter.getType() == 1) {
/* 422 */         if (((PdfArray)filter).contains(PdfName.FlateDecode))
/* 423 */           return true; 
/*     */       } else {
/* 425 */         throw new PdfException("filter is not a name or array.");
/*     */       } 
/*     */     }
/* 428 */     return false;
/*     */   }
/*     */   
/*     */   protected void updateCompressionFilter(PdfStream pdfStream) {
/* 432 */     PdfObject filter = pdfStream.get(PdfName.Filter);
/* 433 */     if (filter == null) {
/* 434 */       pdfStream.put(PdfName.Filter, PdfName.FlateDecode);
/*     */     } else {
/* 436 */       PdfArray filters = new PdfArray();
/* 437 */       filters.add(PdfName.FlateDecode);
/* 438 */       if (filter instanceof PdfArray) {
/* 439 */         filters.addAll((PdfArray)filter);
/*     */       } else {
/* 441 */         filters.add(filter);
/*     */       } 
/* 443 */       PdfObject decodeParms = pdfStream.get(PdfName.DecodeParms);
/* 444 */       if (decodeParms != null) {
/* 445 */         if (decodeParms instanceof PdfDictionary) {
/* 446 */           PdfArray array = new PdfArray();
/* 447 */           array.add(new PdfNull());
/* 448 */           array.add(decodeParms);
/* 449 */           pdfStream.put(PdfName.DecodeParms, array);
/* 450 */         } else if (decodeParms instanceof PdfArray) {
/* 451 */           ((PdfArray)decodeParms).add(0, new PdfNull());
/*     */         } else {
/* 453 */           throw (new PdfException("Decode parameter type {0} is not supported.")).setMessageParams(new Object[] { decodeParms.getClass().toString() });
/*     */         } 
/*     */       }
/* 456 */       pdfStream.put(PdfName.Filter, filters);
/*     */     } 
/*     */   } protected byte[] decodeFlateBytes(PdfStream stream, byte[] bytes) {
/*     */     PdfName filterName;
/*     */     PdfDictionary decodeParams;
/* 461 */     PdfObject filterObject = stream.get(PdfName.Filter);
/* 462 */     if (filterObject == null) {
/* 463 */       return bytes;
/*     */     }
/*     */ 
/*     */     
/* 467 */     PdfArray filtersArray = null;
/* 468 */     if (filterObject instanceof PdfName) {
/* 469 */       filterName = (PdfName)filterObject;
/* 470 */     } else if (filterObject instanceof PdfArray) {
/* 471 */       filtersArray = (PdfArray)filterObject;
/* 472 */       filterName = filtersArray.getAsName(0);
/*     */     } else {
/* 474 */       throw new PdfException("filter is not a name or array.");
/*     */     } 
/*     */     
/* 477 */     if (!PdfName.FlateDecode.equals(filterName)) {
/* 478 */       return bytes;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 483 */     PdfArray decodeParamsArray = null;
/* 484 */     PdfObject decodeParamsObject = stream.get(PdfName.DecodeParms);
/* 485 */     if (decodeParamsObject == null) {
/* 486 */       decodeParams = null;
/* 487 */     } else if (decodeParamsObject.getType() == 3) {
/* 488 */       decodeParams = (PdfDictionary)decodeParamsObject;
/* 489 */     } else if (decodeParamsObject.getType() == 1) {
/* 490 */       decodeParamsArray = (PdfArray)decodeParamsObject;
/* 491 */       decodeParams = decodeParamsArray.getAsDictionary(0);
/*     */     } else {
/* 493 */       throw (new PdfException("Decode parameter type {0} is not supported.")).setMessageParams(new Object[] { decodeParamsObject.getClass().toString() });
/*     */     } 
/*     */ 
/*     */     
/* 497 */     byte[] res = FlateDecodeFilter.flateDecode(bytes, true);
/* 498 */     if (res == null)
/* 499 */       res = FlateDecodeFilter.flateDecode(bytes, false); 
/* 500 */     bytes = FlateDecodeFilter.decodePredictor(res, decodeParams);
/*     */ 
/*     */ 
/*     */     
/* 504 */     filterObject = null;
/* 505 */     if (filtersArray != null) {
/* 506 */       filtersArray.remove(0);
/* 507 */       if (filtersArray.size() == 1) {
/* 508 */         filterObject = filtersArray.get(0);
/* 509 */       } else if (!filtersArray.isEmpty()) {
/* 510 */         filterObject = filtersArray;
/*     */       } 
/*     */     } 
/*     */     
/* 514 */     decodeParamsObject = null;
/* 515 */     if (decodeParamsArray != null) {
/* 516 */       decodeParamsArray.remove(0);
/* 517 */       if (decodeParamsArray.size() == 1 && decodeParamsArray.get(0).getType() != 7) {
/* 518 */         decodeParamsObject = decodeParamsArray.get(0);
/* 519 */       } else if (!decodeParamsArray.isEmpty()) {
/* 520 */         decodeParamsObject = decodeParamsArray;
/*     */       } 
/*     */     } 
/*     */     
/* 524 */     if (filterObject == null) {
/* 525 */       stream.remove(PdfName.Filter);
/*     */     } else {
/* 527 */       stream.put(PdfName.Filter, filterObject);
/*     */     } 
/*     */     
/* 530 */     if (decodeParamsObject == null) {
/* 531 */       stream.remove(PdfName.DecodeParms);
/*     */     } else {
/* 533 */       stream.put(PdfName.DecodeParms, decodeParamsObject);
/*     */     } 
/*     */     
/* 536 */     return bytes;
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
/* 547 */     in.defaultReadObject();
/* 548 */     if (this.outputStream == null && this.duplicateContentBuffer != null) {
/* 549 */       this.outputStream = (OutputStream)new ByteArrayOutputStream();
/* 550 */       write(this.duplicateContentBuffer);
/* 551 */       this.duplicateContentBuffer = null;
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
/* 562 */     OutputStream tempOutputStream = this.outputStream;
/* 563 */     if (this.outputStream instanceof ByteArrayOutputStream) {
/* 564 */       this.duplicateContentBuffer = ((ByteArrayOutputStream)this.outputStream).toByteArray();
/*     */     }
/* 566 */     this.outputStream = null;
/* 567 */     out.defaultWriteObject();
/* 568 */     this.outputStream = tempOutputStream;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfOutputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */