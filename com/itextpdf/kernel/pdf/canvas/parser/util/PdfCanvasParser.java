/*     */ package com.itextpdf.kernel.pdf.canvas.parser.util;
/*     */ 
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfLiteral;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfResources;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfCanvasParser
/*     */ {
/*     */   private PdfTokenizer tokeniser;
/*     */   private PdfResources currentResources;
/*     */   
/*     */   public PdfCanvasParser(PdfTokenizer tokeniser) {
/*  80 */     this.tokeniser = tokeniser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfCanvasParser(PdfTokenizer tokeniser, PdfResources currentResources) {
/*  91 */     this.tokeniser = tokeniser;
/*  92 */     this.currentResources = currentResources;
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
/*     */   public List<PdfObject> parse(List<PdfObject> ls) throws IOException {
/* 110 */     if (ls == null) {
/* 111 */       ls = new ArrayList<>();
/*     */     } else {
/* 113 */       ls.clear();
/* 114 */     }  PdfObject ob = null;
/* 115 */     while ((ob = readObject()) != null) {
/* 116 */       ls.add(ob);
/* 117 */       if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.Other) {
/* 118 */         if ("BI".equals(ob.toString())) {
/* 119 */           PdfStream inlineImageAsStream = InlineImageParsingUtils.parse(this, this.currentResources.getResource(PdfName.ColorSpace));
/* 120 */           ls.clear();
/* 121 */           ls.add(inlineImageAsStream);
/* 122 */           ls.add(new PdfLiteral("EI"));
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/* 127 */     return ls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTokenizer getTokeniser() {
/* 135 */     return this.tokeniser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokeniser(PdfTokenizer tokeniser) {
/* 143 */     this.tokeniser = tokeniser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfDictionary readDictionary() throws IOException {
/* 152 */     PdfDictionary dic = new PdfDictionary();
/*     */     while (true) {
/* 154 */       if (!nextValidToken())
/* 155 */         throw new PdfException("Unexpected end of file."); 
/* 156 */       if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndDic)
/*     */         break; 
/* 158 */       if (this.tokeniser.getTokenType() != PdfTokenizer.TokenType.Name)
/* 159 */         this.tokeniser.throwError("Dictionary key {0} is not a name.", new Object[] { this.tokeniser.getStringValue() }); 
/* 160 */       PdfName name = new PdfName(this.tokeniser.getStringValue());
/* 161 */       PdfObject obj = readObject();
/* 162 */       dic.put(name, obj);
/*     */     } 
/* 164 */     return dic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfArray readArray() throws IOException {
/* 173 */     PdfArray array = new PdfArray();
/*     */     while (true) {
/* 175 */       PdfObject obj = readObject();
/* 176 */       if (!obj.isArray() && this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndArray)
/*     */         break; 
/* 178 */       if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.EndDic && obj.getType() != 3)
/* 179 */         this.tokeniser.throwError("unexpected >>.", new Object[0]); 
/* 180 */       array.add(obj);
/*     */     } 
/* 182 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfObject readObject() throws IOException {
/*     */     PdfDictionary dic;
/*     */     PdfString str;
/* 191 */     if (!nextValidToken())
/* 192 */       return null; 
/* 193 */     PdfTokenizer.TokenType type = this.tokeniser.getTokenType();
/* 194 */     switch (type) {
/*     */       case StartDic:
/* 196 */         dic = readDictionary();
/* 197 */         return (PdfObject)dic;
/*     */       
/*     */       case StartArray:
/* 200 */         return (PdfObject)readArray();
/*     */       case String:
/* 202 */         str = (new PdfString(this.tokeniser.getDecodedStringContent())).setHexWriting(this.tokeniser.isHexString());
/* 203 */         return (PdfObject)str;
/*     */       case Name:
/* 205 */         return (PdfObject)new PdfName(this.tokeniser.getByteContent());
/*     */       
/*     */       case Number:
/* 208 */         return (PdfObject)new PdfNumber(this.tokeniser.getByteContent());
/*     */     } 
/* 210 */     return (PdfObject)new PdfLiteral(this.tokeniser.getByteContent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextValidToken() throws IOException {
/* 220 */     while (this.tokeniser.nextToken()) {
/* 221 */       if (this.tokeniser.getTokenType() == PdfTokenizer.TokenType.Comment)
/*     */         continue; 
/* 223 */       return true;
/*     */     } 
/* 225 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/util/PdfCanvasParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */