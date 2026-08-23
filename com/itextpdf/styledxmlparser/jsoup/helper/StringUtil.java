/*     */ package com.itextpdf.styledxmlparser.jsoup.helper;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringUtil
/*     */ {
/*  56 */   private static final String[] padding = new String[] { "", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          " };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String join(Collection strings, String sep) {
/*  65 */     return join(strings.iterator(), sep);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String join(Iterator<E> strings, String sep) {
/*  75 */     if (!strings.hasNext()) {
/*  76 */       return "";
/*     */     }
/*  78 */     String start = strings.next().toString();
/*  79 */     if (!strings.hasNext()) {
/*  80 */       return start;
/*     */     }
/*  82 */     StringBuilder sb = (new StringBuilder(64)).append(start);
/*  83 */     while (strings.hasNext()) {
/*  84 */       sb.append(sep);
/*  85 */       sb.append(strings.next());
/*     */     } 
/*  87 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String padding(int width) {
/*  96 */     if (width < 0) {
/*  97 */       throw new IllegalArgumentException("width must be > 0");
/*     */     }
/*  99 */     if (width < padding.length) {
/* 100 */       return padding[width];
/*     */     }
/* 102 */     char[] out = new char[width];
/* 103 */     for (int i = 0; i < width; i++)
/* 104 */       out[i] = ' '; 
/* 105 */     return String.valueOf(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBlank(String string) {
/* 114 */     if (string == null || string.length() == 0) {
/* 115 */       return true;
/*     */     }
/* 117 */     int l = string.length();
/* 118 */     for (int i = 0; i < l; i++) {
/* 119 */       if (!isWhitespace(string.codePointAt(i)))
/* 120 */         return false; 
/*     */     } 
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNumeric(String string) {
/* 131 */     if (string == null || string.length() == 0) {
/* 132 */       return false;
/*     */     }
/* 134 */     int l = string.length();
/* 135 */     for (int i = 0; i < l; i++) {
/* 136 */       if (!Character.isDigit(string.codePointAt(i)))
/* 137 */         return false; 
/*     */     } 
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWhitespace(int c) {
/* 148 */     return (c == 32 || c == 9 || c == 10 || c == 12 || c == 13);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String normaliseWhitespace(String string) {
/* 158 */     StringBuilder sb = new StringBuilder(string.length());
/* 159 */     appendNormalisedWhitespace(sb, string, false);
/* 160 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void appendNormalisedWhitespace(StringBuilder accum, String string, boolean stripLeading) {
/* 170 */     boolean lastWasWhite = false;
/* 171 */     boolean reachedNonWhite = false;
/*     */     
/* 173 */     int len = string.length();
/*     */     int i;
/* 175 */     for (i = 0; i < len; i += Character.charCount(c)) {
/* 176 */       int c = string.codePointAt(i);
/* 177 */       if (isWhitespace(c)) {
/* 178 */         if ((!stripLeading || reachedNonWhite) && !lastWasWhite) {
/*     */           
/* 180 */           accum.append(' ');
/* 181 */           lastWasWhite = true;
/*     */         } 
/*     */       } else {
/* 184 */         accum.appendCodePoint(c);
/* 185 */         lastWasWhite = false;
/* 186 */         reachedNonWhite = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean in(String needle, String... haystack) {
/* 192 */     for (String hay : haystack) {
/* 193 */       if (hay.equals(needle))
/* 194 */         return true; 
/*     */     } 
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean inSorted(String needle, String[] haystack) {
/* 200 */     return (Arrays.binarySearch((Object[])haystack, needle) >= 0);
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
/*     */   public static URL resolve(URL base, String relUrl) throws MalformedURLException {
/* 212 */     if (relUrl.startsWith("?")) {
/* 213 */       relUrl = base.getPath() + relUrl;
/*     */     }
/* 215 */     if (relUrl.indexOf('.') == 0 && base.getFile().indexOf('/') != 0) {
/* 216 */       base = new URL(base.getProtocol(), base.getHost(), base.getPort(), "/" + base.getFile());
/*     */     }
/* 218 */     return new URL(base, relUrl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String resolve(String baseUrl, String relUrl) {
/*     */     try {
/*     */       URL base;
/*     */       try {
/* 231 */         base = new URL(baseUrl);
/* 232 */       } catch (MalformedURLException e) {
/*     */         
/* 234 */         URL abs = new URL(relUrl);
/* 235 */         return abs.toExternalForm();
/*     */       } 
/* 237 */       return resolve(base, relUrl).toExternalForm();
/* 238 */     } catch (MalformedURLException e) {
/* 239 */       return "";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/helper/StringUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */