/*     */ package com.itextpdf.kernel.pdf.canvas.wmf;
/*     */ 
/*     */ import com.itextpdf.io.image.ImageData;
/*     */ import com.itextpdf.io.image.ImageType;
/*     */ import com.itextpdf.io.util.UrlUtil;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WmfImageData
/*     */   extends ImageData
/*     */ {
/*  62 */   private static final byte[] wmf = new byte[] { -41, -51 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WmfImageData(String fileName) throws MalformedURLException {
/*  71 */     this(UrlUtil.toURL(fileName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WmfImageData(URL url) {
/*  80 */     super(url, ImageType.WMF);
/*  81 */     byte[] imageType = readImageType(url);
/*  82 */     if (!imageTypeIs(imageType, wmf)) {
/*  83 */       throw new PdfException("Not a WMF image.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WmfImageData(byte[] bytes) {
/*  93 */     super(bytes, ImageType.WMF);
/*  94 */     byte[] imageType = readImageType(bytes);
/*  95 */     if (!imageTypeIs(imageType, wmf)) {
/*  96 */       throw new PdfException("Not a WMF image.");
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean imageTypeIs(byte[] imageType, byte[] compareWith) {
/* 101 */     for (int i = 0; i < compareWith.length; i++) {
/* 102 */       if (imageType[i] != compareWith[i])
/* 103 */         return false; 
/*     */     } 
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   private static byte[] readImageType(URL source) {
/* 109 */     InputStream is = null;
/*     */     try {
/* 111 */       is = source.openStream();
/* 112 */       byte[] bytes = new byte[8];
/* 113 */       is.read(bytes);
/* 114 */       return bytes;
/* 115 */     } catch (IOException e) {
/* 116 */       throw new PdfException("I/O exception.", e);
/*     */     } finally {
/* 118 */       if (is != null) {
/*     */         try {
/* 120 */           is.close();
/* 121 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readImageType(byte[] bytes) {
/* 129 */     return Arrays.copyOfRange(bytes, 0, 8);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/wmf/WmfImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */