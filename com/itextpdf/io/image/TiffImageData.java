/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.codec.TIFFDirectory;
/*     */ import com.itextpdf.io.source.IRandomAccessSource;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TiffImageData
/*     */   extends RawImageData
/*     */ {
/*     */   private boolean recoverFromImageError;
/*     */   private int page;
/*     */   private boolean direct;
/*     */   
/*     */   protected TiffImageData(URL url, boolean recoverFromImageError, int page, boolean direct) {
/*  61 */     super(url, ImageType.TIFF);
/*  62 */     this.recoverFromImageError = recoverFromImageError;
/*  63 */     this.page = page;
/*  64 */     this.direct = direct;
/*     */   }
/*     */   
/*     */   protected TiffImageData(byte[] bytes, boolean recoverFromImageError, int page, boolean direct) {
/*  68 */     super(bytes, ImageType.TIFF);
/*  69 */     this.recoverFromImageError = recoverFromImageError;
/*  70 */     this.page = page;
/*  71 */     this.direct = direct;
/*     */   }
/*     */   
/*     */   private static ImageData getImage(URL url, boolean recoverFromImageError, int page, boolean direct) {
/*  75 */     return new TiffImageData(url, recoverFromImageError, page, direct);
/*     */   }
/*     */   
/*     */   private static ImageData getImage(byte[] bytes, boolean recoverFromImageError, int page, boolean direct) {
/*  79 */     return new TiffImageData(bytes, recoverFromImageError, page, direct);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumberOfPages(RandomAccessFileOrArray raf) {
/*     */     try {
/*  89 */       return TIFFDirectory.getNumDirectories(raf);
/*  90 */     } catch (Exception e) {
/*  91 */       throw new IOException("TIFF image exception.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumberOfPages(byte[] bytes) {
/* 100 */     IRandomAccessSource ras = (new RandomAccessSourceFactory()).createSource(bytes);
/* 101 */     return getNumberOfPages(new RandomAccessFileOrArray(ras));
/*     */   }
/*     */   
/*     */   public boolean isRecoverFromImageError() {
/* 105 */     return this.recoverFromImageError;
/*     */   }
/*     */   
/*     */   public int getPage() {
/* 109 */     return this.page;
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 113 */     return this.direct;
/*     */   }
/*     */   
/*     */   public void setOriginalType(ImageType originalType) {
/* 117 */     this.originalType = originalType;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/TiffImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */