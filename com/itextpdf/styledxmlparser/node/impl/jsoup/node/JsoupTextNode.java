/*    */ package com.itextpdf.styledxmlparser.node.impl.jsoup.node;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
/*    */ import com.itextpdf.styledxmlparser.node.ITextNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsoupTextNode
/*    */   extends JsoupNode
/*    */   implements ITextNode
/*    */ {
/*    */   private TextNode textNode;
/*    */   
/*    */   public JsoupTextNode(TextNode textNode) {
/* 63 */     super((Node)textNode);
/* 64 */     this.textNode = textNode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String wholeText() {
/* 72 */     return this.textNode.getWholeText();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/node/JsoupTextNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */