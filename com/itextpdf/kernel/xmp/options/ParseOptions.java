/*     */ package com.itextpdf.kernel.xmp.options;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ParseOptions
/*     */   extends Options
/*     */ {
/*     */   public static final int REQUIRE_XMP_META = 1;
/*     */   public static final int STRICT_ALIASING = 4;
/*     */   public static final int FIX_CONTROL_CHARS = 8;
/*     */   public static final int ACCEPT_LATIN_1 = 16;
/*     */   public static final int OMIT_NORMALIZATION = 32;
/*     */   
/*     */   public ParseOptions() {
/*  60 */     setOption(24, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getRequireXMPMeta() {
/*  69 */     return getOption(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParseOptions setRequireXMPMeta(boolean value) {
/*  79 */     setOption(1, value);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getStrictAliasing() {
/*  89 */     return getOption(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParseOptions setStrictAliasing(boolean value) {
/*  99 */     setOption(4, value);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFixControlChars() {
/* 109 */     return getOption(8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParseOptions setFixControlChars(boolean value) {
/* 119 */     setOption(8, value);
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAcceptLatin1() {
/* 129 */     return getOption(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParseOptions setOmitNormalization(boolean value) {
/* 139 */     setOption(32, value);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOmitNormalization() {
/* 149 */     return getOption(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParseOptions setAcceptLatin1(boolean value) {
/* 159 */     setOption(16, value);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String defineOptionName(int option) {
/* 169 */     switch (option) {
/*     */       case 1:
/* 171 */         return "REQUIRE_XMP_META";
/* 172 */       case 4: return "STRICT_ALIASING";
/* 173 */       case 8: return "FIX_CONTROL_CHARS";
/* 174 */       case 16: return "ACCEPT_LATIN_1";
/* 175 */       case 32: return "OMIT_NORMALIZATION";
/* 176 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getValidOptions() {
/* 186 */     return 61;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/options/ParseOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */