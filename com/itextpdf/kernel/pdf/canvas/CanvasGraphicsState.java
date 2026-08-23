/*     */ package com.itextpdf.kernel.pdf.canvas;
/*     */ 
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.colors.DeviceGray;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class CanvasGraphicsState
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9151840268986283292L;
/*  74 */   private Matrix ctm = new Matrix();
/*     */ 
/*     */   
/*  77 */   private Color strokeColor = (Color)DeviceGray.BLACK;
/*  78 */   private Color fillColor = (Color)DeviceGray.BLACK;
/*     */ 
/*     */   
/*  81 */   private float charSpacing = 0.0F;
/*  82 */   private float wordSpacing = 0.0F;
/*     */   
/*  84 */   private float scale = 100.0F;
/*  85 */   private float leading = 0.0F;
/*     */   private PdfFont font;
/*     */   private float fontSize;
/*  88 */   private int textRenderingMode = 0;
/*  89 */   private float textRise = 0.0F;
/*     */   
/*     */   private boolean textKnockout = true;
/*  92 */   private float lineWidth = 1.0F;
/*  93 */   private int lineCapStyle = 0;
/*  94 */   private int lineJoinStyle = 0;
/*  95 */   private float miterLimit = 10.0F;
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
/* 106 */   private PdfArray dashPattern = new PdfArray(Arrays.asList(new PdfObject[] { (PdfObject)new PdfArray(), (PdfObject)new PdfNumber(0) }));
/*     */   
/* 108 */   private PdfName renderingIntent = PdfName.RelativeColorimetric;
/*     */   private boolean automaticStrokeAdjustment = false;
/* 110 */   private PdfObject blendMode = (PdfObject)PdfName.Normal;
/* 111 */   private PdfObject softMask = (PdfObject)PdfName.None;
/*     */ 
/*     */   
/* 114 */   private float strokeAlpha = 1.0F;
/* 115 */   private float fillAlpha = 1.0F;
/*     */   
/*     */   private boolean alphaIsShape = false;
/*     */   
/*     */   private boolean strokeOverprint = false;
/*     */   private boolean fillOverprint = false;
/* 121 */   private int overprintMode = 0;
/*     */   private PdfObject blackGenerationFunction;
/*     */   private PdfObject blackGenerationFunction2;
/*     */   private PdfObject underColorRemovalFunction;
/*     */   private PdfObject underColorRemovalFunction2;
/*     */   private PdfObject transferFunction;
/*     */   private PdfObject transferFunction2;
/*     */   private PdfObject halftone;
/* 129 */   private float flatnessTolerance = 1.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   private Float smoothnessTolerance;
/*     */ 
/*     */ 
/*     */   
/*     */   private PdfObject htp;
/*     */ 
/*     */ 
/*     */   
/*     */   protected CanvasGraphicsState() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasGraphicsState(CanvasGraphicsState source) {
/* 146 */     copyFrom(source);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFromExtGState(PdfDictionary extGState) {
/* 155 */     updateFromExtGState(new PdfExtGState(extGState), (extGState.getIndirectReference() == null) ? null : extGState.getIndirectReference().getDocument());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix getCtm() {
/* 162 */     return this.ctm;
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
/*     */   public void updateCtm(float a, float b, float c, float d, float e, float f) {
/* 177 */     updateCtm(new Matrix(a, b, c, d, e, f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCtm(Matrix newCtm) {
/* 186 */     this.ctm = newCtm.multiply(this.ctm);
/*     */   }
/*     */   
/*     */   public Color getFillColor() {
/* 190 */     return this.fillColor;
/*     */   }
/*     */   
/*     */   public void setFillColor(Color fillColor) {
/* 194 */     this.fillColor = fillColor;
/*     */   }
/*     */   
/*     */   public Color getStrokeColor() {
/* 198 */     return this.strokeColor;
/*     */   }
/*     */   
/*     */   public void setStrokeColor(Color strokeColor) {
/* 202 */     this.strokeColor = strokeColor;
/*     */   }
/*     */   
/*     */   public float getLineWidth() {
/* 206 */     return this.lineWidth;
/*     */   }
/*     */   
/*     */   public void setLineWidth(float lineWidth) {
/* 210 */     this.lineWidth = lineWidth;
/*     */   }
/*     */   
/*     */   public int getLineCapStyle() {
/* 214 */     return this.lineCapStyle;
/*     */   }
/*     */   
/*     */   public void setLineCapStyle(int lineCapStyle) {
/* 218 */     this.lineCapStyle = lineCapStyle;
/*     */   }
/*     */   
/*     */   public int getLineJoinStyle() {
/* 222 */     return this.lineJoinStyle;
/*     */   }
/*     */   
/*     */   public void setLineJoinStyle(int lineJoinStyle) {
/* 226 */     this.lineJoinStyle = lineJoinStyle;
/*     */   }
/*     */   
/*     */   public float getMiterLimit() {
/* 230 */     return this.miterLimit;
/*     */   }
/*     */   
/*     */   public void setMiterLimit(float miterLimit) {
/* 234 */     this.miterLimit = miterLimit;
/*     */   }
/*     */   
/*     */   public PdfArray getDashPattern() {
/* 238 */     return this.dashPattern;
/*     */   }
/*     */   
/*     */   public void setDashPattern(PdfArray dashPattern) {
/* 242 */     this.dashPattern = dashPattern;
/*     */   }
/*     */   
/*     */   public PdfName getRenderingIntent() {
/* 246 */     return this.renderingIntent;
/*     */   }
/*     */   
/*     */   public void setRenderingIntent(PdfName renderingIntent) {
/* 250 */     this.renderingIntent = renderingIntent;
/*     */   }
/*     */   
/*     */   public float getFontSize() {
/* 254 */     return this.fontSize;
/*     */   }
/*     */   
/*     */   public void setFontSize(float fontSize) {
/* 258 */     this.fontSize = fontSize;
/*     */   }
/*     */   
/*     */   public PdfFont getFont() {
/* 262 */     return this.font;
/*     */   }
/*     */   
/*     */   public void setFont(PdfFont font) {
/* 266 */     this.font = font;
/*     */   }
/*     */   
/*     */   public int getTextRenderingMode() {
/* 270 */     return this.textRenderingMode;
/*     */   }
/*     */   
/*     */   public void setTextRenderingMode(int textRenderingMode) {
/* 274 */     this.textRenderingMode = textRenderingMode;
/*     */   }
/*     */   
/*     */   public float getTextRise() {
/* 278 */     return this.textRise;
/*     */   }
/*     */   
/*     */   public void setTextRise(float textRise) {
/* 282 */     this.textRise = textRise;
/*     */   }
/*     */   
/*     */   public float getFlatnessTolerance() {
/* 286 */     return this.flatnessTolerance;
/*     */   }
/*     */   
/*     */   public void setFlatnessTolerance(float flatnessTolerance) {
/* 290 */     this.flatnessTolerance = flatnessTolerance;
/*     */   }
/*     */   
/*     */   public void setWordSpacing(float wordSpacing) {
/* 294 */     this.wordSpacing = wordSpacing;
/*     */   }
/*     */   
/*     */   public float getWordSpacing() {
/* 298 */     return this.wordSpacing;
/*     */   }
/*     */   
/*     */   public void setCharSpacing(float characterSpacing) {
/* 302 */     this.charSpacing = characterSpacing;
/*     */   }
/*     */   
/*     */   public float getCharSpacing() {
/* 306 */     return this.charSpacing;
/*     */   }
/*     */   
/*     */   public float getLeading() {
/* 310 */     return this.leading;
/*     */   }
/*     */   
/*     */   public void setLeading(float leading) {
/* 314 */     this.leading = leading;
/*     */   }
/*     */   
/*     */   public float getHorizontalScaling() {
/* 318 */     return this.scale;
/*     */   }
/*     */   
/*     */   public void setHorizontalScaling(float scale) {
/* 322 */     this.scale = scale;
/*     */   }
/*     */   
/*     */   public boolean getStrokeOverprint() {
/* 326 */     return this.strokeOverprint;
/*     */   }
/*     */   
/*     */   public boolean getFillOverprint() {
/* 330 */     return this.fillOverprint;
/*     */   }
/*     */   
/*     */   public int getOverprintMode() {
/* 334 */     return this.overprintMode;
/*     */   }
/*     */   
/*     */   public PdfObject getBlackGenerationFunction() {
/* 338 */     return this.blackGenerationFunction;
/*     */   }
/*     */   
/*     */   public PdfObject getBlackGenerationFunction2() {
/* 342 */     return this.blackGenerationFunction2;
/*     */   }
/*     */   
/*     */   public PdfObject getUnderColorRemovalFunction() {
/* 346 */     return this.underColorRemovalFunction;
/*     */   }
/*     */   
/*     */   public PdfObject getUnderColorRemovalFunction2() {
/* 350 */     return this.underColorRemovalFunction2;
/*     */   }
/*     */   
/*     */   public PdfObject getTransferFunction() {
/* 354 */     return this.transferFunction;
/*     */   }
/*     */   
/*     */   public PdfObject getTransferFunction2() {
/* 358 */     return this.transferFunction2;
/*     */   }
/*     */   
/*     */   public PdfObject getHalftone() {
/* 362 */     return this.halftone;
/*     */   }
/*     */   
/*     */   public Float getSmoothnessTolerance() {
/* 366 */     return this.smoothnessTolerance;
/*     */   }
/*     */   
/*     */   public boolean getAutomaticStrokeAdjustment() {
/* 370 */     return this.automaticStrokeAdjustment;
/*     */   }
/*     */   
/*     */   public PdfObject getBlendMode() {
/* 374 */     return this.blendMode;
/*     */   }
/*     */   
/*     */   public PdfObject getSoftMask() {
/* 378 */     return this.softMask;
/*     */   }
/*     */   
/*     */   public float getStrokeOpacity() {
/* 382 */     return this.strokeAlpha;
/*     */   }
/*     */   
/*     */   public float getFillOpacity() {
/* 386 */     return this.fillAlpha;
/*     */   }
/*     */   
/*     */   public boolean getAlphaIsShape() {
/* 390 */     return this.alphaIsShape;
/*     */   }
/*     */   
/*     */   public boolean getTextKnockout() {
/* 394 */     return this.textKnockout;
/*     */   }
/*     */   
/*     */   public PdfObject getHTP() {
/* 398 */     return this.htp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFromExtGState(PdfExtGState extGState) {
/* 407 */     updateFromExtGState(extGState, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateFromExtGState(PdfExtGState extGState, PdfDocument pdfDocument) {
/* 417 */     Float lw = extGState.getLineWidth();
/* 418 */     if (lw != null)
/* 419 */       this.lineWidth = lw.floatValue(); 
/* 420 */     Integer lc = extGState.getLineCapStyle();
/* 421 */     if (lc != null)
/* 422 */       this.lineCapStyle = lc.intValue(); 
/* 423 */     Integer lj = extGState.getLineJoinStyle();
/* 424 */     if (lj != null)
/* 425 */       this.lineJoinStyle = lj.intValue(); 
/* 426 */     Float ml = extGState.getMiterLimit();
/* 427 */     if (ml != null)
/* 428 */       this.miterLimit = ml.floatValue(); 
/* 429 */     PdfArray d = extGState.getDashPattern();
/* 430 */     if (d != null)
/* 431 */       this.dashPattern = d; 
/* 432 */     PdfName ri = extGState.getRenderingIntent();
/* 433 */     if (ri != null)
/* 434 */       this.renderingIntent = ri; 
/* 435 */     Boolean op = extGState.getStrokeOverprintFlag();
/* 436 */     if (op != null)
/* 437 */       this.strokeOverprint = op.booleanValue(); 
/* 438 */     op = extGState.getFillOverprintFlag();
/* 439 */     if (op != null)
/* 440 */       this.fillOverprint = op.booleanValue(); 
/* 441 */     Integer opm = extGState.getOverprintMode();
/* 442 */     if (opm != null)
/* 443 */       this.overprintMode = opm.intValue(); 
/* 444 */     PdfArray fnt = extGState.getFont();
/* 445 */     if (fnt != null) {
/* 446 */       PdfDictionary fontDictionary = fnt.getAsDictionary(0);
/* 447 */       if (this.font == null || this.font.getPdfObject() != fontDictionary) {
/* 448 */         this.font = pdfDocument.getFont(fontDictionary);
/*     */       }
/* 450 */       PdfNumber fntSz = fnt.getAsNumber(1);
/* 451 */       if (fntSz != null)
/* 452 */         this.fontSize = fntSz.floatValue(); 
/*     */     } 
/* 454 */     PdfObject bg = extGState.getBlackGenerationFunction();
/* 455 */     if (bg != null)
/* 456 */       this.blackGenerationFunction = bg; 
/* 457 */     PdfObject bg2 = extGState.getBlackGenerationFunction2();
/* 458 */     if (bg2 != null)
/* 459 */       this.blackGenerationFunction2 = bg2; 
/* 460 */     PdfObject ucr = extGState.getUndercolorRemovalFunction();
/* 461 */     if (ucr != null)
/* 462 */       this.underColorRemovalFunction = ucr; 
/* 463 */     PdfObject ucr2 = extGState.getUndercolorRemovalFunction2();
/* 464 */     if (ucr2 != null)
/* 465 */       this.underColorRemovalFunction2 = ucr2; 
/* 466 */     PdfObject tr = extGState.getTransferFunction();
/* 467 */     if (tr != null)
/* 468 */       this.transferFunction = tr; 
/* 469 */     PdfObject tr2 = extGState.getTransferFunction2();
/* 470 */     if (tr2 != null)
/* 471 */       this.transferFunction2 = tr2; 
/* 472 */     PdfObject ht = extGState.getHalftone();
/* 473 */     if (ht != null)
/* 474 */       this.halftone = ht; 
/* 475 */     PdfObject local_htp = ((PdfDictionary)extGState.getPdfObject()).get(PdfName.HTP);
/* 476 */     if (local_htp != null)
/* 477 */       this.htp = local_htp; 
/* 478 */     Float fl = extGState.getFlatnessTolerance();
/* 479 */     if (fl != null)
/* 480 */       this.flatnessTolerance = fl.floatValue(); 
/* 481 */     Float sm = extGState.getSmothnessTolerance();
/* 482 */     if (sm != null)
/* 483 */       this.smoothnessTolerance = sm; 
/* 484 */     Boolean sa = extGState.getAutomaticStrokeAdjustmentFlag();
/* 485 */     if (sa != null)
/* 486 */       this.automaticStrokeAdjustment = sa.booleanValue(); 
/* 487 */     PdfObject bm = extGState.getBlendMode();
/* 488 */     if (bm != null)
/* 489 */       this.blendMode = bm; 
/* 490 */     PdfObject sMask = extGState.getSoftMask();
/* 491 */     if (sMask != null)
/* 492 */       this.softMask = sMask; 
/* 493 */     Float ca = extGState.getStrokeOpacity();
/* 494 */     if (ca != null)
/* 495 */       this.strokeAlpha = ca.floatValue(); 
/* 496 */     ca = extGState.getFillOpacity();
/* 497 */     if (ca != null)
/* 498 */       this.fillAlpha = ca.floatValue(); 
/* 499 */     Boolean ais = extGState.getAlphaSourceFlag();
/* 500 */     if (ais != null)
/* 501 */       this.alphaIsShape = ais.booleanValue(); 
/* 502 */     Boolean tk = extGState.getTextKnockoutFlag();
/* 503 */     if (tk != null)
/* 504 */       this.textKnockout = tk.booleanValue(); 
/*     */   }
/*     */   
/*     */   private void copyFrom(CanvasGraphicsState source) {
/* 508 */     this.ctm = source.ctm;
/* 509 */     this.strokeColor = source.strokeColor;
/* 510 */     this.fillColor = source.fillColor;
/* 511 */     this.charSpacing = source.charSpacing;
/* 512 */     this.wordSpacing = source.wordSpacing;
/* 513 */     this.scale = source.scale;
/* 514 */     this.leading = source.leading;
/* 515 */     this.font = source.font;
/* 516 */     this.fontSize = source.fontSize;
/* 517 */     this.textRenderingMode = source.textRenderingMode;
/* 518 */     this.textRise = source.textRise;
/* 519 */     this.textKnockout = source.textKnockout;
/* 520 */     this.lineWidth = source.lineWidth;
/* 521 */     this.lineCapStyle = source.lineCapStyle;
/* 522 */     this.lineJoinStyle = source.lineJoinStyle;
/* 523 */     this.miterLimit = source.miterLimit;
/* 524 */     this.dashPattern = source.dashPattern;
/* 525 */     this.renderingIntent = source.renderingIntent;
/* 526 */     this.automaticStrokeAdjustment = source.automaticStrokeAdjustment;
/* 527 */     this.blendMode = source.blendMode;
/* 528 */     this.softMask = source.softMask;
/* 529 */     this.strokeAlpha = source.strokeAlpha;
/* 530 */     this.fillAlpha = source.fillAlpha;
/* 531 */     this.alphaIsShape = source.alphaIsShape;
/* 532 */     this.strokeOverprint = source.strokeOverprint;
/* 533 */     this.fillOverprint = source.fillOverprint;
/* 534 */     this.overprintMode = source.overprintMode;
/* 535 */     this.blackGenerationFunction = source.blackGenerationFunction;
/* 536 */     this.blackGenerationFunction2 = source.blackGenerationFunction2;
/* 537 */     this.underColorRemovalFunction = source.underColorRemovalFunction;
/* 538 */     this.underColorRemovalFunction2 = source.underColorRemovalFunction2;
/* 539 */     this.transferFunction = source.transferFunction;
/* 540 */     this.transferFunction2 = source.transferFunction2;
/* 541 */     this.halftone = source.halftone;
/* 542 */     this.flatnessTolerance = source.flatnessTolerance;
/* 543 */     this.smoothnessTolerance = source.smoothnessTolerance;
/* 544 */     this.htp = source.htp;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/CanvasGraphicsState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */