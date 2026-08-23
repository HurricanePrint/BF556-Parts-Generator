/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextNode
/*     */   extends Node
/*     */ {
/*     */   private static final String TEXT_KEY = "text";
/*     */   String text;
/*     */   
/*     */   public TextNode(String text, String baseUri) {
/*  71 */     this.baseUri = baseUri;
/*  72 */     this.text = text;
/*     */   }
/*     */   
/*     */   public String nodeName() {
/*  76 */     return "#text";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String text() {
/*  85 */     return normaliseWhitespace(getWholeText());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextNode text(String text) {
/*  94 */     this.text = text;
/*  95 */     if (this.attributes != null)
/*  96 */       this.attributes.put("text", text); 
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWholeText() {
/* 105 */     return (this.attributes == null) ? this.text : this.attributes.get("text");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlank() {
/* 113 */     return StringUtil.isBlank(getWholeText());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextNode splitText(int offset) {
/* 123 */     Validate.isTrue((offset >= 0), "Split offset must be not be negative");
/* 124 */     Validate.isTrue((offset < this.text.length()), "Split offset must not be greater than current text length");
/*     */     
/* 126 */     String head = getWholeText().substring(0, offset);
/* 127 */     String tail = getWholeText().substring(offset);
/* 128 */     text(head);
/* 129 */     TextNode tailNode = new TextNode(tail, baseUri());
/* 130 */     if (parent() != null) {
/* 131 */       parent().addChildren(siblingIndex() + 1, new Node[] { tailNode });
/*     */     }
/* 133 */     return tailNode;
/*     */   }
/*     */   
/*     */   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/* 137 */     if (out.prettyPrint() && ((siblingIndex() == 0 && this.parentNode instanceof Element && ((Element)this.parentNode).tag().formatAsBlock() && !isBlank()) || (out.outline() && siblingNodes().size() > 0 && !isBlank()))) {
/* 138 */       indent(accum, depth, out);
/*     */     }
/*     */     
/* 141 */     boolean normaliseWhite = (out.prettyPrint() && parent() instanceof Element && !Element.preserveWhitespace(parent()));
/* 142 */     Entities.escape(accum, getWholeText(), out, false, normaliseWhite, false);
/*     */   }
/*     */ 
/*     */   
/*     */   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {}
/*     */   
/*     */   public String toString() {
/* 149 */     return outerHtml();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextNode createFromEncoded(String encodedText, String baseUri) {
/* 159 */     String text = Entities.unescape(encodedText);
/* 160 */     return new TextNode(text, baseUri);
/*     */   }
/*     */   
/*     */   static String normaliseWhitespace(String text) {
/* 164 */     text = StringUtil.normaliseWhitespace(text);
/* 165 */     return text;
/*     */   }
/*     */   
/*     */   static String stripLeadingWhitespace(String text) {
/* 169 */     return text.replaceFirst("^\\s+", "");
/*     */   }
/*     */   
/*     */   static boolean lastCharIsWhitespace(StringBuilder sb) {
/* 173 */     return (sb.length() != 0 && sb.charAt(sb.length() - 1) == ' ');
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureAttributes() {
/* 178 */     if (this.attributes == null) {
/* 179 */       this.attributes = new Attributes();
/* 180 */       this.attributes.put("text", this.text);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String attr(String attributeKey) {
/* 186 */     ensureAttributes();
/* 187 */     return super.attr(attributeKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes attributes() {
/* 192 */     ensureAttributes();
/* 193 */     return super.attributes();
/*     */   }
/*     */ 
/*     */   
/*     */   public Node attr(String attributeKey, String attributeValue) {
/* 198 */     ensureAttributes();
/* 199 */     return super.attr(attributeKey, attributeValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAttr(String attributeKey) {
/* 204 */     ensureAttributes();
/* 205 */     return super.hasAttr(attributeKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public Node removeAttr(String attributeKey) {
/* 210 */     ensureAttributes();
/* 211 */     return super.removeAttr(attributeKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public String absUrl(String attributeKey) {
/* 216 */     ensureAttributes();
/* 217 */     return super.absUrl(attributeKey);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/TextNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */