/*    */ package com.itextpdf.styledxmlparser.node.impl.jsoup.node;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*    */ import com.itextpdf.styledxmlparser.node.IDataNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsoupDataNode
/*    */   extends JsoupNode
/*    */   implements IDataNode
/*    */ {
/*    */   private DataNode dataNode;
/*    */   
/*    */   public JsoupDataNode(DataNode dataNode) {
/* 63 */     super((Node)dataNode);
/* 64 */     this.dataNode = dataNode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getWholeData() {
/* 72 */     return this.dataNode.getWholeData();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/node/JsoupDataNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */