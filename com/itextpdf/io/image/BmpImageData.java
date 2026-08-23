/*     */ package com.itextpdf.io.image;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BmpImageData
/*     */   extends RawImageData
/*     */ {
/*     */   private int size;
/*     */   private boolean noHeader;
/*     */   
/*     */   protected BmpImageData(URL url, boolean noHeader) {
/*  59 */     super(url, ImageType.BMP);
/*  60 */     this.noHeader = noHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected BmpImageData(URL url, boolean noHeader, int size) {
/*  72 */     this(url, noHeader);
/*  73 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BmpImageData(byte[] bytes, boolean noHeader) {
/*  82 */     super(bytes, ImageType.BMP);
/*  83 */     this.noHeader = noHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected BmpImageData(byte[] bytes, boolean noHeader, int size) {
/*  95 */     this(bytes, noHeader);
/*  96 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getSize() {
/* 105 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNoHeader() {
/* 112 */     return this.noHeader;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/BmpImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */