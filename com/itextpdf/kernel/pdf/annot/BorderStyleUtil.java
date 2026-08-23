/*    */ package com.itextpdf.kernel.pdf.annot;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfArray;
/*    */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*    */ import com.itextpdf.kernel.pdf.PdfName;
/*    */ import com.itextpdf.kernel.pdf.PdfObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BorderStyleUtil
/*    */ {
/*    */   public static final PdfDictionary setStyle(PdfDictionary bs, PdfName style) {
/* 69 */     if (null == bs) {
/* 70 */       bs = new PdfDictionary();
/*    */     }
/* 72 */     bs.put(PdfName.S, (PdfObject)style);
/* 73 */     return bs;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final PdfDictionary setDashPattern(PdfDictionary bs, PdfArray dashPattern) {
/* 87 */     if (null == bs) {
/* 88 */       bs = new PdfDictionary();
/*    */     }
/* 90 */     bs.put(PdfName.D, (PdfObject)dashPattern);
/* 91 */     return bs;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/BorderStyleUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */