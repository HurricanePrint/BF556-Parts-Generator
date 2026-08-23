/*     */ package com.itextpdf.styledxmlparser.css.media;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MediaDeviceDescription
/*     */ {
/*  50 */   private static final MediaDeviceDescription DEFAULT = createDefault();
/*     */ 
/*     */   
/*     */   private String type;
/*     */ 
/*     */   
/*  56 */   private int bitsPerComponent = 0;
/*     */ 
/*     */   
/*  59 */   private int colorIndex = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private float width;
/*     */ 
/*     */ 
/*     */   
/*     */   private float height;
/*     */ 
/*     */   
/*     */   private boolean isGrid;
/*     */ 
/*     */   
/*     */   private String scan;
/*     */ 
/*     */   
/*     */   private String orientation;
/*     */ 
/*     */   
/*     */   private int monochrome;
/*     */ 
/*     */   
/*     */   private float resolution;
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription(String type) {
/*  87 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription(String type, float width, float height) {
/*  98 */     this(type);
/*  99 */     this.width = width;
/* 100 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MediaDeviceDescription createDefault() {
/* 109 */     return new MediaDeviceDescription(MediaType.ALL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MediaDeviceDescription getDefault() {
/* 119 */     return DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 128 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBitsPerComponent() {
/* 137 */     return this.bitsPerComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setBitsPerComponent(int bitsPerComponent) {
/* 147 */     this.bitsPerComponent = bitsPerComponent;
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorIndex() {
/* 157 */     return this.colorIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setColorIndex(int colorIndex) {
/* 167 */     this.colorIndex = colorIndex;
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 177 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setWidth(float width) {
/* 187 */     this.width = width;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 197 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setHeight(float height) {
/* 207 */     this.height = height;
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGrid() {
/* 217 */     return this.isGrid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setGrid(boolean grid) {
/* 227 */     this.isGrid = grid;
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getScan() {
/* 237 */     return this.scan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setScan(String scan) {
/* 247 */     this.scan = scan;
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOrientation() {
/* 257 */     return this.orientation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setOrientation(String orientation) {
/* 267 */     this.orientation = orientation;
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonochrome() {
/* 277 */     return this.monochrome;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setMonochrome(int monochrome) {
/* 287 */     this.monochrome = monochrome;
/* 288 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getResolution() {
/* 297 */     return this.resolution;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription setResolution(float resolution) {
/* 307 */     this.resolution = resolution;
/* 308 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/media/MediaDeviceDescription.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */