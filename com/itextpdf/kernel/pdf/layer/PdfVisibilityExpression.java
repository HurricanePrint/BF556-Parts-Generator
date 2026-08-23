/*     */ package com.itextpdf.kernel.pdf.layer;
/*     */ 
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfObjectWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PdfVisibilityExpression
/*     */   extends PdfObjectWrapper<PdfArray>
/*     */ {
/*     */   private static final long serialVersionUID = 4152369893262322542L;
/*     */   
/*     */   public PdfVisibilityExpression(PdfArray visibilityExpressionArray) {
/*  63 */     super((PdfObject)visibilityExpressionArray);
/*  64 */     PdfName operator = visibilityExpressionArray.getAsName(0);
/*  65 */     if (visibilityExpressionArray.size() < 1 || (!PdfName.Or.equals(operator) && 
/*  66 */       !PdfName.And.equals(operator) && !PdfName.Not.equals(operator))) {
/*  67 */       throw new IllegalArgumentException("Invalid visibilityExpressionArray");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfVisibilityExpression(PdfName operator) {
/*  76 */     super((PdfObject)new PdfArray());
/*  77 */     if (operator == null || (!PdfName.Or.equals(operator) && !PdfName.And.equals(operator) && !PdfName.Not.equals(operator)))
/*  78 */       throw new IllegalArgumentException("Invalid operator"); 
/*  79 */     ((PdfArray)getPdfObject()).add((PdfObject)operator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperand(PdfLayer layer) {
/*  87 */     ((PdfArray)getPdfObject()).add(layer.getPdfObject());
/*  88 */     ((PdfArray)getPdfObject()).setModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperand(PdfVisibilityExpression expression) {
/*  96 */     ((PdfArray)getPdfObject()).add(expression.getPdfObject());
/*  97 */     ((PdfArray)getPdfObject()).setModified();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/layer/PdfVisibilityExpression.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */