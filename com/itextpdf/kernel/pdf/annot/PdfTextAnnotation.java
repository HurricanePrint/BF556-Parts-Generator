/*     */ package com.itextpdf.kernel.pdf.annot;
/*     */ 
/*     */ import com.itextpdf.kernel.geom.Rectangle;
/*     */ import com.itextpdf.kernel.pdf.PdfBoolean;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.PdfString;
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
/*     */ public class PdfTextAnnotation
/*     */   extends PdfMarkupAnnotation
/*     */ {
/*     */   private static final long serialVersionUID = -2061119066076464569L;
/*     */   
/*     */   public PdfTextAnnotation(Rectangle rect) {
/*  58 */     super(rect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PdfTextAnnotation(PdfDictionary pdfObject) {
/*  69 */     super(pdfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfName getSubtype() {
/*  74 */     return PdfName.Text;
/*     */   }
/*     */   
/*     */   public PdfString getState() {
/*  78 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.State);
/*     */   }
/*     */   
/*     */   public PdfTextAnnotation setState(PdfString state) {
/*  82 */     return (PdfTextAnnotation)put(PdfName.State, (PdfObject)state);
/*     */   }
/*     */   
/*     */   public PdfString getStateModel() {
/*  86 */     return ((PdfDictionary)getPdfObject()).getAsString(PdfName.StateModel);
/*     */   }
/*     */   
/*     */   public PdfTextAnnotation setStateModel(PdfString stateModel) {
/*  90 */     return (PdfTextAnnotation)put(PdfName.StateModel, (PdfObject)stateModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOpen() {
/*  99 */     return PdfBoolean.TRUE.equals(((PdfDictionary)getPdfObject()).getAsBoolean(PdfName.Open));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfTextAnnotation setOpen(boolean open) {
/* 109 */     return (PdfTextAnnotation)put(PdfName.Open, (PdfObject)PdfBoolean.valueOf(open));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdfName getIconName() {
/* 119 */     return ((PdfDictionary)getPdfObject()).getAsName(PdfName.Name);
/*     */   }
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
/*     */   public PdfTextAnnotation setIconName(PdfName name) {
/* 137 */     return (PdfTextAnnotation)put(PdfName.Name, (PdfObject)name);
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/annot/PdfTextAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */