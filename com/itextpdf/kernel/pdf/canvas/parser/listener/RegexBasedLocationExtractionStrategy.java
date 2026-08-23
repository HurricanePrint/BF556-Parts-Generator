/*     */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegexBasedLocationExtractionStrategy
/*     */   implements ILocationExtractionStrategy
/*     */ {
/*     */   private Pattern pattern;
/*  67 */   private List<CharacterRenderInfo> parseResult = new ArrayList<>();
/*     */   
/*     */   public RegexBasedLocationExtractionStrategy(String regex) {
/*  70 */     this.pattern = Pattern.compile(regex);
/*     */   }
/*     */   
/*     */   public RegexBasedLocationExtractionStrategy(Pattern pattern) {
/*  74 */     this.pattern = pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<IPdfTextLocation> getResultantLocations() {
/*  80 */     Collections.sort(this.parseResult, new TextChunkLocationBasedComparator(new DefaultTextChunkLocationComparator()));
/*     */ 
/*     */     
/*  83 */     List<IPdfTextLocation> retval = new ArrayList<>();
/*     */     
/*  85 */     CharacterRenderInfo.StringConversionInfo txt = CharacterRenderInfo.mapString(this.parseResult);
/*     */     
/*  87 */     Matcher mat = this.pattern.matcher(txt.text);
/*  88 */     while (mat.find()) {
/*  89 */       Integer startIndex = getStartIndex(txt.indexMap, mat.start(), txt.text);
/*  90 */       Integer endIndex = getEndIndex(txt.indexMap, mat.end() - 1);
/*  91 */       if (startIndex != null && endIndex != null && startIndex.intValue() <= endIndex.intValue()) {
/*  92 */         for (Rectangle r : toRectangles(this.parseResult.subList(startIndex.intValue(), endIndex.intValue() + 1))) {
/*  93 */           retval.add(new DefaultPdfTextLocation(0, r, mat.group(0)));
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     Collections.sort(retval, new Comparator<IPdfTextLocation>()
/*     */         {
/*     */           public int compare(IPdfTextLocation l1, IPdfTextLocation l2) {
/* 106 */             Rectangle o1 = l1.getRectangle();
/* 107 */             Rectangle o2 = l2.getRectangle();
/* 108 */             if (o1.getY() == o2.getY()) {
/* 109 */               return (o1.getX() == o2.getX()) ? 0 : ((o1.getX() < o2.getX()) ? -1 : 1);
/*     */             }
/* 111 */             return (o1.getY() < o2.getY()) ? -1 : 1;
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 117 */     removeDuplicates(retval);
/*     */     
/* 119 */     return retval;
/*     */   }
/*     */   
/*     */   private void removeDuplicates(List<IPdfTextLocation> sortedList) {
/* 123 */     IPdfTextLocation lastItem = null;
/* 124 */     int orgSize = sortedList.size();
/* 125 */     for (int i = orgSize - 1; i >= 0; i--) {
/* 126 */       IPdfTextLocation currItem = sortedList.get(i);
/* 127 */       Rectangle currRect = currItem.getRectangle();
/* 128 */       if (lastItem != null && currRect.equalsWithEpsilon(lastItem.getRectangle())) {
/* 129 */         sortedList.remove(currItem);
/*     */       }
/* 131 */       lastItem = currItem;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void eventOccurred(IEventData data, EventType type) {
/* 137 */     if (data instanceof TextRenderInfo) {
/* 138 */       this.parseResult.addAll(toCRI((TextRenderInfo)data));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EventType> getSupportedEvents() {
/* 144 */     return null;
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
/*     */   protected List<CharacterRenderInfo> toCRI(TextRenderInfo tri) {
/* 159 */     List<CharacterRenderInfo> cris = new ArrayList<>();
/* 160 */     for (TextRenderInfo subTri : tri.getCharacterRenderInfos()) {
/* 161 */       cris.add(new CharacterRenderInfo(subTri));
/*     */     }
/* 163 */     return cris;
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
/*     */   protected List<Rectangle> toRectangles(List<CharacterRenderInfo> cris) {
/* 180 */     List<Rectangle> retval = new ArrayList<>();
/* 181 */     if (cris.isEmpty()) {
/* 182 */       return retval;
/*     */     }
/* 184 */     int prev = 0;
/* 185 */     int curr = 0;
/* 186 */     while (curr < cris.size()) {
/* 187 */       while (curr < cris.size() && ((CharacterRenderInfo)cris.get(curr)).sameLine(cris.get(prev))) {
/* 188 */         curr++;
/*     */       }
/* 190 */       Rectangle resultRectangle = null;
/* 191 */       for (CharacterRenderInfo cri : cris.subList(prev, curr)) {
/*     */         
/* 193 */         resultRectangle = Rectangle.getCommonRectangle(new Rectangle[] { resultRectangle, cri.getBoundingBox() });
/*     */       } 
/* 195 */       retval.add(resultRectangle);
/* 196 */       prev = curr;
/*     */     } 
/*     */ 
/*     */     
/* 200 */     return retval;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Integer getStartIndex(Map<Integer, Integer> indexMap, int index, String txt) {
/* 205 */     while (!indexMap.containsKey(Integer.valueOf(index)) && index < txt.length()) {
/* 206 */       index++;
/*     */     }
/* 208 */     return indexMap.get(Integer.valueOf(index));
/*     */   }
/*     */   
/*     */   private static Integer getEndIndex(Map<Integer, Integer> indexMap, int index) {
/* 212 */     while (!indexMap.containsKey(Integer.valueOf(index)) && index >= 0) {
/* 213 */       index--;
/*     */     }
/* 215 */     return indexMap.get(Integer.valueOf(index));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/RegexBasedLocationExtractionStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */