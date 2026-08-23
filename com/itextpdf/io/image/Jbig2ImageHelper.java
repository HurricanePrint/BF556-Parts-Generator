/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.codec.Jbig2SegmentReader;
/*     */ import com.itextpdf.io.source.IRandomAccessSource;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Jbig2ImageHelper
/*     */ {
/*     */   private byte[] globals;
/*     */   
/*     */   public static byte[] getGlobalSegment(RandomAccessFileOrArray ra) {
/*     */     try {
/*  67 */       Jbig2SegmentReader sr = new Jbig2SegmentReader(ra);
/*  68 */       sr.read();
/*  69 */       return sr.getGlobal(true);
/*  70 */     } catch (Exception e) {
/*  71 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void processImage(ImageData jbig2) {
/*  76 */     if (jbig2.getOriginalType() != ImageType.JBIG2)
/*  77 */       throw new IllegalArgumentException("JBIG2 image expected"); 
/*  78 */     Jbig2ImageData image = (Jbig2ImageData)jbig2;
/*     */     
/*     */     try {
/*  81 */       if (image.getData() == null) {
/*  82 */         image.loadData();
/*     */       }
/*  84 */       IRandomAccessSource ras = (new RandomAccessSourceFactory()).createSource(image.getData());
/*  85 */       RandomAccessFileOrArray raf = new RandomAccessFileOrArray(ras);
/*  86 */       Jbig2SegmentReader sr = new Jbig2SegmentReader(raf);
/*  87 */       sr.read();
/*  88 */       Jbig2SegmentReader.Jbig2Page p = sr.getPage(image.getPage());
/*  89 */       raf.close();
/*     */       
/*  91 */       image.setHeight(p.pageBitmapHeight);
/*  92 */       image.setWidth(p.pageBitmapWidth);
/*  93 */       image.setBpc(1);
/*  94 */       image.setColorSpace(1);
/*     */       
/*  96 */       byte[] globals = sr.getGlobal(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       if (globals != null) {
/* 103 */         Map<String, Object> decodeParms = new HashMap<>();
/*     */ 
/*     */         
/* 106 */         decodeParms.put("JBIG2Globals", globals);
/* 107 */         image.decodeParms = decodeParms;
/*     */       } 
/*     */       
/* 110 */       image.setFilter("JBIG2Decode");
/* 111 */       image.setColorSpace(1);
/* 112 */       image.setBpc(1);
/* 113 */       image.data = p.getData(true);
/* 114 */     } catch (IOException e) {
/* 115 */       throw new IOException("JBIG2 image exception.", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/Jbig2ImageHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */