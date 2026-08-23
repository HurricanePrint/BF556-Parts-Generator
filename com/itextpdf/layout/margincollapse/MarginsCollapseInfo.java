/*     */ package com.itextpdf.layout.margincollapse;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MarginsCollapseInfo
/*     */   implements Serializable
/*     */ {
/*     */   private boolean ignoreOwnMarginTop;
/*     */   private boolean ignoreOwnMarginBottom;
/*     */   private MarginsCollapse collapseBefore;
/*     */   private MarginsCollapse collapseAfter;
/*     */   private MarginsCollapse ownCollapseAfter;
/*     */   private boolean isSelfCollapsing;
/*     */   private float bufferSpaceOnTop;
/*     */   private float bufferSpaceOnBottom;
/*     */   private float usedBufferSpaceOnTop;
/*     */   private float usedBufferSpaceOnBottom;
/*     */   private boolean clearanceApplied;
/*     */   
/*     */   MarginsCollapseInfo() {
/*  67 */     this.ignoreOwnMarginTop = false;
/*  68 */     this.ignoreOwnMarginBottom = false;
/*  69 */     this.collapseBefore = new MarginsCollapse();
/*  70 */     this.collapseAfter = new MarginsCollapse();
/*  71 */     this.isSelfCollapsing = true;
/*  72 */     this.bufferSpaceOnTop = 0.0F;
/*  73 */     this.bufferSpaceOnBottom = 0.0F;
/*  74 */     this.usedBufferSpaceOnTop = 0.0F;
/*  75 */     this.usedBufferSpaceOnBottom = 0.0F;
/*  76 */     this.clearanceApplied = false;
/*     */   }
/*     */   
/*     */   MarginsCollapseInfo(boolean ignoreOwnMarginTop, boolean ignoreOwnMarginBottom, MarginsCollapse collapseBefore, MarginsCollapse collapseAfter) {
/*  80 */     this.ignoreOwnMarginTop = ignoreOwnMarginTop;
/*  81 */     this.ignoreOwnMarginBottom = ignoreOwnMarginBottom;
/*  82 */     this.collapseBefore = collapseBefore;
/*  83 */     this.collapseAfter = collapseAfter;
/*  84 */     this.isSelfCollapsing = true;
/*  85 */     this.bufferSpaceOnTop = 0.0F;
/*  86 */     this.bufferSpaceOnBottom = 0.0F;
/*  87 */     this.usedBufferSpaceOnTop = 0.0F;
/*  88 */     this.usedBufferSpaceOnBottom = 0.0F;
/*  89 */     this.clearanceApplied = false;
/*     */   }
/*     */   
/*     */   public void copyTo(MarginsCollapseInfo destInfo) {
/*  93 */     destInfo.ignoreOwnMarginTop = this.ignoreOwnMarginTop;
/*  94 */     destInfo.ignoreOwnMarginBottom = this.ignoreOwnMarginBottom;
/*  95 */     destInfo.collapseBefore = this.collapseBefore;
/*  96 */     destInfo.collapseAfter = this.collapseAfter;
/*     */     
/*  98 */     destInfo.setOwnCollapseAfter(this.ownCollapseAfter);
/*  99 */     destInfo.setSelfCollapsing(this.isSelfCollapsing);
/* 100 */     destInfo.setBufferSpaceOnTop(this.bufferSpaceOnTop);
/* 101 */     destInfo.setBufferSpaceOnBottom(this.bufferSpaceOnBottom);
/* 102 */     destInfo.setUsedBufferSpaceOnTop(this.usedBufferSpaceOnTop);
/* 103 */     destInfo.setUsedBufferSpaceOnBottom(this.usedBufferSpaceOnBottom);
/*     */     
/* 105 */     destInfo.setClearanceApplied(this.clearanceApplied);
/*     */   }
/*     */   
/*     */   public static MarginsCollapseInfo createDeepCopy(MarginsCollapseInfo instance) {
/* 109 */     MarginsCollapseInfo copy = new MarginsCollapseInfo();
/* 110 */     instance.copyTo(copy);
/*     */     
/* 112 */     copy.collapseBefore = instance.collapseBefore.clone();
/* 113 */     copy.collapseAfter = instance.collapseAfter.clone();
/* 114 */     if (instance.ownCollapseAfter != null) {
/* 115 */       copy.setOwnCollapseAfter(instance.ownCollapseAfter.clone());
/*     */     }
/*     */     
/* 118 */     return copy;
/*     */   }
/*     */   
/*     */   public static void updateFromCopy(MarginsCollapseInfo originalInstance, MarginsCollapseInfo processedCopy) {
/* 122 */     originalInstance.ignoreOwnMarginTop = processedCopy.ignoreOwnMarginTop;
/* 123 */     originalInstance.ignoreOwnMarginBottom = processedCopy.ignoreOwnMarginBottom;
/*     */     
/* 125 */     originalInstance.collapseBefore.joinMargin(processedCopy.collapseBefore);
/* 126 */     originalInstance.collapseAfter.joinMargin(processedCopy.collapseAfter);
/*     */     
/* 128 */     if (processedCopy.getOwnCollapseAfter() != null) {
/* 129 */       if (originalInstance.getOwnCollapseAfter() == null) {
/* 130 */         originalInstance.setOwnCollapseAfter(new MarginsCollapse());
/*     */       }
/* 132 */       originalInstance.getOwnCollapseAfter().joinMargin(processedCopy.getOwnCollapseAfter());
/*     */     } 
/* 134 */     originalInstance.setSelfCollapsing(processedCopy.isSelfCollapsing);
/* 135 */     originalInstance.setBufferSpaceOnTop(processedCopy.bufferSpaceOnTop);
/* 136 */     originalInstance.setBufferSpaceOnBottom(processedCopy.bufferSpaceOnBottom);
/* 137 */     originalInstance.setUsedBufferSpaceOnTop(processedCopy.usedBufferSpaceOnTop);
/* 138 */     originalInstance.setUsedBufferSpaceOnBottom(processedCopy.usedBufferSpaceOnBottom);
/*     */     
/* 140 */     originalInstance.setClearanceApplied(processedCopy.clearanceApplied);
/*     */   }
/*     */   
/*     */   MarginsCollapse getCollapseBefore() {
/* 144 */     return this.collapseBefore;
/*     */   }
/*     */   MarginsCollapse getCollapseAfter() {
/* 147 */     return this.collapseAfter;
/*     */   }
/*     */   void setCollapseAfter(MarginsCollapse collapseAfter) {
/* 150 */     this.collapseAfter = collapseAfter;
/*     */   }
/*     */   MarginsCollapse getOwnCollapseAfter() {
/* 153 */     return this.ownCollapseAfter;
/*     */   }
/*     */   void setOwnCollapseAfter(MarginsCollapse marginsCollapse) {
/* 156 */     this.ownCollapseAfter = marginsCollapse;
/*     */   }
/*     */   
/*     */   void setSelfCollapsing(boolean selfCollapsing) {
/* 160 */     this.isSelfCollapsing = selfCollapsing;
/*     */   }
/*     */   
/*     */   boolean isSelfCollapsing() {
/* 164 */     return this.isSelfCollapsing;
/*     */   }
/*     */   
/*     */   boolean isIgnoreOwnMarginTop() {
/* 168 */     return this.ignoreOwnMarginTop;
/*     */   }
/*     */   
/*     */   boolean isIgnoreOwnMarginBottom() {
/* 172 */     return this.ignoreOwnMarginBottom;
/*     */   }
/*     */   
/*     */   float getBufferSpaceOnTop() {
/* 176 */     return this.bufferSpaceOnTop;
/*     */   }
/*     */   
/*     */   void setBufferSpaceOnTop(float bufferSpaceOnTop) {
/* 180 */     this.bufferSpaceOnTop = bufferSpaceOnTop;
/*     */   }
/*     */   
/*     */   float getBufferSpaceOnBottom() {
/* 184 */     return this.bufferSpaceOnBottom;
/*     */   }
/*     */   
/*     */   void setBufferSpaceOnBottom(float bufferSpaceOnBottom) {
/* 188 */     this.bufferSpaceOnBottom = bufferSpaceOnBottom;
/*     */   }
/*     */   
/*     */   float getUsedBufferSpaceOnTop() {
/* 192 */     return this.usedBufferSpaceOnTop;
/*     */   }
/*     */   
/*     */   void setUsedBufferSpaceOnTop(float usedBufferSpaceOnTop) {
/* 196 */     this.usedBufferSpaceOnTop = usedBufferSpaceOnTop;
/*     */   }
/*     */   
/*     */   float getUsedBufferSpaceOnBottom() {
/* 200 */     return this.usedBufferSpaceOnBottom;
/*     */   }
/*     */   
/*     */   void setUsedBufferSpaceOnBottom(float usedBufferSpaceOnBottom) {
/* 204 */     this.usedBufferSpaceOnBottom = usedBufferSpaceOnBottom;
/*     */   }
/*     */   
/*     */   boolean isClearanceApplied() {
/* 208 */     return this.clearanceApplied;
/*     */   }
/*     */   
/*     */   void setClearanceApplied(boolean clearanceApplied) {
/* 212 */     this.clearanceApplied = clearanceApplied;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/margincollapse/MarginsCollapseInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */