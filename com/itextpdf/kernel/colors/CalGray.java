/*    */ package com.itextpdf.kernel.colors;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
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
/*    */ public class CalGray
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = 2654434937251198951L;
/*    */   
/*    */   public CalGray(PdfCieBasedCs.CalGray cs) {
/* 53 */     this(cs, 0.0F);
/*    */   }
/*    */   
/*    */   public CalGray(PdfCieBasedCs.CalGray cs, float value) {
/* 57 */     super((PdfColorSpace)cs, new float[] { value });
/*    */   }
/*    */   
/*    */   public CalGray(float[] whitePoint, float value) {
/* 61 */     super((PdfColorSpace)new PdfCieBasedCs.CalGray(whitePoint), new float[] { value });
/*    */   }
/*    */   
/*    */   public CalGray(float[] whitePoint, float[] blackPoint, float gamma, float value) {
/* 65 */     this(new PdfCieBasedCs.CalGray(whitePoint, blackPoint, gamma), value);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/CalGray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */