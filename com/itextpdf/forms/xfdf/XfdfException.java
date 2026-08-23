/*    */ package com.itextpdf.forms.xfdf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XfdfException
/*    */   extends RuntimeException
/*    */ {
/*    */   public static final String ATTRIBUTE_NAME_OR_VALUE_MISSING = "Attribute name or value are missing";
/*    */   public static final String PAGE_IS_MISSING = "Required Page attribute is missing.";
/*    */   
/*    */   public XfdfException(String message) {
/* 48 */     super(message);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/forms/xfdf/XfdfException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */