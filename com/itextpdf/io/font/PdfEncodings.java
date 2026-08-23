/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.util.EncodingUtil;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
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
/*     */ public class PdfEncodings
/*     */ {
/*     */   public static final String IDENTITY_H = "Identity-H";
/*     */   public static final String IDENTITY_V = "Identity-V";
/*     */   public static final String CP1250 = "Cp1250";
/*     */   public static final String CP1252 = "Cp1252";
/*     */   public static final String CP1253 = "Cp1253";
/*     */   public static final String CP1257 = "Cp1257";
/*     */   public static final String WINANSI = "Cp1252";
/*     */   public static final String MACROMAN = "MacRoman";
/*     */   public static final String SYMBOL = "Symbol";
/*     */   public static final String ZAPFDINGBATS = "ZapfDingbats";
/*     */   public static final String UNICODE_BIG = "UnicodeBig";
/*     */   public static final String UNICODE_BIG_UNMARKED = "UnicodeBigUnmarked";
/*     */   public static final String PDF_DOC_ENCODING = "PDF";
/*     */   public static final String UTF8 = "UTF-8";
/*     */   private static final String EMPTY_STRING = "";
/*  89 */   private static final char[] winansiByteToChar = new char[] { Character.MIN_VALUE, '\001', '\002', '\003', '\004', '\005', '\006', '\007', '\b', '\t', '\n', '\013', '\f', '\r', '\016', '\017', '\020', '\021', '\022', '\023', '\024', '\025', '\026', '\027', '\030', '\031', '\032', '\033', '\034', '\035', '\036', '\037', ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '', '€', '�', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '�', 'Ž', '�', '�', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '�', 'ž', 'Ÿ', ' ', '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '­', '®', '¯', '°', '±', '²', '³', '´', 'µ', '¶', '·', '¸', '¹', 'º', '»', '¼', '½', '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static final char[] pdfEncodingByteToChar = new char[] { Character.MIN_VALUE, '\001', '\002', '\003', '\004', '\005', '\006', '\007', '\b', '\t', '\n', '\013', '\f', '\r', '\016', '\017', '\020', '\021', '\022', '\023', '\024', '\025', '\026', '\027', '\030', '\031', '\032', '\033', '\034', '\035', '\036', '\037', ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '', '•', '†', '‡', '…', '—', '–', 'ƒ', '⁄', '‹', '›', '−', '‰', '„', '“', '”', '‘', '’', '‚', '™', 'ﬁ', 'ﬂ', 'Ł', 'Œ', 'Š', 'Ÿ', 'Ž', 'ı', 'ł', 'œ', 'š', 'ž', '�', '€', '¡', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', 'ª', '«', '¬', '­', '®', '¯', '°', '±', '²', '³', '´', 'µ', '¶', '·', '¸', '¹', 'º', '»', '¼', '½', '¾', '¿', 'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'þ', 'ÿ' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   static final int[] standardEncoding = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 33, 34, 35, 36, 37, 38, 8217, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 8216, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 161, 162, 163, 8260, 165, 402, 167, 164, 39, 8220, 171, 8249, 8250, 64257, 64258, 0, 8211, 8224, 8225, 183, 0, 182, 8226, 8218, 8222, 8221, 187, 8230, 8240, 0, 191, 0, 96, 180, 710, 732, 175, 728, 729, 168, 0, 730, 184, 0, 733, 731, 711, 8212, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 198, 0, 170, 0, 0, 0, 0, 321, 216, 338, 186, 0, 0, 0, 0, 0, 230, 0, 0, 0, 305, 0, 0, 322, 248, 339, 223, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   private static final IntHashtable winansi = new IntHashtable();
/*     */   
/* 147 */   private static final IntHashtable pdfEncoding = new IntHashtable();
/*     */   
/* 149 */   private static final Map<String, IExtraEncoding> extraEncodings = new HashMap<>();
/*     */   static {
/*     */     int k;
/* 152 */     for (k = 128; k < 161; k++) {
/* 153 */       char c = winansiByteToChar[k];
/* 154 */       if (c != '�') {
/* 155 */         winansi.put(c, k);
/*     */       }
/*     */     } 
/* 158 */     for (k = 128; k < 161; k++) {
/* 159 */       char c = pdfEncodingByteToChar[k];
/* 160 */       if (c != '�') {
/* 161 */         pdfEncoding.put(c, k);
/*     */       }
/*     */     } 
/*     */     
/* 165 */     addExtraEncoding("Wingdings", new WingdingsConversion());
/* 166 */     addExtraEncoding("Symbol", new SymbolConversion(true));
/* 167 */     addExtraEncoding("ZapfDingbats", new SymbolConversion(false));
/* 168 */     addExtraEncoding("SymbolTT", new SymbolTTConversion());
/* 169 */     addExtraEncoding("Cp437", new Cp437Conversion());
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
/*     */   public static byte[] convertToBytes(String text, String encoding) {
/* 181 */     if (text == null)
/* 182 */       return new byte[0]; 
/* 183 */     if (encoding == null || encoding.length() == 0) {
/* 184 */       int len = text.length();
/* 185 */       byte[] b = new byte[len];
/* 186 */       for (int k = 0; k < len; k++) {
/* 187 */         b[k] = (byte)text.charAt(k);
/*     */       }
/* 189 */       return b;
/*     */     } 
/* 191 */     IExtraEncoding extra = extraEncodings.get(encoding.toLowerCase());
/* 192 */     if (extra != null) {
/* 193 */       byte[] b = extra.charToByte(text, encoding);
/* 194 */       if (b != null)
/* 195 */         return b; 
/*     */     } 
/* 197 */     IntHashtable hash = null;
/* 198 */     if (encoding.equals("Cp1252")) {
/* 199 */       hash = winansi;
/* 200 */     } else if (encoding.equals("PDF")) {
/* 201 */       hash = pdfEncoding;
/*     */     } 
/* 203 */     if (hash != null) {
/* 204 */       char[] cc = text.toCharArray();
/* 205 */       int len = cc.length;
/* 206 */       int ptr = 0;
/* 207 */       byte[] b = new byte[len];
/*     */       
/* 209 */       for (int k = 0; k < len; k++) {
/* 210 */         int c; char ch = cc[k];
/* 211 */         if (ch < '' || (ch > ' ' && ch <= 'ÿ')) {
/* 212 */           c = ch;
/*     */         } else {
/* 214 */           c = hash.get(ch);
/*     */         } 
/* 216 */         if (c != 0) {
/* 217 */           b[ptr++] = (byte)c;
/*     */         }
/*     */       } 
/* 220 */       if (ptr == len)
/* 221 */         return b; 
/* 222 */       byte[] b2 = new byte[ptr];
/* 223 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 224 */       return b2;
/*     */     } 
/*     */     try {
/* 227 */       return EncodingUtil.convertToBytes(text.toCharArray(), encoding);
/* 228 */     } catch (IOException e) {
/* 229 */       throw new IOException("Character code exception.", e);
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
/*     */   
/*     */   public static byte[] convertToBytes(char ch, String encoding) {
/* 242 */     if (encoding == null || encoding.length() == 0)
/* 243 */       return new byte[] { (byte)ch }; 
/* 244 */     IntHashtable hash = null;
/* 245 */     if (encoding.equals("Cp1252")) {
/* 246 */       hash = winansi;
/* 247 */     } else if (encoding.equals("PDF")) {
/* 248 */       hash = pdfEncoding;
/* 249 */     }  if (hash != null) {
/*     */       int c;
/* 251 */       if (ch < '' || (ch > ' ' && ch <= 'ÿ')) {
/* 252 */         c = ch;
/*     */       } else {
/* 254 */         c = hash.get(ch);
/*     */       } 
/* 256 */       if (c != 0) {
/* 257 */         return new byte[] { (byte)c };
/*     */       }
/* 259 */       return new byte[0];
/*     */     } 
/*     */     
/*     */     try {
/* 263 */       return EncodingUtil.convertToBytes(new char[] { ch }, encoding);
/* 264 */     } catch (IOException e) {
/* 265 */       throw new IOException("Character code exception.", e);
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
/*     */   
/*     */   public static String convertToString(byte[] bytes, String encoding) {
/* 278 */     if (bytes == null)
/* 279 */       return ""; 
/* 280 */     if (encoding == null || encoding.length() == 0) {
/* 281 */       char[] c = new char[bytes.length];
/* 282 */       for (int k = 0; k < bytes.length; k++) {
/* 283 */         c[k] = (char)(bytes[k] & 0xFF);
/*     */       }
/* 285 */       return new String(c);
/*     */     } 
/* 287 */     IExtraEncoding extra = extraEncodings.get(encoding.toLowerCase());
/* 288 */     if (extra != null) {
/* 289 */       String text = extra.byteToChar(bytes, encoding);
/* 290 */       if (text != null) {
/* 291 */         return text;
/*     */       }
/*     */     } 
/* 294 */     char[] ch = null;
/* 295 */     if (encoding.equals("Cp1252")) {
/* 296 */       ch = winansiByteToChar;
/* 297 */     } else if (encoding.equals("PDF")) {
/* 298 */       ch = pdfEncodingByteToChar;
/* 299 */     }  if (ch != null) {
/* 300 */       int len = bytes.length;
/* 301 */       char[] c = new char[len];
/* 302 */       for (int k = 0; k < len; k++) {
/* 303 */         c[k] = ch[bytes[k] & 0xFF];
/*     */       }
/* 305 */       return new String(c);
/*     */     } 
/*     */     try {
/* 308 */       return EncodingUtil.convertToString(bytes, encoding);
/* 309 */     } catch (UnsupportedEncodingException e) {
/* 310 */       throw new IOException("Unsupported encoding exception.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPdfDocEncoding(String text) {
/* 321 */     if (text == null)
/* 322 */       return true; 
/* 323 */     int len = text.length();
/* 324 */     for (int k = 0; k < len; k++) {
/* 325 */       char ch = text.charAt(k);
/* 326 */       if (ch >= '' && (ch <= ' ' || ch > 'ÿ'))
/*     */       {
/* 328 */         if (!pdfEncoding.containsKey(ch))
/* 329 */           return false;  } 
/*     */     } 
/* 331 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addExtraEncoding(String name, IExtraEncoding enc) {
/* 341 */     synchronized (extraEncodings) {
/* 342 */       extraEncodings.put(name.toLowerCase(), enc);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class WingdingsConversion implements IExtraEncoding { private WingdingsConversion() {}
/*     */     
/*     */     public byte[] charToByte(char char1, String encoding) {
/* 349 */       if (char1 == ' ')
/* 350 */         return new byte[] { (byte)char1 }; 
/* 351 */       if (char1 >= '✁' && char1 <= '➾') {
/* 352 */         byte v = table[char1 - 9984];
/* 353 */         if (v != 0)
/* 354 */           return new byte[] { v }; 
/*     */       } 
/* 356 */       return new byte[0];
/*     */     }
/*     */     
/*     */     public byte[] charToByte(String text, String encoding) {
/* 360 */       char[] cc = text.toCharArray();
/* 361 */       byte[] b = new byte[cc.length];
/* 362 */       int ptr = 0;
/* 363 */       int len = cc.length;
/* 364 */       for (int k = 0; k < len; k++) {
/* 365 */         char c = cc[k];
/* 366 */         if (c == ' ') {
/* 367 */           b[ptr++] = (byte)c;
/* 368 */         } else if (c >= '✁' && c <= '➾') {
/* 369 */           byte v = table[c - 9984];
/* 370 */           if (v != 0)
/* 371 */             b[ptr++] = v; 
/*     */         } 
/*     */       } 
/* 374 */       if (ptr == len)
/* 375 */         return b; 
/* 376 */       byte[] b2 = new byte[ptr];
/* 377 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 378 */       return b2;
/*     */     }
/*     */     
/*     */     public String byteToChar(byte[] b, String encoding) {
/* 382 */       return null;
/*     */     }
/*     */     
/* 385 */     private static final byte[] table = new byte[] { 0, 35, 34, 0, 0, 0, 41, 62, 81, 42, 0, 0, 65, 63, 0, 0, 0, 0, 0, -4, 0, 0, 0, -5, 0, 0, 0, 0, 0, 0, 86, 0, 88, 89, 0, 0, 0, 0, 0, 0, 0, 0, -75, 0, 0, 0, 0, 0, -74, 0, 0, 0, -83, -81, -84, 0, 0, 0, 0, 0, 0, 0, 0, 124, 123, 0, 0, 0, 84, 0, 0, 0, 0, 0, 0, 0, 0, -90, 0, 0, 0, 113, 114, 0, 0, 0, 117, 0, 0, 0, 0, 0, 0, 125, 126, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -127, -126, -125, -124, -123, -122, -121, -120, -119, -118, -116, -115, -114, -113, -112, -111, -110, -109, -108, -107, -24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -24, -40, 0, 0, -60, -58, 0, 0, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, -36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Cp437Conversion
/*     */     implements IExtraEncoding
/*     */   {
/*     */     private Cp437Conversion() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     private static IntHashtable c2b = new IntHashtable();
/*     */     
/*     */     public byte[] charToByte(String text, String encoding) {
/* 413 */       char[] cc = text.toCharArray();
/* 414 */       byte[] b = new byte[cc.length];
/* 415 */       int ptr = 0;
/* 416 */       int len = cc.length;
/* 417 */       for (int k = 0; k < len; k++) {
/* 418 */         char c = cc[k];
/* 419 */         if (c < '') {
/* 420 */           b[ptr++] = (byte)c;
/*     */         } else {
/* 422 */           byte v = (byte)c2b.get(c);
/* 423 */           if (v != 0)
/* 424 */             b[ptr++] = v; 
/*     */         } 
/*     */       } 
/* 427 */       if (ptr == len)
/* 428 */         return b; 
/* 429 */       byte[] b2 = new byte[ptr];
/* 430 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 431 */       return b2;
/*     */     }
/*     */     
/*     */     public byte[] charToByte(char char1, String encoding) {
/* 435 */       if (char1 < '') {
/* 436 */         return new byte[] { (byte)char1 };
/*     */       }
/* 438 */       byte v = (byte)c2b.get(char1);
/* 439 */       if (v != 0) {
/* 440 */         return new byte[] { v };
/*     */       }
/* 442 */       return new byte[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public String byteToChar(byte[] b, String encoding) {
/* 447 */       int len = b.length;
/* 448 */       char[] cc = new char[len];
/* 449 */       int ptr = 0;
/* 450 */       for (int k = 0; k < len; k++) {
/* 451 */         int c = b[k] & 0xFF;
/* 452 */         if (c >= 32)
/*     */         {
/* 454 */           if (c < 128) {
/* 455 */             cc[ptr++] = (char)c;
/*     */           } else {
/* 457 */             char v = table[c - 128];
/* 458 */             cc[ptr++] = v;
/*     */           }  } 
/*     */       } 
/* 461 */       return new String(cc, 0, ptr);
/*     */     }
/*     */     
/* 464 */     private static final char[] table = new char[] { 'Ç', 'ü', 'é', 'â', 'ä', 'à', 'å', 'ç', 'ê', 'ë', 'è', 'ï', 'î', 'ì', 'Ä', 'Å', 'É', 'æ', 'Æ', 'ô', 'ö', 'ò', 'û', 'ù', 'ÿ', 'Ö', 'Ü', '¢', '£', '¥', '₧', 'ƒ', 'á', 'í', 'ó', 'ú', 'ñ', 'Ñ', 'ª', 'º', '¿', '⌐', '¬', '½', '¼', '¡', '«', '»', '░', '▒', '▓', '│', '┤', '╡', '╢', '╖', '╕', '╣', '║', '╗', '╝', '╜', '╛', '┐', '└', '┴', '┬', '├', '─', '┼', '╞', '╟', '╚', '╔', '╩', '╦', '╠', '═', '╬', '╧', '╨', '╤', '╥', '╙', '╘', '╒', '╓', '╫', '╪', '┘', '┌', '█', '▄', '▌', '▐', '▀', 'α', 'ß', 'Γ', 'π', 'Σ', 'σ', 'µ', 'τ', 'Φ', 'Θ', 'Ω', 'δ', '∞', 'φ', 'ε', '∩', '≡', '±', '≥', '≤', '⌠', '⌡', '÷', '≈', '°', '∙', '·', '√', 'ⁿ', '²', '■', ' ' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 476 */       for (int k = 0; k < table.length; k++)
/* 477 */         c2b.put(table[k], k + 128); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SymbolConversion
/*     */     implements IExtraEncoding {
/* 483 */     private static final IntHashtable t1 = new IntHashtable();
/* 484 */     private static final IntHashtable t2 = new IntHashtable();
/*     */     private IntHashtable translation;
/*     */     private final char[] byteToChar;
/*     */     
/*     */     SymbolConversion(boolean symbol) {
/* 489 */       if (symbol) {
/* 490 */         this.translation = t1;
/* 491 */         this.byteToChar = table1;
/*     */       } else {
/* 493 */         this.translation = t2;
/* 494 */         this.byteToChar = table2;
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte[] charToByte(String text, String encoding) {
/* 499 */       char[] cc = text.toCharArray();
/* 500 */       byte[] b = new byte[cc.length];
/* 501 */       int ptr = 0;
/* 502 */       int len = cc.length;
/* 503 */       for (int k = 0; k < len; k++) {
/* 504 */         char c = cc[k];
/* 505 */         byte v = (byte)this.translation.get(c);
/* 506 */         if (v != 0)
/* 507 */           b[ptr++] = v; 
/*     */       } 
/* 509 */       if (ptr == len)
/* 510 */         return b; 
/* 511 */       byte[] b2 = new byte[ptr];
/* 512 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 513 */       return b2;
/*     */     }
/*     */     
/*     */     public byte[] charToByte(char char1, String encoding) {
/* 517 */       byte v = (byte)this.translation.get(char1);
/* 518 */       if (v != 0) {
/* 519 */         return new byte[] { v };
/*     */       }
/* 521 */       return new byte[0];
/*     */     }
/*     */     
/*     */     public String byteToChar(byte[] b, String encoding) {
/* 525 */       int len = b.length;
/* 526 */       char[] cc = new char[len];
/* 527 */       int ptr = 0;
/* 528 */       for (int k = 0; k < len; k++) {
/* 529 */         int c = b[k] & 0xFF;
/* 530 */         char v = this.byteToChar[c];
/* 531 */         cc[ptr++] = v;
/*     */       } 
/* 533 */       return new String(cc, 0, ptr);
/*     */     }
/*     */     
/* 536 */     private static final char[] table1 = new char[] { Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, ' ', '!', '∀', '#', '∃', '%', '&', '∋', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '≅', 'Α', 'Β', 'Χ', 'Δ', 'Ε', 'Φ', 'Γ', 'Η', 'Ι', 'ϑ', 'Κ', 'Λ', 'Μ', 'Ν', 'Ο', 'Π', 'Θ', 'Ρ', 'Σ', 'Τ', 'Υ', 'ς', 'Ω', 'Ξ', 'Ψ', 'Ζ', '[', '∴', ']', '⊥', '_', '̅', 'α', 'β', 'χ', 'δ', 'ε', 'ϕ', 'γ', 'η', 'ι', 'φ', 'κ', 'λ', 'μ', 'ν', 'ο', 'π', 'θ', 'ρ', 'σ', 'τ', 'υ', 'ϖ', 'ω', 'ξ', 'ψ', 'ζ', '{', '|', '}', '~', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '€', 'ϒ', '′', '≤', '⁄', '∞', 'ƒ', '♣', '♦', '♥', '♠', '↔', '←', '↑', '→', '↓', '°', '±', '″', '≥', '×', '∝', '∂', '•', '÷', '≠', '≡', '≈', '…', '│', '─', '↵', 'ℵ', 'ℑ', 'ℜ', '℘', '⊗', '⊕', '∅', '∩', '∪', '⊃', '⊇', '⊄', '⊂', '⊆', '∈', '∉', '∠', '∇', '®', '©', '™', '∏', '√', '⋅', '¬', '∧', '∨', '⇔', '⇐', '⇑', '⇒', '⇓', '◊', '〈', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '∑', '⎛', '⎜', '⎝', '⎡', '⎢', '⎣', '⎧', '⎨', '⎩', '⎪', Character.MIN_VALUE, '〉', '∫', '⌠', '⎮', '⌡', '⎞', '⎟', '⎠', '⎤', '⎥', '⎦', '⎫', '⎬', '⎭', Character.MIN_VALUE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 555 */     private static final char[] table2 = new char[] { Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, ' ', '✁', '✂', '✃', '✄', '☎', '✆', '✇', '✈', '✉', '☛', '☞', '✌', '✍', '✎', '✏', '✐', '✑', '✒', '✓', '✔', '✕', '✖', '✗', '✘', '✙', '✚', '✛', '✜', '✝', '✞', '✟', '✠', '✡', '✢', '✣', '✤', '✥', '✦', '✧', '★', '✩', '✪', '✫', '✬', '✭', '✮', '✯', '✰', '✱', '✲', '✳', '✴', '✵', '✶', '✷', '✸', '✹', '✺', '✻', '✼', '✽', '✾', '✿', '❀', '❁', '❂', '❃', '❄', '❅', '❆', '❇', '❈', '❉', '❊', '❋', '●', '❍', '■', '❏', '❐', '❑', '❒', '▲', '▼', '◆', '❖', '◗', '❘', '❙', '❚', '❛', '❜', '❝', '❞', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, '❡', '❢', '❣', '❤', '❥', '❦', '❧', '♣', '♦', '♥', '♠', '①', '②', '③', '④', '⑤', '⑥', '⑦', '⑧', '⑨', '⑩', '❶', '❷', '❸', '❹', '❺', '❻', '❼', '❽', '❾', '❿', '➀', '➁', '➂', '➃', '➄', '➅', '➆', '➇', '➈', '➉', '➊', '➋', '➌', '➍', '➎', '➏', '➐', '➑', '➒', '➓', '➔', '→', '↔', '↕', '➘', '➙', '➚', '➛', '➜', '➝', '➞', '➟', '➠', '➡', '➢', '➣', '➤', '➥', '➦', '➧', '➨', '➩', '➪', '➫', '➬', '➭', '➮', '➯', Character.MIN_VALUE, '➱', '➲', '➳', '➴', '➵', '➶', '➷', '➸', '➹', '➺', '➻', '➼', '➽', '➾', Character.MIN_VALUE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       int k;
/* 575 */       for (k = 0; k < 256; k++) {
/* 576 */         int v = table1[k];
/* 577 */         if (v != 0)
/* 578 */           t1.put(v, k); 
/*     */       } 
/* 580 */       for (k = 0; k < 256; k++) {
/* 581 */         int v = table2[k];
/* 582 */         if (v != 0)
/* 583 */           t2.put(v, k); 
/*     */       } 
/*     */     } }
/*     */   
/*     */   private static class SymbolTTConversion implements IExtraEncoding {
/*     */     private SymbolTTConversion() {}
/*     */     
/*     */     public byte[] charToByte(char char1, String encoding) {
/* 591 */       if ((char1 & 0xFF00) == 0 || (char1 & 0xFF00) == 61440) {
/* 592 */         return new byte[] { (byte)char1 };
/*     */       }
/* 594 */       return new byte[0];
/*     */     }
/*     */     
/*     */     public byte[] charToByte(String text, String encoding) {
/* 598 */       char[] ch = text.toCharArray();
/* 599 */       byte[] b = new byte[ch.length];
/* 600 */       int ptr = 0;
/* 601 */       int len = ch.length;
/* 602 */       for (int k = 0; k < len; k++) {
/* 603 */         char c = ch[k];
/* 604 */         if ((c & 0xFF00) == 0 || (c & 0xFF00) == 61440)
/* 605 */           b[ptr++] = (byte)c; 
/*     */       } 
/* 607 */       if (ptr == len)
/* 608 */         return b; 
/* 609 */       byte[] b2 = new byte[ptr];
/* 610 */       System.arraycopy(b, 0, b2, 0, ptr);
/* 611 */       return b2;
/*     */     }
/*     */     
/*     */     public String byteToChar(byte[] b, String encoding) {
/* 615 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/PdfEncodings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */