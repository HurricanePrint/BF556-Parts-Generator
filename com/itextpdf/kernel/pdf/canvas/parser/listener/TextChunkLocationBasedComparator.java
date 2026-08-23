/*    */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TextChunkLocationBasedComparator
/*    */   implements Comparator<TextChunk>
/*    */ {
/*    */   private Comparator<ITextChunkLocation> locationComparator;
/*    */   
/*    */   public TextChunkLocationBasedComparator(Comparator<ITextChunkLocation> locationComparator) {
/* 51 */     this.locationComparator = locationComparator;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(TextChunk o1, TextChunk o2) {
/* 56 */     return this.locationComparator.compare(o1.location, o2.location);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/TextChunkLocationBasedComparator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */