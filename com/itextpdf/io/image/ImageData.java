/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.colors.IccProfile;
/*     */ import com.itextpdf.io.source.ByteArrayOutputStream;
/*     */ import com.itextpdf.io.source.RandomAccessFileOrArray;
/*     */ import com.itextpdf.io.source.RandomAccessSourceFactory;
/*     */ import com.itextpdf.io.util.StreamUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
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
/*     */ public abstract class ImageData
/*     */ {
/*  62 */   private static long serialId = 0L;
/*     */   
/*  64 */   private static final Object staticLock = new Object();
/*     */   
/*     */   protected URL url;
/*     */   
/*     */   protected int[] transparency;
/*     */   
/*     */   protected ImageType originalType;
/*     */   
/*     */   protected float width;
/*     */   
/*     */   protected float height;
/*     */   
/*     */   protected byte[] data;
/*     */   
/*     */   protected int imageSize;
/*     */   
/*  80 */   protected int bpc = 1;
/*     */ 
/*     */   
/*  83 */   protected int colorSpace = -1;
/*     */   
/*     */   protected float[] decode;
/*     */   
/*     */   protected Map<String, Object> decodeParms;
/*     */   
/*     */   protected boolean inverted = false;
/*     */   
/*     */   protected float rotation;
/*     */   
/*     */   protected IccProfile profile;
/*     */   
/*  95 */   protected int dpiX = 0;
/*     */   
/*  97 */   protected int dpiY = 0;
/*     */   
/*  99 */   protected int colorTransform = 1;
/*     */   
/*     */   protected boolean deflated;
/*     */   
/*     */   protected boolean mask = false;
/*     */   
/*     */   protected ImageData imageMask;
/*     */   
/*     */   protected boolean interpolation;
/*     */   
/* 109 */   protected float XYRatio = 0.0F;
/*     */   
/*     */   protected String filter;
/*     */   
/*     */   protected Map<String, Object> imageAttributes;
/*     */   
/* 115 */   protected Long mySerialId = getSerialId();
/*     */   
/*     */   protected ImageData(URL url, ImageType type) {
/* 118 */     this.url = url;
/* 119 */     this.originalType = type;
/*     */   }
/*     */   
/*     */   protected ImageData(byte[] bytes, ImageType type) {
/* 123 */     this.data = bytes;
/* 124 */     this.originalType = type;
/*     */   }
/*     */   
/*     */   public boolean isRawImage() {
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   public URL getUrl() {
/* 132 */     return this.url;
/*     */   }
/*     */   
/*     */   public void setUrl(URL url) {
/* 136 */     this.url = url;
/*     */   }
/*     */   
/*     */   public int[] getTransparency() {
/* 140 */     return this.transparency;
/*     */   }
/*     */   
/*     */   public void setTransparency(int[] transparency) {
/* 144 */     this.transparency = transparency;
/*     */   }
/*     */   
/*     */   public boolean isInverted() {
/* 148 */     return this.inverted;
/*     */   }
/*     */   
/*     */   public void setInverted(boolean inverted) {
/* 152 */     this.inverted = inverted;
/*     */   }
/*     */   
/*     */   public float getRotation() {
/* 156 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public void setRotation(float rotation) {
/* 160 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public IccProfile getProfile() {
/* 164 */     return this.profile;
/*     */   }
/*     */   
/*     */   public void setProfile(IccProfile profile) {
/* 168 */     this.profile = profile;
/*     */   }
/*     */   
/*     */   public int getDpiX() {
/* 172 */     return this.dpiX;
/*     */   }
/*     */   
/*     */   public int getDpiY() {
/* 176 */     return this.dpiY;
/*     */   }
/*     */   
/*     */   public void setDpi(int dpiX, int dpiY) {
/* 180 */     this.dpiX = dpiX;
/* 181 */     this.dpiY = dpiY;
/*     */   }
/*     */   
/*     */   public int getColorTransform() {
/* 185 */     return this.colorTransform;
/*     */   }
/*     */   
/*     */   public void setColorTransform(int colorTransform) {
/* 189 */     this.colorTransform = colorTransform;
/*     */   }
/*     */   
/*     */   public boolean isDeflated() {
/* 193 */     return this.deflated;
/*     */   }
/*     */   
/*     */   public void setDeflated(boolean deflated) {
/* 197 */     this.deflated = deflated;
/*     */   }
/*     */   
/*     */   public ImageType getOriginalType() {
/* 201 */     return this.originalType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorSpace() {
/* 209 */     return this.colorSpace;
/*     */   }
/*     */   
/*     */   public void setColorSpace(int colorSpace) {
/* 213 */     this.colorSpace = colorSpace;
/*     */   }
/*     */   
/*     */   public byte[] getData() {
/* 217 */     return this.data;
/*     */   }
/*     */   
/*     */   public boolean canBeMask() {
/* 221 */     if (isRawImage() && 
/* 222 */       this.bpc > 255) {
/* 223 */       return true;
/*     */     }
/* 225 */     return (this.colorSpace == 1);
/*     */   }
/*     */   
/*     */   public boolean isMask() {
/* 229 */     return this.mask;
/*     */   }
/*     */   
/*     */   public ImageData getImageMask() {
/* 233 */     return this.imageMask;
/*     */   }
/*     */   
/*     */   public void setImageMask(ImageData imageMask) {
/* 237 */     if (this.mask)
/* 238 */       throw new IOException("Image mask cannot contain another image mask."); 
/* 239 */     if (!imageMask.mask)
/* 240 */       throw new IOException("Image is not a mask. You must call ImageData#makeMask()."); 
/* 241 */     this.imageMask = imageMask;
/*     */   }
/*     */   
/*     */   public boolean isSoftMask() {
/* 245 */     return (this.mask && this.bpc > 1 && this.bpc <= 8);
/*     */   }
/*     */   
/*     */   public void makeMask() {
/* 249 */     if (!canBeMask())
/* 250 */       throw new IOException("This image can not be an image mask."); 
/* 251 */     this.mask = true;
/*     */   }
/*     */   
/*     */   public float getWidth() {
/* 255 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(float width) {
/* 259 */     this.width = width;
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 263 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(float height) {
/* 267 */     this.height = height;
/*     */   }
/*     */   
/*     */   public int getBpc() {
/* 271 */     return this.bpc;
/*     */   }
/*     */   
/*     */   public void setBpc(int bpc) {
/* 275 */     this.bpc = bpc;
/*     */   }
/*     */   
/*     */   public boolean isInterpolation() {
/* 279 */     return this.interpolation;
/*     */   }
/*     */   
/*     */   public void setInterpolation(boolean interpolation) {
/* 283 */     this.interpolation = interpolation;
/*     */   }
/*     */   
/*     */   public float getXYRatio() {
/* 287 */     return this.XYRatio;
/*     */   }
/*     */   
/*     */   public void setXYRatio(float XYRatio) {
/* 291 */     this.XYRatio = XYRatio;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getImageAttributes() {
/* 295 */     return this.imageAttributes;
/*     */   }
/*     */   
/*     */   public void setImageAttributes(Map<String, Object> imageAttributes) {
/* 299 */     this.imageAttributes = imageAttributes;
/*     */   }
/*     */   
/*     */   public String getFilter() {
/* 303 */     return this.filter;
/*     */   }
/*     */   
/*     */   public void setFilter(String filter) {
/* 307 */     this.filter = filter;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getDecodeParms() {
/* 311 */     return this.decodeParms;
/*     */   }
/*     */   
/*     */   public float[] getDecode() {
/* 315 */     return this.decode;
/*     */   }
/*     */   
/*     */   public void setDecode(float[] decode) {
/* 319 */     this.decode = decode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canImageBeInline() {
/* 327 */     Logger logger = LoggerFactory.getLogger(ImageData.class);
/* 328 */     if (this.imageSize > 4096) {
/* 329 */       logger.warn("Inline image size cannot be more than 4KB. It will be added as an ImageXObject");
/* 330 */       return false;
/*     */     } 
/* 332 */     if (this.imageMask != null) {
/* 333 */       logger.warn("Image cannot be inline if it has a Mask");
/* 334 */       return false;
/*     */     } 
/* 336 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void loadData() throws IOException {
/* 345 */     RandomAccessFileOrArray raf = new RandomAccessFileOrArray((new RandomAccessSourceFactory()).createSource(this.url));
/* 346 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 347 */     StreamUtil.transferBytes(raf, (OutputStream)stream);
/* 348 */     raf.close();
/* 349 */     this.data = stream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Long getSerialId() {
/* 355 */     synchronized (staticLock) {
/* 356 */       return Long.valueOf(++serialId);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/ImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */