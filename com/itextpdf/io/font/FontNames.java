/*     */ package com.itextpdf.io.font;
/*     */ 
/*     */ import com.itextpdf.io.font.constants.FontWeights;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
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
/*     */ public class FontNames
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1005168842463622025L;
/*     */   protected Map<Integer, List<String[]>> allNames;
/*     */   private String[][] fullName;
/*     */   private String[][] familyName;
/*     */   private String[][] subfamily;
/*     */   private String fontName;
/*  69 */   private String style = "";
/*     */   
/*     */   private String cidFontName;
/*     */   
/*  73 */   private int weight = 400;
/*     */   
/*  75 */   private String fontStretch = "Normal";
/*     */ 
/*     */ 
/*     */   
/*     */   private int macStyle;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean allowEmbedding;
/*     */ 
/*     */ 
/*     */   
/*     */   public String[][] getNames(int id) {
/*  88 */     List<String[]> names = this.allNames.get(Integer.valueOf(id));
/*  89 */     return (names != null && names.size() > 0) ? listToArray(names) : (String[][])null;
/*     */   }
/*     */   
/*     */   public String[][] getFullName() {
/*  93 */     return this.fullName;
/*     */   }
/*     */   
/*     */   public String getFontName() {
/*  97 */     return this.fontName;
/*     */   }
/*     */   
/*     */   public String getCidFontName() {
/* 101 */     return this.cidFontName;
/*     */   }
/*     */   
/*     */   public String[][] getFamilyName() {
/* 105 */     return this.familyName;
/*     */   }
/*     */   
/*     */   public String getStyle() {
/* 109 */     return this.style;
/*     */   }
/*     */   
/*     */   public String getSubfamily() {
/* 113 */     return (this.subfamily != null) ? this.subfamily[0][3] : "";
/*     */   }
/*     */   
/*     */   public int getFontWeight() {
/* 117 */     return this.weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontWeight(int weight) {
/* 125 */     this.weight = FontWeights.normalizeFontWeight(weight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFontStretch() {
/* 134 */     return this.fontStretch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFontStretch(String fontStretch) {
/* 143 */     this.fontStretch = fontStretch;
/*     */   }
/*     */   
/*     */   public boolean allowEmbedding() {
/* 147 */     return this.allowEmbedding;
/*     */   }
/*     */   
/*     */   public boolean isBold() {
/* 151 */     return ((this.macStyle & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public boolean isItalic() {
/* 155 */     return ((this.macStyle & 0x2) != 0);
/*     */   }
/*     */   
/*     */   public boolean isUnderline() {
/* 159 */     return ((this.macStyle & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public boolean isOutline() {
/* 163 */     return ((this.macStyle & 0x8) != 0);
/*     */   }
/*     */   
/*     */   public boolean isShadow() {
/* 167 */     return ((this.macStyle & 0x10) != 0);
/*     */   }
/*     */   
/*     */   public boolean isCondensed() {
/* 171 */     return ((this.macStyle & 0x20) != 0);
/*     */   }
/*     */   
/*     */   public boolean isExtended() {
/* 175 */     return ((this.macStyle & 0x40) != 0);
/*     */   }
/*     */   
/*     */   protected void setAllNames(Map<Integer, List<String[]>> allNames) {
/* 179 */     this.allNames = allNames;
/*     */   }
/*     */   
/*     */   protected void setFullName(String[][] fullName) {
/* 183 */     this.fullName = fullName;
/*     */   }
/*     */   
/*     */   protected void setFullName(String fullName) {
/* 187 */     this.fullName = new String[][] { { "", "", "", fullName } };
/*     */   }
/*     */   
/*     */   protected void setFontName(String psFontName) {
/* 191 */     this.fontName = psFontName;
/*     */   }
/*     */   
/*     */   protected void setCidFontName(String cidFontName) {
/* 195 */     this.cidFontName = cidFontName;
/*     */   }
/*     */   
/*     */   protected void setFamilyName(String[][] familyName) {
/* 199 */     this.familyName = familyName;
/*     */   }
/*     */   
/*     */   protected void setFamilyName(String familyName) {
/* 203 */     this.familyName = new String[][] { { "", "", "", familyName } };
/*     */   }
/*     */   
/*     */   protected void setStyle(String style) {
/* 207 */     this.style = style;
/*     */   }
/*     */   
/*     */   protected void setSubfamily(String subfamily) {
/* 211 */     this.subfamily = new String[][] { { "", "", "", subfamily } };
/*     */   }
/*     */   
/*     */   protected void setSubfamily(String[][] subfamily) {
/* 215 */     this.subfamily = subfamily;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setMacStyle(int macStyle) {
/* 225 */     this.macStyle = macStyle;
/*     */   }
/*     */   
/*     */   protected int getMacStyle() {
/* 229 */     return this.macStyle;
/*     */   }
/*     */   
/*     */   protected void setAllowEmbedding(boolean allowEmbedding) {
/* 233 */     this.allowEmbedding = allowEmbedding;
/*     */   }
/*     */   
/*     */   private String[][] listToArray(List<String[]> list) {
/* 237 */     String[][] array = new String[list.size()][];
/* 238 */     for (int i = 0; i < list.size(); i++) {
/* 239 */       array[i] = list.get(i);
/*     */     }
/* 241 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 246 */     String name = getFontName();
/* 247 */     return (name.length() > 0) ? name : super.toString();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/FontNames.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */