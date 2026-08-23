/*     */ package com.itextpdf.styledxmlparser.css.media;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MediaType
/*     */ {
/*  54 */   private static final Set<String> registeredMediaTypes = new HashSet<>();
/*     */ 
/*     */   
/*  57 */   public static final String ALL = registerMediaType("all");
/*     */ 
/*     */   
/*  60 */   public static final String AURAL = registerMediaType("aural");
/*     */ 
/*     */   
/*  63 */   public static final String BRAILLE = registerMediaType("braille");
/*     */ 
/*     */   
/*  66 */   public static final String EMBOSSED = registerMediaType("embossed");
/*     */ 
/*     */   
/*  69 */   public static final String HANDHELD = registerMediaType("handheld");
/*     */ 
/*     */   
/*  72 */   public static final String PRINT = registerMediaType("print");
/*     */ 
/*     */   
/*  75 */   public static final String PROJECTION = registerMediaType("projection");
/*     */ 
/*     */   
/*  78 */   public static final String SCREEN = registerMediaType("screen");
/*     */ 
/*     */   
/*  81 */   public static final String SPEECH = registerMediaType("speech");
/*     */ 
/*     */   
/*  84 */   public static final String TTY = registerMediaType("tty");
/*     */ 
/*     */   
/*  87 */   public static final String TV = registerMediaType("tv");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidMediaType(String mediaType) {
/* 102 */     return registeredMediaTypes.contains(mediaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String registerMediaType(String mediaType) {
/* 112 */     registeredMediaTypes.add(mediaType);
/* 113 */     return mediaType;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/media/MediaType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */