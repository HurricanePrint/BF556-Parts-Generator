/*     */ package com.itextpdf.styledxmlparser.node.impl.jsoup.node;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class JsoupNode
/*     */   implements INode
/*     */ {
/*     */   private Node node;
/*  64 */   private List<INode> childNodes = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   INode parentNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsoupNode(Node node) {
/*  75 */     this.node = node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<INode> childNodes() {
/*  83 */     return Collections.unmodifiableList(this.childNodes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(INode node) {
/*  91 */     if (node instanceof JsoupNode) {
/*  92 */       this.childNodes.add(node);
/*  93 */       ((JsoupNode)node).parentNode = this;
/*     */     } else {
/*  95 */       Logger logger = LoggerFactory.getLogger(JsoupNode.class);
/*  96 */       logger.error("Error adding child node.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INode parentNode() {
/* 105 */     return this.parentNode;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/node/impl/jsoup/node/JsoupNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */