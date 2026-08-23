/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
/*    */ import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
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
/*    */ public class ArrayDataTypeValidator
/*    */   implements ICssDataTypeValidator
/*    */ {
/*    */   private final ICssDataTypeValidator dataTypeValidator;
/*    */   
/*    */   public ArrayDataTypeValidator(ICssDataTypeValidator dataTypeValidator) {
/* 65 */     this.dataTypeValidator = dataTypeValidator;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValid(String objectString) {
/* 70 */     if (objectString == null) {
/* 71 */       return false;
/*    */     }
/* 73 */     List<String> values = CssUtils.splitStringWithComma(objectString);
/* 74 */     for (String value : values) {
/* 75 */       if (!this.dataTypeValidator.isValid(value.trim())) {
/* 76 */         return false;
/*    */       }
/*    */     } 
/* 79 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/ArrayDataTypeValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */