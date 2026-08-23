/*     */ package com.itextpdf.styledxmlparser.css.selector.item;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.selector.CssSelector;
/*     */ import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
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
/*     */ 
/*     */ public abstract class CssPseudoClassSelectorItem
/*     */   implements ICssSelectorItem
/*     */ {
/*     */   protected String arguments;
/*     */   private String pseudoClass;
/*     */   
/*     */   protected CssPseudoClassSelectorItem(String pseudoClass) {
/*  69 */     this(pseudoClass, "");
/*     */   }
/*     */   
/*     */   protected CssPseudoClassSelectorItem(String pseudoClass, String arguments) {
/*  73 */     this.pseudoClass = pseudoClass;
/*  74 */     this.arguments = arguments;
/*     */   }
/*     */   public static CssPseudoClassSelectorItem create(String fullSelectorString) {
/*     */     String pseudoClass, arguments;
/*  78 */     int indexOfParentheses = fullSelectorString.indexOf('(');
/*     */ 
/*     */     
/*  81 */     if (indexOfParentheses == -1) {
/*  82 */       pseudoClass = fullSelectorString;
/*  83 */       arguments = "";
/*     */     } else {
/*  85 */       pseudoClass = fullSelectorString.substring(0, indexOfParentheses);
/*  86 */       arguments = fullSelectorString.substring(indexOfParentheses + 1, fullSelectorString.length() - 1).trim();
/*     */     } 
/*  88 */     return create(pseudoClass, arguments);
/*     */   }
/*     */   public static CssPseudoClassSelectorItem create(String pseudoClass, String arguments) {
/*     */     CssSelector selector;
/*  92 */     switch (pseudoClass) {
/*     */       case "empty":
/*  94 */         return CssPseudoClassEmptySelectorItem.getInstance();
/*     */       case "first-child":
/*  96 */         return CssPseudoClassFirstChildSelectorItem.getInstance();
/*     */       case "first-of-type":
/*  98 */         return CssPseudoClassFirstOfTypeSelectorItem.getInstance();
/*     */       case "last-child":
/* 100 */         return CssPseudoClassLastChildSelectorItem.getInstance();
/*     */       case "last-of-type":
/* 102 */         return CssPseudoClassLastOfTypeSelectorItem.getInstance();
/*     */       case "nth-child":
/* 104 */         return new CssPseudoClassNthChildSelectorItem(arguments);
/*     */       case "nth-of-type":
/* 106 */         return new CssPseudoClassNthOfTypeSelectorItem(arguments);
/*     */       case "not":
/* 108 */         selector = new CssSelector(arguments);
/* 109 */         for (ICssSelectorItem item : selector.getSelectorItems()) {
/* 110 */           if (item instanceof CssPseudoClassNotSelectorItem || item instanceof CssPseudoElementSelectorItem) {
/* 111 */             return null;
/*     */           }
/*     */         } 
/* 114 */         return new CssPseudoClassNotSelectorItem((ICssSelector)selector);
/*     */       case "root":
/* 116 */         return CssPseudoClassRootSelectorItem.getInstance();
/*     */       case "link":
/* 118 */         return new AlwaysApplySelectorItem(pseudoClass, arguments);
/*     */       case "active":
/*     */       case "focus":
/*     */       case "hover":
/*     */       case "target":
/*     */       case "visited":
/* 124 */         return new AlwaysNotApplySelectorItem(pseudoClass, arguments);
/*     */       case "disabled":
/* 126 */         return CssPseudoClassDisabledSelectorItem.getInstance();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpecificity() {
/* 153 */     return 1024;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(INode node) {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 169 */     return ":" + this.pseudoClass + (!this.arguments.isEmpty() ? ("(" + this.arguments + ")") : "");
/*     */   }
/*     */   
/*     */   public String getPseudoClass() {
/* 173 */     return this.pseudoClass;
/*     */   }
/*     */   
/*     */   private static class AlwaysApplySelectorItem extends CssPseudoClassSelectorItem {
/*     */     AlwaysApplySelectorItem(String pseudoClass, String arguments) {
/* 178 */       super(pseudoClass, arguments);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(INode node) {
/* 183 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class AlwaysNotApplySelectorItem extends CssPseudoClassSelectorItem {
/*     */     AlwaysNotApplySelectorItem(String pseudoClass, String arguments) {
/* 189 */       super(pseudoClass, arguments);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(INode node) {
/* 194 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */