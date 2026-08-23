/*    */ package com.itextpdf.styledxmlparser.node.impl.jsoup.node;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
/*    */ import com.itextpdf.styledxmlparser.node.IAttribute;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsoupAttribute
/*    */   implements IAttribute
/*    */ {
/*    */   private Attribute attribute;
/*    */   
/*    */   public JsoupAttribute(Attribute attribute) {
/* 65 */     this.attribute = attribute;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 73 */     return this.attribute.getKey();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 81 */     return this.attribute.getValue();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/node/JsoupAttribute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */