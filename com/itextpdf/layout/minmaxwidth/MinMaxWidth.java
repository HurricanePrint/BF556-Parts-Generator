/*     */ package com.itextpdf.layout.minmaxwidth;
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
/*     */ public class MinMaxWidth
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4642527900783929637L;
/*     */   private float childrenMinWidth;
/*     */   private float childrenMaxWidth;
/*     */   private float additionalWidth;
/*     */   
/*     */   public MinMaxWidth() {
/*  56 */     this(0.0F);
/*     */   }
/*     */   
/*     */   public MinMaxWidth(float additionalWidth) {
/*  60 */     this(0.0F, 0.0F, additionalWidth);
/*     */   }
/*     */   
/*     */   public MinMaxWidth(float childrenMinWidth, float childrenMaxWidth, float additionalWidth) {
/*  64 */     this.childrenMinWidth = childrenMinWidth;
/*  65 */     this.childrenMaxWidth = childrenMaxWidth;
/*  66 */     this.additionalWidth = additionalWidth;
/*     */   }
/*     */   
/*     */   public float getChildrenMinWidth() {
/*  70 */     return this.childrenMinWidth;
/*     */   }
/*     */   
/*     */   public void setChildrenMinWidth(float childrenMinWidth) {
/*  74 */     this.childrenMinWidth = childrenMinWidth;
/*     */   }
/*     */   
/*     */   public float getChildrenMaxWidth() {
/*  78 */     return this.childrenMaxWidth;
/*     */   }
/*     */   
/*     */   public void setChildrenMaxWidth(float childrenMaxWidth) {
/*  82 */     this.childrenMaxWidth = childrenMaxWidth;
/*     */   }
/*     */   
/*     */   public float getAdditionalWidth() {
/*  86 */     return this.additionalWidth;
/*     */   }
/*     */   
/*     */   public void setAdditionalWidth(float additionalWidth) {
/*  90 */     this.additionalWidth = additionalWidth;
/*     */   }
/*     */   
/*     */   public float getMaxWidth() {
/*  94 */     return Math.min(this.childrenMaxWidth + this.additionalWidth, MinMaxWidthUtils.getInfWidth());
/*     */   }
/*     */   
/*     */   public float getMinWidth() {
/*  98 */     return Math.min(this.childrenMinWidth + this.additionalWidth, getMaxWidth());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return "min=" + (this.childrenMinWidth + this.additionalWidth) + ", max=" + (this.childrenMaxWidth + this.additionalWidth);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/minmaxwidth/MinMaxWidth.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */