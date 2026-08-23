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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BorderRadius
/*     */ {
/*     */   private UnitValue horizontalRadius;
/*     */   private UnitValue verticalRadius;
/*     */   
/*     */   public BorderRadius(UnitValue radius) {
/*  63 */     this.horizontalRadius = radius;
/*  64 */     this.verticalRadius = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BorderRadius(float radius) {
/*  73 */     this.horizontalRadius = UnitValue.createPointValue(radius);
/*  74 */     this.verticalRadius = this.horizontalRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BorderRadius(UnitValue horizontalRadius, UnitValue verticalRadius) {
/*  84 */     this.horizontalRadius = horizontalRadius;
/*  85 */     this.verticalRadius = verticalRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BorderRadius(float horizontalRadius, float verticalRadius) {
/*  95 */     this.horizontalRadius = UnitValue.createPointValue(horizontalRadius);
/*  96 */     this.verticalRadius = UnitValue.createPointValue(verticalRadius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getHorizontalRadius() {
/* 105 */     return this.horizontalRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getVerticalRadius() {
/* 114 */     return this.verticalRadius;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/BorderRadius.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */