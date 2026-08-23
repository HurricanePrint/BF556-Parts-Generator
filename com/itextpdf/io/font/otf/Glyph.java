/*     */ package com.itextpdf.io.font.otf;
/*     */ 
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Glyph
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1627806639423114471L;
/*     */   private static final char REPLACEMENT_CHARACTER = '�';
/*  57 */   private static final char[] REPLACEMENT_CHARACTERS = new char[] { '�' };
/*  58 */   private static final String REPLACEMENT_CHARACTER_STRING = String.valueOf('�');
/*     */ 
/*     */   
/*     */   private final int code;
/*     */   
/*     */   private final int width;
/*     */   
/*  65 */   private int[] bbox = null;
/*     */ 
/*     */   
/*     */   private int unicode;
/*     */   
/*     */   private char[] chars;
/*     */   
/*     */   private final boolean isMark;
/*     */   
/*  74 */   short xPlacement = 0;
/*  75 */   short yPlacement = 0;
/*     */   
/*  77 */   short xAdvance = 0;
/*  78 */   short yAdvance = 0;
/*     */ 
/*     */   
/*  81 */   short anchorDelta = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Glyph(int code, int width, int unicode) {
/*  91 */     this(code, width, unicode, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Glyph(int code, int width, char[] chars) {
/* 102 */     this(code, width, codePoint(chars), chars, false);
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
/*     */   public Glyph(int code, int width, int unicode, int[] bbox) {
/* 114 */     this(code, width, unicode, null, false);
/* 115 */     this.bbox = bbox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Glyph(int width, int unicode) {
/* 125 */     this(-1, width, unicode, getChars(unicode), false);
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
/*     */   public Glyph(int code, int width, int unicode, char[] chars, boolean IsMark) {
/* 139 */     this.code = code;
/* 140 */     this.width = width;
/* 141 */     this.unicode = unicode;
/* 142 */     this.isMark = IsMark;
/* 143 */     this.chars = (chars != null) ? chars : getChars(unicode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Glyph(Glyph glyph) {
/* 152 */     this.code = glyph.code;
/* 153 */     this.width = glyph.width;
/* 154 */     this.chars = glyph.chars;
/* 155 */     this.unicode = glyph.unicode;
/* 156 */     this.isMark = glyph.isMark;
/* 157 */     this.bbox = glyph.bbox;
/*     */     
/* 159 */     this.xPlacement = glyph.xPlacement;
/* 160 */     this.yPlacement = glyph.yPlacement;
/* 161 */     this.xAdvance = glyph.xAdvance;
/* 162 */     this.yAdvance = glyph.yAdvance;
/* 163 */     this.anchorDelta = glyph.anchorDelta;
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
/*     */   public Glyph(Glyph glyph, int xPlacement, int yPlacement, int xAdvance, int yAdvance, int anchorDelta) {
/* 177 */     this(glyph);
/* 178 */     this.xPlacement = (short)xPlacement;
/* 179 */     this.yPlacement = (short)yPlacement;
/* 180 */     this.xAdvance = (short)xAdvance;
/* 181 */     this.yAdvance = (short)yAdvance;
/* 182 */     this.anchorDelta = (short)anchorDelta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Glyph(Glyph glyph, int unicode) {
/* 192 */     this(glyph.code, glyph.width, unicode, getChars(unicode), glyph.isMark());
/*     */   }
/*     */   
/*     */   public int getCode() {
/* 196 */     return this.code;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 200 */     return this.width;
/*     */   }
/*     */   
/*     */   public int[] getBbox() {
/* 204 */     return this.bbox;
/*     */   }
/*     */   
/*     */   public boolean hasValidUnicode() {
/* 208 */     return (this.unicode > -1);
/*     */   }
/*     */   
/*     */   public int getUnicode() {
/* 212 */     return this.unicode;
/*     */   }
/*     */   
/*     */   public void setUnicode(int unicode) {
/* 216 */     this.unicode = unicode;
/* 217 */     this.chars = getChars(unicode);
/*     */   }
/*     */   
/*     */   public char[] getChars() {
/* 221 */     return this.chars;
/*     */   }
/*     */   
/*     */   public void setChars(char[] chars) {
/* 225 */     this.chars = chars;
/*     */   }
/*     */   
/*     */   public boolean isMark() {
/* 229 */     return this.isMark;
/*     */   }
/*     */   
/*     */   public short getXPlacement() {
/* 233 */     return this.xPlacement;
/*     */   }
/*     */   
/*     */   public void setXPlacement(short xPlacement) {
/* 237 */     this.xPlacement = xPlacement;
/*     */   }
/*     */   
/*     */   public short getYPlacement() {
/* 241 */     return this.yPlacement;
/*     */   }
/*     */   
/*     */   public void setYPlacement(short yPlacement) {
/* 245 */     this.yPlacement = yPlacement;
/*     */   }
/*     */   
/*     */   public short getXAdvance() {
/* 249 */     return this.xAdvance;
/*     */   }
/*     */   
/*     */   public void setXAdvance(short xAdvance) {
/* 253 */     this.xAdvance = xAdvance;
/*     */   }
/*     */   
/*     */   public short getYAdvance() {
/* 257 */     return this.yAdvance;
/*     */   }
/*     */   
/*     */   public void setYAdvance(short yAdvance) {
/* 261 */     this.yAdvance = yAdvance;
/*     */   }
/*     */   
/*     */   public short getAnchorDelta() {
/* 265 */     return this.anchorDelta;
/*     */   }
/*     */   
/*     */   public void setAnchorDelta(short anchorDelta) {
/* 269 */     this.anchorDelta = anchorDelta;
/*     */   }
/*     */   
/*     */   public boolean hasOffsets() {
/* 273 */     return (hasAdvance() || hasPlacement());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlacement() {
/* 278 */     return (this.anchorDelta != 0);
/*     */   }
/*     */   
/*     */   public boolean hasAdvance() {
/* 282 */     return (this.xAdvance != 0 || this.yAdvance != 0);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 286 */     int prime = 31;
/* 287 */     int result = 1;
/* 288 */     result = 31 * result + ((this.chars == null) ? 0 : Arrays.hashCode(this.chars));
/* 289 */     result = 31 * result + this.code;
/* 290 */     result = 31 * result + this.width;
/* 291 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 301 */     if (this == obj) {
/* 302 */       return true;
/*     */     }
/* 304 */     if (obj == null || getClass() != obj.getClass()) {
/* 305 */       return false;
/*     */     }
/* 307 */     Glyph other = (Glyph)obj;
/* 308 */     return (Arrays.equals(this.chars, other.chars) && this.code == other.code && this.width == other.width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnicodeString() {
/* 318 */     if (this.chars != null) {
/* 319 */       return String.valueOf(this.chars);
/*     */     }
/* 321 */     return REPLACEMENT_CHARACTER_STRING;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getUnicodeChars() {
/* 332 */     if (this.chars != null) {
/* 333 */       return this.chars;
/*     */     }
/* 335 */     return REPLACEMENT_CHARACTERS;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 340 */     return MessageFormatUtil.format("[id={0}, chars={1}, uni={2}, width={3}]", new Object[] {
/* 341 */           toHex(this.code), (this.chars != null) ? Arrays.toString(this.chars) : "null", toHex(this.unicode), Integer.valueOf(this.width) });
/*     */   }
/*     */   
/*     */   private static String toHex(int ch) {
/* 345 */     String s = "0000" + Integer.toHexString(ch);
/* 346 */     return s.substring(Math.min(4, s.length() - 4));
/*     */   }
/*     */   
/*     */   private static int codePoint(char[] a) {
/* 350 */     if (a != null) {
/* 351 */       if (a.length == 1 && Character.isValidCodePoint(a[0]))
/* 352 */         return a[0]; 
/* 353 */       if (a.length == 2 && Character.isHighSurrogate(a[0]) && Character.isLowSurrogate(a[1])) {
/* 354 */         return Character.toCodePoint(a[0], a[1]);
/*     */       }
/*     */     } 
/* 357 */     return -1;
/*     */   }
/*     */   
/*     */   private static char[] getChars(int unicode) {
/* 361 */     return (unicode > -1) ? TextUtil.convertFromUtf32(unicode) : null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/Glyph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */