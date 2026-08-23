/*     */ package com.itextpdf.kernel.font;
/*     */ 
/*     */ import com.itextpdf.io.font.FontCache;
/*     */ import com.itextpdf.io.font.cmap.AbstractCMap;
/*     */ import com.itextpdf.io.font.cmap.CMapLocationFromBytes;
/*     */ import com.itextpdf.io.font.cmap.CMapParser;
/*     */ import com.itextpdf.io.font.cmap.CMapToUnicode;
/*     */ import com.itextpdf.io.font.cmap.CMapUniCid;
/*     */ import com.itextpdf.io.font.cmap.ICMapLocation;
/*     */ import com.itextpdf.io.util.IntHashtable;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfStream;
/*     */ import java.util.HashMap;
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
/*     */ class FontUtil
/*     */ {
/*  68 */   private static final HashMap<String, CMapToUnicode> uniMaps = new HashMap<>();
/*     */   
/*     */   static CMapToUnicode processToUnicode(PdfObject toUnicode) {
/*  71 */     CMapToUnicode cMapToUnicode = null;
/*  72 */     if (toUnicode instanceof PdfStream) {
/*     */       try {
/*  74 */         byte[] uniBytes = ((PdfStream)toUnicode).getBytes();
/*  75 */         CMapLocationFromBytes cMapLocationFromBytes = new CMapLocationFromBytes(uniBytes);
/*  76 */         cMapToUnicode = new CMapToUnicode();
/*  77 */         CMapParser.parseCid("", (AbstractCMap)cMapToUnicode, (ICMapLocation)cMapLocationFromBytes);
/*  78 */       } catch (Exception e) {
/*  79 */         Logger logger = LoggerFactory.getLogger(CMapToUnicode.class);
/*  80 */         logger.error("Unknown error while processing CMap.");
/*  81 */         cMapToUnicode = CMapToUnicode.EmptyCMapToUnicodeMap;
/*     */       } 
/*  83 */     } else if (PdfName.IdentityH.equals(toUnicode)) {
/*  84 */       cMapToUnicode = CMapToUnicode.getIdentity();
/*     */     } 
/*  86 */     return cMapToUnicode;
/*     */   }
/*     */   
/*     */   static CMapToUnicode getToUnicodeFromUniMap(String uniMap) {
/*  90 */     if (uniMap == null)
/*  91 */       return null; 
/*  92 */     synchronized (uniMaps) {
/*  93 */       CMapToUnicode toUnicode; if (uniMaps.containsKey(uniMap)) {
/*  94 */         return uniMaps.get(uniMap);
/*     */       }
/*     */       
/*  97 */       if ("Identity-H".equals(uniMap)) {
/*  98 */         toUnicode = CMapToUnicode.getIdentity();
/*     */       } else {
/* 100 */         CMapUniCid uni = FontCache.getUni2CidCmap(uniMap);
/* 101 */         if (uni == null) {
/* 102 */           return null;
/*     */         }
/* 104 */         toUnicode = uni.exportToUnicode();
/*     */       } 
/* 106 */       uniMaps.put(uniMap, toUnicode);
/* 107 */       return toUnicode;
/*     */     } 
/*     */   }
/*     */   
/*     */   static String createRandomFontName() {
/* 112 */     StringBuilder s = new StringBuilder("");
/* 113 */     for (int k = 0; k < 7; k++) {
/* 114 */       s.append((char)(int)(Math.random() * 26.0D + 65.0D));
/*     */     }
/* 116 */     return s.toString();
/*     */   }
/*     */   
/*     */   static int[] convertSimpleWidthsArray(PdfArray widthsArray, int first, int missingWidth) {
/* 120 */     int[] res = new int[256]; int i;
/* 121 */     for (i = 0; i < res.length; i++) {
/* 122 */       res[i] = missingWidth;
/*     */     }
/* 124 */     if (widthsArray == null) {
/* 125 */       Logger logger = LoggerFactory.getLogger(FontUtil.class);
/* 126 */       logger.warn("Font dictionary does not contain required /Widths entry.");
/* 127 */       return res;
/*     */     } 
/*     */     
/* 130 */     for (i = 0; i < widthsArray.size() && first + i < 256; i++) {
/* 131 */       PdfNumber number = widthsArray.getAsNumber(i);
/* 132 */       res[first + i] = (number != null) ? number.intValue() : missingWidth;
/*     */     } 
/* 134 */     return res;
/*     */   }
/*     */   
/*     */   static IntHashtable convertCompositeWidthsArray(PdfArray widthsArray) {
/* 138 */     IntHashtable res = new IntHashtable();
/* 139 */     if (widthsArray == null) {
/* 140 */       return res;
/*     */     }
/*     */     
/* 143 */     for (int k = 0; k < widthsArray.size(); k++) {
/* 144 */       int c1 = widthsArray.getAsNumber(k).intValue();
/* 145 */       PdfObject obj = widthsArray.get(++k);
/* 146 */       if (obj.isArray()) {
/* 147 */         PdfArray subWidths = (PdfArray)obj;
/* 148 */         for (int j = 0; j < subWidths.size(); j++) {
/* 149 */           int c2 = subWidths.getAsNumber(j).intValue();
/* 150 */           res.put(c1++, c2);
/*     */         } 
/*     */       } else {
/* 153 */         int c2 = ((PdfNumber)obj).intValue();
/* 154 */         int w = widthsArray.getAsNumber(++k).intValue();
/* 155 */         for (; c1 <= c2; c1++) {
/* 156 */           res.put(c1, w);
/*     */         }
/*     */       } 
/*     */     } 
/* 160 */     return res;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/font/FontUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */