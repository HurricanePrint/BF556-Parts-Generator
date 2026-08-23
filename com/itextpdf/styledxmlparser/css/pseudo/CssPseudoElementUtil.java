/*    */ package com.itextpdf.styledxmlparser.css.pseudo;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.node.IElementNode;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CssPseudoElementUtil
/*    */ {
/*    */   private static final String TAG_NAME_PREFIX = "pseudo-element::";
/*    */   
/*    */   public static String createPseudoElementTagName(String pseudoElementName) {
/* 65 */     return "pseudo-element::" + pseudoElementName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean hasBeforeAfterElements(IElementNode node) {
/* 75 */     if (node == null || node instanceof CssPseudoElementNode || node.name().startsWith("pseudo-element::")) {
/* 76 */       return false;
/*    */     }
/* 78 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/pseudo/CssPseudoElementUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */