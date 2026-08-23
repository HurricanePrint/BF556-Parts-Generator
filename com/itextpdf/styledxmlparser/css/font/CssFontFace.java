/*     */ package com.itextpdf.styledxmlparser.css.font;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.layout.font.FontFamilySplitter;
/*     */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*     */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssFontFace
/*     */ {
/*     */   private final String alias;
/*     */   private final List<CssFontFaceSrc> sources;
/*     */   
/*     */   public static CssFontFace create(List<CssDeclaration> properties) {
/*  54 */     String fontFamily = null;
/*  55 */     String srcs = null;
/*  56 */     for (CssDeclaration descriptor : properties) {
/*  57 */       if ("font-family".equals(descriptor.getProperty())) {
/*     */         
/*  59 */         fontFamily = FontFamilySplitter.removeQuotes(descriptor.getExpression()); continue;
/*  60 */       }  if ("src".equals(descriptor.getProperty())) {
/*  61 */         srcs = descriptor.getExpression();
/*     */       }
/*     */     } 
/*  64 */     if (fontFamily == null || srcs == null)
/*     */     {
/*     */ 
/*     */       
/*  68 */       return null;
/*     */     }
/*     */     
/*  71 */     List<CssFontFaceSrc> sources = new ArrayList<>();
/*     */     
/*  73 */     for (String src : splitSourcesSequence(srcs)) {
/*     */       
/*  75 */       CssFontFaceSrc source = CssFontFaceSrc.create(src.trim());
/*  76 */       if (source != null) {
/*  77 */         sources.add(source);
/*     */       }
/*     */     } 
/*     */     
/*  81 */     if (sources.size() > 0) {
/*  82 */       return new CssFontFace(fontFamily, sources);
/*     */     }
/*  84 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] splitSourcesSequence(String src) {
/*  99 */     List<String> list = new ArrayList<>();
/* 100 */     int indexToStart = 0;
/* 101 */     while (indexToStart < src.length()) {
/*     */       
/* 103 */       int indexToCut, indexUnescapedOpeningQuoteMark = Math.min((CssUtils.findNextUnescapedChar(src, '\'', indexToStart) >= 0) ? 
/* 104 */           CssUtils.findNextUnescapedChar(src, '\'', indexToStart) : Integer.MAX_VALUE, 
/* 105 */           (CssUtils.findNextUnescapedChar(src, '"', indexToStart) >= 0) ? 
/* 106 */           CssUtils.findNextUnescapedChar(src, '"', indexToStart) : Integer.MAX_VALUE);
/* 107 */       int indexUnescapedBracket = CssUtils.findNextUnescapedChar(src, ')', indexToStart);
/* 108 */       if (indexUnescapedOpeningQuoteMark < indexUnescapedBracket) {
/* 109 */         indexToCut = CssUtils.findNextUnescapedChar(src, src.charAt(indexUnescapedOpeningQuoteMark), indexUnescapedOpeningQuoteMark + 1);
/*     */         
/* 111 */         if (indexToCut == -1) {
/* 112 */           indexToCut = src.length();
/*     */         }
/*     */       } else {
/*     */         
/* 116 */         indexToCut = indexUnescapedBracket;
/*     */       } 
/* 118 */       while (indexToCut < src.length() && src.charAt(indexToCut) != ',') {
/* 119 */         indexToCut++;
/*     */       }
/* 121 */       list.add(src.substring(indexToStart, indexToCut).trim());
/* 122 */       indexToStart = ++indexToCut;
/*     */     } 
/* 124 */     String[] result = new String[list.size()];
/* 125 */     list.toArray(result);
/* 126 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSupportedFontFormat(FontFormat format) {
/* 136 */     switch (format) {
/*     */       case None:
/*     */       case TrueType:
/*     */       case OpenType:
/*     */       case WOFF:
/*     */       case WOFF2:
/* 142 */         return true;
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFontFamily() {
/* 155 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CssFontFaceSrc> getSources() {
/* 164 */     return new ArrayList<>(this.sources);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CssFontFace(String alias, List<CssFontFaceSrc> sources) {
/* 174 */     this.alias = alias;
/* 175 */     this.sources = new ArrayList<>(sources);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum FontFormat
/*     */   {
/* 182 */     None,
/*     */     
/* 184 */     TrueType,
/*     */     
/* 186 */     OpenType,
/*     */     
/* 188 */     WOFF,
/*     */     
/* 190 */     WOFF2,
/*     */     
/* 192 */     EOT,
/*     */     
/* 194 */     SVG;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CssFontFaceSrc
/*     */   {
/* 203 */     public static final Pattern UrlPattern = Pattern.compile("^((local)|(url))\\((('[^']*')|(\"[^\"]*\")|([^'\"\\)]*))\\)( format\\((('[^']*')|(\"[^\"]*\")|([^'\"\\)]*))\\))?$");
/*     */ 
/*     */     
/*     */     public static final int TypeGroup = 1;
/*     */ 
/*     */     
/*     */     public static final int UrlGroup = 4;
/*     */ 
/*     */     
/*     */     public static final int FormatGroup = 9;
/*     */ 
/*     */     
/*     */     final CssFontFace.FontFormat format;
/*     */ 
/*     */     
/*     */     final String src;
/*     */     
/*     */     final boolean isLocal;
/*     */ 
/*     */     
/*     */     public CssFontFace.FontFormat getFormat() {
/* 224 */       return this.format;
/*     */     }
/*     */     
/*     */     public String getSrc() {
/* 228 */       return this.src;
/*     */     }
/*     */     
/*     */     public boolean isLocal() {
/* 232 */       return this.isLocal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 240 */       return 
/* 241 */         MessageFormatUtil.format("{0}({1}){2}", new Object[] {
/* 242 */             this.isLocal ? "local" : "url", this.src, (this.format != CssFontFace.FontFormat.None) ? MessageFormatUtil.format(" format({0})", new Object[] { this.format }) : ""
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static CssFontFaceSrc create(String src) {
/* 254 */       Matcher m = UrlPattern.matcher(src);
/* 255 */       if (!m.matches()) {
/* 256 */         return null;
/*     */       }
/* 258 */       return new CssFontFaceSrc(unquote(m.group(4)), "local"
/* 259 */           .equals(m.group(1)), 
/* 260 */           parseFormat(m.group(9)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static CssFontFace.FontFormat parseFormat(String formatStr) {
/* 270 */       if (formatStr != null && formatStr.length() > 0) {
/* 271 */         switch (unquote(formatStr).toLowerCase()) {
/*     */           case "truetype":
/* 273 */             return CssFontFace.FontFormat.TrueType;
/*     */           case "opentype":
/* 275 */             return CssFontFace.FontFormat.OpenType;
/*     */           case "woff":
/* 277 */             return CssFontFace.FontFormat.WOFF;
/*     */           case "woff2":
/* 279 */             return CssFontFace.FontFormat.WOFF2;
/*     */           case "embedded-opentype":
/* 281 */             return CssFontFace.FontFormat.EOT;
/*     */           case "svg":
/* 283 */             return CssFontFace.FontFormat.SVG;
/*     */         } 
/*     */       }
/* 286 */       return CssFontFace.FontFormat.None;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static String unquote(String quotedString) {
/* 296 */       if (quotedString.charAt(0) == '\'' || quotedString.charAt(0) == '"') {
/* 297 */         return quotedString.substring(1, quotedString.length() - 1);
/*     */       }
/* 299 */       return quotedString;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CssFontFaceSrc(String src, boolean isLocal, CssFontFace.FontFormat format) {
/* 310 */       this.format = format;
/* 311 */       this.src = src;
/* 312 */       this.isLocal = isLocal;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/font/CssFontFace.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */