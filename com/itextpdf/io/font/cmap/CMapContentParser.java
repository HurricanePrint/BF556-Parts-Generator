/*     */ package com.itextpdf.io.font.cmap;
/*     */ 
/*     */ import com.itextpdf.io.IOException;
/*     */ import com.itextpdf.io.font.PdfEncodings;
/*     */ import com.itextpdf.io.source.ByteBuffer;
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CMapContentParser
/*     */ {
/*     */   public static final int COMMAND_TYPE = 200;
/*     */   private PdfTokenizer tokeniser;
/*     */   
/*     */   public CMapContentParser(PdfTokenizer tokeniser) {
/*  74 */     this.tokeniser = tokeniser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(List<CMapObject> ls) throws IOException {
/*  85 */     ls.clear();
/*     */     CMapObject ob;
/*  87 */     while ((ob = readObject()) != null) {
/*  88 */       ls.add(ob);
/*     */       
/*  90 */       if (ob.isLiteral()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CMapObject readDictionary() throws IOException {
/* 101 */     Map<String, CMapObject> dic = new HashMap<>();
/*     */     while (true) {
/* 103 */       if (!nextValidToken())
/* 104 */         throw new IOException("Unexpected end of file."); 
/* 105 */       if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndDic)
/*     */         break; 
/* 107 */       if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.Other && "def".equals(this.tokeniser.getStringValue()))
/*     */         continue; 
/* 109 */       if (this.tokeniser.getTokenType() != PdfTokenizer.TokenType.Name)
/* 110 */         throw (new IOException("Dictionary key {0} is not a name.")).setMessageParams(new Object[] { this.tokeniser.getStringValue() }); 
/* 111 */       String name = this.tokeniser.getStringValue();
/* 112 */       CMapObject obj = readObject();
/* 113 */       if (obj.isToken()) {
/* 114 */         if (obj.toString().equals(">>")) {
/* 115 */           this.tokeniser.throwError("Unexpected '>>'.", new Object[0]);
/*     */         }
/* 117 */         if (obj.toString().equals("]")) {
/* 118 */           this.tokeniser.throwError("Unexpected close bracket.", new Object[0]);
/*     */         }
/*     */       } 
/* 121 */       dic.put(name, obj);
/*     */     } 
/* 123 */     return new CMapObject(7, dic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CMapObject readArray() throws IOException {
/* 132 */     List<CMapObject> array = new ArrayList<>();
/*     */     while (true) {
/* 134 */       CMapObject obj = readObject();
/* 135 */       if (obj.isToken()) {
/* 136 */         if (obj.toString().equals("]")) {
/*     */           break;
/*     */         }
/* 139 */         if (obj.toString().equals(">>")) {
/* 140 */           this.tokeniser.throwError("Unexpected '>>'.", new Object[0]);
/*     */         }
/*     */       } 
/* 143 */       array.add(obj);
/*     */     } 
/* 145 */     return new CMapObject(6, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CMapObject readObject() throws IOException {
/*     */     CMapObject obj, numObject;
/* 154 */     if (!nextValidToken())
/* 155 */       return null; 
/* 156 */     PdfTokenizer.TokenType type = this.tokeniser.getTokenType();
/* 157 */     switch (type) {
/*     */       case StartDic:
/* 159 */         return readDictionary();
/*     */       case StartArray:
/* 161 */         return readArray();
/*     */       
/*     */       case String:
/* 164 */         if (this.tokeniser.isHexString()) {
/* 165 */           obj = new CMapObject(2, PdfTokenizer.decodeStringContent(this.tokeniser.getByteContent(), true));
/*     */         } else {
/* 167 */           obj = new CMapObject(1, PdfTokenizer.decodeStringContent(this.tokeniser.getByteContent(), false));
/*     */         } 
/* 169 */         return obj;
/*     */       case Name:
/* 171 */         return new CMapObject(3, decodeName(this.tokeniser.getByteContent()));
/*     */       case Number:
/* 173 */         numObject = new CMapObject(4, null);
/*     */         try {
/* 175 */           numObject.setValue(Integer.valueOf((int)Double.parseDouble(this.tokeniser.getStringValue())));
/* 176 */         } catch (NumberFormatException e) {
/* 177 */           numObject.setValue(Integer.valueOf(-2147483648));
/*     */         } 
/* 179 */         return numObject;
/*     */       case Other:
/* 181 */         return new CMapObject(5, this.tokeniser.getStringValue());
/*     */       case EndArray:
/* 183 */         return new CMapObject(8, "]");
/*     */       case EndDic:
/* 185 */         return new CMapObject(8, ">>");
/*     */     } 
/* 187 */     return new CMapObject(0, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextValidToken() throws IOException {
/* 197 */     while (this.tokeniser.nextToken()) {
/* 198 */       if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.Comment)
/*     */         continue; 
/* 200 */       return true;
/*     */     } 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String decodeName(byte[] content) {
/* 207 */     StringBuilder buf = new StringBuilder();
/*     */     try {
/* 209 */       for (int k = 0; k < content.length; k++) {
/* 210 */         char c = (char)content[k];
/* 211 */         if (c == '#') {
/* 212 */           byte c1 = content[k + 1];
/* 213 */           byte c2 = content[k + 2];
/* 214 */           c = (char)((ByteBuffer.getHex(c1) << 4) + ByteBuffer.getHex(c2));
/* 215 */           k += 2;
/*     */         } 
/* 217 */         buf.append(c);
/*     */       } 
/* 219 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*     */ 
/*     */     
/* 222 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private static String toHex4(int n) {
/* 226 */     String s = "0000" + Integer.toHexString(n);
/* 227 */     return s.substring(s.length() - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHex(int n) {
/* 237 */     if (n < 65536)
/* 238 */       return "<" + toHex4(n) + ">"; 
/* 239 */     n -= 65536;
/* 240 */     int high = n / 1024 + 55296;
/* 241 */     int low = n % 1024 + 56320;
/* 242 */     return "[<" + toHex4(high) + toHex4(low) + ">]";
/*     */   }
/*     */   
/*     */   public static String decodeCMapObject(CMapObject cMapObject) {
/* 246 */     if (cMapObject.isHexString()) {
/* 247 */       return PdfEncodings.convertToString(((String)cMapObject.getValue()).getBytes(), "UnicodeBigUnmarked");
/*     */     }
/* 249 */     return (String)cMapObject.getValue();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapContentParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */