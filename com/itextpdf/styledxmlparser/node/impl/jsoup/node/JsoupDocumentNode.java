/*    */ package com.itextpdf.styledxmlparser.node.impl.jsoup.node;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
/*    */ import com.itextpdf.styledxmlparser.node.IDocumentNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsoupDocumentNode
/*    */   extends JsoupElementNode
/*    */   implements IDocumentNode
/*    */ {
/*    */   private Document document;
/*    */   
/*    */   public JsoupDocumentNode(Document document) {
/* 63 */     super((Element)document);
/* 64 */     this.document = document;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Document getDocument() {
/* 73 */     return this.document;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/node/JsoupDocumentNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */