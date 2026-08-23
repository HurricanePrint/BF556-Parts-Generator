/*    */ package com.itextpdf.layout.property;
/*    */ 
/*    */ import com.itextpdf.io.util.MessageFormatUtil;
/*    */ import com.itextpdf.layout.renderer.ParagraphRenderer;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ public class ParagraphOrphansControl
/*    */ {
/*    */   private int minOrphans;
/*    */   
/*    */   public ParagraphOrphansControl(int minOrphans) {
/* 45 */     this.minOrphans = minOrphans;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParagraphOrphansControl setMinAllowedOrphans(int minOrphans) {
/* 55 */     this.minOrphans = minOrphans;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinOrphans() {
/* 65 */     return this.minOrphans;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleViolatedOrphans(ParagraphRenderer renderer, String message) {
/* 77 */     Logger logger = LoggerFactory.getLogger(ParagraphOrphansControl.class);
/* 78 */     if (renderer.getOccupiedArea() != null && renderer.getLines() != null) {
/* 79 */       int pageNumber = renderer.getOccupiedArea().getPageNumber();
/* 80 */       String warnText = MessageFormatUtil.format("Orphans constraint violated for paragraph split at page {0}. Min number of orphans: {1}; actual: {2}. \nComment: {3}", new Object[] { Integer.valueOf(pageNumber), 
/* 81 */             Integer.valueOf(this.minOrphans), Integer.valueOf(renderer.getLines().size()), message });
/* 82 */       logger.warn(warnText);
/*    */     } else {
/* 84 */       logger.warn("Premature call of handleViolation method.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/layout/property/ParagraphOrphansControl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */