/*     */ package com.itextpdf.styledxmlparser.jsoup.nodes;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
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
/*     */ public class DocumentType
/*     */   extends Node
/*     */ {
/*     */   private static final String NAME = "name";
/*     */   private static final String PUBLIC_ID = "publicId";
/*     */   private static final String SYSTEM_ID = "systemId";
/*     */   
/*     */   public DocumentType(String name, String publicId, String systemId, String baseUri) {
/*  67 */     super(baseUri);
/*     */     
/*  69 */     attr("name", name);
/*  70 */     attr("publicId", publicId);
/*  71 */     attr("systemId", systemId);
/*     */   }
/*     */ 
/*     */   
/*     */   public String nodeName() {
/*  76 */     return "#doctype";
/*     */   }
/*     */ 
/*     */   
/*     */   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
/*  81 */     if (out.syntax() == Document.OutputSettings.Syntax.html && !has("publicId") && !has("systemId")) {
/*     */       
/*  83 */       accum.append("<!doctype");
/*     */     } else {
/*  85 */       accum.append("<!DOCTYPE");
/*     */     } 
/*  87 */     if (has("name"))
/*  88 */       accum.append(" ").append(attr("name")); 
/*  89 */     if (has("publicId"))
/*  90 */       accum.append(" PUBLIC \"").append(attr("publicId")).append('"'); 
/*  91 */     if (has("systemId"))
/*  92 */       accum.append(" \"").append(attr("systemId")).append('"'); 
/*  93 */     accum.append('>');
/*     */   }
/*     */ 
/*     */   
/*     */   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {}
/*     */ 
/*     */   
/*     */   private boolean has(String attribute) {
/* 101 */     return !StringUtil.isBlank(attr(attribute));
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/jsoup/nodes/DocumentType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */