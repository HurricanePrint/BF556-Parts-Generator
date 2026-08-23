/*     */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.LineSegment;
/*     */ import com.itextpdf.kernel.geom.Matrix;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.canvas.CanvasTag;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocationTextExtractionStrategy
/*     */   implements ITextExtractionStrategy
/*     */ {
/*     */   private static boolean DUMP_STATE = false;
/*  71 */   private final List<TextChunk> locationalResult = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private final ITextChunkLocationStrategy tclStrat;
/*     */ 
/*     */   
/*     */   private boolean useActualText = false;
/*     */   
/*     */   private boolean rightToLeftRunDirection = false;
/*     */   
/*     */   private TextRenderInfo lastTextRenderInfo;
/*     */ 
/*     */   
/*     */   public LocationTextExtractionStrategy() {
/*  85 */     this(new ITextChunkLocationStrategy() {
/*     */           public ITextChunkLocation createLocation(TextRenderInfo renderInfo, LineSegment baseline) {
/*  87 */             return new TextChunkLocationDefaultImp(baseline.getStartPoint(), baseline.getEndPoint(), renderInfo.getSingleSpaceWidth());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocationTextExtractionStrategy(ITextChunkLocationStrategy strat) {
/* 100 */     this.tclStrat = strat;
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
/*     */   public LocationTextExtractionStrategy setUseActualText(boolean useActualText) {
/* 112 */     this.useActualText = useActualText;
/* 113 */     return this;
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
/*     */   public LocationTextExtractionStrategy setRightToLeftRunDirection(boolean rightToLeftRunDirection) {
/* 125 */     this.rightToLeftRunDirection = rightToLeftRunDirection;
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseActualText() {
/* 136 */     return this.useActualText;
/*     */   }
/*     */ 
/*     */   
/*     */   public void eventOccurred(IEventData data, EventType type) {
/* 141 */     if (type.equals(EventType.RENDER_TEXT)) {
/* 142 */       TextRenderInfo renderInfo = (TextRenderInfo)data;
/* 143 */       LineSegment segment = renderInfo.getBaseline();
/* 144 */       if (renderInfo.getRise() != 0.0F) {
/*     */         
/* 146 */         Matrix riseOffsetTransform = new Matrix(0.0F, -renderInfo.getRise());
/* 147 */         segment = segment.transformBy(riseOffsetTransform);
/*     */       } 
/*     */       
/* 150 */       if (this.useActualText) {
/*     */         
/* 152 */         CanvasTag lastTagWithActualText = (this.lastTextRenderInfo != null) ? findLastTagWithActualText(this.lastTextRenderInfo.getCanvasTagHierarchy()) : null;
/*     */         
/* 154 */         if (lastTagWithActualText != null && lastTagWithActualText == findLastTagWithActualText(renderInfo.getCanvasTagHierarchy())) {
/*     */           
/* 156 */           TextChunk lastTextChunk = this.locationalResult.get(this.locationalResult.size() - 1);
/*     */ 
/*     */           
/* 159 */           Vector mergedStart = new Vector(Math.min(lastTextChunk.getLocation().getStartLocation().get(0), segment.getStartPoint().get(0)), Math.min(lastTextChunk.getLocation().getStartLocation().get(1), segment.getStartPoint().get(1)), Math.min(lastTextChunk.getLocation().getStartLocation().get(2), segment.getStartPoint().get(2)));
/*     */ 
/*     */           
/* 162 */           Vector mergedEnd = new Vector(Math.max(lastTextChunk.getLocation().getEndLocation().get(0), segment.getEndPoint().get(0)), Math.max(lastTextChunk.getLocation().getEndLocation().get(1), segment.getEndPoint().get(1)), Math.max(lastTextChunk.getLocation().getEndLocation().get(2), segment.getEndPoint().get(2)));
/* 163 */           TextChunk merged = new TextChunk(lastTextChunk.getText(), this.tclStrat.createLocation(renderInfo, new LineSegment(mergedStart, mergedEnd)));
/*     */           
/* 165 */           this.locationalResult.set(this.locationalResult.size() - 1, merged);
/*     */         } else {
/* 167 */           String actualText = renderInfo.getActualText();
/*     */           
/* 169 */           TextChunk tc = new TextChunk((actualText != null) ? actualText : renderInfo.getText(), this.tclStrat.createLocation(renderInfo, segment));
/* 170 */           this.locationalResult.add(tc);
/*     */         } 
/*     */       } else {
/* 173 */         TextChunk tc = new TextChunk(renderInfo.getText(), this.tclStrat.createLocation(renderInfo, segment));
/* 174 */         this.locationalResult.add(tc);
/*     */       } 
/*     */       
/* 177 */       this.lastTextRenderInfo = renderInfo;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EventType> getSupportedEvents() {
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResultantText() {
/* 188 */     if (DUMP_STATE) dumpState();
/*     */     
/* 190 */     List<TextChunk> textChunks = new ArrayList<>(this.locationalResult);
/* 191 */     sortWithMarks(textChunks);
/*     */     
/* 193 */     StringBuilder sb = new StringBuilder();
/* 194 */     TextChunk lastChunk = null;
/* 195 */     for (TextChunk chunk : textChunks) {
/* 196 */       if (lastChunk == null) {
/* 197 */         sb.append(chunk.text);
/*     */       }
/* 199 */       else if (chunk.sameLine(lastChunk)) {
/*     */         
/* 201 */         if (isChunkAtWordBoundary(chunk, lastChunk) && !startsWithSpace(chunk.text) && !endsWithSpace(lastChunk.text)) {
/* 202 */           sb.append(' ');
/*     */         }
/*     */         
/* 205 */         sb.append(chunk.text);
/*     */       } else {
/* 207 */         sb.append('\n');
/* 208 */         sb.append(chunk.text);
/*     */       } 
/*     */       
/* 211 */       lastChunk = chunk;
/*     */     } 
/*     */     
/* 214 */     return sb.toString();
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
/*     */   protected boolean isChunkAtWordBoundary(TextChunk chunk, TextChunk previousChunk) {
/* 229 */     return chunk.getLocation().isAtWordBoundary(previousChunk.getLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean startsWithSpace(String str) {
/* 239 */     return (str.length() != 0 && str.charAt(0) == ' ');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean endsWithSpace(String str) {
/* 249 */     return (str.length() != 0 && str.charAt(str.length() - 1) == ' ');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dumpState() {
/* 256 */     for (TextChunk location : this.locationalResult) {
/* 257 */       location.printDiagnostics();
/* 258 */       System.out.println();
/*     */     } 
/*     */   }
/*     */   
/*     */   private CanvasTag findLastTagWithActualText(List<CanvasTag> canvasTagHierarchy) {
/* 263 */     CanvasTag lastActualText = null;
/* 264 */     for (CanvasTag tag : canvasTagHierarchy) {
/* 265 */       if (tag.getActualText() != null) {
/* 266 */         lastActualText = tag;
/*     */         break;
/*     */       } 
/*     */     } 
/* 270 */     return lastActualText;
/*     */   }
/*     */   
/*     */   private void sortWithMarks(List<TextChunk> textChunks) {
/* 274 */     Map<TextChunk, TextChunkMarks> marks = new HashMap<>();
/* 275 */     List<TextChunk> toSort = new ArrayList<>();
/*     */     
/* 277 */     for (int markInd = 0; markInd < textChunks.size(); markInd++) {
/* 278 */       ITextChunkLocation location = ((TextChunk)textChunks.get(markInd)).getLocation();
/* 279 */       if (location.getStartLocation().equals(location.getEndLocation())) {
/* 280 */         boolean foundBaseToAttachTo = false;
/* 281 */         for (int baseInd = 0; baseInd < textChunks.size(); baseInd++) {
/* 282 */           if (markInd != baseInd) {
/* 283 */             ITextChunkLocation baseLocation = ((TextChunk)textChunks.get(baseInd)).getLocation();
/* 284 */             if (!baseLocation.getStartLocation().equals(baseLocation.getEndLocation()) && TextChunkLocationDefaultImp.containsMark(baseLocation, location)) {
/* 285 */               TextChunkMarks currentMarks = marks.get(textChunks.get(baseInd));
/* 286 */               if (currentMarks == null) {
/* 287 */                 currentMarks = new TextChunkMarks();
/* 288 */                 marks.put(textChunks.get(baseInd), currentMarks);
/*     */               } 
/*     */               
/* 291 */               if (markInd < baseInd) {
/* 292 */                 currentMarks.preceding.add(textChunks.get(markInd));
/*     */               } else {
/* 294 */                 currentMarks.succeeding.add(textChunks.get(markInd));
/*     */               } 
/*     */               
/* 297 */               foundBaseToAttachTo = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 303 */         if (!foundBaseToAttachTo) {
/* 304 */           toSort.add(textChunks.get(markInd));
/*     */         }
/*     */       } else {
/* 307 */         toSort.add(textChunks.get(markInd));
/*     */       } 
/*     */     } 
/*     */     
/* 311 */     Collections.sort(toSort, new TextChunkLocationBasedComparator(new DefaultTextChunkLocationComparator(!this.rightToLeftRunDirection)));
/*     */     
/* 313 */     textChunks.clear();
/*     */     
/* 315 */     for (TextChunk current : toSort) {
/* 316 */       TextChunkMarks currentMarks = marks.get(current);
/* 317 */       if (currentMarks != null) {
/* 318 */         if (!this.rightToLeftRunDirection) {
/* 319 */           for (int j = 0; j < currentMarks.preceding.size(); j++) {
/* 320 */             textChunks.add(currentMarks.preceding.get(j));
/*     */           }
/*     */         } else {
/* 323 */           for (int j = currentMarks.succeeding.size() - 1; j >= 0; j--) {
/* 324 */             textChunks.add(currentMarks.succeeding.get(j));
/*     */           }
/*     */         } 
/*     */       }
/* 328 */       textChunks.add(current);
/* 329 */       if (currentMarks != null) {
/* 330 */         if (!this.rightToLeftRunDirection) {
/* 331 */           for (int i = 0; i < currentMarks.succeeding.size(); i++)
/* 332 */             textChunks.add(currentMarks.succeeding.get(i)); 
/*     */           continue;
/*     */         } 
/* 335 */         for (int j = currentMarks.preceding.size() - 1; j >= 0; j--)
/* 336 */           textChunks.add(currentMarks.preceding.get(j)); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface ITextChunkLocationStrategy {
/*     */     ITextChunkLocation createLocation(TextRenderInfo param1TextRenderInfo, LineSegment param1LineSegment);
/*     */   }
/*     */   
/*     */   private static class TextChunkMarks {
/*     */     private TextChunkMarks() {}
/*     */     
/* 348 */     List<TextChunk> preceding = new ArrayList<>();
/* 349 */     List<TextChunk> succeeding = new ArrayList<>();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/LocationTextExtractionStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */