/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PdfObjectStream
/*     */   extends PdfStream
/*     */ {
/*     */   private static final long serialVersionUID = -3513488307665597642L;
/*     */   public static final int MAX_OBJ_STREAM_SIZE = 200;
/*  62 */   protected PdfNumber size = new PdfNumber(0);
/*     */ 
/*     */   
/*     */   protected PdfOutputStream indexStream;
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObjectStream(PdfDocument doc) {
/*  70 */     this(doc, (OutputStream)new ByteArrayOutputStream());
/*  71 */     this.indexStream = new PdfOutputStream((OutputStream)new ByteArrayOutputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PdfObjectStream(PdfObjectStream prev) {
/*  80 */     this(prev.getIndirectReference().getDocument(), prev.getOutputStream().getOutputStream());
/*  81 */     this.indexStream = new PdfOutputStream(prev.indexStream.getOutputStream());
/*  82 */     ((ByteArrayOutputStream)this.outputStream.getOutputStream()).reset();
/*  83 */     ((ByteArrayOutputStream)this.indexStream.getOutputStream()).reset();
/*     */     
/*  85 */     prev.releaseContent(true);
/*     */   }
/*     */   
/*     */   private PdfObjectStream(PdfDocument doc, OutputStream outputStream) {
/*  89 */     super(outputStream);
/*     */     
/*  91 */     makeIndirect(doc, doc.getXref().createNewIndirectReference(doc));
/*  92 */     (getOutputStream()).document = doc;
/*  93 */     put(PdfName.Type, PdfName.ObjStm);
/*  94 */     put(PdfName.N, this.size);
/*  95 */     put(PdfName.First, new PdfNumber(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObject(PdfObject object) {
/* 104 */     if (this.size.intValue() == 200) {
/* 105 */       throw new PdfException("PdfObjectStream reach max size.");
/*     */     }
/* 107 */     PdfOutputStream outputStream = getOutputStream();
/* 108 */     ((PdfOutputStream)((PdfOutputStream)((PdfOutputStream)this.indexStream.writeInteger(object.getIndirectReference().getObjNumber()))
/* 109 */       .writeSpace())
/* 110 */       .writeLong(outputStream.getCurrentPos()))
/* 111 */       .writeSpace();
/* 112 */     outputStream.write(object);
/* 113 */     object.getIndirectReference().setObjStreamNumber(getIndirectReference().getObjNumber());
/* 114 */     object.getIndirectReference().setIndex(this.size.intValue());
/* 115 */     outputStream.writeSpace();
/* 116 */     this.size.increment();
/* 117 */     getAsNumber(PdfName.First).setValue(this.indexStream.getCurrentPos());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 126 */     return this.size.intValue();
/*     */   }
/*     */   
/*     */   public PdfOutputStream getIndexStream() {
/* 130 */     return this.indexStream;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void releaseContent() {
/* 135 */     releaseContent(false);
/*     */   }
/*     */   
/*     */   private void releaseContent(boolean close) {
/* 139 */     if (close) {
/* 140 */       this.outputStream = null;
/* 141 */       this.indexStream = null;
/* 142 */       super.releaseContent();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfObjectStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */