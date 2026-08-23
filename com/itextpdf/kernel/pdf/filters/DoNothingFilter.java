/*    */ package com.itextpdf.kernel.pdf.filters;
/*    */ 
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
/*    */ public class DoNothingFilter
/*    */   implements IFilterHandler
/*    */ {
/*    */   private PdfName lastFilterName;
/*    */   
/*    */   public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
/* 59 */     this.lastFilterName = filterName;
/* 60 */     return b;
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
/*    */   @Deprecated
/*    */   public PdfName getLastFilterName() {
/* 73 */     return this.lastFilterName;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/filters/DoNothingFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */