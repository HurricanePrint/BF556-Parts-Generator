/*    */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Comment
/*    */   extends Node
/*    */ {
/*    */   private static final String COMMENT_KEY = "comment";
/*    */   
/*    */   public Comment(String data, String baseUri) {
/* 60 */     super(baseUri);
/* 61 */     this.attributes.put("comment", data);
/*    */   }
/*    */   
/*    */   public String nodeName() {
/* 65 */     return "#comment";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getData() {
/* 73 */     return this.attributes.get("comment");
/*    */   }
/*    */   
/*    */   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/* 77 */     if (out.prettyPrint())
/* 78 */       indent(accum, depth, out); 
/* 79 */     accum
/* 80 */       .append("<!--")
/* 81 */       .append(getData())
/* 82 */       .append("-->");
/*    */   }
/*    */ 
/*    */   
/*    */   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {}
/*    */   
/*    */   public String toString() {
/* 89 */     return outerHtml();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/Comment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */