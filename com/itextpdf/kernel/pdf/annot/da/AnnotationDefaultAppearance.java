/*     */ package com.itextpdf.kernel.pdf.annot.da;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.colors.DeviceCmyk;
/*     */ import com.itextpdf.kernel.colors.DeviceGray;
/*     */ import com.itextpdf.kernel.colors.DeviceRgb;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationDefaultAppearance
/*     */ {
/*  60 */   private static final Map<StandardAnnotationFont, String> stdAnnotFontNames = new HashMap<>();
/*  61 */   private static final Map<ExtendedAnnotationFont, String> extAnnotFontNames = new HashMap<>();
/*     */   static {
/*  63 */     stdAnnotFontNames.put(StandardAnnotationFont.CourierBoldOblique, "/Courier-BoldOblique");
/*  64 */     stdAnnotFontNames.put(StandardAnnotationFont.CourierBold, "/Courier-Bold");
/*  65 */     stdAnnotFontNames.put(StandardAnnotationFont.CourierOblique, "/Courier-Oblique");
/*  66 */     stdAnnotFontNames.put(StandardAnnotationFont.Courier, "/Courier");
/*  67 */     stdAnnotFontNames.put(StandardAnnotationFont.HelveticaBoldOblique, "/Helvetica-BoldOblique");
/*  68 */     stdAnnotFontNames.put(StandardAnnotationFont.HelveticaBold, "/Helvetica-Bold");
/*  69 */     stdAnnotFontNames.put(StandardAnnotationFont.HelveticaOblique, "/Courier-Oblique");
/*  70 */     stdAnnotFontNames.put(StandardAnnotationFont.Helvetica, "/Helvetica");
/*  71 */     stdAnnotFontNames.put(StandardAnnotationFont.Symbol, "/Symbol");
/*  72 */     stdAnnotFontNames.put(StandardAnnotationFont.TimesBoldItalic, "/Times-BoldItalic");
/*  73 */     stdAnnotFontNames.put(StandardAnnotationFont.TimesBold, "/Times-Bold");
/*  74 */     stdAnnotFontNames.put(StandardAnnotationFont.TimesItalic, "/Times-Italic");
/*  75 */     stdAnnotFontNames.put(StandardAnnotationFont.TimesRoman, "/Times-Roman");
/*  76 */     stdAnnotFontNames.put(StandardAnnotationFont.ZapfDingbats, "/ZapfDingbats");
/*     */     
/*  78 */     extAnnotFontNames.put(ExtendedAnnotationFont.HYSMyeongJoMedium, "/HySm");
/*  79 */     extAnnotFontNames.put(ExtendedAnnotationFont.HYGoThicMedium, "/HyGo");
/*  80 */     extAnnotFontNames.put(ExtendedAnnotationFont.HeiseiKakuGoW5, "/KaGo");
/*  81 */     extAnnotFontNames.put(ExtendedAnnotationFont.HeiseiMinW3, "/KaMi");
/*  82 */     extAnnotFontNames.put(ExtendedAnnotationFont.MHeiMedium, "/MHei");
/*  83 */     extAnnotFontNames.put(ExtendedAnnotationFont.MSungLight, "/MSun");
/*  84 */     extAnnotFontNames.put(ExtendedAnnotationFont.STSongLight, "/STSo");
/*  85 */     extAnnotFontNames.put(ExtendedAnnotationFont.MSungStdLight, "/MSun");
/*  86 */     extAnnotFontNames.put(ExtendedAnnotationFont.STSongStdLight, "/STSo");
/*  87 */     extAnnotFontNames.put(ExtendedAnnotationFont.HYSMyeongJoStdMedium, "/HySm");
/*  88 */     extAnnotFontNames.put(ExtendedAnnotationFont.KozMinProRegular, "/KaMi");
/*     */   }
/*     */   
/*  91 */   private String colorOperand = "0 g";
/*  92 */   private String rawFontName = "/Helv";
/*  93 */   private float fontSize = 0.0F;
/*     */   
/*     */   public AnnotationDefaultAppearance() {
/*  96 */     setFont(StandardAnnotationFont.Helvetica);
/*  97 */     setFontSize(12.0F);
/*     */   }
/*     */   
/*     */   public AnnotationDefaultAppearance setFont(StandardAnnotationFont font) {
/* 101 */     setRawFontName(stdAnnotFontNames.get(font));
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public AnnotationDefaultAppearance setFont(ExtendedAnnotationFont font) {
/* 106 */     setRawFontName(extAnnotFontNames.get(font));
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public AnnotationDefaultAppearance setFontSize(float fontSize) {
/* 111 */     this.fontSize = fontSize;
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public AnnotationDefaultAppearance setColor(DeviceRgb rgbColor) {
/* 116 */     setColorOperand(rgbColor.getColorValue(), "rg");
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public AnnotationDefaultAppearance setColor(DeviceCmyk cmykColor) {
/* 121 */     setColorOperand(cmykColor.getColorValue(), "k");
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public AnnotationDefaultAppearance setColor(DeviceGray grayColor) {
/* 126 */     setColorOperand(grayColor.getColorValue(), "g");
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public PdfString toPdfString() {
/* 131 */     return new PdfString(MessageFormatUtil.format("{0} {1} Tf {2}", new Object[] { this.rawFontName, Float.valueOf(this.fontSize), this.colorOperand }));
/*     */   }
/*     */   
/*     */   private void setColorOperand(float[] colorValues, String operand) {
/* 135 */     StringBuilder builder = new StringBuilder();
/* 136 */     for (float value : colorValues) {
/* 137 */       builder.append(MessageFormatUtil.format("{0} ", new Object[] { Float.valueOf(value) }));
/*     */     } 
/* 139 */     builder.append(operand);
/* 140 */     this.colorOperand = builder.toString();
/*     */   }
/*     */   
/*     */   private void setRawFontName(String rawFontName) {
/* 144 */     if (rawFontName == null) {
/* 145 */       throw new IllegalArgumentException("Passed raw font name can not be null");
/*     */     }
/* 147 */     this.rawFontName = rawFontName;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/da/AnnotationDefaultAppearance.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */