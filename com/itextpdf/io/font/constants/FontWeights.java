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
/*     */ public final class FontWeights
/*     */ {
/*     */   public static final int THIN = 100;
/*     */   public static final int EXTRA_LIGHT = 200;
/*     */   public static final int LIGHT = 300;
/*     */   public static final int NORMAL = 400;
/*     */   public static final int MEDIUM = 500;
/*     */   public static final int SEMI_BOLD = 600;
/*     */   public static final int BOLD = 700;
/*     */   public static final int EXTRA_BOLD = 800;
/*     */   public static final int BLACK = 900;
/*     */   
/*     */   public static int fromType1FontWeight(String weight) {
/*  78 */     int fontWeight = 400;
/*  79 */     switch (weight.toLowerCase()) {
/*     */       case "ultralight":
/*  81 */         fontWeight = 100;
/*     */         break;
/*     */       case "thin":
/*     */       case "extralight":
/*  85 */         fontWeight = 200;
/*     */         break;
/*     */       case "light":
/*  88 */         fontWeight = 300;
/*     */         break;
/*     */       case "book":
/*     */       case "regular":
/*     */       case "normal":
/*  93 */         fontWeight = 400;
/*     */         break;
/*     */       case "medium":
/*  96 */         fontWeight = 500;
/*     */         break;
/*     */       case "demibold":
/*     */       case "semibold":
/* 100 */         fontWeight = 600;
/*     */         break;
/*     */       case "bold":
/* 103 */         fontWeight = 700;
/*     */         break;
/*     */       case "extrabold":
/*     */       case "ultrabold":
/* 107 */         fontWeight = 800;
/*     */         break;
/*     */       case "heavy":
/*     */       case "black":
/*     */       case "ultra":
/*     */       case "ultrablack":
/* 113 */         fontWeight = 900;
/*     */         break;
/*     */       case "fat":
/*     */       case "extrablack":
/* 117 */         fontWeight = 900;
/*     */         break;
/*     */     } 
/* 120 */     return fontWeight;
/*     */   }
/*     */   
/*     */   public static int normalizeFontWeight(int fontWeight) {
/* 124 */     fontWeight = fontWeight / 100 * 100;
/* 125 */     if (fontWeight < 100) return 100; 
/* 126 */     if (fontWeight > 900) return 900; 
/* 127 */     return fontWeight;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/constants/FontWeights.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */