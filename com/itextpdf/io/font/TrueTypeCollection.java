/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import com.itextpdf.io.util.FileUtil;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrueTypeCollection
/*     */ {
/*     */   protected RandomAccessFileOrArray raf;
/*  57 */   private int TTCSize = 0;
/*     */ 
/*     */   
/*     */   private String ttcPath;
/*     */ 
/*     */   
/*     */   private byte[] ttc;
/*     */   
/*     */   private boolean cached = true;
/*     */ 
/*     */   
/*     */   public TrueTypeCollection(byte[] ttc) throws IOException {
/*  69 */     this.raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(ttc));
/*  70 */     this.ttc = ttc;
/*  71 */     initFontSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrueTypeCollection(String ttcPath) throws IOException {
/*  81 */     if (!FileUtil.fileExists(ttcPath)) {
/*  82 */       throw (new IOException("Font file {0} not found.")).setMessageParams(new Object[] { ttcPath });
/*     */     }
/*  84 */     this.raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createBestSource(ttcPath));
/*  85 */     this.ttcPath = ttcPath;
/*  86 */     initFontSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProgram getFontByTccIndex(int ttcIndex) throws IOException {
/*  97 */     if (ttcIndex > this.TTCSize - 1) {
/*  98 */       throw new IOException("TTC index doesn't exist in this TTC file.");
/*     */     }
/*     */     
/* 101 */     if (this.ttcPath != null) {
/* 102 */       return FontProgramFactory.createFont(this.ttcPath, ttcIndex, this.cached);
/*     */     }
/* 104 */     return FontProgramFactory.createFont(this.ttc, ttcIndex, this.cached);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTTCSize() {
/* 114 */     return this.TTCSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCached() {
/* 123 */     return this.cached;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCached(boolean cached) {
/* 132 */     this.cached = cached;
/*     */   }
/*     */   
/*     */   private void initFontSize() throws IOException {
/* 136 */     String mainTag = this.raf.readString(4, "Cp1252");
/* 137 */     if (!mainTag.equals("ttcf")) {
/* 138 */       throw new IOException("{0} is not a valid TTC file.");
/*     */     }
/* 140 */     this.raf.skipBytes(4);
/* 141 */     this.TTCSize = this.raf.readInt();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/TrueTypeCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */