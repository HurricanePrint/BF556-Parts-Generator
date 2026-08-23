/*     */ package com.itextpdf.io.font.cmap;
/*     */ 
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.io.util.TextUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CMapToUnicode
/*     */   extends AbstractCMap
/*     */ {
/*     */   private static final long serialVersionUID = 1037675640549795312L;
/*  65 */   public static CMapToUnicode EmptyCMapToUnicodeMap = new CMapToUnicode(true);
/*     */   
/*     */   private Map<Integer, char[]> byteMappings;
/*     */   
/*     */   private CMapToUnicode(boolean emptyCMap) {
/*  70 */     this.byteMappings = Collections.emptyMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CMapToUnicode() {
/*  77 */     this.byteMappings = (Map)new HashMap<>();
/*     */   }
/*     */   
/*     */   public static CMapToUnicode getIdentity() {
/*  81 */     CMapToUnicode uni = new CMapToUnicode();
/*  82 */     for (int i = 0; i < 65537; i++) {
/*  83 */       uni.addChar(i, TextUtil.convertFromUtf32(i));
/*     */     }
/*  85 */     return uni;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasByteMappings() {
/*  94 */     return (this.byteMappings.size() != 0);
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
/*     */   public char[] lookup(byte[] code, int offset, int length) {
/* 106 */     char[] result = null;
/*     */     
/* 108 */     if (length == 1) {
/* 109 */       int key = code[offset] & 0xFF;
/* 110 */       result = this.byteMappings.get(Integer.valueOf(key));
/* 111 */     } else if (length == 2) {
/* 112 */       int intKey = code[offset] & 0xFF;
/* 113 */       intKey <<= 8;
/* 114 */       intKey += code[offset + 1] & 0xFF;
/* 115 */       int key = intKey;
/* 116 */       result = this.byteMappings.get(Integer.valueOf(key));
/*     */     } 
/* 118 */     return result;
/*     */   }
/*     */   
/*     */   public char[] lookup(byte[] code) {
/* 122 */     return lookup(code, 0, code.length);
/*     */   }
/*     */   
/*     */   public char[] lookup(int code) {
/* 126 */     return this.byteMappings.get(Integer.valueOf(code));
/*     */   }
/*     */   
/*     */   public Set<Integer> getCodes() {
/* 130 */     return this.byteMappings.keySet();
/*     */   }
/*     */   
/*     */   public IntHashtable createDirectMapping() {
/* 134 */     IntHashtable result = new IntHashtable();
/* 135 */     for (Map.Entry<Integer, char[]> entry : this.byteMappings.entrySet()) {
/* 136 */       if (((char[])entry.getValue()).length == 1) {
/* 137 */         result.put(((Integer)entry.getKey()).intValue(), convertToInt(entry.getValue()));
/*     */       }
/*     */     } 
/* 140 */     return result;
/*     */   }
/*     */   
/*     */   public Map<Integer, Integer> createReverseMapping() throws IOException {
/* 144 */     Map<Integer, Integer> result = new HashMap<>();
/* 145 */     for (Map.Entry<Integer, char[]> entry : this.byteMappings.entrySet()) {
/* 146 */       if (((char[])entry.getValue()).length == 1) {
/* 147 */         result.put(Integer.valueOf(convertToInt(entry.getValue())), entry.getKey());
/*     */       }
/*     */     } 
/* 150 */     return result;
/*     */   }
/*     */   
/*     */   private int convertToInt(char[] s) {
/* 154 */     int value = 0;
/* 155 */     for (int i = 0; i < s.length - 1; i++) {
/* 156 */       value += s[i];
/* 157 */       value <<= 8;
/*     */     } 
/* 159 */     value += s[s.length - 1];
/* 160 */     return value;
/*     */   }
/*     */   
/*     */   void addChar(int cid, char[] uni) {
/* 164 */     this.byteMappings.put(Integer.valueOf(cid), uni);
/*     */   }
/*     */ 
/*     */   
/*     */   void addChar(String mark, CMapObject code) {
/* 169 */     if (mark.length() == 1) {
/* 170 */       char[] dest = createCharsFromDoubleBytes((byte[])code.getValue());
/* 171 */       this.byteMappings.put(Integer.valueOf(mark.charAt(0)), dest);
/* 172 */     } else if (mark.length() == 2) {
/* 173 */       char[] dest = createCharsFromDoubleBytes((byte[])code.getValue());
/* 174 */       this.byteMappings.put(Integer.valueOf((mark.charAt(0) << 8) + mark.charAt(1)), dest);
/*     */     } else {
/* 176 */       Logger logger = LoggerFactory.getLogger(CMapToUnicode.class);
/* 177 */       logger.warn("ToUnicode CMap more than 2 bytes not supported.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private char[] createCharsFromSingleBytes(byte[] bytes) {
/* 182 */     if (bytes.length == 1) {
/* 183 */       return new char[] { (char)(bytes[0] & 0xFF) };
/*     */     }
/* 185 */     char[] chars = new char[bytes.length];
/* 186 */     for (int i = 0; i < bytes.length; i++) {
/* 187 */       chars[i] = (char)(bytes[i] & 0xFF);
/*     */     }
/* 189 */     return chars;
/*     */   }
/*     */ 
/*     */   
/*     */   private char[] createCharsFromDoubleBytes(byte[] bytes) {
/* 194 */     char[] chars = new char[bytes.length / 2];
/* 195 */     for (int i = 0; i < bytes.length; i += 2) {
/* 196 */       chars[i / 2] = (char)(((bytes[i] & 0xFF) << 8) + (bytes[i + 1] & 0xFF));
/*     */     }
/* 198 */     return chars;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapToUnicode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */