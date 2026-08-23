/*    */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*    */ 
/*    */ import com.itextpdf.kernel.geom.Rectangle;
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
/*    */ public class DefaultPdfTextLocation
/*    */   implements IPdfTextLocation
/*    */ {
/*    */   private int pageNr;
/*    */   private Rectangle rectangle;
/*    */   private String text;
/*    */   
/*    */   public DefaultPdfTextLocation(int pageNr, Rectangle rect, String text) {
/* 57 */     this.pageNr = pageNr;
/* 58 */     this.rectangle = rect;
/* 59 */     this.text = text;
/*    */   }
/*    */ 
/*    */   
/*    */   public Rectangle getRectangle() {
/* 64 */     return this.rectangle;
/*    */   }
/*    */   
/*    */   public DefaultPdfTextLocation setRectangle(Rectangle rectangle) {
/* 68 */     this.rectangle = rectangle;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 74 */     return this.text;
/*    */   }
/*    */   
/*    */   public DefaultPdfTextLocation setText(String text) {
/* 78 */     this.text = text;
/* 79 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPageNumber() {
/* 84 */     return this.pageNr;
/*    */   }
/*    */   
/*    */   public DefaultPdfTextLocation setPageNr(int pageNr) {
/* 88 */     this.pageNr = pageNr;
/* 89 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/DefaultPdfTextLocation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */