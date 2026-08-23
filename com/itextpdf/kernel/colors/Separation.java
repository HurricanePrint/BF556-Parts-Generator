/*    */ package com.itextpdf.kernel.colors;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
/*    */ import com.itextpdf.kernel.pdf.function.PdfFunction;
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
/*    */ public class Separation
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = 5995354549050682283L;
/*    */   
/*    */   public Separation(PdfSpecialCs.Separation cs) {
/* 55 */     this(cs, 1.0F);
/*    */   }
/*    */   
/*    */   public Separation(PdfSpecialCs.Separation cs, float value) {
/* 59 */     super((PdfColorSpace)cs, new float[] { value });
/*    */   }
/*    */   
/*    */   public Separation(String name, PdfColorSpace alternateCs, PdfFunction tintTransform, float value) {
/* 63 */     this(new PdfSpecialCs.Separation(name, alternateCs, tintTransform), value);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/Separation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */