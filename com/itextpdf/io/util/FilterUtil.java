/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FilterUtil
/*     */ {
/*  64 */   private static final Logger LOGGER = LoggerFactory.getLogger(FilterUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] flateDecode(byte[] input, boolean strict) {
/*  78 */     ByteArrayInputStream stream = new ByteArrayInputStream(input);
/*  79 */     InflaterInputStream zip = new InflaterInputStream(stream);
/*  80 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  81 */     byte[] b = new byte[strict ? 4092 : 1];
/*     */     try {
/*     */       int n;
/*  84 */       while ((n = zip.read(b)) >= 0) {
/*  85 */         output.write(b, 0, n);
/*     */       }
/*  87 */       zip.close();
/*  88 */       output.close();
/*  89 */       return output.toByteArray();
/*  90 */     } catch (Exception e) {
/*  91 */       return strict ? null : output.toByteArray();
/*     */     } finally {
/*     */       try {
/*  94 */         zip.close();
/*  95 */         output.close();
/*  96 */       } catch (Exception e) {
/*     */         
/*  98 */         LOGGER.error(e.getMessage(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] flateDecode(byte[] input) {
/* 110 */     byte[] b = flateDecode(input, true);
/* 111 */     if (b == null)
/* 112 */       return flateDecode(input, false); 
/* 113 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void inflateData(byte[] deflated, byte[] inflated) {
/* 123 */     Inflater inflater = new Inflater();
/* 124 */     inflater.setInput(deflated);
/*     */     try {
/* 126 */       inflater.inflate(inflated);
/* 127 */     } catch (DataFormatException dfe) {
/* 128 */       throw new IOException("Cannot inflate TIFF image.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static InputStream getInflaterInputStream(InputStream input) {
/* 133 */     return new InflaterInputStream(input, new Inflater());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/FilterUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */