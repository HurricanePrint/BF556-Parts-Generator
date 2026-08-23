/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataNode
/*     */   extends Node
/*     */ {
/*     */   private static final String DATA_KEY = "data";
/*     */   
/*     */   public DataNode(String data, String baseUri) {
/*  60 */     super(baseUri);
/*  61 */     this.attributes.put("data", data);
/*     */   }
/*     */   
/*     */   public String nodeName() {
/*  65 */     return "#data";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWholeData() {
/*  73 */     return this.attributes.get("data");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataNode setWholeData(String data) {
/*  82 */     this.attributes.put("data", data);
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/*  87 */     accum.append(getWholeData());
/*     */   }
/*     */ 
/*     */   
/*     */   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {}
/*     */   
/*     */   public String toString() {
/*  94 */     return outerHtml();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DataNode createFromEncoded(String encodedData, String baseUri) {
/* 104 */     String data = Entities.unescape(encodedData);
/* 105 */     return new DataNode(data, baseUri);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/DataNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */