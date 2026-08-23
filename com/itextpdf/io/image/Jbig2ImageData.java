/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.codec.Jbig2SegmentReader;
/*     */ import com.itextpdf.io.source.IRandomAccessSource;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import java.net.URL;
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
/*     */ public class Jbig2ImageData
/*     */   extends ImageData
/*     */ {
/*     */   private int page;
/*     */   
/*     */   protected Jbig2ImageData(URL url, int page) {
/*  63 */     super(url, ImageType.JBIG2);
/*  64 */     this.page = page;
/*     */   }
/*     */   
/*     */   protected Jbig2ImageData(byte[] bytes, int page) {
/*  68 */     super(bytes, ImageType.JBIG2);
/*  69 */     this.page = page;
/*     */   }
/*     */   
/*     */   public int getPage() {
/*  73 */     return this.page;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumberOfPages(byte[] bytes) {
/*  82 */     IRandomAccessSource ras = (new RandomAccessSourceFactory()).createSource(bytes);
/*  83 */     return getNumberOfPages(new RandomAccessFileOrArray(ras));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumberOfPages(RandomAccessFileOrArray raf) {
/*     */     try {
/*  93 */       Jbig2SegmentReader sr = new Jbig2SegmentReader(raf);
/*  94 */       sr.read();
/*  95 */       return sr.numberOfPages();
/*  96 */     } catch (Exception e) {
/*  97 */       throw new IOException("JBIG2 image exception.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canImageBeInline() {
/* 103 */     Logger logger = LoggerFactory.getLogger(ImageData.class);
/* 104 */     logger.warn("Image cannot be inline if it has JBIG2Decode filter. It will be added as an ImageXObject");
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/Jbig2ImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */