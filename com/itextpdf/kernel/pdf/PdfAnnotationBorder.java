/*    */ package com.itextpdf.kernel.pdf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfAnnotationBorder
/*    */   extends PdfObjectWrapper<PdfArray>
/*    */ {
/*    */   private static final long serialVersionUID = -4058970009483489460L;
/*    */   
/*    */   public PdfAnnotationBorder(float hRadius, float vRadius, float width) {
/* 51 */     this(hRadius, vRadius, width, null);
/*    */   }
/*    */   
/*    */   public PdfAnnotationBorder(float hRadius, float vRadius, float width, PdfDashPattern dash) {
/* 55 */     super(new PdfArray(new float[] { hRadius, vRadius, width }));
/* 56 */     if (dash != null) {
/* 57 */       PdfArray dashArray = new PdfArray();
/* 58 */       getPdfObject().add(dashArray);
/* 59 */       if (dash.getDash() >= 0.0F) {
/* 60 */         dashArray.add(new PdfNumber(dash.getDash()));
/*    */       }
/* 62 */       if (dash.getGap() >= 0.0F) {
/* 63 */         dashArray.add(new PdfNumber(dash.getGap()));
/*    */       }
/* 65 */       if (dash.getPhase() >= 0.0F) {
/* 66 */         getPdfObject().add(new PdfNumber(dash.getPhase()));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWrappedObjectMustBeIndirect() {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/PdfAnnotationBorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */