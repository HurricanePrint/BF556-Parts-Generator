/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.util.UrlUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ImageTypeDetector
/*     */ {
/*  37 */   private static final byte[] gif = new byte[] { 71, 73, 70 };
/*  38 */   private static final byte[] jpeg = new byte[] { -1, -40 };
/*  39 */   private static final byte[] jpeg2000_1 = new byte[] { 0, 0, 0, 12 };
/*  40 */   private static final byte[] jpeg2000_2 = new byte[] { -1, 79, -1, 81 };
/*  41 */   private static final byte[] png = new byte[] { -119, 80, 78, 71 };
/*  42 */   private static final byte[] wmf = new byte[] { -41, -51 };
/*  43 */   private static final byte[] bmp = new byte[] { 66, 77 };
/*  44 */   private static final byte[] tiff_1 = new byte[] { 77, 77, 0, 42 };
/*  45 */   private static final byte[] tiff_2 = new byte[] { 73, 73, 42, 0 };
/*  46 */   private static final byte[] jbig2 = new byte[] { -105, 74, 66, 50, 13, 10, 26, 10 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageType detectImageType(byte[] source) {
/*  57 */     byte[] header = readImageType(source);
/*  58 */     return detectImageTypeByHeader(header);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageType detectImageType(URL source) {
/*  67 */     byte[] header = readImageType(source);
/*  68 */     return detectImageTypeByHeader(header);
/*     */   }
/*     */   
/*     */   private static ImageType detectImageTypeByHeader(byte[] header) {
/*  72 */     if (imageTypeIs(header, gif))
/*  73 */       return ImageType.GIF; 
/*  74 */     if (imageTypeIs(header, jpeg))
/*  75 */       return ImageType.JPEG; 
/*  76 */     if (imageTypeIs(header, jpeg2000_1) || imageTypeIs(header, jpeg2000_2))
/*  77 */       return ImageType.JPEG2000; 
/*  78 */     if (imageTypeIs(header, png))
/*  79 */       return ImageType.PNG; 
/*  80 */     if (imageTypeIs(header, bmp))
/*  81 */       return ImageType.BMP; 
/*  82 */     if (imageTypeIs(header, tiff_1) || imageTypeIs(header, tiff_2))
/*  83 */       return ImageType.TIFF; 
/*  84 */     if (imageTypeIs(header, jbig2))
/*  85 */       return ImageType.JBIG2; 
/*  86 */     if (imageTypeIs(header, wmf)) {
/*  87 */       return ImageType.WMF;
/*     */     }
/*  89 */     return ImageType.NONE;
/*     */   }
/*     */   
/*     */   private static boolean imageTypeIs(byte[] imageType, byte[] compareWith) {
/*  93 */     for (int i = 0; i < compareWith.length; i++) {
/*  94 */       if (imageType[i] != compareWith[i])
/*  95 */         return false; 
/*     */     } 
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   private static byte[] readImageType(URL source) {
/* 101 */     try (InputStream stream = UrlUtil.openStream(source)) {
/* 102 */       byte[] bytes = new byte[8];
/* 103 */       stream.read(bytes);
/* 104 */       return bytes;
/* 105 */     } catch (IOException e) {
/* 106 */       throw new IOException("I/O exception.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte[] readImageType(byte[] source) {
/*     */     try {
/* 112 */       InputStream stream = new ByteArrayInputStream(source);
/* 113 */       byte[] bytes = new byte[8];
/* 114 */       stream.read(bytes);
/* 115 */       return bytes;
/* 116 */     } catch (IOException e) {
/* 117 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/ImageTypeDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */