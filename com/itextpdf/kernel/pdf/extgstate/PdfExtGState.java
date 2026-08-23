/*     */ package com.itextpdf.kernel.pdf.extgstate;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
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
/*     */ public class PdfExtGState
/*     */   extends PdfObjectWrapper<PdfDictionary>
/*     */ {
/*     */   private static final long serialVersionUID = 5205219918362853395L;
/*  68 */   public static PdfName BM_NORMAL = PdfName.Normal;
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static PdfName BM_MULTIPLY = PdfName.Multiply;
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static PdfName BM_SCREEN = PdfName.Screen;
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static PdfName BM_OVERLAY = PdfName.Overlay;
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static PdfName BM_DARKEN = PdfName.Darken;
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static PdfName BM_LIGHTEN = PdfName.Lighten;
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static PdfName BM_COLOR_DODGE = PdfName.ColorDodge;
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static PdfName BM_COLOR_BURN = PdfName.ColorBurn;
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static PdfName BM_HARD_LIGHT = PdfName.HardLight;
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static PdfName BM_SOFT_LIGHT = PdfName.SoftLight;
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static PdfName BM_DIFFERENCE = PdfName.Difference;
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static PdfName BM_EXCLUSION = PdfName.Exclusion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static PdfName BM_HUE = PdfName.Hue;
/*     */ 
/*     */ 
/*     */   
/* 121 */   public static PdfName BM_SATURATION = PdfName.Saturation;
/*     */ 
/*     */ 
/*     */   
/* 125 */   public static PdfName BM_COLOR = PdfName.Color;
/*     */ 
/*     */ 
/*     */   
/* 129 */   public static PdfName BM_LUMINOSITY = PdfName.Luminosity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState(PdfDictionary pdfObject) {
/* 138 */     super((PdfObject)pdfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState() {
/* 145 */     this(new PdfDictionary());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getLineWidth() {
/* 154 */     return ((PdfDictionary)getPdfObject()).getAsFloat(PdfName.LW);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setLineWidth(float lineWidth) {
/* 164 */     return put(PdfName.LW, (PdfObject)new PdfNumber(lineWidth));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getLineCapStyle() {
/* 173 */     return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.LC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setLineCapStyle(int lineCapStyle) {
/* 183 */     return put(PdfName.LC, (PdfObject)new PdfNumber(lineCapStyle));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getLineJoinStyle() {
/* 192 */     return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.LJ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setLineJoinStyle(int lineJoinStyle) {
/* 202 */     return put(PdfName.LJ, (PdfObject)new PdfNumber(lineJoinStyle));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getMiterLimit() {
/* 211 */     return ((PdfDictionary)getPdfObject()).getAsFloat(PdfName.ML);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setMiterLimit(float miterLimit) {
/* 221 */     return put(PdfName.ML, (PdfObject)new PdfNumber(miterLimit));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getDashPattern() {
/* 230 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setDashPattern(PdfArray dashPattern) {
/* 240 */     return put(PdfName.D, (PdfObject)dashPattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getRenderingIntent() {
/* 251 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.RI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setRenderingIntent(PdfName renderingIntent) {
/* 262 */     return put(PdfName.RI, (PdfObject)renderingIntent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getStrokeOverprintFlag() {
/* 271 */     return ((PdfDictionary)getPdfObject()).getAsBool(PdfName.OP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setStrokeOverPrintFlag(boolean strokeOverPrintFlag) {
/* 281 */     return put(PdfName.OP, (PdfObject)PdfBoolean.valueOf(strokeOverPrintFlag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getFillOverprintFlag() {
/* 290 */     return ((PdfDictionary)getPdfObject()).getAsBool(PdfName.op);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setFillOverPrintFlag(boolean fillOverprintFlag) {
/* 300 */     return put(PdfName.op, (PdfObject)PdfBoolean.valueOf(fillOverprintFlag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getOverprintMode() {
/* 309 */     return ((PdfDictionary)getPdfObject()).getAsInt(PdfName.OPM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setOverprintMode(int overprintMode) {
/* 319 */     return put(PdfName.OPM, (PdfObject)new PdfNumber(overprintMode));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray getFont() {
/* 330 */     return ((PdfDictionary)getPdfObject()).getAsArray(PdfName.Font);
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
/*     */   public PdfExtGState setFont(PdfArray font) {
/* 344 */     return put(PdfName.Font, (PdfObject)font);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getBlackGenerationFunction() {
/* 353 */     return ((PdfDictionary)getPdfObject()).get(PdfName.BG);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setBlackGenerationFunction(PdfObject blackGenerationFunction) {
/* 363 */     return put(PdfName.BG, blackGenerationFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getBlackGenerationFunction2() {
/* 372 */     return ((PdfDictionary)getPdfObject()).get(PdfName.BG2);
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
/*     */   public PdfExtGState setBlackGenerationFunction2(PdfObject blackGenerationFunction2) {
/* 385 */     return put(PdfName.BG2, blackGenerationFunction2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getUndercolorRemovalFunction() {
/* 394 */     return ((PdfDictionary)getPdfObject()).get(PdfName.UCR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setUndercolorRemovalFunction(PdfObject undercolorRemovalFunction) {
/* 404 */     return put(PdfName.UCR, undercolorRemovalFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getUndercolorRemovalFunction2() {
/* 413 */     return ((PdfDictionary)getPdfObject()).get(PdfName.UCR2);
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
/*     */   public PdfExtGState setUndercolorRemovalFunction2(PdfObject undercolorRemovalFunction2) {
/* 426 */     return put(PdfName.UCR2, undercolorRemovalFunction2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getTransferFunction() {
/* 436 */     return ((PdfDictionary)getPdfObject()).get(PdfName.TR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setTransferFunction(PdfObject transferFunction) {
/* 447 */     return put(PdfName.TR, transferFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getTransferFunction2() {
/* 457 */     return ((PdfDictionary)getPdfObject()).get(PdfName.TR2);
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
/*     */   public PdfExtGState setTransferFunction2(PdfObject transferFunction2) {
/* 470 */     return put(PdfName.TR2, transferFunction2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getHalftone() {
/* 480 */     return ((PdfDictionary)getPdfObject()).get(PdfName.HT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setHalftone(PdfObject halftone) {
/* 491 */     return put(PdfName.HT, halftone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getFlatnessTolerance() {
/* 500 */     return ((PdfDictionary)getPdfObject()).getAsFloat(PdfName.FL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setFlatnessTolerance(float flatnessTolerance) {
/* 510 */     return put(PdfName.FL, (PdfObject)new PdfNumber(flatnessTolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getSmothnessTolerance() {
/* 519 */     return ((PdfDictionary)getPdfObject()).getAsFloat(PdfName.SM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setSmoothnessTolerance(float smoothnessTolerance) {
/* 529 */     return put(PdfName.SM, (PdfObject)new PdfNumber(smoothnessTolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getAutomaticStrokeAdjustmentFlag() {
/* 538 */     return ((PdfDictionary)getPdfObject()).getAsBool(PdfName.SA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setAutomaticStrokeAdjustmentFlag(boolean strokeAdjustment) {
/* 548 */     return put(PdfName.SA, (PdfObject)PdfBoolean.valueOf(strokeAdjustment));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getBlendMode() {
/* 557 */     return ((PdfDictionary)getPdfObject()).get(PdfName.BM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setBlendMode(PdfObject blendMode) {
/* 567 */     return put(PdfName.BM, blendMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject getSoftMask() {
/* 576 */     return ((PdfDictionary)getPdfObject()).get(PdfName.SMask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setSoftMask(PdfObject sMask) {
/* 586 */     return put(PdfName.SMask, sMask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getStrokeOpacity() {
/* 596 */     return ((PdfDictionary)getPdfObject()).getAsFloat(PdfName.CA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setStrokeOpacity(float strokingAlphaConstant) {
/* 607 */     return put(PdfName.CA, (PdfObject)new PdfNumber(strokingAlphaConstant));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float getFillOpacity() {
/* 617 */     return ((PdfDictionary)getPdfObject()).getAsFloat(PdfName.ca);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setFillOpacity(float fillingAlphaConstant) {
/* 628 */     return put(PdfName.ca, (PdfObject)new PdfNumber(fillingAlphaConstant));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getAlphaSourceFlag() {
/* 638 */     return ((PdfDictionary)getPdfObject()).getAsBool(PdfName.AIS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setAlphaSourceFlag(boolean alphaSourceFlag) {
/* 649 */     return put(PdfName.AIS, (PdfObject)PdfBoolean.valueOf(alphaSourceFlag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean getTextKnockoutFlag() {
/* 659 */     return ((PdfDictionary)getPdfObject()).getAsBool(PdfName.TK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setTextKnockoutFlag(boolean textKnockoutFlag) {
/* 670 */     return put(PdfName.TK, (PdfObject)PdfBoolean.valueOf(textKnockoutFlag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfExtGState setUseBlackPointCompensation(boolean useBlackPointCompensation) {
/* 681 */     return put(PdfName.UseBlackPtComp, useBlackPointCompensation ? (PdfObject)PdfName.ON : (PdfObject)PdfName.OFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isBlackPointCompensationUsed() {
/* 691 */     PdfName useBlackPointCompensation = ((PdfDictionary)getPdfObject()).getAsName(PdfName.UseBlackPtComp);
/* 692 */     if (PdfName.ON.equals(useBlackPointCompensation))
/* 693 */       return Boolean.valueOf(true); 
/* 694 */     if (PdfName.OFF.equals(useBlackPointCompensation)) {
/* 695 */       return Boolean.valueOf(false);
/*     */     }
/* 697 */     return null;
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
/*     */   public PdfExtGState setHalftoneOrigin(float x, float y) {
/* 709 */     PdfArray hto = new PdfArray();
/* 710 */     hto.add((PdfObject)new PdfNumber(x));
/* 711 */     hto.add((PdfObject)new PdfNumber(y));
/* 712 */     return put(PdfName.HTO, (PdfObject)hto);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getHalftoneOrigin() {
/* 722 */     PdfArray hto = ((PdfDictionary)getPdfObject()).getAsArray(PdfName.HTO);
/* 723 */     if (hto != null && hto.size() == 2 && hto.get(0).isNumber() && hto.get(1).isNumber()) {
/* 724 */       return new float[] { hto.getAsNumber(0).floatValue(), hto.getAsNumber(1).floatValue() };
/*     */     }
/* 726 */     return null;
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
/*     */   public PdfExtGState put(PdfName key, PdfObject value) {
/* 739 */     ((PdfDictionary)getPdfObject()).put(key, value);
/* 740 */     setModified();
/* 741 */     return this;
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
/*     */   public void flush() {
/* 753 */     super.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 761 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/extgstate/PdfExtGState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */