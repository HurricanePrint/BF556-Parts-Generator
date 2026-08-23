/*     */ package com.itextpdf.kernel.numbering;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GreekAlphabetNumbering
/*     */ {
/*  62 */   protected static final char[] ALPHABET_LOWERCASE = new char[24];
/*  63 */   protected static final char[] ALPHABET_UPPERCASE = new char[24]; static {
/*  64 */     for (int i = 0; i < 24; i++) {
/*  65 */       ALPHABET_LOWERCASE[i] = (char)(945 + i + ((i > 16) ? 1 : 0));
/*  66 */       ALPHABET_UPPERCASE[i] = (char)(913 + i + ((i > 16) ? 1 : 0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int ALPHABET_LENGTH = 24;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toGreekAlphabetNumberLowerCase(int number) {
/*  80 */     return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_LOWERCASE);
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
/*     */   public static String toGreekAlphabetNumberUpperCase(int number) {
/*  93 */     return AlphabetNumbering.toAlphabetNumber(number, ALPHABET_UPPERCASE);
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
/*     */   public static String toGreekAlphabetNumber(int number, boolean upperCase) {
/* 108 */     return toGreekAlphabetNumber(number, upperCase, false);
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
/*     */   public static String toGreekAlphabetNumber(int number, boolean upperCase, boolean symbolFont) {
/* 128 */     String result = upperCase ? toGreekAlphabetNumberUpperCase(number) : toGreekAlphabetNumberLowerCase(number);
/* 129 */     if (symbolFont) {
/* 130 */       StringBuilder symbolFontStr = new StringBuilder();
/* 131 */       for (int i = 0; i < result.length(); i++) {
/* 132 */         symbolFontStr.append(getSymbolFontChar(result.charAt(i)));
/*     */       }
/* 134 */       return symbolFontStr.toString();
/*     */     } 
/* 136 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char getSymbolFontChar(char unicodeChar) {
/* 147 */     switch (unicodeChar) {
/*     */       
/*     */       case 'Α':
/* 150 */         return 'A';
/*     */       
/*     */       case 'Β':
/* 153 */         return 'B';
/*     */       
/*     */       case 'Γ':
/* 156 */         return 'G';
/*     */       
/*     */       case 'Δ':
/* 159 */         return 'D';
/*     */       
/*     */       case 'Ε':
/* 162 */         return 'E';
/*     */       
/*     */       case 'Ζ':
/* 165 */         return 'Z';
/*     */       
/*     */       case 'Η':
/* 168 */         return 'H';
/*     */       
/*     */       case 'Θ':
/* 171 */         return 'Q';
/*     */       
/*     */       case 'Ι':
/* 174 */         return 'I';
/*     */       
/*     */       case 'Κ':
/* 177 */         return 'K';
/*     */       
/*     */       case 'Λ':
/* 180 */         return 'L';
/*     */       
/*     */       case 'Μ':
/* 183 */         return 'M';
/*     */       
/*     */       case 'Ν':
/* 186 */         return 'N';
/*     */       
/*     */       case 'Ξ':
/* 189 */         return 'X';
/*     */       
/*     */       case 'Ο':
/* 192 */         return 'O';
/*     */       
/*     */       case 'Π':
/* 195 */         return 'P';
/*     */       
/*     */       case 'Ρ':
/* 198 */         return 'R';
/*     */       
/*     */       case 'Σ':
/* 201 */         return 'S';
/*     */       
/*     */       case 'Τ':
/* 204 */         return 'T';
/*     */       
/*     */       case 'Υ':
/* 207 */         return 'U';
/*     */       
/*     */       case 'Φ':
/* 210 */         return 'F';
/*     */       
/*     */       case 'Χ':
/* 213 */         return 'C';
/*     */       
/*     */       case 'Ψ':
/* 216 */         return 'Y';
/*     */       
/*     */       case 'Ω':
/* 219 */         return 'W';
/*     */       
/*     */       case 'α':
/* 222 */         return 'a';
/*     */       
/*     */       case 'β':
/* 225 */         return 'b';
/*     */       
/*     */       case 'γ':
/* 228 */         return 'g';
/*     */       
/*     */       case 'δ':
/* 231 */         return 'd';
/*     */       
/*     */       case 'ε':
/* 234 */         return 'e';
/*     */       
/*     */       case 'ζ':
/* 237 */         return 'z';
/*     */       
/*     */       case 'η':
/* 240 */         return 'h';
/*     */       
/*     */       case 'θ':
/* 243 */         return 'q';
/*     */       
/*     */       case 'ι':
/* 246 */         return 'i';
/*     */       
/*     */       case 'κ':
/* 249 */         return 'k';
/*     */       
/*     */       case 'λ':
/* 252 */         return 'l';
/*     */       
/*     */       case 'μ':
/* 255 */         return 'm';
/*     */       
/*     */       case 'ν':
/* 258 */         return 'n';
/*     */       
/*     */       case 'ξ':
/* 261 */         return 'x';
/*     */       
/*     */       case 'ο':
/* 264 */         return 'o';
/*     */       
/*     */       case 'π':
/* 267 */         return 'p';
/*     */       
/*     */       case 'ρ':
/* 270 */         return 'r';
/*     */       
/*     */       case 'ς':
/* 273 */         return 'V';
/*     */       
/*     */       case 'σ':
/* 276 */         return 's';
/*     */       
/*     */       case 'τ':
/* 279 */         return 't';
/*     */       
/*     */       case 'υ':
/* 282 */         return 'u';
/*     */       
/*     */       case 'φ':
/* 285 */         return 'f';
/*     */       
/*     */       case 'χ':
/* 288 */         return 'c';
/*     */       
/*     */       case 'ψ':
/* 291 */         return 'y';
/*     */       
/*     */       case 'ω':
/* 294 */         return 'w';
/*     */     } 
/* 296 */     return ' ';
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/numbering/GreekAlphabetNumbering.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */