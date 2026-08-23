/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
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
/*     */ 
/*     */ 
/*     */ public class XmlDeclaration
/*     */   extends Node
/*     */ {
/*     */   private final String name;
/*     */   private final boolean isProcessingInstruction;
/*     */   
/*     */   public XmlDeclaration(String name, String baseUri, boolean isProcessingInstruction) {
/*  64 */     super(baseUri);
/*  65 */     Validate.notNull(name);
/*  66 */     this.name = name;
/*  67 */     this.isProcessingInstruction = isProcessingInstruction;
/*     */   }
/*     */   
/*     */   public String nodeName() {
/*  71 */     return "#declaration";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  80 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWholeDeclaration() {
/*  88 */     return this.attributes.html().trim();
/*     */   }
/*     */   
/*     */   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/*  92 */     accum
/*  93 */       .append("<")
/*  94 */       .append(this.isProcessingInstruction ? "!" : "?")
/*  95 */       .append(this.name);
/*  96 */     this.attributes.html(accum, out);
/*  97 */     accum
/*  98 */       .append(this.isProcessingInstruction ? "!" : "?")
/*  99 */       .append(">");
/*     */   }
/*     */ 
/*     */   
/*     */   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {}
/*     */   
/*     */   public String toString() {
/* 106 */     return outerHtml();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/XmlDeclaration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */