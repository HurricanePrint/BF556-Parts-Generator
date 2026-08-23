/*     */ package com.itextpdf.styledxmlparser.css.selector.item;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.node.IElementNode;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CssPseudoClassChildSelectorItem
/*     */   extends CssPseudoClassSelectorItem
/*     */ {
/*     */   CssPseudoClassChildSelectorItem(String pseudoClass) {
/*  59 */     super(pseudoClass);
/*     */   }
/*     */   
/*     */   CssPseudoClassChildSelectorItem(String pseudoClass, String arguments) {
/*  63 */     super(pseudoClass, arguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<INode> getAllSiblings(INode node) {
/*  73 */     INode parentElement = node.parentNode();
/*  74 */     if (parentElement != null) {
/*  75 */       List<INode> childrenUnmodifiable = parentElement.childNodes();
/*  76 */       List<INode> children = new ArrayList<>(childrenUnmodifiable.size());
/*  77 */       for (INode iNode : childrenUnmodifiable) {
/*  78 */         if (iNode instanceof IElementNode)
/*  79 */           children.add(iNode); 
/*     */       } 
/*  81 */       return children;
/*     */     } 
/*  83 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<INode> getAllSiblingsOfNodeType(INode node) {
/*  93 */     INode parentElement = node.parentNode();
/*  94 */     if (parentElement != null) {
/*  95 */       List<INode> childrenUnmodifiable = parentElement.childNodes();
/*  96 */       List<INode> children = new ArrayList<>(childrenUnmodifiable.size());
/*  97 */       for (INode iNode : childrenUnmodifiable) {
/*  98 */         if (iNode instanceof IElementNode && ((IElementNode)iNode).name().equals(((IElementNode)node).name()))
/*  99 */           children.add(iNode); 
/*     */       } 
/* 101 */       return children;
/*     */     } 
/* 103 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassChildSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */