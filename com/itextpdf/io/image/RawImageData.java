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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RawImageData
/*     */   extends ImageData
/*     */ {
/*     */   public static final int CCITTG4 = 256;
/*     */   public static final int CCITTG3_1D = 257;
/*     */   public static final int CCITTG3_2D = 258;
/*     */   public static final int CCITT_BLACKIS1 = 1;
/*     */   public static final int CCITT_ENCODEDBYTEALIGN = 2;
/*     */   public static final int CCITT_ENDOFLINE = 4;
/*     */   public static final int CCITT_ENDOFBLOCK = 8;
/*     */   protected int typeCcitt;
/*     */   
/*     */   protected RawImageData(URL url, ImageType type) {
/*  95 */     super(url, type);
/*     */   }
/*     */   
/*     */   protected RawImageData(byte[] bytes, ImageType type) {
/*  99 */     super(bytes, type);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRawImage() {
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   public int getTypeCcitt() {
/* 108 */     return this.typeCcitt;
/*     */   }
/*     */   
/*     */   public void setTypeCcitt(int typeCcitt) {
/* 112 */     this.typeCcitt = typeCcitt;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/RawImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */