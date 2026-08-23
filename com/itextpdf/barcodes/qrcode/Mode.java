/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Mode
/*     */ {
/*  55 */   public static final Mode TERMINATOR = new Mode(new int[] { 0, 0, 0 }, 0, "TERMINATOR");
/*  56 */   public static final Mode NUMERIC = new Mode(new int[] { 10, 12, 14 }, 1, "NUMERIC");
/*  57 */   public static final Mode ALPHANUMERIC = new Mode(new int[] { 9, 11, 13 }, 2, "ALPHANUMERIC");
/*     */ 
/*     */   
/*  60 */   public static final Mode STRUCTURED_APPEND = new Mode(new int[] { 0, 0, 0 }, 3, "STRUCTURED_APPEND");
/*  61 */   public static final Mode BYTE = new Mode(new int[] { 8, 16, 16 }, 4, "BYTE");
/*     */ 
/*     */   
/*  64 */   public static final Mode ECI = new Mode(null, 7, "ECI");
/*  65 */   public static final Mode KANJI = new Mode(new int[] { 8, 10, 12 }, 8, "KANJI");
/*  66 */   public static final Mode FNC1_FIRST_POSITION = new Mode(null, 5, "FNC1_FIRST_POSITION");
/*  67 */   public static final Mode FNC1_SECOND_POSITION = new Mode(null, 9, "FNC1_SECOND_POSITION");
/*     */   
/*     */   private final int[] characterCountBitsForVersions;
/*     */   private final int bits;
/*     */   private final String name;
/*     */   
/*     */   private Mode(int[] characterCountBitsForVersions, int bits, String name) {
/*  74 */     this.characterCountBitsForVersions = characterCountBitsForVersions;
/*  75 */     this.bits = bits;
/*  76 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mode forBits(int bits) {
/*  85 */     switch (bits) {
/*     */       case 0:
/*  87 */         return TERMINATOR;
/*     */       case 1:
/*  89 */         return NUMERIC;
/*     */       case 2:
/*  91 */         return ALPHANUMERIC;
/*     */       case 3:
/*  93 */         return STRUCTURED_APPEND;
/*     */       case 4:
/*  95 */         return BYTE;
/*     */       case 5:
/*  97 */         return FNC1_FIRST_POSITION;
/*     */       case 7:
/*  99 */         return ECI;
/*     */       case 8:
/* 101 */         return KANJI;
/*     */       case 9:
/* 103 */         return FNC1_SECOND_POSITION;
/*     */     } 
/* 105 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharacterCountBits(Version version) {
/*     */     int offset;
/* 115 */     if (this.characterCountBitsForVersions == null) {
/* 116 */       throw new IllegalArgumentException("Character count doesn't apply to this mode");
/*     */     }
/* 118 */     int number = version.getVersionNumber();
/*     */     
/* 120 */     if (number <= 9) {
/* 121 */       offset = 0;
/* 122 */     } else if (number <= 26) {
/* 123 */       offset = 1;
/*     */     } else {
/* 125 */       offset = 2;
/*     */     } 
/* 127 */     return this.characterCountBitsForVersions[offset];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBits() {
/* 134 */     return this.bits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 141 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 148 */     return this.name;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/Mode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */