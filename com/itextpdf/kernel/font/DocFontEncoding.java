/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.AdobeGlyphList;
/*     */ import com.itextpdf.io.font.FontEncoding;
/*     */ import com.itextpdf.io.font.cmap.CMapToUnicode;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.io.util.MessageFormatUtil;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DocFontEncoding
/*     */   extends FontEncoding
/*     */ {
/*     */   private static final long serialVersionUID = -4248206280861742148L;
/*     */   
/*     */   public static FontEncoding createDocFontEncoding(PdfObject encoding, CMapToUnicode toUnicode) {
/*  74 */     if (encoding != null) {
/*  75 */       if (encoding.isName())
/*  76 */         return FontEncoding.createFontEncoding(((PdfName)encoding).getValue()); 
/*  77 */       if (encoding.isDictionary()) {
/*  78 */         DocFontEncoding fontEncoding = new DocFontEncoding();
/*  79 */         fontEncoding.differences = new String[256];
/*  80 */         fillBaseEncoding(fontEncoding, ((PdfDictionary)encoding).getAsName(PdfName.BaseEncoding));
/*  81 */         fillDifferences(fontEncoding, ((PdfDictionary)encoding).getAsArray(PdfName.Differences), toUnicode);
/*  82 */         return fontEncoding;
/*     */       } 
/*     */     } 
/*  85 */     if (toUnicode != null) {
/*  86 */       DocFontEncoding fontEncoding = new DocFontEncoding();
/*  87 */       fontEncoding.differences = new String[256];
/*  88 */       fillDifferences(fontEncoding, toUnicode);
/*  89 */       return fontEncoding;
/*     */     } 
/*  91 */     return FontEncoding.createFontSpecificEncoding();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fillBaseEncoding(DocFontEncoding fontEncoding, PdfName baseEncodingName) {
/*  96 */     if (baseEncodingName != null) {
/*  97 */       fontEncoding.baseEncoding = baseEncodingName.getValue();
/*     */     }
/*  99 */     if (PdfName.MacRomanEncoding.equals(baseEncodingName) || PdfName.WinAnsiEncoding.equals(baseEncodingName) || PdfName.Symbol
/* 100 */       .equals(baseEncodingName) || PdfName.ZapfDingbats.equals(baseEncodingName)) {
/* 101 */       String enc = "Cp1252";
/* 102 */       if (PdfName.MacRomanEncoding.equals(baseEncodingName)) {
/* 103 */         enc = "MacRoman";
/* 104 */       } else if (PdfName.Symbol.equals(baseEncodingName)) {
/* 105 */         enc = "Symbol";
/* 106 */       } else if (PdfName.ZapfDingbats.equals(baseEncodingName)) {
/* 107 */         enc = "ZapfDingbats";
/*     */       } 
/* 109 */       fontEncoding.baseEncoding = enc;
/* 110 */       fontEncoding.fillNamedEncoding();
/*     */     }
/*     */     else {
/*     */       
/* 114 */       fontEncoding.fillStandardEncoding();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void fillDifferences(DocFontEncoding fontEncoding, PdfArray diffs, CMapToUnicode toUnicode) {
/* 119 */     IntHashtable byte2uni = (toUnicode != null) ? toUnicode.createDirectMapping() : new IntHashtable();
/* 120 */     if (diffs != null) {
/* 121 */       int currentNumber = 0;
/* 122 */       for (int k = 0; k < diffs.size(); k++) {
/* 123 */         PdfObject obj = diffs.get(k);
/* 124 */         if (obj.isNumber()) {
/* 125 */           currentNumber = ((PdfNumber)obj).intValue();
/* 126 */         } else if (currentNumber > 255) {
/* 127 */           Logger LOGGER = LoggerFactory.getLogger(DocFontEncoding.class);
/* 128 */           LOGGER.warn(MessageFormatUtil.format("Document Font has illegal differences array. Entry {0} references a glyph ID over 255 and will be ignored.", new Object[] { ((PdfName)obj).getValue() }));
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 135 */           String glyphName = ((PdfName)obj).getValue();
/* 136 */           int unicode = AdobeGlyphList.nameToUnicode(glyphName);
/* 137 */           if (unicode != -1) {
/* 138 */             fontEncoding.codeToUnicode[currentNumber] = unicode;
/* 139 */             fontEncoding.unicodeToCode.put(unicode, currentNumber);
/* 140 */             fontEncoding.differences[currentNumber] = glyphName;
/* 141 */             fontEncoding.unicodeDifferences.put(unicode, unicode);
/*     */           }
/* 143 */           else if (byte2uni.containsKey(currentNumber)) {
/* 144 */             unicode = byte2uni.get(currentNumber);
/* 145 */             fontEncoding.codeToUnicode[currentNumber] = unicode;
/* 146 */             fontEncoding.unicodeToCode.put(unicode, currentNumber);
/* 147 */             fontEncoding.differences[currentNumber] = glyphName;
/* 148 */             fontEncoding.unicodeDifferences.put(unicode, unicode);
/*     */           } 
/*     */           
/* 151 */           currentNumber++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void fillDifferences(DocFontEncoding fontEncoding, CMapToUnicode toUnicode) {
/* 158 */     IntHashtable byte2uni = toUnicode.createDirectMapping(); int arrayOfInt[], i; byte b;
/* 159 */     for (arrayOfInt = byte2uni.getKeys(), i = arrayOfInt.length, b = 0; b < i; ) { Integer code = Integer.valueOf(arrayOfInt[b]);
/* 160 */       int unicode = byte2uni.get(code.intValue());
/* 161 */       String glyphName = AdobeGlyphList.unicodeToName(unicode);
/* 162 */       fontEncoding.codeToUnicode[code.intValue()] = unicode;
/* 163 */       fontEncoding.unicodeToCode.put(unicode, code.intValue());
/* 164 */       fontEncoding.differences[code.intValue()] = glyphName;
/* 165 */       fontEncoding.unicodeDifferences.put(unicode, unicode);
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/DocFontEncoding.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */