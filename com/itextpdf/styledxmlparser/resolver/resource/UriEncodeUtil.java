/*     */ package com.itextpdf.styledxmlparser.resolver.resource;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;
/*     */ import java.io.CharArrayWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.util.BitSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UriEncodeUtil
/*     */ {
/*     */   private static BitSet unreservedAndReserved;
/*     */   private static final int caseDiff = 32;
/*  67 */   private static String dfltEncName = "UTF-8";
/*     */   
/*     */   static {
/*  70 */     unreservedAndReserved = new BitSet(256);
/*     */     int i;
/*  72 */     for (i = 97; i <= 122; i++) {
/*  73 */       unreservedAndReserved.set(i);
/*     */     }
/*  75 */     for (i = 65; i <= 90; i++) {
/*  76 */       unreservedAndReserved.set(i);
/*     */     }
/*  78 */     for (i = 48; i <= 57; i++) {
/*  79 */       unreservedAndReserved.set(i);
/*     */     }
/*     */     
/*  82 */     unreservedAndReserved.set(45);
/*  83 */     unreservedAndReserved.set(95);
/*  84 */     unreservedAndReserved.set(46);
/*  85 */     unreservedAndReserved.set(126);
/*     */     
/*  87 */     unreservedAndReserved.set(58);
/*  88 */     unreservedAndReserved.set(47);
/*  89 */     unreservedAndReserved.set(63);
/*  90 */     unreservedAndReserved.set(35);
/*  91 */     unreservedAndReserved.set(91);
/*  92 */     unreservedAndReserved.set(93);
/*  93 */     unreservedAndReserved.set(64);
/*  94 */     unreservedAndReserved.set(33);
/*  95 */     unreservedAndReserved.set(36);
/*  96 */     unreservedAndReserved.set(38);
/*  97 */     unreservedAndReserved.set(39);
/*  98 */     unreservedAndReserved.set(92);
/*  99 */     unreservedAndReserved.set(40);
/* 100 */     unreservedAndReserved.set(41);
/* 101 */     unreservedAndReserved.set(42);
/* 102 */     unreservedAndReserved.set(43);
/* 103 */     unreservedAndReserved.set(44);
/* 104 */     unreservedAndReserved.set(59);
/* 105 */     unreservedAndReserved.set(61);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encode(String s) {
/* 115 */     return encode(s, dfltEncName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encode(String s, String enc) {
/*     */     Charset charset;
/* 126 */     boolean needToChange = false;
/* 127 */     StringBuffer out = new StringBuffer(s.length());
/*     */     
/* 129 */     CharArrayWriter charArrayWriter = new CharArrayWriter();
/*     */     
/* 131 */     if (enc == null) {
/* 132 */       throw new StyledXMLParserException("Unsupported encoding exception.");
/*     */     }
/*     */     
/*     */     try {
/* 136 */       charset = Charset.forName(enc);
/* 137 */     } catch (IllegalCharsetNameException e) {
/* 138 */       throw new StyledXMLParserException("Unsupported encoding exception.");
/*     */     } 
/* 140 */     int i = 0;
/* 141 */     boolean firstHash = true;
/* 142 */     while (i < s.length()) {
/* 143 */       int c = s.charAt(i);
/* 144 */       if (92 == c) {
/* 145 */         out.append('/');
/* 146 */         needToChange = true;
/* 147 */         i++; continue;
/* 148 */       }  if (37 == c) {
/* 149 */         int v = -1;
/* 150 */         if (i + 2 < s.length()) {
/*     */           try {
/* 152 */             v = Integer.parseInt(s.substring(i + 1, i + 3), 16);
/* 153 */           } catch (NumberFormatException e) {
/* 154 */             v = -1;
/*     */           } 
/* 156 */           if (v >= 0)
/* 157 */             out.append((char)c); 
/*     */         } 
/* 159 */         if (v < 0) {
/*     */ 
/*     */           
/* 162 */           needToChange = true;
/* 163 */           out.append("%25");
/*     */         } 
/* 165 */         i++; continue;
/* 166 */       }  if (35 == c) {
/*     */         
/* 168 */         if (firstHash) {
/* 169 */           out.append((char)c);
/* 170 */           firstHash = false;
/*     */         } else {
/*     */           
/* 173 */           out.append("%23");
/* 174 */           needToChange = true;
/*     */         } 
/* 176 */         i++; continue;
/* 177 */       }  if (unreservedAndReserved.get(c)) {
/* 178 */         out.append((char)c);
/* 179 */         i++;
/*     */         continue;
/*     */       } 
/*     */       do {
/* 183 */         charArrayWriter.write(c);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 192 */         if (c < 55296 || c > 56319) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 197 */         if (i + 1 >= s.length())
/* 198 */           continue;  int d = s.charAt(i + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 203 */         if (d < 56320 || d > 57343) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 209 */         charArrayWriter.write(d);
/* 210 */         i++;
/*     */ 
/*     */ 
/*     */         
/* 214 */         ++i;
/* 215 */       } while (i < s.length() && !unreservedAndReserved.get(c = s.charAt(i)));
/*     */       
/* 217 */       charArrayWriter.flush();
/* 218 */       String str = new String(charArrayWriter.toCharArray());
/* 219 */       byte[] ba = str.getBytes(charset);
/* 220 */       for (int j = 0; j < ba.length; j++) {
/* 221 */         out.append('%');
/* 222 */         char ch = Character.forDigit(ba[j] >> 4 & 0xF, 16);
/*     */ 
/*     */         
/* 225 */         if (Character.isLetter(ch)) {
/* 226 */           ch = (char)(ch - 32);
/*     */         }
/* 228 */         out.append(ch);
/* 229 */         ch = Character.forDigit(ba[j] & 0xF, 16);
/* 230 */         if (Character.isLetter(ch)) {
/* 231 */           ch = (char)(ch - 32);
/*     */         }
/* 233 */         out.append(ch);
/*     */       } 
/* 235 */       charArrayWriter.reset();
/* 236 */       needToChange = true;
/*     */     } 
/*     */     
/* 239 */     return needToChange ? out.toString() : s;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/resolver/resource/UriEncodeUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */