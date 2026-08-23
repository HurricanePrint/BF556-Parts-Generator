/*     */ package com.itextpdf.io.font.constants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontStretches
/*     */ {
/*     */   private static final int FWIDTH_ULTRA_CONDENSED = 1;
/*     */   private static final int FWIDTH_EXTRA_CONDENSED = 2;
/*     */   private static final int FWIDTH_CONDENSED = 3;
/*     */   private static final int FWIDTH_SEMI_CONDENSED = 4;
/*     */   private static final int FWIDTH_NORMAL = 5;
/*     */   private static final int FWIDTH_SEMI_EXPANDED = 6;
/*     */   private static final int FWIDTH_EXPANDED = 7;
/*     */   private static final int FWIDTH_EXTRA_EXPANDED = 8;
/*     */   private static final int FWIDTH_ULTRA_EXPANDED = 9;
/*     */   public static final String ULTRA_CONDENSED = "UltraCondensed";
/*     */   public static final String EXTRA_CONDENSED = "ExtraCondensed";
/*     */   public static final String CONDENSED = "Condensed";
/*     */   public static final String SEMI_CONDENSED = "SemiCondensed";
/*     */   public static final String NORMAL = "Normal";
/*     */   public static final String SEMI_EXPANDED = "SemiExpanded";
/*     */   public static final String EXPANDED = "Expanded";
/*     */   public static final String EXTRA_EXPANDED = "ExtraExpanded";
/*     */   public static final String ULTRA_EXPANDED = "UltraExpanded";
/*     */   
/*     */   public static String fromOpenTypeWidthClass(int fontWidth) {
/*  88 */     String fontWidthValue = "Normal";
/*  89 */     switch (fontWidth) {
/*     */       case 1:
/*  91 */         fontWidthValue = "UltraCondensed";
/*     */         break;
/*     */       case 2:
/*  94 */         fontWidthValue = "ExtraCondensed";
/*     */         break;
/*     */       case 3:
/*  97 */         fontWidthValue = "Condensed";
/*     */         break;
/*     */       case 4:
/* 100 */         fontWidthValue = "SemiCondensed";
/*     */         break;
/*     */       case 5:
/* 103 */         fontWidthValue = "Normal";
/*     */         break;
/*     */       case 6:
/* 106 */         fontWidthValue = "SemiExpanded";
/*     */         break;
/*     */       case 7:
/* 109 */         fontWidthValue = "Expanded";
/*     */         break;
/*     */       case 8:
/* 112 */         fontWidthValue = "ExtraExpanded";
/*     */         break;
/*     */       case 9:
/* 115 */         fontWidthValue = "UltraExpanded";
/*     */         break;
/*     */     } 
/* 118 */     return fontWidthValue;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/constants/FontStretches.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */