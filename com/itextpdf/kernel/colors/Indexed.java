/*    */ package com.itextpdf.kernel.colors;
/*    */ 
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
/*    */ 
/*    */ public class Indexed
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = 5374740389023596345L;
/*    */   
/*    */   public Indexed(PdfColorSpace colorSpace) {
/* 53 */     this(colorSpace, 0);
/*    */   }
/*    */   
/*    */   public Indexed(PdfColorSpace colorSpace, int colorValue) {
/* 57 */     super(colorSpace, new float[] { colorValue });
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/Indexed.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */