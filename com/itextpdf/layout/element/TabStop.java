/*     */ package com.itextpdf.layout.element;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
/*     */ import com.itextpdf.layout.property.TabAlignment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TabStop
/*     */ {
/*     */   private float tabPosition;
/*     */   private TabAlignment tabAlignment;
/*     */   private Character tabAnchor;
/*     */   private ILineDrawer tabLeader;
/*     */   
/*     */   public TabStop(float tabPosition) {
/*  70 */     this(tabPosition, TabAlignment.LEFT);
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
/*     */   public TabStop(float tabPosition, TabAlignment tabAlignment) {
/*  82 */     this(tabPosition, tabAlignment, null);
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
/*     */   public TabStop(float tabPosition, TabAlignment tabAlignment, ILineDrawer tabLeader) {
/*  97 */     this.tabPosition = tabPosition;
/*  98 */     this.tabAlignment = tabAlignment;
/*  99 */     this.tabLeader = tabLeader;
/* 100 */     this.tabAnchor = Character.valueOf('.');
/*     */   }
/*     */   
/*     */   public float getTabPosition() {
/* 104 */     return this.tabPosition;
/*     */   }
/*     */   
/*     */   public TabAlignment getTabAlignment() {
/* 108 */     return this.tabAlignment;
/*     */   }
/*     */   
/*     */   public void setTabAlignment(TabAlignment tabAlignment) {
/* 112 */     this.tabAlignment = tabAlignment;
/*     */   }
/*     */   
/*     */   public Character getTabAnchor() {
/* 116 */     return this.tabAnchor;
/*     */   }
/*     */   
/*     */   public void setTabAnchor(Character tabAnchor) {
/* 120 */     this.tabAnchor = tabAnchor;
/*     */   }
/*     */   
/*     */   public ILineDrawer getTabLeader() {
/* 124 */     return this.tabLeader;
/*     */   }
/*     */   
/*     */   public void setTabLeader(ILineDrawer tabLeader) {
/* 128 */     this.tabLeader = tabLeader;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/element/TabStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */