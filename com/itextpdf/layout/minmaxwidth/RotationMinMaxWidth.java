/*     */ package com.itextpdf.layout.minmaxwidth;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotationMinMaxWidth
/*     */   extends MinMaxWidth
/*     */ {
/*     */   private double minWidthOrigin;
/*     */   private double maxWidthOrigin;
/*     */   private double minWidthHeight;
/*     */   private double maxWidthHeight;
/*     */   
/*     */   public RotationMinMaxWidth(double minWidth, double maxWidth, double minWidthOrigin, double maxWidthOrigin, double minWidthHeight, double maxWidthHeight) {
/*  70 */     super((float)minWidth, (float)maxWidth, 0.0F);
/*  71 */     this.maxWidthOrigin = maxWidthOrigin;
/*  72 */     this.minWidthOrigin = minWidthOrigin;
/*  73 */     this.minWidthHeight = minWidthHeight;
/*  74 */     this.maxWidthHeight = maxWidthHeight;
/*     */   }
/*     */   
/*     */   public double getMinWidthOrigin() {
/*  78 */     return this.minWidthOrigin;
/*     */   }
/*     */   
/*     */   public double getMaxWidthOrigin() {
/*  82 */     return this.maxWidthOrigin;
/*     */   }
/*     */   
/*     */   public double getMinWidthHeight() {
/*  86 */     return this.minWidthHeight;
/*     */   }
/*     */   
/*     */   public double getMaxWidthHeight() {
/*  90 */     return this.maxWidthHeight;
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
/*     */   public static RotationMinMaxWidth calculate(double angle, double area, MinMaxWidth elementMinMaxWidth) {
/* 103 */     WidthFunction function = new WidthFunction(angle, area);
/* 104 */     return calculate(function, elementMinMaxWidth.getMinWidth(), elementMinMaxWidth.getMaxWidth());
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
/*     */   public static RotationMinMaxWidth calculate(double angle, double area, MinMaxWidth elementMinMaxWidth, double availableWidth) {
/* 118 */     WidthFunction function = new WidthFunction(angle, area);
/* 119 */     WidthFunction.Interval validArguments = function.getValidOriginalWidths(availableWidth);
/* 120 */     if (validArguments == null) {
/* 121 */       return null;
/*     */     }
/* 123 */     double xMin = Math.max(elementMinMaxWidth.getMinWidth(), validArguments.getMin());
/* 124 */     double xMax = Math.min(elementMinMaxWidth.getMaxWidth(), validArguments.getMax());
/*     */     
/* 126 */     if (xMax < xMin) {
/*     */ 
/*     */       
/* 129 */       double rotatedWidth = function.getRotatedWidth(xMin);
/* 130 */       double rotatedHeight = function.getRotatedHeight(xMin);
/* 131 */       return new RotationMinMaxWidth(rotatedWidth, rotatedWidth, xMin, xMin, rotatedHeight, rotatedHeight);
/*     */     } 
/* 133 */     return calculate(function, xMin, xMax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double calculateRotatedWidth(Rectangle area, double angle) {
/* 144 */     return area.getWidth() * cos(angle) + area.getHeight() * sin(angle);
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
/*     */   private static RotationMinMaxWidth calculate(WidthFunction func, double xMin, double xMax) {
/* 161 */     double minWidthOrigin, maxWidthOrigin, x0 = func.getWidthDerivativeZeroPoint();
/*     */ 
/*     */     
/* 164 */     if (x0 < xMin) {
/*     */       
/* 166 */       minWidthOrigin = xMin;
/* 167 */       maxWidthOrigin = xMax;
/*     */     }
/* 169 */     else if (x0 > xMax) {
/*     */       
/* 171 */       minWidthOrigin = xMax;
/* 172 */       maxWidthOrigin = xMin;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 177 */       minWidthOrigin = x0;
/* 178 */       maxWidthOrigin = (func.getRotatedWidth(xMax) > func.getRotatedWidth(xMin)) ? xMax : xMin;
/*     */     } 
/*     */     
/* 181 */     return new RotationMinMaxWidth(func.getRotatedWidth(minWidthOrigin), func.getRotatedWidth(maxWidthOrigin), minWidthOrigin, maxWidthOrigin, func
/* 182 */         .getRotatedHeight(minWidthOrigin), func.getRotatedHeight(maxWidthOrigin));
/*     */   }
/*     */   
/*     */   private static double sin(double angle) {
/* 186 */     return correctSinCos(Math.abs(Math.sin(angle)));
/*     */   }
/*     */   
/*     */   private static double cos(double angle) {
/* 190 */     return correctSinCos(Math.abs(Math.cos(angle)));
/*     */   }
/*     */   
/*     */   private static double correctSinCos(double value) {
/* 194 */     if (MinMaxWidthUtils.isEqual(value, 0.0D))
/* 195 */       return 0.0D; 
/* 196 */     if (MinMaxWidthUtils.isEqual(value, 1.0D)) {
/* 197 */       return 1.0D;
/*     */     }
/* 199 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WidthFunction
/*     */   {
/*     */     private double sin;
/*     */ 
/*     */ 
/*     */     
/*     */     private double cos;
/*     */ 
/*     */ 
/*     */     
/*     */     private double area;
/*     */ 
/*     */ 
/*     */     
/*     */     public WidthFunction(double angle, double area) {
/* 220 */       this.sin = RotationMinMaxWidth.sin(angle);
/* 221 */       this.cos = RotationMinMaxWidth.cos(angle);
/* 222 */       this.area = area;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getRotatedWidth(double x) {
/* 232 */       return x * this.cos + this.area * this.sin / x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getRotatedHeight(double x) {
/* 242 */       return x * this.sin + this.area * this.cos / x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Interval getValidOriginalWidths(double availableWidth) {
/*     */       double minWidth;
/*     */       double maxWidth;
/* 254 */       if (this.cos == 0.0D) {
/* 255 */         minWidth = this.area * this.sin / availableWidth;
/* 256 */         maxWidth = MinMaxWidthUtils.getInfWidth();
/* 257 */       } else if (this.sin == 0.0D) {
/* 258 */         minWidth = 0.0D;
/* 259 */         maxWidth = availableWidth / this.cos;
/*     */       } else {
/* 261 */         double D = availableWidth * availableWidth - 4.0D * this.area * this.sin * this.cos;
/* 262 */         if (D < 0.0D) {
/* 263 */           return null;
/*     */         }
/* 265 */         minWidth = (availableWidth - Math.sqrt(D)) / 2.0D * this.cos;
/* 266 */         maxWidth = (availableWidth + Math.sqrt(D)) / 2.0D * this.cos;
/*     */       } 
/* 268 */       return new Interval(minWidth, maxWidth);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getWidthDerivativeZeroPoint() {
/* 279 */       return Math.sqrt(this.area * this.sin / this.cos);
/*     */     }
/*     */     
/*     */     public static class Interval {
/*     */       private double min;
/*     */       private double max;
/*     */       
/*     */       public Interval(double min, double max) {
/* 287 */         this.min = min;
/* 288 */         this.max = max;
/*     */       }
/*     */       
/*     */       public double getMin() {
/* 292 */         return this.min;
/*     */       }
/*     */       
/*     */       public double getMax() {
/* 296 */         return this.max; } } } public static class Interval { public double getMax() { return this.max; }
/*     */ 
/*     */     
/*     */     private double min;
/*     */     private double max;
/*     */     
/*     */     public Interval(double min, double max) {
/*     */       this.min = min;
/*     */       this.max = max;
/*     */     }
/*     */     
/*     */     public double getMin() {
/*     */       return this.min;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/minmaxwidth/RotationMinMaxWidth.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */