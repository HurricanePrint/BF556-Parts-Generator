/*     */ package com.itextpdf.styledxmlparser.css.selector.item;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssClassSelectorItem
/*     */   implements ICssSelectorItem
/*     */ {
/*     */   private String className;
/*     */   
/*     */   public CssClassSelectorItem(String className) {
/*  66 */     this.className = className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpecificity() {
/*  74 */     return 1024;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  82 */     return "." + this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(INode node) {
/*  90 */     if (!(node instanceof IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/*  91 */       return false;
/*     */     }
/*  93 */     IElementNode element = (IElementNode)node;
/*  94 */     String classAttr = element.getAttribute("class");
/*  95 */     if (classAttr != null && classAttr.length() > 0) {
/*  96 */       String[] classNames = classAttr.split(" ");
/*  97 */       for (String currClassName : classNames) {
/*  98 */         if (this.className.equals(currClassName.trim()))
/*  99 */           return true; 
/*     */       } 
/* 101 */     }  return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssClassSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */