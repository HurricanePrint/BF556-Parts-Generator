/*     */ package com.itextpdf.kernel.pdf.filespec;
/*     */ 
/*     */ import com.itextpdf.io.util.UrlUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDate;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import com.itextpdf.kernel.pdf.collection.PdfCollectionItem;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfFileSpec
/*     */   extends PdfObjectWrapper<PdfObject>
/*     */ {
/*     */   private static final long serialVersionUID = 126861971006090239L;
/*     */   
/*     */   protected PdfFileSpec(PdfObject pdfObject) {
/*  71 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfFileSpec wrapFileSpecObject(PdfObject fileSpecObject) {
/*  82 */     if (fileSpecObject != null) {
/*  83 */       if (fileSpecObject.isString())
/*  84 */         return new PdfStringFS((PdfString)fileSpecObject); 
/*  85 */       if (fileSpecObject.isDictionary()) {
/*  86 */         return new PdfDictionaryFS((PdfDictionary)fileSpecObject);
/*     */       }
/*     */     } 
/*  89 */     return null;
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
/*     */   public static PdfFileSpec createExternalFileSpec(PdfDocument doc, String filePath, PdfName afRelationshipValue) {
/* 103 */     PdfDictionary dict = new PdfDictionary();
/* 104 */     dict.put(PdfName.Type, (PdfObject)PdfName.Filespec);
/* 105 */     dict.put(PdfName.F, (PdfObject)new PdfString(filePath));
/* 106 */     dict.put(PdfName.UF, (PdfObject)new PdfString(filePath, "UnicodeBig"));
/* 107 */     if (afRelationshipValue != null) {
/* 108 */       dict.put(PdfName.AFRelationship, (PdfObject)afRelationshipValue);
/*     */     } else {
/* 110 */       dict.put(PdfName.AFRelationship, (PdfObject)PdfName.Unspecified);
/*     */     } 
/* 112 */     return (PdfFileSpec)(new PdfFileSpec((PdfObject)dict)).makeIndirect(doc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PdfFileSpec createExternalFileSpec(PdfDocument doc, String filePath) {
/* 123 */     return createExternalFileSpec(doc, filePath, (PdfName)null);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, byte[] fileStore, String description, String fileDisplay, PdfName mimeType, PdfDictionary fileParameter, PdfName afRelationshipValue) {
/* 142 */     PdfStream stream = (PdfStream)(new PdfStream(fileStore)).makeIndirect(doc);
/* 143 */     PdfDictionary params = new PdfDictionary();
/* 144 */     if (fileParameter != null) {
/* 145 */       params.mergeDifferent(fileParameter);
/*     */     }
/* 147 */     if (!params.containsKey(PdfName.ModDate)) {
/* 148 */       params.put(PdfName.ModDate, (new PdfDate()).getPdfObject());
/*     */     }
/* 150 */     if (fileStore != null) {
/* 151 */       params.put(PdfName.Size, (PdfObject)new PdfNumber((stream.getBytes()).length));
/*     */     }
/* 153 */     stream.put(PdfName.Params, (PdfObject)params);
/* 154 */     return createEmbeddedFileSpec(doc, stream, description, fileDisplay, mimeType, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, byte[] fileStore, String description, String fileDisplay, PdfDictionary fileParameter, PdfName afRelationshipValue) {
/* 171 */     return createEmbeddedFileSpec(doc, fileStore, description, fileDisplay, (PdfName)null, fileParameter, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, byte[] fileStore, String fileDisplay, PdfDictionary fileParameter, PdfName afRelationshipValue) {
/* 188 */     return createEmbeddedFileSpec(doc, fileStore, (String)null, fileDisplay, (PdfName)null, fileParameter, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, byte[] fileStore, String fileDisplay, PdfName afRelationshipValue) {
/* 203 */     return createEmbeddedFileSpec(doc, fileStore, (String)null, fileDisplay, (PdfName)null, (PdfDictionary)null, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, byte[] fileStore, String description, String fileDisplay, PdfName afRelationshipValue) {
/* 219 */     return createEmbeddedFileSpec(doc, fileStore, description, fileDisplay, (PdfName)null, (PdfDictionary)null, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, String filePath, String description, String fileDisplay, PdfName mimeType, PdfDictionary fileParameter, PdfName afRelationshipValue) throws IOException {
/* 240 */     PdfStream stream = new PdfStream(doc, UrlUtil.toURL(filePath).openStream());
/* 241 */     PdfDictionary params = new PdfDictionary();
/* 242 */     if (fileParameter != null) {
/* 243 */       params.mergeDifferent(fileParameter);
/*     */     }
/* 245 */     if (!params.containsKey(PdfName.ModDate)) {
/* 246 */       params.put(PdfName.ModDate, (new PdfDate()).getPdfObject());
/*     */     }
/* 248 */     stream.put(PdfName.Params, (PdfObject)params);
/* 249 */     return createEmbeddedFileSpec(doc, stream, description, fileDisplay, mimeType, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, String filePath, String description, String fileDisplay, PdfName mimeType, PdfName afRelationshipValue) throws IOException {
/* 268 */     return createEmbeddedFileSpec(doc, filePath, description, fileDisplay, mimeType, (PdfDictionary)null, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, String filePath, String description, String fileDisplay, PdfName afRelationshipValue) throws IOException {
/* 285 */     return createEmbeddedFileSpec(doc, filePath, description, fileDisplay, (PdfName)null, (PdfDictionary)null, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, String filePath, String fileDisplay, PdfName afRelationshipValue) throws IOException {
/* 301 */     return createEmbeddedFileSpec(doc, filePath, (String)null, fileDisplay, (PdfName)null, (PdfDictionary)null, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, InputStream is, String description, String fileDisplay, PdfName mimeType, PdfDictionary fileParameter, PdfName afRelationshipValue) {
/* 320 */     PdfStream stream = new PdfStream(doc, is);
/* 321 */     PdfDictionary params = new PdfDictionary();
/* 322 */     if (fileParameter != null) {
/* 323 */       params.mergeDifferent(fileParameter);
/*     */     }
/* 325 */     if (!params.containsKey(PdfName.ModDate)) {
/* 326 */       params.put(PdfName.ModDate, (new PdfDate()).getPdfObject());
/*     */     }
/* 328 */     stream.put(PdfName.Params, (PdfObject)params);
/* 329 */     return createEmbeddedFileSpec(doc, stream, description, fileDisplay, mimeType, afRelationshipValue);
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
/*     */   public static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, InputStream is, String description, String fileDisplay, PdfName mimeType, PdfName afRelationshipValue) {
/* 347 */     return createEmbeddedFileSpec(doc, is, description, fileDisplay, mimeType, (PdfDictionary)null, afRelationshipValue);
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
/*     */   private static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, PdfStream stream, String description, String fileDisplay, PdfName mimeType, PdfName afRelationshipValue) {
/* 365 */     PdfDictionary dict = new PdfDictionary();
/* 366 */     stream.put(PdfName.Type, (PdfObject)PdfName.EmbeddedFile);
/* 367 */     if (afRelationshipValue != null) {
/* 368 */       dict.put(PdfName.AFRelationship, (PdfObject)afRelationshipValue);
/*     */     } else {
/* 370 */       dict.put(PdfName.AFRelationship, (PdfObject)PdfName.Unspecified);
/*     */     } 
/*     */     
/* 373 */     if (mimeType != null) {
/* 374 */       stream.put(PdfName.Subtype, (PdfObject)mimeType);
/*     */     } else {
/* 376 */       stream.put(PdfName.Subtype, (PdfObject)PdfName.ApplicationOctetStream);
/*     */     } 
/*     */     
/* 379 */     if (description != null) {
/* 380 */       dict.put(PdfName.Desc, (PdfObject)new PdfString(description));
/*     */     }
/* 382 */     dict.put(PdfName.Type, (PdfObject)PdfName.Filespec);
/* 383 */     dict.put(PdfName.F, (PdfObject)new PdfString(fileDisplay));
/* 384 */     dict.put(PdfName.UF, (PdfObject)new PdfString(fileDisplay, "UnicodeBig"));
/*     */     
/* 386 */     PdfDictionary ef = new PdfDictionary();
/* 387 */     ef.put(PdfName.F, (PdfObject)stream);
/* 388 */     ef.put(PdfName.UF, (PdfObject)stream);
/* 389 */     dict.put(PdfName.EF, (PdfObject)ef);
/*     */     
/* 391 */     return (PdfFileSpec)(new PdfFileSpec((PdfObject)dict)).makeIndirect(doc);
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
/*     */   private static PdfFileSpec createEmbeddedFileSpec(PdfDocument doc, PdfStream stream, String description, String fileDisplay, PdfName afRelationshipValue) {
/* 406 */     return createEmbeddedFileSpec(doc, stream, description, fileDisplay, (PdfName)null, afRelationshipValue);
/*     */   }
/*     */   
/*     */   public PdfFileSpec setFileIdentifier(PdfArray fileIdentifier) {
/* 410 */     return put(PdfName.ID, (PdfObject)fileIdentifier);
/*     */   }
/*     */   
/*     */   public PdfArray getFileIdentifier() {
/* 414 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.ID);
/*     */   }
/*     */   
/*     */   public PdfFileSpec setVolatile(PdfBoolean isVolatile) {
/* 418 */     return put(PdfName.Volatile, (PdfObject)isVolatile);
/*     */   }
/*     */   
/*     */   public PdfBoolean isVolatile() {
/* 422 */     return ((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.Volatile);
/*     */   }
/*     */   
/*     */   public PdfFileSpec setCollectionItem(PdfCollectionItem item) {
/* 426 */     return put(PdfName.CI, item.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFileSpec setThumbnailImage(PdfImageXObject thumbnailImage) {
/* 436 */     return put(PdfName.Thumb, thumbnailImage.getPdfObject());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfImageXObject getThumbnailImage() {
/* 445 */     PdfStream thumbnailStream = ((PdfDictionary)getPdfObject()).getAsStream(PdfName.Thumb);
/* 446 */     return (thumbnailStream != null) ? new PdfImageXObject(thumbnailStream) : null;
/*     */   }
/*     */   
/*     */   public PdfFileSpec put(PdfName key, PdfObject value) {
/* 450 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 451 */     setModified();
/* 452 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 457 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filespec/PdfFileSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */