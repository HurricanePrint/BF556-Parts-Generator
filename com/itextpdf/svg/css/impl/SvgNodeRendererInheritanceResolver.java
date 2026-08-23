/*     */ package com.itextpdf.svg.css.impl;
/*     */ 
/*     */ import com.itextpdf.styledxmlparser.css.resolve.CssInheritance;
/*     */ import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
/*     */ import com.itextpdf.styledxmlparser.util.StyleUtil;
/*     */ import com.itextpdf.svg.renderers.ISvgNodeRenderer;
/*     */ import com.itextpdf.svg.renderers.impl.AbstractBranchSvgNodeRenderer;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvgNodeRendererInheritanceResolver
/*     */ {
/*     */   public void applyInheritanceToSubTree(ISvgNodeRenderer root, ISvgNodeRenderer subTree) {
/*  71 */     applyStyles(root, subTree);
/*     */     
/*  73 */     if (subTree instanceof AbstractBranchSvgNodeRenderer) {
/*  74 */       AbstractBranchSvgNodeRenderer subTreeAsBranch = (AbstractBranchSvgNodeRenderer)subTree;
/*  75 */       for (ISvgNodeRenderer child : subTreeAsBranch.getChildren()) {
/*  76 */         applyInheritanceToSubTree((ISvgNodeRenderer)subTreeAsBranch, child);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void applyStyles(ISvgNodeRenderer parent, ISvgNodeRenderer child) {
/*  82 */     if (parent != null && child != null) {
/*  83 */       Map<String, String> childStyles = child.getAttributeMapCopy();
/*  84 */       if (childStyles == null) {
/*  85 */         childStyles = new HashMap<>();
/*     */       }
/*  87 */       Map<String, String> parentStyles = parent.getAttributeMapCopy();
/*  88 */       String parentFontSize = parent.getAttribute("font-size");
/*  89 */       if (parentFontSize == null) {
/*  90 */         parentFontSize = "0";
/*     */       }
/*     */       
/*  93 */       Set<IStyleInheritance> inheritanceRules = new HashSet<>();
/*  94 */       inheritanceRules.add(new CssInheritance());
/*  95 */       inheritanceRules.add(new SvgAttributeInheritance());
/*     */       
/*  97 */       for (Map.Entry<String, String> parentAttribute : parentStyles.entrySet())
/*     */       {
/*  99 */         childStyles = StyleUtil.mergeParentStyleDeclaration(childStyles, parentAttribute.getKey(), parentAttribute.getValue(), parentFontSize, inheritanceRules);
/*     */       }
/* 101 */       child.setAttributesAndStyles(childStyles);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/svg/css/impl/SvgNodeRendererInheritanceResolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */