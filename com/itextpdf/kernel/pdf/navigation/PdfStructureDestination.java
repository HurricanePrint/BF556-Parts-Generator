/*     */ package com.itextpdf.kernel.pdf.navigation;
/*     */ 
/*     */ import com.itextpdf.kernel.PdfException;
/*     */ import com.itextpdf.kernel.pdf.PdfArray;
/*     */ import com.itextpdf.kernel.pdf.PdfDictionary;
/*     */ import com.itextpdf.kernel.pdf.PdfName;
/*     */ import com.itextpdf.kernel.pdf.PdfNumber;
/*     */ import com.itextpdf.kernel.pdf.PdfObject;
/*     */ import com.itextpdf.kernel.pdf.tagging.IStructureNode;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfMcr;
/*     */ import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class PdfStructureDestination
/*     */   extends PdfDestination
/*     */ {
/*     */   public PdfStructureDestination(PdfArray structureDestination) {
/*  61 */     super((PdfObject)structureDestination);
/*     */   }
/*     */   
/*     */   private PdfStructureDestination() {
/*  65 */     super((PdfObject)new PdfArray());
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createXYZ(PdfStructElem elem, float left, float top, float zoom) {
/*  69 */     return create(elem, PdfName.XYZ, left, Float.NaN, Float.NaN, top, zoom);
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createFit(PdfStructElem elem) {
/*  73 */     return create(elem, PdfName.Fit, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createFitH(PdfStructElem elem, float top) {
/*  77 */     return create(elem, PdfName.FitH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createFitV(PdfStructElem elem, float left) {
/*  81 */     return create(elem, PdfName.FitV, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createFitR(PdfStructElem elem, float left, float bottom, float right, float top) {
/*  85 */     return create(elem, PdfName.FitR, left, bottom, right, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createFitB(PdfStructElem elem) {
/*  89 */     return create(elem, PdfName.FitB, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createFitBH(PdfStructElem elem, float top) {
/*  93 */     return create(elem, PdfName.FitBH, Float.NaN, Float.NaN, Float.NaN, top, Float.NaN);
/*     */   }
/*     */   
/*     */   public static PdfStructureDestination createFitBV(PdfStructElem elem, float left) {
/*  97 */     return create(elem, PdfName.FitBH, left, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
/*     */   }
/*     */   
/*     */   private static PdfStructureDestination create(PdfStructElem elem, PdfName type, float left, float bottom, float right, float top, float zoom) {
/* 101 */     return (new PdfStructureDestination()).add(elem).add(type).add(left).add(bottom).add(right).add(top).add(zoom);
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfObject getDestinationPage(Map<String, PdfObject> names) {
/* 106 */     PdfObject firstObj = ((PdfArray)getPdfObject()).get(0);
/* 107 */     if (firstObj.isDictionary()) {
/* 108 */       PdfStructElem structElem = new PdfStructElem((PdfDictionary)firstObj);
/*     */       while (true) {
/* 110 */         List<IStructureNode> kids = structElem.getKids();
/* 111 */         IStructureNode firstKid = (kids.size() > 0) ? kids.get(0) : null;
/* 112 */         if (firstKid instanceof PdfMcr)
/* 113 */           return (PdfObject)((PdfMcr)firstKid).getPageObject(); 
/* 114 */         if (firstKid instanceof PdfStructElem) {
/* 115 */           structElem = (PdfStructElem)firstKid;
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWrappedObjectMustBeIndirect() {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   private PdfStructureDestination add(float value) {
/* 130 */     if (!Float.isNaN(value)) {
/* 131 */       ((PdfArray)getPdfObject()).add((PdfObject)new PdfNumber(value));
/*     */     }
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   private PdfStructureDestination add(PdfStructElem elem) {
/* 137 */     if (((PdfDictionary)elem.getPdfObject()).getIndirectReference() == null) {
/* 138 */       throw new PdfException("Structure element referenced by a structure destination shall be an indirect object.");
/*     */     }
/* 140 */     ((PdfArray)getPdfObject()).add(elem.getPdfObject());
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   private PdfStructureDestination add(PdfName type) {
/* 145 */     ((PdfArray)getPdfObject()).add((PdfObject)type);
/* 146 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/com/itextpdf/kernel/pdf/navigation/PdfStructureDestination.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */