/*     */ package com.itextpdf.kernel.xmp.options;
/*     */ 
/*     */ import com.itextpdf.kernel.xmp.XMPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PropertyOptions
/*     */   extends Options
/*     */ {
/*     */   public static final int NO_OPTIONS = 0;
/*     */   public static final int URI = 2;
/*     */   public static final int HAS_QUALIFIERS = 16;
/*     */   public static final int QUALIFIER = 32;
/*     */   public static final int HAS_LANGUAGE = 64;
/*     */   public static final int HAS_TYPE = 128;
/*     */   public static final int STRUCT = 256;
/*     */   public static final int ARRAY = 512;
/*     */   public static final int ARRAY_ORDERED = 1024;
/*     */   public static final int ARRAY_ALTERNATE = 2048;
/*     */   public static final int ARRAY_ALT_TEXT = 4096;
/*     */   public static final int SCHEMA_NODE = -2147483648;
/*     */   public static final int DELETE_EXISTING = 536870912;
/*     */   public static final int SEPARATE_NODE = 1073741824;
/*     */   
/*     */   public PropertyOptions() {}
/*     */   
/*     */   public PropertyOptions(int options) throws XMPException {
/*  93 */     super(options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isURI() {
/* 103 */     return getOption(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setURI(boolean value) {
/* 113 */     setOption(2, value);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHasQualifiers() {
/* 125 */     return getOption(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setHasQualifiers(boolean value) {
/* 135 */     setOption(16, value);
/* 136 */     return this;
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
/*     */   public boolean isQualifier() {
/* 148 */     return getOption(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setQualifier(boolean value) {
/* 158 */     setOption(32, value);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHasLanguage() {
/* 166 */     return getOption(64);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setHasLanguage(boolean value) {
/* 176 */     setOption(64, value);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHasType() {
/* 184 */     return getOption(128);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setHasType(boolean value) {
/* 194 */     setOption(128, value);
/* 195 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStruct() {
/* 202 */     return getOption(256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setStruct(boolean value) {
/* 212 */     setOption(256, value);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray() {
/* 223 */     return getOption(512);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setArray(boolean value) {
/* 233 */     setOption(512, value);
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArrayOrdered() {
/* 244 */     return getOption(1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setArrayOrdered(boolean value) {
/* 254 */     setOption(1024, value);
/* 255 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArrayAlternate() {
/* 265 */     return getOption(2048);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setArrayAlternate(boolean value) {
/* 275 */     setOption(2048, value);
/* 276 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArrayAltText() {
/* 287 */     return getOption(4096);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setArrayAltText(boolean value) {
/* 297 */     setOption(4096, value);
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSchemaNode() {
/* 306 */     return getOption(-2147483648);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyOptions setSchemaNode(boolean value) {
/* 316 */     setOption(-2147483648, value);
/* 317 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCompositeProperty() {
/* 328 */     return ((getOptions() & 0x300) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSimple() {
/* 337 */     return ((getOptions() & 0x300) == 0);
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
/*     */   public boolean equalArrayTypes(PropertyOptions options) {
/* 349 */     return (
/* 350 */       isArray() == options.isArray() && 
/* 351 */       isArrayOrdered() == options.isArrayOrdered() && 
/* 352 */       isArrayAlternate() == options.isArrayAlternate() && 
/* 353 */       isArrayAltText() == options.isArrayAltText());
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
/*     */   public void mergeWith(PropertyOptions options) throws XMPException {
/* 366 */     if (options != null)
/*     */     {
/* 368 */       setOptions(getOptions() | options.getOptions());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnlyArrayOptions() {
/* 378 */     return ((getOptions() & 0xFFFFE1FF) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getValidOptions() {
/* 388 */     return -1073733646;
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
/*     */   protected String defineOptionName(int option) {
/* 409 */     switch (option) {
/*     */       case 2:
/* 411 */         return "URI";
/* 412 */       case 16: return "HAS_QUALIFIER";
/* 413 */       case 32: return "QUALIFIER";
/* 414 */       case 64: return "HAS_LANGUAGE";
/* 415 */       case 128: return "HAS_TYPE";
/* 416 */       case 256: return "STRUCT";
/* 417 */       case 512: return "ARRAY";
/* 418 */       case 1024: return "ARRAY_ORDERED";
/* 419 */       case 2048: return "ARRAY_ALTERNATE";
/* 420 */       case 4096: return "ARRAY_ALT_TEXT";
/* 421 */       case -2147483648: return "SCHEMA_NODE";
/* 422 */     }  return null;
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
/*     */   public void assertConsistency(int options) throws XMPException {
/* 436 */     if ((options & 0x100) > 0 && (options & 0x200) > 0)
/*     */     {
/* 438 */       throw new XMPException("IsStruct and IsArray options are mutually exclusive", 103);
/*     */     }
/*     */     
/* 441 */     if ((options & 0x2) > 0 && (options & 0x300) > 0)
/*     */     {
/* 443 */       throw new XMPException("Structs and arrays can't have \"value\" options", 103);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/xmp/options/PropertyOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */