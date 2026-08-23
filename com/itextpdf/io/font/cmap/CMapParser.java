/*     */ package com.itextpdf.io.font.cmap;
/*     */ 
/*     */ import com.itextpdf.io.source.PdfTokenizer;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class CMapParser
/*     */ {
/*     */   private static final String def = "def";
/*     */   private static final String endcidrange = "endcidrange";
/*     */   private static final String endcidchar = "endcidchar";
/*     */   private static final String endbfrange = "endbfrange";
/*     */   private static final String endbfchar = "endbfchar";
/*     */   private static final String endcodespacerange = "endcodespacerange";
/*     */   private static final String usecmap = "usecmap";
/*     */   private static final String Registry = "Registry";
/*     */   private static final String Ordering = "Ordering";
/*     */   private static final String Supplement = "Supplement";
/*     */   private static final String CMapName = "CMapName";
/*     */   private static final int MAX_LEVEL = 10;
/*     */   
/*     */   public static void parseCid(String cmapName, AbstractCMap cmap, ICMapLocation location) throws IOException {
/*  72 */     parseCid(cmapName, cmap, location, 0);
/*     */   }
/*     */   
/*     */   private static void parseCid(String cmapName, AbstractCMap cmap, ICMapLocation location, int level) throws IOException {
/*  76 */     if (level >= 10)
/*     */       return; 
/*  78 */     PdfTokenizer inp = location.getLocation(cmapName);
/*     */     try {
/*  80 */       List<CMapObject> list = new ArrayList<>();
/*  81 */       CMapContentParser cp = new CMapContentParser(inp);
/*  82 */       int maxExc = 50;
/*     */       while (true) {
/*     */         try {
/*  85 */           cp.parse(list);
/*  86 */         } catch (Exception ex) {
/*  87 */           if (--maxExc < 0)
/*     */             break; 
/*     */           continue;
/*     */         } 
/*  91 */         if (list.size() == 0)
/*     */           break; 
/*  93 */         String last = ((CMapObject)list.get(list.size() - 1)).toString();
/*  94 */         if (level == 0 && list.size() == 3 && last.equals("def")) {
/*  95 */           CMapObject cmapObject = list.get(0);
/*  96 */           if ("Registry".equals(cmapObject.toString())) {
/*  97 */             cmap.setRegistry(((CMapObject)list.get(1)).toString()); continue;
/*  98 */           }  if ("Ordering".equals(cmapObject.toString())) {
/*  99 */             cmap.setOrdering(((CMapObject)list.get(1)).toString()); continue;
/* 100 */           }  if ("CMapName".equals(cmapObject.toString())) {
/* 101 */             cmap.setName(((CMapObject)list.get(1)).toString()); continue;
/* 102 */           }  if ("Supplement".equals(cmapObject.toString()))
/*     */             try {
/* 104 */               cmap.setSupplement(((Integer)((CMapObject)list.get(1)).getValue()).intValue());
/* 105 */             } catch (Exception exception) {} 
/*     */           continue;
/*     */         } 
/* 108 */         if ((last.equals("endcidchar") || last.equals("endbfchar")) && list.size() >= 3) {
/* 109 */           int lMax = list.size() - 2;
/* 110 */           for (int k = 0; k < lMax; k += 2) {
/* 111 */             if (((CMapObject)list.get(k)).isString())
/* 112 */               cmap.addChar(((CMapObject)list.get(k)).toString(), list.get(k + 1)); 
/*     */           }  continue;
/*     */         } 
/* 115 */         if ((last.equals("endcidrange") || last.equals("endbfrange")) && list.size() >= 4) {
/* 116 */           int lMax = list.size() - 3;
/* 117 */           for (int k = 0; k < lMax; k += 3) {
/* 118 */             if (((CMapObject)list.get(k)).isString() && ((CMapObject)list.get(k + 1)).isString())
/* 119 */               cmap.addRange(((CMapObject)list.get(k)).toString(), ((CMapObject)list.get(k + 1)).toString(), list.get(k + 2)); 
/*     */           }  continue;
/*     */         } 
/* 122 */         if (last.equals("usecmap") && list.size() == 2 && ((CMapObject)list.get(0)).isName()) {
/* 123 */           parseCid(((CMapObject)list.get(0)).toString(), cmap, location, level + 1); continue;
/* 124 */         }  if (last.equals("endcodespacerange")) {
/* 125 */           for (int i = 0; i < list.size() + 1; i += 2) {
/* 126 */             if (((CMapObject)list.get(i)).isHexString() && ((CMapObject)list.get(i + 1)).isHexString()) {
/* 127 */               byte[] low = ((CMapObject)list.get(i)).toHexByteArray();
/* 128 */               byte[] high = ((CMapObject)list.get(i + 1)).toHexByteArray();
/* 129 */               cmap.addCodeSpaceRange(low, high);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 134 */     } catch (Exception ex) {
/* 135 */       Logger logger = LoggerFactory.getLogger(CMapParser.class);
/* 136 */       logger.error("Unknown error while processing CMap.");
/*     */     } finally {
/* 138 */       inp.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/cmap/CMapParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */