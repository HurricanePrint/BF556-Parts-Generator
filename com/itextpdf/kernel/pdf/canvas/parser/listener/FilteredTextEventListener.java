/*    */ package com.itextpdf.kernel.pdf.canvas.parser.listener;
/*    */ 
/*    */ import com.itextpdf.kernel.pdf.canvas.parser.filter.IEventFilter;
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
/*    */ public class FilteredTextEventListener
/*    */   extends FilteredEventListener
/*    */   implements ITextExtractionStrategy
/*    */ {
/*    */   public FilteredTextEventListener(ITextExtractionStrategy delegate, IEventFilter... filterSet) {
/* 62 */     super(delegate, filterSet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getResultantText() {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     for (IEventListener delegate : this.delegates) {
/* 74 */       if (delegate instanceof ITextExtractionStrategy) {
/* 75 */         sb.append(((ITextExtractionStrategy)delegate).getResultantText());
/*    */       }
/*    */     } 
/* 78 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/canvas/parser/listener/FilteredTextEventListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */