/*     */ package com.itextpdf.io.codec.brotli.dec;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Transform
/*     */ {
/*     */   private final byte[] prefix;
/*     */   private final int type;
/*     */   private final byte[] suffix;
/*     */   
/*     */   Transform(String prefix, int type, String suffix) {
/*  42 */     this.prefix = readUniBytes(prefix);
/*  43 */     this.type = type;
/*  44 */     this.suffix = readUniBytes(suffix);
/*     */   }
/*     */   
/*     */   static byte[] readUniBytes(String uniBytes) {
/*  48 */     byte[] result = new byte[uniBytes.length()];
/*  49 */     for (int i = 0; i < result.length; i++) {
/*  50 */       result[i] = (byte)uniBytes.charAt(i);
/*     */     }
/*  52 */     return result;
/*     */   }
/*     */   
/*  55 */   static final Transform[] TRANSFORMS = new Transform[] { new Transform("", 0, ""), new Transform("", 0, " "), new Transform(" ", 0, " "), new Transform("", 12, ""), new Transform("", 10, " "), new Transform("", 0, " the "), new Transform(" ", 0, ""), new Transform("s ", 0, " "), new Transform("", 0, " of "), new Transform("", 10, ""), new Transform("", 0, " and "), new Transform("", 13, ""), new Transform("", 1, ""), new Transform(", ", 0, " "), new Transform("", 0, ", "), new Transform(" ", 10, " "), new Transform("", 0, " in "), new Transform("", 0, " to "), new Transform("e ", 0, " "), new Transform("", 0, "\""), new Transform("", 0, "."), new Transform("", 0, "\">"), new Transform("", 0, "\n"), new Transform("", 3, ""), new Transform("", 0, "]"), new Transform("", 0, " for "), new Transform("", 14, ""), new Transform("", 2, ""), new Transform("", 0, " a "), new Transform("", 0, " that "), new Transform(" ", 10, ""), new Transform("", 0, ". "), new Transform(".", 0, ""), new Transform(" ", 0, ", "), new Transform("", 15, ""), new Transform("", 0, " with "), new Transform("", 0, "'"), new Transform("", 0, " from "), new Transform("", 0, " by "), new Transform("", 16, ""), new Transform("", 17, ""), new Transform(" the ", 0, ""), new Transform("", 4, ""), new Transform("", 0, ". The "), new Transform("", 11, ""), new Transform("", 0, " on "), new Transform("", 0, " as "), new Transform("", 0, " is "), new Transform("", 7, ""), new Transform("", 1, "ing "), new Transform("", 0, "\n\t"), new Transform("", 0, ":"), new Transform(" ", 0, ". "), new Transform("", 0, "ed "), new Transform("", 20, ""), new Transform("", 18, ""), new Transform("", 6, ""), new Transform("", 0, "("), new Transform("", 10, ", "), new Transform("", 8, ""), new Transform("", 0, " at "), new Transform("", 0, "ly "), new Transform(" the ", 0, " of "), new Transform("", 5, ""), new Transform("", 9, ""), new Transform(" ", 10, ", "), new Transform("", 10, "\""), new Transform(".", 0, "("), new Transform("", 11, " "), new Transform("", 10, "\">"), new Transform("", 0, "=\""), new Transform(" ", 0, "."), new Transform(".com/", 0, ""), new Transform(" the ", 0, " of the "), new Transform("", 10, "'"), new Transform("", 0, ". This "), new Transform("", 0, ","), new Transform(".", 0, " "), new Transform("", 10, "("), new Transform("", 10, "."), new Transform("", 0, " not "), new Transform(" ", 0, "=\""), new Transform("", 0, "er "), new Transform(" ", 11, " "), new Transform("", 0, "al "), new Transform(" ", 11, ""), new Transform("", 0, "='"), new Transform("", 11, "\""), new Transform("", 10, ". "), new Transform(" ", 0, "("), new Transform("", 0, "ful "), new Transform(" ", 10, ". "), new Transform("", 0, "ive "), new Transform("", 0, "less "), new Transform("", 11, "'"), new Transform("", 0, "est "), new Transform(" ", 10, "."), new Transform("", 11, "\">"), new Transform(" ", 0, "='"), new Transform("", 10, ","), new Transform("", 0, "ize "), new Transform("", 11, "."), new Transform("Â ", 0, ""), new Transform(" ", 0, ","), new Transform("", 10, "=\""), new Transform("", 11, "=\""), new Transform("", 0, "ous "), new Transform("", 11, ", "), new Transform("", 10, "='"), new Transform(" ", 10, ","), new Transform(" ", 11, "=\""), new Transform(" ", 11, ", "), new Transform("", 11, ","), new Transform("", 11, "("), new Transform("", 11, ". "), new Transform(" ", 11, "."), new Transform("", 11, "='"), new Transform(" ", 11, ". "), new Transform(" ", 10, "=\""), new Transform(" ", 11, "='"), new Transform(" ", 10, "='") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int transformDictionaryWord(byte[] dst, int dstOffset, ByteBuffer data, int wordOffset, int len, Transform transform) {
/* 181 */     int offset = dstOffset;
/*     */ 
/*     */     
/* 184 */     byte[] string = transform.prefix;
/* 185 */     int tmp = string.length;
/* 186 */     int i = 0;
/*     */     
/* 188 */     while (i < tmp) {
/* 189 */       dst[offset++] = string[i++];
/*     */     }
/*     */ 
/*     */     
/* 193 */     int op = transform.type;
/* 194 */     tmp = WordTransformType.getOmitFirst(op);
/* 195 */     if (tmp > len) {
/* 196 */       tmp = len;
/*     */     }
/* 198 */     wordOffset += tmp;
/* 199 */     len -= tmp;
/* 200 */     len -= WordTransformType.getOmitLast(op);
/* 201 */     i = len;
/* 202 */     while (i > 0) {
/* 203 */       dst[offset++] = data.get(wordOffset++);
/* 204 */       i--;
/*     */     } 
/*     */     
/* 207 */     if (op == 11 || op == 10) {
/* 208 */       int uppercaseOffset = offset - len;
/* 209 */       if (op == 10) {
/* 210 */         len = 1;
/*     */       }
/* 212 */       while (len > 0) {
/* 213 */         tmp = dst[uppercaseOffset] & 0xFF;
/* 214 */         if (tmp < 192) {
/* 215 */           if (tmp >= 97 && tmp <= 122) {
/* 216 */             dst[uppercaseOffset] = (byte)(dst[uppercaseOffset] ^ 0x20);
/*     */           }
/* 218 */           uppercaseOffset++;
/* 219 */           len--; continue;
/* 220 */         }  if (tmp < 224) {
/* 221 */           dst[uppercaseOffset + 1] = (byte)(dst[uppercaseOffset + 1] ^ 0x20);
/* 222 */           uppercaseOffset += 2;
/* 223 */           len -= 2; continue;
/*     */         } 
/* 225 */         dst[uppercaseOffset + 2] = (byte)(dst[uppercaseOffset + 2] ^ 0x5);
/* 226 */         uppercaseOffset += 3;
/* 227 */         len -= 3;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 233 */     string = transform.suffix;
/* 234 */     tmp = string.length;
/* 235 */     i = 0;
/* 236 */     while (i < tmp) {
/* 237 */       dst[offset++] = string[i++];
/*     */     }
/*     */     
/* 240 */     return offset - dstOffset;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/codec/brotli/dec/Transform.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */