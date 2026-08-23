/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.util.CssUtils;
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
/*    */ public class CssNumericValueValidator
/*    */   implements ICssDataTypeValidator
/*    */ {
/*    */   private final boolean allowedPercent;
/*    */   private final boolean allowedNormal;
/*    */   
/*    */   public CssNumericValueValidator(boolean allowedPercent, boolean allowedNormal) {
/* 44 */     this.allowedPercent = allowedPercent;
/* 45 */     this.allowedNormal = allowedNormal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(String objectString) {
/* 53 */     if (objectString == null) {
/* 54 */       return false;
/*    */     }
/* 56 */     if ("initial".equals(objectString) || "inherit".equals(objectString) || "unset"
/* 57 */       .equals(objectString)) {
/* 58 */       return true;
/*    */     }
/* 60 */     if ("normal".equals(objectString)) {
/* 61 */       return this.allowedNormal;
/*    */     }
/* 63 */     if (!CssUtils.isValidNumericValue(objectString)) {
/* 64 */       return false;
/*    */     }
/* 66 */     if (CssUtils.isPercentageValue(objectString)) {
/* 67 */       return this.allowedPercent;
/*    */     }
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/CssNumericValueValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */