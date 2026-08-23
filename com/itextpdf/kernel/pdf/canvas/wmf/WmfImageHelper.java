/*     */ package com.itextpdf.kernel.pdf.canvas.wmf;
/*     */ 
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.io.image.ImageType;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfXObject;
/*     */ import java.io.ByteArrayInputStream;
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
/*     */ 
/*     */ 
/*     */ public class WmfImageHelper
/*     */ {
/*  64 */   public static float wmfFontCorrection = 0.86F;
/*     */ 
/*     */   
/*     */   private WmfImageData wmf;
/*     */ 
/*     */   
/*     */   private float plainWidth;
/*     */ 
/*     */   
/*     */   private float plainHeight;
/*     */ 
/*     */ 
/*     */   
/*     */   public WmfImageHelper(ImageData wmf) {
/*  78 */     if (wmf.getOriginalType() != ImageType.WMF)
/*  79 */       throw new IllegalArgumentException("WMF image expected"); 
/*  80 */     this.wmf = (WmfImageData)wmf;
/*  81 */     processParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processParameters() {
/*  88 */     InputStream is = null;
/*     */     try {
/*     */       String errorID;
/*  91 */       if (this.wmf.getData() == null) {
/*  92 */         is = this.wmf.getUrl().openStream();
/*  93 */         errorID = this.wmf.getUrl().toString();
/*     */       } else {
/*     */         
/*  96 */         is = new ByteArrayInputStream(this.wmf.getData());
/*  97 */         errorID = "Byte array";
/*     */       } 
/*  99 */       InputMeta in = new InputMeta(is);
/* 100 */       if (in.readInt() != -1698247209) {
/* 101 */         throw new PdfException("{0} is not a valid placeable windows metafile.", errorID);
/*     */       }
/* 103 */       in.readWord();
/* 104 */       int left = in.readShort();
/* 105 */       int top = in.readShort();
/* 106 */       int right = in.readShort();
/* 107 */       int bottom = in.readShort();
/* 108 */       int inch = in.readWord();
/* 109 */       this.wmf.setDpi(72, 72);
/* 110 */       this.wmf.setHeight((bottom - top) / inch * 72.0F);
/* 111 */       this.wmf.setWidth((right - left) / inch * 72.0F);
/* 112 */     } catch (IOException e) {
/* 113 */       throw new PdfException("WMF image exception.");
/*     */     } finally {
/* 115 */       if (is != null) {
/*     */         try {
/* 117 */           is.close();
/* 118 */         } catch (IOException iOException) {}
/*     */       }
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
/*     */   public PdfXObject createFormXObject(PdfDocument document) {
/* 131 */     PdfFormXObject pdfForm = new PdfFormXObject(new Rectangle(0.0F, 0.0F, this.wmf.getWidth(), this.wmf.getHeight()));
/* 132 */     PdfCanvas canvas = new PdfCanvas(pdfForm, document);
/*     */     
/* 134 */     InputStream is = null;
/*     */     try {
/* 136 */       if (this.wmf.getData() == null) {
/* 137 */         is = this.wmf.getUrl().openStream();
/*     */       } else {
/*     */         
/* 140 */         is = new ByteArrayInputStream(this.wmf.getData());
/*     */       } 
/* 142 */       MetaDo meta = new MetaDo(is, canvas);
/* 143 */       meta.readAll();
/* 144 */     } catch (IOException e) {
/* 145 */       throw new PdfException("WMF image exception.", e);
/*     */     } finally {
/* 147 */       if (is != null) {
/*     */         try {
/* 149 */           is.close();
/* 150 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/* 153 */     return (PdfXObject)pdfForm;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/WmfImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */