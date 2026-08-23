/*    */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextChunk
/*    */ {
/*    */   protected final String text;
/*    */   protected final ITextChunkLocation location;
/*    */   
/*    */   public TextChunk(String string, ITextChunkLocation loc) {
/* 56 */     this.text = string;
/* 57 */     this.location = loc;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 64 */     return this.text;
/*    */   }
/*    */   
/*    */   public ITextChunkLocation getLocation() {
/* 68 */     return this.location;
/*    */   }
/*    */   
/*    */   void printDiagnostics() {
/* 72 */     System.out.println("Text (@" + this.location.getStartLocation() + " -> " + this.location.getEndLocation() + "): " + this.text);
/* 73 */     System.out.println("orientationMagnitude: " + this.location.orientationMagnitude());
/* 74 */     System.out.println("distPerpendicular: " + this.location.distPerpendicular());
/* 75 */     System.out.println("distParallel: " + this.location.distParallelStart());
/*    */   }
/*    */   
/*    */   boolean sameLine(TextChunk lastChunk) {
/* 79 */     return getLocation().sameLine(lastChunk.getLocation());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/TextChunk.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */