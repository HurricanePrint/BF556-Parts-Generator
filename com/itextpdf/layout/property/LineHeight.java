/*     */ package com.itextpdf.layout.property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineHeight
/*     */ {
/*     */   private static final int FIXED = 1;
/*     */   private static final int MULTIPLIED = 2;
/*     */   private static final int NORMAL = 4;
/*     */   private int type;
/*     */   private float value;
/*     */   
/*     */   private LineHeight(int type, float value) {
/*  40 */     this.type = type;
/*  41 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValue() {
/*  51 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LineHeight createFixedValue(float value) {
/*  61 */     return new LineHeight(1, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LineHeight createMultipliedValue(float value) {
/*  72 */     return new LineHeight(2, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LineHeight createNormalValue() {
/*  81 */     return new LineHeight(4, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFixedValue() {
/*  90 */     return (this.type == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMultipliedValue() {
/*  99 */     return (this.type == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNormalValue() {
/* 108 */     return (this.type == 4);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/LineHeight.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */