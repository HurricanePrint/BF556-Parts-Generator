/*     */ package com.itextpdf.styledxmlparser.css.selector.item;
/*     */ 
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
/*     */ class CssPseudoClassNthSelectorItem
/*     */   extends CssPseudoClassChildSelectorItem
/*     */ {
/*     */   private int nthA;
/*     */   private int nthB;
/*     */   
/*     */   CssPseudoClassNthSelectorItem(String pseudoClass, String arguments) {
/*  63 */     super(pseudoClass, arguments);
/*  64 */     getNthArguments();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(INode node) {
/*  69 */     if (!(node instanceof com.itextpdf.styledxmlparser.node.IElementNode) || node instanceof com.itextpdf.styledxmlparser.node.ICustomElementNode || node instanceof com.itextpdf.styledxmlparser.node.IDocumentNode) {
/*  70 */       return false;
/*     */     }
/*  72 */     List<INode> children = getAllSiblings(node);
/*  73 */     return (!children.isEmpty() && resolveNth(node, children));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void getNthArguments() {
/*  80 */     if (this.arguments.matches("((-|\\+)?[0-9]*n(\\s*(-|\\+)\\s*[0-9]+)?|(-|\\+)?[0-9]+|odd|even)"))
/*  81 */     { if (this.arguments.equals("odd")) {
/*  82 */         this.nthA = 2;
/*  83 */         this.nthB = 1;
/*  84 */       } else if (this.arguments.equals("even")) {
/*  85 */         this.nthA = 2;
/*  86 */         this.nthB = 0;
/*     */       } else {
/*  88 */         int indexOfN = this.arguments.indexOf('n');
/*  89 */         if (indexOfN == -1) {
/*  90 */           this.nthA = 0;
/*  91 */           this.nthB = Integer.parseInt(this.arguments);
/*     */         } else {
/*  93 */           String aParticle = this.arguments.substring(0, indexOfN).trim();
/*  94 */           if (aParticle.isEmpty()) {
/*  95 */             this.nthA = 0;
/*  96 */           } else if (aParticle.length() == 1 && !Character.isDigit(aParticle.charAt(0))) {
/*  97 */             this.nthA = aParticle.equals("+") ? 1 : -1;
/*     */           } else {
/*  99 */             this.nthA = Integer.parseInt(aParticle);
/* 100 */           }  String bParticle = this.arguments.substring(indexOfN + 1).trim();
/* 101 */           if (!bParticle.isEmpty()) {
/* 102 */             this.nthB = Integer.parseInt(bParticle.charAt(0) + bParticle.substring(1).trim());
/*     */           } else {
/* 104 */             this.nthB = 0;
/*     */           } 
/*     */         } 
/*     */       }  }
/* 108 */     else { this.nthA = 0;
/* 109 */       this.nthB = 0; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean resolveNth(INode node, List<INode> children) {
/* 121 */     if (!children.contains(node))
/* 122 */       return false; 
/* 123 */     if (this.nthA > 0) {
/* 124 */       int temp = children.indexOf(node) + 1 - this.nthB;
/* 125 */       return (temp >= 0 && temp % this.nthA == 0);
/* 126 */     }  if (this.nthA < 0) {
/* 127 */       int temp = children.indexOf(node) + 1 - this.nthB;
/* 128 */       return (temp <= 0 && temp % this.nthA == 0);
/*     */     } 
/* 130 */     return (children.indexOf(node) + 1 - this.nthB == 0);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/selector/item/CssPseudoClassNthSelectorItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */