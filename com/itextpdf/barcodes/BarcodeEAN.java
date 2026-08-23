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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarcodeEAN
/*     */   extends Barcode1D
/*     */ {
/*     */   public static final int EAN13 = 1;
/*     */   public static final int EAN8 = 2;
/*     */   public static final int UPCA = 3;
/*     */   public static final int UPCE = 4;
/*     */   public static final int SUPP2 = 5;
/*     */   public static final int SUPP5 = 6;
/*  73 */   private static final int[] GUARD_EMPTY = new int[0];
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final int[] GUARD_UPCA = new int[] { 0, 2, 4, 6, 28, 30, 52, 54, 56, 58 };
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final int[] GUARD_EAN13 = new int[] { 0, 2, 28, 30, 56, 58 };
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final int[] GUARD_EAN8 = new int[] { 0, 2, 20, 22, 40, 42 };
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static final int[] GUARD_UPCE = new int[] { 0, 2, 28, 30, 32 };
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static final float[] TEXTPOS_EAN13 = new float[] { 6.5F, 13.5F, 20.5F, 27.5F, 34.5F, 41.5F, 53.5F, 60.5F, 67.5F, 74.5F, 81.5F, 88.5F };
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final float[] TEXTPOS_EAN8 = new float[] { 6.5F, 13.5F, 20.5F, 27.5F, 39.5F, 46.5F, 53.5F, 60.5F };
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final byte[][] BARS = new byte[][] { { 3, 2, 1, 1 }, { 2, 2, 2, 1 }, { 2, 1, 2, 2 }, { 1, 4, 1, 1 }, { 1, 1, 3, 2 }, { 1, 2, 3, 1 }, { 1, 1, 1, 4 }, { 1, 3, 1, 2 }, { 1, 2, 1, 3 }, { 3, 1, 1, 2 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TOTALBARS_EAN13 = 59;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TOTALBARS_EAN8 = 43;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TOTALBARS_UPCE = 33;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TOTALBARS_SUPP2 = 13;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TOTALBARS_SUPP5 = 31;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int ODD = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int EVEN = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   private static final byte[][] PARITY13 = new byte[][] { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 1, 1 }, { 0, 0, 1, 1, 0, 1 }, { 0, 0, 1, 1, 1, 0 }, { 0, 1, 0, 0, 1, 1 }, { 0, 1, 1, 0, 0, 1 }, { 0, 1, 1, 1, 0, 0 }, { 0, 1, 0, 1, 0, 1 }, { 0, 1, 0, 1, 1, 0 }, { 0, 1, 1, 0, 1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   private static final byte[][] PARITY2 = new byte[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   private static final byte[][] PARITY5 = new byte[][] { { 1, 1, 0, 0, 0 }, { 1, 0, 1, 0, 0 }, { 1, 0, 0, 1, 0 }, { 1, 0, 0, 0, 1 }, { 0, 1, 1, 0, 0 }, { 0, 0, 1, 1, 0 }, { 0, 0, 0, 1, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 0, 0, 1 }, { 0, 0, 1, 0, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 259 */   private static final byte[][] PARITYE = new byte[][] { { 1, 1, 1, 0, 0, 0 }, { 1, 1, 0, 1, 0, 0 }, { 1, 1, 0, 0, 1, 0 }, { 1, 1, 0, 0, 0, 1 }, { 1, 0, 1, 1, 0, 0 }, { 1, 0, 0, 1, 1, 0 }, { 1, 0, 0, 0, 1, 1 }, { 1, 0, 1, 0, 1, 0 }, { 1, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeEAN(PdfDocument document) {
/* 301 */     this(document, document.getDefaultFont());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeEAN(PdfDocument document, PdfFont font) {
/* 311 */     super(document);
/* 312 */     this.x = 0.8F;
/* 313 */     this.font = font;
/* 314 */     this.size = 8.0F;
/* 315 */     this.baseline = this.size;
/* 316 */     this.barHeight = this.size * 3.0F;
/* 317 */     this.guardBars = true;
/* 318 */     this.codeType = 1;
/* 319 */     this.code = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateEANParity(String code) {
/* 329 */     int mul = 3;
/* 330 */     int total = 0;
/* 331 */     for (int k = code.length() - 1; k >= 0; k--) {
/* 332 */       int n = code.charAt(k) - 48;
/* 333 */       total += mul * n;
/* 334 */       mul ^= 0x2;
/*     */     } 
/* 336 */     return (10 - total % 10) % 10;
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
/*     */   public static String convertUPCAtoUPCE(String text) {
/* 348 */     if (text.length() != 12 || (!text.startsWith("0") && !text.startsWith("1")))
/* 349 */       return null; 
/* 350 */     if (text.substring(3, 6).equals("000") || text.substring(3, 6).equals("100") || text
/* 351 */       .substring(3, 6).equals("200")) {
/* 352 */       if (text.substring(6, 8).equals("00")) {
/* 353 */         return text.substring(0, 1) + text.substring(1, 3) + text.substring(8, 11) + text.substring(3, 4) + text.substring(11);
/*     */       }
/* 355 */     } else if (text.substring(4, 6).equals("00")) {
/* 356 */       if (text.substring(6, 9).equals("000")) {
/* 357 */         return text.substring(0, 1) + text.substring(1, 4) + text.substring(9, 11) + "3" + text.substring(11);
/*     */       }
/* 359 */     } else if (text.substring(5, 6).equals("0")) {
/* 360 */       if (text.substring(6, 10).equals("0000")) {
/* 361 */         return text.substring(0, 1) + text.substring(1, 5) + text.substring(10, 11) + "4" + text.substring(11);
/*     */       }
/* 363 */     } else if (text.charAt(10) >= '5' && 
/* 364 */       text.substring(6, 10).equals("0000")) {
/* 365 */       return text.substring(0, 1) + text.substring(1, 6) + text.substring(10, 11) + text.substring(11);
/*     */     } 
/*     */     
/* 368 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsEAN13(String _code) {
/* 378 */     int[] code = new int[_code.length()];
/* 379 */     for (int k = 0; k < code.length; k++) {
/* 380 */       code[k] = _code.charAt(k) - 48;
/*     */     }
/* 382 */     byte[] bars = new byte[59];
/* 383 */     int pb = 0;
/* 384 */     bars[pb++] = 1;
/* 385 */     bars[pb++] = 1;
/* 386 */     bars[pb++] = 1;
/* 387 */     byte[] sequence = PARITY13[code[0]]; int i;
/* 388 */     for (i = 0; i < sequence.length; i++) {
/* 389 */       int c = code[i + 1];
/* 390 */       byte[] stripes = BARS[c];
/* 391 */       if (sequence[i] == 0) {
/* 392 */         bars[pb++] = stripes[0];
/* 393 */         bars[pb++] = stripes[1];
/* 394 */         bars[pb++] = stripes[2];
/* 395 */         bars[pb++] = stripes[3];
/*     */       } else {
/* 397 */         bars[pb++] = stripes[3];
/* 398 */         bars[pb++] = stripes[2];
/* 399 */         bars[pb++] = stripes[1];
/* 400 */         bars[pb++] = stripes[0];
/*     */       } 
/*     */     } 
/* 403 */     bars[pb++] = 1;
/* 404 */     bars[pb++] = 1;
/* 405 */     bars[pb++] = 1;
/* 406 */     bars[pb++] = 1;
/* 407 */     bars[pb++] = 1;
/* 408 */     for (i = 7; i < 13; i++) {
/* 409 */       int c = code[i];
/* 410 */       byte[] stripes = BARS[c];
/* 411 */       bars[pb++] = stripes[0];
/* 412 */       bars[pb++] = stripes[1];
/* 413 */       bars[pb++] = stripes[2];
/* 414 */       bars[pb++] = stripes[3];
/*     */     } 
/* 416 */     bars[pb++] = 1;
/* 417 */     bars[pb++] = 1;
/* 418 */     bars[pb++] = 1;
/* 419 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsEAN8(String _code) {
/* 429 */     int[] code = new int[_code.length()];
/* 430 */     for (int k = 0; k < code.length; k++) {
/* 431 */       code[k] = _code.charAt(k) - 48;
/*     */     }
/* 433 */     byte[] bars = new byte[43];
/* 434 */     int pb = 0;
/* 435 */     bars[pb++] = 1;
/* 436 */     bars[pb++] = 1;
/* 437 */     bars[pb++] = 1; int i;
/* 438 */     for (i = 0; i < 4; i++) {
/* 439 */       int c = code[i];
/* 440 */       byte[] stripes = BARS[c];
/* 441 */       bars[pb++] = stripes[0];
/* 442 */       bars[pb++] = stripes[1];
/* 443 */       bars[pb++] = stripes[2];
/* 444 */       bars[pb++] = stripes[3];
/*     */     } 
/* 446 */     bars[pb++] = 1;
/* 447 */     bars[pb++] = 1;
/* 448 */     bars[pb++] = 1;
/* 449 */     bars[pb++] = 1;
/* 450 */     bars[pb++] = 1;
/* 451 */     for (i = 4; i < 8; i++) {
/* 452 */       int c = code[i];
/* 453 */       byte[] stripes = BARS[c];
/* 454 */       bars[pb++] = stripes[0];
/* 455 */       bars[pb++] = stripes[1];
/* 456 */       bars[pb++] = stripes[2];
/* 457 */       bars[pb++] = stripes[3];
/*     */     } 
/* 459 */     bars[pb++] = 1;
/* 460 */     bars[pb++] = 1;
/* 461 */     bars[pb++] = 1;
/* 462 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsUPCE(String _code) {
/* 472 */     int[] code = new int[_code.length()];
/* 473 */     for (int k = 0; k < code.length; k++) {
/* 474 */       code[k] = _code.charAt(k) - 48;
/*     */     }
/* 476 */     byte[] bars = new byte[33];
/* 477 */     boolean flip = (code[0] != 0);
/* 478 */     int pb = 0;
/* 479 */     bars[pb++] = 1;
/* 480 */     bars[pb++] = 1;
/* 481 */     bars[pb++] = 1;
/* 482 */     byte[] sequence = PARITYE[code[code.length - 1]];
/* 483 */     for (int i = 1; i < code.length - 1; i++) {
/* 484 */       int c = code[i];
/* 485 */       byte[] stripes = BARS[c];
/* 486 */       if (sequence[i - 1] == (flip ? 1 : 0)) {
/* 487 */         bars[pb++] = stripes[0];
/* 488 */         bars[pb++] = stripes[1];
/* 489 */         bars[pb++] = stripes[2];
/* 490 */         bars[pb++] = stripes[3];
/*     */       } else {
/* 492 */         bars[pb++] = stripes[3];
/* 493 */         bars[pb++] = stripes[2];
/* 494 */         bars[pb++] = stripes[1];
/* 495 */         bars[pb++] = stripes[0];
/*     */       } 
/*     */     } 
/* 498 */     bars[pb++] = 1;
/* 499 */     bars[pb++] = 1;
/* 500 */     bars[pb++] = 1;
/* 501 */     bars[pb++] = 1;
/* 502 */     bars[pb++] = 1;
/* 503 */     bars[pb++] = 1;
/* 504 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsSupplemental2(String _code) {
/* 514 */     int[] code = new int[2];
/* 515 */     for (int k = 0; k < code.length; k++) {
/* 516 */       code[k] = _code.charAt(k) - 48;
/*     */     }
/* 518 */     byte[] bars = new byte[13];
/* 519 */     int pb = 0;
/* 520 */     int parity = (code[0] * 10 + code[1]) % 4;
/* 521 */     bars[pb++] = 1;
/* 522 */     bars[pb++] = 1;
/* 523 */     bars[pb++] = 2;
/* 524 */     byte[] sequence = PARITY2[parity];
/* 525 */     for (int i = 0; i < sequence.length; i++) {
/* 526 */       if (i == 1) {
/* 527 */         bars[pb++] = 1;
/* 528 */         bars[pb++] = 1;
/*     */       } 
/* 530 */       int c = code[i];
/* 531 */       byte[] stripes = BARS[c];
/* 532 */       if (sequence[i] == 0) {
/* 533 */         bars[pb++] = stripes[0];
/* 534 */         bars[pb++] = stripes[1];
/* 535 */         bars[pb++] = stripes[2];
/* 536 */         bars[pb++] = stripes[3];
/*     */       } else {
/* 538 */         bars[pb++] = stripes[3];
/* 539 */         bars[pb++] = stripes[2];
/* 540 */         bars[pb++] = stripes[1];
/* 541 */         bars[pb++] = stripes[0];
/*     */       } 
/*     */     } 
/* 544 */     return bars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getBarsSupplemental5(String _code) {
/* 554 */     int[] code = new int[5];
/* 555 */     for (int k = 0; k < code.length; k++) {
/* 556 */       code[k] = _code.charAt(k) - 48;
/*     */     }
/* 558 */     byte[] bars = new byte[31];
/* 559 */     int pb = 0;
/* 560 */     int parity = ((code[0] + code[2] + code[4]) * 3 + (code[1] + code[3]) * 9) % 10;
/* 561 */     bars[pb++] = 1;
/* 562 */     bars[pb++] = 1;
/* 563 */     bars[pb++] = 2;
/* 564 */     byte[] sequence = PARITY5[parity];
/* 565 */     for (int i = 0; i < sequence.length; i++) {
/* 566 */       if (i != 0) {
/* 567 */         bars[pb++] = 1;
/* 568 */         bars[pb++] = 1;
/*     */       } 
/* 570 */       int c = code[i];
/* 571 */       byte[] stripes = BARS[c];
/* 572 */       if (sequence[i] == 0) {
/* 573 */         bars[pb++] = stripes[0];
/* 574 */         bars[pb++] = stripes[1];
/* 575 */         bars[pb++] = stripes[2];
/* 576 */         bars[pb++] = stripes[3];
/*     */       } else {
/* 578 */         bars[pb++] = stripes[3];
/* 579 */         bars[pb++] = stripes[2];
/* 580 */         bars[pb++] = stripes[1];
/* 581 */         bars[pb++] = stripes[0];
/*     */       } 
/*     */     } 
/* 584 */     return bars;
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
/*     */   public Rectangle getBarcodeSize() {
/* 596 */     float width, height = this.barHeight;
/* 597 */     if (this.font != null) {
/* 598 */       if (this.baseline <= 0.0F) {
/* 599 */         height += -this.baseline + this.size;
/*     */       } else {
/* 601 */         height += this.baseline - getDescender();
/*     */       } 
/*     */     }
/* 604 */     switch (this.codeType) {
/*     */       case 1:
/* 606 */         width = this.x * 95.0F;
/* 607 */         if (this.font != null) {
/* 608 */           width += this.font.getWidth(this.code.charAt(0), this.size);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 635 */         return new Rectangle(width, height);case 2: width = this.x * 67.0F; return new Rectangle(width, height);case 3: width = this.x * 95.0F; if (this.font != null) width += this.font.getWidth(this.code.charAt(0), this.size) + this.font.getWidth(this.code.charAt(11), this.size);  return new Rectangle(width, height);case 4: width = this.x * 51.0F; if (this.font != null) width += this.font.getWidth(this.code.charAt(0), this.size) + this.font.getWidth(this.code.charAt(7), this.size);  return new Rectangle(width, height);case 5: width = this.x * 20.0F; return new Rectangle(width, height);case 6: width = this.x * 47.0F; return new Rectangle(width, height);
/*     */     } 
/*     */     throw new PdfException("Invalid code type");
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
/*     */   public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
/*     */     byte[] bars;
/* 679 */     Rectangle rect = getBarcodeSize();
/* 680 */     float barStartX = 0.0F;
/* 681 */     float barStartY = 0.0F;
/* 682 */     float textStartY = 0.0F;
/* 683 */     if (this.font != null) {
/* 684 */       if (this.baseline <= 0.0F) {
/* 685 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 687 */         textStartY = -getDescender();
/* 688 */         barStartY = textStartY + this.baseline;
/*     */       } 
/*     */     }
/* 691 */     switch (this.codeType) {
/*     */       case 1:
/*     */       case 3:
/*     */       case 4:
/* 695 */         if (this.font != null) {
/* 696 */           barStartX += this.font.getWidth(this.code.charAt(0), this.size);
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 701 */     int[] guard = GUARD_EMPTY;
/* 702 */     switch (this.codeType) {
/*     */       case 1:
/* 704 */         bars = getBarsEAN13(this.code);
/* 705 */         guard = GUARD_EAN13;
/*     */         break;
/*     */       case 2:
/* 708 */         bars = getBarsEAN8(this.code);
/* 709 */         guard = GUARD_EAN8;
/*     */         break;
/*     */       case 3:
/* 712 */         bars = getBarsEAN13("0" + this.code);
/* 713 */         guard = GUARD_UPCA;
/*     */         break;
/*     */       case 4:
/* 716 */         bars = getBarsUPCE(this.code);
/* 717 */         guard = GUARD_UPCE;
/*     */         break;
/*     */       case 5:
/* 720 */         bars = getBarsSupplemental2(this.code);
/*     */         break;
/*     */       case 6:
/* 723 */         bars = getBarsSupplemental5(this.code);
/*     */         break;
/*     */       default:
/* 726 */         throw new PdfException("Invalid code type");
/*     */     } 
/* 728 */     float keepBarX = barStartX;
/* 729 */     boolean print = true;
/* 730 */     float gd = 0.0F;
/* 731 */     if (this.font != null && this.baseline > 0.0F && this.guardBars) {
/* 732 */       gd = this.baseline / 2.0F;
/*     */     }
/* 734 */     if (barColor != null)
/* 735 */       canvas.setFillColor(barColor); 
/*     */     int k;
/* 737 */     for (k = 0; k < bars.length; k++) {
/* 738 */       float w = bars[k] * this.x;
/* 739 */       if (print) {
/* 740 */         if (Arrays.binarySearch(guard, k) >= 0) {
/* 741 */           canvas.rectangle(barStartX, (barStartY - gd), (w - this.inkSpreading), (this.barHeight + gd));
/*     */         } else {
/* 743 */           canvas.rectangle(barStartX, barStartY, (w - this.inkSpreading), this.barHeight);
/*     */         } 
/*     */       }
/* 746 */       print = !print;
/* 747 */       barStartX += w;
/*     */     } 
/* 749 */     canvas.fill();
/* 750 */     if (this.font != null) {
/* 751 */       if (textColor != null) {
/* 752 */         canvas.setFillColor(textColor);
/*     */       }
/* 754 */       canvas.beginText();
/* 755 */       canvas.setFontAndSize(this.font, this.size);
/* 756 */       switch (this.codeType) {
/*     */         case 1:
/* 758 */           canvas.setTextMatrix(0.0F, textStartY);
/* 759 */           canvas.showText(this.code.substring(0, 1));
/* 760 */           for (k = 1; k < 13; k++) {
/* 761 */             String c = this.code.substring(k, k + 1);
/* 762 */             float len = this.font.getWidth(c, this.size);
/* 763 */             float pX = keepBarX + TEXTPOS_EAN13[k - 1] * this.x - len / 2.0F;
/* 764 */             canvas.setTextMatrix(pX, textStartY);
/* 765 */             canvas.showText(c);
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 769 */           for (k = 0; k < 8; k++) {
/* 770 */             String c = this.code.substring(k, k + 1);
/* 771 */             float len = this.font.getWidth(c, this.size);
/* 772 */             float pX = TEXTPOS_EAN8[k] * this.x - len / 2.0F;
/* 773 */             canvas.setTextMatrix(pX, textStartY);
/* 774 */             canvas.showText(c);
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 778 */           canvas.setTextMatrix(0.0F, textStartY);
/* 779 */           canvas.showText(this.code.substring(0, 1));
/* 780 */           for (k = 1; k < 11; k++) {
/* 781 */             String c = this.code.substring(k, k + 1);
/* 782 */             float len = this.font.getWidth(c, this.size);
/* 783 */             float pX = keepBarX + TEXTPOS_EAN13[k] * this.x - len / 2.0F;
/* 784 */             canvas.setTextMatrix(pX, textStartY);
/* 785 */             canvas.showText(c);
/*     */           } 
/* 787 */           canvas.setTextMatrix(keepBarX + this.x * 95.0F, textStartY);
/* 788 */           canvas.showText(this.code.substring(11, 12));
/*     */           break;
/*     */         case 4:
/* 791 */           canvas.setTextMatrix(0.0F, textStartY);
/* 792 */           canvas.showText(this.code.substring(0, 1));
/* 793 */           for (k = 1; k < 7; k++) {
/* 794 */             String c = this.code.substring(k, k + 1);
/* 795 */             float len = this.font.getWidth(c, this.size);
/* 796 */             float pX = keepBarX + TEXTPOS_EAN13[k - 1] * this.x - len / 2.0F;
/* 797 */             canvas.setTextMatrix(pX, textStartY);
/* 798 */             canvas.showText(c);
/*     */           } 
/* 800 */           canvas.setTextMatrix(keepBarX + this.x * 51.0F, textStartY);
/* 801 */           canvas.showText(this.code.substring(7, 8));
/*     */           break;
/*     */         case 5:
/*     */         case 6:
/* 805 */           for (k = 0; k < this.code.length(); k++) {
/* 806 */             String c = this.code.substring(k, k + 1);
/* 807 */             float len = this.font.getWidth(c, this.size);
/* 808 */             float pX = (7.5F + (9 * k)) * this.x - len / 2.0F;
/* 809 */             canvas.setTextMatrix(pX, textStartY);
/* 810 */             canvas.showText(c);
/*     */           } 
/*     */           break;
/*     */       } 
/* 814 */       canvas.endText();
/*     */     } 
/* 816 */     return rect;
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
/*     */   public Image createAwtImage(Color foreground, Color background) {
/*     */     int width;
/*     */     byte[] bars;
/* 831 */     int f = (foreground == null) ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
/* 832 */     int g = (background == null) ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
/* 833 */     Canvas canvas = new Canvas();
/*     */ 
/*     */ 
/*     */     
/* 837 */     switch (this.codeType) {
/*     */       case 1:
/* 839 */         bars = getBarsEAN13(this.code);
/* 840 */         width = 95;
/*     */         break;
/*     */       case 2:
/* 843 */         bars = getBarsEAN8(this.code);
/* 844 */         width = 67;
/*     */         break;
/*     */       case 3:
/* 847 */         bars = getBarsEAN13("0" + this.code);
/* 848 */         width = 95;
/*     */         break;
/*     */       case 4:
/* 851 */         bars = getBarsUPCE(this.code);
/* 852 */         width = 51;
/*     */         break;
/*     */       case 5:
/* 855 */         bars = getBarsSupplemental2(this.code);
/* 856 */         width = 20;
/*     */         break;
/*     */       case 6:
/* 859 */         bars = getBarsSupplemental5(this.code);
/* 860 */         width = 47;
/*     */         break;
/*     */       default:
/* 863 */         throw new PdfException("Invalid code type");
/*     */     } 
/*     */     
/* 866 */     boolean print = true;
/* 867 */     int ptr = 0;
/* 868 */     int height = (int)this.barHeight;
/* 869 */     int[] pix = new int[width * height]; int k;
/* 870 */     for (k = 0; k < bars.length; k++) {
/* 871 */       int w = bars[k];
/* 872 */       int c = g;
/* 873 */       if (print) {
/* 874 */         c = f;
/*     */       }
/* 876 */       print = !print;
/* 877 */       for (int j = 0; j < w; j++) {
/* 878 */         pix[ptr++] = c;
/*     */       }
/*     */     } 
/* 881 */     for (k = width; k < pix.length; k += width) {
/* 882 */       System.arraycopy(pix, 0, pix, k, width);
/*     */     }
/* 884 */     return canvas.createImage(new MemoryImageSource(width, height, pix, 0, width));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/BarcodeEAN.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */