/*    */ package com.itextpdf.barcodes;
/*    */ 
/*    */ import com.itextpdf.kernel.colors.Color;
/*    */ import com.itextpdf.kernel.geom.Rectangle;
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*    */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Barcode2D
/*    */ {
/*    */   protected static final float DEFAULT_MODULE_SIZE = 1.0F;
/*    */   
/*    */   public abstract Rectangle getBarcodeSize();
/*    */   
/*    */   public abstract Rectangle placeBarcode(PdfCanvas paramPdfCanvas, Color paramColor);
/*    */   
/*    */   public PdfFormXObject createFormXObject(PdfDocument document) {
/* 83 */     return createFormXObject(null, document);
/*    */   }
/*    */   
/*    */   public abstract PdfFormXObject createFormXObject(Color paramColor, PdfDocument paramPdfDocument);
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/Barcode2D.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */