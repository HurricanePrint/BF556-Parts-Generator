/*    */ package com.itextpdf.io.font.constants;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StandardFonts
/*    */ {
/* 53 */   private static final Set<String> BUILTIN_FONTS = new HashSet<>(); public static final String COURIER = "Courier"; public static final String COURIER_BOLD = "Courier-Bold"; public static final String COURIER_OBLIQUE = "Courier-Oblique"; public static final String COURIER_BOLDOBLIQUE = "Courier-BoldOblique"; public static final String HELVETICA = "Helvetica"; public static final String HELVETICA_BOLD = "Helvetica-Bold"; public static final String HELVETICA_OBLIQUE = "Helvetica-Oblique";
/*    */   
/*    */   static {
/* 56 */     BUILTIN_FONTS.add("Courier");
/* 57 */     BUILTIN_FONTS.add("Courier-Bold");
/* 58 */     BUILTIN_FONTS.add("Courier-BoldOblique");
/* 59 */     BUILTIN_FONTS.add("Courier-Oblique");
/* 60 */     BUILTIN_FONTS.add("Helvetica");
/* 61 */     BUILTIN_FONTS.add("Helvetica-Bold");
/* 62 */     BUILTIN_FONTS.add("Helvetica-BoldOblique");
/* 63 */     BUILTIN_FONTS.add("Helvetica-Oblique");
/* 64 */     BUILTIN_FONTS.add("Symbol");
/* 65 */     BUILTIN_FONTS.add("Times-Roman");
/* 66 */     BUILTIN_FONTS.add("Times-Bold");
/* 67 */     BUILTIN_FONTS.add("Times-BoldItalic");
/* 68 */     BUILTIN_FONTS.add("Times-Italic");
/* 69 */     BUILTIN_FONTS.add("ZapfDingbats");
/*    */   }
/*    */   public static final String HELVETICA_BOLDOBLIQUE = "Helvetica-BoldOblique"; public static final String SYMBOL = "Symbol"; public static final String TIMES_ROMAN = "Times-Roman"; public static final String TIMES_BOLD = "Times-Bold"; public static final String TIMES_ITALIC = "Times-Italic"; public static final String TIMES_BOLDITALIC = "Times-BoldItalic"; public static final String ZAPFDINGBATS = "ZapfDingbats";
/*    */   public static boolean isStandardFont(String fontName) {
/* 73 */     return BUILTIN_FONTS.contains(fontName);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/constants/StandardFonts.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */