/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontProgramDescriptor
/*     */ {
/*     */   private final String fontName;
/*     */   private final String fullNameLowerCase;
/*     */   private final String fontNameLowerCase;
/*     */   private final String familyNameLowerCase;
/*     */   private final String style;
/*     */   private final int macStyle;
/*     */   private final int weight;
/*     */   private final float italicAngle;
/*     */   private final boolean isMonospace;
/*     */   private final Set<String> fullNamesAllLangs;
/*     */   private final Set<String> fullNamesEnglishOpenType;
/*     */   private final String familyNameEnglishOpenType;
/*  75 */   private static final String[] TT_FAMILY_ORDER = new String[] { "3", "1", "1033", "3", "0", "1033", "1", "0", "0", "0", "3", "0" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FontProgramDescriptor(FontNames fontNames, float italicAngle, boolean isMonospace) {
/*  83 */     this.fontName = fontNames.getFontName();
/*  84 */     this.fontNameLowerCase = this.fontName.toLowerCase();
/*  85 */     this.fullNameLowerCase = fontNames.getFullName()[0][3].toLowerCase();
/*  86 */     this.familyNameLowerCase = (fontNames.getFamilyName() != null && fontNames.getFamilyName()[0][3] != null) ? fontNames.getFamilyName()[0][3].toLowerCase() : null;
/*  87 */     this.style = fontNames.getStyle();
/*  88 */     this.weight = fontNames.getFontWeight();
/*  89 */     this.macStyle = fontNames.getMacStyle();
/*  90 */     this.italicAngle = italicAngle;
/*  91 */     this.isMonospace = isMonospace;
/*  92 */     this.familyNameEnglishOpenType = extractFamilyNameEnglishOpenType(fontNames);
/*  93 */     this.fullNamesAllLangs = extractFullFontNames(fontNames);
/*  94 */     this.fullNamesEnglishOpenType = extractFullNamesEnglishOpenType(fontNames);
/*     */   }
/*     */   
/*     */   FontProgramDescriptor(FontNames fontNames, FontMetrics fontMetrics) {
/*  98 */     this(fontNames, fontMetrics.getItalicAngle(), fontMetrics.isFixedPitch());
/*     */   }
/*     */   
/*     */   public String getFontName() {
/* 102 */     return this.fontName;
/*     */   }
/*     */   
/*     */   public String getStyle() {
/* 106 */     return this.style;
/*     */   }
/*     */   
/*     */   public int getFontWeight() {
/* 110 */     return this.weight;
/*     */   }
/*     */   
/*     */   public float getItalicAngle() {
/* 114 */     return this.italicAngle;
/*     */   }
/*     */   
/*     */   public boolean isMonospace() {
/* 118 */     return this.isMonospace;
/*     */   }
/*     */   
/*     */   public boolean isBold() {
/* 122 */     return ((this.macStyle & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public boolean isItalic() {
/* 126 */     return ((this.macStyle & 0x2) != 0);
/*     */   }
/*     */   
/*     */   public String getFullNameLowerCase() {
/* 130 */     return this.fullNameLowerCase;
/*     */   }
/*     */   
/*     */   public String getFontNameLowerCase() {
/* 134 */     return this.fontNameLowerCase;
/*     */   }
/*     */   
/*     */   public String getFamilyNameLowerCase() {
/* 138 */     return this.familyNameLowerCase;
/*     */   }
/*     */   public Set<String> getFullNameAllLangs() {
/* 141 */     return this.fullNamesAllLangs;
/*     */   } public Set<String> getFullNamesEnglishOpenType() {
/* 143 */     return this.fullNamesEnglishOpenType;
/*     */   } String getFamilyNameEnglishOpenType() {
/* 145 */     return this.familyNameEnglishOpenType;
/*     */   }
/*     */   private Set<String> extractFullFontNames(FontNames fontNames) {
/* 148 */     Set<String> uniqueFullNames = new HashSet<>();
/* 149 */     for (String[] fullName : fontNames.getFullName())
/* 150 */       uniqueFullNames.add(fullName[3].toLowerCase()); 
/* 151 */     return uniqueFullNames;
/*     */   }
/*     */   
/*     */   private String extractFamilyNameEnglishOpenType(FontNames fontNames) {
/* 155 */     if (fontNames.getFamilyName() != null) {
/* 156 */       for (int k = 0; k < TT_FAMILY_ORDER.length; k += 3) {
/* 157 */         for (String[] name : fontNames.getFamilyName()) {
/* 158 */           if (TT_FAMILY_ORDER[k].equals(name[0]) && TT_FAMILY_ORDER[k + 1].equals(name[1]) && TT_FAMILY_ORDER[k + 2].equals(name[2])) {
/* 159 */             return name[3].toLowerCase();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   private Set<String> extractFullNamesEnglishOpenType(FontNames fontNames) {
/* 168 */     if (this.familyNameEnglishOpenType != null) {
/* 169 */       Set<String> uniqueTtfSuitableFullNames = new HashSet<>();
/* 170 */       String[][] names = fontNames.getFullName();
/* 171 */       for (String[] name : names) {
/* 172 */         for (int k = 0; k < TT_FAMILY_ORDER.length; k += 3) {
/* 173 */           if (TT_FAMILY_ORDER[k].equals(name[0]) && TT_FAMILY_ORDER[k + 1].equals(name[1]) && TT_FAMILY_ORDER[k + 2].equals(name[2])) {
/* 174 */             uniqueTtfSuitableFullNames.add(name[3]);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 179 */       return uniqueTtfSuitableFullNames;
/*     */     } 
/* 181 */     return new HashSet<>();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontProgramDescriptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */