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
/*    */ public class CalRgb
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = 3916506066056271822L;
/*    */   
/*    */   public CalRgb(PdfCieBasedCs.CalRgb cs) {
/* 53 */     this(cs, new float[cs.getNumberOfComponents()]);
/*    */   }
/*    */   
/*    */   public CalRgb(PdfCieBasedCs.CalRgb cs, float[] value) {
/* 57 */     super((PdfColorSpace)cs, value);
/*    */   }
/*    */   
/*    */   public CalRgb(float[] whitePoint, float[] value) {
/* 61 */     super((PdfColorSpace)new PdfCieBasedCs.CalRgb(whitePoint), value);
/*    */   }
/*    */   
/*    */   public CalRgb(float[] whitePoint, float[] blackPoint, float[] gamma, float[] matrix, float[] value) {
/* 65 */     this(new PdfCieBasedCs.CalRgb(whitePoint, blackPoint, gamma, matrix), value);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/CalRgb.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */