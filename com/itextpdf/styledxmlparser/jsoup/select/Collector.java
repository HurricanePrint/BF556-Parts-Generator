/*    */ package com.itextpdf.styledxmlparser.jsoup.select;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Collector
/*    */ {
/*    */   public static Elements collect(Evaluator eval, Element root) {
/* 65 */     Elements elements = new Elements();
/* 66 */     (new NodeTraversor(new Accumulator(root, elements, eval))).traverse((Node)root);
/* 67 */     return elements;
/*    */   }
/*    */   
/*    */   private static class Accumulator implements NodeVisitor {
/*    */     private final Element root;
/*    */     private final Elements elements;
/*    */     private final Evaluator eval;
/*    */     
/*    */     Accumulator(Element root, Elements elements, Evaluator eval) {
/* 76 */       this.root = root;
/* 77 */       this.elements = elements;
/* 78 */       this.eval = eval;
/*    */     }
/*    */     
/*    */     public void head(Node node, int depth) {
/* 82 */       if (node instanceof Element) {
/* 83 */         Element el = (Element)node;
/* 84 */         if (this.eval.matches(this.root, el))
/* 85 */           this.elements.add(el); 
/*    */       } 
/*    */     }
/*    */     
/*    */     public void tail(Node node, int depth) {}
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/Collector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */