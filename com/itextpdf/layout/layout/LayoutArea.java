/*     */ package com.itextpdf.layout.layout;
/*     */ 
/*     */ import com.itextpdf.io.util.HashCode;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
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
/*     */ 
/*     */ 
/*     */ public class LayoutArea
/*     */   implements Cloneable
/*     */ {
/*     */   protected int pageNumber;
/*     */   protected Rectangle bBox;
/*     */   
/*     */   public LayoutArea(int pageNumber, Rectangle bBox) {
/*  72 */     this.pageNumber = pageNumber;
/*  73 */     this.bBox = bBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPageNumber() {
/*  82 */     return this.pageNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBBox() {
/*  91 */     return this.bBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBBox(Rectangle bbox) {
/* 100 */     this.bBox = bbox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutArea clone() {
/*     */     try {
/* 112 */       LayoutArea clone = (LayoutArea)super.clone();
/*     */       
/* 114 */       clone.bBox = this.bBox.clone();
/* 115 */       return clone;
/* 116 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 118 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 127 */     if (getClass() != obj.getClass()) {
/* 128 */       return false;
/*     */     }
/* 130 */     LayoutArea that = (LayoutArea)obj;
/* 131 */     return (this.pageNumber == that.pageNumber && this.bBox.equalsWithEpsilon(that.bBox));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     HashCode hashCode = new HashCode();
/* 140 */     hashCode.append(this.pageNumber)
/* 141 */       .append(this.bBox.hashCode());
/* 142 */     return hashCode.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 150 */     return MessageFormatUtil.format("{0}, page {1}", new Object[] { this.bBox.toString(), Integer.valueOf(this.pageNumber) });
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/layout/LayoutArea.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */