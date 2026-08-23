/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public abstract class PdfPrimitiveObject
/*     */   extends PdfObject
/*     */ {
/*     */   private static final long serialVersionUID = -1788064882121987538L;
/*  56 */   protected byte[] content = null;
/*     */   
/*     */   protected boolean directOnly;
/*     */ 
/*     */   
/*     */   protected PdfPrimitiveObject() {}
/*     */ 
/*     */   
/*     */   protected PdfPrimitiveObject(boolean directOnly) {
/*  65 */     this.directOnly = directOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfPrimitiveObject(byte[] content) {
/*  74 */     this();
/*  75 */     assert content != null;
/*  76 */     this.content = content;
/*     */   }
/*     */   
/*     */   protected final byte[] getInternalContent() {
/*  80 */     if (this.content == null)
/*  81 */       generateContent(); 
/*  82 */     return this.content;
/*     */   }
/*     */   
/*     */   protected boolean hasContent() {
/*  86 */     return (this.content != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void generateContent();
/*     */   
/*     */   public PdfObject makeIndirect(PdfDocument document, PdfIndirectReference reference) {
/*  93 */     if (!this.directOnly) {
/*  94 */       return super.makeIndirect(document, reference);
/*     */     }
/*  96 */     Logger logger = LoggerFactory.getLogger(PdfObject.class);
/*  97 */     logger.warn("DirectOnly object cannot be indirect");
/*     */     
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfObject setIndirectReference(PdfIndirectReference indirectReference) {
/* 104 */     if (!this.directOnly) {
/* 105 */       super.setIndirectReference(indirectReference);
/*     */     } else {
/* 107 */       Logger logger = LoggerFactory.getLogger(PdfObject.class);
/* 108 */       logger.warn("DirectOnly object cannot be indirect");
/*     */     } 
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyContent(PdfObject from, PdfDocument document) {
/* 115 */     super.copyContent(from, document);
/* 116 */     PdfPrimitiveObject object = (PdfPrimitiveObject)from;
/* 117 */     if (object.content != null)
/* 118 */       this.content = Arrays.copyOf(object.content, object.content.length); 
/*     */   }
/*     */   
/*     */   protected int compareContent(PdfPrimitiveObject o) {
/* 122 */     for (int i = 0; i < Math.min(this.content.length, o.content.length); i++) {
/* 123 */       if (this.content[i] > o.content[i])
/* 124 */         return 1; 
/* 125 */       if (this.content[i] < o.content[i])
/* 126 */         return -1; 
/*     */     } 
/* 128 */     return Integer.compare(this.content.length, o.content.length);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfPrimitiveObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */