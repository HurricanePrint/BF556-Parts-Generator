/*    */ package com.itextpdf.kernel.pdf.canvas.parser;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.PdfDocument;
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
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
/*    */ public class PdfDocumentContentParser
/*    */ {
/*    */   private final PdfDocument pdfDocument;
/*    */   
/*    */   public PdfDocumentContentParser(PdfDocument pdfDocument) {
/* 61 */     this.pdfDocument = pdfDocument;
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
/*    */ 
/*    */ 
/*    */   
/*    */   public <E extends IEventListener> E processContent(int pageNumber, E renderListener, Map<String, IContentOperator> additionalContentOperators) {
/* 76 */     PdfCanvasProcessor processor = new PdfCanvasProcessor((IEventListener)renderListener, additionalContentOperators);
/* 77 */     processor.processPageContent(this.pdfDocument.getPage(pageNumber));
/* 78 */     return renderListener;
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
/*    */   public <E extends IEventListener> E processContent(int pageNumber, E renderListener) {
/* 90 */     return processContent(pageNumber, renderListener, new HashMap<>());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/PdfDocumentContentParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */