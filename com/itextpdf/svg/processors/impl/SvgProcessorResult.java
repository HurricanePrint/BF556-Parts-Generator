/*     */ package com.itextpdf.svg.processors.impl;
/*     */ 
/*     */ import com.itextpdf.layout.font.FontProvider;
/*     */ import com.itextpdf.layout.font.FontSet;
/*     */ import com.itextpdf.svg.processors.ISvgProcessorResult;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvgProcessorResult
/*     */   implements ISvgProcessorResult
/*     */ {
/*     */   private Map<String, ISvgNodeRenderer> namedObjects;
/*     */   private ISvgNodeRenderer root;
/*     */   @Deprecated
/*     */   private FontProvider fontProvider;
/*     */   @Deprecated
/*     */   private FontSet tempFonts;
/*     */   private SvgProcessorContext context;
/*     */   
/*     */   @Deprecated
/*     */   public SvgProcessorResult(Map<String, ISvgNodeRenderer> namedObjects, ISvgNodeRenderer root, FontProvider fontProvider, FontSet tempFonts) {
/*  87 */     this.namedObjects = namedObjects;
/*  88 */     this.root = root;
/*  89 */     this.fontProvider = fontProvider;
/*  90 */     this.tempFonts = tempFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvgProcessorResult(Map<String, ISvgNodeRenderer> namedObjects, ISvgNodeRenderer root, SvgProcessorContext context) {
/* 101 */     this.namedObjects = namedObjects;
/* 102 */     this.root = root;
/* 103 */     this.fontProvider = context.getFontProvider();
/* 104 */     this.tempFonts = context.getTempFonts();
/* 105 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, ISvgNodeRenderer> getNamedObjects() {
/* 110 */     return this.namedObjects;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISvgNodeRenderer getRootRenderer() {
/* 115 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public FontProvider getFontProvider() {
/* 120 */     return this.fontProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public FontSet getTempFonts() {
/* 125 */     return this.tempFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvgProcessorContext getContext() {
/* 133 */     return this.context;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 138 */     if (o == null || !o.getClass().equals(getClass())) {
/* 139 */       return false;
/*     */     }
/* 141 */     SvgProcessorResult otherResult = (SvgProcessorResult)o;
/* 142 */     return (otherResult.getNamedObjects().equals(getNamedObjects()) && otherResult.getRootRenderer().equals(getRootRenderer()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 147 */     return getNamedObjects().hashCode() + 43 * getRootRenderer().hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/impl/SvgProcessorResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */