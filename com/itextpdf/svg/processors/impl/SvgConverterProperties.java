/*     */ package com.itextpdf.svg.processors.impl;
/*     */ 
/*     */ import com.itextpdf.layout.font.FontProvider;
/*     */ import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.DefaultResourceRetriever;
/*     */ import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;
/*     */ import com.itextpdf.svg.processors.ISvgConverterProperties;
/*     */ import com.itextpdf.svg.renderers.factories.DefaultSvgNodeRendererFactory;
/*     */ import com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvgConverterProperties
/*     */   implements ISvgConverterProperties
/*     */ {
/*     */   private MediaDeviceDescription mediaDeviceDescription;
/*     */   private FontProvider fontProvider;
/*  67 */   private String baseUri = "";
/*     */ 
/*     */   
/*     */   private IResourceRetriever resourceRetriever;
/*     */   
/*     */   private ISvgNodeRendererFactory rendererFactory;
/*     */   
/*  74 */   private String charset = StandardCharsets.UTF_8.name();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvgConverterProperties() {
/*  81 */     this.resourceRetriever = (IResourceRetriever)new DefaultResourceRetriever();
/*  82 */     this.rendererFactory = (ISvgNodeRendererFactory)new DefaultSvgNodeRendererFactory();
/*     */   }
/*     */   
/*     */   public SvgConverterProperties setRendererFactory(ISvgNodeRendererFactory rendererFactory) {
/*  86 */     this.rendererFactory = rendererFactory;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public SvgConverterProperties setFontProvider(FontProvider fontProvider) {
/*  91 */     this.fontProvider = fontProvider;
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRendererFactory getRendererFactory() {
/*  97 */     return this.rendererFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCharset() {
/* 103 */     return this.charset;
/*     */   }
/*     */   
/*     */   public SvgConverterProperties setCharset(String charset) {
/* 107 */     this.charset = charset;
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseUri() {
/* 118 */     return this.baseUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontProvider getFontProvider() {
/* 128 */     return this.fontProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MediaDeviceDescription getMediaDeviceDescription() {
/* 138 */     return this.mediaDeviceDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvgConverterProperties setMediaDeviceDescription(MediaDeviceDescription mediaDeviceDescription) {
/* 148 */     this.mediaDeviceDescription = mediaDeviceDescription;
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvgConverterProperties setBaseUri(String baseUri) {
/* 159 */     this.baseUri = baseUri;
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IResourceRetriever getResourceRetriever() {
/* 171 */     return this.resourceRetriever;
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
/*     */   public SvgConverterProperties setResourceRetriever(IResourceRetriever resourceRetriever) {
/* 183 */     this.resourceRetriever = resourceRetriever;
/* 184 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/impl/SvgConverterProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */