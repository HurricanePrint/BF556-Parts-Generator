/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ResourceUtil
/*     */ {
/*     */   public static InputStream getResourceStream(String key) {
/*  64 */     return getResourceStream(key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceStream(String key, ClassLoader loader) {
/*  75 */     if (key.startsWith("/")) {
/*  76 */       key = key.substring(1);
/*     */     }
/*  78 */     InputStream stream = null;
/*  79 */     if (loader != null) {
/*  80 */       stream = loader.getResourceAsStream(key);
/*  81 */       if (stream != null) {
/*  82 */         return stream;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/*  87 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*  88 */       if (contextClassLoader != null) {
/*  89 */         stream = contextClassLoader.getResourceAsStream(key);
/*     */       }
/*  91 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */     
/*  94 */     if (stream == null) {
/*  95 */       stream = ResourceUtil.class.getResourceAsStream("/" + key);
/*     */     }
/*  97 */     if (stream == null) {
/*  98 */       stream = ClassLoader.getSystemResourceAsStream(key);
/*     */     }
/* 100 */     return stream;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/ResourceUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */