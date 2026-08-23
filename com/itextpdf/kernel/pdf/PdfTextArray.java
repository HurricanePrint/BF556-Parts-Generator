/*     */ package com.itextpdf.kernel.pdf;
/*     */ 
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfTextArray
/*     */   extends PdfArray
/*     */ {
/*     */   private static final long serialVersionUID = 2555632135770071680L;
/*  65 */   private float lastNumber = Float.NaN;
/*     */   
/*     */   private StringBuilder lastString;
/*     */   
/*     */   public void add(PdfObject pdfObject) {
/*  70 */     if (pdfObject.isNumber()) {
/*  71 */       add(((PdfNumber)pdfObject).floatValue());
/*  72 */     } else if (pdfObject instanceof PdfString) {
/*  73 */       add(((PdfString)pdfObject).getValueBytes());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(PdfArray a) {
/*  84 */     if (a != null) {
/*  85 */       addAll(a.list);
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
/*     */   public void addAll(Collection<PdfObject> c) {
/*  97 */     for (PdfObject obj : c) {
/*  98 */       add(obj);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(float number) {
/* 104 */     if (number != 0.0F) {
/* 105 */       if (!Float.isNaN(this.lastNumber)) {
/* 106 */         this.lastNumber = number + this.lastNumber;
/* 107 */         if (this.lastNumber != 0.0F) {
/* 108 */           set(size() - 1, new PdfNumber(this.lastNumber));
/*     */         } else {
/* 110 */           remove(size() - 1);
/*     */         } 
/*     */       } else {
/* 113 */         this.lastNumber = number;
/* 114 */         super.add(new PdfNumber(this.lastNumber));
/*     */       } 
/* 116 */       this.lastString = null;
/* 117 */       return true;
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(String text, PdfFont font) {
/* 124 */     return add(font.convertToBytes(text));
/*     */   }
/*     */   
/*     */   public boolean add(byte[] text) {
/* 128 */     return add((new PdfString(text)).getValue());
/*     */   }
/*     */   
/*     */   protected boolean add(String text) {
/* 132 */     if (text.length() > 0) {
/* 133 */       if (this.lastString != null) {
/* 134 */         this.lastString.append(text);
/* 135 */         set(size() - 1, new PdfString(this.lastString.toString()));
/*     */       } else {
/* 137 */         this.lastString = new StringBuilder(text);
/* 138 */         super.add(new PdfString(this.lastString.toString()));
/*     */       } 
/* 140 */       this.lastNumber = Float.NaN;
/* 141 */       return true;
/*     */     } 
/* 143 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfTextArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */