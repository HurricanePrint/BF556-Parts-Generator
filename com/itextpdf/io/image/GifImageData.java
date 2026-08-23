/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import com.itextpdf.io.util.UrlUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GifImageData
/*     */ {
/*     */   private float logicalHeight;
/*     */   private float logicalWidth;
/*  59 */   private List<ImageData> frames = new ArrayList<>();
/*     */   private byte[] data;
/*     */   private URL url;
/*     */   
/*     */   protected GifImageData(URL url) {
/*  64 */     this.url = url;
/*     */   }
/*     */   
/*     */   protected GifImageData(byte[] data) {
/*  68 */     this.data = data;
/*     */   }
/*     */   
/*     */   public float getLogicalHeight() {
/*  72 */     return this.logicalHeight;
/*     */   }
/*     */   
/*     */   public void setLogicalHeight(float logicalHeight) {
/*  76 */     this.logicalHeight = logicalHeight;
/*     */   }
/*     */   
/*     */   public float getLogicalWidth() {
/*  80 */     return this.logicalWidth;
/*     */   }
/*     */   
/*     */   public void setLogicalWidth(float logicalWidth) {
/*  84 */     this.logicalWidth = logicalWidth;
/*     */   }
/*     */   
/*     */   public List<ImageData> getFrames() {
/*  88 */     return this.frames;
/*     */   }
/*     */   
/*     */   protected byte[] getData() {
/*  92 */     return this.data;
/*     */   }
/*     */   
/*     */   protected URL getUrl() {
/*  96 */     return this.url;
/*     */   }
/*     */   
/*     */   protected void addFrame(ImageData frame) {
/* 100 */     this.frames.add(frame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void loadData() throws IOException {
/* 109 */     InputStream input = null;
/*     */     try {
/* 111 */       input = UrlUtil.openStream(this.url);
/* 112 */       ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 113 */       StreamUtil.transferBytes(UrlUtil.openStream(this.url), (OutputStream)stream);
/* 114 */       this.data = stream.toByteArray();
/*     */     } finally {
/* 116 */       if (input != null)
/* 117 */         input.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/GifImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */