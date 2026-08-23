/*     */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.LineSegment;
/*     */ import com.itextpdf.kernel.geom.Point;
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
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
/*     */ public class CharacterRenderInfo
/*     */   extends TextChunk
/*     */ {
/*     */   private Rectangle boundingBox;
/*     */   
/*     */   static StringConversionInfo mapString(List<CharacterRenderInfo> cris) {
/*  71 */     Map<Integer, Integer> indexMap = new HashMap<>();
/*  72 */     StringBuilder sb = new StringBuilder();
/*  73 */     CharacterRenderInfo lastChunk = null;
/*  74 */     for (int i = 0; i < cris.size(); i++) {
/*  75 */       CharacterRenderInfo chunk = cris.get(i);
/*  76 */       if (lastChunk == null) {
/*  77 */         putCharsWithIndex(chunk.getText(), i, indexMap, sb);
/*     */       }
/*  79 */       else if (chunk.sameLine(lastChunk)) {
/*     */         
/*  81 */         if (chunk.getLocation().isAtWordBoundary(lastChunk.getLocation()) && !chunk.getText().startsWith(" ") && !chunk.getText().endsWith(" ")) {
/*  82 */           sb.append(' ');
/*     */         }
/*  84 */         putCharsWithIndex(chunk.getText(), i, indexMap, sb);
/*     */       } else {
/*     */         
/*  87 */         sb.append('\n');
/*  88 */         putCharsWithIndex(chunk.getText(), i, indexMap, sb);
/*     */       } 
/*     */       
/*  91 */       lastChunk = chunk;
/*     */     } 
/*  93 */     StringConversionInfo ret = new StringConversionInfo();
/*  94 */     ret.indexMap = indexMap;
/*  95 */     ret.text = sb.toString();
/*  96 */     return ret;
/*     */   }
/*     */   
/*     */   private static void putCharsWithIndex(CharSequence seq, int index, Map<Integer, Integer> indexMap, StringBuilder sb) {
/* 100 */     int charCount = seq.length();
/* 101 */     for (int i = 0; i < charCount; i++) {
/* 102 */       indexMap.put(Integer.valueOf(sb.length()), Integer.valueOf(index));
/* 103 */       sb.append(seq.charAt(i));
/*     */     } 
/*     */   }
/*     */   
/*     */   public CharacterRenderInfo(TextRenderInfo tri) {
/* 108 */     super((tri == null) ? "" : tri.getText(), (tri == null) ? null : getLocation(tri));
/* 109 */     if (tri == null) {
/* 110 */       throw new IllegalArgumentException("TextRenderInfo argument is not nullable.");
/*     */     }
/*     */     
/* 113 */     List<Point> points = new ArrayList<>();
/* 114 */     points.add(new Point(tri.getDescentLine().getStartPoint().get(0), tri.getDescentLine().getStartPoint().get(1)));
/* 115 */     points.add(new Point(tri.getDescentLine().getEndPoint().get(0), tri.getDescentLine().getEndPoint().get(1)));
/* 116 */     points.add(new Point(tri.getAscentLine().getStartPoint().get(0), tri.getAscentLine().getStartPoint().get(1)));
/* 117 */     points.add(new Point(tri.getAscentLine().getEndPoint().get(0), tri.getAscentLine().getEndPoint().get(1)));
/*     */     
/* 119 */     this.boundingBox = Rectangle.calculateBBox(points);
/*     */   }
/*     */   
/*     */   public Rectangle getBoundingBox() {
/* 123 */     return this.boundingBox;
/*     */   }
/*     */   
/*     */   private static ITextChunkLocation getLocation(TextRenderInfo tri) {
/* 127 */     LineSegment baseline = tri.getBaseline();
/* 128 */     return new TextChunkLocationDefaultImp(baseline.getStartPoint(), baseline
/* 129 */         .getEndPoint(), tri
/* 130 */         .getSingleSpaceWidth());
/*     */   }
/*     */   
/*     */   static class StringConversionInfo {
/*     */     Map<Integer, Integer> indexMap;
/*     */     String text;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/CharacterRenderInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */