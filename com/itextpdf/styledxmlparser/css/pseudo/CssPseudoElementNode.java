/*     */ package com.itextpdf.styledxmlparser.css.pseudo;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.CssContextNode;
/*     */ import com.itextpdf.styledxmlparser.node.IAttribute;
/*     */ import com.itextpdf.styledxmlparser.node.IAttributes;
/*     */ import com.itextpdf.styledxmlparser.node.ICustomElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class CssPseudoElementNode
/*     */   extends CssContextNode
/*     */   implements IElementNode, ICustomElementNode
/*     */ {
/*     */   private String pseudoElementName;
/*     */   private String pseudoElementTagName;
/*     */   
/*     */   public CssPseudoElementNode(INode parentNode, String pseudoElementName) {
/*  77 */     super(parentNode);
/*  78 */     this.pseudoElementName = pseudoElementName;
/*  79 */     this.pseudoElementTagName = CssPseudoElementUtil.createPseudoElementTagName(pseudoElementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPseudoElementName() {
/*  88 */     return this.pseudoElementName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  96 */     return this.pseudoElementTagName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IAttributes getAttributes() {
/* 104 */     return new AttributesStub();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute(String key) {
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Map<String, String>> getAdditionalHtmlStyles() {
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAdditionalHtmlStyles(Map<String, String> styles) {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLang() {
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class AttributesStub
/*     */     implements IAttributes
/*     */   {
/*     */     private AttributesStub() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAttribute(String key) {
/* 149 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setAttribute(String key, String value) {
/* 157 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 165 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<IAttribute> iterator() {
/* 173 */       return Collections.emptyIterator();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/pseudo/CssPseudoElementNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */