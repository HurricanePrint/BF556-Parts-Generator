/*    */ package com.itextpdf.styledxmlparser.css.validate.impl.datatype;
/*    */ 
/*    */ import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
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
/*    */ public class CssEnumValidator
/*    */   implements ICssDataTypeValidator
/*    */ {
/*    */   private List<String> allowedValues;
/*    */   
/*    */   public CssEnumValidator(String... allowedValues) {
/* 67 */     this.allowedValues = new ArrayList<>(Arrays.asList(allowedValues));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addAllowedValues(Collection<String> allowedValues) {
/* 76 */     this.allowedValues.addAll(allowedValues);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid(String objectString) {
/* 84 */     return this.allowedValues.contains(objectString);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/styledxmlparser/css/validate/impl/datatype/CssEnumValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */