/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.declaration;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*    */ import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
/*    */ import com.itextpdf.styledxmlparser.css.validate.ICssDeclarationValidator;
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ public class MultiTypeDeclarationValidator
/*    */   implements ICssDeclarationValidator
/*    */ {
/*    */   private List<ICssDataTypeValidator> allowedTypes;
/*    */   
/*    */   public MultiTypeDeclarationValidator(ICssDataTypeValidator... allowedTypes) {
/* 67 */     this.allowedTypes = Arrays.asList(allowedTypes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(CssDeclaration cssDeclaration) {
/* 75 */     for (ICssDataTypeValidator dTypeValidator : this.allowedTypes) {
/* 76 */       if (dTypeValidator.isValid(cssDeclaration.getExpression())) {
/* 77 */         return true;
/*    */       }
/*    */     } 
/* 80 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/declaration/MultiTypeDeclarationValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */