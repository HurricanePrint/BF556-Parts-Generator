/*     */ package com.itextpdf.kernel.colors;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
/*     */ import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Color
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6639782922289701126L;
/*     */   protected PdfColorSpace colorSpace;
/*     */   protected float[] colorValue;
/*     */   
/*     */   protected Color(PdfColorSpace colorSpace, float[] colorValue) {
/*  80 */     this.colorSpace = colorSpace;
/*  81 */     if (colorValue == null) {
/*  82 */       this.colorValue = new float[colorSpace.getNumberOfComponents()];
/*     */     } else {
/*  84 */       this.colorValue = colorValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color makeColor(PdfColorSpace colorSpace) {
/*  95 */     return makeColor(colorSpace, null);
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
/*     */   public static Color makeColor(PdfColorSpace colorSpace, float[] colorValue) {
/* 107 */     Color c = null;
/* 108 */     boolean unknownColorSpace = false;
/* 109 */     if (colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs) {
/* 110 */       if (colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Gray) {
/* 111 */         c = (colorValue != null) ? new DeviceGray(colorValue[0]) : new DeviceGray();
/* 112 */       } else if (colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Rgb) {
/* 113 */         c = (colorValue != null) ? new DeviceRgb(colorValue[0], colorValue[1], colorValue[2]) : new DeviceRgb();
/* 114 */       } else if (colorSpace instanceof com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs.Cmyk) {
/* 115 */         c = (colorValue != null) ? new DeviceCmyk(colorValue[0], colorValue[1], colorValue[2], colorValue[3]) : new DeviceCmyk();
/*     */       } else {
/* 117 */         unknownColorSpace = true;
/*     */       } 
/* 119 */     } else if (colorSpace instanceof PdfCieBasedCs) {
/* 120 */       if (colorSpace instanceof PdfCieBasedCs.CalGray) {
/* 121 */         PdfCieBasedCs.CalGray calGray = (PdfCieBasedCs.CalGray)colorSpace;
/* 122 */         c = (colorValue != null) ? new CalGray(calGray, colorValue[0]) : new CalGray(calGray);
/* 123 */       } else if (colorSpace instanceof PdfCieBasedCs.CalRgb) {
/* 124 */         PdfCieBasedCs.CalRgb calRgb = (PdfCieBasedCs.CalRgb)colorSpace;
/* 125 */         c = (colorValue != null) ? new CalRgb(calRgb, colorValue) : new CalRgb(calRgb);
/* 126 */       } else if (colorSpace instanceof PdfCieBasedCs.IccBased) {
/* 127 */         PdfCieBasedCs.IccBased iccBased = (PdfCieBasedCs.IccBased)colorSpace;
/* 128 */         c = (colorValue != null) ? new IccBased(iccBased, colorValue) : new IccBased(iccBased);
/* 129 */       } else if (colorSpace instanceof PdfCieBasedCs.Lab) {
/* 130 */         PdfCieBasedCs.Lab lab = (PdfCieBasedCs.Lab)colorSpace;
/* 131 */         c = (colorValue != null) ? new Lab(lab, colorValue) : new Lab(lab);
/*     */       } else {
/* 133 */         unknownColorSpace = true;
/*     */       } 
/* 135 */     } else if (colorSpace instanceof PdfSpecialCs) {
/* 136 */       if (colorSpace instanceof PdfSpecialCs.Separation) {
/* 137 */         PdfSpecialCs.Separation separation = (PdfSpecialCs.Separation)colorSpace;
/* 138 */         c = (colorValue != null) ? new Separation(separation, colorValue[0]) : new Separation(separation);
/* 139 */       } else if (colorSpace instanceof PdfSpecialCs.DeviceN) {
/*     */         
/* 141 */         PdfSpecialCs.DeviceN deviceN = (PdfSpecialCs.DeviceN)colorSpace;
/* 142 */         c = (colorValue != null) ? new DeviceN(deviceN, colorValue) : new DeviceN(deviceN);
/* 143 */       } else if (colorSpace instanceof PdfSpecialCs.Indexed) {
/* 144 */         c = (colorValue != null) ? new Indexed(colorSpace, (int)colorValue[0]) : new Indexed(colorSpace);
/*     */       } else {
/* 146 */         unknownColorSpace = true;
/*     */       } 
/* 148 */     } else if (colorSpace instanceof PdfSpecialCs.Pattern) {
/* 149 */       c = new Color(colorSpace, colorValue);
/*     */     } else {
/* 151 */       unknownColorSpace = true;
/*     */     } 
/* 153 */     if (unknownColorSpace) {
/* 154 */       throw new PdfException("Unknown color space.");
/*     */     }
/* 156 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceRgb convertCmykToRgb(DeviceCmyk cmykColor) {
/* 167 */     float cyanComp = 1.0F - cmykColor.getColorValue()[0];
/* 168 */     float magentaComp = 1.0F - cmykColor.getColorValue()[1];
/* 169 */     float yellowComp = 1.0F - cmykColor.getColorValue()[2];
/* 170 */     float blackComp = 1.0F - cmykColor.getColorValue()[3];
/*     */     
/* 172 */     float r = cyanComp * blackComp;
/* 173 */     float g = magentaComp * blackComp;
/* 174 */     float b = yellowComp * blackComp;
/* 175 */     return new DeviceRgb(r, g, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceCmyk convertRgbToCmyk(DeviceRgb rgbColor) {
/* 186 */     float redComp = rgbColor.getColorValue()[0];
/* 187 */     float greenComp = rgbColor.getColorValue()[1];
/* 188 */     float blueComp = rgbColor.getColorValue()[2];
/*     */     
/* 190 */     float k = 1.0F - Math.max(Math.max(redComp, greenComp), blueComp);
/* 191 */     float c = (1.0F - redComp - k) / (1.0F - k);
/* 192 */     float m = (1.0F - greenComp - k) / (1.0F - k);
/* 193 */     float y = (1.0F - blueComp - k) / (1.0F - k);
/* 194 */     return new DeviceCmyk(c, m, y, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfComponents() {
/* 203 */     return this.colorValue.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfColorSpace getColorSpace() {
/* 213 */     return this.colorSpace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getColorValue() {
/* 222 */     return this.colorValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorValue(float[] value) {
/* 231 */     if (this.colorValue.length != value.length) {
/* 232 */       throw new PdfException("Incorrect number of components.", this);
/*     */     }
/* 234 */     this.colorValue = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 243 */     if (this == o) {
/* 244 */       return true;
/*     */     }
/* 246 */     if (o == null || getClass() != o.getClass()) {
/* 247 */       return false;
/*     */     }
/* 249 */     Color color = (Color)o;
/* 250 */     return (((this.colorSpace != null) ? this.colorSpace.getPdfObject().equals(color.colorSpace.getPdfObject()) : (color.colorSpace == null)) && 
/* 251 */       Arrays.equals(this.colorValue, color.colorValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 259 */     int result = (this.colorSpace != null) ? this.colorSpace.getPdfObject().hashCode() : 0;
/* 260 */     result = 31 * result + ((this.colorValue != null) ? Arrays.hashCode(this.colorValue) : 0);
/* 261 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/Color.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */