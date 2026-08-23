/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.CommonCssConstants;
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
/*    */ public class CssBlendModeValidator
/*    */   implements ICssDataTypeValidator
/*    */ {
/*    */   public boolean isValid(String objectString) {
/* 52 */     return CommonCssConstants.BLEND_MODE_VALUES.contains(objectString);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/CssBlendModeValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */