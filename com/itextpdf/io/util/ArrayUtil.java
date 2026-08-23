/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArrayUtil
/*     */ {
/*     */   public static byte[] shortenArray(byte[] src, int length) {
/*  58 */     if (length < src.length) {
/*  59 */       byte[] shortened = new byte[length];
/*  60 */       System.arraycopy(src, 0, shortened, 0, length);
/*  61 */       return shortened;
/*     */     } 
/*  63 */     return src;
/*     */   }
/*     */   
/*     */   public static int[] toIntArray(Collection<Integer> collection) {
/*  67 */     int[] array = new int[collection.size()];
/*  68 */     int k = 0;
/*  69 */     for (Integer key : collection) {
/*  70 */       array[k++] = key.intValue();
/*     */     }
/*  72 */     return array;
/*     */   }
/*     */   
/*     */   public static int hashCode(byte[] a) {
/*  76 */     if (a == null) {
/*  77 */       return 0;
/*     */     }
/*  79 */     int result = 1;
/*  80 */     for (byte element : a) {
/*  81 */       result = 31 * result + element;
/*     */     }
/*  83 */     return result;
/*     */   }
/*     */   
/*     */   public static int[] fillWithValue(int[] a, int value) {
/*  87 */     for (int i = 0; i < a.length; i++) {
/*  88 */       a[i] = value;
/*     */     }
/*  90 */     return a;
/*     */   }
/*     */   
/*     */   public static float[] fillWithValue(float[] a, float value) {
/*  94 */     for (int i = 0; i < a.length; i++) {
/*  95 */       a[i] = value;
/*     */     }
/*  97 */     return a;
/*     */   }
/*     */   
/*     */   public static <T> void fillWithValue(T[] a, T value) {
/* 101 */     for (int i = 0; i < a.length; i++) {
/* 102 */       a[i] = value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static int[] cloneArray(int[] src) {
/* 107 */     return (int[])src.clone();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/ArrayUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */