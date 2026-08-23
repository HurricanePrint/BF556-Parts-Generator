/*     */ package com.itextpdf.barcodes;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarcodeEANSUPP
/*     */   extends Barcode1D
/*     */ {
/*     */   protected Barcode1D ean;
/*     */   protected Barcode1D supp;
/*     */   
/*     */   public BarcodeEANSUPP(Barcode1D ean, Barcode1D supp) {
/*  72 */     super(ean.document);
/*     */     
/*  74 */     this.n = 8.0F;
/*  75 */     this.ean = ean;
/*  76 */     this.supp = supp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBarcodeSize() {
/*  86 */     Rectangle rect = this.ean.getBarcodeSize();
/*  87 */     rect.setWidth(rect.getWidth() + this.supp.getBarcodeSize().getWidth() + this.n);
/*  88 */     return rect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
/* 132 */     if (this.supp.getFont() != null) {
/* 133 */       float sizeCoef = this.supp.getSize() / 1000.0F;
/* 134 */       this.supp.setBarHeight(this.ean.getBarHeight() + this.supp.getBaseline() - this.supp
/* 135 */           .getFont().getFontProgram().getFontMetrics().getCapHeight() * sizeCoef);
/*     */     } else {
/* 137 */       this.supp.setBarHeight(this.ean.getBarHeight());
/*     */     } 
/* 139 */     Rectangle eanR = this.ean.getBarcodeSize();
/* 140 */     canvas.saveState();
/* 141 */     this.ean.placeBarcode(canvas, barColor, textColor);
/* 142 */     canvas.restoreState();
/* 143 */     canvas.saveState();
/* 144 */     canvas.concatMatrix(1.0D, 0.0D, 0.0D, 1.0D, (eanR.getWidth() + this.n), (eanR.getHeight() - this.ean.getBarHeight()));
/* 145 */     this.supp.placeBarcode(canvas, barColor, textColor);
/* 146 */     canvas.restoreState();
/* 147 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image createAwtImage(Color foreground, Color background) {
/* 162 */     throw new UnsupportedOperationException("The two barcodes must be composed externally.");
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodeEANSUPP.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */