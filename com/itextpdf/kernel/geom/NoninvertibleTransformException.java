/*    */ package com.itextpdf.kernel.geom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoninvertibleTransformException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 6137225240503990466L;
/*    */   public static final String DETERMINANT_IS_ZERO_CANNOT_INVERT_TRANSFORMATION = "Determinant is zero. Cannot invert transformation.";
/*    */   
/*    */   public NoninvertibleTransformException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/geom/NoninvertibleTransformException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */