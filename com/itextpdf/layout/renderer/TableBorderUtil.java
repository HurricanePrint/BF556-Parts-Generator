/*     */ package com.itextpdf.layout.renderer;
/*     */ 
/*     */ import com.itextpdf.layout.borders.Border;
/*     */ import com.itextpdf.layout.element.Cell;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TableBorderUtil
/*     */ {
/*     */   public static Border getCellSideBorder(Cell cellModel, int borderType) {
/*  57 */     Border cellModelSideBorder = (Border)cellModel.getProperty(borderType);
/*  58 */     if (null == cellModelSideBorder && !cellModel.hasProperty(borderType)) {
/*  59 */       cellModelSideBorder = (Border)cellModel.getProperty(9);
/*  60 */       if (null == cellModelSideBorder && !cellModel.hasProperty(9)) {
/*  61 */         cellModelSideBorder = (Border)cellModel.getDefaultProperty(9);
/*     */       }
/*     */     } 
/*  64 */     return cellModelSideBorder;
/*     */   }
/*     */   
/*     */   public static Border getWidestBorder(List<Border> borderList) {
/*  68 */     Border theWidestBorder = null;
/*  69 */     if (0 != borderList.size()) {
/*  70 */       for (Border border : borderList) {
/*  71 */         if (null != border && (null == theWidestBorder || border.getWidth() > theWidestBorder.getWidth())) {
/*  72 */           theWidestBorder = border;
/*     */         }
/*     */       } 
/*     */     }
/*  76 */     return theWidestBorder;
/*     */   }
/*     */   
/*     */   public static Border getWidestBorder(List<Border> borderList, int start, int end) {
/*  80 */     Border theWidestBorder = null;
/*  81 */     if (0 != borderList.size()) {
/*  82 */       for (Border border : borderList.subList(start, end)) {
/*  83 */         if (null != border && (null == theWidestBorder || border.getWidth() > theWidestBorder.getWidth())) {
/*  84 */           theWidestBorder = border;
/*     */         }
/*     */       } 
/*     */     }
/*  88 */     return theWidestBorder;
/*     */   }
/*     */   
/*     */   public static List<Border> createAndFillBorderList(Border border, int size) {
/*  92 */     List<Border> borderList = new ArrayList<>();
/*  93 */     for (int i = 0; i < size; i++) {
/*  94 */       borderList.add(border);
/*     */     }
/*  96 */     return borderList;
/*     */   }
/*     */   
/*     */   public static List<Border> createAndFillBorderList(List<Border> originalList, Border borderToCollapse, int size) {
/* 100 */     List<Border> borderList = new ArrayList<>();
/* 101 */     if (null != originalList) {
/* 102 */       borderList.addAll(originalList);
/*     */     }
/* 104 */     while (borderList.size() < size) {
/* 105 */       borderList.add(borderToCollapse);
/*     */     }
/* 107 */     int end = (null == originalList) ? size : Math.min(originalList.size(), size);
/* 108 */     for (int i = 0; i < end; i++) {
/* 109 */       if (null == borderList.get(i) || (null != borderToCollapse && ((Border)borderList.get(i)).getWidth() <= borderToCollapse.getWidth())) {
/* 110 */         borderList.set(i, borderToCollapse);
/*     */       }
/*     */     } 
/* 113 */     return borderList;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/renderer/TableBorderUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */