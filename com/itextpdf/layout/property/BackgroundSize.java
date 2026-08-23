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
/*     */ public class BackgroundSize
/*     */ {
/*     */   private UnitValue backgroundWidthSize;
/*     */   private UnitValue backgroundHeightSize;
/*     */   private boolean cover = false;
/*     */   private boolean contain = false;
/*     */   
/*     */   public void setBackgroundSizeToValues(UnitValue width, UnitValue height) {
/*  63 */     clear();
/*  64 */     this.backgroundWidthSize = width;
/*  65 */     this.backgroundHeightSize = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundSizeToContain() {
/*  74 */     clear();
/*  75 */     this.contain = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundSizeToCover() {
/*  84 */     clear();
/*  85 */     this.cover = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getBackgroundWidthSize() {
/*  95 */     return this.backgroundWidthSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitValue getBackgroundHeightSize() {
/* 105 */     return this.backgroundHeightSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecificSize() {
/* 114 */     return (this.contain || this.cover);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContain() {
/* 124 */     return this.contain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCover() {
/* 134 */     return this.cover;
/*     */   }
/*     */   
/*     */   private void clear() {
/* 138 */     this.contain = false;
/* 139 */     this.cover = false;
/* 140 */     this.backgroundWidthSize = null;
/* 141 */     this.backgroundHeightSize = null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/BackgroundSize.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */