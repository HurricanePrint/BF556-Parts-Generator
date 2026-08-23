/*     */ package com.itextpdf.styledxmlparser.jsoup.helper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Validate
/*     */ {
/*     */   public static void notNull(Object obj) {
/*  57 */     if (obj == null) {
/*  58 */       throw new IllegalArgumentException("Object must not be null");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notNull(Object obj, String msg) {
/*  67 */     if (obj == null) {
/*  68 */       throw new IllegalArgumentException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void isTrue(boolean val) {
/*  76 */     if (!val) {
/*  77 */       throw new IllegalArgumentException("Must be true");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void isTrue(boolean val, String msg) {
/*  86 */     if (!val) {
/*  87 */       throw new IllegalArgumentException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void isFalse(boolean val) {
/*  95 */     if (val) {
/*  96 */       throw new IllegalArgumentException("Must be false");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void isFalse(boolean val, String msg) {
/* 105 */     if (val) {
/* 106 */       throw new IllegalArgumentException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void noNullElements(Object[] objects) {
/* 114 */     noNullElements(objects, "Array must not contain any null objects");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void noNullElements(Object[] objects, String msg) {
/* 123 */     for (Object obj : objects) {
/* 124 */       if (obj == null) {
/* 125 */         throw new IllegalArgumentException(msg);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notEmpty(String string) {
/* 133 */     if (string == null || string.length() == 0) {
/* 134 */       throw new IllegalArgumentException("String must not be empty");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notEmpty(String string, String msg) {
/* 143 */     if (string == null || string.length() == 0) {
/* 144 */       throw new IllegalArgumentException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fail(String msg) {
/* 152 */     throw new IllegalArgumentException(msg);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/helper/Validate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */