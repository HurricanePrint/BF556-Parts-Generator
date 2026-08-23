/*     */ package com.itextpdf.svg.processors.impl.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontProgram;
/*     */ import com.itextpdf.io.font.FontProgramFactory;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.layout.font.FontInfo;
/*     */ import com.itextpdf.layout.font.Range;
/*     */ import com.itextpdf.styledxmlparser.css.CssFontFaceRule;
/*     */ import com.itextpdf.styledxmlparser.css.ICssResolver;
/*     */ import com.itextpdf.styledxmlparser.css.font.CssFontFace;
/*     */ import com.itextpdf.svg.css.impl.SvgStyleResolver;
/*     */ import com.itextpdf.svg.processors.impl.SvgProcessorContext;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvgFontProcessor
/*     */ {
/*     */   private SvgProcessorContext context;
/*     */   
/*     */   public SvgFontProcessor(SvgProcessorContext context) {
/*  70 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFontFaceFonts(ICssResolver cssResolver) {
/*  79 */     if (cssResolver instanceof SvgStyleResolver) {
/*  80 */       for (CssFontFaceRule fontFace : ((SvgStyleResolver)cssResolver).getFonts()) {
/*  81 */         boolean findSupportedSrc = false;
/*  82 */         CssFontFace ff = CssFontFace.create(fontFace.getProperties());
/*  83 */         if (ff != null) {
/*  84 */           for (CssFontFace.CssFontFaceSrc src : ff.getSources()) {
/*  85 */             if (createFont(ff.getFontFamily(), src, fontFace.resolveUnicodeRange())) {
/*  86 */               findSupportedSrc = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*  91 */         if (!findSupportedSrc) {
/*  92 */           LoggerFactory.getLogger(SvgFontProcessor.class)
/*  93 */             .error(MessageFormatUtil.format("Unable to retrieve font:\n {0}", new Object[] { fontFace }));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean createFont(String fontFamily, CssFontFace.CssFontFaceSrc src, Range unicodeRange) {
/* 107 */     if (!CssFontFace.isSupportedFontFormat(src.getFormat()))
/* 108 */       return false; 
/* 109 */     if (src.isLocal()) {
/* 110 */       Collection<FontInfo> fonts = this.context.getFontProvider().getFontSet().get(src.getSrc());
/* 111 */       if (fonts.size() > 0) {
/* 112 */         for (FontInfo fi : fonts) {
/* 113 */           this.context.addTemporaryFont(fi, fontFamily);
/*     */         }
/* 115 */         return true;
/*     */       } 
/* 117 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 123 */       byte[] bytes = this.context.getResourceResolver().retrieveBytesFromResource(src.getSrc());
/* 124 */       if (bytes != null) {
/* 125 */         FontProgram fp = FontProgramFactory.createFont(bytes, false);
/* 126 */         this.context.addTemporaryFont(fp, "Identity-H", fontFamily, unicodeRange);
/* 127 */         return true;
/*     */       } 
/* 129 */     } catch (Exception exception) {}
/*     */     
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/processors/impl/font/SvgFontProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */