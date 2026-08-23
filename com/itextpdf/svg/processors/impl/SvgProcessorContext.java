/*     */ package com.itextpdf.svg.processors.impl;
/*     */ 
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.layout.font.FontInfo;
/*     */ import com.itextpdf.layout.font.FontProvider;
/*     */ import com.itextpdf.layout.font.FontSet;
/*     */ import com.itextpdf.layout.font.Range;
/*     */ import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
/*     */ import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
/*     */ import com.itextpdf.svg.processors.ISvgConverterProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvgProcessorContext
/*     */ {
/*     */   private FontProvider fontProvider;
/*     */   private FontSet tempFonts;
/*     */   private ResourceResolver resourceResolver;
/*     */   private MediaDeviceDescription deviceDescription;
/*     */   
/*     */   public SvgProcessorContext(ISvgConverterProperties converterProperties) {
/*  81 */     this.deviceDescription = converterProperties.getMediaDeviceDescription();
/*  82 */     if (this.deviceDescription == null) {
/*  83 */       this.deviceDescription = MediaDeviceDescription.getDefault();
/*     */     }
/*     */     
/*  86 */     this.fontProvider = converterProperties.getFontProvider();
/*  87 */     if (this.fontProvider == null) {
/*  88 */       this.fontProvider = (FontProvider)new BasicFontProvider();
/*     */     }
/*     */     
/*  91 */     IResourceRetriever retriever = null;
/*     */ 
/*     */     
/*  94 */     if (converterProperties instanceof SvgConverterProperties) {
/*  95 */       retriever = ((SvgConverterProperties)converterProperties).getResourceRetriever();
/*     */     }
/*  97 */     this.resourceResolver = new ResourceResolver(converterProperties.getBaseUri(), retriever);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProvider getFontProvider() {
/* 106 */     return this.fontProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceResolver getResourceResolver() {
/* 115 */     return this.resourceResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription getDeviceDescription() {
/* 124 */     return this.deviceDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontSet getTempFonts() {
/* 133 */     return this.tempFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTemporaryFont(FontProgram fontProgram, String encoding, String alias, Range unicodeRange) {
/* 145 */     if (this.tempFonts == null) {
/* 146 */       this.tempFonts = new FontSet();
/*     */     }
/* 148 */     this.tempFonts.addFont(fontProgram, encoding, alias, unicodeRange);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTemporaryFont(FontProgram fontProgram, String encoding, String alias) {
/* 159 */     if (this.tempFonts == null) this.tempFonts = new FontSet(); 
/* 160 */     this.tempFonts.addFont(fontProgram, encoding, alias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTemporaryFont(FontInfo fontInfo, String alias) {
/* 170 */     if (this.tempFonts == null) this.tempFonts = new FontSet(); 
/* 171 */     this.tempFonts.addFont(fontInfo, alias);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/impl/SvgProcessorContext.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */