/*     */ package com.itextpdf.barcodes;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.colors.Color;
/*     */ import com.itextpdf.kernel.font.PdfFont;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfDocument;
/*     */ import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.MemoryImageSource;
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
/*     */ public class Barcode128
/*     */   extends Barcode1D
/*     */ {
/*     */   public static final int CODE128 = 1;
/*     */   public static final int CODE128_UCC = 2;
/*     */   public static final int CODE128_RAW = 3;
/*  67 */   private static final byte[][] BARS = new byte[][] { { 2, 1, 2, 2, 2, 2 }, { 2, 2, 2, 1, 2, 2 }, { 2, 2, 2, 2, 2, 1 }, { 1, 2, 1, 2, 2, 3 }, { 1, 2, 1, 3, 2, 2 }, { 1, 3, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 3 }, { 1, 2, 2, 3, 1, 2 }, { 1, 3, 2, 2, 1, 2 }, { 2, 2, 1, 2, 1, 3 }, { 2, 2, 1, 3, 1, 2 }, { 2, 3, 1, 2, 1, 2 }, { 1, 1, 2, 2, 3, 2 }, { 1, 2, 2, 1, 3, 2 }, { 1, 2, 2, 2, 3, 1 }, { 1, 1, 3, 2, 2, 2 }, { 1, 2, 3, 1, 2, 2 }, { 1, 2, 3, 2, 2, 1 }, { 2, 2, 3, 2, 1, 1 }, { 2, 2, 1, 1, 3, 2 }, { 2, 2, 1, 2, 3, 1 }, { 2, 1, 3, 2, 1, 2 }, { 2, 2, 3, 1, 1, 2 }, { 3, 1, 2, 1, 3, 1 }, { 3, 1, 1, 2, 2, 2 }, { 3, 2, 1, 1, 2, 2 }, { 3, 2, 1, 2, 2, 1 }, { 3, 1, 2, 2, 1, 2 }, { 3, 2, 2, 1, 1, 2 }, { 3, 2, 2, 2, 1, 1 }, { 2, 1, 2, 1, 2, 3 }, { 2, 1, 2, 3, 2, 1 }, { 2, 3, 2, 1, 2, 1 }, { 1, 1, 1, 3, 2, 3 }, { 1, 3, 1, 1, 2, 3 }, { 1, 3, 1, 3, 2, 1 }, { 1, 1, 2, 3, 1, 3 }, { 1, 3, 2, 1, 1, 3 }, { 1, 3, 2, 3, 1, 1 }, { 2, 1, 1, 3, 1, 3 }, { 2, 3, 1, 1, 1, 3 }, { 2, 3, 1, 3, 1, 1 }, { 1, 1, 2, 1, 3, 3 }, { 1, 1, 2, 3, 3, 1 }, { 1, 3, 2, 1, 3, 1 }, { 1, 1, 3, 1, 2, 3 }, { 1, 1, 3, 3, 2, 1 }, { 1, 3, 3, 1, 2, 1 }, { 3, 1, 3, 1, 2, 1 }, { 2, 1, 1, 3, 3, 1 }, { 2, 3, 1, 1, 3, 1 }, { 2, 1, 3, 1, 1, 3 }, { 2, 1, 3, 3, 1, 1 }, { 2, 1, 3, 1, 3, 1 }, { 3, 1, 1, 1, 2, 3 }, { 3, 1, 1, 3, 2, 1 }, { 3, 3, 1, 1, 2, 1 }, { 3, 1, 2, 1, 1, 3 }, { 3, 1, 2, 3, 1, 1 }, { 3, 3, 2, 1, 1, 1 }, { 3, 1, 4, 1, 1, 1 }, { 2, 2, 1, 4, 1, 1 }, { 4, 3, 1, 1, 1, 1 }, { 1, 1, 1, 2, 2, 4 }, { 1, 1, 1, 4, 2, 2 }, { 1, 2, 1, 1, 2, 4 }, { 1, 2, 1, 4, 2, 1 }, { 1, 4, 1, 1, 2, 2 }, { 1, 4, 1, 2, 2, 1 }, { 1, 1, 2, 2, 1, 4 }, { 1, 1, 2, 4, 1, 2 }, { 1, 2, 2, 1, 1, 4 }, { 1, 2, 2, 4, 1, 1 }, { 1, 4, 2, 1, 1, 2 }, { 1, 4, 2, 2, 1, 1 }, { 2, 4, 1, 2, 1, 1 }, { 2, 2, 1, 1, 1, 4 }, { 4, 1, 3, 1, 1, 1 }, { 2, 4, 1, 1, 1, 2 }, { 1, 3, 4, 1, 1, 1 }, { 1, 1, 1, 2, 4, 2 }, { 1, 2, 1, 1, 4, 2 }, { 1, 2, 1, 2, 4, 1 }, { 1, 1, 4, 2, 1, 2 }, { 1, 2, 4, 1, 1, 2 }, { 1, 2, 4, 2, 1, 1 }, { 4, 1, 1, 2, 1, 2 }, { 4, 2, 1, 1, 1, 2 }, { 4, 2, 1, 2, 1, 1 }, { 2, 1, 2, 1, 4, 1 }, { 2, 1, 4, 1, 2, 1 }, { 4, 1, 2, 1, 2, 1 }, { 1, 1, 1, 1, 4, 3 }, { 1, 1, 1, 3, 4, 1 }, { 1, 3, 1, 1, 4, 1 }, { 1, 1, 4, 1, 1, 3 }, { 1, 1, 4, 3, 1, 1 }, { 4, 1, 1, 1, 1, 3 }, { 4, 1, 1, 3, 1, 1 }, { 1, 1, 3, 1, 4, 1 }, { 1, 1, 4, 1, 3, 1 }, { 3, 1, 1, 1, 4, 1 }, { 4, 1, 1, 1, 3, 1 }, { 2, 1, 1, 4, 1, 2 }, { 2, 1, 1, 2, 1, 4 }, { 2, 1, 1, 2, 3, 2 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   private static final byte[] BARS_STOP = new byte[] { 2, 3, 3, 1, 1, 1, 2 };
/*     */ 
/*     */   
/*     */   public static final char CODE_AB_TO_C = 'c';
/*     */ 
/*     */   
/*     */   public static final char CODE_AC_TO_B = 'd';
/*     */ 
/*     */   
/*     */   public static final char CODE_BC_TO_A = 'e';
/*     */ 
/*     */   
/*     */   public static final char FNC1_INDEX = 'f';
/*     */   
/*     */   public static final char START_A = 'g';
/*     */   
/*     */   public static final char START_B = 'h';
/*     */   
/*     */   public static final char START_C = 'i';
/*     */   
/*     */   public static final char FNC1 = 'Ê';
/*     */   
/*     */   public static final char DEL = 'Ã';
/*     */   
/*     */   public static final char FNC3 = 'Ä';
/*     */   
/*     */   public static final char FNC2 = 'Å';
/*     */   
/*     */   public static final char SHIFT = 'Æ';
/*     */   
/*     */   public static final char CODE_C = 'Ç';
/*     */   
/*     */   public static final char CODE_A = 'È';
/*     */   
/*     */   public static final char FNC4 = 'È';
/*     */   
/*     */   public static final char STARTA = 'Ë';
/*     */   
/*     */   public static final char STARTB = 'Ì';
/*     */   
/*     */   public static final char STARTC = 'Í';
/*     */   
/* 222 */   private static Map<Integer, Integer> ais = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private Barcode128CodeSet codeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Barcode128(PdfDocument document) {
/* 232 */     this(document, document.getDefaultFont());
/*     */   }
/*     */ 
/*     */   
/*     */   public enum Barcode128CodeSet
/*     */   {
/*     */     A, B, C, AUTO;
/*     */   }
/*     */   
/*     */   public Barcode128(PdfDocument document, PdfFont font) {
/* 242 */     super(document);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     this.codeSet = Barcode128CodeSet.AUTO;
/*     */     this.x = 0.8F;
/*     */     this.font = font;
/*     */     this.size = 8.0F;
/*     */     this.baseline = this.size;
/*     */     this.barHeight = this.size * 3.0F;
/*     */     this.textAlignment = 3;
/*     */     this.codeType = 1; } public static String removeFNC1(String code) {
/* 274 */     int len = code.length();
/* 275 */     StringBuilder buf = new StringBuilder(len);
/* 276 */     for (int k = 0; k < len; k++) {
/* 277 */       char c = code.charAt(k);
/* 278 */       if (c >= ' ' && c <= '~')
/* 279 */         buf.append(c); 
/*     */     } 
/* 281 */     return buf.toString();
/*     */   }
/*     */   public void setCodeSet(Barcode128CodeSet codeSet) {
/*     */     this.codeSet = codeSet;
/*     */   }
/*     */   public Barcode128CodeSet getCodeSet() {
/*     */     return this.codeSet;
/*     */   }
/*     */   
/*     */   public static String getHumanReadableUCCEAN(String code) {
/* 291 */     StringBuilder buf = new StringBuilder();
/* 292 */     String fnc1 = new String(new char[] { 'Ê' });
/*     */     while (true) {
/* 294 */       while (code.startsWith(fnc1)) {
/* 295 */         code = code.substring(1);
/*     */       }
/*     */       
/* 298 */       int n = 0;
/* 299 */       int idlen = 0;
/* 300 */       for (int k = 2; k < 5 && 
/* 301 */         code.length() >= k; k++) {
/*     */         
/* 303 */         int subcode = Integer.parseInt(code.substring(0, k));
/* 304 */         n = ais.containsKey(Integer.valueOf(subcode)) ? ((Integer)ais.get(Integer.valueOf(subcode))).intValue() : 0;
/* 305 */         if (n != 0) {
/* 306 */           idlen = k;
/*     */           break;
/*     */         } 
/*     */       } 
/* 310 */       if (idlen == 0)
/*     */         break; 
/* 312 */       buf.append('(').append(code.substring(0, idlen)).append(')');
/* 313 */       code = code.substring(idlen);
/* 314 */       if (n > 0) {
/* 315 */         n -= idlen;
/* 316 */         if (code.length() <= n)
/*     */           break; 
/* 318 */         buf.append(removeFNC1(code.substring(0, n)));
/* 319 */         code = code.substring(n); continue;
/*     */       } 
/* 321 */       int idx = code.indexOf('Ê');
/* 322 */       if (idx < 0)
/*     */         break; 
/* 324 */       buf.append(code.substring(0, idx));
/* 325 */       code = code.substring(idx + 1);
/*     */     } 
/*     */     
/* 328 */     buf.append(removeFNC1(code));
/* 329 */     return buf.toString();
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
/*     */   public static String getRawText(String text, boolean ucc, Barcode128CodeSet codeSet) {
/* 343 */     String out = "";
/* 344 */     int tLen = text.length();
/* 345 */     if (tLen == 0) {
/* 346 */       out = out + getStartSymbol(codeSet);
/* 347 */       if (ucc)
/* 348 */         out = out + 'f'; 
/* 349 */       return out;
/*     */     } 
/*     */     
/* 352 */     for (int k = 0; k < tLen; k++) {
/* 353 */       int i = text.charAt(k);
/* 354 */       if (i > 127 && i != 202)
/* 355 */         throw new PdfException("There are illegal characters for barcode 128 in {0}."); 
/*     */     } 
/* 357 */     int c = text.charAt(0);
/* 358 */     char currentCode = getStartSymbol(codeSet);
/* 359 */     int index = 0;
/* 360 */     if ((codeSet == Barcode128CodeSet.AUTO || codeSet == Barcode128CodeSet.C) && isNextDigits(text, index, 2)) {
/* 361 */       currentCode = 'i';
/* 362 */       out = out + currentCode;
/* 363 */       if (ucc)
/* 364 */         out = out + 'f'; 
/* 365 */       String out2 = getPackedRawDigits(text, index, 2);
/* 366 */       index += out2.charAt(0);
/* 367 */       out = out + out2.substring(1);
/* 368 */     } else if (c < 32) {
/* 369 */       currentCode = 'g';
/* 370 */       out = out + currentCode;
/* 371 */       if (ucc)
/* 372 */         out = out + 'f'; 
/* 373 */       out = out + (char)(c + 64);
/* 374 */       index++;
/*     */     } else {
/* 376 */       out = out + currentCode;
/* 377 */       if (ucc)
/* 378 */         out = out + 'f'; 
/* 379 */       if (c == 202) {
/* 380 */         out = out + 'f';
/*     */       } else {
/* 382 */         out = out + (char)(c - 32);
/* 383 */       }  index++;
/*     */     } 
/* 385 */     if (codeSet != Barcode128CodeSet.AUTO && currentCode != getStartSymbol(codeSet))
/* 386 */       throw new PdfException("There are illegal characters for barcode 128 in {0}."); 
/* 387 */     while (index < tLen) {
/* 388 */       switch (currentCode) {
/*     */         case 'g':
/* 390 */           if (codeSet == Barcode128CodeSet.AUTO && isNextDigits(text, index, 4)) {
/* 391 */             currentCode = 'i';
/* 392 */             out = out + 'c';
/* 393 */             String out2 = getPackedRawDigits(text, index, 4);
/* 394 */             index += out2.charAt(0);
/* 395 */             out = out + out2.substring(1); break;
/*     */           } 
/* 397 */           c = text.charAt(index++);
/* 398 */           if (c == 202) {
/* 399 */             out = out + 'f'; break;
/* 400 */           }  if (c > 95) {
/* 401 */             currentCode = 'h';
/* 402 */             out = out + 'd';
/* 403 */             out = out + (char)(c - 32); break;
/* 404 */           }  if (c < 32) {
/* 405 */             out = out + (char)(c + 64); break;
/*     */           } 
/* 407 */           out = out + (char)(c - 32);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 'h':
/* 412 */           if (codeSet == Barcode128CodeSet.AUTO && isNextDigits(text, index, 4)) {
/* 413 */             currentCode = 'i';
/* 414 */             out = out + 'c';
/* 415 */             String out2 = getPackedRawDigits(text, index, 4);
/* 416 */             index += out2.charAt(0);
/* 417 */             out = out + out2.substring(1); break;
/*     */           } 
/* 419 */           c = text.charAt(index++);
/* 420 */           if (c == 202) {
/* 421 */             out = out + 'f'; break;
/* 422 */           }  if (c < 32) {
/* 423 */             currentCode = 'g';
/* 424 */             out = out + 'e';
/* 425 */             out = out + (char)(c + 64); break;
/*     */           } 
/* 427 */           out = out + (char)(c - 32);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 'i':
/* 433 */           if (isNextDigits(text, index, 2)) {
/* 434 */             String out2 = getPackedRawDigits(text, index, 2);
/* 435 */             index += out2.charAt(0);
/* 436 */             out = out + out2.substring(1); break;
/*     */           } 
/* 438 */           c = text.charAt(index++);
/* 439 */           if (c == 202) {
/* 440 */             out = out + 'f'; break;
/* 441 */           }  if (c < 32) {
/* 442 */             currentCode = 'g';
/* 443 */             out = out + 'e';
/* 444 */             out = out + (char)(c + 64); break;
/*     */           } 
/* 446 */           currentCode = 'h';
/* 447 */           out = out + 'd';
/* 448 */           out = out + (char)(c - 32);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 454 */       if (codeSet != Barcode128CodeSet.AUTO && currentCode != getStartSymbol(codeSet))
/* 455 */         throw new PdfException("There are illegal characters for barcode 128 in {0}."); 
/*     */     } 
/* 457 */     return out;
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
/*     */   public static String getRawText(String text, boolean ucc) {
/* 470 */     return getRawText(text, ucc, Barcode128CodeSet.AUTO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsCode128Raw(String text) {
/* 481 */     int idx = text.indexOf('￿');
/* 482 */     if (idx >= 0)
/* 483 */       text = text.substring(0, idx); 
/* 484 */     int chk = text.charAt(0);
/* 485 */     for (int k = 1; k < text.length(); k++)
/* 486 */       chk += k * text.charAt(k); 
/* 487 */     chk %= 103;
/* 488 */     text = text + (char)chk;
/* 489 */     byte[] bars = new byte[(text.length() + 1) * 6 + 7];
/*     */     int i;
/* 491 */     for (i = 0; i < text.length(); i++)
/* 492 */       System.arraycopy(BARS[text.charAt(i)], 0, bars, i * 6, 6); 
/* 493 */     System.arraycopy(BARS_STOP, 0, bars, i * 6, 7);
/* 494 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBarcodeSize() {
/*     */     String fullCode;
/* 505 */     float fontX = 0.0F;
/* 506 */     float fontY = 0.0F;
/*     */     
/* 508 */     if (this.font != null) {
/* 509 */       if (this.baseline > 0.0F) {
/* 510 */         fontY = this.baseline - getDescender();
/*     */       } else {
/* 512 */         fontY = -this.baseline + this.size;
/*     */       } 
/* 514 */       if (this.codeType == 3) {
/* 515 */         int idx = this.code.indexOf('￿');
/* 516 */         if (idx < 0) {
/* 517 */           fullCode = "";
/*     */         } else {
/* 519 */           fullCode = this.code.substring(idx + 1);
/*     */         } 
/* 521 */       } else if (this.codeType == 2) {
/* 522 */         fullCode = getHumanReadableUCCEAN(this.code);
/*     */       } else {
/* 524 */         fullCode = removeFNC1(this.code);
/*     */       } 
/* 526 */       fontX = this.font.getWidth((this.altText != null) ? this.altText : fullCode, this.size);
/*     */     } 
/* 528 */     if (this.codeType == 3)
/* 529 */     { int idx = this.code.indexOf('￿');
/* 530 */       if (idx >= 0) {
/* 531 */         fullCode = this.code.substring(0, idx);
/*     */       } else {
/* 533 */         fullCode = this.code;
/*     */       }  }
/* 535 */     else { fullCode = getRawText(this.code, (this.codeType == 2), this.codeSet); }
/*     */     
/* 537 */     int len = fullCode.length();
/* 538 */     float fullWidth = ((len + 2) * 11) * this.x + 2.0F * this.x;
/* 539 */     fullWidth = Math.max(fullWidth, fontX);
/* 540 */     float fullHeight = this.barHeight + fontY;
/*     */     
/* 542 */     return new Rectangle(fullWidth, fullHeight);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
/*     */     String fullCode, bCode;
/* 587 */     if (this.codeType == 3) {
/* 588 */       int idx = this.code.indexOf('￿');
/* 589 */       if (idx < 0) {
/* 590 */         fullCode = "";
/*     */       } else {
/* 592 */         fullCode = this.code.substring(idx + 1);
/*     */       } 
/* 594 */     } else if (this.codeType == 2) {
/* 595 */       fullCode = getHumanReadableUCCEAN(this.code);
/*     */     } else {
/* 597 */       fullCode = removeFNC1(this.code);
/*     */     } 
/* 599 */     float fontX = 0.0F;
/* 600 */     if (this.font != null) {
/* 601 */       fontX = this.font.getWidth(fullCode = (this.altText != null) ? this.altText : fullCode, this.size);
/*     */     }
/*     */     
/* 604 */     if (this.codeType == 3)
/* 605 */     { int idx = this.code.indexOf('￿');
/* 606 */       if (idx >= 0) {
/* 607 */         bCode = this.code.substring(0, idx);
/*     */       } else {
/* 609 */         bCode = this.code;
/*     */       }  }
/* 611 */     else { bCode = getRawText(this.code, (this.codeType == 2), this.codeSet); }
/*     */     
/* 613 */     int len = bCode.length();
/* 614 */     float fullWidth = ((len + 2) * 11) * this.x + 2.0F * this.x;
/* 615 */     float barStartX = 0.0F;
/* 616 */     float textStartX = 0.0F;
/* 617 */     switch (this.textAlignment) {
/*     */       case 1:
/*     */         break;
/*     */       case 2:
/* 621 */         if (fontX > fullWidth) {
/* 622 */           barStartX = fontX - fullWidth; break;
/*     */         } 
/* 624 */         textStartX = fullWidth - fontX;
/*     */         break;
/*     */       
/*     */       default:
/* 628 */         if (fontX > fullWidth) {
/* 629 */           barStartX = (fontX - fullWidth) / 2.0F; break;
/*     */         } 
/* 631 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */         break;
/*     */     } 
/*     */     
/* 635 */     float barStartY = 0.0F;
/* 636 */     float textStartY = 0.0F;
/* 637 */     if (this.font != null) {
/* 638 */       if (this.baseline <= 0.0F) {
/* 639 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 641 */         textStartY = -getDescender();
/* 642 */         barStartY = textStartY + this.baseline;
/*     */       } 
/*     */     }
/* 645 */     byte[] bars = getBarsCode128Raw(bCode);
/* 646 */     boolean print = true;
/* 647 */     if (barColor != null) {
/* 648 */       canvas.setFillColor(barColor);
/*     */     }
/* 650 */     for (int k = 0; k < bars.length; k++) {
/* 651 */       float w = bars[k] * this.x;
/* 652 */       if (print) {
/* 653 */         canvas.rectangle(barStartX, barStartY, (w - this.inkSpreading), this.barHeight);
/*     */       }
/* 655 */       print = !print;
/* 656 */       barStartX += w;
/*     */     } 
/* 658 */     canvas.fill();
/* 659 */     if (this.font != null) {
/* 660 */       if (textColor != null) {
/* 661 */         canvas.setFillColor(textColor);
/*     */       }
/* 663 */       canvas.beginText();
/* 664 */       canvas.setFontAndSize(this.font, this.size);
/* 665 */       canvas.setTextMatrix(textStartX, textStartY);
/* 666 */       canvas.showText(fullCode);
/* 667 */       canvas.endText();
/*     */     } 
/* 669 */     return getBarcodeSize();
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
/*     */   public void setCode(String code) {
/* 682 */     if (getCodeType() == 2 && code.startsWith("(")) {
/* 683 */       int idx = 0;
/* 684 */       StringBuilder ret = new StringBuilder("");
/* 685 */       while (idx >= 0) {
/* 686 */         int end = code.indexOf(')', idx);
/* 687 */         if (end < 0) {
/* 688 */           throw new IllegalArgumentException("Badly formed ucc string");
/*     */         }
/* 690 */         String sai = code.substring(idx + 1, end);
/* 691 */         if (sai.length() < 2) {
/* 692 */           throw new IllegalArgumentException("AI is too short");
/*     */         }
/* 694 */         int ai = Integer.parseInt(sai);
/* 695 */         int len = ((Integer)ais.get(Integer.valueOf(ai))).intValue();
/* 696 */         if (len == 0) {
/* 697 */           throw new IllegalArgumentException("AI not found");
/*     */         }
/* 699 */         sai = Integer.valueOf(ai).toString();
/* 700 */         if (sai.length() == 1) {
/* 701 */           sai = "0" + sai;
/*     */         }
/* 703 */         idx = code.indexOf('(', end);
/* 704 */         int next = (idx < 0) ? code.length() : idx;
/* 705 */         ret.append(sai).append(code.substring(end + 1, next));
/* 706 */         if (len < 0) {
/* 707 */           if (idx >= 0)
/* 708 */             ret.append('Ê');  continue;
/*     */         } 
/* 710 */         if (next - end - 1 + sai.length() != len) {
/* 711 */           throw new IllegalArgumentException("Invalid AI length");
/*     */         }
/*     */       } 
/* 714 */       super.setCode(ret.toString());
/*     */     } else {
/* 716 */       super.setCode(code);
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
/*     */   public Image createAwtImage(Color foreground, Color background) {
/*     */     String bCode;
/* 730 */     int f = (foreground == null) ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
/* 731 */     int g = (background == null) ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
/* 732 */     Canvas canvas = new Canvas();
/*     */     
/* 734 */     if (this.codeType == 3) {
/* 735 */       int idx = this.code.indexOf('￿');
/* 736 */       if (idx >= 0) {
/* 737 */         bCode = this.code.substring(0, idx);
/*     */       } else {
/* 739 */         bCode = this.code;
/*     */       } 
/*     */     } else {
/* 742 */       bCode = getRawText(this.code, (this.codeType == 2));
/*     */     } 
/* 744 */     int len = bCode.length();
/* 745 */     int fullWidth = (len + 2) * 11 + 2;
/* 746 */     byte[] bars = getBarsCode128Raw(bCode);
/*     */     
/* 748 */     boolean print = true;
/* 749 */     int ptr = 0;
/* 750 */     int height = (int)this.barHeight;
/* 751 */     int[] pix = new int[fullWidth * height]; int k;
/* 752 */     for (k = 0; k < bars.length; k++) {
/* 753 */       int w = bars[k];
/* 754 */       int c = g;
/* 755 */       if (print) {
/* 756 */         c = f;
/*     */       }
/* 758 */       print = !print;
/* 759 */       for (int j = 0; j < w; j++) {
/* 760 */         pix[ptr++] = c;
/*     */       }
/*     */     } 
/* 763 */     for (k = fullWidth; k < pix.length; k += fullWidth) {
/* 764 */       System.arraycopy(pix, 0, pix, k, fullWidth);
/*     */     }
/* 766 */     return canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */   }
/*     */   
/*     */   private static char getStartSymbol(Barcode128CodeSet codeSet) {
/* 770 */     switch (codeSet) {
/*     */       case A:
/* 772 */         return 'g';
/*     */       case B:
/* 774 */         return 'h';
/*     */       case C:
/* 776 */         return 'i';
/*     */     } 
/* 778 */     return 'h';
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 783 */     ais.put(Integer.valueOf(0), Integer.valueOf(20));
/* 784 */     ais.put(Integer.valueOf(1), Integer.valueOf(16));
/* 785 */     ais.put(Integer.valueOf(2), Integer.valueOf(16));
/* 786 */     ais.put(Integer.valueOf(10), Integer.valueOf(-1));
/* 787 */     ais.put(Integer.valueOf(11), Integer.valueOf(9));
/* 788 */     ais.put(Integer.valueOf(12), Integer.valueOf(8));
/* 789 */     ais.put(Integer.valueOf(13), Integer.valueOf(8));
/* 790 */     ais.put(Integer.valueOf(15), Integer.valueOf(8));
/* 791 */     ais.put(Integer.valueOf(17), Integer.valueOf(8));
/* 792 */     ais.put(Integer.valueOf(20), Integer.valueOf(4));
/* 793 */     ais.put(Integer.valueOf(21), Integer.valueOf(-1));
/* 794 */     ais.put(Integer.valueOf(22), Integer.valueOf(-1));
/* 795 */     ais.put(Integer.valueOf(23), Integer.valueOf(-1));
/* 796 */     ais.put(Integer.valueOf(240), Integer.valueOf(-1));
/* 797 */     ais.put(Integer.valueOf(241), Integer.valueOf(-1));
/* 798 */     ais.put(Integer.valueOf(250), Integer.valueOf(-1));
/* 799 */     ais.put(Integer.valueOf(251), Integer.valueOf(-1));
/* 800 */     ais.put(Integer.valueOf(252), Integer.valueOf(-1));
/* 801 */     ais.put(Integer.valueOf(30), Integer.valueOf(-1)); int k;
/* 802 */     for (k = 3100; k < 3700; k++) {
/* 803 */       ais.put(Integer.valueOf(k), Integer.valueOf(10));
/*     */     }
/* 805 */     ais.put(Integer.valueOf(37), Integer.valueOf(-1));
/* 806 */     for (k = 3900; k < 3940; k++) {
/* 807 */       ais.put(Integer.valueOf(k), Integer.valueOf(-1));
/*     */     }
/* 809 */     ais.put(Integer.valueOf(400), Integer.valueOf(-1));
/* 810 */     ais.put(Integer.valueOf(401), Integer.valueOf(-1));
/* 811 */     ais.put(Integer.valueOf(402), Integer.valueOf(20));
/* 812 */     ais.put(Integer.valueOf(403), Integer.valueOf(-1));
/* 813 */     for (k = 410; k < 416; k++) {
/* 814 */       ais.put(Integer.valueOf(k), Integer.valueOf(16));
/*     */     }
/* 816 */     ais.put(Integer.valueOf(420), Integer.valueOf(-1));
/* 817 */     ais.put(Integer.valueOf(421), Integer.valueOf(-1));
/* 818 */     ais.put(Integer.valueOf(422), Integer.valueOf(6));
/* 819 */     ais.put(Integer.valueOf(423), Integer.valueOf(-1));
/* 820 */     ais.put(Integer.valueOf(424), Integer.valueOf(6));
/* 821 */     ais.put(Integer.valueOf(425), Integer.valueOf(6));
/* 822 */     ais.put(Integer.valueOf(426), Integer.valueOf(6));
/* 823 */     ais.put(Integer.valueOf(7001), Integer.valueOf(17));
/* 824 */     ais.put(Integer.valueOf(7002), Integer.valueOf(-1));
/* 825 */     for (k = 7030; k < 7040; k++) {
/* 826 */       ais.put(Integer.valueOf(k), Integer.valueOf(-1));
/*     */     }
/* 828 */     ais.put(Integer.valueOf(8001), Integer.valueOf(18));
/* 829 */     ais.put(Integer.valueOf(8002), Integer.valueOf(-1));
/* 830 */     ais.put(Integer.valueOf(8003), Integer.valueOf(-1));
/* 831 */     ais.put(Integer.valueOf(8004), Integer.valueOf(-1));
/* 832 */     ais.put(Integer.valueOf(8005), Integer.valueOf(10));
/* 833 */     ais.put(Integer.valueOf(8006), Integer.valueOf(22));
/* 834 */     ais.put(Integer.valueOf(8007), Integer.valueOf(-1));
/* 835 */     ais.put(Integer.valueOf(8008), Integer.valueOf(-1));
/* 836 */     ais.put(Integer.valueOf(8018), Integer.valueOf(22));
/* 837 */     ais.put(Integer.valueOf(8020), Integer.valueOf(-1));
/* 838 */     ais.put(Integer.valueOf(8100), Integer.valueOf(10));
/* 839 */     ais.put(Integer.valueOf(8101), Integer.valueOf(14));
/* 840 */     ais.put(Integer.valueOf(8102), Integer.valueOf(6));
/* 841 */     for (k = 90; k < 100; k++) {
/* 842 */       ais.put(Integer.valueOf(k), Integer.valueOf(-1));
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
/*     */   
/*     */   static boolean isNextDigits(String text, int textIndex, int numDigits) {
/* 856 */     int len = text.length();
/* 857 */     while (textIndex < len && numDigits > 0) {
/* 858 */       if (text.charAt(textIndex) == 'Ê') {
/* 859 */         textIndex++;
/*     */         continue;
/*     */       } 
/* 862 */       int n = Math.min(2, numDigits);
/* 863 */       if (textIndex + n > len) {
/* 864 */         return false;
/*     */       }
/* 866 */       while (n-- > 0) {
/* 867 */         char c = text.charAt(textIndex++);
/* 868 */         if (c < '0' || c > '9') {
/* 869 */           return false;
/*     */         }
/* 871 */         numDigits--;
/*     */       } 
/*     */     } 
/* 874 */     return (numDigits == 0);
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
/*     */   static String getPackedRawDigits(String text, int textIndex, int numDigits) {
/* 887 */     StringBuilder out = new StringBuilder("");
/* 888 */     int start = textIndex;
/* 889 */     while (numDigits > 0) {
/* 890 */       if (text.charAt(textIndex) == 'Ê') {
/* 891 */         out.append('f');
/* 892 */         textIndex++;
/*     */         continue;
/*     */       } 
/* 895 */       numDigits -= 2;
/* 896 */       int c1 = text.charAt(textIndex++) - 48;
/* 897 */       int c2 = text.charAt(textIndex++) - 48;
/* 898 */       out.append((char)(c1 * 10 + c2));
/*     */     } 
/* 900 */     return (char)(textIndex - start) + out.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/Barcode128.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */