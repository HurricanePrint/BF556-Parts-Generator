/*    */ package com.itextpdf.io.font.otf;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public abstract class ContextualRule
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -9013175115747848532L;
/*    */   
/*    */   public abstract int getContextLength();
/*    */   
/*    */   public abstract boolean isGlyphMatchesInput(int paramInt1, int paramInt2);
/*    */   
/*    */   public int getLookaheadContextLength() {
/* 53 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBacktrackContextLength() {
/* 61 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/io/font/otf/ContextualRule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */