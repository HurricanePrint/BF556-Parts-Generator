/*    */ package com.itextpdf.kernel.colors;
/*    */ 
/*    */ import com.itextpdf.kernel.PdfException;
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IccBased
/*    */   extends Color
/*    */ {
/*    */   private static final long serialVersionUID = -2204252409856288615L;
/*    */   
/*    */   public IccBased(PdfCieBasedCs.IccBased cs) {
/* 56 */     this(cs, new float[cs.getNumberOfComponents()]);
/*    */   }
/*    */   
/*    */   public IccBased(PdfCieBasedCs.IccBased cs, float[] value) {
/* 60 */     super((PdfColorSpace)cs, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IccBased(InputStream iccStream) {
/* 69 */     this(new PdfCieBasedCs.IccBased(iccStream), (float[])null);
/* 70 */     this.colorValue = new float[getNumberOfComponents()];
/* 71 */     for (int i = 0; i < getNumberOfComponents(); i++) {
/* 72 */       this.colorValue[i] = 0.0F;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IccBased(InputStream iccStream, float[] value) {
/* 82 */     this(new PdfCieBasedCs.IccBased(iccStream), value);
/*    */   }
/*    */   
/*    */   public IccBased(InputStream iccStream, float[] range, float[] value) {
/* 86 */     this(new PdfCieBasedCs.IccBased(iccStream, range), value);
/* 87 */     if (getNumberOfComponents() * 2 != range.length)
/* 88 */       throw new PdfException("Invalid range array.", this); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/IccBased.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */