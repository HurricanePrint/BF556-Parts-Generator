/*    */ package com.itextpdf.styledxmlparser.css.page;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*    */ import com.itextpdf.styledxmlparser.css.CssRuleSet;
/*    */ import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CssNonStandardRuleSet
/*    */   extends CssRuleSet
/*    */ {
/*    */   public CssNonStandardRuleSet(ICssSelector selector, List<CssDeclaration> declarations) {
/* 63 */     super(selector, declarations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     StringBuilder sb = new StringBuilder(); int i;
/* 72 */     for (i = 0; i < getNormalDeclarations().size(); i++) {
/* 73 */       if (i > 0) {
/* 74 */         sb.append(";").append("\n");
/*    */       }
/* 76 */       CssDeclaration declaration = getNormalDeclarations().get(i);
/* 77 */       sb.append(declaration.toString());
/*    */     } 
/* 79 */     for (i = 0; i < getImportantDeclarations().size(); i++) {
/* 80 */       if (i > 0 || getNormalDeclarations().size() > 0) {
/* 81 */         sb.append(";").append("\n");
/*    */       }
/* 83 */       CssDeclaration declaration = getImportantDeclarations().get(i);
/* 84 */       sb.append(declaration.toString()).append(" !important");
/*    */     } 
/* 86 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/page/CssNonStandardRuleSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */