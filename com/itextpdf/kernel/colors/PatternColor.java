/*     */ package com.itextpdf.kernel.colors;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
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
/*     */ public class PatternColor
/*     */   extends Color
/*     */ {
/*     */   private static final long serialVersionUID = -2405470180325720440L;
/*     */   private PdfPattern pattern;
/*     */   private Color underlyingColor;
/*     */   
/*     */   public PatternColor(PdfPattern coloredPattern) {
/*  58 */     super((PdfColorSpace)new PdfSpecialCs.Pattern(), null);
/*  59 */     this.pattern = coloredPattern;
/*     */   }
/*     */   
/*     */   public PatternColor(PdfPattern.Tiling uncoloredPattern, Color color) {
/*  63 */     this(uncoloredPattern, color.getColorSpace(), color.getColorValue());
/*     */   }
/*     */   
/*     */   public PatternColor(PdfPattern.Tiling uncoloredPattern, PdfColorSpace underlyingCS, float[] colorValue) {
/*  67 */     this(uncoloredPattern, new PdfSpecialCs.UncoloredTilingPattern(ensureNotPatternCs(underlyingCS)), colorValue);
/*     */   }
/*     */   
/*     */   public PatternColor(PdfPattern.Tiling uncoloredPattern, PdfSpecialCs.UncoloredTilingPattern uncoloredTilingCS, float[] colorValue) {
/*  71 */     super((PdfColorSpace)uncoloredTilingCS, colorValue);
/*  72 */     this.pattern = (PdfPattern)uncoloredPattern;
/*  73 */     this.underlyingColor = makeColor(uncoloredTilingCS.getUnderlyingColorSpace(), colorValue);
/*     */   }
/*     */   
/*     */   public PdfPattern getPattern() {
/*  77 */     return this.pattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColorValue(float[] value) {
/*  82 */     super.setColorValue(value);
/*  83 */     this.underlyingColor.setColorValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setPattern(PdfPattern pattern) {
/*  94 */     this.pattern = pattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  99 */     if (!super.equals(o)) {
/* 100 */       return false;
/*     */     }
/* 102 */     PatternColor color = (PatternColor)o;
/* 103 */     return (this.pattern.equals(color.pattern) && ((this.underlyingColor != null) ? this.underlyingColor
/* 104 */       .equals(color.underlyingColor) : (color.underlyingColor == null)));
/*     */   }
/*     */   
/*     */   private static PdfColorSpace ensureNotPatternCs(PdfColorSpace underlyingCS) {
/* 108 */     if (underlyingCS instanceof PdfSpecialCs.Pattern)
/* 109 */       throw new IllegalArgumentException("underlyingCS"); 
/* 110 */     return underlyingCS;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/PatternColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */