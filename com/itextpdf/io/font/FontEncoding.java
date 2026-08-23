/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.util.ArrayUtil;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontEncoding
/*     */   implements Serializable
/*     */ {
/*  58 */   private static final byte[] emptyBytes = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   protected IntHashtable unicodeToCode = new IntHashtable(256);
/*  94 */   protected int[] codeToUnicode = ArrayUtil.fillWithValue(new int[256], -1);
/*  95 */   protected IntHashtable unicodeDifferences = new IntHashtable(256); protected boolean fontSpecific = false;
/*     */   private static final long serialVersionUID = -684967385759439083L;
/*     */   public static final String FONT_SPECIFIC = "FontSpecific";
/*     */   
/*     */   public static FontEncoding createFontEncoding(String baseEncoding) {
/* 100 */     FontEncoding encoding = new FontEncoding();
/* 101 */     encoding.baseEncoding = normalizeEncoding(baseEncoding);
/* 102 */     if (encoding.baseEncoding.startsWith("#")) {
/* 103 */       encoding.fillCustomEncoding();
/*     */     } else {
/* 105 */       encoding.fillNamedEncoding();
/*     */     } 
/* 107 */     return encoding;
/*     */   }
/*     */   public static final String NOTDEF = ".notdef"; protected String baseEncoding; protected String[] differences;
/*     */   public static FontEncoding createEmptyFontEncoding() {
/* 111 */     FontEncoding encoding = new FontEncoding();
/* 112 */     encoding.baseEncoding = null;
/* 113 */     encoding.fontSpecific = false;
/* 114 */     encoding.differences = new String[256];
/* 115 */     for (int ch = 0; ch < 256; ch++) {
/* 116 */       encoding.unicodeDifferences.put(ch, ch);
/*     */     }
/* 118 */     return encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontEncoding createFontSpecificEncoding() {
/* 127 */     FontEncoding encoding = new FontEncoding();
/* 128 */     encoding.fontSpecific = true;
/* 129 */     for (int ch = 0; ch < 256; ch++) {
/* 130 */       encoding.unicodeToCode.put(ch, ch);
/* 131 */       encoding.codeToUnicode[ch] = ch;
/* 132 */       encoding.unicodeDifferences.put(ch, ch);
/*     */     } 
/* 134 */     return encoding;
/*     */   }
/*     */   
/*     */   public String getBaseEncoding() {
/* 138 */     return this.baseEncoding;
/*     */   }
/*     */   
/*     */   public boolean isFontSpecific() {
/* 142 */     return this.fontSpecific;
/*     */   }
/*     */   
/*     */   public boolean addSymbol(int code, int unicode) {
/* 146 */     if (code < 0 || code > 255) {
/* 147 */       return false;
/*     */     }
/* 149 */     String glyphName = AdobeGlyphList.unicodeToName(unicode);
/* 150 */     if (glyphName != null) {
/* 151 */       this.unicodeToCode.put(unicode, code);
/* 152 */       this.codeToUnicode[code] = unicode;
/* 153 */       this.differences[code] = glyphName;
/* 154 */       this.unicodeDifferences.put(unicode, unicode);
/* 155 */       return true;
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUnicode(int index) {
/* 168 */     return this.codeToUnicode[index];
/*     */   }
/*     */   
/*     */   public int getUnicodeDifference(int index) {
/* 172 */     return this.unicodeDifferences.get(index);
/*     */   }
/*     */   
/*     */   public boolean hasDifferences() {
/* 176 */     return (this.differences != null);
/*     */   }
/*     */   
/*     */   public String getDifference(int index) {
/* 180 */     return (this.differences != null) ? this.differences[index] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] convertToBytes(String text) {
/* 191 */     if (text == null || text.length() == 0) {
/* 192 */       return emptyBytes;
/*     */     }
/* 194 */     int ptr = 0;
/* 195 */     byte[] bytes = new byte[text.length()];
/* 196 */     for (int i = 0; i < text.length(); i++) {
/* 197 */       if (this.unicodeToCode.containsKey(text.charAt(i))) {
/* 198 */         bytes[ptr++] = (byte)convertToByte(text.charAt(i));
/*     */       }
/*     */     } 
/* 201 */     return ArrayUtil.shortenArray(bytes, ptr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int convertToByte(int unicode) {
/* 212 */     return this.unicodeToCode.get(unicode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canEncode(int unicode) {
/* 223 */     return (this.unicodeToCode.containsKey(unicode) || TextUtil.isNonPrintable(unicode) || TextUtil.isNewLine(unicode));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDecode(int code) {
/* 234 */     return (this.codeToUnicode[code] > -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBuiltWith(String encoding) {
/* 244 */     return Objects.equals(normalizeEncoding(encoding), this.baseEncoding);
/*     */   }
/*     */   
/*     */   protected void fillCustomEncoding() {
/* 248 */     this.differences = new String[256];
/* 249 */     StringTokenizer tok = new StringTokenizer(this.baseEncoding.substring(1), " ,\t\n\r\f");
/* 250 */     if (tok.nextToken().equals("full")) {
/* 251 */       while (tok.hasMoreTokens()) {
/* 252 */         int orderK; String order = tok.nextToken();
/* 253 */         String name = tok.nextToken();
/* 254 */         char uni = (char)Integer.parseInt(tok.nextToken(), 16);
/* 255 */         int uniName = AdobeGlyphList.nameToUnicode(name);
/*     */         
/* 257 */         if (order.startsWith("'")) {
/* 258 */           orderK = order.charAt(1);
/*     */         } else {
/* 260 */           orderK = Integer.parseInt(order);
/*     */         } 
/* 262 */         orderK %= 256;
/* 263 */         this.unicodeToCode.put(uni, orderK);
/* 264 */         this.codeToUnicode[orderK] = uni;
/* 265 */         this.differences[orderK] = name;
/* 266 */         this.unicodeDifferences.put(uni, uniName);
/*     */       } 
/*     */     } else {
/* 269 */       int i = 0;
/* 270 */       if (tok.hasMoreTokens()) {
/* 271 */         i = Integer.parseInt(tok.nextToken());
/*     */       }
/* 273 */       while (tok.hasMoreTokens() && i < 256) {
/* 274 */         String hex = tok.nextToken();
/* 275 */         int uni = Integer.parseInt(hex, 16) % 65536;
/* 276 */         String name = AdobeGlyphList.unicodeToName(uni);
/* 277 */         if (name == null) {
/* 278 */           name = "uni" + hex;
/*     */         }
/* 280 */         this.unicodeToCode.put(uni, i);
/* 281 */         this.codeToUnicode[i] = uni;
/* 282 */         this.differences[i] = name;
/* 283 */         this.unicodeDifferences.put(uni, uni);
/* 284 */         i++;
/*     */       } 
/*     */     } 
/* 287 */     for (int k = 0; k < 256; k++) {
/* 288 */       if (this.differences[k] == null) {
/* 289 */         this.differences[k] = ".notdef";
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fillNamedEncoding() {
/* 296 */     PdfEncodings.convertToBytes(" ", this.baseEncoding);
/* 297 */     boolean stdEncoding = ("Cp1252".equals(this.baseEncoding) || "MacRoman".equals(this.baseEncoding));
/* 298 */     if (!stdEncoding && this.differences == null) {
/* 299 */       this.differences = new String[256];
/*     */     }
/*     */     
/* 302 */     byte[] b = new byte[256];
/* 303 */     for (int k = 0; k < 256; k++) {
/* 304 */       b[k] = (byte)k;
/*     */     }
/* 306 */     String str = PdfEncodings.convertToString(b, this.baseEncoding);
/* 307 */     char[] encoded = str.toCharArray();
/* 308 */     for (int ch = 0; ch < 256; ch++) {
/* 309 */       char uni = encoded[ch];
/* 310 */       String name = AdobeGlyphList.unicodeToName(uni);
/* 311 */       if (name == null) {
/* 312 */         name = ".notdef";
/*     */       } else {
/* 314 */         this.unicodeToCode.put(uni, ch);
/* 315 */         this.codeToUnicode[ch] = uni;
/* 316 */         this.unicodeDifferences.put(uni, uni);
/*     */       } 
/* 318 */       if (this.differences != null) {
/* 319 */         this.differences[ch] = name;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void fillStandardEncoding() {
/* 325 */     int[] encoded = PdfEncodings.standardEncoding;
/* 326 */     for (int ch = 0; ch < 256; ch++) {
/* 327 */       int uni = encoded[ch];
/* 328 */       String name = AdobeGlyphList.unicodeToName(uni);
/* 329 */       if (name == null) {
/* 330 */         name = ".notdef";
/*     */       } else {
/* 332 */         this.unicodeToCode.put(uni, ch);
/* 333 */         this.codeToUnicode[ch] = uni;
/* 334 */         this.unicodeDifferences.put(uni, uni);
/*     */       } 
/* 336 */       if (this.differences != null) {
/* 337 */         this.differences[ch] = name;
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
/*     */   protected static String normalizeEncoding(String enc) {
/* 350 */     String tmp = (enc == null) ? "" : enc.toLowerCase();
/* 351 */     switch (tmp) {
/*     */       case "":
/*     */       case "winansi":
/*     */       case "winansiencoding":
/* 355 */         return "Cp1252";
/*     */       case "macroman":
/*     */       case "macromanencoding":
/* 358 */         return "MacRoman";
/*     */       case "zapfdingbatsencoding":
/* 360 */         return "ZapfDingbats";
/*     */     } 
/* 362 */     return enc;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontEncoding.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */