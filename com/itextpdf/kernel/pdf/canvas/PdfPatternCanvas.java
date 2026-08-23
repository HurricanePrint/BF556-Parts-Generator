/*    */ package com.itextpdf.kernel.pdf.canvas;
/*    */ 
/*    */ import com.itextpdf.kernel.PdfException;
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.PdfResources;
/*    */ import com.itextpdf.kernel.pdf.PdfStream;
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*    */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfPatternCanvas
/*    */   extends PdfCanvas
/*    */ {
/*    */   private static final long serialVersionUID = -8325687042148621178L;
/*    */   private final PdfPattern.Tiling tilingPattern;
/*    */   
/*    */   public PdfPatternCanvas(PdfStream contentStream, PdfResources resources, PdfDocument document) {
/* 69 */     super(contentStream, resources, document);
/* 70 */     this.tilingPattern = new PdfPattern.Tiling(contentStream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PdfPatternCanvas(PdfPattern.Tiling pattern, PdfDocument document) {
/* 79 */     super((PdfStream)pattern.getPdfObject(), pattern.getResources(), document);
/* 80 */     this.tilingPattern = pattern;
/*    */   }
/*    */ 
/*    */   
/*    */   public PdfCanvas setColor(PdfColorSpace colorSpace, float[] colorValue, PdfPattern pattern, boolean fill) {
/* 85 */     checkNoColor();
/* 86 */     return super.setColor(colorSpace, colorValue, pattern, fill);
/*    */   }
/*    */   
/*    */   private void checkNoColor() {
/* 90 */     if (!this.tilingPattern.isColored())
/* 91 */       throw new PdfException("Content stream must not invoke operators that specify colors or other color related parameters in the graphics state."); 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/PdfPatternCanvas.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */