/*     */ package com.itextpdf.barcodes.qrcode;
/*     */ 
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
/*     */ final class CharacterSetECI
/*     */ {
/*     */   private static Map<String, CharacterSetECI> NAME_TO_ECI;
/*     */   private final String encodingName;
/*     */   private final int value;
/*     */   
/*     */   private static void initialize() {
/*  60 */     Map<String, CharacterSetECI> n = new HashMap<>(29);
/*  61 */     addCharacterSet(0, "Cp437", n);
/*  62 */     addCharacterSet(1, new String[] { "ISO8859_1", "ISO-8859-1" }, n);
/*  63 */     addCharacterSet(2, "Cp437", n);
/*  64 */     addCharacterSet(3, new String[] { "ISO8859_1", "ISO-8859-1" }, n);
/*  65 */     addCharacterSet(4, new String[] { "ISO8859_2", "ISO-8859-2" }, n);
/*  66 */     addCharacterSet(5, new String[] { "ISO8859_3", "ISO-8859-3" }, n);
/*  67 */     addCharacterSet(6, new String[] { "ISO8859_4", "ISO-8859-4" }, n);
/*  68 */     addCharacterSet(7, new String[] { "ISO8859_5", "ISO-8859-5" }, n);
/*  69 */     addCharacterSet(8, new String[] { "ISO8859_6", "ISO-8859-6" }, n);
/*  70 */     addCharacterSet(9, new String[] { "ISO8859_7", "ISO-8859-7" }, n);
/*  71 */     addCharacterSet(10, new String[] { "ISO8859_8", "ISO-8859-8" }, n);
/*  72 */     addCharacterSet(11, new String[] { "ISO8859_9", "ISO-8859-9" }, n);
/*  73 */     addCharacterSet(12, new String[] { "ISO8859_10", "ISO-8859-10" }, n);
/*  74 */     addCharacterSet(13, new String[] { "ISO8859_11", "ISO-8859-11" }, n);
/*  75 */     addCharacterSet(15, new String[] { "ISO8859_13", "ISO-8859-13" }, n);
/*  76 */     addCharacterSet(16, new String[] { "ISO8859_14", "ISO-8859-14" }, n);
/*  77 */     addCharacterSet(17, new String[] { "ISO8859_15", "ISO-8859-15" }, n);
/*  78 */     addCharacterSet(18, new String[] { "ISO8859_16", "ISO-8859-16" }, n);
/*  79 */     addCharacterSet(20, new String[] { "SJIS", "Shift_JIS" }, n);
/*  80 */     NAME_TO_ECI = n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CharacterSetECI(int value, String encodingName) {
/*  87 */     this.encodingName = encodingName;
/*  88 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncodingName() {
/*  95 */     return this.encodingName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 102 */     return this.value;
/*     */   }
/*     */   
/*     */   private static void addCharacterSet(int value, String encodingName, Map<String, CharacterSetECI> n) {
/* 106 */     CharacterSetECI eci = new CharacterSetECI(value, encodingName);
/* 107 */     n.put(encodingName, eci);
/*     */   }
/*     */   
/*     */   private static void addCharacterSet(int value, String[] encodingNames, Map<String, CharacterSetECI> n) {
/* 111 */     CharacterSetECI eci = new CharacterSetECI(value, encodingNames[0]);
/* 112 */     for (int i = 0; i < encodingNames.length; i++) {
/* 113 */       n.put(encodingNames[i], eci);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharacterSetECI getCharacterSetECIByName(String name) {
/* 123 */     if (NAME_TO_ECI == null) {
/* 124 */       initialize();
/*     */     }
/* 126 */     return NAME_TO_ECI.get(name);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/barcodes/qrcode/CharacterSetECI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */