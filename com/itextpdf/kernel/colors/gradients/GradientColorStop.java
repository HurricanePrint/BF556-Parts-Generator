/*     */ package com.itextpdf.kernel.colors.gradients;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GradientColorStop
/*     */ {
/*     */   private final float[] rgb;
/*     */   private final float opacity;
/*     */   private OffsetType offsetType;
/*     */   private double offset;
/*  44 */   private double hintOffset = 0.0D;
/*  45 */   private HintOffsetType hintOffsetType = HintOffsetType.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientColorStop(float[] rgb) {
/*  54 */     this(rgb, 1.0F, 0.0D, OffsetType.AUTO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientColorStop(float[] rgb, double offset, OffsetType offsetType) {
/*  65 */     this(rgb, 1.0F, offset, offsetType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientColorStop(GradientColorStop gradientColorStop, double offset, OffsetType offsetType) {
/*  76 */     this(gradientColorStop.getRgbArray(), gradientColorStop.getOpacity(), offset, offsetType);
/*     */   }
/*     */   
/*     */   private GradientColorStop(float[] rgb, float opacity, double offset, OffsetType offsetType) {
/*  80 */     this.rgb = copyRgbArray(rgb);
/*     */     
/*  82 */     this.opacity = normalize(opacity);
/*     */     
/*  84 */     setOffset(offset, offsetType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getRgbArray() {
/*  93 */     return copyRgbArray(this.rgb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getOpacity() {
/* 103 */     return this.opacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OffsetType getOffsetType() {
/* 112 */     return this.offsetType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOffset() {
/* 121 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHintOffset() {
/* 130 */     return this.hintOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HintOffsetType getHintOffsetType() {
/* 139 */     return this.hintOffsetType;
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
/*     */   public GradientColorStop setOffset(double offset, OffsetType offsetType) {
/* 151 */     this.offsetType = (offsetType != null) ? offsetType : OffsetType.AUTO;
/* 152 */     this.offset = (this.offsetType != OffsetType.AUTO) ? offset : 0.0D;
/* 153 */     return this;
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
/*     */   public GradientColorStop setHint(double hintOffset, HintOffsetType hintOffsetType) {
/* 165 */     this.hintOffsetType = (hintOffsetType != null) ? hintOffsetType : HintOffsetType.NONE;
/* 166 */     this.hintOffset = (this.hintOffsetType != HintOffsetType.NONE) ? hintOffset : 0.0D;
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 172 */     if (this == o) {
/* 173 */       return true;
/*     */     }
/* 175 */     if (o == null || getClass() != o.getClass()) {
/* 176 */       return false;
/*     */     }
/* 178 */     GradientColorStop that = (GradientColorStop)o;
/* 179 */     return (Float.compare(that.opacity, this.opacity) == 0 && 
/* 180 */       Double.compare(that.offset, this.offset) == 0 && 
/* 181 */       Double.compare(that.hintOffset, this.hintOffset) == 0 && 
/* 182 */       Arrays.equals(this.rgb, that.rgb) && this.offsetType == that.offsetType && this.hintOffsetType == that.hintOffsetType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 189 */     int result = Objects.hash(new Object[] { Float.valueOf(this.opacity), Double.valueOf(this.offset), Double.valueOf(this.hintOffset) });
/* 190 */     result = 31 * result + this.offsetType.hashCode();
/* 191 */     result = 31 * result + this.hintOffsetType.hashCode();
/* 192 */     result = 31 * result + Arrays.hashCode(this.rgb);
/* 193 */     return result;
/*     */   }
/*     */   
/*     */   private static float normalize(float toNormalize) {
/* 197 */     return (toNormalize > 1.0F) ? 1.0F : ((toNormalize > 0.0F) ? toNormalize : 0.0F);
/*     */   }
/*     */   
/*     */   private static float[] copyRgbArray(float[] toCopy) {
/* 201 */     if (toCopy == null || toCopy.length < 3) {
/* 202 */       return new float[] { 0.0F, 0.0F, 0.0F };
/*     */     }
/* 204 */     return new float[] { normalize(toCopy[0]), normalize(toCopy[1]), normalize(toCopy[2]) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum OffsetType
/*     */   {
/* 214 */     ABSOLUTE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     AUTO,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     RELATIVE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum HintOffsetType
/*     */   {
/* 237 */     ABSOLUTE_ON_GRADIENT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     RELATIVE_ON_GRADIENT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     RELATIVE_BETWEEN_COLORS,
/*     */ 
/*     */ 
/*     */     
/* 251 */     NONE;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/colors/gradients/GradientColorStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */