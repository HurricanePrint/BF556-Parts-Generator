/*    */ package com.itextpdf.kernel.pdf.canvas.parser;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfPage;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public final class PdfTextExtractor
/*    */ {
/*    */   public static String getTextFromPage(PdfPage page, ITextExtractionStrategy strategy, Map<String, IContentOperator> additionalContentOperators) {
/* 70 */     PdfCanvasProcessor parser = new PdfCanvasProcessor((IEventListener)strategy, additionalContentOperators);
/* 71 */     parser.processPageContent(page);
/* 72 */     return strategy.getResultantText();
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
/*    */   public static String getTextFromPage(PdfPage page, ITextExtractionStrategy strategy) {
/* 84 */     return getTextFromPage(page, strategy, new HashMap<>());
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
/*    */   public static String getTextFromPage(PdfPage page) {
/* 96 */     return getTextFromPage(page, (ITextExtractionStrategy)new LocationTextExtractionStrategy());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/PdfTextExtractor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */