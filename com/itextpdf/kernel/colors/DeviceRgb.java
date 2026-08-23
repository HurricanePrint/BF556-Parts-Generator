/*     */ package com.itextpdf.kernel.colors;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
/*     */ import java.awt.Color;
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
/*     */ public class DeviceRgb
/*     */   extends Color
/*     */ {
/*  59 */   public static final Color BLACK = new DeviceRgb(0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final Color WHITE = new DeviceRgb(255, 255, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final Color RED = new DeviceRgb(255, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final Color GREEN = new DeviceRgb(0, 255, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public static final Color BLUE = new DeviceRgb(0, 0, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 7172400358137528030L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceRgb(int r, int g, int b) {
/*  94 */     this(r / 255.0F, g / 255.0F, b / 255.0F);
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
/*     */   public DeviceRgb(float r, float g, float b) {
/* 108 */     super((PdfColorSpace)new PdfDeviceCs.Rgb(), new float[] { (r > 1.0F) ? 1.0F : ((r > 0.0F) ? r : 0.0F), (g > 1.0F) ? 1.0F : ((g > 0.0F) ? g : 0.0F), (b > 1.0F) ? 1.0F : ((b > 0.0F) ? b : 0.0F) });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (r > 1.0F || r < 0.0F || g > 1.0F || g < 0.0F || b > 1.0F || b < 0.0F) {
/* 114 */       Logger LOGGER = LoggerFactory.getLogger(DeviceRgb.class);
/* 115 */       LOGGER.warn("Some of colorant intensities are invalid: they are bigger than 1 or less than 0. We will force them to become 1 or 0 respectively.");
/*     */     } 
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
/*     */   public DeviceRgb(Color color) {
/* 128 */     this(color.getRed(), color.getGreen(), color.getBlue());
/* 129 */     if (color.getAlpha() != 255) {
/* 130 */       Logger LOGGER = LoggerFactory.getLogger(DeviceRgb.class);
/* 131 */       LOGGER.warn(MessageFormatUtil.format("Alpha channel {0} was ignored during color creation. Note that opacity can be achieved in some places by using 'setOpacity' method or 'TransparentColor' class", new Object[] { Integer.valueOf(color.getAlpha()) }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceRgb() {
/* 139 */     this(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceRgb makeLighter(DeviceRgb rgbColor) {
/* 149 */     float r = rgbColor.getColorValue()[0];
/* 150 */     float g = rgbColor.getColorValue()[1];
/* 151 */     float b = rgbColor.getColorValue()[2];
/*     */     
/* 153 */     float v = Math.max(r, Math.max(g, b));
/*     */     
/* 155 */     if (v == 0.0F) {
/* 156 */       return new DeviceRgb(84, 84, 84);
/*     */     }
/*     */     
/* 159 */     float multiplier = Math.min(1.0F, v + 0.33F) / v;
/*     */     
/* 161 */     r = multiplier * r;
/* 162 */     g = multiplier * g;
/* 163 */     b = multiplier * b;
/* 164 */     return new DeviceRgb(r, g, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceRgb makeDarker(DeviceRgb rgbColor) {
/* 174 */     float r = rgbColor.getColorValue()[0];
/* 175 */     float g = rgbColor.getColorValue()[1];
/* 176 */     float b = rgbColor.getColorValue()[2];
/*     */     
/* 178 */     float v = Math.max(r, Math.max(g, b));
/*     */     
/* 180 */     float multiplier = Math.max(0.0F, (v - 0.33F) / v);
/*     */     
/* 182 */     r = multiplier * r;
/* 183 */     g = multiplier * g;
/* 184 */     b = multiplier * b;
/* 185 */     return new DeviceRgb(r, g, b);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/DeviceRgb.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */