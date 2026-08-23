/*     */ package com.itextpdf.styledxmlparser.css.selector;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.parse.CssSelectorParser;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.CssSeparatorSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
/*     */ import com.itextpdf.styledxmlparser.node.INode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CssSelector
/*     */   extends AbstractCssSelector
/*     */ {
/*     */   public CssSelector(List<ICssSelectorItem> selectorItems) {
/*  67 */     super(selectorItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CssSelector(String selector) {
/*  76 */     this(CssSelectorParser.parseSelectorItems(selector));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(INode element) {
/*  83 */     return matches(element, this.selectorItems.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matches(INode element, int lastSelectorItemInd) {
/*  94 */     if (!(element instanceof com.itextpdf.styledxmlparser.node.IElementNode)) {
/*  95 */       return false;
/*     */     }
/*  97 */     if (lastSelectorItemInd < 0) {
/*  98 */       return true;
/*     */     }
/* 100 */     boolean isPseudoElement = element instanceof com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
/* 101 */     for (int i = lastSelectorItemInd; i >= 0; i--) {
/* 102 */       if (isPseudoElement && this.selectorItems.get(lastSelectorItemInd) instanceof com.itextpdf.styledxmlparser.css.selector.item.CssPseudoElementSelectorItem && i < lastSelectorItemInd) {
/*     */ 
/*     */ 
/*     */         
/* 106 */         element = element.parentNode();
/* 107 */         isPseudoElement = false;
/*     */       } 
/* 109 */       ICssSelectorItem currentItem = this.selectorItems.get(i);
/* 110 */       if (currentItem instanceof CssSeparatorSelectorItem) {
/* 111 */         INode parent; char separator = ((CssSeparatorSelectorItem)currentItem).getSeparator();
/* 112 */         switch (separator) {
/*     */           case '>':
/* 114 */             return matches(element.parentNode(), i - 1);
/*     */           case ' ':
/* 116 */             parent = element.parentNode();
/* 117 */             while (parent != null) {
/* 118 */               boolean parentMatches = matches(parent, i - 1);
/* 119 */               if (parentMatches) {
/* 120 */                 return true;
/*     */               }
/* 122 */               parent = parent.parentNode();
/*     */             } 
/*     */             
/* 125 */             return false;
/*     */           
/*     */           case '~':
/* 128 */             parent = element.parentNode();
/* 129 */             if (parent != null) {
/* 130 */               int indexOfElement = parent.childNodes().indexOf(element);
/* 131 */               for (int j = indexOfElement - 1; j >= 0; j--) {
/* 132 */                 if (matches(parent.childNodes().get(j), i - 1)) {
/* 133 */                   return true;
/*     */                 }
/*     */               } 
/*     */             } 
/* 137 */             return false;
/*     */           
/*     */           case '+':
/* 140 */             parent = element.parentNode();
/* 141 */             if (parent != null) {
/* 142 */               int indexOfElement = parent.childNodes().indexOf(element);
/* 143 */               INode previousElement = null;
/* 144 */               for (int j = indexOfElement - 1; j >= 0; j--) {
/* 145 */                 if (parent.childNodes().get(j) instanceof com.itextpdf.styledxmlparser.node.IElementNode) {
/* 146 */                   previousElement = parent.childNodes().get(j); break;
/*     */                 } 
/*     */               } 
/* 149 */               if (previousElement != null)
/* 150 */                 return (indexOfElement > 0 && matches(previousElement, i - 1)); 
/*     */             } 
/* 152 */             return false;
/*     */         } 
/*     */         
/* 155 */         return false;
/*     */       } 
/*     */       
/* 158 */       if (!currentItem.matches(element)) {
/* 159 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 164 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/CssSelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */