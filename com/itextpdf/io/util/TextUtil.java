/*     */ package com.itextpdf.io.util;
/*     */ 
/*     */ import com.itextpdf.io.font.otf.Glyph;
/*     */ import com.itextpdf.io.font.otf.GlyphLine;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TextUtil
/*     */ {
/*     */   public static boolean isSurrogateHigh(char c) {
/*  70 */     return (c >= '?' && c <= '?');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSurrogateLow(char c) {
/*  81 */     return (c >= '?' && c <= '?');
/*     */   }
/*     */   
/*     */   public static char highSurrogate(int codePoint) {
/*  85 */     return (char)((codePoint >>> 10) + 55232);
/*     */   }
/*     */ 
/*     */   
/*     */   public static char lowSurrogate(int codePoint) {
/*  90 */     return (char)((codePoint & 0x3FF) + 56320);
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
/*     */   public static boolean isSurrogatePair(String text, int idx) {
/* 103 */     return (idx >= 0 && idx <= text.length() - 2 && 
/* 104 */       isSurrogateHigh(text.charAt(idx)) && 
/* 105 */       isSurrogateLow(text.charAt(idx + 1)));
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
/*     */   public static boolean isSurrogatePair(char[] text, int idx) {
/* 118 */     return (idx >= 0 && idx <= text.length - 2 && 
/* 119 */       isSurrogateHigh(text[idx]) && 
/* 120 */       isSurrogateLow(text[idx + 1]));
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
/*     */   public static int convertToUtf32(char highSurrogate, char lowSurrogate) {
/* 132 */     return (highSurrogate - 55296) * 1024 + lowSurrogate - 56320 + 65536;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int convertToUtf32(char[] text, int idx) {
/* 143 */     return (text[idx] - 55296) * 1024 + text[idx + 1] - 56320 + 65536;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int convertToUtf32(String text, int idx) {
/* 154 */     return (text.charAt(idx) - 55296) * 1024 + text.charAt(idx + 1) - 56320 + 65536;
/*     */   }
/*     */   
/*     */   public static int[] convertToUtf32(String text) {
/* 158 */     if (text == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     List<Integer> charCodes = new ArrayList<>(text.length());
/* 162 */     int pos = 0;
/* 163 */     while (pos < text.length()) {
/* 164 */       if (isSurrogatePair(text, pos)) {
/* 165 */         charCodes.add(Integer.valueOf(convertToUtf32(text, pos)));
/* 166 */         pos += 2; continue;
/*     */       } 
/* 168 */       charCodes.add(Integer.valueOf(text.charAt(pos)));
/* 169 */       pos++;
/*     */     } 
/*     */     
/* 172 */     return ArrayUtil.toIntArray(charCodes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] convertFromUtf32(int codePoint) {
/* 182 */     if (codePoint < 65536) {
/* 183 */       return new char[] { (char)codePoint };
/*     */     }
/* 185 */     codePoint -= 65536;
/* 186 */     return new char[] { (char)(codePoint / 1024 + 55296), (char)(codePoint % 1024 + 56320) };
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
/*     */   public static String convertFromUtf32(int[] text, int startPos, int endPos) {
/* 199 */     StringBuilder sb = new StringBuilder();
/* 200 */     for (int i = startPos; i < endPos; i++) {
/* 201 */       sb.append(convertFromUtf32ToCharArray(text[i]));
/*     */     }
/* 203 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] convertFromUtf32ToCharArray(int codePoint) {
/* 213 */     if (codePoint < 65536) {
/* 214 */       return new char[] { (char)codePoint };
/*     */     }
/* 216 */     codePoint -= 65536;
/* 217 */     return new char[] { (char)(codePoint / 1024 + 55296), (char)(codePoint % 1024 + 56320) };
/*     */   }
/*     */   
/*     */   public static String charToString(char ch) {
/* 221 */     return String.valueOf(ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNewLine(Glyph glyph) {
/* 231 */     int unicode = glyph.getUnicode();
/* 232 */     return isNewLine(unicode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNewLine(char c) {
/* 242 */     int unicode = c;
/* 243 */     return isNewLine(unicode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNewLine(int unicode) {
/* 253 */     return (unicode == 10 || unicode == 13);
/*     */   }
/*     */   
/*     */   public static boolean isCarriageReturnFollowedByLineFeed(GlyphLine glyphLine, int carriageReturnPosition) {
/* 257 */     return (glyphLine.size() > 1 && carriageReturnPosition <= glyphLine
/* 258 */       .size() - 2 && glyphLine
/* 259 */       .get(carriageReturnPosition).getUnicode() == 13 && glyphLine
/* 260 */       .get(carriageReturnPosition + 1).getUnicode() == 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSpaceOrWhitespace(Glyph glyph) {
/* 266 */     return (Character.isSpaceChar((char)glyph.getUnicode()) || Character.isWhitespace((char)glyph.getUnicode()));
/*     */   }
/*     */   
/*     */   public static boolean isWhitespace(Glyph glyph) {
/* 270 */     return Character.isWhitespace(glyph.getUnicode());
/*     */   }
/*     */   
/*     */   public static boolean isNonBreakingHyphen(Glyph glyph) {
/* 274 */     return (8209 == glyph.getUnicode());
/*     */   }
/*     */   
/*     */   public static boolean isSpace(Glyph glyph) {
/* 278 */     return Character.isSpaceChar((char)glyph.getUnicode());
/*     */   }
/*     */   
/*     */   public static boolean isUni0020(Glyph g) {
/* 282 */     return (g.getUnicode() == 32);
/*     */   }
/*     */   
/*     */   public static boolean isNonPrintable(int c) {
/* 286 */     return (Character.isIdentifierIgnorable(c) || c == 173);
/*     */   }
/*     */   
/*     */   public static boolean isWhitespaceOrNonPrintable(int code) {
/* 290 */     return (Character.isWhitespace(code) || isNonPrintable(code));
/*     */   }
/*     */   
/*     */   public static boolean charsetIsSupported(String charsetName) {
/*     */     try {
/* 295 */       return Charset.isSupported(charsetName);
/* 296 */     } catch (IllegalArgumentException e) {
/* 297 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/util/TextUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */