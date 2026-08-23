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
/*     */ public class DeviceGray
/*     */   extends Color
/*     */ {
/*  59 */   public static final DeviceGray WHITE = new DeviceGray(1.0F);
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final DeviceGray GRAY = new DeviceGray(0.5F);
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final DeviceGray BLACK = new DeviceGray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 8307729543359242834L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceGray(float value) {
/*  80 */     super((PdfColorSpace)new PdfDeviceCs.Gray(), new float[] { (value > 1.0F) ? 1.0F : ((value > 0.0F) ? value : 0.0F) });
/*  81 */     if (value > 1.0F || value < 0.0F) {
/*  82 */       Logger LOGGER = LoggerFactory.getLogger(DeviceGray.class);
/*  83 */       LOGGER.warn("Some of colorant intensities are invalid: they are bigger than 1 or less than 0. We will force them to become 1 or 0 respectively.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceGray() {
/*  91 */     this(0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceGray makeLighter(DeviceGray grayColor) {
/* 101 */     float v = grayColor.getColorValue()[0];
/*     */     
/* 103 */     if (v == 0.0F) {
/* 104 */       return new DeviceGray(0.3F);
/*     */     }
/* 106 */     float multiplier = Math.min(1.0F, v + 0.33F) / v;
/*     */     
/* 108 */     return new DeviceGray(v * multiplier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceGray makeDarker(DeviceGray grayColor) {
/* 118 */     float v = grayColor.getColorValue()[0];
/* 119 */     float multiplier = Math.max(0.0F, (v - 0.33F) / v);
/*     */     
/* 121 */     return new DeviceGray(v * multiplier);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/DeviceGray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */