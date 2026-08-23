/*    */ package com.itextpdf.styledxmlparser.jsoup.select;
/*    */ 
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
/*    */ public class NodeTraversor
/*    */ {
/*    */   private NodeVisitor visitor;
/*    */   
/*    */   public NodeTraversor(NodeVisitor visitor) {
/* 60 */     this.visitor = visitor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void traverse(Node root) {
/* 68 */     Node node = root;
/* 69 */     int depth = 0;
/*    */     
/* 71 */     while (node != null) {
/* 72 */       this.visitor.head(node, depth);
/* 73 */       if (node.childNodeSize() > 0) {
/* 74 */         node = node.childNode(0);
/* 75 */         depth++; continue;
/*    */       } 
/* 77 */       while (node.nextSibling() == null && depth > 0) {
/* 78 */         this.visitor.tail(node, depth);
/* 79 */         node = node.parentNode();
/* 80 */         depth--;
/*    */       } 
/* 82 */       this.visitor.tail(node, depth);
/* 83 */       if (node == root)
/*    */         break; 
/* 85 */       node = node.nextSibling();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/select/NodeTraversor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */