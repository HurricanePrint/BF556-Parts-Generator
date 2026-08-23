/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
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
/*    */ public class CssIdentifierValidator
/*    */   implements ICssDataTypeValidator
/*    */ {
/*    */   public boolean isValid(String objectString) {
/* 64 */     if (objectString.length() >= 2 && objectString.startsWith("--"))
/* 65 */       return false; 
/* 66 */     if (objectString.matches("^[0-9].*")) {
/* 67 */       return false;
/*    */     }
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/CssIdentifierValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */