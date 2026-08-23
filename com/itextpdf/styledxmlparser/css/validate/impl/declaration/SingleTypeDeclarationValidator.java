/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.declaration;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.CssDeclaration;
/*    */ import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
/*    */ import com.itextpdf.styledxmlparser.css.validate.ICssDeclarationValidator;
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
/*    */ public class SingleTypeDeclarationValidator
/*    */   implements ICssDeclarationValidator
/*    */ {
/*    */   private ICssDataTypeValidator dataTypeValidator;
/*    */   
/*    */   public SingleTypeDeclarationValidator(ICssDataTypeValidator dataTypeValidator) {
/* 64 */     this.dataTypeValidator = dataTypeValidator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(CssDeclaration cssDeclaration) {
/* 72 */     return this.dataTypeValidator.isValid(cssDeclaration.getExpression());
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/declaration/SingleTypeDeclarationValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */