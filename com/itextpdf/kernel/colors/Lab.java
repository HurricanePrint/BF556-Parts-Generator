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
/*    */ public class Lab
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = -103738025280259190L;
/*    */   
/*    */   public Lab(PdfCieBasedCs.Lab cs) {
/* 53 */     this(cs, new float[cs.getNumberOfComponents()]);
/*    */   }
/*    */   
/*    */   public Lab(PdfCieBasedCs.Lab cs, float[] value) {
/* 57 */     super((PdfColorSpace)cs, value);
/*    */   }
/*    */   
/*    */   public Lab(float[] whitePoint, float[] value) {
/* 61 */     super((PdfColorSpace)new PdfCieBasedCs.Lab(whitePoint), value);
/*    */   }
/*    */   
/*    */   public Lab(float[] whitePoint, float[] blackPoint, float[] range, float[] value) {
/* 65 */     this(new PdfCieBasedCs.Lab(whitePoint, blackPoint, range), value);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/Lab.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */