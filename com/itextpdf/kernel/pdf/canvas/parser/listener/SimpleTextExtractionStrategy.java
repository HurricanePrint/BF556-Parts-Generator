/*     */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.LineSegment;
/*     */ import com.itextpdf.kernel.geom.Vector;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.EventType;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
/*     */ import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
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
/*     */ public class SimpleTextExtractionStrategy
/*     */   implements ITextExtractionStrategy
/*     */ {
/*     */   private Vector lastStart;
/*     */   private Vector lastEnd;
/*  61 */   private final StringBuilder result = new StringBuilder();
/*     */ 
/*     */   
/*     */   public void eventOccurred(IEventData data, EventType type) {
/*  65 */     if (type.equals(EventType.RENDER_TEXT)) {
/*  66 */       TextRenderInfo renderInfo = (TextRenderInfo)data;
/*  67 */       boolean firstRender = (this.result.length() == 0);
/*  68 */       boolean hardReturn = false;
/*     */       
/*  70 */       LineSegment segment = renderInfo.getBaseline();
/*  71 */       Vector start = segment.getStartPoint();
/*  72 */       Vector end = segment.getEndPoint();
/*     */       
/*  74 */       if (!firstRender) {
/*  75 */         Vector x1 = this.lastStart;
/*  76 */         Vector x2 = this.lastEnd;
/*     */ 
/*     */         
/*  79 */         float dist = x2.subtract(x1).cross(x1.subtract(start)).lengthSquared() / x2.subtract(x1).lengthSquared();
/*     */ 
/*     */         
/*  82 */         float sameLineThreshold = 1.0F;
/*  83 */         if (dist > sameLineThreshold) {
/*  84 */           hardReturn = true;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  90 */       if (hardReturn) {
/*     */         
/*  92 */         appendTextChunk("\n");
/*  93 */       } else if (!firstRender) {
/*     */         
/*  95 */         if (this.result.charAt(this.result.length() - 1) != ' ' && renderInfo.getText().length() > 0 && renderInfo.getText().charAt(0) != ' ') {
/*  96 */           float spacing = this.lastEnd.subtract(start).length();
/*  97 */           if (spacing > renderInfo.getSingleSpaceWidth() / 2.0F) {
/*  98 */             appendTextChunk(" ");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       appendTextChunk(renderInfo.getText());
/*     */       
/* 109 */       this.lastStart = start;
/* 110 */       this.lastEnd = end;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EventType> getSupportedEvents() {
/* 116 */     return Collections.unmodifiableSet(new LinkedHashSet<>(Collections.singletonList(EventType.RENDER_TEXT)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResultantText() {
/* 125 */     return this.result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void appendTextChunk(CharSequence text) {
/* 135 */     this.result.append(text);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/SimpleTextExtractionStrategy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */