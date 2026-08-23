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
/*     */ public class PngImageData
/*     */   extends RawImageData
/*     */ {
/*     */   private byte[] colorPalette;
/*     */   private int colorType;
/*  52 */   private float gamma = 1.0F;
/*     */   private PngChromaticities pngChromaticities;
/*     */   
/*     */   protected PngImageData(byte[] bytes) {
/*  56 */     super(bytes, ImageType.PNG);
/*     */   }
/*     */   
/*     */   protected PngImageData(URL url) {
/*  60 */     super(url, ImageType.PNG);
/*     */   }
/*     */   
/*     */   public byte[] getColorPalette() {
/*  64 */     return this.colorPalette;
/*     */   }
/*     */   
/*     */   public void setColorPalette(byte[] colorPalette) {
/*  68 */     this.colorPalette = colorPalette;
/*     */   }
/*     */   
/*     */   public float getGamma() {
/*  72 */     return this.gamma;
/*     */   }
/*     */   
/*     */   public void setGamma(float gamma) {
/*  76 */     this.gamma = gamma;
/*     */   }
/*     */   
/*     */   public boolean isHasCHRM() {
/*  80 */     return (this.pngChromaticities != null);
/*     */   }
/*     */   
/*     */   public PngChromaticities getPngChromaticities() {
/*  84 */     return this.pngChromaticities;
/*     */   }
/*     */   
/*     */   public void setPngChromaticities(PngChromaticities pngChromaticities) {
/*  88 */     this.pngChromaticities = pngChromaticities;
/*     */   }
/*     */   
/*     */   public int getColorType() {
/*  92 */     return this.colorType;
/*     */   }
/*     */   
/*     */   public void setColorType(int colorType) {
/*  96 */     this.colorType = colorType;
/*     */   }
/*     */   
/*     */   public boolean isIndexed() {
/* 100 */     return (this.colorType == 3);
/*     */   }
/*     */   
/*     */   public boolean isGrayscaleImage() {
/* 104 */     return ((this.colorType & 0x2) == 0);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/PngImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */