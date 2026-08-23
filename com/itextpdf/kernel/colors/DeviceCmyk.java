/*     */ package com.itextpdf.kernel.colors;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceCmyk
/*     */   extends Color
/*     */ {
/*  59 */   public static final DeviceCmyk CYAN = new DeviceCmyk(100, 0, 0, 0);
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final DeviceCmyk MAGENTA = new DeviceCmyk(0, 100, 0, 0);
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final DeviceCmyk YELLOW = new DeviceCmyk(0, 0, 100, 0);
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final DeviceCmyk BLACK = new DeviceCmyk(0, 0, 0, 100);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 5466518014595706050L;
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceCmyk() {
/*  79 */     this(0.0F, 0.0F, 0.0F, 1.0F);
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
/*     */   public DeviceCmyk(int c, int m, int y, int k) {
/*  94 */     this(c / 100.0F, m / 100.0F, y / 100.0F, k / 100.0F);
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
/*     */   public DeviceCmyk(float c, float m, float y, float k) {
/* 109 */     super((PdfColorSpace)new PdfDeviceCs.Cmyk(), new float[] { (c > 1.0F) ? 1.0F : ((c > 0.0F) ? c : 0.0F), (m > 1.0F) ? 1.0F : ((m > 0.0F) ? m : 0.0F), (y > 1.0F) ? 1.0F : ((y > 0.0F) ? y : 0.0F), (k > 1.0F) ? 1.0F : ((k > 0.0F) ? k : 0.0F) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (c > 1.0F || c < 0.0F || m > 1.0F || m < 0.0F || y > 1.0F || y < 0.0F || k > 1.0F || k < 0.0F) {
/* 116 */       Logger LOGGER = LoggerFactory.getLogger(DeviceCmyk.class);
/* 117 */       LOGGER.warn("Some of colorant intensities are invalid: they are bigger than 1 or less than 0. We will force them to become 1 or 0 respectively.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceCmyk makeLighter(DeviceCmyk cmykColor) {
/* 128 */     DeviceRgb rgbEquivalent = convertCmykToRgb(cmykColor);
/* 129 */     DeviceRgb lighterRgb = DeviceRgb.makeLighter(rgbEquivalent);
/* 130 */     return convertRgbToCmyk(lighterRgb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceCmyk makeDarker(DeviceCmyk cmykColor) {
/* 140 */     DeviceRgb rgbEquivalent = convertCmykToRgb(cmykColor);
/* 141 */     DeviceRgb darkerRgb = DeviceRgb.makeDarker(rgbEquivalent);
/* 142 */     return convertRgbToCmyk(darkerRgb);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/DeviceCmyk.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */