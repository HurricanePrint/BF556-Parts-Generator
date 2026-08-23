/*     */ package com.itextpdf.styledxmlparser.css.media;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MediaExpression
/*     */ {
/*     */   private static final float DEFAULT_FONT_SIZE = 12.0F;
/*     */   private boolean minPrefix;
/*     */   private boolean maxPrefix;
/*     */   private String feature;
/*     */   private String value;
/*     */   
/*     */   MediaExpression(String feature, String value) {
/*  85 */     this.feature = feature.trim().toLowerCase();
/*  86 */     if (value != null) {
/*  87 */       this.value = value.trim().toLowerCase();
/*     */     }
/*     */     
/*  90 */     String minPref = "min-";
/*  91 */     String maxPref = "max-";
/*  92 */     this.minPrefix = feature.startsWith(minPref);
/*  93 */     if (this.minPrefix) {
/*  94 */       this.feature = feature.substring(minPref.length());
/*     */     }
/*  96 */     this.maxPrefix = feature.startsWith(maxPref);
/*  97 */     if (this.maxPrefix) {
/*  98 */       this.feature = feature.substring(maxPref.length());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(MediaDeviceDescription deviceDescription) {
/*     */     Integer integer2;
/*     */     int[] aspectRatio;
/*     */     Integer integer1;
/*     */     float val;
/* 109 */     switch (this.feature) {
/*     */       case "color":
/* 111 */         integer2 = CssUtils.parseInteger(this.value);
/* 112 */         if (this.minPrefix)
/* 113 */           return (integer2 != null && deviceDescription.getBitsPerComponent() >= integer2.intValue()); 
/* 114 */         if (this.maxPrefix) {
/* 115 */           return (integer2 != null && deviceDescription.getBitsPerComponent() <= integer2.intValue());
/*     */         }
/* 117 */         return (integer2 == null) ? ((deviceDescription.getBitsPerComponent() != 0)) : ((integer2.intValue() == deviceDescription.getBitsPerComponent()));
/*     */ 
/*     */       
/*     */       case "color-index":
/* 121 */         integer2 = CssUtils.parseInteger(this.value);
/* 122 */         if (this.minPrefix)
/* 123 */           return (integer2 != null && deviceDescription.getColorIndex() >= integer2.intValue()); 
/* 124 */         if (this.maxPrefix) {
/* 125 */           return (integer2 != null && deviceDescription.getColorIndex() <= integer2.intValue());
/*     */         }
/* 127 */         return (integer2 == null) ? ((deviceDescription.getColorIndex() != 0)) : ((integer2.intValue() == deviceDescription.getColorIndex()));
/*     */ 
/*     */       
/*     */       case "aspect-ratio":
/* 131 */         aspectRatio = CssUtils.parseAspectRatio(this.value);
/* 132 */         if (this.minPrefix)
/* 133 */           return (aspectRatio != null && aspectRatio[0] * deviceDescription.getHeight() >= aspectRatio[1] * deviceDescription.getWidth()); 
/* 134 */         if (this.maxPrefix) {
/* 135 */           return (aspectRatio != null && aspectRatio[0] * deviceDescription.getHeight() <= aspectRatio[1] * deviceDescription.getWidth());
/*     */         }
/* 137 */         return (aspectRatio != null && CssUtils.compareFloats(aspectRatio[0] * deviceDescription.getHeight(), aspectRatio[1] * deviceDescription.getWidth()));
/*     */ 
/*     */       
/*     */       case "grid":
/* 141 */         integer1 = CssUtils.parseInteger(this.value);
/* 142 */         return ((integer1 != null && integer1.intValue() == 0 && !deviceDescription.isGrid()) || deviceDescription.isGrid());
/*     */       
/*     */       case "scan":
/* 145 */         return Objects.equals(this.value, deviceDescription.getScan());
/*     */       
/*     */       case "orientation":
/* 148 */         return Objects.equals(this.value, deviceDescription.getOrientation());
/*     */       
/*     */       case "monochrome":
/* 151 */         integer1 = CssUtils.parseInteger(this.value);
/* 152 */         if (this.minPrefix)
/* 153 */           return (integer1 != null && deviceDescription.getMonochrome() >= integer1.intValue()); 
/* 154 */         if (this.maxPrefix) {
/* 155 */           return (integer1 != null && deviceDescription.getMonochrome() <= integer1.intValue());
/*     */         }
/* 157 */         return (integer1 == null) ? ((deviceDescription.getMonochrome() > 0)) : ((integer1.intValue() == deviceDescription.getMonochrome()));
/*     */ 
/*     */       
/*     */       case "height":
/* 161 */         val = parseAbsoluteLength(this.value);
/* 162 */         if (this.minPrefix)
/* 163 */           return (deviceDescription.getHeight() >= val); 
/* 164 */         if (this.maxPrefix) {
/* 165 */           return (deviceDescription.getHeight() <= val);
/*     */         }
/* 167 */         return (deviceDescription.getHeight() > 0.0F);
/*     */ 
/*     */       
/*     */       case "width":
/* 171 */         val = parseAbsoluteLength(this.value);
/* 172 */         if (this.minPrefix)
/* 173 */           return (deviceDescription.getWidth() >= val); 
/* 174 */         if (this.maxPrefix) {
/* 175 */           return (deviceDescription.getWidth() <= val);
/*     */         }
/* 177 */         return (deviceDescription.getWidth() > 0.0F);
/*     */ 
/*     */       
/*     */       case "resolution":
/* 181 */         val = CssUtils.parseResolution(this.value);
/* 182 */         if (this.minPrefix)
/* 183 */           return (deviceDescription.getResolution() >= val); 
/* 184 */         if (this.maxPrefix) {
/* 185 */           return (deviceDescription.getResolution() <= val);
/*     */         }
/* 187 */         return (deviceDescription.getResolution() > 0.0F);
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float parseAbsoluteLength(String value) {
/* 202 */     if (CssUtils.isRelativeValue(value))
/*     */     {
/* 204 */       return CssUtils.parseRelativeValue(value, 12.0F);
/*     */     }
/* 206 */     return CssUtils.parseAbsoluteLength(value);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/media/MediaExpression.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */