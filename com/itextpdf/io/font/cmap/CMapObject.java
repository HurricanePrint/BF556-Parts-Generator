/*     */ package com.itextpdf.io.font.cmap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CMapObject
/*     */ {
/*     */   protected static final int STRING = 1;
/*     */   protected static final int HEX_STRING = 2;
/*     */   protected static final int NAME = 3;
/*     */   protected static final int NUMBER = 4;
/*     */   protected static final int LITERAL = 5;
/*     */   protected static final int ARRAY = 6;
/*     */   protected static final int DICTIONARY = 7;
/*     */   protected static final int TOKEN = 8;
/*     */   private int type;
/*     */   private Object value;
/*     */   
/*     */   public CMapObject(int objectType, Object value) {
/*  61 */     this.type = objectType;
/*  62 */     this.value = value;
/*     */   }
/*     */   
/*     */   public Object getValue() {
/*  66 */     return this.value;
/*     */   }
/*     */   
/*     */   public int getType() {
/*  70 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/*  74 */     this.value = value;
/*     */   }
/*     */   
/*     */   public boolean isString() {
/*  78 */     return (this.type == 1 || this.type == 2);
/*     */   }
/*     */   
/*     */   public boolean isHexString() {
/*  82 */     return (this.type == 2);
/*     */   }
/*     */   
/*     */   public boolean isName() {
/*  86 */     return (this.type == 3);
/*     */   }
/*     */   
/*     */   public boolean isNumber() {
/*  90 */     return (this.type == 4);
/*     */   }
/*     */   
/*     */   public boolean isLiteral() {
/*  94 */     return (this.type == 5);
/*     */   }
/*     */   
/*     */   public boolean isArray() {
/*  98 */     return (this.type == 6);
/*     */   }
/*     */   
/*     */   public boolean isDictionary() {
/* 102 */     return (this.type == 7);
/*     */   }
/*     */   
/*     */   public boolean isToken() {
/* 106 */     return (this.type == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     if (this.type == 1 || this.type == 2) {
/* 115 */       byte[] content = (byte[])this.value;
/* 116 */       StringBuilder str = new StringBuilder(content.length);
/* 117 */       for (byte b : content) {
/* 118 */         str.append((char)(b & 0xFF));
/*     */       }
/* 120 */       return str.toString();
/*     */     } 
/* 122 */     return this.value.toString();
/*     */   }
/*     */   
/*     */   public byte[] toHexByteArray() {
/* 126 */     if (this.type == 2) {
/* 127 */       return (byte[])this.value;
/*     */     }
/* 129 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */