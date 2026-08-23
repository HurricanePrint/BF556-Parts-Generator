/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.constants.StandardFonts;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import com.itextpdf.io.util.ResourceUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Type1Parser
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8484541242371901414L;
/*     */   private static final String AFM_HEADER = "StartFontMetrics";
/*     */   private String afmPath;
/*     */   private String pfbPath;
/*     */   private byte[] pfbData;
/*     */   private byte[] afmData;
/*     */   private boolean isBuiltInFont;
/*  70 */   private RandomAccessSourceFactory sourceFactory = new RandomAccessSourceFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type1Parser(String metricsPath, String binaryPath, byte[] afm, byte[] pfb) {
/*  79 */     this.afmData = afm;
/*  80 */     this.pfbData = pfb;
/*  81 */     this.afmPath = metricsPath;
/*  82 */     this.pfbPath = binaryPath;
/*     */   }
/*     */   
/*     */   public RandomAccessFileOrArray getMetricsFile() throws IOException {
/*  86 */     this.isBuiltInFont = false;
/*  87 */     if (StandardFonts.isStandardFont(this.afmPath)) {
/*  88 */       this.isBuiltInFont = true;
/*  89 */       byte[] buf = new byte[1024];
/*  90 */       InputStream resource = null;
/*     */       try {
/*  92 */         String resourcePath = "com/itextpdf/io/font/afm/" + this.afmPath + ".afm";
/*  93 */         resource = ResourceUtil.getResourceStream(resourcePath);
/*  94 */         if (resource == null) {
/*  95 */           throw (new IOException("{0} was not found as resource.")).setMessageParams(new Object[] { resourcePath });
/*     */         }
/*  97 */         ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*     */         int read;
/*  99 */         while ((read = resource.read(buf)) >= 0) {
/* 100 */           stream.write(buf, 0, read);
/*     */         }
/* 102 */         buf = stream.toByteArray();
/*     */       } finally {
/* 104 */         if (resource != null) {
/*     */           try {
/* 106 */             resource.close();
/* 107 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/* 110 */       return new RandomAccessFileOrArray(this.sourceFactory.createSource(buf));
/* 111 */     }  if (this.afmPath != null) {
/* 112 */       if (this.afmPath.toLowerCase().endsWith(".afm"))
/* 113 */         return new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.afmPath)); 
/* 114 */       if (this.afmPath.toLowerCase().endsWith(".pfm")) {
/* 115 */         ByteArrayOutputStream ba = new ByteArrayOutputStream();
/* 116 */         RandomAccessFileOrArray rf = new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.afmPath));
/* 117 */         Pfm2afm.convert(rf, ba);
/* 118 */         rf.close();
/* 119 */         return new RandomAccessFileOrArray(this.sourceFactory.createSource(ba.toByteArray()));
/*     */       } 
/* 121 */       throw (new IOException("{0} is not an afm or pfm font file.")).setMessageParams(new Object[] { this.afmPath });
/*     */     } 
/* 123 */     if (this.afmData != null) {
/* 124 */       RandomAccessFileOrArray rf = new RandomAccessFileOrArray(this.sourceFactory.createSource(this.afmData));
/* 125 */       if (isAfmFile(rf)) {
/* 126 */         return rf;
/*     */       }
/* 128 */       ByteArrayOutputStream ba = new ByteArrayOutputStream();
/*     */       try {
/* 130 */         Pfm2afm.convert(rf, ba);
/* 131 */       } catch (Exception ignored) {
/* 132 */         throw new IOException("Invalid afm or pfm font file.");
/*     */       } finally {
/* 134 */         rf.close();
/*     */       } 
/* 136 */       return new RandomAccessFileOrArray(this.sourceFactory.createSource(ba.toByteArray()));
/*     */     } 
/*     */     
/* 139 */     throw new IOException("Invalid afm or pfm font file.");
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomAccessFileOrArray getPostscriptBinary() throws IOException {
/* 144 */     if (this.pfbData != null)
/* 145 */       return new RandomAccessFileOrArray(this.sourceFactory.createSource(this.pfbData)); 
/* 146 */     if (this.pfbPath != null && this.pfbPath.toLowerCase().endsWith(".pfb")) {
/* 147 */       return new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.pfbPath));
/*     */     }
/* 149 */     this.pfbPath = this.afmPath.substring(0, this.afmPath.length() - 3) + "pfb";
/* 150 */     return new RandomAccessFileOrArray(this.sourceFactory.createBestSource(this.pfbPath));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltInFont() {
/* 155 */     return this.isBuiltInFont;
/*     */   }
/*     */   
/*     */   public String getAfmPath() {
/* 159 */     return this.afmPath;
/*     */   }
/*     */   
/*     */   private boolean isAfmFile(RandomAccessFileOrArray raf) throws IOException {
/* 163 */     StringBuilder builder = new StringBuilder("StartFontMetrics".length());
/* 164 */     for (int i = 0; i < "StartFontMetrics".length(); i++) {
/*     */       try {
/* 166 */         builder.append((char)raf.readByte());
/* 167 */       } catch (EOFException e) {
/* 168 */         raf.seek(0L);
/* 169 */         return false;
/*     */       } 
/*     */     } 
/* 172 */     raf.seek(0L);
/* 173 */     return "StartFontMetrics".equals(builder.toString());
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/Type1Parser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */