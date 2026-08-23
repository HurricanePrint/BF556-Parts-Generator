/*     */ package com.itextpdf.io.image;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class Jpeg2000ImageData
/*     */   extends ImageData
/*     */ {
/*     */   protected Parameters parameters;
/*     */   
/*     */   public static class Parameters
/*     */   {
/*     */     public int numOfComps;
/*  58 */     public List<Jpeg2000ImageData.ColorSpecBox> colorSpecBoxes = null;
/*     */     public boolean isJp2 = false;
/*     */     public boolean isJpxBaseline = false;
/*     */     public byte[] bpcBoxData;
/*     */   }
/*     */   
/*     */   public static class ColorSpecBox
/*     */     extends ArrayList<Integer>
/*     */   {
/*     */     private static final long serialVersionUID = -6008490897027025733L;
/*     */     private byte[] colorProfile;
/*     */     
/*     */     public int getMeth() {
/*  71 */       return get(0).intValue();
/*     */     }
/*     */     
/*     */     public int getPrec() {
/*  75 */       return get(1).intValue();
/*     */     }
/*     */     
/*     */     public int getApprox() {
/*  79 */       return get(2).intValue();
/*     */     }
/*     */     
/*     */     public int getEnumCs() {
/*  83 */       return get(3).intValue();
/*     */     }
/*     */     
/*     */     public byte[] getColorProfile() {
/*  87 */       return this.colorProfile;
/*     */     }
/*     */     
/*     */     void setColorProfile(byte[] colorProfile) {
/*  91 */       this.colorProfile = colorProfile;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Jpeg2000ImageData(URL url) {
/*  98 */     super(url, ImageType.JPEG2000);
/*     */   }
/*     */   
/*     */   protected Jpeg2000ImageData(byte[] bytes) {
/* 102 */     super(bytes, ImageType.JPEG2000);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canImageBeInline() {
/* 107 */     Logger logger = LoggerFactory.getLogger(ImageData.class);
/* 108 */     logger.warn("Image cannot be inline if it has JPXDecode filter. It will be added as an ImageXObject");
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public Parameters getParameters() {
/* 113 */     return this.parameters;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/image/Jpeg2000ImageData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */