/*     */ package com.itextpdf.barcodes;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
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
/*     */ public abstract class Barcode1D
/*     */ {
/*     */   public static final int ALIGN_LEFT = 1;
/*     */   public static final int ALIGN_RIGHT = 2;
/*     */   public static final int ALIGN_CENTER = 3;
/*  64 */   protected final Color DEFAULT_BAR_FOREGROUND_COLOR = Color.BLACK;
/*     */ 
/*     */ 
/*     */   
/*  68 */   protected final Color DEFAULT_BAR_BACKGROUND_COLOR = Color.WHITE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfDocument document;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float n;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfFont font;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float baseline;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float barHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int textAlignment;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean generateChecksum;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checksumText;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean startStopText;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean extended;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   protected String code = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean guardBars;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int codeType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   protected float inkSpreading = 0.0F;
/*     */ 
/*     */   
/*     */   protected String altText;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Barcode1D(PdfDocument document) {
/* 157 */     this.document = document;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/* 166 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(float x) {
/* 175 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getN() {
/* 184 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setN(float n) {
/* 193 */     this.n = n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFont getFont() {
/* 202 */     return this.font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(PdfFont font) {
/* 211 */     this.font = font;
/*     */   }
/*     */   
/*     */   public float getSize() {
/* 215 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(float size) {
/* 224 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBaseline() {
/* 235 */     return this.baseline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseline(float baseline) {
/* 246 */     this.baseline = baseline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBarHeight() {
/* 255 */     return this.barHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBarHeight(float barHeight) {
/* 264 */     this.barHeight = barHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextAlignment() {
/* 273 */     return this.textAlignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextAlignment(int textAlignment) {
/* 282 */     this.textAlignment = textAlignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGenerateChecksum() {
/* 291 */     return this.generateChecksum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGenerateChecksum(boolean generateChecksum) {
/* 300 */     this.generateChecksum = generateChecksum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChecksumText() {
/* 309 */     return this.checksumText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChecksumText(boolean checksumText) {
/* 318 */     this.checksumText = checksumText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartStopText() {
/* 328 */     return this.startStopText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartStopText(boolean startStopText) {
/* 338 */     this.startStopText = startStopText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExtended() {
/* 347 */     return this.extended;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtended(boolean extended) {
/* 356 */     this.extended = extended;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCode() {
/* 365 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCode(String code) {
/* 374 */     this.code = code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGuardBars() {
/* 383 */     return this.guardBars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGuardBars(boolean guardBars) {
/* 392 */     this.guardBars = guardBars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCodeType() {
/* 401 */     return this.codeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCodeType(int codeType) {
/* 410 */     this.codeType = codeType;
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
/*     */   public abstract Rectangle getBarcodeSize();
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
/*     */   public abstract Rectangle placeBarcode(PdfCanvas paramPdfCanvas, Color paramColor1, Color paramColor2);
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
/*     */   public float getInkSpreading() {
/* 468 */     return this.inkSpreading;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInkSpreading(float inkSpreading) {
/* 479 */     this.inkSpreading = inkSpreading;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAltText() {
/* 488 */     return this.altText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAltText(String altText) {
/* 498 */     this.altText = altText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Image createAwtImage(Color paramColor1, Color paramColor2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfFormXObject createFormXObject(PdfDocument document) {
/* 518 */     return createFormXObject(null, null, document);
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
/*     */   public PdfFormXObject createFormXObject(Color barColor, Color textColor, PdfDocument document) {
/* 531 */     PdfFormXObject xObject = new PdfFormXObject((Rectangle)null);
/* 532 */     Rectangle rect = placeBarcode(new PdfCanvas(xObject, document), barColor, textColor);
/* 533 */     xObject.setBBox(new PdfArray(rect));
/*     */     
/* 535 */     return xObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fitWidth(float width) {
/* 544 */     setX(this.x * width / getBarcodeSize().getWidth());
/*     */   }
/*     */   
/*     */   protected float getDescender() {
/* 548 */     float sizeCoef = this.size / 1000.0F;
/* 549 */     return this.font.getFontProgram().getFontMetrics().getTypoDescender() * sizeCoef;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/Barcode1D.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */