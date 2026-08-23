/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*    */ 
/*    */ import com.itextpdf.kernel.colors.WebColors;
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
/*    */ public class CssColorValidator
/*    */   implements ICssDataTypeValidator
/*    */ {
/*    */   public boolean isValid(String objectString) {
/* 59 */     float[] rgbaColor = WebColors.getRGBAColor(objectString);
/* 60 */     return (rgbaColor != null);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/CssColorValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */